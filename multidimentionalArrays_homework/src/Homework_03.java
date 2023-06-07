public class Homework_03 {

    public static int[][] generateMatrix(int row, int col) {

        int[][] matrix = new int[row][col];

        for (int i = 0; i < row; i++) {

            int index = 1;
            for (int j = 0; j < col; j++) {
                matrix[i][j] = index + i;
                index += col;
            }
        }
        return matrix;
    }

    public static int[][] generateReversedTable(int row, int col) {

        int[][] matrix = new int[row][col];

        int index = 5;
        for (int i = row - 1; i >= 0; i--) {
            for (int j = col - 1; j >= 0; j--) {
                matrix[i][j] = index;
                index += 5;
            }
        }

        return matrix;
    }

    public static void main(String[] args) {

        System.out.println("A:");

        int[][] matrix = generateMatrix(4, 4);

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println();
        System.out.println("B:");

        int[][] table = generateReversedTable(4, 4);

        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[0].length; j++) {
                System.out.print(table[i][j] + " ");
            }
            System.out.println();
        }

    }
}
