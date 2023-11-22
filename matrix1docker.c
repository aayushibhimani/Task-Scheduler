//without multi threading

#include <stdio.h>
#include <stdlib.h>
#include <time.h>

// Function declarations
int** generateRandomMatrix(int rows, int cols);
int** multiplyMatrices(int** A, int** B, int rowsA, int colsA, int rowsB, int colsB);
void printMatrix(int** matrix, int rows, int cols);

int main() {
    srand(time(NULL)); // Seed for random number generation

    // Assuming the array contains dimensions in the format: [rowsA, colsA, rowsB, colsB]
    int dimensions[] = {10, 100, 500, 1000,2000}; // Example dimensions

    for(int i=0;i<5;i++)
    {
        int rowsA = dimensions[i];
        int colsA = dimensions[i];
        int rowsB = dimensions[i];
        int colsB = dimensions[i];

        if (colsA != rowsB) {
            printf("Matrix multiplication is not possible with the provided dimensions.\n");
            return 1;
        }

        int** matrixA = generateRandomMatrix(rowsA, colsA);
        int** matrixB = generateRandomMatrix(rowsB, colsB);

        int** result = multiplyMatrices(matrixA, matrixB, rowsA, colsA, rowsB, colsB);

        //printf("Result Matrix:\n");
        //printMatrix(result, rowsA, colsB);

        // Free allocated memory
        for (int i = 0; i < rowsA; i++) {
            free(matrixA[i]);
        }
        free(matrixA);

        for (int i = 0; i < rowsB; i++) {
            free(matrixB[i]);
        }
        free(matrixB);

        for (int i = 0; i < rowsA; i++) {
            free(result[i]);
        }
        free(result);
    }

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
