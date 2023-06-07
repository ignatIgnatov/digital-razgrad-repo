import java.util.Arrays;

public class lab_03 {

    public static int[]createArray(int[] arr) {
        int resultArr[] = new int[arr.length];

        for (int i = 0; i < arr.length; i++) {
            int current = arr[i];

            if (i > 0 && i < arr.length - 1) {
                current = arr[i - 1] + arr[i + 1];
            }

            resultArr[i] = current;
        }
        return resultArr;
    }

    public static void main(String[] args) {


        int[] arr = {1, 2, 3, 4, 5};

        int[] result = createArray(arr);

        System.out.println(Arrays.toString(result));
    }
}
