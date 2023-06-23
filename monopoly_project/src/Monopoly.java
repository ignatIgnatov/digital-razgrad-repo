import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Monopoly {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        Map<String, Integer> playersFieldNumber = new HashMap<>();
        Map<String, Integer> playersBudget = new HashMap<>();

        Map<String, List<String>> playersProperties = new HashMap<>();
        Map<String, Map<String, Integer>> playersColorFields = new HashMap<>();
        Map<String, Map<String, Boolean>> haveColorGroup = new HashMap<>();
        Map<String, Map<String, Integer>> playersHouses = new HashMap<>();
        Map<String, Map<String, Integer>> playersHotels = new HashMap<>();

        Map<String, List<String>> playersRailroads = new HashMap<>();
        Map<String, List<String>> playersUtilities = new HashMap<>();

        Map<String, Boolean> playersInJail = new HashMap<>();
        Map<String, Integer> playersJailCounter = new HashMap<>();

        Map<String, List<String>> playersSpecialCards = new HashMap<>();

        List<String> players = new ArrayList<>();

        String[] board = arrayFromFile("dataBase/BoardFields.txt", 40);

        String[] communityChest = arrayFromFile("dataBase/CommunityChest.txt", 16);
        ArrayDeque<String> communityChestCards = convertArrayToDeque(communityChest);

        String[] chance = arrayFromFile("dataBase/Chance.txt", 16);
        ArrayDeque<String> chanceCards = convertArrayToDeque(chance);

        printMonopoly();

        int numberOfPlayers = enterNumberOfPlayers(scanner);

        fillPlayerNames(numberOfPlayers, players, scanner);

        for (int i = 0; i < players.size(); i++) {
            playersBudget.put(players.get(i), 1500);
            playersFieldNumber.put(players.get(i), 0);
            playersProperties.put(players.get(i), new ArrayList<>());
            playersRailroads.put(players.get(i), new ArrayList<>());
            playersUtilities.put(players.get(i), new ArrayList<>());
            playersSpecialCards.put(players.get(i), new ArrayList<>());
            playersInJail.put(players.get(i), false);
            playersHouses.put(players.get(i), new HashMap<>());
            playersHotels.put(players.get(i), new HashMap<>());
            playersColorFields.put(players.get(i), new HashMap<>());
            haveColorGroup.put(players.get(i), new HashMap<>());
            playersJailCounter.put(players.get(i), 0);
        }

        printStart(playersBudget, players);

        int index = 0;
        while (!players.isEmpty()) {
            String currentPlayer = players.get(index);
            int moveNumber = 0;

            if (playersInJail.get(currentPlayer)) {

                stayInJail(moveNumber, scanner, playersBudget, playersJailCounter, playersSpecialCards, playersInJail, players, index, currentPlayer);
                index = checkIndexForNextPlayer(scanner, players, index);

                System.out.println("-----------");
                System.out.println("Next player!");
                pressEnterToContinue(scanner);
                continue;
            }

            if (playersBudget.get(currentPlayer) < 0) {

                index = checkIndexForNotEnoughMoney(scanner, playersBudget, playersProperties, playersHouses,
                        playersHotels, players, board, index, currentPlayer, playersFieldNumber, haveColorGroup, playersColorFields, playersRailroads.get(currentPlayer), playersUtilities.get(currentPlayer));

                String winner = checkForWinner(playersBudget, playersProperties, playersSpecialCards, numberOfPlayers, players, currentPlayer, playersHouses, playersHotels, playersRailroads, playersUtilities);

                if (!winner.equals("")) {
                    return;
                }

                index = checkIndexForNextPlayer(scanner, players, index);
                System.out.println("Next player!");
                pressEnterToContinue(scanner);
                continue;
            }

            pressEnterToRollTheDice(currentPlayer, scanner);
            moveNumber = rollTheDice(currentPlayer, scanner);


            playersFieldNumber.put(currentPlayer, playersFieldNumber.get(currentPlayer) + moveNumber);

            scrollTheFields(scanner, playersFieldNumber, playersBudget, currentPlayer);

            String[] currentField = getCurrentField(playersFieldNumber, board, currentPlayer);

            printCurrentField(currentField);

            printPlayerStats(currentPlayer, playersBudget.get(currentPlayer), playersProperties.get(currentPlayer), playersSpecialCards.get(currentPlayer), playersHouses, playersHotels, playersRailroads.get(currentPlayer), playersUtilities.get(currentPlayer));

            String fieldCard = "";

            switch (playersFieldNumber.get(currentPlayer)) {

                case 0, 2, 4, 7, 10, 17, 20, 22, 30, 33, 36, 38:
                    fieldCard = createOtherFieldCard(currentField);

                    switchOtherFields(scanner, players, playersFieldNumber, playersBudget, playersProperties,
                            playersHouses, playersHotels, playersSpecialCards,
                            playersInJail, board, communityChestCards, chanceCards, currentPlayer,
                            currentField, fieldCard, playersRailroads, playersUtilities, haveColorGroup, playersColorFields, moveNumber, playersRailroads.get(currentPlayer), playersUtilities.get(currentPlayer));

                    break;
                case 1, 3, 6, 8, 9, 11, 13, 14, 16, 18, 19, 21, 23, 24, 26, 27, 29, 31, 32, 34, 37, 39:
                    Map<String, Boolean> temp = new HashMap<>();
                    temp.put(switchColor(playersFieldNumber, currentPlayer), false);
                    haveColorGroup.put(currentPlayer, temp);

                    Map<String, Integer> color = new HashMap<>();
                    color.put(switchColor(playersFieldNumber, currentPlayer), 0);
                    playersColorFields.put(currentPlayer, color);

                    buyColorField(currentField, scanner, currentPlayer,
                            playersBudget, playersProperties.get(currentPlayer),
                            playersSpecialCards.get(currentPlayer), playersFieldNumber,
                            board, fieldCard, playersHouses, playersHotels,
                            players, playersRailroads, playersUtilities, haveColorGroup, playersColorFields, playersRailroads.get(currentPlayer), playersUtilities.get(currentPlayer));

                    break;
                case 5, 15, 25, 35:

                    buyCompanyField(currentField, scanner, currentPlayer, playersBudget,
                            playersRailroads.get(currentPlayer), playersSpecialCards.get(currentPlayer),
                            playersFieldNumber, board, fieldCard, playersHotels, playersHouses, players, playersColorFields, haveColorGroup, moveNumber, playersRailroads.get(currentPlayer), playersUtilities.get(currentPlayer), playersRailroads, playersUtilities);

                    break;
                case 12, 28:

                    buyCompanyField(currentField, scanner, currentPlayer, playersBudget,
                            playersUtilities.get(currentPlayer), playersSpecialCards.get(currentPlayer),
                            playersFieldNumber, board, fieldCard, playersHotels, playersHouses, players, playersColorFields, haveColorGroup, moveNumber, playersRailroads.get(currentPlayer), playersUtilities.get(currentPlayer), playersRailroads, playersUtilities);

                    break;
            }

            printPlayerStats(currentPlayer, playersBudget.get(currentPlayer), playersProperties.get(currentPlayer), playersSpecialCards.get(currentPlayer), playersHouses, playersHotels, playersRailroads.get(currentPlayer), playersUtilities.get(currentPlayer));
            index = checkIndexForNextPlayer(scanner, players, index);
            System.out.println("Next player!");
            pressEnterToContinue(scanner);
        }
    }

    public static void printMonopoly() {
        String TEXT_BG_RED = "\u001B[41m";
        String TEXT_RED = "\u001B[31m";
        String TEXT_RESET = "\u001B[0m";

        System.out.println(TEXT_RED + "    *******************" + TEXT_RESET);
        System.out.println(TEXT_RED + "**** " + TEXT_RESET + TEXT_BG_RED + " M O N O P O L Y " + TEXT_RESET + TEXT_RED + " ****" + TEXT_RESET);
        System.out.println(TEXT_RED + "    *******************" + TEXT_RESET);
        System.out.println();
        System.out.println("Hello friend!");
        System.out.print("Enter a number of players (1-4): ");
    }

    public static int enterNumberOfPlayers(Scanner scanner) {
        int numberOfPlayers = Integer.parseInt(scanner.nextLine());
        while (numberOfPlayers < 1 || numberOfPlayers > 4) {
            System.out.print("Enter valid number of players (1-4): ");
            numberOfPlayers = Integer.parseInt(scanner.nextLine());
        }
        return numberOfPlayers;
    }

    public static void fillPlayerNames(int numberOfPlayers, List<String> players, Scanner scanner) {
        for (int i = 0; i < numberOfPlayers; i++) {
            System.out.printf("Enter Player %d name: ", i + 1);
            String currentName = scanner.nextLine();
            currentName = checkForEmptyName(currentName, scanner);

            if (i > 0) {
                currentName = checkForSameName(currentName, players, scanner);
            }

            players.add(currentName);
        }
    }

    public static void printStart(Map<String, Integer> playersBudget, List<String> players) {
        String TEXT_GREEN = "\u001B[32m";
        String TEXT_RESET = "\u001B[0m";
        System.out.println();
        System.out.println(TEXT_GREEN + "   *********");
        System.out.println("**** START ****");
        System.out.println("   *********" + TEXT_RESET);
        System.out.println();
        System.out.println("===========");
        System.out.println("Each player is given $" + playersBudget.get(players.get(0)));
        System.out.println("===========");
    }

    public static int rollTheDice(String currentPlayer, Scanner scanner) {
        Random firstDice = new Random();
        Random secondDice = new Random();

        int firstDiceResult = 0;
        int secondDiceResult = 0;

        firstDiceResult = firstDice.nextInt(1, 7);
        secondDiceResult = secondDice.nextInt(1, 7);
        printDiceResult(firstDiceResult, secondDiceResult);

        return firstDiceResult + secondDiceResult;
    }

    private static void printDiceResult(int firstDiceResult, int secondDiceResult) {
        System.out.println("First dice: " + firstDiceResult);
        System.out.println("Second dice: " + secondDiceResult);
        System.out.println("-----------");
    }

    private static void pressEnterToRollTheDice(String currentPlayer, Scanner scanner) {
        System.out.println();
        System.out.printf("%s, press 'Enter' to roll the dice: %n", currentPlayer);
        String playerRoll = scanner.nextLine();

        while (!playerRoll.isEmpty()) {
            System.out.printf("%s, press 'Enter' to roll the dice: %n", currentPlayer);
            playerRoll = scanner.nextLine();
        }
    }

    public static void scrollTheFields(Scanner scanner, Map<String, Integer> playersFieldNumber, Map<String, Integer> playersBudget, String currentPlayer) {
        if (playersFieldNumber.get(currentPlayer) >= 40) {

            playersFieldNumber.put(currentPlayer, playersFieldNumber.get(currentPlayer) - 40);
            playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) + 200);
            goTroughTheStart(currentPlayer, scanner);

        }
    }

    public static String[] getCurrentField(Map<String, Integer> playersFieldNumber, String[] board, String currentPlayer) {
        String[] currentField = board[playersFieldNumber.get(currentPlayer)].split(", ");
        return currentField;
    }

    public static void printCurrentField(String[] currentField) {
        String TEXT_BG_WHITE = "\u001B[47m";
        String TEXT_BLUE = "\u001B[34m";
        String TEXT_RESET = "\u001B[0m";
        System.out.println(TEXT_BG_WHITE + TEXT_BLUE + "Current field: " + currentField[0] + TEXT_RESET);
        System.out.println("-----------");
    }

    public static void printPlayerStats(String name, int money, List<String> purchases, List<String> specialCards, Map<String, Map<String, Integer>> playersHouses, Map<String, Map<String, Integer>> playersHotels, List<String> railroads, List<String> utilities) {
        String TEXT_GREEN = "\u001B[32m";
        String TEXT_RESET = "\u001B[0m";
        System.out.println(TEXT_GREEN + "----------------------");
        System.out.printf("%s's statistic: %n", name);
        System.out.println("Money: " + "$" + money);
        String playerFields = purchases.size() > 0 ? "Properties purchased: " + String.join(", ", purchases) : "Properties purchased: none";
        System.out.println(playerFields);
        System.out.println("    Houses: " + findSumOfAllHotelsOrHousesOfPlayer(name, playersHouses));
        System.out.println("    Hotels: " + findSumOfAllHotelsOrHousesOfPlayer(name, playersHotels));
        String playerRailroads = railroads.size() > 0 ? "RailRoads purchased: " + String.join(", ", railroads) : "Railroads purchased: none";
        System.out.println(playerRailroads);
        String playerUtilities = utilities.size() > 0 ? "Utilities purchased: " + String.join(", ", utilities) : "Utilities purchased: none";
        System.out.println(playerUtilities);
        String playerCards = specialCards.size() > 0 ? "Special cards: " + String.join(", ", specialCards) : "Special cards: none";
        System.out.println(playerCards);
        System.out.println("----------------------" + TEXT_RESET);
    }

    public static String checkForWinner(Map<String, Integer> playersBudget, Map<String, List<String>> playersPurchases, Map<String, List<String>> playersSpecialCards, int numberOfPlayers, List<String> players, String currentPlayer, Map<String, Map<String, Integer>> playersHouses, Map<String, Map<String, Integer>> playersHotels, Map<String, List<String>> playersRailroads, Map<String, List<String>> playersUtilities) {
        String TEXT_YELLOW = "\u001B[33m";
        String TEXT_RESET = "\u001B[0m";


        if (numberOfPlayers != players.size() && players.size() == 1) {
            String winner = players.get(0);

            printPlayerStats(winner, playersBudget.get(winner), playersPurchases.get(winner), playersSpecialCards.get(winner), playersHouses, playersHotels, playersRailroads.get(winner), playersUtilities.get(winner));

            System.out.println();
            System.out.println(TEXT_YELLOW + "***** " + winner + ", YOU WIN! *****" + TEXT_RESET);
            System.out.println();
            return winner;

        } else if (numberOfPlayers == 1 && players.isEmpty()) {

            System.out.println("-----------");
            System.out.println("GAME OVER!");
            System.out.println(currentPlayer + ", you lost the game!");

            return "none";
        }
        return "";
    }

    public static ArrayDeque<String> convertArrayToDeque(String[] communityChest) {
        ArrayDeque<String> result = new ArrayDeque<>();
        for (int j = 0; j < communityChest.length; j++) {
            result.offer(communityChest[j]);
        }
        return result;
    }

    public static void buyCompanyField(String[] currentField, Scanner scanner, String currentPlayer,
                                       Map<String, Integer> playersBudget, List<String> playersPurchases,
                                       List<String> playersSpecialCards, Map<String, Integer> playersFieldNumber,
                                       String[] board, String fieldCard, Map<String, Map<String, Integer>> playersHotels,
                                       Map<String, Map<String, Integer>> playersHouses, List<String> players,
                                       Map<String, Map<String, Integer>> playersColorSet, Map<String, Map<String, Boolean>> haveColorSet, int moveNumber, List<String> railroads, List<String> utilities, Map<String, List<String>> playersRailroads, Map<String, List<String>> playersUtilities) {

        String TEXT_YELLOW = "\u001B[33m";
        String TEXT_RESET = "\u001B[0m";

        printFieldCard(createCompanyFieldCard(currentField), TEXT_YELLOW, TEXT_RESET);

        if (currentField[1].equals("for rent") && !currentPlayer.equals(currentField[currentField.length - 1])) {

            if (doYouWontToBuy(currentField, scanner).equals("yes")) {

                if (moneyValidation(playersBudget.get(currentPlayer), Integer.parseInt(currentField[2]))) {

                    answerYesToBuy(currentField, currentPlayer, playersBudget, playersPurchases, playersSpecialCards, playersFieldNumber, board, TEXT_YELLOW, TEXT_RESET, playersHouses, playersHotels, playersColorSet, haveColorSet);
                    System.out.println(currentPlayer + ", you bought " + currentField[0]);
                    printFieldCard(createCompanyFieldCard(currentField), TEXT_YELLOW, TEXT_RESET);

                } else {
                    offerToSell(currentPlayer, playersBudget, playersPurchases, board, scanner, playersHotels, playersHouses, playersPurchases, players, playersFieldNumber, haveColorSet, playersColorSet, railroads, utilities);

                    if (moneyValidation(playersBudget.get(currentPlayer), Integer.parseInt(currentField[2]))) {

                        buyCompanyField(currentField, scanner, currentPlayer, playersBudget,
                                playersPurchases, playersSpecialCards,
                                playersFieldNumber, board, fieldCard, playersHotels, playersHouses, players, playersColorSet, haveColorSet, moveNumber, railroads, utilities, playersRailroads, playersUtilities);
                    } else {
                        System.out.println(currentPlayer + ", you don't have enough money to buy " + currentField[0] + "...");
                    }
                }
            }
        } else if (!currentPlayer.equals(currentField[currentField.length - 1])) {

            String fieldOwner = currentField[currentField.length - 1];
            int annuity = 0;
            int fieldNumber = playersFieldNumber.get(currentPlayer);

            switch (fieldNumber) {
                case 5, 15, 25, 35:
                    if (playersRailroads.get(fieldOwner).size() == 1) {
                        annuity = 25;
                    } else if (playersRailroads.get(fieldOwner).size() == 2) {
                        annuity = 50;
                    } else if (playersRailroads.get(fieldOwner).size() == 3) {
                        annuity = 100;
                    } else if (playersRailroads.get(fieldOwner).size() == 4) {
                        annuity = 200;
                    }
                    break;
                case 12, 28:
                    if (playersUtilities.get(fieldOwner).size() == 1) {
                        annuity = moveNumber * 4;
                    } else if (playersUtilities.get(fieldOwner).size() == 2) {
                        annuity = moveNumber * 10;
                    }
                    break;
            }

            payAnnuity(currentPlayer, playersBudget, fieldOwner, annuity);
        } else {
            System.out.printf("%s, '%s' field is yours.%n", currentPlayer, currentField[0]);
            pressEnterToContinue(scanner);
        }
    }

    public static void buyColorField(String[] currentField, Scanner scanner, String currentPlayer,
                                     Map<String, Integer> playersBudget, List<String> playersPurchases,
                                     List<String> playersSpecialCards, Map<String, Integer> playersFieldNumber,
                                     String[] board, String fieldCard, Map<String, Map<String, Integer>> playersHouses, Map<String, Map<String, Integer>> playersHotels,
                                     List<String> players, Map<String, List<String>> playersRailroads, Map<String, List<String>> playersUtilities,
                                     Map<String, Map<String, Boolean>> haveColorGroup, Map<String, Map<String, Integer>> playersColorSet, List<String> railroads, List<String> utilities) {

        String TEXT_CYAN = "\u001B[36m";
        String TEXT_RESET = "\u001B[0m";

        Map<String, Integer> houses = playersHouses.get(currentPlayer);
        Map<String, Integer> hotels = playersHotels.get(currentPlayer);

        printFieldCard(createColorFieldCard(currentField), TEXT_CYAN, TEXT_RESET);

        if (currentField[1].equals("for rent")) {

            if (doYouWontToBuy(currentField, scanner).equals("yes")) {

                if (moneyValidation(playersBudget.get(currentPlayer), Integer.parseInt(currentField[2]))) {

                    answerYesToBuy(currentField, currentPlayer, playersBudget, playersPurchases, playersSpecialCards, playersFieldNumber, board, TEXT_CYAN, TEXT_RESET, playersHouses, playersHotels, playersColorSet, haveColorGroup);

                    houses.put(currentField[0], 0);
                    hotels.put(currentField[0], 0);
                    playersHouses.put(currentPlayer, houses);
                    playersHotels.put(currentPlayer, hotels);

                    System.out.println(currentPlayer + ", you bought " + currentField[0]);
                    printFieldCard(createColorFieldCard(currentField), TEXT_CYAN, TEXT_RESET);

                } else {
                    offerToSell(currentPlayer, playersBudget, playersPurchases, board, scanner, playersHotels, playersHouses, playersPurchases, players, playersFieldNumber, haveColorGroup, playersColorSet, railroads, utilities);

                    if (moneyValidation(playersBudget.get(currentPlayer), Integer.parseInt(currentField[2]))) {
                        buyColorField(currentField, scanner, currentPlayer,
                                playersBudget, playersPurchases,
                                playersSpecialCards, playersFieldNumber,
                                board, fieldCard, playersHouses, playersHotels, players, playersRailroads, playersUtilities, haveColorGroup, playersColorSet, railroads, utilities);
                    } else {
                        System.out.println(currentPlayer + ", you don't have enough money to buy " + currentField[0] + "...");
                    }
                }
            }

        } else {

            if (!currentPlayer.equals(currentField[currentField.length - 1])) {

                String fieldOwner = currentField[currentField.length - 1];

                int housesCount = playersHouses.get(fieldOwner).get(currentField[0]);
                int hotelsCount = playersHotels.get(fieldOwner).get(currentField[0]);

                int annuity = getAnnuity(currentField, housesCount, hotelsCount, haveColorGroup, currentPlayer, playersFieldNumber);

                payAnnuity(currentPlayer, playersBudget, fieldOwner, annuity);

            } else {
                String color = switchColor(playersFieldNumber, currentPlayer);
                if (haveColorGroup.get(currentPlayer).get(color)) {
                    buyHouseOrHotel(playersHouses, playersHotels, currentPlayer,
                            currentField, scanner, playersBudget, playersPurchases, playersSpecialCards, board, playersRailroads, playersUtilities);
                } else {
                    System.out.printf("%s, '%s' field is yours.%n", currentPlayer, currentField[0]);
                    pressEnterToContinue(scanner);
                }
            }
        }
    }

    public static void sellHouse(Scanner scanner, String currentPlayer, Map<String, Integer> playersBudget, String[] board, Map<String, Map<String, Integer>> playersHouses, Map<String, Map<String, Integer>> playersHotels, int playerHouses) {
        System.out.println(currentPlayer + ", you have " + playerHouses + " houses. You have to sell one of them...");
        System.out.println("These are your houses: ");

        List<String> fields = createListOfFields(currentPlayer, playersHouses);

        int numberOfField = enterNumberOfField(scanner, fields);

        sellHouseOperation(currentPlayer, playersBudget, board, playersHouses, fields, numberOfField);

        System.out.println(currentPlayer + ", you sold a house in a field " + fields.get(numberOfField - 1) + ".");

        pressEnterToContinue(scanner);
    }

    public static void sellHotel(Scanner scanner, String currentPlayer, Map<String, Integer> playersBudget, String[] board, Map<String, Map<String, Integer>> playersHotels, int playerHotels) {
        System.out.println(currentPlayer + ", you have " + playerHotels + " hotels. You have to sell one of them");
        System.out.println("These are your hotels: ");

        List<String> fields = createListOfFields(currentPlayer, playersHotels);

        int numberOfField = enterNumberOfField(scanner, fields);

        sellHotelOperation(currentPlayer, playersBudget, board, playersHotels, fields, numberOfField);

        System.out.println(currentPlayer + ", you sold a hotel in a field " + fields.get(numberOfField - 1) + ".");

        pressEnterToContinue(scanner);
    }

    public static void sellHotelOperation(String currentPlayer, Map<String, Integer> playersBudget, String[] board, Map<String, Map<String, Integer>> playersHotels, List<String> fields, int numberOfField) {
        String nameOfField = fields.get(numberOfField - 1);

        Map<String, Integer> temp = playersHotels.get(currentPlayer);
        temp.put(nameOfField, temp.get(nameOfField) - 1);
        playersHotels.put(currentPlayer, temp);

        int price = Integer.parseInt(findFieldByName(nameOfField, board)[11]);
        playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) + price);
    }

    public static void sellHouseOperation(String currentPlayer, Map<String, Integer> playersBudget, String[] board, Map<String, Map<String, Integer>> playersHouses, List<String> fields, int numberOfField) {
        String nameOfField = fields.get(numberOfField - 1);

        Map<String, Integer> temp = playersHouses.get(currentPlayer);
        temp.put(nameOfField, temp.get(nameOfField) - 1);
        playersHouses.put(currentPlayer, temp);

        int price = Integer.parseInt(findFieldByName(nameOfField, board)[10]);
        playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) + price);
    }

    public static List<String> createListOfFields(String currentPlayer, Map<String, Map<String, Integer>> playersHotels) {
        int index = 1;
        List<String> fields = new ArrayList<>();
        for (Map.Entry<String, Integer> stringIntegerEntry : playersHotels.get(currentPlayer).entrySet()) {
            if (stringIntegerEntry.getValue() > 0) {
                fields.add(stringIntegerEntry.getKey());
                System.out.println(index + ". " + stringIntegerEntry.getKey());
                index++;
            }
        }
        return fields;
    }

    public static int enterNumberOfField(Scanner scanner, List<String> fields) {
        System.out.println("Enter the number of the field whose house or hotel you want to sell: ");
        int numberOfTheField = scanner.nextInt();

        while (numberOfTheField <= 0 || numberOfTheField > fields.size()) {
            System.out.println("Enter the number of the field whose house or hotel you want to sell: ");
            numberOfTheField = scanner.nextInt();
        }
        return numberOfTheField;
    }

    public static int findSumOfAllHotelsOrHousesOfPlayer(String currentPlayer, Map<String, Map<String, Integer>> playersHotels) {
        int sumOfHotels = 0;
        for (Integer value : playersHotels.get(currentPlayer).values()) {
            sumOfHotels += value;
        }
        return sumOfHotels;
    }

    public static int getAnnuity(String[] currentField, int housesCount, int hotelsCount, Map<String, Map<String, Boolean>> haveColorGroup, String currentPlayer, Map<String, Integer> playersFieldNumber) {
        int annuity = 0;
        if (hotelsCount == 1) {
            annuity = Integer.parseInt(currentField[9]);
        } else if (housesCount > 0) {
            switch (housesCount) {
                case 1:
                    annuity = Integer.parseInt(currentField[5]);
                    break;
                case 2:
                    annuity = Integer.parseInt(currentField[6]);
                    break;
                case 3:
                    annuity = Integer.parseInt(currentField[7]);
                    break;
                case 4:
                    annuity = Integer.parseInt(currentField[8]);
            }
        } else {
            String color = switchColor(playersFieldNumber, currentPlayer);
            String fieldOwner = currentField[currentField.length - 1];
            if (haveColorGroup.get(fieldOwner).get(color)) {
                annuity = Integer.parseInt(currentField[4]);
            } else {
                annuity = Integer.parseInt(currentField[3]);
            }
        }
        return annuity;
    }

    public static void payAnnuity(String currentPlayer, Map<String, Integer> playersBudget, String fieldOwner, int annuity) {
        playersBudget.put(fieldOwner, playersBudget.get(fieldOwner) + annuity);
        playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) - annuity);
        System.out.printf("%s pays %s an annuity of $%d%n", currentPlayer, fieldOwner, annuity);
    }

    public static void printFieldCard(String fieldCard, String TEXT, String TEXT_RESET) {
        System.out.println(TEXT + fieldCard + TEXT_RESET);
    }

    public static void answerYesToBuy(String[] currentField, String currentPlayer, Map<String, Integer> playersBudget, List<String> playersPurchases, List<String> playersSpecialCards, Map<String, Integer> playersFieldNumber, String[] board, String TEXT_YELLOW, String TEXT_RESET, Map<String, Map<String, Integer>> playersHouses, Map<String, Map<String, Integer>> playersHotels, Map<String, Map<String, Integer>> playersColorSet, Map<String, Map<String, Boolean>> haveColorSet) {
        currentField[1] = "Sold";
        currentField[currentField.length - 1] = currentPlayer;
        playersPurchases.add(currentField[0]);
        playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) - Integer.parseInt(currentField[2]));

        board[playersFieldNumber.get(currentPlayer)] = String.join(", ", currentField);

        switch (playersFieldNumber.get(currentPlayer)) {
            case 1, 3, 6, 8, 9, 11, 13, 14, 16, 18, 19, 21, 23, 24, 26, 27, 29, 31, 32, 34, 37, 39:
                String colorType = switchColor(playersFieldNumber, currentPlayer);
                setColor(playersColorSet, currentPlayer, colorType);
                checkForColorGroup(playersColorSet, currentPlayer, colorType, haveColorSet, playersFieldNumber);
        }
    }

    public static String doYouWontToBuy(String[] currentField, Scanner scanner) {
        System.out.println("Do you want to buy " + currentField[0] + "?");
        return enterYesOrNo(scanner);
    }

    public static void buyHouseOrHotel(Map<String, Map<String, Integer>> playersHouses,
                                       Map<String, Map<String, Integer>> playersHotels,
                                       String currentPlayer, String[] currentField, Scanner scanner,
                                       Map<String, Integer> playersBudget, List<String> playersPurchases,
                                       List<String> playersSpecialCards, String[] board, Map<String, List<String>> playersRailroads, Map<String, List<String>> playersUtilities) {

        Map<String, Integer> houses = playersHouses.get(currentPlayer);
        Map<String, Integer> hotels = playersHotels.get(currentPlayer);

        if (houses.get(currentField[0]) < 4) {

            System.out.printf("%s field is yours.%n", currentField[0]);
            System.out.printf("You have %d houses and %d hotels on this field.%n",
                    playersHouses.get(currentPlayer).get(currentField[0]),
                    playersHotels.get(currentPlayer).get(currentField[0]));

            System.out.println("Do you want to buy a house?");

            if (enterYesOrNo(scanner).equals("yes")) {

                int housePrice = Integer.parseInt(currentField[10]);

                houses.put(currentField[0], houses.get(currentField[0]) + 1);
                playersHouses.put(currentPlayer, houses);
                playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) - housePrice);

                System.out.println(currentPlayer + ", you bought a house.");
                printPlayerStats(currentPlayer, playersBudget.get(currentPlayer), playersPurchases, playersSpecialCards, playersHouses, playersHotels, playersRailroads.get(currentPlayer), playersUtilities.get(currentPlayer));
            }

        } else {

            if (hotels.get(currentField[0]) == 0) {

                System.out.println(currentPlayer + ", this field has 4 houses. Do you want to buy a hotel?");
                String hotelBuyAnswer = enterYesOrNo(scanner);

                if (hotelBuyAnswer.equals("yes")) {

                    int hotelPrice = Integer.parseInt(currentField[11]);
                    hotels.put(currentField[0], hotels.get(currentField[0]) + 1);
                    playersHotels.put(currentPlayer, hotels);
                    houses = playersHouses.get(currentPlayer);

                    houses.put(currentField[0], 0);
                    playersHouses.put(currentPlayer, houses);
                    playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) - hotelPrice);

                    System.out.println(currentPlayer + ", you bought a hotel");
                    printPlayerStats(currentPlayer, playersBudget.get(currentPlayer), playersPurchases, playersSpecialCards, playersHouses, playersHotels, playersRailroads.get(currentPlayer), playersUtilities.get(currentPlayer));
                }
            }
        }
    }

    public static void switchChance(String chanceRow, Map<String, Integer> playersFieldNumber,
                                    String currentPlayer, Map<String, Integer> playersBudget,
                                    List<String> playersSpecialCards, Map<String, Boolean> playersInJail,
                                    List<String> players, List<String> playersPurchases, String[] board, Scanner scanner, String[] currentField, String fieldCard, Map<String, Map<String, Integer>> playersHouses, Map<String, Map<String, Integer>> playersHotels, Map<String, List<String>> playersRailroads, Map<String, List<String>> playersUtilities, Map<String, Map<String, Boolean>> haveColorSet, Map<String, Map<String, Integer>> playersColorSet, int moveNumber, List<String> railroads, List<String> utilities) {
        switch (chanceRow) {
            case "Advance to Boardwalk":

                playersFieldNumber.put(currentPlayer, 39);

                currentField = board[playersFieldNumber.get(currentPlayer)].split(", ");

                System.out.println(currentPlayer + ", you went to Boardwalk.");

                buyColorField(currentField, scanner, currentPlayer, playersBudget,
                        playersPurchases, playersSpecialCards, playersFieldNumber,
                        board, fieldCard, playersHouses, playersHotels, players, playersRailroads, playersUtilities, haveColorSet, playersColorSet, railroads, utilities);
                break;
            case "Advance to START (Collect $200)":

                playersFieldNumber.put(currentPlayer, 0);
                playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) + 200);

                break;
            case "Advance to Illinois Avenue. If you pass START, collect $200":

                int currentPosition = playersFieldNumber.get(currentPlayer);

                if (currentPosition == 36) {
                    playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) + 200);
                    System.out.println("Collect $200");
                }

                playersFieldNumber.put(currentPlayer, 24);
                System.out.println(currentPosition + ", you're going to Illinois Avenue");

                currentField = board[playersFieldNumber.get(currentPlayer)].split(", ");

                buyColorField(currentField, scanner, currentPlayer, playersBudget,
                        playersPurchases, playersSpecialCards, playersFieldNumber,
                        board, fieldCard, playersHouses, playersHotels, players, playersRailroads, playersUtilities, haveColorSet, playersColorSet, railroads, utilities);

                break;
            case "Advance to St. Charles Place. If you pass START, collect $200":

                currentPosition = playersFieldNumber.get(currentPlayer);

                if (currentPosition == 36 || currentPosition == 22) {
                    playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) + 200);
                    System.out.println(currentPlayer + ", you passed the Start and collect $200");
                }

                playersFieldNumber.put(currentPlayer, 11);
                System.out.println(currentPlayer + ", you're going to St. Charles Place");

                currentField = board[playersFieldNumber.get(currentPlayer)].split(", ");

                buyColorField(currentField, scanner, currentPlayer, playersBudget,
                        playersPurchases, playersSpecialCards, playersFieldNumber,
                        board, fieldCard, playersHouses, playersHotels, players, playersRailroads, playersUtilities, haveColorSet, playersColorSet, railroads, utilities);

                break;
            case "Advance to the nearest Railroad. If unowned, you may buy it from the Bank. If owned, " +
                    "pay wonder twice the rental to which they are otherwise entitled":

                currentPosition = playersFieldNumber.get(currentPlayer);

                if (currentPosition == 7) {
                    playersFieldNumber.put(currentPlayer, 25);
                    System.out.println(currentPlayer + ", you went to the nearest Railroad - B. & O. Railroad");
                } else if (currentPosition == 22) {
                    playersFieldNumber.put(currentPlayer, 25);
                    System.out.println(currentPlayer + ", you went to the nearest Railroad - B. & O. Railroad");
                } else {
                    playersFieldNumber.put(currentPlayer, 5);
                    System.out.println(currentPlayer + ", you went to the nearest Railroad - Reading RailRoad");
                    System.out.println("You passed a Start but you don't get $200");
                    System.out.println("-----------");
                }

                currentField = board[playersFieldNumber.get(currentPlayer)].split(", ");

                buyCompanyField(currentField, scanner, currentPlayer, playersBudget, playersPurchases,
                        playersSpecialCards, playersFieldNumber, board, fieldCard, playersHotels, playersHouses, players, playersColorSet, haveColorSet, moveNumber, railroads, utilities, playersRailroads, playersUtilities);

                break;
            case "Advance token to nearest Utility. If unowned, you may buy it from the Bank.":

                currentPosition = playersFieldNumber.get(currentPlayer);

                if (currentPosition == 22) {
                    playersFieldNumber.put(currentPlayer, 28);
                    System.out.println(currentPlayer + ", you went to Water works");
                } else {
                    playersFieldNumber.put(currentPlayer, 12);
                    System.out.println(currentPlayer + ", you went to Electric Company");
                }

                currentField = board[playersFieldNumber.get(currentPlayer)].split(", ");

                buyCompanyField(currentField, scanner, currentPlayer, playersBudget, playersPurchases,
                        playersSpecialCards, playersFieldNumber, board, fieldCard, playersHotels, playersHouses, players, playersColorSet, haveColorSet, moveNumber, railroads, utilities, playersRailroads, playersUtilities);

                break;
            case "Bank pays you dividend of $50":

                playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) + 50);
                System.out.println(currentPlayer + ", you received $50");

                break;
            case "Get Out of Jail Free":

                playersSpecialCards.add(chanceRow);
                System.out.println(currentPlayer + ", you added the card 'Get Out of Jail Free' to your Special Card Collection");

                break;
            case "Go Back 3 Spaces":

                playersFieldNumber.put(currentPlayer, playersFieldNumber.get(currentPlayer) - 3);
                System.out.println(currentPlayer + ", you moved back three spaces");

                break;
            case "Go to Jail. Go directly to Jail, do not pass START, do not collect $200":

                playersFieldNumber.put(currentPlayer, 10);
                playersInJail.put(currentPlayer, true);
                System.out.println(currentPlayer + ", you are in Jail");

                break;
            case "Make general repairs on all your property. For each house pay $25. For each hotel pay $100":

                repairHousesAndHotels(currentPlayer, playersBudget, playersHouses, playersHotels, 25, 100);

                break;
            case "Speeding fine $15":

                playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) - 15);
                System.out.println(currentPlayer + ", you paid $15");

                break;
            case "Take a trip to Reading Railroad. If you pass START, collect $200":

                playersFieldNumber.put(currentPlayer, 5);
                playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) + 200);

                System.out.println(currentPlayer + ", you move to Reading Railroad");
                System.out.println("You passed a Start and get $200");

                currentField = board[playersFieldNumber.get(currentPlayer)].split(", ");

                buyCompanyField(currentField, scanner, currentPlayer, playersBudget, playersPurchases,
                        playersSpecialCards, playersFieldNumber, board, fieldCard, playersHotels, playersHouses, players, playersColorSet, haveColorSet, moveNumber, railroads, utilities, playersRailroads, playersUtilities);

                break;
            case "You have been elected Chairman of the Board. Pay each player $50":

                int sum = 0;
                for (int j = 0; j < players.size(); j++) {
                    String current = players.get(j);
                    playersBudget.put(current, playersBudget.get(current) + 50);
                    sum += 50;
                }
                playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) - sum);
                System.out.println(currentPlayer + ", you paid $" + sum);
                break;
            case "Your building loan matures. Collect $150":
                playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) + 150);
                System.out.println(currentPlayer + ", you collect $150");
                break;
        }
    }

    public static void switchCommunityChest(String communityChestRow,
                                            String currentPlayer,
                                            Map<String, Integer> playersBudget,
                                            Map<String, Integer> playersFieldNumber,
                                            List<String> playersSpecialCards,
                                            Map<String, Boolean> playersInJail,
                                            List<String> players, Map<String, Map<String, Integer>> playersHouses, Map<String, Map<String, Integer>> playersHotels) {
        switch (communityChestRow) {
            case "Advance to START(Collect $200)":
                playersFieldNumber.put(currentPlayer, 0);
                playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) + 200);
                break;
            case "Bank error in your favor. Collect $200":
                playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) + 200);
                System.out.println(currentPlayer + ", you received $200");
                break;
            case "Doctor’s fee. Pay $50",
                    "Pay school fees of $50":
                playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) - 50);
                System.out.println(currentPlayer + ", you pay $50");
                break;
            case "From sale of stock you get $50":
                playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) + 50);
                System.out.println(currentPlayer + ", you received $50");
                break;
            case "Get Out of Jail Free":
                playersSpecialCards.add(communityChestRow);
                System.out.println(currentPlayer + ", you added the card 'Get Out of Jail Free' to your Special Card Collection");
                break;
            case "Go to Jail. Go directly to jail, do not pass Go, do not collect $200":
                playersFieldNumber.put(currentPlayer, 10);
                playersInJail.put(currentPlayer, true);
                System.out.println(currentPlayer + ", you are in Jail");
                break;
            case "Holiday fund matures. Receive $100",
                    "Life insurance matures. Collect $100",
                    "You inherit $100":
                playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) + 100);
                System.out.println(currentPlayer + ", you received $100");
                break;
            case "Income tax refund. Collect $20":
                playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) + 20);
                System.out.println(currentPlayer + ", you received $20");
                break;
            case "It is your birthday. Collect $10 from every player":
                int sum = 0;
                for (int k = 0; k < players.size(); k++) {
                    String current = players.get(k);
                    if (!current.equals(currentPlayer)) {
                        playersBudget.put(current, playersBudget.get(current) - 10);
                        sum += 10;
                    }
                }
                playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) + sum);
                System.out.printf("%s, you received $%d%n", currentPlayer, sum);
                break;
            case "Pay hospital fees of $100":
                playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) - 100);
                System.out.println(currentPlayer + ", you pay $100");
                break;
            case "Receive $25 consultancy fee":
                playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) + 25);
                System.out.println(currentPlayer + ", you received $25");
                break;
            case "You are assessed for street repair. $40 per house. $115 per hotel":
                repairHousesAndHotels(currentPlayer, playersBudget, playersHouses, playersHotels, 40, 115);
                break;
            case "You have won second prize in a beauty contest. Collect $10":
                playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) + 10);
                System.out.println(currentPlayer + ", you received $10");
                break;
        }
    }

    public static void repairHousesAndHotels(String currentPlayer, Map<String, Integer> playersBudget, Map<String, Map<String, Integer>> playersHouses, Map<String, Map<String, Integer>> playersHotels, int housePrice, int hotelPrice) {

        int hotelsCount = findSumOfAllHotelsOrHousesOfPlayer(currentPlayer, playersHotels);
        int housesCount = findSumOfAllHotelsOrHousesOfPlayer(currentPlayer, playersHouses);

        if (hotelsCount > 0 || housesCount > 0) {
            int sum = housesCount * housePrice + hotelsCount * hotelPrice;
            playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) - sum);

            System.out.printf("%s, you repaired %d houses and %d hotels%n", currentPlayer, housesCount, hotelsCount);
            System.out.println("You paid $" + sum + ".");

        }
    }

    public static void switchOtherFields(Scanner scanner, List<String> players, Map<String, Integer> playersFieldNumber,
                                         Map<String, Integer> playersBudget, Map<String, List<String>> playersPurchases,
                                         Map<String, Map<String, Integer>> playersHouses, Map<String, Map<String, Integer>> playersHotels,
                                         Map<String, List<String>> playersSpecialCards, Map<String, Boolean> playersInJail, String[] board, ArrayDeque<String> communityChestCards, ArrayDeque<String> chanceCards, String currentPlayer, String[] currentField, String fieldCard, Map<String, List<String>> playersRailroads, Map<String, List<String>> playersUtilities, Map<String, Map<String, Boolean>> haveColorSet, Map<String, Map<String, Integer>> playersColorSet, int moveNumber, List<String> railroads, List<String> utilities) {

        switch (currentField[0]) {
            case "START":
                System.out.println("You become $200");
                break;
            case "Community Chest":
                String communityChestRow = communityChestCards.poll();
                System.out.println("-----------");
                System.out.println("Community chest card content: " + communityChestRow);
                pressEnterToContinue(scanner);

                switchCommunityChest(communityChestRow, currentPlayer, playersBudget, playersFieldNumber,
                        playersSpecialCards.get(currentPlayer), playersInJail, players, playersHouses, playersHotels);

                communityChestCards.offer(communityChestRow);
                break;
            case "Income Tax":

                playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) - 200);
                System.out.println(currentPlayer + ", you paid Income Tax - $200");

                break;
            case "Chance":

                String chanceRow = chanceCards.poll();
                System.out.println("-----------");
                System.out.println("Chance card content: " + chanceRow);
                pressEnterToContinue(scanner);

                switchChance(chanceRow, playersFieldNumber,
                        currentPlayer, playersBudget,
                        playersSpecialCards.get(currentPlayer), playersInJail,
                        players, playersPurchases.get(currentPlayer), board, scanner, currentField, fieldCard, playersHouses, playersHotels, playersRailroads, playersUtilities, haveColorSet, playersColorSet, moveNumber, railroads, utilities);

                chanceCards.offer(chanceRow);
                break;

            case "Jail - only visit":

                System.out.println(currentPlayer + ", you just visited the Jail");
                System.out.println("Next player!");

                break;
            case "Free Parking":

                System.out.println(currentPlayer + ", do nothing");
                System.out.println("Next player!");

                break;
            case "Go To Jail":

                playersFieldNumber.put(currentPlayer, 10);
                playersInJail.put(currentPlayer, true);
                System.out.println(currentPlayer + ", you are in Jail");

                break;
            case "Luxury tax":

                playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) - 100);
                System.out.println(currentPlayer + ", you paid Luxury Tax - $100");

                break;
        }
    }

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

    public static String createColorFieldCard(String[] currentField) {
        StringBuilder sb = new StringBuilder();

        sb.append("----------------------").append(System.lineSeparator());
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
        sb.append("----------------------");

        return sb.toString();
    }

    public static String createCompanyFieldCard(String[] currentField) {
        StringBuilder sb = new StringBuilder();

        sb.append("----------------------").append(System.lineSeparator());
        sb.append(currentField[0]).append(System.lineSeparator());
        sb.append("Status: ").append(currentField[1]).append(System.lineSeparator());
        sb.append("----------").append(System.lineSeparator());
        sb.append("Price: ").append("$").append(currentField[2]).append(System.lineSeparator());
        sb.append("----------").append(System.lineSeparator());
        sb.append("Owner: ").append(currentField[3]).append(System.lineSeparator());
        sb.append("----------------------");

        return sb.toString();
    }

    public static String createOtherFieldCard(String[] currentField) {

        StringBuilder sb = new StringBuilder();

        sb.append("----------").append(System.lineSeparator());
        sb.append(currentField[0]).append(System.lineSeparator());
        sb.append("----------");

        return sb.toString();
    }

    public static void offerToSell(String currentPlayer, Map<String, Integer> playersBudget, List<String> properties, String[] board, Scanner scanner, Map<String, Map<String, Integer>> playersHotels, Map<String, Map<String, Integer>> playersHouses, List<String> playersPurchases, List<String> players, Map<String, Integer> playersFieldNumber, Map<String, Map<String, Boolean>> haveColorSet, Map<String, Map<String, Integer>> playersColorSet, List<String> railroads, List<String> utilities) {

        System.out.println(currentPlayer + ", you don't have enough money!");
        System.out.println("You need to sell one of your properties, railroads or utilities.");

        pressEnterToContinue(scanner);

        int playerHotels = findSumOfAllHotelsOrHousesOfPlayer(currentPlayer, playersHotels);
        int playerHouses = findSumOfAllHotelsOrHousesOfPlayer(currentPlayer, playersHouses);

        sellEntity(currentPlayer, playersBudget, properties, board, scanner, playersHotels, playersHouses, playerHotels, playerHouses, playersPurchases, players, playersFieldNumber, haveColorSet, playersColorSet, railroads, utilities);

    }

    public static void sellEntity(String currentPlayer, Map<String, Integer> playersBudget, List<String> properties, String[] board, Scanner scanner, Map<String, Map<String, Integer>> playersHotels, Map<String, Map<String, Integer>> playersHouses, int playerHotels, int playerHouses, List<String> playersPurchases, List<String> players, Map<String, Integer> playersFieldNumber, Map<String, Map<String, Boolean>> haveColorSet, Map<String, Map<String, Integer>> playersColorSet, List<String> railroads, List<String> utilities) {
        if (playerHotels > 0) {

            sellHotel(scanner, currentPlayer, playersBudget, board, playersHotels, playerHotels);

        } else if (playerHouses > 0) {

            sellHouse(scanner, currentPlayer, playersBudget, board, playersHouses, playersHotels, playerHouses);

        } else if (properties.isEmpty() && railroads.isEmpty() && utilities.isEmpty()) {

            System.out.println(currentPlayer + ", you have nothing for sale!");

        } else {

            sellProperties(currentPlayer, playersBudget, properties, board, scanner, playersFieldNumber, haveColorSet, playersColorSet, railroads, utilities);
        }
    }

    public static void sellProperties(String currentPlayer, Map<String, Integer> playersBudget, List<String> properties, String[] board, Scanner scanner, Map<String, Integer> playersFieldNumber, Map<String, Map<String, Boolean>> haveColorSet, Map<String, Map<String, Integer>> playersColorSet, List<String> railroads, List<String> utilities) {

        System.out.printf("%s, you have %d properties, %d railroads and %d utilities.%n", currentPlayer, properties.size(), railroads.size(), utilities.size());
        System.out.println("To sell a property, press 1");
        System.out.println("To sell a railroad, press 2");
        System.out.println("To sell an utility, press 3");
        System.out.println("If you don't want to sell anything, press 0.");
        int choice = scanner.nextInt();
        while (choice < 0 || choice > 3) {
            System.out.println("Enter correct number: ");
            choice = scanner.nextInt();
        }

        switch (choice) {
            case 0:
                return;
            case 1:
                if (properties.isEmpty()) {
                    System.out.println("You don't have properties. Make another choice...");
                    sellProperties(currentPlayer, playersBudget, properties, board, scanner, playersFieldNumber, haveColorSet, playersColorSet, railroads, utilities);
                } else {
                    sellColorProperty(currentPlayer, playersBudget, properties, board, scanner, playersFieldNumber, haveColorSet, playersColorSet, railroads, utilities);
                }
                break;
            case 2:
                if (railroads.isEmpty()) {
                    System.out.println("You don't have railroads. Make another choice...");
                    sellProperties(currentPlayer, playersBudget, properties, board, scanner, playersFieldNumber, haveColorSet, playersColorSet, railroads, utilities);
                } else {
                    sellRailRoad(currentPlayer, playersBudget, railroads, board, scanner, playersFieldNumber, haveColorSet, playersColorSet, railroads, utilities);
                }
                break;
            case 3:
                if (utilities.isEmpty()) {
                    System.out.println("You don't have utilities. Make another choice...");
                    sellProperties(currentPlayer, playersBudget, properties, board, scanner, playersFieldNumber, haveColorSet, playersColorSet, railroads, utilities);
                } else {
                    sellUtility(currentPlayer, playersBudget, railroads, board, scanner, playersFieldNumber, haveColorSet, playersColorSet, railroads, utilities);
                }
                break;
        }
    }

    public static void sellColorProperty(String currentPlayer, Map<String, Integer> playersBudget, List<String> properties, String[] board, Scanner scanner, Map<String, Integer> playersFieldNumber, Map<String, Map<String, Boolean>> haveColorSet, Map<String, Map<String, Integer>> playersColorSet, List<String> railroads, List<String> utilities) {
        System.out.println(currentPlayer + ", you have the following properties:");

        int price = 0;
        for (int j = 0; j < properties.size(); j++) {
            String current = properties.get(j);
            int index = findIndexOfProperty(current, board);
            price = (int) (Integer.parseInt(board[index].split(", ")[2]) * 0.5);
            System.out.println(j + 1 + ". " + current + " -> $" + price);
        }
        System.out.print("Enter the number of the property you want to sell. ");
        System.out.println("If you don't want to sell anymore, press 0.");
        int numberOfProperty = scanner.nextInt();
        while (numberOfProperty < 0 || numberOfProperty > properties.size()) {
            System.out.println("Enter correct number of property:");
            numberOfProperty = scanner.nextInt();
        }

        if (numberOfProperty == 0) {
            System.out.println("You didn't sell anything...");
            return;
        }

        String propertyName = properties.get(numberOfProperty - 1);
        String[] currentProperty = findFieldByName(propertyName, board);
        int indexOfProperty = findIndexOfProperty(propertyName, board);

        properties.remove(numberOfProperty - 1);
        playersBudget.put(currentPlayer, (int) (playersBudget.get(currentPlayer) + Integer.parseInt(currentProperty[2]) * 0.5));
        currentProperty[1] = "for rent";
        currentProperty[currentProperty.length - 1] = "none";
        board[indexOfProperty] = String.join(", ", currentProperty);

        Map<String, Boolean> temp = new HashMap<>();
        String color = switchColor(playersFieldNumber, currentPlayer);
        temp.put(color, false);
        haveColorSet.put(currentPlayer, temp);
        Map<String, Integer> temp2 = playersColorSet.get(currentPlayer);
        temp2.put(color, temp2.get(color) - 1);
        playersColorSet.put(currentPlayer, temp2);

        System.out.println("You sold a property '" + propertyName + "' for $" + (int) (Integer.parseInt(currentProperty[2]) * 0.5) + ".");
        scanner.nextLine();
    }

    public static void sellRailRoad(String currentPlayer, Map<String, Integer> playersBudget, List<String> properties, String[] board, Scanner scanner, Map<String, Integer> playersFieldNumber, Map<String, Map<String, Boolean>> haveColorSet, Map<String, Map<String, Integer>> playersColorSet, List<String> railroads, List<String> utilities) {
        System.out.println(currentPlayer + ", you have the following railroads:");
        int price = 0;
        for (int j = 0; j < railroads.size(); j++) {
            String current = railroads.get(j);
            int index = findIndexOfProperty(current, board);
            price = (int) (Integer.parseInt(board[index].split(", ")[2]) * 0.5);
            System.out.println(j + 1 + ". " + current + " -> $" + price);
        }
        System.out.print("Enter the number of the property you want to sell. ");
        System.out.println("If you don't want to sell anymore, press 0.");
        int numberOfProperty = scanner.nextInt();
        while (numberOfProperty < 0 || numberOfProperty > railroads.size()) {
            System.out.println("Enter correct number of property:");
            numberOfProperty = scanner.nextInt();
        }

        if (numberOfProperty == 0) {
            System.out.println("You didn't sell anything...");
            return;
        }

        String propertyName = railroads.get(numberOfProperty - 1);
        String[] currentProperty = findFieldByName(propertyName, board);
        int indexOfProperty = findIndexOfProperty(propertyName, board);

        railroads.remove(numberOfProperty - 1);
        playersBudget.put(currentPlayer, (int) (playersBudget.get(currentPlayer) + (Integer.parseInt(currentProperty[2]) * 0.5)));
        currentProperty[1] = "for rent";
        currentProperty[currentProperty.length - 1] = "none";
        board[indexOfProperty] = String.join(", ", currentProperty);

        System.out.println("You sold a railroad '" + propertyName + "' for $" + (int) (Integer.parseInt(currentProperty[2]) * 0.5) + ".");
        scanner.nextLine();
    }

    public static void sellUtility(String currentPlayer, Map<String, Integer> playersBudget, List<String> properties, String[] board, Scanner scanner, Map<String, Integer> playersFieldNumber, Map<String, Map<String, Boolean>> haveColorSet, Map<String, Map<String, Integer>> playersColorSet, List<String> railroads, List<String> utilities) {
        System.out.println(currentPlayer + ", you have the following utilities:");
        int price = 0;
        for (int j = 0; j < utilities.size(); j++) {
            String current = utilities.get(j);
            int index = findIndexOfProperty(current, board);
            price = (int) (Integer.parseInt(board[index].split(", ")[2]) * 0.5);
            System.out.println(j + 1 + ". " + current + " -> $" + price);
        }
        System.out.print("Enter the number of the utility you want to sell. ");
        System.out.println("If you don't want to sell anymore, press 0.");
        int numberOfProperty = scanner.nextInt();
        while (numberOfProperty < 0 || numberOfProperty > utilities.size()) {
            System.out.println("Enter correct number of property:");
            numberOfProperty = scanner.nextInt();
        }

        if (numberOfProperty == 0) {
            System.out.println("You didn't sell anything...");
            return;
        }

        String propertyName = utilities.get(numberOfProperty - 1);
        String[] currentProperty = findFieldByName(propertyName, board);
        int indexOfProperty = findIndexOfProperty(propertyName, board);

        utilities.remove(numberOfProperty - 1);
        playersBudget.put(currentPlayer, (int) (playersBudget.get(currentPlayer) + (Integer.parseInt(currentProperty[2]) * 0.5)));
        currentProperty[1] = "for rent";
        currentProperty[currentProperty.length - 1] = "none";
        board[indexOfProperty] = String.join(", ", currentProperty);

        System.out.println("You sold a utility '" + propertyName + "' for $" + (int) (Integer.parseInt(currentProperty[2]) * 0.5) + ".");
        scanner.nextLine();
    }

    public static String[] findFieldByName(String name, String[] board) {
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

    public static String checkForEmptyName(String name, Scanner scanner) {
        while (name == null || name.trim().isEmpty()) {
            System.out.print("The name can't be empty. Enter player name: ");
            name = scanner.nextLine();
        }
        return name;
    }

    public static String checkForSameName(String currentName, List<String> names, Scanner scanner) {
        for (int i = 0; i < names.size(); ) {
            if (currentName.equals(names.get(i))) {
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
        System.out.println("-----------");
        System.out.println(currentPlayer + ", you passed the Start field. You get $200.");

        pressEnterToContinue(scanner);
    }

    public static void pressEnterToContinue(Scanner scanner) {
        System.out.println("Press 'Enter' to continue.");
        String playerRoll = scanner.nextLine();

        while (!playerRoll.isEmpty()) {
            System.out.println("Press 'Enter' to continue.");
            playerRoll = scanner.nextLine();
        }
    }

    public static int checkIndexForNextPlayer(Scanner scanner, List<String> players, int index) {
        index++;
        index = checkIndex(players, index);
        return index;
    }

    public static int checkIndexForNotEnoughMoney(Scanner scanner, Map<String, Integer> playersBudget, Map<String, List<String>> playersPurchases, Map<String, Map<String, Integer>> playersHouses, Map<String, Map<String, Integer>> playersHotels, List<String> players, String[] board, int index, String currentPlayer, Map<String, Integer> playersFieldNumber, Map<String, Map<String, Boolean>> haveColorSet, Map<String, Map<String, Integer>> playersColorSet, List<String> railroads, List<String> utilities) {
        String TEXT_RESET = "\u001B[0m";
        String TEXT_RED = "\u001B[31m";

        while (playersBudget.get(currentPlayer) < 0) {

            offerToSell(currentPlayer, playersBudget, playersPurchases.get(currentPlayer), board, scanner, playersHotels, playersHouses, playersPurchases.get(currentPlayer), players, playersFieldNumber, haveColorSet, playersColorSet, railroads, utilities);

            if (playersBudget.get(currentPlayer) >= 0) {
                break;
            }

            if (findSumOfAllHotelsOrHousesOfPlayer(currentPlayer, playersHotels) == 0
                    && findSumOfAllHotelsOrHousesOfPlayer(currentPlayer, playersHouses) == 0
                    && playersPurchases.get(currentPlayer).isEmpty()
                    && playersBudget.get(currentPlayer) < 0) {

                players.remove(currentPlayer);

                System.out.println(TEXT_RED + currentPlayer + ", the game over for you!" + TEXT_RESET);
                System.out.println("-----------");
                index--;
                break;
            }
        }
        return index;
    }

    public static void stayInJail(int moveNumber, Scanner scanner, Map<String, Integer> playersBudget, Map<String, Integer> playersJailCounter, Map<String, List<String>> playersSpecialCards, Map<String, Boolean> playersInJail, List<String> players, int index, String currentPlayer) {
        String TEXT_RED = "\u001B[31m";
        String TEXT_RESET = "\u001B[0m";

        if (playersSpecialCards.get(currentPlayer).contains("Get Out of Jail Free")) {
            usePlayerCardInJail(playersSpecialCards, playersInJail, currentPlayer);
        } else {
            System.out.println(TEXT_RED + "-----------");
            System.out.printf("%s, you are In Jail. You must roll 12%n", currentPlayer);
            System.out.println("-----------" + TEXT_RESET);

            if (playersInJail.get(currentPlayer)) {
                pressEnterToRollTheDice(currentPlayer, scanner);
                moveNumber = rollTheDice(currentPlayer, scanner);
            }

            if (moveNumber == 12) {
                rollTwelveInJail(scanner, playersJailCounter, playersInJail, currentPlayer);
            } else {
                rollThreeTimesInJail(scanner, playersBudget, playersJailCounter, playersInJail, currentPlayer);
                if (playersInJail.get(currentPlayer)) {
                    System.out.println("No, try again next time!");
                }
            }
        }
    }

    public static void usePlayerCardInJail(Map<String, List<String>> playersSpecialCards, Map<String, Boolean> playersInJail, String currentPlayer) {
        playersInJail.put(currentPlayer, false);
        playersSpecialCards.get(currentPlayer).remove(0);
    }

    public static void rollThreeTimesInJail(Scanner scanner, Map<String, Integer> playersBudget, Map<String, Integer> playersJailCounter, Map<String, Boolean> playersInJail, String currentPlayer) {

        if (playersJailCounter.get(currentPlayer) >= 2) {

            System.out.println(currentPlayer + ", you failed to roll a 12 three times.");
            System.out.println("You have to pay $150 and get out of jail.");
            pressEnterToContinue(scanner);

            playersInJail.put(currentPlayer, false);
            playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) - 150);
            playersJailCounter.put(currentPlayer, 0);

            System.out.println(currentPlayer + ", you paid $150 and you are out of jail.");
            pressEnterToContinue(scanner);
        } else {
            playersJailCounter.put(currentPlayer, playersJailCounter.get(currentPlayer) + 1);
        }
    }

    public static void rollTwelveInJail(Scanner scanner, Map<String, Integer> playersJailCounter, Map<String, Boolean> playersInJail, String currentPlayer) {
        playersInJail.put(currentPlayer, false);
        playersJailCounter.put(currentPlayer, 0);
        System.out.println(currentPlayer + ", you roll 12 and you're out of jail");
        pressEnterToContinue(scanner);
    }

    public static int checkIndex(List<String> players, int index) {
        if (index == players.size()) {
            index = 0;
        }
        return index;
    }

    public static void setColor(Map<String, Map<String, Integer>> colorSet, String currentPlayer, String colorType) {
        Map<String, Integer> color = colorSet.get(currentPlayer);

        if (color.get(colorType) != null) {
            color.put(colorType, color.get(colorType) + 1);
        } else {
            color.put(colorType, 1);
        }

        colorSet.put(currentPlayer, color);
    }

    public static void checkForColorGroup(Map<String, Map<String, Integer>> playersColorFields, String currentPlayer, String colorType, Map<String, Map<String, Boolean>> haveColorGroup, Map<String, Integer> playersFieldNumber) {

        int numberForColorGroup = 0;
        switch (playersFieldNumber.get(currentPlayer)) {
            case 1, 3, 37, 39:
                numberForColorGroup = 2;
                break;
            case 6, 8, 9, 11, 13, 14, 16, 18, 19, 21, 23, 24, 26, 27, 29, 31, 32, 34:
                numberForColorGroup = 3;
                break;
        }
        Map<String, Boolean> temp = new HashMap<>();
        temp.put(colorType, false);
        if (playersColorFields.get(currentPlayer).get(colorType) == numberForColorGroup) {
            temp.put(colorType, true);
        }
        haveColorGroup.put(currentPlayer, temp);
    }

    public static String switchColor(Map<String, Integer> playersFieldNumber, String currentPlayer) {
        String colorType = "";
        switch (playersFieldNumber.get(currentPlayer)) {
            case 1, 3:
                colorType = "brown";
                break;
            case 6, 8, 9:
                colorType = "light blue";
                break;
            case 11, 13, 14:
                colorType = "pink";
                break;
            case 16, 18, 19:
                colorType = "orange";
                break;
            case 21, 23, 24:
                colorType = "red";
                break;
            case 26, 27, 29:
                colorType = "yellow";
                break;
            case 31, 32, 34:
                colorType = "green";
                break;
            case 37, 39:
                colorType = "dark blue";
                break;
        }
        return colorType;
    }

}

