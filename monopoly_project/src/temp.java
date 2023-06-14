import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class temp {

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

    public static void printPlayerStats(String name, int money, List<String> purchases, List<String> specialCards) {
        String TEXT_GREEN  = "\u001B[32m";
        String TEXT_RESET  = "\u001B[0m";
        System.out.println("-----------------------------------");
        System.out.printf(TEXT_GREEN + "%s's statistic: %n", name);
        System.out.println("Money: " + "$" + money);
        String playerFields = purchases.size() > 0 ? "Fields purchased: " + String.join(", ", purchases) : "Fields purchased: none";
        System.out.println(playerFields);
        String playerCards = specialCards.size() > 0 ? "Special cards: " + String.join(", ", specialCards) : "Special cards: none";
        System.out.println(playerCards + TEXT_RESET);
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

        String TEXT_RED = "\u001B[31m";
        String TEXT_CYAN   = "\u001B[36m";
        String TEXT_YELLOW = "\u001B[33m";
        String TEXT_RESET = "\u001B[0m";

        System.out.println(TEXT_RED + "   *******************" + TEXT_RESET);
        System.out.println(TEXT_RED + "****" + TEXT_RESET + " M O N O P O L Y " + TEXT_RED + "****" + TEXT_RESET);
        System.out.println(TEXT_RED + "   *******************" + TEXT_RESET);
        System.out.println();

        System.out.println("Hello friend!");
        System.out.print("Enter a number of players (1-4): ");
        int numberOfPlayers = Integer.parseInt(scanner.nextLine());
        while (numberOfPlayers < 1 || numberOfPlayers > 4) {
            System.out.print("Enter valid number of players (1-4): ");
            numberOfPlayers = Integer.parseInt(scanner.nextLine());
        }

        System.out.print("Enter Player 1 name: ");
        String firstPlayerName = scanner.nextLine();
        checkForEmptyName(firstPlayerName, scanner);

        String secondPlayerName = "";
        String thirdPlayerName = "";
        String fourthPlayerName = "";

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
        System.out.println(TEXT_RED + "   *********");
        System.out.println("**** START ****");
        System.out.println("   *********" + TEXT_RESET);
        System.out.println();

        Map<String, Integer> playersBudget = new HashMap<>();

        List<String> firstPlayerRepository = new ArrayList<>();
        List<String> secondPlayerRepository = new ArrayList<>();
        List<String> thirdPlayerRepository = new ArrayList<>();
        List<String> fourthPlayerRepository = new ArrayList<>();

        List<String> firstPlayerSpecialCards = new ArrayList<>();
        List<String> secondPlayerSpecialCards = new ArrayList<>();
        List<String> thirdPlayerSpecialCards = new ArrayList<>();
        List<String> fourthPlayerSpecialCards = new ArrayList<>();

        //попълване на игралната дъска от файла BoardFields.txt
        String[] board = arrayFromFile("dataBase/BoardFields.txt", 40);

        //попълване на картите Community chest от файла в масив
        String[] communityChest = arrayFromFile("dataBase/CommunityChest.txt", 16);

        //попълване на картите Chance от файла в масив
        String[] chance = arrayFromFile("dataBase/Chance.txt", 16);

        int counter = 1;

        //всички играчи започват играта на нулево поле, т.е. START
        int firstPlayerFieldNumber = 0;
        int secondPlayerFieldNumber = 0;
        int thirdPlayerFieldNumber = 0;
        int fourthPlayerFieldNumber = 0;

        boolean firstPlayerInJail = true;
        boolean secondPlayerInJail = false;
        boolean thirdPlayerInJail = false;
        boolean fourthPlayerInJail = false;

        Random firstDice = new Random();
        Random secondDice = new Random();

        int firstDiceResult = 0;
        int secondDiceResult = 0;

        switch (numberOfPlayers) {
            case 1:
                //първоначално играча получава $1500
                playersBudget.put(firstPlayerName, 1500);
                int jailCounter = 0;
                int playerCounter = 1;

                while (numberOfPlayers != 0) {

                    //in JAIL
                    if (firstPlayerInJail) {
                        if (firstPlayerSpecialCards.contains("Get Out of Jail Free")) {
                            firstPlayerInJail = false;
                            continue;
                        } else {
                            jailCounter++;
                        }

                        if (jailCounter == 3) {
                            firstPlayerInJail = false;
                            jailCounter = 0;
                        }
                        continue;
                    }



                    printPlayerStats(firstPlayerName, playersBudget.get(firstPlayerName), firstPlayerRepository, firstPlayerSpecialCards);
                    System.out.println();
                    System.out.printf("%s, press 'Enter' to roll the dice: %n", firstPlayerName);
                    String firstPlayerRoll = scanner.nextLine();

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
                        playersBudget.put(firstPlayerName, playersBudget.get(firstPlayerName) + 200);
                    }

                    String[] currentField = board[firstPlayerFieldNumber].split(", ");

                    String fieldCard = "";

                    switch (firstPlayerFieldNumber) {
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
                                    playersBudget.put(firstPlayerName, playersBudget.get(firstPlayerName) - 200);
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
                                    firstPlayerFieldNumber = 10;
                                    firstPlayerInJail = true;
                                    break;
                                case "Luxury tax":
                                    playersBudget.put(firstPlayerName, playersBudget.get(firstPlayerName) - 100);
                                    System.out.println("Income Tax - $100");
                                    break;
                            }
                            break;
                        case 1, 3, 6, 8, 9, 11, 13, 14, 16, 18, 19, 21, 23, 24, 26, 27, 29, 31, 32, 34, 37, 39:
                            fieldCard = colorFieldCard(currentField);
                            System.out.println(TEXT_CYAN + fieldCard + TEXT_RESET);

                            if (currentField[1].equals("for rent")) {
                                System.out.println("Do you want to buy " + currentField[0] + "?");
                                System.out.println("Enter 'yes' or 'no': ");
                                String answer = scanner.nextLine();
                                while (!answer.equals("yes") && !answer.equals("no")) {
                                    System.out.println("Enter 'yes' or 'no': ");
                                    answer = scanner.nextLine();
                                }
                                if (answer.equals("yes")) {
                                    if (moneyValidation(playersBudget.get(firstPlayerName), Integer.parseInt(currentField[2]))) {
                                        currentField[1] = "Sold";
                                        currentField[12] = firstPlayerName;
                                        firstPlayerRepository.add(currentField[0]);
                                        playersBudget.put(firstPlayerName, playersBudget.get(firstPlayerName) - Integer.parseInt(currentField[2]));
                                        printPlayerStats(firstPlayerName, playersBudget.get(firstPlayerName), firstPlayerRepository, firstPlayerSpecialCards);
                                        System.out.println(TEXT_CYAN + colorFieldCard(currentField) + TEXT_RESET);
                                    } else {
                                        System.out.println(TEXT_RED + "Not enough money! You have $" + playersBudget.get(firstPlayerName) +
                                                " and the price is $" + Integer.parseInt(currentField[2]) + TEXT_RESET);
                                    }
                                }
                            } else {
                                String fieldOwner = currentField[12];
                                playersBudget.put(fieldOwner, playersBudget.get(fieldOwner) + Integer.parseInt(currentField[3]));
                                playersBudget.put(firstPlayerName, playersBudget.get(firstPlayerName) - Integer.parseInt(currentField[3]));
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
                                    if (moneyValidation(playersBudget.get(firstPlayerName), Integer.parseInt(currentField[2]))) {
                                    currentField[1] = "Sold";
                                    currentField[3] = firstPlayerName;
                                    firstPlayerRepository.add(currentField[0]);
                                    playersBudget.put(firstPlayerName, playersBudget.get(firstPlayerName) - Integer.parseInt(currentField[2]));
                                    printPlayerStats(firstPlayerName, playersBudget.get(firstPlayerName), firstPlayerRepository, firstPlayerSpecialCards);
                                    System.out.println(TEXT_YELLOW + companyFieldCard(currentField) + TEXT_RESET);
                                    } else {
                                        System.out.println(TEXT_RED + "Not enough money! You have $" + playersBudget.get(firstPlayerName) +
                                                " and the price is $" + Integer.parseInt(currentField[2]) + TEXT_RESET);
                                    }
                                }
                            } else {
                                String fieldOwner = currentField[3];
                                playersBudget.put(fieldOwner, playersBudget.get(fieldOwner) + 50);
                                playersBudget.put(firstPlayerName, playersBudget.get(firstPlayerName) - 50);
                            }

                            break;
                    }
                    if (playersBudget.get(firstPlayerName) <= 0) {
                        numberOfPlayers--;
                    }
                }
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
        }


    }
}
