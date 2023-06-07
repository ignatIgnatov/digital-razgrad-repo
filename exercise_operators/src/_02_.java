import java.util.Scanner;

public class _02_ {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter your weight: ");
        double weightOnHumanOnTheEarth = scanner.nextDouble();

        double weightOnHumanOnTheMoon = weightOnHumanOnTheEarth / 9.81 * 1.622;

        System.out.println("The weight on human on the Moon is: " + weightOnHumanOnTheMoon + "kg");

    }
}
