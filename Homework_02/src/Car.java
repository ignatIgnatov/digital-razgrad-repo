import java.util.Scanner;

public class Car {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int age = Integer.parseInt(scanner.nextLine());
        double price = Double.parseDouble(scanner.nextLine());

        boolean isHighClassCar = (age > 5 && price > 20000) || (age <= 5 && price > 40000);


        System.out.println(isHighClassCar);
    }
}
