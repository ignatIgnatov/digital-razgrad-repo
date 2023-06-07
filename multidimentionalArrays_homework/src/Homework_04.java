public class Homework_04 {

    public static void printSumOfNumbersInEveryOddColumn(int[][] matrix) {

        for (int i = 0; i < matrix.length; i++) {
            int sum = 0;
            String numbers = "";

            if ((i + 1) % 2 != 0) {
                for (int j = 0; j < matrix[0].length; j++) {
                    int current = matrix[j][i];
                    sum += current;
                    numbers += current + ", ";
                }
                System.out.print(numbers + "the sum of the elements is: " + sum);
                System.out.println();
            }
        }
    }

    public static void main(String[] args) {

        int[][] matrix = {
                {11, 12, 13, 14, 15, 16},
                {21, 22, 23, 24, 25, 26},
                {31, 32, 33, 34, 35, 36},
                {41, 42, 43, 44, 45, 46},
                {51, 52, 53, 54, 55, 56},
                {61, 62, 63, 64, 65, 66}
        };

        printSumOfNumbersInEveryOddColumn(matrix);

    }
}
