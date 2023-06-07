import java.util.Scanner;

public class Homework_02 {

    public static void printNumbersFromXToOne(int x) {

        while (x >= 1) {
            System.out.print(x + " ");
            x--;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a number 'X': ");
        int number = scanner.nextInt();

        System.out.println("The numbers from 'X' to 1 are:");
        printNumbersFromXToOne(number);
    }
}
