//without multithreading

import java.util.Scanner;
import java.util.Random;

public class Matrix1 {
        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter the number of rows for Matrix A: ");
            int rowsA = scanner.nextInt();
            System.out.print("Enter the number of columns for Matrix A: ");
            int colsA = scanner.nextInt();

            System.out.print("Enter the number of rows for Matrix B: ");
            int rowsB = scanner.nextInt();
            System.out.print("Enter the number of columns for Matrix B: ");
            int colsB = scanner.nextInt();

            if (colsA != rowsB) {
                System.out.println("Matrix multiplication is not possible with the provided dimensions.");
                return;
            }

        int[][] matrixA = generateRandomMatrix(rowsA, colsA);
        int[][] matrixB = generateRandomMatrix(rowsB, colsB);

        int[][] result = multiplyMatrices(matrixA, matrixB);

        // Print the matrices and the result
        System.out.println("Matrix A:");
        //printMatrix(matrixA);
        System.out.println("Matrix B:");
        //printMatrix(matrixB);
        System.out.println("Result Matrix:");
       // printMatrix(result);

    }

    public static int[][] generateRandomMatrix(int rows, int cols) {
        Random random = new Random();
        int[][] matrix = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = random.nextInt(10); // Generates random integers between 0 and 9
            }
        }
        return matrix;
    }

    public static int[][] multiplyMatrices(int[][] A, int[][] B) {

        long startTime = System.nanoTime();

        int numRowsA = A.length;
        int numColsA = A[0].length;
        int numRowsB = B.length;
        int numColsB = B[0].length;

        int[][] result = new int[numRowsA][numColsB];

        for (int i = 0; i < numRowsA; i++) {
            for (int j = 0; j < numColsB; j++) {
                int sum = 0;
                for (int k = 0; k < numColsA; k++) {
                    sum += A[i][k] * B[k][j];
                }
                result[i][j] = sum;
            }
        }

        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000; // Convert to milliseconds
        System.out.println("time taken :" + duration);
        return result;
    }

    public static void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}
