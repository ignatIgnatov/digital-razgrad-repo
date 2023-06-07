import java.util.Scanner;

public class Lab_06 {

    public static double biggerNumber(double firstNum, double secondNum) {
        return Math.max(firstNum, secondNum);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter first number: ");
        double firstNumber = scanner.nextDouble();

        System.out.print("Enter second number: ");
        double secondNumber = scanner.nextDouble();

        double result = biggerNumber(firstNumber, secondNumber);

        System.out.println(result);
        System.out.println(biggerNumber(firstNumber, secondNumber));
    }
}
