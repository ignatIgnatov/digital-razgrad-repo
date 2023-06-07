import java.util.Scanner;

public class Homework_01 {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number 'a': ");
        int a = scanner.nextInt();

        System.out.print("Enter number 'b': ");
        int b = scanner.nextInt();

        System.out.print("Enter number 'c': ");
        int c = scanner.nextInt();

        int d = b * b - 4 * a * c;

        double x1 = (-b + Math.sqrt(d)) / (2 * a);
        double x2 = (-b - Math.sqrt(d)) / (2 * a);

        if (d > 0) {
            System.out.printf("x1 = %.2f%n", x1);
            System.out.printf("x2 = %.2f%n", x2);
        } else if (d == 0) {
            System.out.printf("x1 = x2 = %.2f%n", x1);
        } else {
            System.out.println("There are not real roots");
        }
    }
}
