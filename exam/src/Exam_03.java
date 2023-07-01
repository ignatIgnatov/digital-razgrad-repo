import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Exam_03 {

    public static int countOfLetterInText(String text, char letter) {
        int counter = 0;
        for (int i = 0; i < text.length(); i++) {
            if (letter == text.charAt(i)) {
                ++counter;
            }
        }
        return counter;
    }

    public static String validateText(Scanner scanner) {
        String input = scanner.nextLine();
        while (input.isEmpty()) {
            System.out.println("Enter a text!");
            input = scanner.nextLine();
        }
        return input;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a text:");
        String input = validateText(scanner);

        System.out.println("Enter a letter. Press 9 for end of the program.");
        String letter = scanner.nextLine();

        Map<Character, Integer> countOfLetters = new HashMap<>();

        while (!letter.equals("9")) {

            int countOfCurrentLetter = countOfLetterInText(input, letter.charAt(0));

            countOfLetters.put(letter.charAt(0), countOfCurrentLetter);

            letter = scanner.nextLine();
        }

        int max = Integer.MIN_VALUE;
        char maxLetter = 0;
        for (Map.Entry<Character, Integer> character : countOfLetters.entrySet()) {

            int countOfCurrentCharacter = character.getValue();

            if (countOfCurrentCharacter > max) {
                max = countOfCurrentCharacter;
                maxLetter = character.getKey();
            }
        }

        System.out.printf("%c has most occurrences -> %d", maxLetter, max);
    }
}
