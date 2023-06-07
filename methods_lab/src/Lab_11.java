import java.util.Scanner;

public class Lab_11 {

    public static int biggerNumber(int a, int b) {
        return Math.max(a, b);
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter first number: ");
        int firstNumber = scanner.nextInt();

        System.out.println("Enter second number: ");
        int secondNumber = scanner.nextInt();

        System.out.println("Enter third number: ");
        int thirdNumber = scanner.nextInt();

        int biggerOfThreeNumbers = biggerNumber(biggerNumber(firstNumber, secondNumber), thirdNumber);

        System.out.println("The bigger number is " + biggerOfThreeNumbers);


    }
}
