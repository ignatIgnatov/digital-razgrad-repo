public class Homework_01 {
    public static void main(String[] args) {

        int[][] filmRates = {
                {4, 6, 2, 5},
                {7, 9, 4, 8},
                {6, 9, 3, 7}
        };

        int sum = 0;
        for (int i = 0; i < filmRates.length; i++) {
            sum += filmRates[i][2];
        }

        System.out.println("The average rate for film #3 is: " + sum * 1.0 / 3);
    }
}
