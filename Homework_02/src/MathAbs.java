import java.util.Scanner;

public class MathAbs {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int number = Integer.parseInt(scanner.nextLine());

        System.out.println(Math.abs(number));
    }
}
