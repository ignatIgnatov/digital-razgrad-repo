public class Lab_03 {

    public static String convertArrayToString(String[] array) {

        StringBuilder sb = new StringBuilder();

        sb.append(array[0]).append(System.lineSeparator());
        sb.append(array[1]).append(System.lineSeparator());
        sb.append(array[2]).append(System.lineSeparator());
        sb.append(array[3]).append(System.lineSeparator());
        sb.append(array[4]).append(System.lineSeparator());

        return sb.toString();
    }

    public static void main(String[] args) {


        String[] input = {
                "Ignat Ignatov",
                "Bulgaria",
                "Russe",
                "Galabets 217B",
                "7015"
        };

        System.out.println(convertArrayToString(input));

    }
}
