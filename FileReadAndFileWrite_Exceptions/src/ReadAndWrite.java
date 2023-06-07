import java.io.*;
import java.util.Scanner;

public class ReadAndWrite {
    private static String readFile(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line = "";
        String restulText = "";
        try {
            while ((line = reader.readLine()) != null) {
                restulText += line + "\n";
            }
            return restulText;
        } finally {
            reader.close();
        }
    }


    public static void main(String[] args)  {
        try{
            String result = readFile("test.txt");
            System.out.println(result);
        } catch (IOException ex) {
            System.out.println(ex);
        }

        try{
            File file = new File("test.txt");
            Scanner sc = new Scanner(file, "utf-8");
            int lineNumber = 0;
            while(sc.hasNextLine()) {
                lineNumber++;
                System.out.println(lineNumber + ": " + sc.nextLine());
            }
            sc.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }


        try {
            PrintStream ps = new PrintStream("numbers1.txt");
            for (int i = 1; i < 21; i++) {
                ps.print(i);
                ps.print(" ");
            }
            ps.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
