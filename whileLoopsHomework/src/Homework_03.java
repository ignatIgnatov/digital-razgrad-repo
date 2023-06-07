import java.util.Scanner;

public class Homework_03 {

    public static void printPlayers(int numberOfPlayers) {

        int player = 1;
        while (player <= numberOfPlayers) {
            System.out.println("Player " + player);
            player++;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a sport /football, volleyball, basketball/: ");
        String spotType = scanner.nextLine();

        int numberOfPlayers = 0;

        switch (spotType) {
            case "football":
                numberOfPlayers = 11;
                break;
            case "volleyball":
                numberOfPlayers = 6;
                break;
            case "basketball":
                numberOfPlayers = 5;
                break;
            default:
                System.out.println("Enter a valid sport type!");
                return;
        }

        printPlayers(numberOfPlayers);
    }
}
