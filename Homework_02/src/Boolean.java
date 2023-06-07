import java.util.Scanner;

public class Boolean {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int number = Integer.parseInt(scanner.nextLine());

        boolean isDivisibleWithoutRemainder = (number % 3 == 0) && (number % 7 == 0);


        System.out.printf("Number %s is divisible by 3 and by 7 without remainder: %b", number, isDivisibleWithoutRemainder);
    }
}
