import com.sun.security.auth.UnixNumericGroupPrincipal;

import java.util.Scanner;

public class Homework_04 {

    public static void invalidInput() {
        System.out.println("Invalid input!");
    }

    public static String changeDigits(int number) {
        int secondDigit = number % 10;
        int firstDigit = number / 10;

        return "" + secondDigit + firstDigit;
    }

    public static double averageValue(double firstNumber, double secondNumber) {
        return (firstNumber + secondNumber) / 2;
    }

    public static double solveLinearEquation(double a, double b) {
        return - b / a;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("* * * * * * * * * * *");
        System.out.println("* Enter an option:  *");
        System.out.println("* * * * * * * * * * *");
        System.out.println("*1* If you want to change the digits of a two-digit number, press 1.");
        System.out.println("*2* If you want to calculate the average of two numbers, press 2");
        System.out.println("*3* If you want to solve the linear equation 'a*x+b=0', press 3");

        int option = scanner.nextInt();

        switch (option) {
            case 1:
                System.out.print("Enter a two digit number: ");
                int  number = scanner.nextInt();
                int length = String.valueOf(number).length();

                if (number > 0 && length == 2) {
                    String result = changeDigits(number);
                    System.out.println("Result: " + result);
                } else {
                    invalidInput();
                }
                break;
            case 2:
                System.out.print("Enter first number: ");
                double firstNumber = scanner.nextDouble();

                if (firstNumber < 0) {
                    invalidInput();
                } else {
                    System.out.print("Enter second number: ");
                    double secondNumber = scanner.nextDouble();

                    if (secondNumber < 0) {
                        invalidInput();
                    } else {
                        double result = averageValue(firstNumber, secondNumber);
                        System.out.printf("Result: %.2f", result);
                    }
                }
                break;
            case 3:
                System.out.print("Enter a coefficient 'a': ");
                double numberA = scanner.nextDouble();

                if (numberA == 0) {
                    invalidInput();
                } else {
                    System.out.print("Enter a coefficient 'b': ");
                    double numberB = scanner.nextDouble();

                    double result = solveLinearEquation(numberA, numberB);
                    System.out.printf("Result: x = %.2f", result);
                }
                break;
            default:
                invalidInput();
        }
    }
}
