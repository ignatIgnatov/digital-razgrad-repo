import java.util.Scanner;

public class _04_ {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int playerPoints = 0;
        String answer = "";
        int result = 0;

        System.out.println("The sky is blue");
        System.out.println("Answer: ");
        answer = scanner.nextLine();
        result = answer.equals("true") ? ++playerPoints : --playerPoints;

        System.out.println("Everest is the highest peak in the world");
        System.out.println("Answer: ");
        answer = scanner.nextLine();
        result = answer.equals("true") ? ++playerPoints : --playerPoints;

        System.out.println("Danube is the longest river in the world");
        System.out.println("Answer: ");
        answer = scanner.nextLine();
        result = answer.equals("true") ? --playerPoints : ++playerPoints;

        System.out.println("Michael Schumacher is a soccer player");
        System.out.println("Answer: ");
        answer = scanner.nextLine();
        result = answer.equals("true") ? --playerPoints : ++playerPoints;

        System.out.println("Ivet Lalova is a world weightlifting champion");
        System.out.println("Answer: ");
        answer = scanner.nextLine();
        result = answer.equals("true") ? --playerPoints : ++playerPoints;

        System.out.println("Razgrad is a city in northeastern Bulgaria");
        System.out.println("Answer: ");
        answer = scanner.nextLine();
        result = answer.equals("true") ? ++playerPoints : --playerPoints;


        System.out.println("Your score: " + playerPoints + " points");

        String finalResult = playerPoints > 3 ? "You win!" : "Game over!";

        System.out.println(finalResult);

    }
}
