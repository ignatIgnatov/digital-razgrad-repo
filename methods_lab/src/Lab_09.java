import java.util.Scanner;

public class Lab_09 {

    public static void printAreaAndPerimeterOfRectangle(int sideA, int sideB) {

        int perimeter = 2 * sideA + 2 * sideB;
        int area = sideA * sideB;

        System.out.println("Perimeter: " + perimeter + ", Area: " + area);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter sideA: ");
        int sideA = scanner.nextInt();

        System.out.println("Enter sideB: ");
        int sideB = scanner.nextInt();

        printAreaAndPerimeterOfRectangle(sideA, sideB);
    }
}
