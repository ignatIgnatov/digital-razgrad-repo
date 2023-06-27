import java.util.Arrays;
import java.util.Scanner;

public class DirectSelection {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String input = scanner.nextLine();
        String[] inputArray = input.split(" ");
        int [] array = new int[inputArray.length];

        for (int i = 0; i < inputArray.length; i++) {
            array[i] = Integer.parseInt(inputArray[i]);
        }
        

        for (int j = 0; j < array.length; j++) {
            int minIndex = j;
            for (int i = j + 1; i < array.length; i++) {
                if (array[i] < array[minIndex]) {
                    minIndex = i;
                }
            }
            int temp;
            if (minIndex != j) {
                temp = array[minIndex];
                array[minIndex] = array[j];
                array[j] = temp;
            }
        }

        System.out.println(Arrays.toString(array));
    }
}
