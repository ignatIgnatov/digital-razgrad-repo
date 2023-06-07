import java.util.Scanner;

public class Exercise_2 {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        //two-digit number
        System.out.println("****************************");

        System.out.print("Enter a two-digit number: ");
        int number = scanner.nextInt();

        int firstNum = number / 10;
        int secondNum = number % 10;

        int sumOfDigits = firstNum + secondNum;

        System.out.printf("The sum of the digits of number %d is %d.%n", number, sumOfDigits);

        //four-digit number
        System.out.println("****************************");

        System.out.print("Enter a four-digit number: ");
        int fourDigitNumber = scanner.nextInt();
        int currentNumber = fourDigitNumber;

        int fourthDigit = fourDigitNumber % 10;
        fourDigitNumber /= 10;

        int thirdDigit = fourDigitNumber % 10;
        fourDigitNumber /= 10;

        int secondDigit = fourDigitNumber % 10;

        int firstDigit = fourDigitNumber / 10;

        int sum = firstDigit + secondDigit + thirdDigit + fourthDigit;

        System.out.printf("The sum of the digits of number %d is %d.", currentNumber, sum);


    }
}
