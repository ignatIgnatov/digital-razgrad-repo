import java.util.Scanner;

public class Homework_01 {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a number from 1 to 5");
        int index = scanner.nextInt();

        double[] array = new double[5];

        try {
            System.out.println("Result: " + array[index - 1]);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("**********************************");
        System.out.println("Second example");

        System.out.println("Enter a number from 1 to 5");
        int number = scanner.nextInt();
        double[] secondArray = new double[5];

        if (number - 1 < 0 || number - 1 >= secondArray.length) {
            throw new IllegalArgumentException("Your number is out of bound for length 5");
        } else {
            System.out.println("Result: " + secondArray[number - 1]);
        }


    }
}
