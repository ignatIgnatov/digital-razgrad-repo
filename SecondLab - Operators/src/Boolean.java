import java.util.Scanner;

public class Boolean {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int x = Integer.parseInt(scanner.nextLine());

        boolean value = (x % 5 == 0) && (x % 7 == 0);

        System.out.println(value);
    }
}
