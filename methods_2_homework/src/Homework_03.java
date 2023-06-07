import java.util.Scanner;

public class Homework_03 {

    public static double calculateKelvinToCelsius(double degrees) {
        return degrees - 273.15;
    }

    public static double calculateKelvinToFahrenheit(double degrees) {
        return degrees * 9 / 5 - 459.67;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter C /for Celsius/ or F /for fahrenheit/: ");
        String letter = scanner.nextLine();

        if (!letter.equals("C") && !letter.equals("F")) {
            System.out.println("Invalid input!");
        } else {
            System.out.print("Enter degrees in Kelvin: ");
            double number = scanner.nextDouble();
            double result = 0;

            if (letter.equals("C")) {
                result = calculateKelvinToCelsius(number);
                System.out.printf("K(%f) -> <C>(%.2f)", number, result);
            } else {
                result = calculateKelvinToFahrenheit(number);
                System.out.printf("K(%f) -> <F>(%.2f)", number, result);
            }
        }
    }
}
