import java.util.Scanner;

public class Stadium {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter the stadium name:");
        String name = scanner.nextLine();

        System.out.println("Please enter the capacity of the stadium:");
        int capacity = Integer.parseInt(scanner.nextLine());

        System.out.println("Please enter if the stadium has lightning (Yes / No):");
        String hasLights = scanner.nextLine();

        System.out.println("Please enter the area of the stadium: ");
        double area = Double.parseDouble(scanner.nextLine());


        System.out.println("Stadium name: " + name);
        System.out.println("Capacity: " + capacity);
        System.out.println("Has lightning: " + hasLights);
        System.out.println("Area: " + area);

    }
}
