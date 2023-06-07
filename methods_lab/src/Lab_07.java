import java.util.Scanner;

public class Lab_07 {

    public static String countryCodeValidation(String code) {
        int length = code.length();
        String result = "";

        if (length == 2) {
            char firstLetter = code.charAt(0);
            char secondLetter = code.charAt(1);

            if (firstLetter >= 65 &&
                    firstLetter <= 90 &&
                    secondLetter >= 65 &&
                    secondLetter <= 90) {
                result = "The code " + code + " is valid";
            } else {
                result = "The code " + code + " is not valid";
            }
        } else {
            result = "The code " + code + " is not valid";
        }
        return result;
    }

    public static String countryCodeValidationV2(String code) {
        int length = code.length();
        String result = "";

        if (length == 2) {
            char firstLetter = code.charAt(0);
            char secondLetter = code.charAt(1);

            if (Character.isUpperCase(firstLetter) && Character.isUpperCase(secondLetter)) {
                result = "The code " + code + " is valid";
            } else {
                result = "The code " + code + " is not valid";
            }
        } else {
            result = "The code " + code + " is not valid";
        }
        return result;
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a country code: ");
        String code = scanner.nextLine();

        System.out.println(countryCodeValidationV2(code));
    }
}

