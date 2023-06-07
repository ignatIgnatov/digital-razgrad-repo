import java.util.Arrays;

public class Ex_03 {
    public static void main(String[] args) {

        int[][] matrix = new int[4][5];
        int number = 2;

        for (int i = 0; i < matrix.length; i++) {
            int sum = 0;
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = number;
                if (j < matrix[i].length - 1) {
                    number += 2;
                }
                sum += matrix[i][j];
            }
            System.out.println(sum);
            number *= 2;
        }

        System.out.println(Arrays.deepToString(matrix));

    }
}
