import java.util.Scanner;

public class Homework_03 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter points between 1 and 9: ");
        int points = scanner.nextInt();

        switch (points) {
            case 1:
            case 2:
            case 3:
                points += points * 10;
                System.out.println(points);
                break;
            case 4:
            case 5:
            case 6:
                points += points * 100;
                System.out.println(points);
                break;
            case 7:
            case 8:
            case 9:
                points += points * 1000;
                System.out.println(points);
                break;
            default:
                System.out.println("Error! Enter correct number!");

        }
    }
}
