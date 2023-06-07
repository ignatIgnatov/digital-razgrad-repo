import java.util.Scanner;

public class Homework_04 {

    public static void vowelOrConsonantLetter(char letter) {

        if ((letter >= 97 && letter <= 122) || (letter >= 65 && letter <= 90)) {
            if ((letter == 'a' || letter == 'o' || letter == 'u' || letter == 'e' || letter == 'i')
                || (letter == 'A' || letter == 'O' || letter == 'U' || letter == 'E' || letter == 'I')) {
                System.out.printf("The letter '%c' is vowel.", letter);
            } else {
                System.out.printf("The letter '%c' is consonant", letter);
            }
        } else {
            System.out.printf("The symbol '%c' is not a letter. Please enter a letter!", letter);
        }



    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a letter: ");
        char letter = scanner.nextLine().charAt(0);

        vowelOrConsonantLetter(letter);
    }
}
