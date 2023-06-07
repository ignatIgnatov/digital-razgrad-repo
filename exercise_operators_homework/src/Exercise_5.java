import java.util.Scanner;

public class Exercise_5 {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        String text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras in quam neque. Vivamus vitae finibus justo, nec rhoncus lorem. Nulla commodo pellentesque bibendum. Nam commodo leo et sagittis tempus. Vivamus ligula justo, efficitur at augue vitae, pellentesque luctus ante. Aenean interdum sem id ante molestie, in tincidunt sem blandit. Aenean euismod commodo quam, vel cursus mauris venenatis in. Fusce arcu eros, faucibus in erat vitae, mollis interdum risus. Fusce tristique sapien enim, et molestie enim elementum eget. Fusce dictum lobortis tellus vitae vestibulum.";

        System.out.print("Please enter a word: ");
        String word = scanner.nextLine();

        String message = text.contains(word) ? "The text contains the word " + word : "The text not contains the word " + word;

        System.out.println(message);
    }
}
