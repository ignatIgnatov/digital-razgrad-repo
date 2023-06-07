public class Lab_02 {

    public static String[] convertTextToArray(String text) {

        return text.split(" ");
    }

    public static void main(String[] args) {


        String text = "This is new text";

        String[] result = convertTextToArray(text);

        for (int i = 0; i < result.length; i++) {
            System.out.print(result[i] + " ");
        }




    }
}
