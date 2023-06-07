public class Homework_02 {

    public static void magicSquare(int[][] squareTable) {

        int sum = 0; //тук запазвам първата изчислена сума, съответно това е сумата на първия ред от таблицата

        //изчисление на сумите на редовете
        for (int i = 0; i < squareTable.length; i++) {
            int rowSum = 0;

            for (int j = 0; j < squareTable.length; j++) {
                rowSum += squareTable[i][j];
            }

            if (i == 0) {
                sum = rowSum; //присвоявам стойност на първата изчислена сума
            } else {
                if (sum != rowSum) { //ако всяка следваща изчислена сума не съвпада с първата изчислена сума, програмата прекъсва
                    System.out.println("This square table is not a Magic table.");
                    return;
                }
            }
        }
        //изчисление на сумите на колоните
        for (int i = 0; i < squareTable.length; i++) {
            int colSum = 0; //тук запазвам сумата на съответната колона

            for (int j = 0; j < squareTable.length; j++) {
                colSum += squareTable[j][i];
            }

            if (sum != colSum) { //ако нямам съвпадение на изчислена сума на колонте с първата изчислена сума, програмата прекъсва
                System.out.println("This square table is not a Magic table.");
                return;
            }

        }
        //изчисление на първия диагонал - от горе на долу
        int index = 0;
        int firstDiagonalSum = 0;
        while (index < squareTable.length) {
            firstDiagonalSum += squareTable[index][index];
            index++;
        }

        if (sum != firstDiagonalSum) { //ако сумата на първия диагонал не съвпада с първата изчислена сума, програмата прекъсва

            System.out.println("This square table is not a Magic square.");

        } else {
            //изчисление на втория диагонал - от долу на горе
            int rowIndex = squareTable.length - 1;
            int colIndex = 0;
            int secondDiagonalSum = 0; //тук запазвам сумата на втория диагонал

            while (rowIndex >= 0) {
                secondDiagonalSum += squareTable[rowIndex][colIndex];
                rowIndex--;
                colIndex++;
            }

            if (sum != secondDiagonalSum) { //ако сумата на втория диагонал не съвпада с първата изчислена сума, програмата прекъсва
                System.out.println("This square table is not a Magic square.");
            } else { //щом програмата е стигнала до тук, значи имаме магически квадрат :)
                System.out.println("This table is Magic square!");
            }
        }
    }

    public static void main(String[] args) {

        int[][] matrix = {
                {16, 3, 2, 13},
                {5, 10, 11, 8},
                {9, 6, 7, 12},
                {4, 15, 14, 1}
        };


        magicSquare(matrix);
    }
}
