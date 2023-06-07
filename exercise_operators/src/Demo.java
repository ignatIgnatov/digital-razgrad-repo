import java.util.Scanner;

public class Demo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int points = 0;
        //First question.
        System.out.println("First question?");
        System.out.println("true or false:");
        String questions1 = scanner.next();
        points = ("true".equals(questions1)) ? ++points : --points;
        //Second question.
        System.out.println("Second question?");
        System.out.println("true or false:");
        String questions2 = scanner.next();
        points = ("true".equals(questions2)) ? ++points : --points;
        //Third question.
        System.out.println("Third question?");
        System.out.println("true or false:");
        String questions3 = scanner.next();
        points = ("true".equals(questions3)) ? ++points : --points;
        //Fourth question.
        System.out.println("Fourth question?");
        System.out.println("true or false:");
        String questions4 = scanner.next();
        points = ("true".equals(questions4)) ? ++points : --points;
        //Fifth question.
        System.out.println("Fifth question?");
        System.out.println("true or false:");
        String questions5 = scanner.next();
        points = ("true".equals(questions5)) ? ++points : --points;
        //Bonus Points
        String bonus = (points > (5/*becouse i have 5 questions*/ / 2)) ? "You Win" : "You Lost";
        System.out.println("Your score is : " + points + "\n" + bonus);
    }
}
