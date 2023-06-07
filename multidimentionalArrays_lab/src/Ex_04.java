import java.util.Arrays;
import java.util.Scanner;

public class Ex_04 {


    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();

        int[][] matrix = new int[n][n];

        int top = 0;
        int left = 0;
        int bottom = matrix.length;
        int right = matrix[0].length;

        while (top < bottom && left < right) {

            //print top
            for (int i = left; i < right; i += 1) {
                System.out.println(matrix[top][i]);
            }
            top++;

            //print right column
            for (int i = top; i < bottom; i += 1) {
                System.out.println(matrix[i][right - 1]);
            }
            right--;

            if (top < bottom) {
                //print bottom
                for (int i = right - 1; i >= left; i -= 1) {
                    System.out.println(matrix[bottom - 1][i]);
                }
                bottom--;
            }

            if (left < right) {
                //print left column
                for (int i = bottom - 1; i >= top; i -= 1) {
                    System.out.println(matrix[i][left]);
                }
                left++;
            }
        }


    }
}
