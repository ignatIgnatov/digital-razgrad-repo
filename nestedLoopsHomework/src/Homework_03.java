import java.util.Scanner;

public class Homework_03 {
    
    public static void printTriangle(int number) {

        for (int i = 1; i <= number; i++) {
            for (int j = i; j < number; j++) {
                System.out.print(" ");
            }
            for (int k = 0; k < i; k++) {
                System.out.print("O");
            }
            for (int l = 1; l < i; l++) {
                System.out.print("O");
            }
            System.out.println();
        }

    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a number:");
        int number = scanner.nextInt();

        printTriangle(number);
    }
}
