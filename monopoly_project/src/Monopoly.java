import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Monopoly {

    public static String colorFieldCard(String[] currentField) {
        StringBuilder sb = new StringBuilder();

        sb.append("----------").append(System.lineSeparator());
        sb.append(currentField[0]).append(System.lineSeparator());
        sb.append("Status: ").append(currentField[1]).append(System.lineSeparator());
        sb.append("----------").append(System.lineSeparator());
        sb.append("Price: ").append("$").append(currentField[2]).append(System.lineSeparator());
        sb.append("Rent: ").append("$").append(currentField[3]).append(System.lineSeparator());
        sb.append("Rent with color set: ").append("$").append(currentField[4]).append(System.lineSeparator());
        sb.append("Rent for 1 house: ").append("$").append(currentField[5]).append(System.lineSeparator());
        sb.append("Rent for 2 houses: ").append("$").append(currentField[6]).append(System.lineSeparator());
        sb.append("Rent for 3 houses: ").append("$").append(currentField[7]).append(System.lineSeparator());
        sb.append("Rent for 4 houses: ").append("$").append(currentField[8]).append(System.lineSeparator());
        sb.append("Rent for a hotel: ").append("$").append(currentField[9]).append(System.lineSeparator());
        sb.append("----------").append(System.lineSeparator());
        sb.append("House price: ").append("$").append(currentField[10]).append(System.lineSeparator());
        sb.append("Hotel price: ").append("$").append(currentField[11]).append(System.lineSeparator());
        sb.append("----------").append(System.lineSeparator());
        sb.append("Owner: ").append(currentField[12]);

        return sb.toString();
    }

    public static String companyFieldCard(String[] currentField) {
        StringBuilder sb = new StringBuilder();

        sb.append("----------").append(System.lineSeparator());
        sb.append(currentField[0]).append(System.lineSeparator());
        sb.append("Status: ").append(currentField[1]).append(System.lineSeparator());
        sb.append("----------").append(System.lineSeparator());
        sb.append("Price: ").append("$").append(currentField[2]).append(System.lineSeparator());
        sb.append("----------").append(System.lineSeparator());
        sb.append("Owner: ").append(currentField[3]);

        return sb.toString();
    }

    public static String otherFieldCard(String[] currentField) {

        StringBuilder sb = new StringBuilder();

        sb.append("----------").append(System.lineSeparator());
        sb.append(currentField[0]).append(System.lineSeparator());
        sb.append("----------").append(System.lineSeparator());

        return sb.toString();
    }

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

    public static void main(String[] args) throws IOException {
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

        System.out.println();
        System.out.println("*********");
        System.out.println("* START *");
        System.out.println("*********");
        System.out.println();

        int firstPlayerMoney = 500;
        int secondPlayerMoney = 500;
        int thirdPlayerMoney = 500;
        int fourthPlayerMoney = 500;

        List<String> firstPlayerRepository = new ArrayList<>();
        List<String> secondPlayerRepository = new ArrayList<>();
        List<String> thirdPlayerRepository = new ArrayList<>();
        List<String> fourthPlayerRepository = new ArrayList<>();

        List<String> firstPlayerSpecialCards = new ArrayList<>();

        BufferedReader bufferedReader = new BufferedReader(new FileReader("dataBase/BoardFields.txt"));

        String[] board = new String[40];
        int index = 0;
        String line = "";

        while ((line = bufferedReader.readLine()) != null) {
            board[index] = line;
            index++;
        }

        bufferedReader.close();

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

        if (firstPlayerFieldNumber >= 40) {
            firstPlayerFieldNumber -= 40;
        }

        String[] currentField = board[firstPlayerFieldNumber].split(", ");

        StringBuilder sb = new StringBuilder();

        String fieldCard = "";

        switch (firstPlayerFieldNumber) {
            case 0, 2, 4, 7, 10, 17, 20, 22, 30, 33, 36, 38:
                fieldCard = otherFieldCard(currentField);
                System.out.println(fieldCard);
                break;
            case 1, 3, 6, 8, 9, 11, 13, 14, 16, 18, 19, 21, 23, 24, 26, 27, 29, 31, 32, 34, 37, 39:
                fieldCard = colorFieldCard(currentField);
                System.out.println(fieldCard);
                if (currentField[1].equals("for rent")) {
                    System.out.println("Do you want to buy " + currentField[0] + "?");
                    System.out.print("Enter 'yes' or 'no': ");
                    String answer = scanner.nextLine();
                    if (answer.equals("yes")) {
                        currentField[1] = "Sold";
                        currentField[12] = firstPlayerName;
                        firstPlayerRepository.add(currentField[0]);
                        firstPlayerMoney -= Integer.parseInt(currentField[2]);
                        printPlayerStats(firstPlayerName, firstPlayerMoney, firstPlayerRepository, firstPlayerSpecialCards);
                        System.out.println(colorFieldCard(currentField));
                    }
                } else {

                }
                break;
            case 5, 12, 15, 25, 28, 35:
                fieldCard = companyFieldCard(currentField);
                System.out.println(fieldCard);
                break;
        }








    }
}
