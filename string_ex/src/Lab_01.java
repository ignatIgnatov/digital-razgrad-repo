import java.io.*;
import java.util.Random;

public class Lab_01 {
    public static void main(String[] args) throws IOException {

        File inputFile = new File("spisak.txt");
        String line = "";

        BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile));
        PrintStream printStream = new PrintStream("result.csv");
        printStream.print("First Name;Last Name;Class;Degree 1;Degree 2;Degree 3; Degree 4;Degree 5;" +
                "Degree 6;Degree 7;Degree 8; Degree 9;Degree 10; Degree 11;Degree 12;" +
                "Degree 13;Degree 14;Degree 15;Average Degree");
        printStream.print(System.lineSeparator());

        while ((line = bufferedReader.readLine()) != null) {

            int studentClass = 0;
            String classType = "";

            String[] array = line.split(" ");
            String firstName = array[0];
            String lastName = array[1];
            int age = Integer.parseInt(array[2]);

            if (age == 13 || age == 14) {
                studentClass = 8;
            } else {
                studentClass = 9;
            }

            if (firstName.length() + lastName.length() < 15) {
                classType = "A";
            } else {
                classType = "B";
            }

            String gender = "";

            char lastCharOfFirstName = firstName.charAt(firstName.length() - 1);
            if (lastCharOfFirstName == 'a' || lastCharOfFirstName == 'o' || lastCharOfFirstName == 'u'
            || lastCharOfFirstName == 'e' || lastCharOfFirstName == 'i') {
                gender = "girl";
            } else {
                gender = "boy";
            }

            int[] grades = new int[15];
            int sumOfGrades = 0;

            Random random = new Random();

            int grade = 0;
            for (int i = 0; i < grades.length; i++) {

                if (gender.equals("boy")) {
                    grade = random.nextInt(2, 7);
                } else {
                    grade = random.nextInt(4, 7);
                }

                grades[i] = grade;
                sumOfGrades += grade;
            }

            double averageDegree = sumOfGrades * 1.0 / grades.length;

            StringBuilder sb = new StringBuilder();
            sb.append(firstName).append(";");
            sb.append(lastName).append(";");
            sb.append(studentClass).append(classType).append(";");
            for (int i = 0; i < grades.length; i++) {
                sb.append(grades[i]).append(";");
            }
            sb.append(String.format("%.2f", averageDegree));

            printStream.print(sb.toString());
            printStream.print(System.lineSeparator());
        }

        printStream.close();
        bufferedReader.close();
    }
}
