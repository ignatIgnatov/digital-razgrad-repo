import java.util.Random;
import java.util.Scanner;

public class Homework_04a {

    private static void playGame(int randomNumber, Scanner scanner) {

        System.out.println("*** Guess the number! ***");
        System.out.print("Enter a number from 1 to 20: ");
        int playerNumber = scanner.nextInt();

        while (playerNumber != randomNumber) {
            System.out.print("No! Enter another number: ");
            playerNumber = scanner.nextInt();
        }
        System.out.println("* *** *");
        System.out.printf("You guessed right, %d is the correct number!%n", playerNumber);
        System.out.println("* *** *");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Random random = new Random();
        int programNumber = random.nextInt(20) + 1;

        playGame(programNumber, scanner);

    }
}
