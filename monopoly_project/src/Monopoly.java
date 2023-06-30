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

        String[] board = arrayFromFile("BoardFields.txt", 40);

        String[] communityChest = arrayFromFile("CommunityChest.txt", 16);
        ArrayDeque<String> communityChestCards = convertArrayToDeque(communityChest);

        String[] chance = arrayFromFile("Chance.txt", 16);
        ArrayDeque<String> chanceCards = convertArrayToDeque(chance);

        printWelcome(scanner);
        printBoard();

        int numberOfPlayers = enterNumberOfPlayers(scanner);

        fillPlayerNames(numberOfPlayers, players, scanner);

        for (String player : players) {
            playersBudget.put(player, 10000);
            playersFieldNumber.put(player, 0);
            playersProperties.put(player, new ArrayList<>());
            playersRailroads.put(player, new ArrayList<>());
            playersUtilities.put(player, new ArrayList<>());
            playersSpecialCards.put(player, new ArrayList<>());
            playersInJail.put(player, false);
            playersHouses.put(player, new HashMap<>());
            playersHotels.put(player, new HashMap<>());
            playersColorFields.put(player, new HashMap<>());
            haveColorGroup.put(player, new HashMap<>());
            playersJailCounter.put(player, 0);
        }

        printStart(playersBudget, players);

        int index = 0;
        while (!players.isEmpty()) {
            String currentPlayer = players.get(index);
            int moveNumber = 0;

            if (playersInJail.get(currentPlayer)) {

                stayInJail(moveNumber, scanner, playersBudget, playersJailCounter, playersSpecialCards, playersInJail, currentPlayer);
                index = checkIndexForNextPlayer(players, index);

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

                index = checkIndexForNextPlayer(players, index);
                System.out.println("Next player!");
                pressEnterToContinue(scanner);
                continue;
            }

            pressEnterToRollTheDice(currentPlayer, scanner);
            moveNumber = rollTheDice();


            playersFieldNumber.put(currentPlayer, playersFieldNumber.get(currentPlayer) + moveNumber);

            scrollTheFields(scanner, playersFieldNumber, playersBudget, currentPlayer);

            String[] currentField = getCurrentField(playersFieldNumber, board, currentPlayer);

            printCurrentField(currentField);
            pressEnterToContinue(scanner);

            System.out.println(getPlayersStats(currentPlayer, playersBudget.get(currentPlayer), playersProperties.get(currentPlayer), playersSpecialCards.get(currentPlayer), playersHouses, playersHotels, playersRailroads.get(currentPlayer), playersUtilities.get(currentPlayer)));

            switch (playersFieldNumber.get(currentPlayer)) {
                case 0, 2, 4, 7, 10, 17, 20, 22, 30, 33, 36, 38 ->
                        switchOtherFields(scanner, players, playersFieldNumber, playersBudget, playersProperties,
                                playersHouses, playersHotels, playersSpecialCards, playersInJail, board, communityChestCards, chanceCards, currentPlayer,
                                playersRailroads, playersUtilities, haveColorGroup, playersColorFields);
                case 1, 3, 6, 8, 9, 11, 13, 14, 16, 18, 19, 21, 23, 24, 26, 27, 29, 31, 32, 34, 37, 39 ->
                        buyColorField(currentField, scanner, currentPlayer,
                                playersBudget, playersProperties.get(currentPlayer),
                                playersSpecialCards.get(currentPlayer), playersFieldNumber,
                                board, playersHouses, playersHotels,
                                playersRailroads, playersUtilities, haveColorGroup, playersColorFields);
                case 5, 15, 25, 35 -> buyCompanyField(currentField, scanner, currentPlayer, playersBudget,
                        playersRailroads.get(currentPlayer),
                        playersFieldNumber, board, playersHotels, playersHouses, playersColorFields, haveColorGroup, playersRailroads, playersUtilities);
                case 12, 28 -> buyCompanyField(currentField, scanner, currentPlayer, playersBudget,
                        playersUtilities.get(currentPlayer),
                        playersFieldNumber, board, playersHotels, playersHouses, playersColorFields, haveColorGroup, playersRailroads, playersUtilities);
            }

            System.out.println(getPlayersStats(currentPlayer, playersBudget.get(currentPlayer), playersProperties.get(currentPlayer), playersSpecialCards.get(currentPlayer), playersHouses, playersHotels, playersRailroads.get(currentPlayer), playersUtilities.get(currentPlayer)));
            index = checkIndexForNextPlayer(players, index);
            System.out.println("Next player!");
            pressEnterToContinue(scanner);
        }
    }

    public static int enterNumberOfPlayers(Scanner scanner) {
        System.out.println("Enter a number of players (1-4): ");
        String number = scanner.nextLine();
        while (number.isEmpty()) {
            System.out.println("Enter a number of players (1-4): ");
            number = scanner.nextLine();
        }
        int numberOfPlayers = Integer.parseInt(number);
        while (numberOfPlayers < 1 || numberOfPlayers > 4) {
            System.out.println("Enter valid number of players (1-4): ");
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
        System.out.println(TEXT_GREEN + "        *********");
        System.out.println("     **** START ****");
        System.out.println("        *********");
        System.out.println("--------------------------");
        System.out.println("Each player is given $" + playersBudget.get(players.get(0)));
        System.out.println("--------------------------" + TEXT_RESET);
    }

    public static int rollTheDice() {
        Random firstDice = new Random();
        Random secondDice = new Random();

        int firstDiceResult = firstDice.nextInt(1, 7);
        int secondDiceResult = secondDice.nextInt(1, 7);
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
        scanner.nextLine();
    }

    public static void scrollTheFields(Scanner scanner, Map<String, Integer> playersFieldNumber, Map<String, Integer> playersBudget, String currentPlayer) {
        if (playersFieldNumber.get(currentPlayer) >= 40) {

            playersFieldNumber.put(currentPlayer, playersFieldNumber.get(currentPlayer) - 40);
            playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) + 200);
            printGoTroughTheStart(currentPlayer, scanner);

        }
    }

    public static String[] getCurrentField(Map<String, Integer> playersFieldNumber, String[] board, String currentPlayer) {
        return board[playersFieldNumber.get(currentPlayer)].split(", ");
    }

    public static void printCurrentField(String[] currentField) {
        String TEXT_BLUE = "\u001B[34m";
        String TEXT_RESET = "\u001B[0m";
        System.out.println(TEXT_BLUE + "You go to '" + currentField[0] + "' field." + TEXT_RESET);
        System.out.println("-----------");
    }

    public static String getPlayersStats(String name, int money, List<String> purchases, List<String> specialCards, Map<String, Map<String, Integer>> playersHouses, Map<String, Map<String, Integer>> playersHotels, List<String> railroads, List<String> utilities) {
        String TEXT_GREEN = "\u001B[32m";
        String TEXT_RESET = "\u001B[0m";

        String playerFields = purchases.size() > 0 ? "Properties purchased: " + String.join(", ", purchases) : "Properties purchased: none";
        String playerRailroads = railroads.size() > 0 ? "RailRoads purchased: " + String.join(", ", railroads) : "Railroads purchased: none";
        String playerUtilities = utilities.size() > 0 ? "Utilities purchased: " + String.join(", ", utilities) : "Utilities purchased: none";
        String playerCards = specialCards.size() > 0 ? "Special cards: " + String.join(", ", specialCards) : "Special cards: none";

        StringBuilder sb = new StringBuilder();
        sb.append("----------------------").append(System.lineSeparator())
                .append(String.format("%s's statistic:", name)).append(System.lineSeparator())
                .append(String.format("Money: " + "$" + money)).append(System.lineSeparator())
                .append(playerFields).append(System.lineSeparator())
                .append(String.format("    Houses: " + findSumOfAllHotelsOrHousesOfPlayer(name, playersHouses))).append(System.lineSeparator())
                .append(String.format("    Hotels: " + findSumOfAllHotelsOrHousesOfPlayer(name, playersHotels))).append(System.lineSeparator())
                .append(playerRailroads).append(System.lineSeparator())
                .append(playerUtilities).append(System.lineSeparator())
                .append(playerCards).append(System.lineSeparator())
                .append("----------------------");

        return TEXT_GREEN + sb + TEXT_RESET;
    }

    public static String checkForWinner(Map<String, Integer> playersBudget, Map<String, List<String>> playersPurchases, Map<String, List<String>> playersSpecialCards, int numberOfPlayers, List<String> players, String currentPlayer, Map<String, Map<String, Integer>> playersHouses, Map<String, Map<String, Integer>> playersHotels, Map<String, List<String>> playersRailroads, Map<String, List<String>> playersUtilities) {
        String TEXT_YELLOW = "\u001B[33m";
        String TEXT_RESET = "\u001B[0m";


        if (numberOfPlayers != players.size() && players.size() == 1) {
            String winner = players.get(0);

            System.out.println(getPlayersStats(winner, playersBudget.get(winner), playersPurchases.get(winner), playersSpecialCards.get(winner), playersHouses, playersHotels, playersRailroads.get(winner), playersUtilities.get(winner)));
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
        for (String currentPlayer : communityChest) {
            result.offer(currentPlayer);
        }
        return result;
    }

    public static void buyCompanyField(String[] currentField, Scanner scanner, String currentPlayer,
                                       Map<String, Integer> playersBudget, List<String> properties,
                                       Map<String, Integer> playersFieldNumber, String[] board, Map<String, Map<String, Integer>> playersHotels,
                                       Map<String, Map<String, Integer>> playersHouses, Map<String, Map<String, Integer>> playersColorSet,
                                       Map<String, Map<String, Boolean>> haveColorSet,
                                       Map<String, List<String>> playersRailroads, Map<String, List<String>> playersUtilities) {

        String TEXT_YELLOW = "\u001B[33m";
        String TEXT_RESET = "\u001B[0m";

        printFieldCard(createCompanyFieldCard(currentField), TEXT_YELLOW, TEXT_RESET);

        if (currentField[1].equals("for rent") && !currentPlayer.equals(currentField[currentField.length - 1])) {
            getCompanyFieldForRent(currentField, scanner, currentPlayer, playersBudget, properties, playersFieldNumber, board, playersHotels, playersHouses, playersColorSet, haveColorSet, playersRailroads, playersUtilities, TEXT_YELLOW, TEXT_RESET);
        } else if (!currentPlayer.equals(currentField[currentField.length - 1])) {
            String fieldOwner = currentField[currentField.length - 1];
            int fieldNumber = playersFieldNumber.get(currentPlayer);
            int annuity = getCompanyAnnuity(playersRailroads, playersUtilities, fieldOwner, fieldNumber);
            payAnnuity(currentPlayer, playersBudget, fieldOwner, annuity);
        } else {
            System.out.printf("%s, '%s' field is yours.%n", currentPlayer, currentField[0]);
            pressEnterToContinue(scanner);
        }
    }

    private static void getCompanyFieldForRent(String[] currentField, Scanner scanner, String currentPlayer, Map<String, Integer> playersBudget, List<String> playersPurchases, Map<String, Integer> playersFieldNumber, String[] board, Map<String, Map<String, Integer>> playersHotels, Map<String, Map<String, Integer>> playersHouses, Map<String, Map<String, Integer>> playersColorSet, Map<String, Map<String, Boolean>> haveColorSet, Map<String, List<String>> playersRailroads, Map<String, List<String>> playersUtilities, String TEXT_YELLOW, String TEXT_RESET) {
        if (doYouWontToBuy(currentField, scanner).equals("yes")) {

            if (moneyValidation(playersBudget.get(currentPlayer), Integer.parseInt(currentField[2]))) {

                answerYesToBuy(currentField, currentPlayer, playersBudget, playersPurchases, playersFieldNumber, board);
                System.out.println(currentPlayer + ", you bought " + currentField[0]);
                printFieldCard(createCompanyFieldCard(currentField), TEXT_YELLOW, TEXT_RESET);
            } else {
                offerToSell(currentPlayer, playersBudget, playersPurchases, board, scanner, playersHotels, playersHouses, playersFieldNumber, haveColorSet, playersColorSet, playersRailroads.get(currentPlayer), playersUtilities.get(currentPlayer));

                if (moneyValidation(playersBudget.get(currentPlayer), Integer.parseInt(currentField[2]))) {

                    buyCompanyField(currentField, scanner, currentPlayer, playersBudget,
                            playersPurchases,
                            playersFieldNumber, board, playersHotels, playersHouses, playersColorSet, haveColorSet, playersRailroads, playersUtilities);
                } else {
                    System.out.println(currentPlayer + ", you don't have enough money to buy " + currentField[0] + "...");
                }
            }
        }
    }

    private static int getCompanyAnnuity(Map<String, List<String>> playersRailroads, Map<String, List<String>> playersUtilities, String fieldOwner, int fieldNumber) {
        int annuity = 0;
        switch (fieldNumber) {
            case 5, 15, 25, 35 -> {
                if (playersRailroads.get(fieldOwner).size() == 1) {
                    annuity = 25;
                } else if (playersRailroads.get(fieldOwner).size() == 2) {
                    annuity = 50;
                } else if (playersRailroads.get(fieldOwner).size() == 3) {
                    annuity = 100;
                } else if (playersRailroads.get(fieldOwner).size() == 4) {
                    annuity = 200;
                }
            }
            case 12, 28 -> {
                if (playersUtilities.get(fieldOwner).size() == 1) {
                    annuity = 25;
                } else if (playersUtilities.get(fieldOwner).size() == 2) {
                    annuity = 75;
                }
            }
        }
        return annuity;
    }

    public static void buyColorField(String[] currentField, Scanner scanner, String currentPlayer,
                                     Map<String, Integer> playersBudget, List<String> playersPurchases,
                                     List<String> playersSpecialCards, Map<String, Integer> playersFieldNumber,
                                     String[] board, Map<String, Map<String, Integer>> playersHouses, Map<String, Map<String, Integer>> playersHotels,
                                     Map<String, List<String>> playersRailroads, Map<String, List<String>> playersUtilities,
                                     Map<String, Map<String, Boolean>> haveColorGroup, Map<String, Map<String, Integer>> playersColorSet) {

        String TEXT_CYAN = "\u001B[36m";
        String TEXT_RESET = "\u001B[0m";

        printFieldCard(createColorFieldCard(currentField), TEXT_CYAN, TEXT_RESET);

        if (currentField[1].equals("for rent")) {
            getColorFieldForRent(currentField, scanner, currentPlayer, playersBudget, playersPurchases, playersSpecialCards, playersFieldNumber, board, playersHouses, playersHotels, playersRailroads, playersUtilities, haveColorGroup, playersColorSet);
        } else {

            if (!currentPlayer.equals(currentField[currentField.length - 1])) {

                String fieldOwner = currentField[currentField.length - 1];
                int housesCount = playersHouses.get(fieldOwner).get(currentField[0]);
                int hotelsCount = playersHotels.get(fieldOwner).get(currentField[0]);
                int annuity = getColorAnnuity(currentField, housesCount, hotelsCount, haveColorGroup, currentPlayer, playersFieldNumber);
                payAnnuity(currentPlayer, playersBudget, fieldOwner, annuity);

            } else {
                String color = switchColor(playersFieldNumber, currentPlayer);
                if (haveColorGroup.get(currentPlayer).get(color)) {
                    buyHouseOrHotel(playersHouses, playersHotels, currentPlayer,
                            currentField, scanner, playersBudget, playersPurchases, playersSpecialCards, playersRailroads, playersUtilities);
                } else {
                    System.out.printf("%s, '%s' field is yours.%n", currentPlayer, currentField[0]);
                    pressEnterToContinue(scanner);
                }
            }
        }
    }

    private static void getColorFieldForRent(String[] currentField, Scanner scanner, String currentPlayer, Map<String, Integer> playersBudget, List<String> playersPurchases, List<String> playersSpecialCards, Map<String, Integer> playersFieldNumber, String[] board, Map<String, Map<String, Integer>> playersHouses, Map<String, Map<String, Integer>> playersHotels, Map<String, List<String>> playersRailroads, Map<String, List<String>> playersUtilities, Map<String, Map<String, Boolean>> haveColorGroup, Map<String, Map<String, Integer>> playersColorSet) {
        String TEXT_CYAN = "\u001B[36m";
        String TEXT_RESET = "\u001B[0m";

        if (doYouWontToBuy(currentField, scanner).equals("yes")) {

            if (moneyValidation(playersBudget.get(currentPlayer), Integer.parseInt(currentField[2]))) {

                answerYesToBuyColorField(currentField, currentPlayer, playersBudget, playersPurchases, playersFieldNumber, board, playersColorSet, haveColorGroup);
                initializeHousesAndHotels(currentField, currentPlayer, playersHouses, playersHotels);

                System.out.println(currentPlayer + ", you bought " + currentField[0]);
                printFieldCard(createColorFieldCard(currentField), TEXT_CYAN, TEXT_RESET);

            } else {
                offerToSell(currentPlayer, playersBudget, playersPurchases, board, scanner, playersHotels, playersHouses, playersFieldNumber, haveColorGroup, playersColorSet, playersRailroads.get(currentPlayer), playersUtilities.get(currentPlayer));

                if (moneyValidation(playersBudget.get(currentPlayer), Integer.parseInt(currentField[2]))) {
                    buyColorField(currentField, scanner, currentPlayer,
                            playersBudget, playersPurchases,
                            playersSpecialCards, playersFieldNumber,
                            board, playersHouses, playersHotels, playersRailroads, playersUtilities, haveColorGroup, playersColorSet);
                } else {
                    System.out.println(currentPlayer + ", you don't have enough money to buy " + currentField[0] + "...");
                }
            }
        }
    }

    private static void initializeHousesAndHotels(String[] currentField, String currentPlayer, Map<String, Map<String, Integer>> playersHouses, Map<String, Map<String, Integer>> playersHotels) {
        Map<String, Integer> houses = playersHouses.get(currentPlayer);
        Map<String, Integer> hotels = playersHotels.get(currentPlayer);
        houses.put(currentField[0], 0);
        hotels.put(currentField[0], 0);
        playersHouses.put(currentPlayer, houses);
        playersHotels.put(currentPlayer, hotels);
    }

    public static void sellHouse(Scanner scanner, String currentPlayer, Map<String, Integer> playersBudget, String[] board, Map<String, Map<String, Integer>> playersHouses, int playerHouses) {
        printHousesInformation(currentPlayer, playerHouses);

        List<String> fields = createListOfFields(currentPlayer, playersHouses);
        int numberOfField = enterNumberOfField(scanner, fields);
        sellHouseOrHotelOperation(currentPlayer, playersBudget, board, playersHouses, numberOfField);
        System.out.println(currentPlayer + ", you sold a house in a field " + fields.get(numberOfField - 1) + ".");
        pressEnterToContinue(scanner);
    }

    private static void printHousesInformation(String currentPlayer, int playerHouses) {
        System.out.println(currentPlayer + ", you have " + playerHouses + " houses. You have to sell one of them...");
        System.out.println("These are your houses: ");
    }

    public static void sellHotel(Scanner scanner, String currentPlayer, Map<String, Integer> playersBudget, String[] board, Map<String, Map<String, Integer>> playersHotels, int playerHotels) {
        printHotelsInformation(currentPlayer, playerHotels);

        List<String> fields = createListOfFields(currentPlayer, playersHotels);
        int numberOfField = enterNumberOfField(scanner, fields);
        sellHouseOrHotelOperation(currentPlayer, playersBudget, board, playersHotels, numberOfField);
        System.out.println(currentPlayer + ", you sold a hotel in a field " + fields.get(numberOfField - 1) + ".");
        pressEnterToContinue(scanner);
    }

    private static void printHotelsInformation(String currentPlayer, int playerHotels) {
        System.out.println(currentPlayer + ", you have " + playerHotels + " hotels. You have to sell one of them");
        System.out.println("These are your hotels: ");
    }

    public static void sellHouseOrHotelOperation(String currentPlayer, Map<String, Integer> playersBudget, String[] board, Map<String, Map<String, Integer>> playersHotels, int numberOfField) {

        List<String> fields = createListOfFields(currentPlayer, playersHotels);
        String nameOfField = fields.get(numberOfField - 1);

        Map<String, Integer> temp = playersHotels.get(currentPlayer);
        temp.put(nameOfField, temp.get(nameOfField) - 1);
        playersHotels.put(currentPlayer, temp);

        int price = Integer.parseInt(findFieldByName(nameOfField, board)[11]);
        playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) + price);
    }

    public static List<String> createListOfFields(String currentPlayer, Map<String, Map<String, Integer>> playersHotelsOrHouses) {
        int index = 1;
        List<String> fields = new ArrayList<>();
        for (Map.Entry<String, Integer> stringIntegerEntry : playersHotelsOrHouses.get(currentPlayer).entrySet()) {
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
        String number = validateEmptyString(scanner);
        int numberOfTheField = Integer.parseInt(number);

        while (numberOfTheField <= 0 || numberOfTheField > fields.size()) {
            System.out.println("Enter the number of the field whose house or hotel you want to sell: ");
            numberOfTheField = Integer.parseInt(scanner.nextLine());
        }
        return numberOfTheField;
    }

    public static int findSumOfAllHotelsOrHousesOfPlayer(String currentPlayer, Map<String, Map<String, Integer>> housesOrHotels) {
        int sumOfHotels = 0;
        for (Integer value : housesOrHotels.get(currentPlayer).values()) {
            sumOfHotels += value;
        }
        return sumOfHotels;
    }

    public static int getColorAnnuity(String[] currentField, int housesCount, int hotelsCount, Map<String, Map<String, Boolean>> haveColorGroup, String currentPlayer, Map<String, Integer> playersFieldNumber) {
        int annuity = 0;
        if (hotelsCount == 1) {
            annuity = Integer.parseInt(currentField[9]);
        } else if (housesCount > 0) {
            annuity = switch (housesCount) {
                case 1 -> Integer.parseInt(currentField[5]);
                case 2 -> Integer.parseInt(currentField[6]);
                case 3 -> Integer.parseInt(currentField[7]);
                case 4 -> Integer.parseInt(currentField[8]);
                default -> annuity;
            };
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

    public static void answerYesToBuy(String[] currentField, String currentPlayer, Map<String, Integer> playersBudget, List<String> playersPurchases, Map<String, Integer> playersFieldNumber, String[] board) {
        currentField[1] = "Sold";
        currentField[currentField.length - 1] = currentPlayer;
        playersPurchases.add(currentField[0]);
        playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) - Integer.parseInt(currentField[2]));

        board[playersFieldNumber.get(currentPlayer)] = String.join(", ", currentField);
    }

    public static void answerYesToBuyColorField(String[] currentField, String currentPlayer, Map<String, Integer> playersBudget, List<String> playersPurchases, Map<String, Integer> playersFieldNumber, String[] board, Map<String, Map<String, Integer>> playersColorSet, Map<String, Map<String, Boolean>> haveColorSet) {
        answerYesToBuy(currentField, currentPlayer, playersBudget, playersPurchases, playersFieldNumber, board);
        String colorType = switchColor(playersFieldNumber, currentPlayer);
        setColor(playersColorSet, currentPlayer, colorType);
        checkForColorGroup(playersColorSet, currentPlayer, colorType, haveColorSet, playersFieldNumber);

    }

    public static String doYouWontToBuy(String[] currentField, Scanner scanner) {
        System.out.println("Do you want to buy " + currentField[0] + "?");
        return enterYesOrNo(scanner);
    }

    public static void buyHouseOrHotel(Map<String, Map<String, Integer>> playersHouses,
                                       Map<String, Map<String, Integer>> playersHotels,
                                       String currentPlayer, String[] currentField, Scanner scanner,
                                       Map<String, Integer> playersBudget, List<String> playersProperties,
                                       List<String> playersSpecialCards, Map<String, List<String>> playersRailroads,
                                       Map<String, List<String>> playersUtilities) {

        String TEXT_BLUE = "\u001B[34m";
        String TEXT_RESET = "\u001B[0m";
        Map<String, Integer> houses = playersHouses.get(currentPlayer);
        Map<String, Integer> hotels = playersHotels.get(currentPlayer);

        if (houses.get(currentField[0]) < 4) {
            System.out.printf("%s field is yours.%n", currentField[0]);
            System.out.println(TEXT_BLUE + currentPlayer + ", you have a color group. Now you can build houses and hotels..." + TEXT_RESET);
            System.out.printf("You have %d houses and %d hotels on this field.%n",
                    playersHouses.get(currentPlayer).get(currentField[0]),
                    playersHotels.get(currentPlayer).get(currentField[0]));

            System.out.println("Do you want to buy a house?");

            if (enterYesOrNo(scanner).equals("yes")) {

                int housePrice = Integer.parseInt(currentField[10]);

                houses.put(currentField[0], houses.get(currentField[0]) + 1);
                playersHouses.put(currentPlayer, houses);
                playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) - housePrice);

                System.out.println(TEXT_BLUE + currentPlayer + ", you bought a house." + TEXT_RESET);
                System.out.println(getPlayersStats(currentPlayer, playersBudget.get(currentPlayer), playersProperties, playersSpecialCards, playersHouses, playersHotels, playersRailroads.get(currentPlayer), playersUtilities.get(currentPlayer)));
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

                    System.out.println(TEXT_BLUE + currentPlayer + ", you bought a hotel" + TEXT_RESET);
                    System.out.println(getPlayersStats(currentPlayer, playersBudget.get(currentPlayer), playersProperties, playersSpecialCards, playersHouses, playersHotels, playersRailroads.get(currentPlayer), playersUtilities.get(currentPlayer)));
                }
            }
        }
    }

    public static void switchChance(String chanceRow, Map<String, Integer> playersFieldNumber,
                                    String currentPlayer, Map<String, Integer> playersBudget,
                                    List<String> playersSpecialCards, Map<String, Boolean> playersInJail,
                                    List<String> players, List<String> playersPurchases, String[] board,
                                    Scanner scanner, Map<String, Map<String, Integer>> playersHouses,
                                    Map<String, Map<String, Integer>> playersHotels, Map<String, List<String>> playersRailroads,
                                    Map<String, List<String>> playersUtilities, Map<String, Map<String, Boolean>> haveColorSet,
                                    Map<String, Map<String, Integer>> playersColorSet) {
        switch (chanceRow) {
            case "Advance to Boardwalk":
                advanceToBoardwalk(playersFieldNumber, currentPlayer, playersBudget, playersSpecialCards, playersPurchases, board, scanner, playersHouses, playersHotels, playersRailroads, playersUtilities, haveColorSet, playersColorSet);
                break;
            case "Advance to START (Collect $200)":
                playersFieldNumber.put(currentPlayer, 0);
                playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) + 200);
                break;
            case "Advance to Illinois Avenue. If you pass START, collect $200":
                advanceToIllinoisAvenue(playersFieldNumber, currentPlayer, playersBudget, playersSpecialCards, playersPurchases, board, scanner, playersHouses, playersHotels, playersRailroads, playersUtilities, haveColorSet, playersColorSet);
                break;
            case "Advance to St. Charles Place. If you pass START, collect $200":
                advanceToCharlesPlace(playersFieldNumber, currentPlayer, playersBudget, playersSpecialCards, playersPurchases, board, scanner, playersHouses, playersHotels, playersRailroads, playersUtilities, haveColorSet, playersColorSet);
                break;
            case "Advance to the nearest Railroad. If unowned, you may buy it from the Bank. If owned, " +
                    "pay wonder twice the rental to which they are otherwise entitled":
                advanceToRailRoad(playersFieldNumber, currentPlayer, playersBudget, playersPurchases, board, scanner, playersHouses, playersHotels, playersRailroads, playersUtilities, haveColorSet, playersColorSet);
                break;
            case "Advance token to nearest Utility. If unowned, you may buy it from the Bank.":
                advanceToUtility(playersFieldNumber, currentPlayer, playersBudget, playersPurchases, board, scanner, playersHouses, playersHotels, playersRailroads, playersUtilities, haveColorSet, playersColorSet);
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
                goToTheJail(currentPlayer, playersFieldNumber, playersInJail);
                break;
            case "Make general repairs on all your property. For each house pay $25. For each hotel pay $100":
                repairHousesAndHotels(currentPlayer, playersBudget, playersHouses, playersHotels, 25, 100);
                break;
            case "Speeding fine $15":
                playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) - 15);
                System.out.println(currentPlayer + ", you paid $15");
                break;
            case "Take a trip to Reading Railroad. If you pass START, collect $200":
                advanceToReadingRailRoad(playersFieldNumber, currentPlayer, playersBudget, playersPurchases, board, scanner, playersHouses, playersHotels, playersRailroads, playersUtilities, haveColorSet, playersColorSet);
                break;
            case "You have been elected Chairman of the Board. Pay each player $50":
                payEachPlayer(currentPlayer, playersBudget, players);
                break;
            case "Your building loan matures. Collect $150":
                playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) + 150);
                System.out.println(currentPlayer + ", you collect $150");
                break;
        }
    }

    private static void advanceToReadingRailRoad(Map<String, Integer> playersFieldNumber, String currentPlayer, Map<String, Integer> playersBudget, List<String> playersPurchases, String[] board, Scanner scanner, Map<String, Map<String, Integer>> playersHouses, Map<String, Map<String, Integer>> playersHotels, Map<String, List<String>> playersRailroads, Map<String, List<String>> playersUtilities, Map<String, Map<String, Boolean>> haveColorSet, Map<String, Map<String, Integer>> playersColorSet) {
        String[] currentField;
        playersFieldNumber.put(currentPlayer, 5);
        playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) + 200);
        System.out.println(currentPlayer + ", you move to Reading Railroad");
        System.out.println("You passed a Start and get $200");
        currentField = board[playersFieldNumber.get(currentPlayer)].split(", ");
        buyCompanyField(currentField, scanner, currentPlayer, playersBudget, playersPurchases,
                playersFieldNumber, board, playersHotels, playersHouses, playersColorSet, haveColorSet, playersRailroads, playersUtilities);
    }

    private static void payEachPlayer(String currentPlayer, Map<String, Integer> playersBudget, List<String> players) {
        int sum = 0;
        for (String current : players) {
            playersBudget.put(current, playersBudget.get(current) + 50);
            sum += 50;
        }
        playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) - sum);
        System.out.println(currentPlayer + ", you paid $" + sum);
    }

    private static void advanceToUtility(Map<String, Integer> playersFieldNumber, String currentPlayer, Map<String, Integer> playersBudget, List<String> playersPurchases, String[] board, Scanner scanner, Map<String, Map<String, Integer>> playersHouses, Map<String, Map<String, Integer>> playersHotels, Map<String, List<String>> playersRailroads, Map<String, List<String>> playersUtilities, Map<String, Map<String, Boolean>> haveColorSet, Map<String, Map<String, Integer>> playersColorSet) {
        int currentPosition;
        String[] currentField;
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
                playersFieldNumber, board, playersHotels, playersHouses, playersColorSet, haveColorSet, playersRailroads, playersUtilities);
    }

    private static void advanceToRailRoad(Map<String, Integer> playersFieldNumber, String currentPlayer, Map<String, Integer> playersBudget, List<String> playersPurchases, String[] board, Scanner scanner, Map<String, Map<String, Integer>> playersHouses, Map<String, Map<String, Integer>> playersHotels, Map<String, List<String>> playersRailroads, Map<String, List<String>> playersUtilities, Map<String, Map<String, Boolean>> haveColorSet, Map<String, Map<String, Integer>> playersColorSet) {
        int currentPosition;
        String[] currentField;
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
        buyCompanyField(currentField, scanner, currentPlayer, playersBudget, playersPurchases, playersFieldNumber,
                board, playersHotels, playersHouses, playersColorSet, haveColorSet, playersRailroads, playersUtilities);
    }

    private static void advanceToCharlesPlace(Map<String, Integer> playersFieldNumber, String currentPlayer, Map<String, Integer> playersBudget, List<String> playersSpecialCards, List<String> playersPurchases, String[] board, Scanner scanner, Map<String, Map<String, Integer>> playersHouses, Map<String, Map<String, Integer>> playersHotels, Map<String, List<String>> playersRailroads, Map<String, List<String>> playersUtilities, Map<String, Map<String, Boolean>> haveColorSet, Map<String, Map<String, Integer>> playersColorSet) {
        String[] currentField;
        int currentPosition;
        currentPosition = playersFieldNumber.get(currentPlayer);
        if (currentPosition == 36 || currentPosition == 22) {
            playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) + 200);
            System.out.println(currentPlayer + ", you passed the Start and collect $200");
        }
        playersFieldNumber.put(currentPlayer, 11);
        System.out.println(currentPlayer + ", you're going to St. Charles Place");
        currentField = board[playersFieldNumber.get(currentPlayer)].split(", ");
        buyColorField(currentField, scanner, currentPlayer, playersBudget, playersPurchases, playersSpecialCards, playersFieldNumber,
                board, playersHouses, playersHotels, playersRailroads, playersUtilities, haveColorSet, playersColorSet);
    }

    private static void advanceToIllinoisAvenue(Map<String, Integer> playersFieldNumber, String currentPlayer, Map<String, Integer> playersBudget, List<String> playersSpecialCards, List<String> playersPurchases, String[] board, Scanner scanner, Map<String, Map<String, Integer>> playersHouses, Map<String, Map<String, Integer>> playersHotels, Map<String, List<String>> playersRailroads, Map<String, List<String>> playersUtilities, Map<String, Map<String, Boolean>> haveColorSet, Map<String, Map<String, Integer>> playersColorSet) {
        String[] currentField;
        int currentPosition = playersFieldNumber.get(currentPlayer);
        if (currentPosition == 36) {
            playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) + 200);
            System.out.println("Collect $200");
        }
        playersFieldNumber.put(currentPlayer, 24);
        System.out.println(currentPosition + ", you're going to Illinois Avenue");
        currentField = board[playersFieldNumber.get(currentPlayer)].split(", ");
        buyColorField(currentField, scanner, currentPlayer, playersBudget, playersPurchases, playersSpecialCards, playersFieldNumber,
                board, playersHouses, playersHotels, playersRailroads, playersUtilities, haveColorSet, playersColorSet);
    }

    private static void advanceToBoardwalk(Map<String, Integer> playersFieldNumber, String currentPlayer, Map<String, Integer> playersBudget, List<String> playersSpecialCards, List<String> playersPurchases, String[] board, Scanner scanner, Map<String, Map<String, Integer>> playersHouses, Map<String, Map<String, Integer>> playersHotels, Map<String, List<String>> playersRailroads, Map<String, List<String>> playersUtilities, Map<String, Map<String, Boolean>> haveColorSet, Map<String, Map<String, Integer>> playersColorSet) {
        playersFieldNumber.put(currentPlayer, 39);
        String[] currentField = board[playersFieldNumber.get(currentPlayer)].split(", ");
        System.out.println(currentPlayer + ", you went to Boardwalk.");
        buyColorField(currentField, scanner, currentPlayer, playersBudget, playersPurchases, playersSpecialCards, playersFieldNumber,
                board, playersHouses, playersHotels, playersRailroads, playersUtilities, haveColorSet, playersColorSet);
    }

    public static void switchCommunityChest(String communityChestRow,
                                            String currentPlayer,
                                            Map<String, Integer> playersBudget,
                                            Map<String, Integer> playersFieldNumber,
                                            List<String> playersSpecialCards,
                                            Map<String, Boolean> playersInJail,
                                            List<String> players, Map<String, Map<String, Integer>> playersHouses, Map<String, Map<String, Integer>> playersHotels) {
        switch (communityChestRow) {
            case "Advance to START(Collect $200)" -> {
                playersFieldNumber.put(currentPlayer, 0);
                playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) + 200);
            }
            case "Bank error in your favor. Collect $200" -> {
                playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) + 200);
                System.out.println(currentPlayer + ", you received $200");
            }
            case "Doctor’s fee. Pay $50", "Pay school fees of $50" -> {
                playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) - 50);
                System.out.println(currentPlayer + ", you pay $50");
            }
            case "From sale of stock you get $50" -> {
                playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) + 50);
                System.out.println(currentPlayer + ", you received $50");
            }
            case "Get Out of Jail Free" -> {
                playersSpecialCards.add(communityChestRow);
                System.out.println(currentPlayer + ", you added the card 'Get Out of Jail Free' to your Special Card Collection");
            }
            case "Go to Jail. Go directly to jail, do not pass Go, do not collect $200" ->
                    goToTheJail(currentPlayer, playersFieldNumber, playersInJail);

            case "Holiday fund matures. Receive $100", "Life insurance matures. Collect $100", "You inherit $100" -> {
                playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) + 100);
                System.out.println(currentPlayer + ", you received $100");
            }
            case "Income tax refund. Collect $20" -> {
                playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) + 20);
                System.out.println(currentPlayer + ", you received $20");
            }
            case "It is your birthday. Collect $10 from every player" ->
                    collectFromEveryPlayer(currentPlayer, playersBudget, players);
            case "Pay hospital fees of $100" -> {
                playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) - 100);
                System.out.println(currentPlayer + ", you pay $100");
            }
            case "Receive $25 consultancy fee" -> {
                playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) + 25);
                System.out.println(currentPlayer + ", you received $25");
            }
            case "You are assessed for street repair. $40 per house. $115 per hotel" ->
                    repairHousesAndHotels(currentPlayer, playersBudget, playersHouses, playersHotels, 40, 115);
            case "You have won second prize in a beauty contest. Collect $10" -> {
                playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) + 10);
                System.out.println(currentPlayer + ", you received $10");
            }
        }
    }

    private static void collectFromEveryPlayer(String currentPlayer, Map<String, Integer> playersBudget, List<String> players) {
        int sum = 0;
        for (String current : players) {
            if (!current.equals(currentPlayer)) {
                playersBudget.put(current, playersBudget.get(current) - 10);
                sum += 10;
            }
        }
        playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) + sum);
        System.out.printf("%s, you received $%d%n", currentPlayer, sum);
    }

    private static void goToTheJail(String currentPlayer, Map<String, Integer> playersFieldNumber, Map<String, Boolean> playersInJail) {
        String TEXT_RED = "\u001B[31m";
        String TEXT_RESET = "\u001B[0m";
        playersFieldNumber.put(currentPlayer, 10);
        playersInJail.put(currentPlayer, true);
        System.out.println(TEXT_RED + currentPlayer + ", you are in Jail" + TEXT_RESET);
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
                                         Map<String, List<String>> playersSpecialCards, Map<String, Boolean> playersInJail, String[] board,
                                         ArrayDeque<String> communityChestCards, ArrayDeque<String> chanceCards, String currentPlayer,
                                         Map<String, List<String>> playersRailroads, Map<String, List<String>> playersUtilities, Map<String, Map<String, Boolean>> haveColorSet,
                                         Map<String, Map<String, Integer>> playersColorSet) {

        String[] currentField = getCurrentField(playersFieldNumber, board, currentPlayer);
        switch (currentField[0]) {
            case "START" -> System.out.println("You become $200");
            case "Community Chest" ->
                    goToCommunityChest(scanner, players, playersFieldNumber, playersBudget, playersHouses, playersHotels, playersSpecialCards, playersInJail, communityChestCards, currentPlayer);
            case "Income Tax" -> {
                playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) - 200);
                System.out.println(currentPlayer + ", you paid Income Tax - $200");
            }
            case "Chance" ->
                    goToChance(scanner, players, playersFieldNumber, playersBudget, playersPurchases, playersHouses, playersHotels, playersSpecialCards, playersInJail, board, chanceCards, currentPlayer, playersRailroads, playersUtilities, haveColorSet, playersColorSet);
            case "Jail - only visit" -> {
                System.out.println(currentPlayer + ", you just visited the Jail");
                System.out.println("Next player!");
            }
            case "Free Parking" -> {
                System.out.println(currentPlayer + ", do nothing");
                System.out.println("Next player!");
            }
            case "Go To Jail" -> goToTheJail(currentPlayer, playersFieldNumber, playersInJail);
            case "Luxury tax" -> {
                playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) - 100);
                System.out.println(currentPlayer + ", you paid Luxury Tax - $100");
            }
        }
    }

    private static void goToChance(Scanner scanner, List<String> players, Map<String, Integer> playersFieldNumber, Map<String, Integer> playersBudget, Map<String, List<String>> playersPurchases, Map<String, Map<String, Integer>> playersHouses, Map<String, Map<String, Integer>> playersHotels, Map<String, List<String>> playersSpecialCards, Map<String, Boolean> playersInJail, String[] board, ArrayDeque<String> chanceCards, String currentPlayer, Map<String, List<String>> playersRailroads, Map<String, List<String>> playersUtilities, Map<String, Map<String, Boolean>> haveColorSet, Map<String, Map<String, Integer>> playersColorSet) {
        String chanceRow = chanceCards.poll();
        System.out.println("-----------");
        System.out.println("Chance card content: " + chanceRow);
        pressEnterToContinue(scanner);
        assert chanceRow != null;
        switchChance(chanceRow, playersFieldNumber,
                currentPlayer, playersBudget,
                playersSpecialCards.get(currentPlayer), playersInJail,
                players, playersPurchases.get(currentPlayer), board, scanner, playersHouses, playersHotels, playersRailroads, playersUtilities, haveColorSet, playersColorSet);
        chanceCards.offer(chanceRow);
    }

    private static void goToCommunityChest(Scanner scanner, List<String> players, Map<String, Integer> playersFieldNumber, Map<String, Integer> playersBudget, Map<String, Map<String, Integer>> playersHouses, Map<String, Map<String, Integer>> playersHotels, Map<String, List<String>> playersSpecialCards, Map<String, Boolean> playersInJail, ArrayDeque<String> communityChestCards, String currentPlayer) {
        String communityChestRow = communityChestCards.poll();
        System.out.println("-----------");
        System.out.println("Community chest card content: " + communityChestRow);
        pressEnterToContinue(scanner);
        assert communityChestRow != null;
        switchCommunityChest(communityChestRow, currentPlayer, playersBudget, playersFieldNumber,
                playersSpecialCards.get(currentPlayer), playersInJail, players, playersHouses, playersHotels);
        communityChestCards.offer(communityChestRow);
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

    public static void offerToSell(String currentPlayer, Map<String, Integer> playersBudget, List<String> properties, String[] board, Scanner scanner, Map<String, Map<String, Integer>> playersHotels, Map<String, Map<String, Integer>> playersHouses, Map<String, Integer> playersFieldNumber, Map<String, Map<String, Boolean>> haveColorSet, Map<String, Map<String, Integer>> playersColorSet, List<String> railroads, List<String> utilities) {

        System.out.println(currentPlayer + ", you don't have enough money!");
        System.out.println("You need to sell one of your properties, railroads or utilities.");

        pressEnterToContinue(scanner);
        sellEntity(currentPlayer, playersBudget, properties, board, scanner, playersHotels, playersHouses, playersFieldNumber, haveColorSet, playersColorSet, railroads, utilities);

    }

    public static void sellEntity(String currentPlayer, Map<String, Integer> playersBudget, List<String> properties, String[] board, Scanner scanner, Map<String, Map<String, Integer>> playersHotels, Map<String, Map<String, Integer>> playersHouses, Map<String, Integer> playersFieldNumber, Map<String, Map<String, Boolean>> haveColorSet, Map<String, Map<String, Integer>> playersColorSet, List<String> railroads, List<String> utilities) {
        int playerHotels = findSumOfAllHotelsOrHousesOfPlayer(currentPlayer, playersHotels);
        int playerHouses = findSumOfAllHotelsOrHousesOfPlayer(currentPlayer, playersHouses);

        if (playerHotels > 0) {
            sellHotel(scanner, currentPlayer, playersBudget, board, playersHotels, playerHotels);
        } else if (playerHouses > 0) {
            sellHouse(scanner, currentPlayer, playersBudget, board, playersHouses, playerHouses);
        } else if (properties.isEmpty() && railroads.isEmpty() && utilities.isEmpty()) {
            System.out.println(currentPlayer + ", you have nothing for sale!");
        } else {
            sellProperties(currentPlayer, playersBudget, properties, board, scanner, playersFieldNumber, haveColorSet, playersColorSet, railroads, utilities);
        }
    }

    public static void sellProperties(String currentPlayer, Map<String, Integer> playersBudget, List<String> properties, String[] board, Scanner scanner, Map<String, Integer> playersFieldNumber, Map<String, Map<String, Boolean>> haveColorSet, Map<String, Map<String, Integer>> playersColorSet, List<String> railroads, List<String> utilities) {

        printChoiceToSell(currentPlayer, properties, railroads, utilities);
        getChoiceToCell(currentPlayer, playersBudget, properties, board, scanner, playersFieldNumber, haveColorSet, playersColorSet, railroads, utilities);
    }

    private static void getChoiceToCell(String currentPlayer, Map<String, Integer> playersBudget, List<String> properties, String[] board, Scanner scanner, Map<String, Integer> playersFieldNumber, Map<String, Map<String, Boolean>> haveColorSet, Map<String, Map<String, Integer>> playersColorSet, List<String> railroads, List<String> utilities) {
        String number = validateEmptyString(scanner);

        int choice = Integer.parseInt(number);
        while (choice < 0 || choice > 3) {
            System.out.println("Enter correct number: ");
            choice = Integer.parseInt(scanner.nextLine());
        }

        switch (choice) {
            case 0:
                return;
            case 1:
                if (properties.isEmpty()) {
                    System.out.println("You don't have properties. Make another choice...");
                    sellProperties(currentPlayer, playersBudget, properties, board, scanner, playersFieldNumber, haveColorSet, playersColorSet, railroads, utilities);
                } else {
                    sellColorProperty(currentPlayer, playersBudget, properties, board, scanner, playersFieldNumber, haveColorSet, playersColorSet);
                }
                break;
            case 2:
                if (railroads.isEmpty()) {
                    System.out.println("You don't have railroads. Make another choice...");
                    sellProperties(currentPlayer, playersBudget, properties, board, scanner, playersFieldNumber, haveColorSet, playersColorSet, railroads, utilities);
                } else {
                    sellRailRoad(currentPlayer, playersBudget, board, scanner, railroads);
                }
                break;
            case 3:
                if (utilities.isEmpty()) {
                    System.out.println("You don't have utilities. Make another choice...");
                    sellProperties(currentPlayer, playersBudget, properties, board, scanner, playersFieldNumber, haveColorSet, playersColorSet, railroads, utilities);
                } else {
                    sellUtility(currentPlayer, playersBudget, board, scanner, utilities);
                }
                break;
        }
    }

    private static String validateEmptyString(Scanner scanner) {
        String number = scanner.nextLine();
        while (number.equals("")) {
            System.out.println("Enter valid number!");
        }
        return number;
    }

    private static void printChoiceToSell(String currentPlayer, List<String> properties, List<String> railroads, List<String> utilities) {
        System.out.printf("%s, you have %d properties, %d railroads and %d utilities.%n", currentPlayer, properties.size(), railroads.size(), utilities.size());
        System.out.println("To sell a property, press 1");
        System.out.println("To sell a railroad, press 2");
        System.out.println("To sell an utility, press 3");
        System.out.println("If you don't want to sell anything, press 0.");
    }

    public static void sellColorProperty(String currentPlayer, Map<String, Integer> playersBudget, List<String> properties, String[] board, Scanner scanner, Map<String, Integer> playersFieldNumber, Map<String, Map<String, Boolean>> haveColorSet, Map<String, Map<String, Integer>> playersColorSet) {
        System.out.println(currentPlayer + ", you have the following properties:");
        printListOfEntities(properties, board);
        System.out.print("Enter the number of the property you want to sell. ");
        System.out.println("If you don't want to sell anymore, press 0.");
        int numberOfProperty = enterNumberOfEntity(properties, scanner);

        if (numberOfProperty == 0) {
            System.out.println("You didn't sell anything...");
            return;
        }

        String propertyName = properties.get(numberOfProperty - 1);
        String[] currentProperty = findFieldByName(propertyName, board);
        int indexOfProperty = findIndexOfField(propertyName, board);

        removeEntity(currentPlayer, playersBudget, properties, board, numberOfProperty, currentProperty, indexOfProperty);

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

    private static void removeEntity(String currentPlayer, Map<String, Integer> playersBudget, List<String> properties, String[] board, int numberOfProperty, String[] currentProperty, int indexOfProperty) {
        properties.remove(numberOfProperty - 1);
        playersBudget.put(currentPlayer, (int) (playersBudget.get(currentPlayer) + Integer.parseInt(currentProperty[2]) * 0.5));
        currentProperty[1] = "for rent";
        currentProperty[currentProperty.length - 1] = "none";
        board[indexOfProperty] = String.join(", ", currentProperty);
    }

    private static int enterNumberOfEntity(List<String> properties, Scanner scanner) {
        int numberOfProperty = scanner.nextInt();
        while (numberOfProperty < 0 || numberOfProperty > properties.size()) {
            System.out.println("Enter correct number of property:");
            numberOfProperty = scanner.nextInt();
        }
        return numberOfProperty;
    }

    private static void printListOfEntities(List<String> properties, String[] board) {
        for (int j = 0; j < properties.size(); j++) {
            String current = properties.get(j);
            int index = findIndexOfField(current, board);
            int price = (int) (Integer.parseInt(board[index].split(", ")[2]) * 0.5);
            System.out.println(j + 1 + ". " + current + " -> $" + price);
        }
    }

    public static void sellRailRoad(String currentPlayer, Map<String, Integer> playersBudget, String[] board, Scanner scanner, List<String> railroads) {
        System.out.println(currentPlayer + ", you have the following railroads:");
        printListOfEntities(railroads, board);
        System.out.print("Enter the number of the railroad you want to sell. ");
        System.out.println("If you don't want to sell anymore, press 0.");
        int numberOfProperty = enterNumberOfEntity(railroads, scanner);

        if (numberOfProperty == 0) {
            System.out.println("You didn't sell anything...");
            return;
        }

        String propertyName = railroads.get(numberOfProperty - 1);
        String[] currentProperty = findFieldByName(propertyName, board);
        int indexOfProperty = findIndexOfField(propertyName, board);

        removeEntity(currentPlayer, playersBudget, railroads, board, numberOfProperty, currentProperty, indexOfProperty);

        System.out.println("You sold a railroad '" + propertyName + "' for $" + (int) (Integer.parseInt(currentProperty[2]) * 0.5) + ".");
        scanner.nextLine();
    }

    public static void sellUtility(String currentPlayer, Map<String, Integer> playersBudget, String[] board, Scanner scanner, List<String> utilities) {
        System.out.println(currentPlayer + ", you have the following utilities:");
        printListOfEntities(utilities, board);
        System.out.print("Enter the number of the utility you want to sell. ");
        System.out.println("If you don't want to sell anymore, press 0.");
        int numberOfProperty = enterNumberOfEntity(utilities, scanner);

        if (numberOfProperty == 0) {
            System.out.println("You didn't sell anything...");
            return;
        }

        String propertyName = utilities.get(numberOfProperty - 1);
        String[] currentProperty = findFieldByName(propertyName, board);
        int indexOfProperty = findIndexOfField(propertyName, board);

        removeEntity(currentPlayer, playersBudget, utilities, board, numberOfProperty, currentProperty, indexOfProperty);

        System.out.println("You sold a utility '" + propertyName + "' for $" + (int) (Integer.parseInt(currentProperty[2]) * 0.5) + ".");
        scanner.nextLine();
    }

    public static String[] findFieldByName(String name, String[] board) {
        String result = "";
        for (String currentPlayer : board) {
            String[] currentField = currentPlayer.split(", ");
            if (currentField[0].equals(name)) {
                result = String.join(", ", currentField);
            }
        }
        return result.split(", ");
    }

    public static int findIndexOfField(String name, String[] board) {
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

    public static void printGoTroughTheStart(String currentPlayer, Scanner scanner) {
        System.out.println("-----------");
        System.out.println(currentPlayer + ", you passed the Start field. You get $200.");
        pressEnterToContinue(scanner);
    }

    public static void pressEnterToContinue(Scanner scanner) {
        System.out.println("Press 'Enter' to continue...");
        scanner.nextLine();

    }

    public static int checkIndexForNextPlayer(List<String> players, int index) {
        index++;
        index = checkForIndexScroll(players, index);
        return index;
    }

    public static int checkIndexForNotEnoughMoney(Scanner scanner, Map<String, Integer> playersBudget, Map<String, List<String>> playersPurchases, Map<String, Map<String, Integer>> playersHouses, Map<String, Map<String, Integer>> playersHotels, List<String> players, String[] board, int index, String currentPlayer, Map<String, Integer> playersFieldNumber, Map<String, Map<String, Boolean>> haveColorSet, Map<String, Map<String, Integer>> playersColorSet, List<String> railroads, List<String> utilities) {
        String TEXT_RESET = "\u001B[0m";
        String TEXT_RED = "\u001B[31m";

        while (playersBudget.get(currentPlayer) < 0) {

            offerToSell(currentPlayer, playersBudget, playersPurchases.get(currentPlayer), board, scanner, playersHotels, playersHouses, playersFieldNumber, haveColorSet, playersColorSet, railroads, utilities);

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

    public static void stayInJail(int moveNumber, Scanner scanner, Map<String, Integer> playersBudget, Map<String, Integer> playersJailCounter, Map<String, List<String>> playersSpecialCards, Map<String, Boolean> playersInJail, String currentPlayer) {
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
                moveNumber = rollTheDice();
            }

            if (moveNumber == 12) {
                rollTwelveInJail(scanner, playersJailCounter, playersInJail, currentPlayer);
            } else {
                rollThreeTimesInJail(scanner, playersBudget, playersJailCounter, playersInJail, currentPlayer);
                if (playersInJail.get(currentPlayer)) {
                    System.out.println(TEXT_RED + "No, try again next time!" + TEXT_RESET);
                }
            }
        }
    }

    public static void usePlayerCardInJail(Map<String, List<String>> playersSpecialCards, Map<String, Boolean> playersInJail, String currentPlayer) {
        playersInJail.put(currentPlayer, false);
        playersSpecialCards.get(currentPlayer).remove(0);
    }

    public static void rollThreeTimesInJail(Scanner scanner, Map<String, Integer> playersBudget, Map<String, Integer> playersJailCounter, Map<String, Boolean> playersInJail, String currentPlayer) {

        String TEXT_RED = "\u001B[31m";
        String TEXT_GREEN = "\u001B[32m";
        String TEXT_RESET = "\u001B[0m";

        if (playersJailCounter.get(currentPlayer) >= 2) {

            System.out.println(TEXT_RED + currentPlayer + ", you failed to roll a 12 three times.");
            System.out.println("You have to pay $150 and get out of jail." + TEXT_RESET);
            pressEnterToContinue(scanner);

            playersInJail.put(currentPlayer, false);
            playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) - 150);
            playersJailCounter.put(currentPlayer, 0);

            System.out.println(TEXT_GREEN + currentPlayer + ", you paid $150 and you are out of jail." + TEXT_RESET);
            pressEnterToContinue(scanner);
        } else {
            playersJailCounter.put(currentPlayer, playersJailCounter.get(currentPlayer) + 1);
        }
    }

    public static void rollTwelveInJail(Scanner scanner, Map<String, Integer> playersJailCounter, Map<String, Boolean> playersInJail, String currentPlayer) {
        String TEXT_GREEN = "\u001B[32m";
        String TEXT_RESET = "\u001B[0m";
        playersInJail.put(currentPlayer, false);
        playersJailCounter.put(currentPlayer, 0);
        System.out.println(TEXT_GREEN + currentPlayer + ", you roll 12 and you're out of jail" + TEXT_RESET);
        pressEnterToContinue(scanner);
    }

    public static int checkForIndexScroll(List<String> players, int index) {
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
        int numberForColorGroup = switch (playersFieldNumber.get(currentPlayer)) {
            case 1, 3, 37, 39 -> 2;
            case 6, 8, 9, 11, 13, 14, 16, 18, 19, 21, 23, 24, 26, 27, 29, 31, 32, 34 -> 3;
            default -> 0;
        };
        Map<String, Boolean> temp = haveColorGroup.get(currentPlayer);
        temp.put(colorType, false);
        if (playersColorFields.get(currentPlayer).get(colorType) == numberForColorGroup) {
            temp.put(colorType, true);
        }
        haveColorGroup.put(currentPlayer, temp);
    }

    public static String switchColor(Map<String, Integer> playersFieldNumber, String currentPlayer) {
        return switch (playersFieldNumber.get(currentPlayer)) {
            case 1, 3 -> "brown";
            case 6, 8, 9 -> "light blue";
            case 11, 13, 14 -> "pink";
            case 16, 18, 19 -> "orange";
            case 21, 23, 24 -> "red";
            case 26, 27, 29 -> "yellow";
            case 31, 32, 34 -> "green";
            case 37, 39 -> "dark blue";
            default -> "";

        };
    }

    public static void printWelcome(Scanner scanner) {
        String TEXT_GREEN = "\u001B[32m";
        String TEXT_RESET = "\u001B[0m";
        String TEXT_RED = "\u001B[31m";
        System.out.println(TEXT_GREEN);
        System.out.println("    *  *  *  *  *  *");
        System.out.println("  *                  *" + TEXT_RESET);
        System.out.println(TEXT_RED + "* WELCOME TO MONOPOLY! *" + TEXT_RESET);
        System.out.println(TEXT_GREEN + "  *                  *");
        System.out.println("    *  *  *  *  *  *");
        System.out.println(TEXT_RESET);
        System.out.println("Press 'Enter' to show the game board...");
        scanner.nextLine();
    }

    public static void printBoard() {

        String TEXT_RED = "\u001B[31m";
        String TEXT_GREEN = "\u001B[32m";
        String TEXT_RESET = "\u001B[0m";
        System.out.println(TEXT_GREEN + "|-----------------------|---------------|-----------|---------|----------|-----------|-------------|----------|-----------|-------------|-----------------------|");
        System.out.println("|                       | Mediterranean | Community |  Baltic |  Income  |  Reading  |  Oriental   |  Chance  |  Vermont  | Connecticut |                       |");
        System.out.println("|         START  -->    |     Avenue    |   Chest   |  Avenue |    Tax   |  RailRoad |   Avenue    |          |  Avenue   |    Avenue   |   Jail - only visit   |");
        System.out.println("|-----------------------|---------------|-----------|---------|----------|-----------|-------------|----------|-----------|-------------|-----------------------|");
        System.out.println("|        Boardwalk      |                                                                                                               |   St. Charles Place   |");
        System.out.println("|       Luxury tax      |                                                                                                               |    Electric Company   |");
        System.out.println("|       Park Place      |  " + TEXT_RESET + TEXT_RED + "    **      **    *********    ***     **    *********    *********    *********    **         **      **  " + TEXT_RESET + TEXT_GREEN + "  |     States Avenue     |");
        System.out.println("|         Chance        |  " + TEXT_RESET + TEXT_RED + "    ***    ***    **     **    ****    **    **     **    **     **    **     **    **          **    **   " + TEXT_RESET + TEXT_GREEN + "  |    Virginia Avenue    |");
        System.out.println("|  Short line Railroad  |  " + TEXT_RESET + TEXT_RED + "    ****  ****    **     **    ** **   **    **     **    **     **    **     **    **           **  **   " + TEXT_RESET + TEXT_GREEN + "   | Pennsylvania Railroad |");
        System.out.println("|  Pennsylvania Avenue  |  " + TEXT_RESET + TEXT_RED + "    **  **  **    **     **    **  **  **    **     **    *********    **     **    **             **     " + TEXT_RESET + TEXT_GREEN + "   |    St. James Place    |");
        System.out.println("|    Community Chest    |  " + TEXT_RESET + TEXT_RED + "    **      **    **     **    **   *****    **     **    **           **     **    **             **     " + TEXT_RESET + TEXT_GREEN + "   |    Community Chest    |");
        System.out.println("| North Carolina Avenue |  " + TEXT_RESET + TEXT_RED + "    **      **    *********    **     ***    *********    **           *********    *********      **     " + TEXT_RESET + TEXT_GREEN + "   |    Tennessee Avenue   |");
        System.out.println("|     Pacific Avenue    |                                                                                                               |    New York Avenue    |");
        System.out.println("|-----------------------|---------------|-----------|---------|----------|-----------|-------------|----------|-----------|-------------|-----------------------|");
        System.out.println("|      Go To Jail       |    Marvin     |    Water  | Ventnor | Atlantic |  B. & O.  |  Illinois   | Indiana  |  Chance   |   Kentucky  |      Free Parking     |");
        System.out.println("|                       |    Gardens    |    works  | Avenue  |  Avenue  | Railroad  |   Avenue    |  Avenue  |           |    Avenue   |                       |");
        System.out.println("|-----------------------|---------------|-----------|---------|----------|-----------|-------------|----------|-----------|-------------|-----------------------|" + TEXT_RESET);
        System.out.println();
        System.out.println();
    }
}

