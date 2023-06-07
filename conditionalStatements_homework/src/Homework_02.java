import java.util.Scanner;

public class Homework_02 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter an integer number: ");
        int number = scanner.nextInt();

        int result = Math.abs(number - 21);

        if ((number - 21) > 21) {
            System.out.println("Result: " + result * 2);
        } else {
            System.out.println("Result: " + result);
        }
    }
}
