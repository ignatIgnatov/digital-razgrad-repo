import java.util.Scanner;

public class Homework_06 {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a number between 0 and 999: ");
        int currentNumber = scanner.nextInt();
        int number = currentNumber;

        String stringOfUnits = "";
        String stringOfTen = "";
        String stringOfHundred = "";
        String result = "";

        int digitOfUnits = 0;
        int digitOfTen = 0;
        int digitOfHundred = 0;

        if (number > 999) {
            System.out.println("Invalid number");
            return;
        } else if (number >= 100) {
            digitOfUnits = number % 10;
            number = number / 10;
            digitOfTen = number % 10;
            digitOfHundred = number / 10;
        } else if (number >= 10) {
            if (number == 10) {
                result = "ten";
            } else if (number == 11) {
                result = "eleven";
            } else if (number == 12) {
                result = "twelve";
            } else if (number == 13) {
                result = "thirteen";
            } else if (number == 15) {
                result = "fifteen";
            } else if (number == 18) {
                result = "eighteen";
            } else {
                digitOfUnits = number % 10;
                digitOfTen = number / 10;
            }

        } else {
            digitOfUnits = number;
        }

        switch (digitOfUnits) {
            case 0 -> stringOfUnits = "zero";
            case 1 -> stringOfUnits = "one";
            case 2 -> stringOfUnits = "two";
            case 3 -> stringOfUnits = "three";
            case 4 -> stringOfUnits = "four";
            case 5 -> stringOfUnits = "five";
            case 6 -> stringOfUnits = "six";
            case 7 -> stringOfUnits = "seven";
            case 8 -> stringOfUnits = "eight";
            case 9 -> stringOfUnits = "nine";
        }

        switch (digitOfTen) {
            case 2 -> stringOfTen = "twenty";
            case 3 -> stringOfTen = "thirty";
            case 4 -> stringOfTen = "forty";
            case 5 -> stringOfTen = "fifty";
            case 6 -> stringOfTen = "sixty";
            case 7 -> stringOfTen = "seventy";
            case 8 -> stringOfTen = "eighty";
            case 9 -> stringOfTen = "ninety";
        }

        switch (digitOfHundred) {
            case 1 -> stringOfHundred = "one hundred";
            case 2 -> stringOfHundred = "two hundred";
            case 3 -> stringOfHundred = "three hundred";
            case 4 -> stringOfHundred = "four hundred";
            case 5 -> stringOfHundred = "five hundred";
            case 6 -> stringOfHundred = "six hundred";
            case 7 -> stringOfHundred = "seven hundred";
            case 8 -> stringOfHundred = "eight hundred";
            case 9 -> stringOfHundred = "nine hundred";
        }

        if (currentNumber >= 0 && currentNumber <= 9) {
            result = stringOfUnits;
        } else if (currentNumber == 14 || currentNumber == 16 || currentNumber == 17 || currentNumber == 19) {
            result = stringOfUnits + "teen";
        } else if (currentNumber >= 20 && currentNumber <= 99) {
            if (digitOfUnits == 0) {
                result = stringOfTen;
            } else {
                result = stringOfTen + "-" + stringOfUnits;
            }
        } else if (currentNumber >= 100) {

            if (digitOfTen == 0 && digitOfUnits == 0) {
                result = stringOfHundred;
            } else if (digitOfTen == 0) {
                result = stringOfHundred + " and " + stringOfUnits;
            } else if (digitOfTen == 1) {
                if (digitOfUnits == 0) {
                    result = stringOfHundred + " and ten";
                } else if (digitOfUnits == 1) {
                    result = stringOfHundred + " and eleven";
                } else if (digitOfUnits == 2) {
                    result = stringOfHundred + " and twelve";
                } else if (digitOfUnits == 3) {
                    result = stringOfHundred + " and thirteen";
                } else if (digitOfUnits == 5) {
                    result = stringOfHundred + " and fifteen";
                } else if (digitOfUnits == 8) {
                    result = stringOfHundred + " and eighteen";
                } else {
                    result = stringOfHundred + " and " + stringOfUnits + "teen";
                }
            } else if (digitOfUnits == 0) {
                result = stringOfHundred + " and " + stringOfTen;
            } else {
                result = stringOfHundred + " and " + stringOfTen + "-" + stringOfUnits;
            }
        }
        System.out.println(result);
    }
}
