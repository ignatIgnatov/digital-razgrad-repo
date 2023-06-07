public class Student {
    public static void main(String[] args) {

        double firstGrade = 5.55;
        double secondGrade = 4.5;
        double thirdGrade = 4.35;

        long averageGrade = Math.round((firstGrade + secondGrade + thirdGrade) / 3);

        System.out.println(averageGrade);
    }
}
