public class Lab_01 {

    public static int returnPositionOfSecondText(String firstText, String secondText) {

        if (firstText == null || secondText == null) {
            return -5;
        } else if (firstText.length() == 0 || secondText.length() == 0) {
            return -3;
        }

       return firstText.indexOf(secondText);
    }

    public static void main(String[] args) {

        String first = null;
        String second = "Ignat";

        System.out.println(returnPositionOfSecondText(first, second));
    }
}
