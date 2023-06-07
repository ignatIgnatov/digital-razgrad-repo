import java.util.Random;

public class lab_01 {

    public static double averageRate(int[][] matrix, int index) {
        int sum = 0;
        for (int i = 0; i < matrix[index].length; i++) {
            sum += matrix[index][i];
        }
        return sum * 1.0 / matrix[index].length;
    }

    public static int[][] generateRandomMatrix(int rows, int cols) {
        Random random = new Random();

        int[][] matrix = new int[rows][cols];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] = random.nextInt(10) + 1;
            }
        }
        return matrix;
    }

    public static int countRateAbove(int[][] matrix, int rate) {
        int count = 0;

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                int current = matrix[i][j];
                if (current > rate) {
                    count++;
                }
            }
        }
        return count;
    }

    public static void main(String[] args) {

        int[][] matrix = generateRandomMatrix(3, 4);

        System.out.println(averageRate(matrix, 2));

        System.out.println(countRateAbove(matrix, 6));
    }
}
