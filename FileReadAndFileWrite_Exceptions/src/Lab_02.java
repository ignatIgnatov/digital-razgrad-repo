import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Lab_02 {
    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new FileReader("names.txt"));
        String line = "";
        StringBuilder sb = new StringBuilder();

        try {

            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line).append(", ");
            }

            String[] result = sb.toString().split(", ");

            for (int i = 0; i < result.length; i++) {
                System.out.println(result[i]);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            bufferedReader.close();
        }
    }
}
