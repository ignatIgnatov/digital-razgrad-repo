import java.sql.SQLOutput;

public class Exercise_02 {

    public static void printSumsOfRosOrCols(double[][] matrix, String type) {


        switch (type) {
            case "rows":
                for (int i = 0; i < matrix.length; i++) {
                    double sum = 0;
                    for (int j = 0; j < matrix[0].length; j++) {
                        sum += matrix[i][j];
                    }
                    System.out.println(sum);
                }
                break;
            case "cols":
                for (int i = 0; i < matrix[0].length; i++) {
                    double sum = 0;
                    for (int j = 0; j < matrix.length; j++) {
                        sum += matrix[j][i];
                    }
                    System.out.println(sum);
                }
                break;
        }
    }

    public static void main(String[] args) {

        double[][] matrix = {
                {1, 2.5, 3, 4.2},
                {5, 6, 7.1, 8},
                {9, 10, 11, 12}
        };

        printSumsOfRosOrCols(matrix, "rows");
        printSumsOfRosOrCols(matrix, "cols");
    }
}
