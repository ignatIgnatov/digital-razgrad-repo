public class Homework_01 {

    public static void printTriangle(){
        String space = " ";
        String symbol = "@ ";
        System.out.println(space.repeat(6) + symbol);
        System.out.println(space.repeat(4) + symbol.repeat(2));
        System.out.println(space.repeat(2) + symbol.repeat(3));
        System.out.println(symbol.repeat(4));
    }

    public static void main(String[] args) {

        printTriangle();

    }
}
