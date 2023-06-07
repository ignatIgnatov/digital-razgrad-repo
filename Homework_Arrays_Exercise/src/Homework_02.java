import java.util.Arrays;

public class Homework_02 {

    public static String[] findAllEvenNumbers(int[][] matrix) {

        StringBuilder temp = new StringBuilder();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] % 2 == 0) {
                    temp.append(matrix[i][j]).append(" ");
                }
            }
        }

        //ако искаме метода да върне int[] допълнително правим следното преобразувание:
//        int[] result = new int[temp.toString.split(" ").length];
//        for (int i = 0; i < result.length; i++) {
//            result[i] = Integer.parseInt(temp.toString.split(" ")[i]);
//        }
//        return result;

        return temp.toString().split(" ");
    }

    public static void main(String[] args) {

    int[][] matrix = {
            {2, 4, 5, 1},
            {3, 7, 8, 5},
            {9, 5, 0, 4}
    };

        System.out.println(Arrays.toString(findAllEvenNumbers(matrix)));

    }
}
