import java.util.Scanner;

public class Homework_03 {

    public static void printNumbers(int number) {

        if (1 <= number) {
            for (int i = 1; i <= number ; i++) {
                if (i % 4 == 0 || i % 7 == 0) {
                    System.out.println(i);
                }
            }
        } else {
            for (int i = 1; i > number ; i--) {
                if (i % 4 == 0 || i % 7 == 0) {
                    System.out.println(i);
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a number: ");
        int number = scanner.nextInt();

        System.out.println("Result:");
        printNumbers(number);

    }
}
