//with multi-threading

import java.util.Scanner;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Matrix2 {
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

        int[][] resultMatrix = new int[rowsA][colsB];


        long startTime = System.nanoTime();

        int numThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                Runnable task = new MatrixMultiplicationTask(matrixA, matrixB, resultMatrix, i, j, colsA);
                executor.execute(task);
            }
        }

        executor.shutdown();

        try {
            executor.awaitTermination(60, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000; // Convert to milliseconds
        
        System.out.println("time taken :" + duration);


        System.out.println("Result Matrix:");
        /*
        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                System.out.print(resultMatrix[i][j] + " ");
            }
            System.out.println();
        }
        */
    }

    public static int[][] generateRandomMatrix(int rows, int cols) {
        int[][] matrix = new int[rows][cols];
        Random random = new Random();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = random.nextInt(10);
            }
        }

        return matrix;
    }
}

class MatrixMultiplicationTask implements Runnable {
    private int[][] matrixA;
    private int[][] matrixB;
    private int[][] resultMatrix;
    private int row;
    private int col;
    private int n;

    public MatrixMultiplicationTask(int[][] matrixA, int[][] matrixB, int[][] resultMatrix, int row, int col, int n) {
        this.matrixA = matrixA;
        this.matrixB = matrixB;
        this.resultMatrix = resultMatrix;
        this.row = row;
        this.col = col;
        this.n = n;
    }

    @Override
    public void run() {
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += matrixA[row][i] * matrixB[i][col];
        }
        resultMatrix[row][col] = sum;
    }
}
