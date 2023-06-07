import java.util.Scanner;

public class Lab_02 {

    public static void printAreaOfCircle(int radius) {
        double area = Math.PI * radius * radius;
        System.out.println("The area of circle with radius " + radius + " is " + area);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a radius: ");
        int number = scanner.nextInt();

        printAreaOfCircle(number);

    }
}
