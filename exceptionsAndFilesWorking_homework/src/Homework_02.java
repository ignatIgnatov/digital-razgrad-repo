import java.io.File;
import java.io.PrintStream;
import java.util.Random;

public class Homework_02 {

    public static void writeToCsvFile(int[] array) {
        File secodFile = new File("random_numbers.csv");

        try {
            PrintStream printStream = new PrintStream(secodFile);
            for (int i = 0; i < array.length; i++) {
                printStream.print(array[i]);
                printStream.print(";");
            }
            printStream.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void writeToTxtFile(int[] array) {
        File file = new File("random_numbers.txt");

        try {
            PrintStream printStream = new PrintStream(file);
            for (int i = 0; i < array.length; i++) {
                printStream.print(array[i]);
                printStream.print(" ");
            }
            printStream.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static int[] generateArray() {

        Random random = new Random();
        int[] result = new int[10];

        for (int i = 0; i < result.length; i++) {
            result[i] = random.nextInt(101);
        }

        return result;
    }

    public static void main(String[] args) {

        int[] array = generateArray();

        writeToTxtFile(array);
        writeToCsvFile(array);
    }
}
