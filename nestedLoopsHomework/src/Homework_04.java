import java.util.Scanner;

public class Homework_04 {

    public static boolean findASymbol(String text, char symbol) {
        for (int i = 0; i < text.length(); i++) {
            char currentCharacter = text.charAt(i);

            if (currentCharacter == symbol) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter some text: ");
        String text = scanner.nextLine();

        System.out.println("Enter a symbol: ");
        char symbol = scanner.nextLine().charAt(0);

        System.out.println(findASymbol(text, symbol));
    }
}
