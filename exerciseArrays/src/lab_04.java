import java.util.Arrays;
import java.util.Scanner;

public class lab_04 {

    public static int[] fibonacci(int number) {
        int[] result = new int[number];

        result[0] = 0;
        result[1] = 1;

        for (int i = 2; i < number; i++) {
            result[i] = result[i - 1] + result[i - 2];
        }

        return result;
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();

        int[] fib = fibonacci(n);

        System.out.println(Arrays.toString(fib));


    }
}
