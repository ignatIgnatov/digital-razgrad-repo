import java.util.Scanner;

public class Homework_01 {

    public static int numberOfWords(String text) {
        int count = 0;

        String[] words = text.split(" ");
        for (int i = 0; i < words.length; i++) {
            count++;
        }
        return count;
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a text:");
        String input = scanner.nextLine();

        System.out.println("The number of words is: " + numberOfWords(input));

    }
}
