import java.util.Scanner;

public class Exercise_3 {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Please enter a password: ");
        String password = scanner.nextLine();

        String printMessage = "pass1234".equals(password) ? "Welcome!" : "Wrong password!";

        System.out.println(printMessage);
    }
}
