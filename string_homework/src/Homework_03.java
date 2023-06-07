import java.util.Scanner;

public class Homework_03 {

    public static int countWordsEndingInYAndZ(String text) {

        String[] textArray = text.split(" ");
        int counter = 0;

        for (int i = 0; i < textArray.length; i++) {
            String currentWord = textArray[i];

            char lastCharOfWord = currentWord.toLowerCase().charAt(currentWord.length() - 1);

            if (lastCharOfWord == 'y' || lastCharOfWord == 'z') {
                counter++;
            }
        }

        return counter;
    }

    public static void main(String[] args) {


        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a text: ");

        try {
            String input = scanner.nextLine();
            System.out.println("The number of words ending in 'y' and 'z' is " + countWordsEndingInYAndZ(input));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
