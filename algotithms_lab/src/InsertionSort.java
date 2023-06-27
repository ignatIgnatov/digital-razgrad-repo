import java.util.Arrays;
import java.util.Scanner;

public class InsertionSort {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        String input = scanner.nextLine();
        String[] inputArray = input.split(" ");
        int [] array = new int[inputArray.length];

        for (int i = 0; i < inputArray.length; i++) {
            array[i] = Integer.parseInt(inputArray[i]);
        }

        //ascending order
        for (int i = 1; i < array.length; i++) {
            int j = i;
            int temp;
            while (j > 0 && array[j - 1] > array[j]) {
                temp = array[j - 1];
                array[j - 1] = array[j];
                array[j] = temp;
                j = j - 1;
            }
        }
        System.out.println(Arrays.toString(array));


        //descending order
//        for (int i = 1; i < array.length; i++) {
//            int j = i;
//            int temp = 0;
//
//            while (j < array.length && array[j - 1] < array[j]) {
//                temp = array[j - 1];
//                array[j - 1] = array[j];
//                array[j] = temp;
//                j++;
//            }
//        }


        System.out.println(Arrays.toString(array));
    }
}
