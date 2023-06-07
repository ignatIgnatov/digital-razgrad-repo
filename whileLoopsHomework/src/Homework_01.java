import java.util.Scanner;

public class Homework_01 {

    public static int sumOfNumbers(int n) {

        int sum = 0;

        if (n >= 1) {
            while (n >= 1) {

                if (n % 3 == 0 || n % 5 == 0) {
                    sum += n;
                }
                n--;
            }
        } else {
            while (n < 1) {

                if (n % 3 == 0 || n % 5 == 0) {
                    sum += n;
                }
                n++;
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a number: ");
        int n = scanner.nextInt();

        System.out.println(
                "The sum of the numbers form 1 to 'n' divisible by 3 or 5 is: " + sumOfNumbers(n)
        );
    }
}
