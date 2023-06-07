import java.io.PrintStream;
import java.util.Scanner;

public class Lab_01 {

    public static void writeFile(int a, int b) {

        try {

            PrintStream ps = new PrintStream("sequence.txt");

            for (int i = a; i <= b; i++) {
                if (i % 2 != 0) {
                    ps.print(i);
                    ps.print(" ");
                }
            }

            ps.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int a = scanner.nextInt();
        int b = scanner.nextInt();

        writeFile(a, b);
    }
}
