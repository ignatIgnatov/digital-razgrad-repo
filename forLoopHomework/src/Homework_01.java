import java.util.Scanner;

public class Homework_01 {

    public static void printText(String text, int number) {
        for (int i = 0; i < number; i++) {
            System.out.print(text + " ");
        }
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a text: ");
        String text = scanner.nextLine();

        System.out.print("Enter a number: ");
        int number = scanner.nextInt();

        System.out.println("Result:");
        printText(text, number);
    }
}
