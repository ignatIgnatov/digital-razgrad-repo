import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Monopoly {

    public static void printPlayerStats(String name, double money, List<String> purchases, List<String> specialCards) {
        System.out.println("-----------------------------------");
        System.out.printf("%s's statistic: %n", name);
        System.out.println("Money: " + "$" + money);
        String playerFields = purchases.size() > 0 ? "Fields purchased: " + String.join(", ", purchases) : "Fields purchased: none";
        System.out.println(playerFields);
        String playerCards = specialCards.size() > 0 ? "Special cards: " + String.join(", ", specialCards) : "Special cards: none";
        System.out.println(playerCards);
        System.out.println("-----------------------------------");
    }

    public static void checkForEmptyName(String name, Scanner scanner) {
        while (name.trim().isEmpty()) {
            System.out.print("The name can't be empty. Enter player name: ");
            name = scanner.nextLine();
        }
    }

    public static void checkForSameName(String name1, String name2, Scanner scanner) {
        while (name1.equals(name2)) {
            System.out.print("This name is already taken. Enter another name: ");
            name1 = scanner.nextLine();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("*******************");
        System.out.println("* M O N O P O L Y *");
        System.out.println("*******************");

        System.out.println("Hello!");
        System.out.print("Enter a number of players (1-4): ");
        int numberOfPlayers = Integer.parseInt(scanner.nextLine());
        while (numberOfPlayers < 1 || numberOfPlayers > 4) {
            System.out.print("Enter valid number of players (1-4): ");
            numberOfPlayers = Integer.parseInt(scanner.nextLine());
        }

        String firstPlayerName = "";
        String secondPlayerName = "";
        String thirdPlayerName = "";
        String fourthPlayerName = "";

        System.out.print("Enter Player 1 name: ");
        firstPlayerName = scanner.nextLine();
        checkForEmptyName(firstPlayerName, scanner);

        switch (numberOfPlayers) {
            case 2:
                System.out.print("Enter Player 2 name: ");
                secondPlayerName = scanner.nextLine();
                checkForEmptyName(secondPlayerName, scanner);
                checkForSameName(secondPlayerName, firstPlayerName, scanner);
                break;
            case 3:
                System.out.print("Enter Player 2 name: ");
                secondPlayerName = scanner.nextLine();
                checkForEmptyName(secondPlayerName, scanner);
                checkForSameName(secondPlayerName, firstPlayerName, scanner);
                System.out.print("Enter Player 3 name: ");
                thirdPlayerName = scanner.nextLine();
                checkForEmptyName(thirdPlayerName, scanner);
                checkForSameName(thirdPlayerName, secondPlayerName, scanner);
                break;
            case 4:
                System.out.print("Enter Player 2 name: ");
                secondPlayerName = scanner.nextLine();
                checkForEmptyName(secondPlayerName, scanner);
                checkForSameName(secondPlayerName, firstPlayerName, scanner);
                System.out.print("Enter Player 3 name: ");
                thirdPlayerName = scanner.nextLine();
                checkForEmptyName(thirdPlayerName, scanner);
                checkForSameName(thirdPlayerName, secondPlayerName, scanner);
                System.out.print("Enter Player 4 name: ");
                fourthPlayerName = scanner.nextLine();
                checkForEmptyName(fourthPlayerName, scanner);
                checkForSameName(fourthPlayerName, thirdPlayerName, scanner);
                break;
        }

        String[] board = {
                "Start",
                "Black 1",
                "Bank 1",
                "Black 2",
                "Income Tax",
                "Sugar factory",
                "Pink 1",
                "Chance",
                "Pink 2",
                "Pink 3",
                "Jail - just visit",
                "Purple 1",
                "Energy factory",
                "Purple 2",
                "Purple 3",
                "Station 1",
                "Orange 1",
                "Bank 2",
                "Orange 2",
                "Orange 3",
                "Parking",
                "Red 1",
                "Chance",
                "Red 2",
                "Red 3",
                "Station 2",
                "Yellow 1",
                "Yellow 2",
                "Water factory",
                "Yellow 3",
                "Go to the Jail",
                "Green 1",
                "Green 2",
                "Bank 3",
                "Green 3",
                "Station 3",
                "Chance",
                "Blue 1",
                "Luxury tax",
                "Blue 2"
        };

        System.out.println();
        System.out.println("*********");
        System.out.println("* START *");
        System.out.println("*********");
        System.out.println();

        double firstPlayerMoney = 500;
        double secondPlayerMoney = 500;
        double thirdPlayerMoney = 500;
        double fourthPlayerMoney = 500;

        List<String> firstPlayerRepository = new ArrayList<>();
        List<String> secondPlayerRepository = new ArrayList<>();
        List<String> thirdPlayerRepository = new ArrayList<>();
        List<String> fourthPlayerRepository = new ArrayList<>();

        List<String> firstPlayerSpecialCards = new ArrayList<>();

        switch (numberOfPlayers) {
            case 1:

                printPlayerStats(firstPlayerName, firstPlayerMoney, firstPlayerRepository, firstPlayerSpecialCards);

                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
        }


        System.out.printf("%s, press 'Enter' to roll the dice: %n", firstPlayerName);
        String firstPlayerRoll = scanner.nextLine();

        int firstPlayerFieldNumber = 0;
        int secondPlayerFieldNumber = 0;
        int thirdPlayerFieldNumber = 0;
        int fourthPlayerFieldNumber = 0;

        Random firstDice = new Random();
        Random secondDice = new Random();

        int firstDiceResult = 0;
        int secondDiceResult = 0;

        while (!firstPlayerRoll.isEmpty()) {
            System.out.printf("%s, press 'Enter' to roll the dice: %n", firstPlayerName);
            firstPlayerRoll = scanner.nextLine();
        }

        firstDiceResult = firstDice.nextInt(1, 7);
        secondDiceResult = secondDice.nextInt(1, 7);
        System.out.println("First dice: " + firstDiceResult);
        System.out.println("Second dice: " + secondDiceResult);

        int moveNumber = firstDiceResult + secondDiceResult;

        firstPlayerFieldNumber += moveNumber;

        String currentField = board[firstPlayerFieldNumber];
        System.out.println("Current field: " + currentField);
    }
}
