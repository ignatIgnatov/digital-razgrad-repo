import java.util.Scanner;

public class Exam_2 {

    public static int validateDigit(Scanner scanner) {
        int digit = Integer.parseInt(scanner.nextLine());
        while (digit < 0 || digit > 9) {
            System.out.println("Number " + digit + " is not a digit.\nPlease enter a digit!");
            digit = Integer.parseInt(scanner.nextLine());
        }
        return digit;
    }

    public static boolean validateForZeroes(int a, int b, int c) {
        return a == 0 && b == 0 && c == 0;
    }

    public static String biggestNumberFromThreeDigits(int a, int b, int c) {
        int firstDigitOfNumber = 0;
        int secondDigitOfNumber = 0;
        int thirdDigitOfNumber = 0;

        if (a > b && a > c) {
            firstDigitOfNumber = a;
            if (b > c) {
                secondDigitOfNumber = b;
                thirdDigitOfNumber = c;
            } else {
                secondDigitOfNumber = c;
                thirdDigitOfNumber = b;
            }
        } else if (b > a && b > c) {
            firstDigitOfNumber = b;
            if (a > c) {
                secondDigitOfNumber = a;
                thirdDigitOfNumber = c;
            } else {
                secondDigitOfNumber = c;
                thirdDigitOfNumber = a;
            }
        } else if (c > a && c > b) {
            firstDigitOfNumber = c;
            if (a > b) {
                secondDigitOfNumber = a;
                thirdDigitOfNumber = b;
            } else {
                secondDigitOfNumber = b;
                thirdDigitOfNumber = a;

            }
        }

        if (validateForZeroes(a, b, c)) {
            return "invalid number";
        }

        return "The biggest number from the digits is: " + firstDigitOfNumber + secondDigitOfNumber + thirdDigitOfNumber;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter first digit: ");
        int first = validateDigit(scanner);

        System.out.print("Enter second digit: ");
        int second = validateDigit(scanner);

        System.out.print("Enter third digit: ");
        int third = validateDigit(scanner);

        String biggestNumber = biggestNumberFromThreeDigits(first, second, third);
        System.out.println(biggestNumber);

    }
}
