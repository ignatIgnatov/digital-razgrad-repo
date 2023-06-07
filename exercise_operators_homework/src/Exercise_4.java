import java.util.Scanner;

public class Exercise_4 {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("*** Ball info: ***");

        System.out.print("Ball material: ");
        String material = scanner.nextLine();

        System.out.print("Ball radius /cm/: ");
        int radius = scanner.nextInt();

        System.out.print("Ball weight /kg/: ");
        double weight = scanner.nextDouble();

        boolean metal = "metal".equals(material);
        boolean plastic = "plastic".equals(material);
        boolean ballRadius = radius <= 5;
        boolean ballWeight = weight >= 1;

        System.out.println();

        String nonSuitableMessage = "This ball is not suitable for children under 3 years of age!";
        String suitableMessage = "This ball is suitable for children under 3 years of age!";

        String message = metal || plastic || ballRadius || ballWeight ? nonSuitableMessage : suitableMessage;

        System.out.println(message);


    }
}
