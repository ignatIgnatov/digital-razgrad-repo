public class Ex_01 {
    public static void main(String[] args) {

        String[][] companies = {
                {"rz1", "rz2", "rz3", "rz4", "rz5"},
                {"ruse1", "ruse2", "ruse3", "ruse4", "ruse5"}
        };


        for (int i = 0; i < companies.length; i++) {
            for (int j = 0; j < companies[0].length; j++) {
                System.out.print(companies[i][j] + " ");
            }
            System.out.println();
        }
    }
}
