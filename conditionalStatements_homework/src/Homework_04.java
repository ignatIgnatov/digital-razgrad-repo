import java.util.Scanner;

public class Homework_04 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number 'a': ");
        int a = scanner.nextInt();

        System.out.print("Enter number 'b': ");
        int b = scanner.nextInt();

        System.out.print("Enter option: ");
        int option = scanner.nextInt();

        int result = 0;

        switch (option) {
            case 1:
                result = a + b;
                break;
            case 2:
                result = a - b;
                break;
            case 3:
                result = b - 1;
                break;
            case 4:
                result = a * b;
                break;
            case 5:
                result = a / b;
                break;
            case 6:
                result = b / a;
                break;
            default:
                System.out.println("Invalid option");
                return;
        }

        System.out.println("Result: " + result);
    }
}
