import java.util.Scanner;

public class Lab_08 {

    public static String isEquals(int firstNumber, int secondNumber) {
        if (firstNumber == secondNumber) {
            return "Number " + firstNumber + " = " + secondNumber;
        }

        return "Number " + firstNumber + " and number " + secondNumber + " are not equals.";
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter first number: ");
        int firstNumber = scanner.nextInt();

        System.out.print("Enter second number: ");
        int secondNumber = scanner.nextInt();

        String result = isEquals(firstNumber, secondNumber);

        System.out.println(result);


    }
}
