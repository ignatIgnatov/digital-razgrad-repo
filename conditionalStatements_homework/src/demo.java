import java.util.Scanner;

public class demo {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String numberS = input.nextLine();
        int numberI = Integer.parseInt(numberS);
        String result = "";

        if (numberI <= 999 && numberI >= 0) {
            if (numberI == 0) result = "zero"; // 0
            else {
                //едноцифрено число
                if (numberS.length() == 1) {
                    char n1 = numberS.charAt(0);
                    if (n1 == '1') result = "one";
                    else if (n1 == '2') result = "two";
                    else if (n1 == '3') result = "three";
                    else if (n1 == '4') result = "four";
                    else if (n1 == '5') result = "five";
                    else if (n1 == '6') result = "six";
                    else if (n1 == '7') result = "seven";
                    else if (n1 == '8') result = "eight";
                    else if (n1 == '9') result = "nine";
                }

                //двуцифрени числа
                if (numberS.length() == 2) {
                    char n1 = numberS.charAt(0);
                    char n2 = numberS.charAt(1);

                    //числата от 10 до 19
                    if (n1 == '1') {
                        if (n2 == '1') result = "eleven";
                        if (n2 == '2') result = "twelve";
                        if (n2 == '3') result += "thirteen";
                        if (n2 == '4') result += "fourteen";
                        if (n2 == '5') result += "fifteen";
                        if (n2 == '6') result += "sixteen";
                        if (n2 == '7') result += "seventeen";
                        if (n2 == '8') result += "eighteen";
                        if (n2 == '9') result += "nineteen";
                        if (n2 == '0') result += "ten";
                    }

                    //числата от 20 до 99
                    else {
                        //десетици
                        if (n1 == '2') result += "twenty";
                        else if (n1 == '3') result += "thirty";
                        else if (n1 == '4') result += "forty";
                        else if (n1 == '5') result += "fifty";
                        else if (n1 == '6') result += "sixty";
                        else if (n1 == '7') result += "seventy";
                        else if (n1 == '8') result += "eighty";
                        else if (n1 == '9') result += "ninety";

                        //единици
                        if (n2 == '2') result += " two";
                        else if (n2 == '3') result += " three";
                        else if (n2 == '4') result += " four";
                        else if (n2 == '5') result += " five";
                        else if (n2 == '6') result += " six";
                        else if (n2 == '7') result += " seven";
                        else if (n2 == '8') result += " eight";
                        else if (n2 == '9') result += " nine";
                    }
                }

                //трицифрени числа
                if (numberS.length() == 3) {
                    char n1 = numberS.charAt(0);
                    char n2 = numberS.charAt(1);
                    char n3 = numberS.charAt(2);

                    //Определяне на стотиците
                    if (n1 == '1') result = "one hundred";
                    else if (n1 == '2') result = "two hundred";
                    else if (n1 == '3') result = "three hundred";
                    else if (n1 == '4') result = "four hundred";
                    else if (n1 == '5') result = "five hundred";
                    else if (n1 == '6') result = "six hundred";
                    else if (n1 == '7') result = "seven hundred";
                    else if (n1 == '8') result = "eight hundred";
                    else if (n1 == '9') result = "nine hundred";

                    //Определяне на десетиците
                    if (n2 == '2') result += " and twenty";
                    else if (n2 == '3') result += " and thirty";
                    else if (n2 == '4') result += " and forty";
                    else if (n2 == '5') result += " and fifty";
                    else if (n2 == '6') result += " and sixty";
                    else if (n2 == '7') result += " and seventy";
                    else if (n2 == '8') result += " and eighty";
                    else if (n2 == '9') result += " and ninety";

                    //Определяне на едници без тези до 20
                    if (n2 != '1' && n2 != '0') {
                        if (n3 == '1') result += " one";
                        else if (n3 == '2') result += " two";
                        else if (n3 == '3') result += " three";
                        else if (n3 == '4') result += " four";
                        else if (n3 == '5') result += " five";
                        else if (n3 == '6') result += " six";
                        else if (n3 == '7') result += " seven";
                        else if (n3 == '8') result += " eight";
                        else if (n3 == '9') result += " nine";
                    }

                    //Определяне на десетиците и единиците от 10-19
                    if (n2 == '1') {
                        if (n3 == '1') result += " and eleven";
                        else if (n3 == '2') result += " and twelve";
                        else if (n3 == '3') result += " and thirteen";
                        else if (n3 == '4') result += " and fourteen";
                        else if (n3 == '5') result += " and fifteen";
                        else if (n3 == '6') result += " and sixteen";
                        else if (n3 == '7') result += " and seventeen";
                        else if (n3 == '8') result += " and eighteen";
                        else if (n3 == '9') result += " and nineteen";
                        else if (n3 == '0') result += " and ten";
                    }

                    //В случаите, когато цифрата на десетиците е 0
                    if (n2 == '0') {
                        if (n3 == '1') result += " and one";
                        else if (n3 == '2') result += " and two";
                        else if (n3 == '3') result += " and three";
                        else if (n3 == '4') result += " and four";
                        else if (n3 == '5') result += " and five";
                        else if (n3 == '6') result += " and six";
                        else if (n3 == '7') result += " and seven";
                        else if (n3 == '8') result += " and eight";
                        else if (n3 == '9') result += " and nine";
                    }
                }
            }
            System.out.println(result);
        } else {
            System.out.println("Invalid number");
        }
    }
}