import java.util.Scanner;

public class Homework_02 {

    public static String findMirrorString(String text) {

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < text.length() / 2; i++) {
            if (text.toLowerCase().charAt(i) == text.toLowerCase().charAt(text.length() - 1 - i)) {
                sb.append(text.toLowerCase().charAt(i));
            } else {
                break;
            }
        }

        if (sb.isEmpty()) {
            return "No mirrored part";
        }

        return sb.toString();
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a text: ");
        String input = scanner.nextLine();


        System.out.println("Result: ");
        System.out.println(findMirrorString(input));

    }
}
