import java.util.Scanner;

public class _03_ {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        double radius = scanner.nextDouble();

        double area = Math.PI * radius * radius;
        double perimeter = 2 * Math.PI * radius;

        String result = radius > 0 ? "Area: " + area + ", Perimeter: " + perimeter : "Wrong value!";

        System.out.println(result);

    }
}
