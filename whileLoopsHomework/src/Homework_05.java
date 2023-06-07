import java.util.Scanner;

public class Homework_05 {

    public static boolean isNumberPerfect(int myNumber) {

        int sum = 0;
        int num = 1;

        while (num < myNumber) {
            if (myNumber % num == 0) {
                sum += num;
            }
            num++;
        }

        return sum == myNumber;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a number: ");
        int number = scanner.nextInt();

        if (isNumberPerfect(number)) {
            System.out.printf("Number %d is a perfect number!", number);
        } else {
            System.out.printf("Number %d is not a perfect number!", number);
        }
    }
}
