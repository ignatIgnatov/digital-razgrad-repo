import org.w3c.dom.ls.LSOutput;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Monopoly {

    public static String[] arrayFromFile(String filePath, int numbersOfRows) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        String[] board = new String[numbersOfRows];
        int index = 0;
        String line = "";

        while ((line = bufferedReader.readLine()) != null) {
            board[index] = line;
            index++;
        }

        bufferedReader.close();
        return board;
    }

    public static boolean moneyValidation(int money, int price) {
        return money - price >= 0;
    }

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
        sb.append("Owner: ").append(currentField[12]).append(System.lineSeparator());
        sb.append("----------").append(System.lineSeparator());

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
        sb.append("Owner: ").append(currentField[3]).append(System.lineSeparator());
        sb.append("----------").append(System.lineSeparator());

        return sb.toString();
    }

    public static String otherFieldCard(String[] currentField) {

        StringBuilder sb = new StringBuilder();

        sb.append("----------").append(System.lineSeparator());
        sb.append(currentField[0]).append(System.lineSeparator());
        sb.append("----------").append(System.lineSeparator());

        return sb.toString();
    }

    public static void checkForEnoughMoney(String currentPlayer, Map<String, Integer> playersBudget, List<String> properties, String[] board, Scanner scanner) {
        if (playersBudget.get(currentPlayer) <= 0) {
            System.out.println("You have no money!");
            System.out.println("Do you want to sell any of your properties?");
            String answer = enterYesOrNo(scanner);
            if (answer.equals("yes")) {
                System.out.println(currentPlayer + ", you have the following properties:");
                for (int j = 0; j < properties.size(); j++) {
                    String current = properties.get(j);
                    System.out.println(j + 1 + ". " + current);
                }
                System.out.print("Enter the number of the property you want to sell:");
                int numberOfProperty = scanner.nextInt();
                while (numberOfProperty < 1 || numberOfProperty > properties.size()) {
                    System.out.println("Enter correct number of property:");
                    numberOfProperty = scanner.nextInt();
                }
                String propertyName = properties.get(numberOfProperty - 1);
                String[] currentProperty = findPropertyByName(propertyName, board);
                int indexOfProperty = findIndexOfProperty(propertyName, board);

                properties.remove(numberOfProperty - 1);
                playersBudget.put(currentPlayer, Integer.parseInt(currentProperty[2]));

                currentProperty[1] = "for rent";
                currentProperty[currentProperty.length - 1] = "none";

                board[indexOfProperty] = String.join(", ", currentProperty);

                System.out.println("You sold a property " + propertyName);
                scanner.nextLine();

            }
        }
    }

    public static String[] findPropertyByName(String name, String[] board) {
        String result = "";
        for (int i = 0; i < board.length; i++) {
            String[] currentField = board[i].split(", ");
            if (currentField[0].equals(name)) {
                result = String.join(", ", currentField);
            }
        }
        return result.split(", ");
    }

    public static int findIndexOfProperty(String name, String[] board) {
        int index = 0;
        for (int i = 0; i < board.length; i++) {
            String[] currentField = board[i].split(", ");
            if (currentField[0].equals(name)) {
                index = i;
            }
        }
        return index;
    }

    public static void printPlayerStats(String name, int money, List<String> purchases, List<String> specialCards) {
        String TEXT_GREEN = "\u001B[32m";
        String TEXT_RESET = "\u001B[0m";
        System.out.println("-----------------------------------");
        System.out.printf(TEXT_GREEN + "%s's statistic: %n", name);
        System.out.println("Money: " + "$" + money);
        String playerFields = purchases.size() > 0 ? "Fields purchased: " + String.join(", ", purchases) : "Fields purchased: none";
        System.out.println(playerFields);
        String playerCards = specialCards.size() > 0 ? "Special cards: " + String.join(", ", specialCards) : "Special cards: none";
        System.out.println(playerCards + TEXT_RESET);
        System.out.println("-----------------------------------");
    }

    public static String checkForEmptyName(String name, Scanner scanner) {
        while (name == null || name.trim().isEmpty()) {
            System.out.print("The name can't be empty. Enter player name: ");
            name = scanner.nextLine();
        }
        return name;
    }

    public static String checkForSameName(String currentName, String[] names, Scanner scanner) {
        for (int i = 0; i < names.length; ) {
            if (currentName.equals(names[i])) {
                System.out.print("This name is already taken. Enter another name: ");
                currentName = scanner.nextLine();
                currentName = checkForEmptyName(currentName, scanner);
                i = 0;
            } else {
                i++;
            }
        }
        return currentName;
    }

    public static void printStartOfTheGame() {
        String TEXT_RED = "\u001B[31m";
        String TEXT_RESET = "\u001B[0m";

        System.out.println(TEXT_RED + "   *******************" + TEXT_RESET);
        System.out.println(TEXT_RED + "****" + TEXT_RESET + " M O N O P O L Y " + TEXT_RED + "****" + TEXT_RESET);
        System.out.println(TEXT_RED + "   *******************" + TEXT_RESET);
        System.out.println();
        System.out.println("Hello friend!");
        System.out.print("Enter a number of players (1-4): ");
    }

    public static void printStart() {
        String TEXT_RED = "\u001B[31m";
        String TEXT_RESET = "\u001B[0m";
        System.out.println();
        System.out.println(TEXT_RED + "   *********");
        System.out.println("**** START ****");
        System.out.println("   *********" + TEXT_RESET);
        System.out.println();
    }

    public static int enterNumberOfPlayers(Scanner scanner) {
        int numberOfPlayers = Integer.parseInt(scanner.nextLine());
        while (numberOfPlayers < 1 || numberOfPlayers > 4) {
            System.out.print("Enter valid number of players (1-4): ");
            numberOfPlayers = Integer.parseInt(scanner.nextLine());
        }
        return numberOfPlayers;
    }

    public static void fillPlayerNames(String[] players, Scanner scanner) {
        for (int i = 0; i < players.length; i++) {
            System.out.printf("Enter Player %d name: ", i + 1);
            String currentName = scanner.nextLine();
            currentName = checkForEmptyName(currentName, scanner);

            if (i > 0) {
                currentName = checkForSameName(currentName, players, scanner);
            }

            players[i] = currentName;
        }
    }

    public static int rollTheDice(String currentPlayer, Scanner scanner) {
        Random firstDice = new Random();
        Random secondDice = new Random();

        int firstDiceResult = 0;
        int secondDiceResult = 0;

        System.out.println();
        System.out.printf("%s, press 'Enter' to roll the dice: %n", currentPlayer);
        String playerRoll = scanner.nextLine();

        while (!playerRoll.isEmpty()) {
            System.out.printf("%s, press 'Enter' to roll the dice: %n", currentPlayer);
            playerRoll = scanner.nextLine();
        }

        firstDiceResult = firstDice.nextInt(1, 7);
        secondDiceResult = secondDice.nextInt(1, 7);
        System.out.println("First dice: " + firstDiceResult);
        System.out.println("Second dice: " + secondDiceResult);

        return firstDiceResult + secondDiceResult;
    }

    public static String enterYesOrNo(Scanner scanner) {
        System.out.println("Enter 'yes' or 'no': ");
        String answer = scanner.nextLine();

        while (!answer.equals("yes") && !answer.equals("no")) {
            System.out.println("Enter 'yes' or 'no': ");
            answer = scanner.nextLine();
        }
        return answer;
    }

    public static void goTroughTheStart(String currentPlayer, Scanner scanner) {
        System.out.println(currentPlayer + ", you go through the Start field. You get $200.");
        System.out.println("Press 'Enter' to continue.");
        String playerRoll = scanner.nextLine();

        while (!playerRoll.isEmpty()) {
            System.out.println("Press 'Enter' to continue.");
            playerRoll = scanner.nextLine();
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        String TEXT_RED = "\u001B[31m";
        String TEXT_CYAN = "\u001B[36m";
        String TEXT_YELLOW = "\u001B[33m";
        String TEXT_RESET = "\u001B[0m";

        printStartOfTheGame();

        int numberOfPlayers = enterNumberOfPlayers(scanner);

        String[] players = new String[numberOfPlayers];

        fillPlayerNames(players, scanner);

        printStart();

        Map<String, Integer> playersBudget = new HashMap<>();
        Map<String, List<String>> playersPurchases = new HashMap<>();
        Map<String, List<String>> playersSpecialCards = new HashMap<>();
        Map<String, Integer> playersFieldNumber = new HashMap<>();
        Map<String, Boolean> playersInJail = new HashMap<>();

        for (int i = 0; i < players.length; i++) {
            playersBudget.put(players[i], 200);
            playersPurchases.put(players[i], new ArrayList<>());
            playersSpecialCards.put(players[i], new ArrayList<>());
            playersFieldNumber.put(players[i], 0);
            playersInJail.put(players[i], false);
        }

        //попълване на игралната дъска от файла BoardFields.txt
        String[] board = arrayFromFile("dataBase/BoardFields.txt", 40);

        //попълване на картите Community chest от файла в масив
        String[] communityChest = arrayFromFile("dataBase/CommunityChest.txt", 16);

        //попълване на картите Chance от файла в масив
        String[] chance = arrayFromFile("dataBase/Chance.txt", 16);

        for (int i = 0; i < players.length; i++) {
            String currentPlayer = players[i];
            int moveNumber = 0;

            if (playersInJail.get(currentPlayer)) {
                if (playersSpecialCards.get(currentPlayer).contains("Get Out of Jail Free")) {
                    playersInJail.put(currentPlayer, false);
                } else {
                    System.out.printf("%s, you must roll a 12", currentPlayer);
                    moveNumber = rollTheDice(currentPlayer, scanner);
                    if (moveNumber == 12) {
                        playersInJail.put(currentPlayer, false);
                    } else {
                        continue;
                    }
                }
            }

            checkForEnoughMoney(currentPlayer, playersBudget, playersPurchases.get(currentPlayer), board, scanner);

            moveNumber = rollTheDice(currentPlayer, scanner);

            playersFieldNumber.put(currentPlayer, playersFieldNumber.get(currentPlayer) + moveNumber);

            if (playersFieldNumber.get(currentPlayer) >= 40) {
                playersFieldNumber.put(currentPlayer, playersFieldNumber.get(currentPlayer) - 40);
                playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) + 200);
                goTroughTheStart(currentPlayer, scanner);
            }

            printPlayerStats(currentPlayer, playersBudget.get(currentPlayer), playersPurchases.get(currentPlayer), playersSpecialCards.get(currentPlayer));

            String[] currentField = board[playersFieldNumber.get(currentPlayer)].split(", ");

            String fieldCard = "";

            switch (playersFieldNumber.get(currentPlayer)) {
                case 0, 2, 4, 7, 10, 17, 20, 22, 30, 33, 36, 38:
                    fieldCard = otherFieldCard(currentField);
                    System.out.println(fieldCard);
                    System.out.println("----------");
                    switch (fieldCard) {
                        case "START":
                            System.out.println("You become $200");
                            break;
                        case "Community Chest":
                            //TODO: ArrayDeque
                            break;
                        case "Income Tax":
                            playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) - 200);
                            System.out.println("Income Tax - $200");
                            break;
                        case "Chance":
                            //TODO: ArrayDeque
                            break;
                        case "Jail - only visit":
                            break;
                        case "Free Parking":
                            break;
                        case "Go To Jail":
                            playersFieldNumber.put(currentPlayer, 10);
                            playersInJail.put(currentPlayer, true);
                            break;
                        case "Luxury tax":
                            playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) - 100);
                            System.out.println("Income Tax - $100");
                            break;
                    }
                    break;
                case 1, 3, 6, 8, 9, 11, 13, 14, 16, 18, 19, 21, 23, 24, 26, 27, 29, 31, 32, 34, 37, 39:
                    fieldCard = colorFieldCard(currentField);
                    System.out.println(TEXT_CYAN + fieldCard + TEXT_RESET);

                    if (currentField[1].equals("for rent")) {
                        System.out.println("Do you want to buy " + currentField[0] + "?");
                        String answer = enterYesOrNo(scanner);

                        if (answer.equals("yes")) {
                            if (moneyValidation(playersBudget.get(currentPlayer), Integer.parseInt(currentField[2]))) {
                                currentField[1] = "Sold";
                                currentField[12] = currentPlayer;
                                playersPurchases.get(currentPlayer).add(currentField[0]);
                                playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) - Integer.parseInt(currentField[2]));
                                printPlayerStats(currentPlayer, playersBudget.get(currentPlayer), playersPurchases.get(currentPlayer), playersSpecialCards.get(currentPlayer));
                                System.out.println(TEXT_CYAN + colorFieldCard(currentField) + TEXT_RESET);
                                board[playersFieldNumber.get(currentPlayer)] = String.join(", ", currentField);
                            } else {
                                System.out.println(TEXT_RED + "Not enough money! You have $" + playersBudget.get(currentPlayer) +
                                        " and the price is $" + Integer.parseInt(currentField[2]) + TEXT_RESET);
                            }
                        }
                    } else {
                        String fieldOwner = currentField[currentField.length - 1];
                        int annuity = Integer.parseInt(currentField[3]);
                        playersBudget.put(fieldOwner, playersBudget.get(fieldOwner) + annuity);
                        playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) - annuity);
                        System.out.printf("%s pays %s an annuity of $%d%n", currentPlayer, fieldOwner, annuity);
                    }
                    break;

                case 5, 12, 15, 25, 28, 35:
                    fieldCard = companyFieldCard(currentField);
                    System.out.println(TEXT_YELLOW + fieldCard + TEXT_RESET);

                    if (currentField[1].equals("for rent")) {
                        System.out.println("Do you want to buy " + currentField[0] + "?");
                        System.out.println("Enter 'yes' or 'no': ");
                        String answer = scanner.nextLine();
                        while (!answer.equals("yes") && !answer.equals("no")) {
                            answer = scanner.nextLine();
                        }

                        if (answer.equals("yes")) {
                            if (moneyValidation(playersBudget.get(currentPlayer), Integer.parseInt(currentField[2]))) {
                                currentField[1] = "Sold";
                                currentField[3] = currentPlayer;
                                playersPurchases.get(currentPlayer).add(currentField[0]);
                                playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) - Integer.parseInt(currentField[2]));
                                printPlayerStats(currentPlayer, playersBudget.get(currentPlayer), playersPurchases.get(currentPlayer), playersSpecialCards.get(currentPlayer));
                                System.out.println(TEXT_YELLOW + companyFieldCard(currentField) + TEXT_RESET);
                                board[playersFieldNumber.get(currentPlayer)] = String.join(", ", currentField);
                            } else {
                                System.out.println(TEXT_RED + "Not enough money! You have $" + playersBudget.get(currentPlayer) +
                                        " and the price is $" + Integer.parseInt(currentField[2]) + TEXT_RESET);
                            }
                        }
                    } else {
                        String fieldOwner = currentField[3];
                        playersBudget.put(fieldOwner, playersBudget.get(fieldOwner) + 50);
                        playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) - 50);
                        System.out.printf("%s pays %s an annuity of $%d%n", currentPlayer, fieldOwner, 50);
                    }

                    break;
            }

            if (i == players.length - 1) {
                i = -1;
            } else {
                System.out.println("Next player!");
            }
        }
    }
}
