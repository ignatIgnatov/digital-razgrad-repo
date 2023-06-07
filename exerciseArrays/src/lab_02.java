public class lab_02 {

    public static boolean isMirrorArray(int[] array) {

        for (int i = 0; i < array.length / 2; i++) {
            if (array[i] != array[array.length - i - 1]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {


        int[] arr = {2, 0, 2};

        System.out.println(isMirrorArray(arr));
    }
}
