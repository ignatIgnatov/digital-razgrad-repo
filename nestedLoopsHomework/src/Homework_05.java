import java.util.Scanner;

public class Homework_05 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        boolean isTrue = true;

        while(isTrue) {

            System.out.print("Enter a first number: ");
            int firstNumber = scanner.nextInt();

            System.out.print("Enter a second number: ");
            int secondNumber = scanner.nextInt();

            System.out.printf("%d + %d = %d%n", firstNumber, secondNumber, firstNumber + secondNumber);
            System.out.println();

            if (secondNumber == 0) {
                isTrue = false;
            }
        }
    }
}
