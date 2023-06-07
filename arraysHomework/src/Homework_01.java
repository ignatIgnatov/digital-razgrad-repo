import java.util.Scanner;

public class Homework_01 {

    public static void printReverseArray(String[] array) {

        for (int i = array.length - 1; i >= 0 ; i--) {
            System.out.print(array[i] + " ");
        }
    }

    public static void main(String[] args) {

        //масив от текст, разделен с интервали, прочетен от конзолата
//    Scanner scanner = new Scanner(System.in);
//    String[] text = scanner.nextLine().split(" ");


        String[] array = {"this", "is", "my", "test", "sentence"};
        printReverseArray(array);
    }
}
