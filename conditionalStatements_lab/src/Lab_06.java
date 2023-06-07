import java.util.Scanner;

public class Lab_06 {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int number = scanner.nextInt();
        String currentNumber = "" + number;

        int fourthNum = number % 10;
        number /= 10;
        int thirdNum = number % 10;
        number /= 10;
        int secondNum = number % 10;
        int firstNum = number / 10;


        String resultNumber = "" + fourthNum + thirdNum + secondNum + firstNum;

        if (currentNumber.equals(resultNumber)) {
            System.out.printf("Number %s is palindrome%n", currentNumber);
        } else {
            System.out.printf("Number %s is not a palindrome%n", currentNumber);
        }

//Втори начин
        StringBuilder sb = new StringBuilder(currentNumber);
        String reverse = sb.reverse().toString();

        if (sb.toString().equals(reverse)) {
            System.out.printf("Number %s is palindrome", currentNumber);
        } else {
            System.out.printf("Number %s is not a palindrome", currentNumber);
        }


    }
}
