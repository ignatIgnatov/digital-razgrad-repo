import java.util.Scanner;

public class Homework_04 {

    public static void fillMatrix(String[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                matrix[i][j] = ".";
            }
        }
    }

    public static void printMatrix(String[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static boolean inputValidation(int row, int col, String[][] matrix) {
        return row >= 0 && row < matrix.length && col >= 0 && col < matrix[0].length;
    }

    public static boolean winTheGame(String playerSymbol, String[][] matrix) {
        return (matrix[0][0].equals(playerSymbol) && matrix[0][1].equals(playerSymbol) && matrix[0][2].equals(playerSymbol)) ||
                (matrix[1][0].equals(playerSymbol) && matrix[1][1].equals(playerSymbol) && matrix[1][2].equals(playerSymbol) ||
                        (matrix[2][0].equals(playerSymbol) && matrix[2][1].equals(playerSymbol) && matrix[2][2].equals(playerSymbol) ||
                                (matrix[0][0].equals(playerSymbol) && matrix[1][0].equals(playerSymbol) && matrix[2][0].equals(playerSymbol) ||
                                        (matrix[0][1].equals(playerSymbol) && matrix[1][1].equals(playerSymbol) && matrix[2][1].equals(playerSymbol) ||
                                                (matrix[0][2].equals(playerSymbol) && matrix[1][2].equals(playerSymbol) && matrix[2][2].equals(playerSymbol) ||
                                                        (matrix[0][0].equals(playerSymbol) && matrix[1][1].equals(playerSymbol) && matrix[2][2].equals(playerSymbol) ||
                                                                (matrix[2][0].equals(playerSymbol) && matrix[1][1].equals(playerSymbol) && matrix[0][2].equals(playerSymbol))))))));
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        String[][] matrix = new String[3][3];
        String winner = "";

        System.out.println("Start of the game!");
        System.out.println("Player 1 - 'o'");
        System.out.println("Player 2 - 'x'");

        fillMatrix(matrix);
        printMatrix(matrix);

        int counter = 0;
        int row = 0;
        int col = 0;

        while (counter < 9) {

            if (counter % 2 == 0) {
                System.out.println("Player 1 input: ");
                row = scanner.nextInt();
                col = scanner.nextInt();

                if (inputValidation(row, col, matrix)) {
                    if (matrix[row][col].equals(".")) {
                        matrix[row][col] = "o";
                        printMatrix(matrix);
                    } else {
                        System.out.println(" * This field is taken. Select another field * ");
                        counter--;
                    }
                } else {
                    System.out.println(" * Out of range. Select another field * ");
                    counter--;
                }

            } else {
                System.out.println("Player 2 input: ");
                row = scanner.nextInt();
                col = scanner.nextInt();

                if (inputValidation(row, col, matrix)) {
                    if (matrix[row][col].equals(".")) {
                        matrix[row][col] = "x";
                        printMatrix(matrix);
                    } else {
                        System.out.println(" * This field is taken. Select another field * ");
                        counter--;
                    }
                } else {
                    System.out.println(" * Out of range. Select another field * ");
                    counter--;
                }
            }

            if (winTheGame("o", matrix)) {
                winner = "Player 1";
                break;
            } else if (winTheGame("x", matrix)) {
                winner = "Player 2";
                break;
            }

            counter++;
        }

        System.out.println("**********");
        System.out.println("GAME OVER!");
        if (counter >= 9) {
            System.out.println("The result is a draw!");
        } else {
            System.out.println(winner + " wins!");
        }
    }
}
