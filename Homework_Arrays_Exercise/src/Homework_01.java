public class Homework_01 {

    public static void printAllRepeatingNumbers(int[] array) {

        for (int i = 0; i < array.length; i++) {

            for (int j = i + 1; j < array.length; j++) {

                if (array[i] == array[j]) {
                    System.out.print(array[i] + " ");
                }
            }
        }
    }

    public static void main(String[] args) {

        int[] numbers = {1, 2, 3, 1, 3};

        printAllRepeatingNumbers(numbers);
    }
}
