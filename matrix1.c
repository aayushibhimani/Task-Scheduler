//without multithreading
#include <stdio.h>
#include <stdlib.h>
#include <time.h>

// Function declarations
int** generateRandomMatrix(int rows, int cols);
int** multiplyMatrices(int** A, int** B, int rowsA, int colsA, int rowsB, int colsB);
void printMatrix(int** matrix, int rows, int cols);

int main() {
    srand(time(NULL)); // Seed for random number generation

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

    int** matrixA = generateRandomMatrix(rowsA, colsA);
    int** matrixB = generateRandomMatrix(rowsB, colsB);

    int** result = multiplyMatrices(matrixA, matrixB, rowsA, colsA, rowsB, colsB);

    // Print the matrices and the result
    //printf("Matrix A:\n");
    //printMatrix(matrixA, rowsA, colsA);
    //printf("Matrix B:\n");
    //printMatrix(matrixB, rowsB, colsB);
    //printf("Result Matrix:\n");
    //printMatrix(result, rowsA, colsB);

    // Free allocated memory
    free(matrixA[0]);
    free(matrixA);
    free(matrixB[0]);
    free(matrixB);
    free(result[0]);
    free(result);

    return 0;
}

int** generateRandomMatrix(int rows, int cols) {
    int** matrix = (int**)malloc(rows * sizeof(int*));
    for (int i = 0; i < rows; i++) {
        matrix[i] = (int*)malloc(cols * sizeof(int));
        for (int j = 0; j < cols; j++) {
            matrix[i][j] = rand() % 10; // Generates random integers between 0 and 9
        }
    }
    return matrix;
}

int** multiplyMatrices(int** A, int** B, int rowsA, int colsA, int rowsB, int colsB) {
    clock_t startTime = clock();

    int** result = (int**)malloc(rowsA * sizeof(int*));
    for (int i = 0; i < rowsA; i++) {
        result[i] = (int*)malloc(colsB * sizeof(int));
        for (int j = 0; j < colsB; j++) {
            int sum = 0;
            for (int k = 0; k < colsA; k++) {
                sum += A[i][k] * B[k][j];
            }
            result[i][j] = sum;
        }
    }

    clock_t endTime = clock();
    double duration = ((double)(endTime - startTime)) / CLOCKS_PER_SEC * 1000; // Convert to milliseconds
    printf("time taken: %f ms\n", duration);

    return result;
}

void printMatrix(int** matrix, int rows, int cols) {
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            printf("%d ", matrix[i][j]);
        }
        printf("\n");
    }
}
