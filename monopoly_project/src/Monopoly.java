import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Monopoly {

    private static void resetFields(Scanner scanner, Map<String, Integer> playersFieldNumber, Map<String, Integer> playersBudget, String currentPlayer) {
        if (playersFieldNumber.get(currentPlayer) >= 40) {

            playersFieldNumber.put(currentPlayer, playersFieldNumber.get(currentPlayer) - 40);
            playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) + 200);
            goTroughTheStart(currentPlayer, scanner);

        }
    }

    private static String[] getCurrentField(Map<String, Integer> playersFieldNumber, String[] board, String currentPlayer) {
        String[] currentField = board[playersFieldNumber.get(currentPlayer)].split(", ");

        System.out.println("-----------");
        System.out.println("Current field: " + currentField[0]);
        System.out.println("-----------");
        return currentField;
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
                                       String[] board, String fieldCard, Map<String, Map<String, Integer>> playersHotels, Map<String, Map<String, Integer>> playersHouses, List<String> players) {

        String TEXT_YELLOW = "\u001B[33m";
        String TEXT_RESET = "\u001B[0m";

        printFieldCard(currentField, TEXT_YELLOW, TEXT_RESET);

        if (currentField[1].equals("for rent") && !currentPlayer.equals(currentField[currentField.length - 1])) {

            String answer = doYouWontToBuy(currentField, scanner);

            if (answer.equals("yes")) {
                if (moneyValidation(playersBudget.get(currentPlayer), Integer.parseInt(currentField[2]))) {

                    sayYesToBuy(currentField, currentPlayer, playersBudget, playersPurchases, playersSpecialCards, playersFieldNumber, board, TEXT_YELLOW, TEXT_RESET);

                } else {
                    offerToSell(currentPlayer, playersBudget, playersPurchases, board, scanner, playersHotels, playersHouses, playersPurchases, players);
                }
            }

        } else if (!currentPlayer.equals(currentField[currentField.length - 1])) {

            String fieldOwner = currentField[3];
            int annuity = 50;

            payAnnuity(currentPlayer, playersBudget, fieldOwner, annuity);
        }
    }

    public static void buyColorField(String[] currentField, Scanner scanner, String currentPlayer,
                                     Map<String, Integer> playersBudget, List<String> playersPurchases,
                                     List<String> playersSpecialCards, Map<String, Integer> playersFieldNumber,
                                     String[] board, String fieldCard, Map<String, Integer> houses, Map<String, Integer> hotels,
                                     Map<String, Map<String, Integer>> playersHouses, Map<String, Map<String, Integer>> playersHotels, List<String> players) {

        String TEXT_CYAN = "\u001B[36m";
        String TEXT_RESET = "\u001B[0m";

        printFieldCard(currentField, TEXT_CYAN, TEXT_RESET);

        if (currentField[1].equals("for rent")) {

            String answer = doYouWontToBuy(currentField, scanner);

            if (answer.equals("yes")) {

                if (moneyValidation(playersBudget.get(currentPlayer), Integer.parseInt(currentField[2]))) {

                    sayYesToBuy(currentField, currentPlayer, playersBudget, playersPurchases, playersSpecialCards, playersFieldNumber, board, TEXT_CYAN, TEXT_RESET);

                    houses.put(currentField[0], 0);
                    hotels.put(currentField[0], 0);
                    playersHouses.put(currentPlayer, houses);
                    playersHotels.put(currentPlayer, hotels);

                } else {
                    offerToSell(currentPlayer, playersBudget, playersPurchases, board, scanner, playersHotels, playersHouses, playersPurchases, players);
                }
            }

        } else {

            if (!currentPlayer.equals(currentField[currentField.length - 1])) {

                String fieldOwner = currentField[currentField.length - 1];

                int housesCount = playersHouses.get(fieldOwner).get(currentField[0]);
                int hotelsCount = playersHotels.get(fieldOwner).get(currentField[0]);

                int annuity = getAnnuity(currentField, housesCount, hotelsCount);

                payAnnuity(currentPlayer, playersBudget, fieldOwner, annuity);

            } else {

                buyHouseOrHotel(houses, hotels, playersHouses, playersHotels, currentPlayer,
                        currentField, scanner, playersBudget, playersPurchases, playersSpecialCards, board);

            }
        }
    }

    private static void sellHouse(Scanner scanner, String currentPlayer, Map<String, Integer> playersBudget, String[] board, Map<String, Map<String, Integer>> playersHouses, Map<String, Map<String, Integer>> playersHotels, int playerHouses) {
        System.out.println(currentPlayer + ", you have " + playerHouses + " hotels. You have to sell one of them");
        System.out.println("These are your houses: ");

        List<String> fields = createListOfFields(currentPlayer, playersHouses);

        int numberOfField = enterNumberOfField(scanner, fields);

        sellHotelOrHouse(scanner, currentPlayer, playersBudget, board, playersHotels, fields, numberOfField);

        System.out.println(currentPlayer + ", you sold a hotel in a field " + fields.get(numberOfField) + ".");

        pressEnterToContinue(scanner);
    }

    private static void sellHotel(Scanner scanner, String currentPlayer, Map<String, Integer> playersBudget, String[] board, Map<String, Map<String, Integer>> playersHotels, int playerHotels) {
        System.out.println(currentPlayer + ", you have " + playerHotels + " hotels. You have to sell one of them");
        System.out.println("These are your hotels: ");

        List<String> fields = createListOfFields(currentPlayer, playersHotels);

        int numberOfField = enterNumberOfField(scanner, fields);

        sellHotelOrHouse(scanner, currentPlayer, playersBudget, board, playersHotels, fields, numberOfField);

        System.out.println(currentPlayer + ", you sold a hotel in a field " + fields.get(numberOfField) + ".");

        pressEnterToContinue(scanner);
    }

    private static void sellHotelOrHouse(Scanner scanner, String currentPlayer, Map<String, Integer> playersBudget, String[] board, Map<String, Map<String, Integer>> playersHotels, List<String> fields, int numberOfField) {
        String nameOfField = fields.get(numberOfField);

        Map<String, Integer> temp = playersHotels.get(currentPlayer);
        temp.put(nameOfField, temp.get(nameOfField) - 1);
        playersHotels.put(currentPlayer, temp);

        int price = Integer.parseInt(findFieldByName(nameOfField, board)[11]);
        playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) + price);
    }

    private static List<String> createListOfFields(String currentPlayer, Map<String, Map<String, Integer>> playersHotels) {
        int index = 0;
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

    private static int enterNumberOfField(Scanner scanner, List<String> fields) {
        System.out.println("Enter the number of the field whose hotel you want to sell: ");
        int numberOfTheField = scanner.nextInt();

        while (numberOfTheField < 0 || numberOfTheField > fields.size() - 1) {
            System.out.println("Enter the number of the field whose hotel you want to sell: ");
            numberOfTheField = scanner.nextInt();
        }
        return numberOfTheField;
    }

    private static int findSumOfAllHotelsOrHousesOfPlayer(String currentPlayer, Map<String, Map<String, Integer>> playersHotels) {
        int sumOfHotels = 0;
        for (Integer value : playersHotels.get(currentPlayer).values()) {
            sumOfHotels += value;
        }
        return sumOfHotels;
    }

    private static int getAnnuity(String[] currentField, int housesCount, int hotelsCount) {
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
            annuity = Integer.parseInt(currentField[3]);
        }
        return annuity;
    }

    private static void payAnnuity(String currentPlayer, Map<String, Integer> playersBudget, String fieldOwner, int annuity) {
        playersBudget.put(fieldOwner, playersBudget.get(fieldOwner) + annuity);
        playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) - annuity);
        System.out.printf("%s pays %s an annuity of $%d%n", currentPlayer, fieldOwner, annuity);
    }

    private static void printFieldCard(String[] currentField, String TEXT_CYAN, String TEXT_RESET) {
        String fieldCard;
        fieldCard = colorFieldCard(currentField);
        System.out.println(TEXT_CYAN + fieldCard + TEXT_RESET);
    }

    private static void sayYesToBuy(String[] currentField, String currentPlayer, Map<String, Integer> playersBudget, List<String> playersPurchases, List<String> playersSpecialCards, Map<String, Integer> playersFieldNumber, String[] board, String TEXT_YELLOW, String TEXT_RESET) {
        currentField[1] = "Sold";
        currentField[currentField.length - 1] = currentPlayer;
        playersPurchases.add(currentField[0]);
        playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) - Integer.parseInt(currentField[2]));

        printPlayerStats(currentPlayer, playersBudget.get(currentPlayer), playersPurchases, playersSpecialCards);
        System.out.println(TEXT_YELLOW + companyFieldCard(currentField) + TEXT_RESET);
        System.out.println(currentPlayer + ", you bought " + currentField[0]);

        board[playersFieldNumber.get(currentPlayer)] = String.join(", ", currentField);
    }

    private static String doYouWontToBuy(String[] currentField, Scanner scanner) {
        System.out.println("Do you want to buy " + currentField[0] + "?");
        String answer = enterYesOrNo(scanner);
        return answer;
    }

    public static List<String> colorFields(String[] board) {
        List<String> result = new ArrayList<>();

        for (int i = 0; i < board.length; i++) {
            String current = board[i].split(", ")[0];
            if (!current.equals("START") && !current.equals("Community Chest") &&
                    !current.equals("Income Tax") && !current.equals("Reading RailRoad") &&
                    !current.equals("Chance") && !current.equals("Jail - only visit") &&
                    !current.equals("Electric Company") && !current.equals("Pennsylvania Railroad") &&
                    !current.equals("Free Parking") && !current.equals("B. & O. Railroad") &&
                    !current.equals("Water works") && !current.equals("Go To Jail") &&
                    !current.equals("Short line Railroad") && !current.equals("Luxury tax")) {
                result.add(current);
            }
        }

        return result;
    }

    public static void buyHouseOrHotel(Map<String, Integer> houses, Map<String, Integer> hotels, Map<String, Map<String, Integer>> playersHouses,
                                       Map<String, Map<String, Integer>> playersHotels,
                                       String currentPlayer, String[] currentField, Scanner scanner,
                                       Map<String, Integer> playersBudget, List<String> playersPurchases,
                                       List<String> playersSpecialCards, String[] board) {

        if (houses.get(currentField[0]) < 4) {

            System.out.printf("%s field is yours.%n", currentField[0]);
            System.out.printf("You have %d houses and %d hotels on this field.%n",
                    playersHouses.get(currentPlayer).get(currentField[0]),
                    playersHotels.get(currentPlayer).get(currentField[0]));

            System.out.println("Do you want to buy a house?");

            String answer = enterYesOrNo(scanner);

            if (answer.equals("yes")) {

                int housePrice = Integer.parseInt(currentField[10]);

                houses.put(currentField[0], houses.get(currentField[0]) + 1);
                playersHouses.put(currentPlayer, houses);
                playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) - housePrice);

                System.out.println(currentPlayer + ", you bought a house.");
                printPlayerStats(currentPlayer, playersBudget.get(currentPlayer), playersPurchases, playersSpecialCards);
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
                    printPlayerStats(currentPlayer, playersBudget.get(currentPlayer), playersPurchases, playersSpecialCards);
                }
            }
        }
    }

    public static void switchChance(String chanceRow, Map<String, Integer> playersFieldNumber,
                                    String currentPlayer, Map<String, Integer> playersBudget,
                                    List<String> playersSpecialCards, Map<String, Boolean> playersInJail,
                                    List<String> players, List<String> playersPurchases, String[] board, Scanner scanner, String[] currentField, String fieldCard, Map<String, Integer> houses, Map<String, Integer> hotels, Map<String, Map<String, Integer>> playersHouses, Map<String, Map<String, Integer>> playersHotels) {
        switch (chanceRow) {
            case "Advance to Boardwalk":

                playersFieldNumber.put(currentPlayer, 39);

                currentField = board[playersFieldNumber.get(currentPlayer)].split(", ");

                System.out.println(currentPlayer + ", you went to Boardwalk.");

                buyColorField(currentField, scanner, currentPlayer, playersBudget,
                        playersPurchases, playersSpecialCards, playersFieldNumber,
                        board, fieldCard, houses, hotels, playersHouses, playersHotels, players);
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
                        board, fieldCard, houses, hotels, playersHouses, playersHotels, players);

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
                        board, fieldCard, houses, hotels, playersHouses, playersHotels, players);

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
                        playersSpecialCards, playersFieldNumber, board, fieldCard, playersHotels, playersHouses, players);

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
                        playersSpecialCards, playersFieldNumber, board, fieldCard, playersHotels, playersHouses, players);

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
                        playersSpecialCards, playersFieldNumber, board, fieldCard, playersHotels, playersHouses, players);

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

    private static void repairHousesAndHotels(String currentPlayer, Map<String, Integer> playersBudget, Map<String, Map<String, Integer>> playersHouses, Map<String, Map<String, Integer>> playersHotels, int housePrice, int hotelPrice) {

        int hotelsCount = findSumOfAllHotelsOrHousesOfPlayer(currentPlayer, playersHotels);
        int housesCount = findSumOfAllHotelsOrHousesOfPlayer(currentPlayer, playersHouses);

        if (hotelsCount > 0 || housesCount > 0) {
            int sum = housesCount * housePrice + hotelsCount * hotelPrice;
            playersBudget.put(currentPlayer, playersBudget.get(currentPlayer) - sum);

            System.out.printf("%s, you repaired %d houses and %d hotels%n", currentPlayer, housesCount, hotelsCount);
            System.out.println("You paid $" + sum + ".");

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

    private static void switchOtherFields(Scanner scanner, List<String> players, Map<String, Integer> playersFieldNumber, Map<String, Integer> playersBudget, Map<String, List<String>> playersPurchases, Map<String, Map<String, Integer>> playersHouses, Map<String, Map<String, Integer>> playersHotels, Map<String, Integer> houses, Map<String, Integer> hotels, Map<String, List<String>> playersSpecialCards, Map<String, Boolean> playersInJail, String[] board, ArrayDeque<String> communityChestCards, ArrayDeque<String> chanceCards, String currentPlayer, String[] currentField, String fieldCard) {
        switch (currentField[0]) {
            case "START":
                System.out.println("You become $200");
                break;
            case "Community Chest":
                String communityChestRow = communityChestCards.poll();
                System.out.println(communityChestRow);

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
                System.out.println(chanceRow);

                switchChance(chanceRow, playersFieldNumber,
                        currentPlayer, playersBudget,
                        playersSpecialCards.get(currentPlayer), playersInJail,
                        players, playersPurchases.get(currentPlayer), board, scanner, currentField, fieldCard, houses, hotels, playersHouses, playersHotels);

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
        sb.append("----------");

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
        sb.append("----------");

        return sb.toString();
    }

    public static String otherFieldCard(String[] currentField) {

        StringBuilder sb = new StringBuilder();

        sb.append("----------").append(System.lineSeparator());
        sb.append(currentField[0]).append(System.lineSeparator());
        sb.append("----------");

        return sb.toString();
    }

    public static void offerToSell(String currentPlayer, Map<String, Integer> playersBudget, List<String> properties, String[] board, Scanner scanner, Map<String, Map<String, Integer>> playersHotels, Map<String, Map<String, Integer>> playersHouses, List<String> playersPurchases, List<String> players) {

        System.out.println("You don't have enough money!");
        System.out.println("You need to sell one of your properties.");

        pressEnterToContinue(scanner);

        int playerHotels = findSumOfAllHotelsOrHousesOfPlayer(currentPlayer, playersHotels);
        int playerHouses = findSumOfAllHotelsOrHousesOfPlayer(currentPlayer, playersHouses);

        sellEntity(currentPlayer, playersBudget, properties, board, scanner, playersHotels, playersHouses, playerHotels, playerHouses, playersPurchases, players);

    }

    private static void sellEntity(String currentPlayer, Map<String, Integer> playersBudget, List<String> properties, String[] board, Scanner scanner, Map<String, Map<String, Integer>> playersHotels, Map<String, Map<String, Integer>> playersHouses, int playerHotels, int playerHouses, List<String> playersPurchases, List<String> players) {
        if (playerHotels > 0) {

            sellHotel(scanner, currentPlayer, playersBudget, board, playersHotels, playerHotels);

        } else if (playerHouses > 0) {

            sellHouse(scanner, currentPlayer, playersBudget, board, playersHouses, playersHotels, playerHouses);

        } else if (playersPurchases.size() > 0) {

            sellProperty(currentPlayer, playersBudget, properties, board, scanner);
        } else {

            players.remove(currentPlayer);
        }
    }

    private static void sellProperty(String currentPlayer, Map<String, Integer> playersBudget, List<String> properties, String[] board, Scanner scanner) {
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
        String[] currentProperty = findFieldByName(propertyName, board);
        int indexOfProperty = findIndexOfProperty(propertyName, board);

        properties.remove(numberOfProperty - 1);
        playersBudget.put(currentPlayer, Integer.parseInt(currentProperty[2]));

        currentProperty[1] = "for rent";
        currentProperty[currentProperty.length - 1] = "none";

        board[indexOfProperty] = String.join(", ", currentProperty);

        System.out.println("You sold a property " + propertyName);
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

    public static void printPlayerStats(String name, int money, List<String> purchases, List<String> specialCards) {
        String TEXT_GREEN = "\u001B[32m";
        String TEXT_RESET = "\u001B[0m";
        System.out.println(TEXT_GREEN + "-----------------------------------");
        System.out.printf("%s's statistic: %n", name);
        System.out.println("Money: " + "$" + money);
        String playerFields = purchases.size() > 0 ? "Fields purchased: " + String.join(", ", purchases) : "Fields purchased: none";
        System.out.println(playerFields);
        String playerCards = specialCards.size() > 0 ? "Special cards: " + String.join(", ", specialCards) : "Special cards: none";
        System.out.println(playerCards);
        System.out.println("-----------------------------------" + TEXT_RESET);
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

    public static void printLogoOfTheGame() {
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

    public static void fillPlayerNames(List<String> players, Scanner scanner) {
        for (int i = 0; i < players.size(); i++) {
            System.out.printf("Enter Player %d name: ", i + 1);
            String currentName = scanner.nextLine();
            currentName = checkForEmptyName(currentName, scanner);

            if (i > 0) {
                currentName = checkForSameName(currentName, players, scanner);
            }

            players.add(currentName);
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
        System.out.println("-----------");
        System.out.println(currentPlayer + ", you passed the Start field. You get $200.");

        pressEnterToContinue(scanner);
    }

    private static void pressEnterToContinue(Scanner scanner) {
        System.out.println("Press 'Enter' to continue.");
        String playerRoll = scanner.nextLine();

        while (!playerRoll.isEmpty()) {
            System.out.println("Press 'Enter' to continue.");
            playerRoll = scanner.nextLine();
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        Map<String, Integer> playersFieldNumber = new HashMap<>();
        Map<String, Integer> playersBudget = new HashMap<>();
        Map<String, List<String>> playersPurchases = new HashMap<>();

        Map<String, Map<String, Integer>> playersHouses = new HashMap<>();
        Map<String, Map<String, Integer>> playersHotels = new HashMap<>();

        Map<String, Integer> houses = new HashMap<>();
        Map<String, Integer> hotels = new HashMap<>();

        Map<String, List<String>> playersSpecialCards = new HashMap<>();
        Map<String, Boolean> playersInJail = new HashMap<>();

        printLogoOfTheGame();

        int numberOfPlayers = enterNumberOfPlayers(scanner);

        List<String> players = new ArrayList<>();

        fillPlayerNames(players, scanner);

        //попълване на игралната дъска от файла BoardFields.txt
        String[] board = arrayFromFile("dataBase/BoardFields.txt", 40);

        //попълване на картите Community chest от файла в масив
        String[] communityChest = arrayFromFile("dataBase/CommunityChest.txt", 16);
        ArrayDeque<String> communityChestCards = convertArrayToDeque(communityChest);

        //попълване на картите Chance от файла в масив
        String[] chance = arrayFromFile("dataBase/Chance.txt", 16);
        ArrayDeque<String> chanceCards = convertArrayToDeque(chance);

        for (int i = 0; i < players.size(); i++) {
            playersBudget.put(players.get(i), 1500);
            playersFieldNumber.put(players.get(i), 0);
            playersPurchases.put(players.get(i), new ArrayList<>());
            playersSpecialCards.put(players.get(i), new ArrayList<>());
            playersInJail.put(players.get(i), false);
            playersHouses.put(players.get(i), new HashMap<>());
            playersHotels.put(players.get(i), new HashMap<>());
        }

        for (int i = 0; i < colorFields(board).size(); i++) {
            houses.put(colorFields(board).get(i), 0);
            hotels.put(colorFields(board).get(i), 0);
        }

        printStart();

        int jailCounter = 0;

        for (int i = 0; i < players.size(); i++) {
            String currentPlayer = players.get(i);
            int moveNumber = 0;

            if (playersInJail.get(currentPlayer)) {
                if (playersSpecialCards.get(currentPlayer).contains("Get Out of Jail Free")) {
                    playersInJail.put(currentPlayer, false);
                    playersSpecialCards.get(currentPlayer).remove(0);
                } else {
                    System.out.printf("%s, you must roll a 12", currentPlayer);
                    moveNumber = rollTheDice(currentPlayer, scanner);
                    if (moveNumber == 12) {
                        playersInJail.put(currentPlayer, false);
                        jailCounter = 0;
                    } else {
                        jailCounter++;
                        if (jailCounter == 3) {
                            playersInJail.put(currentPlayer, false);
                            jailCounter = 0;
                        }
                        if (i == players.size() - 1) {
                            i = -1;
                            System.out.println("Next turn!");
                        } else {
                            System.out.println("Next player!");
                        }
                        continue;
                    }
                }
            }

            while (playersBudget.get(currentPlayer) <= 0) {
                offerToSell(currentPlayer, playersBudget, playersPurchases.get(currentPlayer), board, scanner, playersHotels, playersHouses, playersPurchases.get(currentPlayer), players);

                if (findSumOfAllHotelsOrHousesOfPlayer(currentPlayer, playersHotels) == 0
                        && findSumOfAllHotelsOrHousesOfPlayer(currentPlayer, playersHouses) == 0
                        && playersPurchases.get(currentPlayer).isEmpty()) {

                    players.remove(currentPlayer);
                    System.out.println(currentPlayer + "the game over for you!");
                    pressEnterToContinue(scanner);
                }
            }

            moveNumber = rollTheDice(currentPlayer, scanner);

            playersFieldNumber.put(currentPlayer, playersFieldNumber.get(currentPlayer) + moveNumber);

            resetFields(scanner, playersFieldNumber, playersBudget, currentPlayer);

            String[] currentField = getCurrentField(playersFieldNumber, board, currentPlayer);

            pressEnterToContinue(scanner);

            String fieldCard = "";

            switch (playersFieldNumber.get(currentPlayer)) {

                case 0, 2, 4, 7, 10, 17, 20, 22, 30, 33, 36, 38:
                    fieldCard = otherFieldCard(currentField);

                    switchOtherFields(scanner, players, playersFieldNumber, playersBudget, playersPurchases,
                            playersHouses, playersHotels, houses, hotels, playersSpecialCards,
                            playersInJail, board, communityChestCards, chanceCards, currentPlayer,
                            currentField, fieldCard);

                    break;
                case 1, 3, 6, 8, 9, 11, 13, 14, 16, 18, 19, 21, 23, 24, 26, 27, 29, 31, 32, 34, 37, 39:

                    buyColorField(currentField, scanner, currentPlayer,
                            playersBudget, playersPurchases.get(currentPlayer),
                            playersSpecialCards.get(currentPlayer), playersFieldNumber,
                            board, fieldCard, houses, hotels, playersHouses, playersHotels, players);

                    break;
                case 5, 12, 15, 25, 28, 35:

                    buyCompanyField(currentField, scanner, currentPlayer, playersBudget,
                            playersPurchases.get(currentPlayer), playersSpecialCards.get(currentPlayer),
                            playersFieldNumber, board, fieldCard, playersHotels, playersHouses, players);

                    break;
            }

            printPlayerStats(currentPlayer, playersBudget.get(currentPlayer), playersPurchases.get(currentPlayer), playersSpecialCards.get(currentPlayer));

            if (i == players.size() - 1) {
                i = -1;
                System.out.println("Next turn!");
            } else {
                System.out.println("-----------");
                System.out.println("Next player!");
            }
        }
    }
}
