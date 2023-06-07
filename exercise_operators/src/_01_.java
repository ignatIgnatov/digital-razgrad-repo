import java.util.Scanner;

public class _01_ {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int a = scanner.nextInt();
        int b = scanner.nextInt();

        String result = (a > b) ? "a is bigger than b" : "a is smaller than b";

        System.out.println(result);
    }
}
