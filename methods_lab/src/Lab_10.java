import java.util.Scanner;

public class Lab_10 {

    public static int perimeterOfTriangle(int a, int b, int c) {
        return a + b + c;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter side A: ");
        int sideA = scanner.nextInt();

        System.out.print("Enter side B: ");
        int sideB = scanner.nextInt();

        System.out.print("Enter side C: ");
        int sideC = scanner.nextInt();

        int result = perimeterOfTriangle(sideA, sideB, sideC);

        System.out.println("Perimeter: " + result);
    }
}
