import java.util.Scanner;

public class Exercise_6 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("*****");
        System.out.print("Please enter a Integer Number: ");
        int number = scanner.nextInt();
        char symbol = (char)number;

        System.out.printf("Number %d is the ASCII code of the character '%c'.%n", number, symbol);
        System.out.println("*****");
    }
}
