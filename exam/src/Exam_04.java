import java.util.Random;
import java.util.Scanner;

public class Exam_04 {

    public static void printWinner(int whitePlayerSum, int blackPlayerSum) {
        if (whitePlayerSum > blackPlayerSum) {
            System.out.println("White player wins!");
        } else if (blackPlayerSum > whitePlayerSum) {
            System.out.println("Black player wins!");
        }

        System.out.printf("Result: %s:%s points", whitePlayerSum, blackPlayerSum);
    }

    public static int[][] createMatrix(int n, int m) {
        int[][] matrix = new int[n][m];
        return matrix;
    }

    public static void fillMatrix(int[][] matrix) {
        Random random = new Random();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] = random.nextInt(1, 21);
            }
        }
    }

    public static int sumWhitePlayerPoints(int[][] matrix) {
        int sum = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (j % 2 != 0) {
                    sum += matrix[i][j];
                }
            }
        }
        return sum;
    }
    public static int sumBlackPlayerPoints(int[][] matrix) {
        int sum = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (j % 2 == 0) {
                    sum += matrix[i][j];
                }
            }
        }
        return sum;
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter rows of the board: ");
        int rows = scanner.nextInt();

        System.out.print("Enter columns of the board: ");
        int cols = scanner.nextInt();

        int[][] gameBoard = createMatrix(rows, cols);

        fillMatrix(gameBoard);

        int whitePlayerSum = sumWhitePlayerPoints(gameBoard);
        int blackPlayerSum = sumBlackPlayerPoints(gameBoard);

        printWinner(whitePlayerSum, blackPlayerSum);

    }
}
