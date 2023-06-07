public class Homework_03 {

    public static int[][] multiplyTwoMatrix(int[][] firstMatrix, int[][] secondMatrix) {

        if (firstMatrix[0].length != secondMatrix.length && firstMatrix.length != 1) {
            throw new UnsupportedOperationException(" *** Error! Incompatible Matrices *** ");
        }
        else if (firstMatrix.length == 1 && firstMatrix[0].length == 1) {
            throw new IllegalArgumentException(" *** This is a method of multiplying two compatible matrices. To multiply a number with a matrix, use another method! ***");
        }

        int[][] resultMatrix = new int[firstMatrix.length][];

            for (int i = 0; i < firstMatrix.length; i++) {

                int[] currentRow = firstMatrix[i];
                int[] tempArr = new int[secondMatrix[0].length];

                for (int j = 0; j < secondMatrix[0].length; j++) {

                    int[] currentCol = new int[secondMatrix.length];

                    for (int k = 0; k < secondMatrix.length; k++) {
                        currentCol[k] = secondMatrix[k][j];
                    }

                    for (int k = 0; k < currentCol.length; k++) {
                        tempArr[j] += currentRow[k] * currentCol[k];
                    }
                }

                resultMatrix[i] = tempArr;
            }

        return resultMatrix;
    }

    public static void main(String[] args) {

        int[][] firstMatrix = {

                //Пример за грешка - невалидни матрици
//                {1, 2, 3},
//                {4, 5, 6},
//                {7, 8, 9}

                //Пример 5
//                {2}

                //Пример 4
//                {4},
//                {5},
//                {6}

                //Пример 3
//                {1, 2, 3}

                //Пример 2
//                {3, 4, 2}

                //Ппример 1
                {1, 2, 3},
                {4, 5, 6},
        };

        int[][] secondMatrix = {

                //Пример за грешка - невалидни матрици
//                {1, 2, 3, 4},
//                {5, 6, 7, 8}

                //Пример 5
//                {1, 2, 3},
//                {4, 5, 6}

                //Пример 4
//                {1, 2, 3}

                //Пример 3
//                {4},
//                {5},
//                {6}

                //Пример 2
//                {13, 9, 7, 15},
//                {8, 7, 4, 6},
//                {6, 4, 0, 3}

                //Пример 1
                {7, 8},
                {9, 10},
                {11, 12}
        };

        int[][] matrix = multiplyTwoMatrix(firstMatrix, secondMatrix);

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}
