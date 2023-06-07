import java.util.Arrays;

public class Ex_02 {
    public static void main(String[] args) {


       int[][] matrix = new int[3][6];
       int number = 1;

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = number++;
            }
        }


        int sum = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                int current = matrix[i][j];
                if (current % 2 == 0) {
                    sum += current;
                }
            }
        }

        System.out.println(sum);
    }
}
