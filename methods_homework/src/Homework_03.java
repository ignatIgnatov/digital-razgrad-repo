import java.util.Scanner;

public class Homework_03 {

    public static void printWhetherFirstNumDivideWithoutReminderBySecondNumAndThirdNum(int firstNum, int secondNum, int thirdNum) {

        boolean divideWithoutReminderBySecondNum = firstNum % secondNum == 0;
        boolean divideWithoutReminderByThirdNum = firstNum % thirdNum == 0;

        if (divideWithoutReminderBySecondNum && divideWithoutReminderByThirdNum) {
            System.out.printf("Number %s divide without reminder by %s and %s.", firstNum, secondNum, thirdNum);
        } else {
            System.out.printf("Number %s not divide without reminder by %s and %s.", firstNum, secondNum, thirdNum);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter first number: ");
        int firstNumber = scanner.nextInt();

        System.out.print("Enter second number: ");
        int secondNumber = scanner.nextInt();

        System.out.print("Enter third number: ");
        int thirdNumber = scanner.nextInt();

        printWhetherFirstNumDivideWithoutReminderBySecondNumAndThirdNum(firstNumber, secondNumber, thirdNumber);
    }
}
