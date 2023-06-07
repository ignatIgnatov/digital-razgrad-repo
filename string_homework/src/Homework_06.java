import java.util.Scanner;

public class Homework_06 {

    public static void printFirstPart(int n) {
        for (int j = 1; j <= 2 * n; j++) {
            System.out.print("*");
        }
        for (int j = 1; j <= n; j++) {
            System.out.print(" ");
        }
        for (int j = 1; j <= 2 * n; j++) {
            System.out.print("*");

        }
        System.out.println();
    }

    public static void printSecondPart(int n, int i) {

        System.out.print("*");
        for (int j = 1; j <= 2 * n - 2; j++) {
            System.out.print("/");
        }
        System.out.print("*");

        if (i == (n - 1) / 2 + 1) {
            for (int j = 1; j <= n; j++) {
                System.out.print("|");
            }
        } else {
            for (int j = 1; j <= n; j++) {
                System.out.print(" ");
            }
        }
        System.out.print("*");
        for (int j = 1; j <= 2 * n - 2; j++) {
            System.out.print("/");
        }
        System.out.print("*");
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a number between 3 and 100: ");
        int n = Integer.parseInt(scanner.nextLine());
        if (n < 3 || n > 100) {
            System.out.println("Error!" + n + " is not valid number!");
        } else {
            for (int i = 1; i <= n; i++) {
                if (i == 1) {
                    printFirstPart(n);
                } else if (i > 1 && i < n) {
                    printSecondPart(n, i);
                } else if (i == n) {
                    printFirstPart(n);
                }
            }
        }
    }
}
