import java.util.Arrays;

public class Homework_02 {
    public static void main(String[] args) {


        int[] numbers = {1, 2, 3, 4, 5, 6};

        int[] resultArray = new int[numbers.length];

        for (int i = 0; i < numbers.length; i++) {
            resultArray[i] = numbers[i] * i;
        }

        System.out.println(Arrays.toString(resultArray));
    }
}
