public class Exercise_04 {

    public static void printTriangleOfDollars(int n) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i + 1; j++) {
                System.out.print("$ ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        printTriangleOfDollars(4);
    }
}
