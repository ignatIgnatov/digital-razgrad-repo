import java.util.Scanner;

public class Homework_05 {

    public static String makeSquareFrame(int number) {

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < number; i++) {
            if (i == 0 || i == number - 1) {
                sb.append("?");
            } else {
                sb.append("|");
            }

            for (int j = 1; j < number - 1; j++) {
                sb.append(" - ");
            }

            if (i == 0 || i == number - 1) {
                sb.append("?");
            } else {
                sb.append("|");
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
            System.out.println(number + " is not valid number!");
        } else {
            String result = makeSquareFrame(number);
            System.out.println(result);
        }
    }
}
