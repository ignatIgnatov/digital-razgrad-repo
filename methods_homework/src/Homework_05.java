import java.time.LocalDate;
import java.time.Year;
import java.util.Scanner;

public class Homework_05 {

    public static void checkLeapYear(int number) {

        if (number > 0 && number <= LocalDate.now().getYear()) {

            int days = Year.of(number).length(); //взимаме броя на дните от годината

            System.out.println(days);

            if (days == 366) {
                System.out.printf("The year %d is leap", number);
            } else {
                System.out.printf("The year %d is not leap", number);
            }
        } else {
            System.out.println("Error! Enter valid year!");
        }

    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a year: ");
        int year = scanner.nextInt();

        checkLeapYear(year);
    }
}
