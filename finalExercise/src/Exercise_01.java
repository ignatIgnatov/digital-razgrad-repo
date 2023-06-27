public class Exercise_01 {

    public static void printMatrix(double[][] matrix) {

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }

        for (int i = 0; i < matrix[0].length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                System.out.print(matrix[j][i] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {

        double[][] matrix = {
                {1, 2.5, 3, 4.2},
                {5, 6, 7.1, 8},
                {9, 10, 11, 12}
        };

        printMatrix(matrix);
    }
}
