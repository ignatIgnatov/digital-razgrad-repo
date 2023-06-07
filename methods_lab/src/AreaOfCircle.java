import java.util.Scanner;

public class AreaOfCircle {

    public static double areaOfCircle(int radius) {
        return Math.PI * radius * radius;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a radius: ");
        int radius = scanner.nextInt();

        double result = areaOfCircle(radius);
        System.out.println(result);
    }
}
