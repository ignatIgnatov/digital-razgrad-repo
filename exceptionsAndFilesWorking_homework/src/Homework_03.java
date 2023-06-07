
import java.io.File;
import java.io.PrintStream;
import java.util.Scanner;

public class Homework_03 {

    public static void generateTwoFiles(String filename) {

        try {

            File file = new File(filename);
            Scanner scanner = new Scanner(file, "utf-8");
            PrintStream printStream = new PrintStream("small_words.txt");
            PrintStream printStr = new PrintStream("words.txt");

            while (scanner.hasNext()) {
                String currentWord = scanner.next();

                if (currentWord.length() <= 3) {
                    printStream.print(currentWord);
                    printStream.print(System.lineSeparator());
                } else {
                    printStr.print(currentWord);
                    printStr.print(" ");
                }
            }

            printStream.close();
            printStr.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {

        generateTwoFiles("lorem.txt");
    }
}
