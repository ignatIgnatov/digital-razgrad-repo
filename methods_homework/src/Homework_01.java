import java.util.Scanner;

public class Homework_01 {

    public static void printWeekDays(int number) {
        String weekDay = "";

        switch (number) {
            case 1:
                weekDay = "Monday";
                break;
            case 2:
                weekDay = "Tuesday";
                break;
            case 3:
                weekDay = "Wednesday";
                break;
            case 4:
                weekDay = "Thursday";
                break;
            case 5:
                weekDay = "Friday";
                break;
            case 6:
                weekDay = "Saturday";
                break;
            case 7:
                weekDay = "Sunday";
                break;
            default:
                weekDay = "Maybe on another planet";

        }

        System.out.println(weekDay);
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a number between 1 and 7: ");
        int number = scanner.nextInt();

        printWeekDays(number);
    }
}
