import java.util.Scanner;

public class WriteNumber {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter a number between 8 and 15");
        int number = scanner.nextInt();

        int integerDivision = number / 3;
        int remainder = number % 3;


        System.out.println("Number: " + number);
        System.out.println("Integer division: " + integerDivision);
        System.out.println("Remainder: " + remainder);
    }
}
