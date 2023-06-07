import java.util.Scanner;

public class Homework_05 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a grade: ");
        int grade = scanner.nextInt();

        String gradeWord = "";

        switch (grade) {
            case 2:
                gradeWord = "two";
                break;
            case 3:
                gradeWord = "three";
                break;
            case 4:
                gradeWord = "four";
                break;
            case 5:
                gradeWord = "five";
                break;
            case 6:
                gradeWord = "six";
                break;
            default:
                gradeWord = "Error! Invalid grade! Enter correct grade!";

        }

        System.out.println(gradeWord);
    }
}
