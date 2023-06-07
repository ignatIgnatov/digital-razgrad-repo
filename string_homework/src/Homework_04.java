import java.io.*;
import java.util.Scanner;

public class Homework_04 {

    public static void writeTriangleToFile(String triangle) {

        File file = new File("triangle.txt");

        try {
            PrintStream printStream = new PrintStream(file);

            printStream.print(triangle);
            printStream.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public static String triangleString(int number) {

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < number; i++) {
            for (int j = 0; j <= i; j++) {
                sb.append("!");
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString().trim();
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a number: ");
        int number = scanner.nextInt();
        if (number < 0) {
            System.out.println("Enter valid number!");
        } else {
            String triangle = triangleString(number);

            System.out.println(triangle);

            writeTriangleToFile(triangle);
        }

    }
}
