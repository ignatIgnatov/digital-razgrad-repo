import java.util.Scanner;

public class Homework_02 {

    public static void printHistogram(int number) {
        while (number > 0) {

            int digit = number % 10;

            System.out.print(digit);

            for (int i = 0; i < digit; i++) {
                System.out.print("*");
            }

            System.out.println();
            number /= 10;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        System.out.println("Enter a number between 100 and 30000:");
        int number = scanner.nextInt();

        if (number >= 100 && number <= 30000) {
            printHistogram(number);
        } else {
            System.out.println("Wrong number! Try again!");
        }
    }
}
