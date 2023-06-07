import java.util.Scanner;

public class Lab_01 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("First number: ");
        int firstNumber = scanner.nextInt();

        System.out.println("Second number: ");
        int secondNumber = scanner.nextInt();

        if (firstNumber > secondNumber) {
            System.out.println(firstNumber + " is bigger than " + secondNumber);
        } else if (secondNumber > firstNumber) {
            System.out.println(secondNumber + " is bigger than " + firstNumber);
        } else {
            System.out.println(firstNumber + " is equals " + secondNumber);
        }
    }
}
