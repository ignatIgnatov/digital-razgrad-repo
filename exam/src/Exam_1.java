import java.util.Scanner;

public class Exam_1 {

    public static double getPaintingArea(double width, double length) {
        return (width * 2.8 + length * 2.8) * 2;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter width: ");
        double width = Double.parseDouble(scanner.nextLine());

        System.out.print("Enter high: ");
        double length = Double.parseDouble(scanner.nextLine());

        System.out.print("Enter a buckets with paint: ");
        int buckets = Integer.parseInt(scanner.nextLine());

        double paintingArea = getPaintingArea(width, length);

        double difference = Math.abs(paintingArea - buckets);

        if (Math.round(paintingArea) > buckets) {
            System.out.printf("You need %d buckets more", Math.round(difference));
        } else if (Math.round(paintingArea) < buckets) {
            System.out.printf("You have %d extra buckets", Math.round(difference));
        } else {
            System.out.println("You have enough buckets");
        }




    }
}
