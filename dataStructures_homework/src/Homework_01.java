import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Homework_01 {

    public static void printList(List<Integer> numbers) {
        numbers.forEach(e -> System.out.print(e + " "));
    }

    public static void printReversedList(List<Integer> numbers) {
        Collections.reverse(numbers);
        numbers.forEach(e -> System.out.print(e + " "));
    }

    public static List<Integer> generateList(int n) {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            if (i % 2 == 0 || i % 5 == 0) {
                numbers.add(i);
            }
        }
        return numbers;
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();

        List<Integer> numbers = generateList(n);

        System.out.println("List elements:");
        printList(numbers);

        System.out.println();

        System.out.println("Reversed List elements:");
        printReversedList(numbers);

    }
}
