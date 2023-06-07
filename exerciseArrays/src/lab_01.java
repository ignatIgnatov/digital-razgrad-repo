
public class lab_01 {

    public static int[] createStrangeArray(int[] array) {

        int[] resultArray = new int[array.length * 2];

        int index = array.length;
        for (int i = 0; i < array.length; i++) {
            resultArray[i] = array[i];

            int temp = array.length - 1 - i;
            resultArray[index++] = array[temp];
        }

        return resultArray;
    }

    public static void main(String[] args) {

        int[] arr = {1, 2, 3, 4};

        int[] numbers = createStrangeArray(arr);


        for (int i = 0; i < numbers.length; i++) {
            System.out.print(numbers[i] + " ");
        }
    }
}
