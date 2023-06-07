import java.util.Scanner;

public class Number {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int x = Integer.parseInt(scanner.nextLine());

        int c = x % 10;

        x/= 10;

        int b = x % 10;

        int a = x / 10;

        int sum = a + b + c;
        String reverse = "" + c + b + a;
        String change = "" + c + a + b;

        System.out.println(sum);
        System.out.println(reverse);
        System.out.println(change);


    }
}
