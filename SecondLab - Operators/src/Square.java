import java.util.Scanner;

public class Square {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);


        double a = Double.parseDouble(scanner.nextLine());

        double area = a * a;

        double perimeter = 4 * a;

        System.out.println(area);
        System.out.println(perimeter);
    }
}
