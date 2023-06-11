import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Homework_02 {

    public static void printNumbers(Set<Integer> numbers) {
        numbers.forEach(e -> System.out.print(e + " "));
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a number: ");
        int number = scanner.nextInt();
        Set<Integer> numbers = new HashSet<>();

        while (number != 0) {
            numbers.add(number);

            System.out.println("Enter another number: ");
            number = scanner.nextInt();
        }

        System.out.println("Result: ");
        printNumbers(numbers);
    }
}
