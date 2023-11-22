//with multithreading

#include <stdio.h>
#include <stdlib.h>
#include <windows.h>

typedef struct {
    int **matrixA;
    int **matrixB;
    int **resultMatrix;
    int row;
    int col;
    int n;
} MatrixData;

DWORD WINAPI multiplyMatrix(LPVOID param);
int **allocateMatrix(int rows, int cols);
void freeMatrix(int **matrix, int rows);
void generateRandomMatrix(int **matrix, int rows, int cols);

int main() {
    int rowsA, colsA, rowsB, colsB;

    printf("Enter the number of rows for Matrix A: ");
    scanf("%d", &rowsA);
    printf("Enter the number of columns for Matrix A: ");
    scanf("%d", &colsA);

    printf("Enter the number of rows for Matrix B: ");
    scanf("%d", &rowsB);
    printf("Enter the number of columns for Matrix B: ");
    scanf("%d", &colsB);

    if (colsA != rowsB) {
        printf("Matrix multiplication is not possible with the provided dimensions.\n");
        return 1;
    }

    int **matrixA = allocateMatrix(rowsA, colsA);
    int **matrixB = allocateMatrix(rowsB, colsB);
    int **resultMatrix = allocateMatrix(rowsA, colsB);

    generateRandomMatrix(matrixA, rowsA, colsA);
    generateRandomMatrix(matrixB, rowsB, colsB);

    HANDLE *threads = malloc(rowsA * colsB * sizeof(HANDLE));
    int threadCount = 0;

    DWORD startTime = GetTickCount(); // Record start time

    for (int i = 0; i < rowsA; i++) {
        for (int j = 0; j < colsB; j++) {
            MatrixData *data = malloc(sizeof(MatrixData));
            data->matrixA = matrixA;
            data->matrixB = matrixB;
            data->resultMatrix = resultMatrix;
            data->row = i;
            data->col = j;
            data->n = colsA;

            threads[threadCount++] = CreateThread(NULL, 0, multiplyMatrix, data, 0, NULL);
        }
    }

    WaitForMultipleObjects(threadCount, threads, TRUE, INFINITE);

    DWORD endTime = GetTickCount(); // Record end time

    for (int i = 0; i < threadCount; i++) {
        CloseHandle(threads[i]);
    }

    free(threads);

    // Calculate and print the duration
    DWORD duration = endTime - startTime;
    printf("Time taken: %lu milliseconds\n", duration);

    // Print result matrix
    //printf("Result Matrix:\n");
    //for (int i = 0; i < rowsA; i++) {
    //    for (int j = 0; j < colsB; j++) {
    //        printf("%d ", resultMatrix[i][j]);
    //    }
    //    printf("\n");
    //}

    // Free allocated memory
    freeMatrix(matrixA, rowsA);
    freeMatrix(matrixB, rowsB);
    freeMatrix(resultMatrix, rowsA);

    return 0;
}

DWORD WINAPI multiplyMatrix(LPVOID param) {
    MatrixData *data = (MatrixData *)param;
    int sum = 0;

    for (int i = 0; i < data->n; i++) {
        sum += data->matrixA[data->row][i] * data->matrixB[i][data->col];
    }

    data->resultMatrix[data->row][data->col] = sum;
    free(data);

    return 0;
}

int **allocateMatrix(int rows, int cols) {
    int **matrix = malloc(rows * sizeof(int *));
    for (int i = 0; i < rows; i++) {
        matrix[i] = malloc(cols * sizeof(int));
    }
    return matrix;
}

void freeMatrix(int **matrix, int rows) {
    for (int i = 0; i < rows; i++) {
        free(matrix[i]);
    }
    free(matrix);
}

void generateRandomMatrix(int **matrix, int rows, int cols) {
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            matrix[i][j] = rand() % 10; // Generates numbers between 0 and 9
        }
    }
}
