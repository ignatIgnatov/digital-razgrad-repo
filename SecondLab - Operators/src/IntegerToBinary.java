import java.util.Scanner;

public class IntegerToBinary {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int number = Integer.parseInt(scanner.nextLine());

        String result = "";

        if (number >= 8 && number <= 15) {
            result = Integer.toBinaryString(number);
        }

        System.out.println(result);

    }
}
