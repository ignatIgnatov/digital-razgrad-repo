import java.util.Scanner;

public class Lab_01 {

    public static void printMyFavoriteSong(String song) {
        System.out.println("My favorite song is " + song);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a favorite song: ");
        String song = scanner.nextLine();

        printMyFavoriteSong(song);
    }
}
