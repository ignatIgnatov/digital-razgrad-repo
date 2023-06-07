import java.util.Arrays;

public class Homework_04 {
    public static void main(String[] args) {

        int[] firstArray = {1, 2, 3, 4, 5, 1};
        int[] secondArray = {4, 10, 2, 6, 20};

        int counter = 0;

        for (int i = 0; i < firstArray.length; i++) {
            int current = firstArray[i];

            for (int j = 0; j < secondArray.length; j++) {
                if (current == secondArray[j]) {
                    counter++;
                }
            }
        }

        int[] resultArray = new int[counter];
        int index = 0;

        for (int i = 0; i < firstArray.length; i++) {
            int current = firstArray[i];

            for (int j = 0; j < secondArray.length; j++) {
                if (current == secondArray[j]) {
                    resultArray[index++] = current;
                }
            }
        }

        System.out.println(Arrays.toString(resultArray));
    }
}
