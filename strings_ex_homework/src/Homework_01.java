
import java.io.*;
import java.util.Scanner;

public class Homework_01 {

    public static int numbersOfWordsInTextFile(String filename) throws IOException {
        File file = new File("src/input.txt");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        String line = "";
        int count = 0;

        while ((line = bufferedReader.readLine()) != null) {

            String[] tempArray = line.split(" ");
            count += tempArray.length;
        }

        bufferedReader.close();
        return count;
    }

    public static int numberOfWords(String text) {
        int count = 0;

        String[] words = text.split(" ");
        for (int i = 0; i < words.length; i++) {
            count++;
        }
        return count;
    }
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a text:");
        String input = scanner.nextLine();

        System.out.println("The number of words is: " + numberOfWords(input));

        System.out.println("The number of words in 'input.txt' file is: " + numbersOfWordsInTextFile("input.txt"));

    }
}
