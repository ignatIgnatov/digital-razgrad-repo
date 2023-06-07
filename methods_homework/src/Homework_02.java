import java.util.Scanner;

public class Homework_02 {

    public static void compareDivisionReminderBy3(int firstNum, int secondNum) {

        int firstNumReminder = firstNum % 3;
        int secondNumReminder = secondNum % 3;

        if (firstNumReminder == secondNumReminder) {
            System.out.printf("When dividing by three, the remainder of %s and %s is the same", firstNum, secondNum);
        } else {
            System.out.printf("When dividing by three, the remainder of %s and %s is not the same", firstNum, secondNum);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter first number: ");
        int firstNumber = scanner.nextInt();

        System.out.print("Enter second number: ");
        int secondNumber = scanner.nextInt();

        compareDivisionReminderBy3(firstNumber, secondNumber);
    }
}
