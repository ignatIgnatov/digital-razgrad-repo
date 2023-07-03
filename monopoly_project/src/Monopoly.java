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

        String[] chance = arrayFromFile("Chance.txt", 15);
        ArrayDeque<String> chanceCards = convertArrayToDeque(chance);

        printWelcome();
        choiceStartOfTheGame(scanner);

        int numberOfPlayers = enterNumberOfPlayers(scanner);

        fillPlayerNames(numberOfPlayers, players, scanner);

        for (String player : players) {
            playersBudget.put(player, 1500);
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
                        playersHotels, players, board, index, currentPlayer, playersFieldNumber, haveColorGroup, playersColorFields, playersRailroads, playersUtilities);

                String TEXT_YELLOW = "\u001B[33m";
                String TEXT_RESET = "\u001B[0m";


                if (numberOfPlayers != players.size() && players.size() == 1) {
                    String winner = players.get(0);
                    printPlayerStats(winner, playersBudget, playersProperties, playersSpecialCards, playersHouses, playersHotels, playersRailroads, playersUtilities);
                    System.out.println();
                    System.out.println(TEXT_YELLOW + "***** " + winner + ", YOU WIN! *****" + TEXT_RESET);
                    System.out.println();
                    return;
                } else if (numberOfPlayers == 1 && players.isEmpty()) {
                    System.out.println("-----------");
                    System.out.println("GAME OVER!");
                    System.out.println(currentPlayer + ", you lost the game!");
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

            printPlayerStats(currentPlayer, playersBudget, playersProperties, playersSpecialCards, playersHouses, playersHotels, playersRailroads, playersUtilities);

            switch (playersFieldNumber.get(currentPlayer)) {
                case 1, 3, 6, 8, 9, 11, 13, 14, 16, 18, 19, 21, 23, 24, 26, 27, 29, 31, 32, 34, 37, 39 ->
                        buyColorField(currentField, scanner, currentPlayer, playersBudget, playersProperties,
                                playersSpecialCards, playersFieldNumber, board, playersHouses, playersHotels,
                                playersRailroads, playersUtilities, haveColorGroup, playersColorFields);

                case 5, 15, 25, 35 ->
                        buyCompanyField(currentField, scanner, currentPlayer, playersBudget, playersRailroads, playersFieldNumber, board);
                case 12, 28 ->
                        buyCompanyField(currentField, scanner, currentPlayer, playersBudget, playersUtilities, playersFieldNumber, board);
                default -> {
                    switch (currentField[0]) {
                        case "START" -> System.out.println("You become $200");
                        case "Community Chest" -> {
                            String communityChestRow = communityChestCards.poll();
                            System.out.println("-----------");
                            System.out.println("Community chest card content: " + communityChestRow);
                            pressEnterToContinue(scanner);
                            assert communityChestRow != null;
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
                                    playersSpecialCards.get(currentPlayer).add(communityChestRow);
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
                                        collectMoneyFromEveryPlayer(currentPlayer, playersBudget, players);
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
                            communityChestCards.offer(communityChestRow);
                        }
                        case "Income Tax" -> {
                            playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) - 200);
                            System.out.println(currentPlayer + ", you paid Income Tax - $200");
                        }
                        case "Chance" -> {
                            String chanceRow = chanceCards.poll();
                            System.out.println("-----------");
                            System.out.println("Chance card content: " + chanceRow);
                            pressEnterToContinue(scanner);
                            assert chanceRow != null;
                            switch (chanceRow) {
                                case "Advance to Boardwalk":
                                    playersFieldNumber.put(currentPlayer, 39);
                                    currentField = board[playersFieldNumber.get(currentPlayer)].split(", ");
                                    System.out.println(currentPlayer + ", you went to Boardwalk.");
                                    buyColorField(currentField, scanner, currentPlayer, playersBudget, playersProperties, playersSpecialCards, playersFieldNumber,
                                            board, playersHouses, playersHotels, playersRailroads, playersUtilities, haveColorGroup, playersColorFields);
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
                                    buyColorField(currentField, scanner, currentPlayer, playersBudget, playersProperties, playersSpecialCards, playersFieldNumber,
                                            board, playersHouses, playersHotels, playersRailroads, playersUtilities, haveColorGroup, playersColorFields);
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
                                    buyColorField(currentField, scanner, currentPlayer, playersBudget, playersProperties, playersSpecialCards, playersFieldNumber,
                                            board, playersHouses, playersHotels, playersRailroads, playersUtilities, haveColorGroup, playersColorFields);
                                    break;
                                case "Advance to the nearest Railroad. If unowned, you may buy it from the Bank. If owned, " +
                                        "pay the rental to which they are otherwise entitled":
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
                                    buyCompanyField(currentField, scanner, currentPlayer, playersBudget, playersRailroads, playersFieldNumber,
                                            board);
                                    break;
                                case "Advance token to nearest Utility. If unowned, you may buy it from the Bank. If owned, pay the rental to which they are otherwise entitled":
                                    currentPosition = playersFieldNumber.get(currentPlayer);
                                    if (currentPosition == 22) {
                                        playersFieldNumber.put(currentPlayer, 28);
                                        System.out.println(currentPlayer + ", you went to Water works");
                                    } else {
                                        playersFieldNumber.put(currentPlayer, 12);
                                        System.out.println(currentPlayer + ", you went to Electric Company");
                                    }
                                    currentField = board[playersFieldNumber.get(currentPlayer)].split(", ");
                                    buyCompanyField(currentField, scanner, currentPlayer, playersBudget, playersUtilities,
                                            playersFieldNumber, board);
                                    break;
                                case "Bank pays you dividend of $50":
                                    playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) + 50);
                                    System.out.println(currentPlayer + ", you received $50");
                                    break;
                                case "Get Out of Jail Free":
                                    playersSpecialCards.get(currentPlayer).add(chanceRow);
                                    System.out.println(currentPlayer + ", you added the card 'Get Out of Jail Free' to your Special Card Collection");
                                    break;
                                case "Go Back 3 Spaces and roll the dice again":
                                    playersFieldNumber.put(currentPlayer, playersFieldNumber.get(currentPlayer) - 3);
                                    System.out.println(currentPlayer + ", you moved back three spaces");
                                    continue;
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
                                    playersFieldNumber.put(currentPlayer, 5);
                                    playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) + 200);
                                    System.out.println(currentPlayer + ", you move to Reading Railroad");
                                    System.out.println("You passed a Start and get $200");
                                    currentField = board[playersFieldNumber.get(currentPlayer)].split(", ");
                                    buyCompanyField(currentField, scanner, currentPlayer, playersBudget, playersRailroads,
                                            playersFieldNumber, board);
                                    break;
                                case "You have been elected Chairman of the Board. Pay each player $50":
                                    payEachPlayer(currentPlayer, playersBudget, players);
                                    break;
                                case "Your building loan matures. Collect $150":
                                    playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) + 150);
                                    System.out.println(currentPlayer + ", you collect $150");
                                    break;
                            }
                            chanceCards.offer(chanceRow);
                        }
                        case "Jail - only visit" ->
                            System.out.println(currentPlayer + ", you just visited the Jail...");
                        case "Free Parking" ->
                            System.out.println(currentPlayer + ", you are in free parking. Stay here and do nothing...");
                        case "Go To Jail" -> goToTheJail(currentPlayer, playersFieldNumber, playersInJail);
                        case "Luxury tax" -> {
                            playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) - 100);
                            System.out.println(currentPlayer + ", you paid Luxury Tax - $100");
                        }
                    }
                }
            }

            printPlayerStats(currentPlayer, playersBudget, playersProperties, playersSpecialCards, playersHouses, playersHotels, playersRailroads, playersUtilities);
            index = checkIndexForNextPlayer(players, index);
            System.out.println("Next player!");
            pressEnterToContinue(scanner);
        }
    }

    public static int enterNumberOfPlayers(Scanner scanner) {
        System.out.println("Enter a number of players (1-4): ");
        String number = validateDigit(scanner);

        int numberOfPlayers = Integer.parseInt(number);
        while (numberOfPlayers < 1 || numberOfPlayers > 4) {
            System.out.println("Enter valid number of players (1-4): ");
            numberOfPlayers = Integer.parseInt(validateDigit(scanner));
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

    public static void printPlayerStats(String name, Map<String, Integer> playersBudget, Map<String, List<String>> properties, Map<String, List<String>> specialCards, Map<String, Map<String, Integer>> playersHouses, Map<String, Map<String, Integer>> playersHotels, Map<String, List<String>> railroads, Map<String, List<String>> utilities) {
        String TEXT_GREEN = "\u001B[32m";
        String TEXT_RESET = "\u001B[0m";

        String playersProperties = properties.get(name).size() > 0 ? "Properties: " + String.join(", ", properties.get(name)) : "Properties: none";
        String playerRailroads = railroads.get(name).size() > 0 ? "RailRoads: " + String.join(", ", railroads.get(name)) : "Railroads: none";
        String playerUtilities = utilities.get(name).size() > 0 ? "Utilities: " + String.join(", ", utilities.get(name)) : "Utilities: none";
        String playerCards = specialCards.get(name).size() > 0 ? "Special cards: " + String.join(", ", specialCards.get(name)) : "Special cards: none";

        StringBuilder sb = new StringBuilder();
        sb.append("----------------------").append(System.lineSeparator())
                .append(String.format("%s's statistic:", name)).append(System.lineSeparator())
                .append(String.format("Money: " + "$" + playersBudget.get(name))).append(System.lineSeparator())
                .append(playersProperties).append(System.lineSeparator())
                .append(playerRailroads).append(System.lineSeparator())
                .append(playerUtilities).append(System.lineSeparator())
                .append(playerCards).append(System.lineSeparator())
                .append("----------------------");

        System.out.println(TEXT_GREEN + sb + TEXT_RESET);


        if (findSumOfAllBuildingsOfPlayer(name, playersHouses) > 0) {
            printFieldsWithHouses(name, playersHouses);
        }
        if (findSumOfAllBuildingsOfPlayer(name, playersHotels) > 0) {
            printFieldsWithHotels(name, playersHotels);
        }
    }

    private static void printFieldsWithHouses(String currentPlayer, Map<String, Map<String, Integer>> playersHouses) {
        String TEXT_BLUE = "\u001B[34m";
        String TEXT_RESET = "\u001B[0m";
        StringBuilder sb = new StringBuilder();

        sb.append(currentPlayer).append("'s houses:").append(System.lineSeparator());
        Map<String, Integer> houses = playersHouses.get(currentPlayer);
        for (Map.Entry<String, Integer> entry : houses.entrySet()) {
            if (entry.getValue() > 0) {
                sb.append(entry.getKey()).append(" -> ").append(entry.getValue()).append(System.lineSeparator());
            }
        }
        sb.append("----------------------");
        System.out.println(TEXT_BLUE + sb + TEXT_RESET);
    }

    private static void printFieldsWithHotels(String currentPlayer, Map<String, Map<String, Integer>> playersHotels) {
        String TEXT_PURPLE = "\u001B[35m";
        String TEXT_RESET = "\u001B[0m";
        StringBuilder sb = new StringBuilder();

        sb.append(currentPlayer).append("'s hotels:").append(System.lineSeparator());
        Map<String, Integer> hotels = playersHotels.get(currentPlayer);
        for (Map.Entry<String, Integer> entry : hotels.entrySet()) {
            if (entry.getValue() > 0) {
                sb.append(entry.getKey()).append(" -> ").append(entry.getValue()).append(System.lineSeparator());
            }
        }
        sb.append("----------------------");
        System.out.println(TEXT_PURPLE + sb + TEXT_RESET);
    }

    public static ArrayDeque<String> convertArrayToDeque(String[] cards) {
        List<Integer> temp = mixCards(cards);
        ArrayDeque<String> result = new ArrayDeque<>();
        for (Integer integer : temp) {
            result.offer(cards[integer]);
        }
        return result;
    }

    public static List<Integer> mixCards(String[] cards) {
        List<Integer> temp = new ArrayList<>();
        Random random = new Random();
        while (temp.size() != cards.length) {
            int number = random.nextInt(0, cards.length);
            if (!temp.contains(number)) {
                temp.add(number);
            }
        }
        return temp;
    }

    public static void buyCompanyField(String[] currentField, Scanner scanner, String currentPlayer,
                                       Map<String, Integer> playersBudget, Map<String, List<String>> purchase,
                                       Map<String, Integer> playersFieldNumber, String[] board) {

        String TEXT_YELLOW = "\u001B[33m";
        String TEXT_RESET = "\u001B[0m";

        printFieldCard(createCompanyFieldCard(currentField), TEXT_YELLOW, TEXT_RESET);

        if (currentField[1].equals("for rent") && !currentPlayer.equals(currentField[currentField.length - 1])) {
            if (enterAnswer(currentField, scanner).equals("yes")) {
                if (moneyValidation(playersBudget.get(currentPlayer), Integer.parseInt(currentField[2]))) {
                    buyCompanyProcess(currentField, currentPlayer, playersBudget, purchase, playersFieldNumber, board);
                    System.out.println(currentPlayer + ", you bought " + currentField[0]);
                    printFieldCard(createCompanyFieldCard(currentField), TEXT_YELLOW, TEXT_RESET);
                }
            }
        } else if (!currentPlayer.equals(currentField[currentField.length - 1])) {
            payCompanyRent(currentField, scanner, currentPlayer, playersBudget, purchase, playersFieldNumber);
        } else {
            System.out.printf("%s, '%s' field is yours.%n", currentPlayer, currentField[0]);
            pressEnterToContinue(scanner);
        }
    }

    public static void payCompanyRent(String[] currentField, Scanner scanner, String currentPlayer, Map<String, Integer> playersBudget, Map<String, List<String>> purchase, Map<String, Integer> playersFieldNumber) {
        String fieldOwner = currentField[currentField.length - 1];
        int fieldNumber = playersFieldNumber.get(currentPlayer);
        int annuity = getCompanyAnnuity(purchase, fieldOwner, fieldNumber);
        payAnnuity(currentPlayer, playersBudget, fieldOwner, annuity);
        pressEnterToContinue(scanner);
    }

    public static int getCompanyAnnuity(Map<String, List<String>> purchase, String fieldOwner, int fieldNumber) {
        int annuity = 0;
        switch (fieldNumber) {
            case 5, 15, 25, 35 -> {
                if (purchase.get(fieldOwner).size() == 1) {
                    annuity = 25;
                } else if (purchase.get(fieldOwner).size() == 2) {
                    annuity = 50;
                } else if (purchase.get(fieldOwner).size() == 3) {
                    annuity = 100;
                } else if (purchase.get(fieldOwner).size() == 4) {
                    annuity = 200;
                }
            }
            case 12, 28 -> {
                if (purchase.get(fieldOwner).size() == 1) {
                    annuity = 25;
                } else if (purchase.get(fieldOwner).size() == 2) {
                    annuity = 75;
                }
            }
        }
        return annuity;
    }

    public static void buyColorField(String[] currentField, Scanner scanner, String currentPlayer,
                                     Map<String, Integer> playersBudget, Map<String, List<String>> playersPurchases,
                                     Map<String, List<String>> playersSpecialCards, Map<String, Integer> playersFieldNumber,
                                     String[] board, Map<String, Map<String, Integer>> playersHouses, Map<String, Map<String, Integer>> playersHotels,
                                     Map<String, List<String>> playersRailroads, Map<String, List<String>> playersUtilities,
                                     Map<String, Map<String, Boolean>> haveColorGroup, Map<String, Map<String, Integer>> playersColorFields) {

        String TEXT_CYAN = "\u001B[36m";
        String TEXT_RESET = "\u001B[0m";

        printFieldCard(createColorFieldCard(currentField), TEXT_CYAN, TEXT_RESET);

        if (currentField[1].equals("for rent")) {
            if (enterAnswer(currentField, scanner).equals("yes")) {
                if (moneyValidation(playersBudget.get(currentPlayer), Integer.parseInt(currentField[2]))) {

                    buyColorFieldProcess(currentField, currentPlayer, playersBudget, playersPurchases, playersFieldNumber, board);
                    String colorType = switchColor(playersFieldNumber, currentPlayer);
                    setColor(playersColorFields, currentPlayer, colorType);
                    checkForColorGroup(playersColorFields, currentPlayer, colorType, haveColorGroup, playersFieldNumber);
                    initializeBuildings(currentField, currentPlayer, playersHouses, playersHotels);

                    System.out.println(currentPlayer + ", you bought " + currentField[0]);
                    printFieldCard(createColorFieldCard(currentField), TEXT_CYAN, TEXT_RESET);
                }
            }
        } else {
            if (!currentPlayer.equals(currentField[currentField.length - 1])) {
                payColorRent(currentField, currentPlayer, playersBudget, playersFieldNumber, playersHouses, playersHotels, haveColorGroup);
            } else {
                String color = switchColor(playersFieldNumber, currentPlayer);
                if (haveColorGroup.get(currentPlayer).get(color)) {
                    String TEXT_BLUE = "\u001B[34m";

                    Map<String, Integer> houses = playersHouses.get(currentPlayer);
                    Map<String, Integer> hotels = playersHotels.get(currentPlayer);

                    if (houses.get(currentField[0]) < 4) {
                        printMessageForColorGroup(currentField, currentPlayer, playersHouses, playersHotels);
                        System.out.println("Do you want to buy a house?");

                        if (enterYesOrNo(scanner).equals("yes")) {
                            buyHouse(currentField, currentPlayer, playersBudget, playersHouses, houses);
                            System.out.println(TEXT_BLUE + currentPlayer + ", you bought a house." + TEXT_RESET);
                            printPlayerStats(currentPlayer, playersBudget, playersPurchases, playersSpecialCards, playersHouses, playersHotels, playersRailroads, playersUtilities);
                        }

                    } else {
                        if (hotels.get(currentField[0]) == 0) {
                            System.out.println(currentPlayer + ", this field has 4 houses. Do you want to buy a hotel?");
                            if (enterYesOrNo(scanner).equals("yes")) {
                                buyHotel(currentField, currentPlayer, playersBudget, playersHouses, playersHotels, hotels);
                                System.out.println(TEXT_BLUE + currentPlayer + ", you bought a hotel" + TEXT_RESET);
                                printPlayerStats(currentPlayer, playersBudget, playersPurchases, playersSpecialCards, playersHouses, playersHotels, playersRailroads, playersUtilities);
                            }
                        }
                    }
                } else {
                    System.out.printf("%s, '%s' field is yours.%n", currentPlayer, currentField[0]);
                    pressEnterToContinue(scanner);
                }
            }
        }
    }



    private static void printMessageForColorGroup(String[] currentField, String currentPlayer, Map<String, Map<String, Integer>> playersHouses, Map<String, Map<String, Integer>> playersHotels) {
        String TEXT_BLUE = "\u001B[34m";
        String TEXT_RESET = "\u001B[0m";

        System.out.printf("%s field is yours.%n", currentField[0]);
        System.out.println(TEXT_BLUE + currentPlayer + ", you have a color group. Now you can build houses and hotels..." + TEXT_RESET);
        System.out.printf("You have %d houses and %d hotels on this field.%n",
                playersHouses.get(currentPlayer).get(currentField[0]),
                playersHotels.get(currentPlayer).get(currentField[0]));
    }

    private static void buyHouse(String[] currentField, String currentPlayer, Map<String, Integer> playersBudget, Map<String, Map<String, Integer>> playersHouses, Map<String, Integer> houses) {
        int housePrice = Integer.parseInt(currentField[10]);
        houses.put(currentField[0], houses.get(currentField[0]) + 1);
        playersHouses.put(currentPlayer, houses);
        playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) - housePrice);
    }

    private static void buyHotel(String[] currentField, String currentPlayer, Map<String, Integer> playersBudget, Map<String, Map<String, Integer>> playersHouses, Map<String, Map<String, Integer>> playersHotels, Map<String, Integer> hotels) {
        Map<String, Integer> houses;
        int hotelPrice = Integer.parseInt(currentField[11]);
        hotels.put(currentField[0], hotels.get(currentField[0]) + 1);
        playersHotels.put(currentPlayer, hotels);
        houses = playersHouses.get(currentPlayer);

        houses.put(currentField[0], 0);
        playersHouses.put(currentPlayer, houses);
        playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) - hotelPrice);
    }

    private static void payColorRent(String[] currentField, String currentPlayer, Map<String, Integer> playersBudget, Map<String, Integer> playersFieldNumber, Map<String, Map<String, Integer>> playersHouses, Map<String, Map<String, Integer>> playersHotels, Map<String, Map<String, Boolean>> haveColorGroup) {
        String fieldOwner = currentField[currentField.length - 1];
        int housesCount = playersHouses.get(fieldOwner).get(currentField[0]);
        int hotelsCount = playersHotels.get(fieldOwner).get(currentField[0]);
        int annuity = getColorAnnuity(currentField, housesCount, hotelsCount, haveColorGroup, currentPlayer, playersFieldNumber);
        payAnnuity(currentPlayer, playersBudget, fieldOwner, annuity);
    }

    private static void initializeBuildings(String[] currentField, String currentPlayer, Map<String, Map<String, Integer>> playersHouses, Map<String, Map<String, Integer>> playersHotels) {
        Map<String, Integer> houses = playersHouses.get(currentPlayer);
        Map<String, Integer> hotels = playersHotels.get(currentPlayer);
        houses.put(currentField[0], 0);
        hotels.put(currentField[0], 0);
        playersHouses.put(currentPlayer, houses);
        playersHotels.put(currentPlayer, hotels);
    }

    private static void printHousesInformation(String currentPlayer, int playerHouses) {
        System.out.println(currentPlayer + ", you have " + playerHouses + " houses. You have to sell one of them...");
        System.out.println("These are your houses: ");
    }

    private static void printHotelsInformation(String currentPlayer, int playerHotels) {
        System.out.println(currentPlayer + ", you have " + playerHotels + " hotels. You have to sell one of them");
        System.out.println("These are your hotels: ");
    }

    public static void sellBuildingsOperation(String currentPlayer, Map<String, Integer> playersBudget, String[] board, Map<String, Map<String, Integer>> buildings, int numberOfField) {

        List<String> fields = createListOfFields(currentPlayer, buildings);
        String nameOfField = fields.get(numberOfField - 1);

        Map<String, Integer> temp = buildings.get(currentPlayer);
        temp.put(nameOfField, temp.get(nameOfField) - 1);
        buildings.put(currentPlayer, temp);

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
        String number = validateDigit(scanner);
        int numberOfTheField = Integer.parseInt(number);

        while (numberOfTheField <= 0 || numberOfTheField > fields.size()) {
            System.out.println("Enter the number of the field whose house or hotel you want to sell: ");
            numberOfTheField = Integer.parseInt(validateDigit(scanner));
        }
        return numberOfTheField;
    }

    public static int findSumOfAllBuildingsOfPlayer(String currentPlayer, Map<String, Map<String, Integer>> housesOrHotels) {
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

    public static void buyColorFieldProcess(String[] currentField, String currentPlayer, Map<String, Integer> playersBudget, Map<String, List<String>> playersPurchases, Map<String, Integer> playersFieldNumber, String[] board) {
        currentField[1] = "Sold";
        currentField[currentField.length - 1] = currentPlayer;
        playersPurchases.get(currentPlayer).add(currentField[0]);
        playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) - Integer.parseInt(currentField[2]));

        board[playersFieldNumber.get(currentPlayer)] = String.join(", ", currentField);
    }

    public static void buyCompanyProcess(String[] currentField, String currentPlayer, Map<String, Integer> playersBudget, Map<String, List<String>> playersPurchases, Map<String, Integer> playersFieldNumber, String[] board) {
        currentField[1] = "Sold";
        currentField[currentField.length - 1] = currentPlayer;
        playersPurchases.get(currentPlayer).add(currentField[0]);
        playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) - Integer.parseInt(currentField[2]));

        board[playersFieldNumber.get(currentPlayer)] = String.join(", ", currentField);
    }

    public static String enterAnswer(String[] currentField, Scanner scanner) {
        System.out.println("Do you want to buy " + currentField[0] + "?");
        return enterYesOrNo(scanner);
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

    private static void collectMoneyFromEveryPlayer(String currentPlayer, Map<String, Integer> playersBudget, List<String> players) {
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

        int hotelsCount = findSumOfAllBuildingsOfPlayer(currentPlayer, playersHotels);
        int housesCount = findSumOfAllBuildingsOfPlayer(currentPlayer, playersHouses);

        if (hotelsCount > 0 || housesCount > 0) {
            int sum = housesCount * housePrice + hotelsCount * hotelPrice;
            playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) - sum);

            System.out.printf("%s, you repaired %d houses and %d hotels%n", currentPlayer, housesCount, hotelsCount);
            System.out.println("You paid $" + sum + ".");

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

    public static void offerToSell(String currentPlayer, Map<String, Integer> playersBudget, Map<String, List<String>> properties, String[] board, Scanner scanner, Map<String, Map<String, Integer>> playersHotels, Map<String, Map<String, Integer>> playersHouses, Map<String, Integer> playersFieldNumber, Map<String, Map<String, Boolean>> haveColorSet, Map<String, Map<String, Integer>> playersColorSet, Map<String, List<String>> railroads, Map<String, List<String>> utilities) {

        System.out.println(currentPlayer + ", you don't have enough money!");
        System.out.println("You need to sell one of your properties, railroads or utilities.");

        pressEnterToContinue(scanner);
        int playerHotels = findSumOfAllBuildingsOfPlayer(currentPlayer, playersHotels);
        int playerHouses = findSumOfAllBuildingsOfPlayer(currentPlayer, playersHouses);

        if (playerHotels > 0) {
            printHotelsInformation(currentPlayer, playerHotels);
            List<String> fields = createListOfFields(currentPlayer, playersHotels);
            int numberOfField = enterNumberOfField(scanner, fields);
            sellBuildingsOperation(currentPlayer, playersBudget, board, playersHotels, numberOfField);
            System.out.println(currentPlayer + ", you sold a hotel in a field " + fields.get(numberOfField - 1) + ".");
            pressEnterToContinue(scanner);
        } else if (playerHouses > 0) {
            printHousesInformation(currentPlayer, playerHouses);
            List<String> fields = createListOfFields(currentPlayer, playersHouses);
            int numberOfField = enterNumberOfField(scanner, fields);
            sellBuildingsOperation(currentPlayer, playersBudget, board, playersHouses, numberOfField);
            System.out.println(currentPlayer + ", you sold a house in a field " + fields.get(numberOfField - 1) + ".");
            pressEnterToContinue(scanner);
        } else if (properties.get(currentPlayer).isEmpty() && railroads.get(currentPlayer).isEmpty() && utilities.get(currentPlayer).isEmpty()) {
            System.out.println(currentPlayer + ", you have nothing for sale!");
        } else {
            sellProperties(currentPlayer, playersBudget, properties, board, scanner, playersFieldNumber, haveColorSet, playersColorSet, railroads, utilities);
        }

    }

    public static void sellProperties(String currentPlayer, Map<String, Integer> playersBudget, Map<String, List<String>> playerProperties, String[] board, Scanner scanner, Map<String, Integer> playersFieldNumber, Map<String, Map<String, Boolean>> haveColorSet, Map<String, Map<String, Integer>> playersColorSet, Map<String, List<String>> playersRailRoads, Map<String, List<String>> playersUtilities) {
        printChoiceToSell(currentPlayer, playerProperties, playersRailRoads, playersUtilities);
        getChoiceToCell(currentPlayer, playersBudget, playerProperties, board, scanner, playersFieldNumber, haveColorSet, playersColorSet, playersRailRoads, playersUtilities);
    }

    private static void getChoiceToCell(String currentPlayer, Map<String, Integer> playersBudget, Map<String, List<String>> playersProperties, String[] board, Scanner scanner, Map<String, Integer> playersFieldNumber, Map<String, Map<String, Boolean>> haveColorSet, Map<String, Map<String, Integer>> playersColorSet, Map<String, List<String>> playersRailRoads, Map<String, List<String>> playersUtilities) {
        String number = validateDigit(scanner);

        int choice = Integer.parseInt(number);
        while (choice < 0 || choice > 3) {
            System.out.println("Enter valid number: ");
            choice = Integer.parseInt(validateDigit(scanner));
        }

        switch (choice) {
            case 0:
                return;
            case 1:
                if (playersProperties.get(currentPlayer).isEmpty()) {
                    System.out.println("You don't have properties. Make another choice...");
                    sellProperties(currentPlayer, playersBudget, playersProperties, board, scanner, playersFieldNumber, haveColorSet, playersColorSet, playersRailRoads, playersUtilities);
                } else {
                    sellProperty(currentPlayer, playersBudget, playersProperties, board, scanner, playersFieldNumber, haveColorSet, playersColorSet);
                }
                break;
            case 2:
                if (playersRailRoads.get(currentPlayer).isEmpty()) {
                    System.out.println("You don't have railroads. Make another choice...");
                    sellProperties(currentPlayer, playersBudget, playersProperties, board, scanner, playersFieldNumber, haveColorSet, playersColorSet, playersRailRoads, playersUtilities);
                } else {
                    sellRailRoad(currentPlayer, playersBudget, board, scanner, playersRailRoads);
                }
                break;
            case 3:
                if (playersUtilities.get(currentPlayer).isEmpty()) {
                    System.out.println("You don't have utilities. Make another choice...");
                    sellProperties(currentPlayer, playersBudget, playersProperties, board, scanner, playersFieldNumber, haveColorSet, playersColorSet, playersRailRoads, playersUtilities);
                } else {
                    sellUtility(currentPlayer, playersBudget, board, scanner, playersUtilities);
                }
                break;
        }
    }

    private static String validateDigit(Scanner scanner) {
        String number = scanner.nextLine();
        while(number.length() == 0) {
            System.out.println("Enter a number!");
            number = scanner.nextLine();
        }
        while (!Character.isDigit(number.charAt(0))) {
            System.out.println("Enter valid number!");
            number = validateDigit(scanner);
        }
        return number;
    }

    private static void printChoiceToSell(String currentPlayer, Map<String, List<String>> playersProperties, Map<String, List<String>> playersRailroads, Map<String, List<String>> playersUtilities) {
        System.out.printf("%s, you have %d properties, %d railroads and %d utilities.%n", currentPlayer, playersProperties.get(currentPlayer).size(), playersRailroads.get(currentPlayer).size(), playersUtilities.get(currentPlayer).size());
        System.out.println("To sell a property, press 1");
        System.out.println("To sell a railroad, press 2");
        System.out.println("To sell an utility, press 3");
        System.out.println("If you don't want to sell anything, press 0.");
    }

    public static void sellProperty(String currentPlayer, Map<String, Integer> playersBudget, Map<String, List<String>> properties, String[] board, Scanner scanner, Map<String, Integer> playersFieldNumber, Map<String, Map<String, Boolean>> haveColorSet, Map<String, Map<String, Integer>> playersColorSet) {
        System.out.println(currentPlayer + ", you have the following properties:");
        printListOfEntities(properties.get(currentPlayer), board);
        System.out.print("Enter the number of the property you want to sell. ");
        System.out.println("If you don't want to sell anymore, press 0.");
        int numberOfProperty = enterNumberOfEntity(properties.get(currentPlayer), scanner);

        if (numberOfProperty == 0) {
            System.out.println("You didn't sell anything...");
            return;
        }

        String propertyName = properties.get(currentPlayer).get(numberOfProperty - 1);
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

    private static void removeEntity(String currentPlayer, Map<String, Integer> playersBudget, Map<String, List<String>> properties, String[] board, int numberOfProperty, String[] currentProperty, int indexOfProperty) {
        properties.get(currentPlayer).remove(numberOfProperty - 1);
        playersBudget.put(currentPlayer, (int) (playersBudget.get(currentPlayer) + Integer.parseInt(currentProperty[2]) * 0.5));
        currentProperty[1] = "for rent";
        currentProperty[currentProperty.length - 1] = "none";
        board[indexOfProperty] = String.join(", ", currentProperty);
    }

    private static int enterNumberOfEntity(List<String> properties, Scanner scanner) {
        String number = validateDigit(scanner);
        int numberOfProperty = Integer.parseInt(number);
        while (numberOfProperty < 0 || numberOfProperty > properties.size()) {
            System.out.println("Enter correct number of property:");
            numberOfProperty = Integer.parseInt(validateDigit(scanner));
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

    public static void sellRailRoad(String currentPlayer, Map<String, Integer> playersBudget, String[] board, Scanner scanner, Map<String, List<String>> railroads) {
        System.out.println(currentPlayer + ", you have the following railroads:");
        printListOfEntities(railroads.get(currentPlayer), board);
        System.out.print("Enter the number of the railroad you want to sell. ");
        System.out.println("If you don't want to sell anymore, press 0.");
        int numberOfProperty = enterNumberOfEntity(railroads.get(currentPlayer), scanner);

        if (numberOfProperty == 0) {
            System.out.println("You didn't sell anything...");
            return;
        }

        String propertyName = railroads.get(currentPlayer).get(numberOfProperty - 1);
        String[] currentProperty = findFieldByName(propertyName, board);
        int indexOfProperty = findIndexOfField(propertyName, board);

        removeEntity(currentPlayer, playersBudget, railroads, board, numberOfProperty, currentProperty, indexOfProperty);

        System.out.println("You sold a railroad '" + propertyName + "' for $" + (int) (Integer.parseInt(currentProperty[2]) * 0.5) + ".");
        scanner.nextLine();
    }

    public static void sellUtility(String currentPlayer, Map<String, Integer> playersBudget, String[] board, Scanner scanner, Map<String, List<String>> utilities) {
        System.out.println(currentPlayer + ", you have the following utilities:");
        printListOfEntities(utilities.get(currentPlayer), board);
        System.out.print("Enter the number of the utility you want to sell. ");
        System.out.println("If you don't want to sell anymore, press 0.");
        int numberOfProperty = enterNumberOfEntity(utilities.get(currentPlayer), scanner);

        if (numberOfProperty == 0) {
            System.out.println("You didn't sell anything...");
            return;
        }

        String propertyName = utilities.get(currentPlayer).get(numberOfProperty - 1);
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

    public static int checkIndexForNotEnoughMoney(Scanner scanner, Map<String, Integer> playersBudget, Map<String, List<String>> playersPurchases, Map<String, Map<String, Integer>> playersHouses, Map<String, Map<String, Integer>> playersHotels, List<String> players, String[] board, int index, String currentPlayer, Map<String, Integer> playersFieldNumber, Map<String, Map<String, Boolean>> haveColorSet, Map<String, Map<String, Integer>> playersColorSet, Map<String, List<String>> railroads, Map<String, List<String>> utilities) {
        String TEXT_RESET = "\u001B[0m";
        String TEXT_RED = "\u001B[31m";

        while (playersBudget.get(currentPlayer) < 0) {

            offerToSell(currentPlayer, playersBudget, playersPurchases, board, scanner, playersHotels, playersHouses, playersFieldNumber, haveColorSet, playersColorSet, railroads, utilities);

            if (playersBudget.get(currentPlayer) >= 0) {
                break;
            }

            if (findSumOfAllBuildingsOfPlayer(currentPlayer, playersHotels) == 0
                    && findSumOfAllBuildingsOfPlayer(currentPlayer, playersHouses) == 0
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
        int numbersForColorGroup = switch (playersFieldNumber.get(currentPlayer)) {
            case 1, 3, 37, 39 -> 2;
            case 6, 8, 9, 11, 13, 14, 16, 18, 19, 21, 23, 24, 26, 27, 29, 31, 32, 34 -> 3;
            default -> 0;
        };
        Map<String, Boolean> temp = haveColorGroup.get(currentPlayer);
        temp.put(colorType, false);
        if (playersColorFields.get(currentPlayer).get(colorType) == numbersForColorGroup) {
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

    public static void printWelcome() {
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
    }

    private static void choiceStartOfTheGame(Scanner scanner) {
        String TEXT_GREEN = "\u001B[32m";
        String TEXT_RESET = "\u001B[0m";
        System.out.println(TEXT_GREEN + "If you want to see the game board, press 1.");
        System.out.println("if you want to start the game, press 2." + TEXT_RESET);
        String choice = validateDigit(scanner);
        int number = Integer.parseInt(choice);
        while (number <= 0 || number > 2) {
            System.out.println("Enter valid number!");
            number = Integer.parseInt(validateDigit(scanner));
        }

        if (number == 1) {
            printBoard();
        } else {
            System.out.println(TEXT_GREEN + "THE GAME START!" + TEXT_RESET);
        }
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
