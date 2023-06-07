import java.util.Scanner;

public class Homework_02 {

    public static String reverseTextOfTwoSymbols(String text) {
        char firstSymbol = text.charAt(0);
        char secondSymbol = text.charAt(1);

        return "" + secondSymbol + firstSymbol;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a text of two characters: ");
        String input = scanner.nextLine();

        if (input.length() == 2) {
            String result = reverseTextOfTwoSymbols(input);

            System.out.println("The reverse text of " + input + " is " + result);
        } else {
            System.out.println("Error! You entered text with " + input.length() + " characters!");
        }


    }
}
