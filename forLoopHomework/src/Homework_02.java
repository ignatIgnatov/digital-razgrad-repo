import java.util.Scanner;

public class Homework_02 {

    public static void printNumbers(int p, int q) {

        if (p <= q) {
            for (int i = p; i <= q ; i++) {
                if (i % 5 == 0) {
                    System.out.println(i);
                }
            }
        } else {
            for (int i = p; i > q ; i--) {
                if (i % 5 == 0) {
                    System.out.println(i);
                }
            }
        }
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a number: ");
        int p = scanner.nextInt();

        System.out.print("Enter another number: ");
        int q = scanner.nextInt();

        System.out.println("Result:");
        printNumbers(p, q);
    }
}
