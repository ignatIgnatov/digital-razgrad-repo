import java.util.Scanner;

public class Exercise_1 {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("      ***************");
        System.out.println("***** Chocolate Info: ******");
        System.out.println("      ***************");
        System.out.println();

        System.out.print("Chocolate Name: ");
        String name = scanner.nextLine();

        System.out.print("Chocolate Weight /grams/: ");
        int weight = Integer.parseInt(scanner.nextLine());

        System.out.print("Chocolate Carbohydrates /percent 0.1 - 0.99/: ");
        double carbohydrates = Double.parseDouble(scanner.nextLine());

        System.out.print("Suitable for Vegans /Yes ot No/: ");
        String vegan = scanner.nextLine();

        System.out.print("Chocolate Price: ");
        double price = Double.parseDouble(scanner.nextLine());

    }
}
