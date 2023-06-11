import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Homework_03 {
    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new FileReader("src/numbers.txt"));

        String line = "";
        List<Integer> evenNumbers = new ArrayList<>();
        Set<Integer> oddNumbers = new HashSet<>();

        while ((line = bufferedReader.readLine()) != null) {
            String[] lineArray = line.split(" ");

            for (int i = 0; i < lineArray.length; i++) {
                int current = Integer.parseInt(lineArray[i]);

                if (current % 2 == 0) {
                    evenNumbers.add(current);
                } else {
                    oddNumbers.add(current);
                }
            }
        }

        PrintStream printStream = new PrintStream("src/result.txt");

        printStream.print("Even numbers:");
        printStream.print(System.lineSeparator());
        evenNumbers.forEach(e -> printStream.print(e + " "));
        printStream.print(System.lineSeparator());

        printStream.print("Odd numbers:");
        printStream.print(System.lineSeparator());
        oddNumbers.forEach(e -> printStream.print(e + " "));

        printStream.close();
    }
}
