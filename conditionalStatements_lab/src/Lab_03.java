import java.sql.SQLOutput;
import java.util.Scanner;

public class Lab_03 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the sum /lv./:");
        int sum = scanner.nextInt();

        System.out.println("Enter currency: ");
        String currency = scanner.next();

        double coefficient = 0;

        if (currency.equals("USD")) {
            coefficient = 0.54;
        } else if (currency.equals("EUR")) {
            coefficient = 0.51;
        } else if (currency.equals("GBP")) {
            coefficient = 0.43;
        }

        double totalSum = sum * coefficient;

        System.out.print("Total sum: " + totalSum);
    }
}
