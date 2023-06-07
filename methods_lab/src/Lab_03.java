import java.util.Scanner;

public class Lab_03 {

    public static void printBiggerNumber(double first, double second) {
        if (first > second) {
            System.out.println("The bigger number is " + first);
        } else {
            System.out.println("The bigger number is " + second);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter two decimal numbers: ");
        double firstNumber = scanner.nextDouble();
        double secondNumber = scanner.nextDouble();

        printBiggerNumber(firstNumber, secondNumber);
    }
}
