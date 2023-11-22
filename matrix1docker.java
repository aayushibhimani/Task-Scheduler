import java.util.Random;

public class matrix1docker {
    public static void main(String[] args) {
        // Loop to repeat the matrix multiplication 5 times

        int[] dimensions = {10, 100, 500, 1000,2000}; 

        for (int i = 0; i < 5; i++) {
            // Assuming the array contains dimensions in the format: [rowsA, colsA, rowsB, colsB]
            
            int rowsA = dimensions[i];
            int colsA = dimensions[i];
            int rowsB = dimensions[i];
            int colsB = dimensions[i];

            if (colsA != rowsB) {
                System.out.println("Matrix multiplication is not possible with the provided dimensions.");
                return;
            }

            int[][] matrixA = generateRandomMatrix(rowsA, colsA);
            int[][] matrixB = generateRandomMatrix(rowsB, colsB);

            int[][] result = multiplyMatrices(matrixA, matrixB);

            // Print the matrices and the result
            System.out.println("Dimensions " + dimensions[i]);
           // System.out.println("Matrix A:");
            //printMatrix(matrixA);
            //System.out.println("Matrix B:");
           // printMatrix(matrixB);
            //System.out.println("Result Matrix:");
            //printMatrix(result);
            System.out.println(); // Separator for each iteration
        }
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
        System.out.println("Time taken: " + duration + " ms");
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
