public class Homework_04 {
    public static void main(String[] args) {

        for (int i = 1000; i <= 9999; i++) {

            int currentNumber = i;
            int firstSum = 0;
            int lastSum = 0;
            int counter = 0;

            while (currentNumber > 0) {
                counter++;

                if (counter <= 2) {
                    lastSum += currentNumber % 10;
                } else {
                    firstSum += currentNumber % 10;
                }

                currentNumber /= 10;
            }

            if (firstSum == lastSum) {
                System.out.println(i);
            }
        }
    }
}
