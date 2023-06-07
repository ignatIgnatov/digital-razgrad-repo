import java.util.Scanner;

public class Lab_02 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        System.out.println("Enter a decimal number: ");

        double number = scanner.nextDouble();

        if (number < 0) {
            System.out.printf("Number %.2f is negative%n", number);
        } else if (number > 0) {
            System.out.printf("Number %.2f is positive%n", number);
        } else {
            System.out.printf("Number %.2f is null", number);
        }
    }
}
