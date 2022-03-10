package converter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter two numbers in format: {source base} {target base} (To quit type /exit) ");
            String sourceBase = scanner.next();
            if (sourceBase.equals("/exit")) {
                break;
            }
            int targetBase = scanner.nextInt();
            programInterface(sourceBase, targetBase);
        }
    }

    public static void programInterface(String sourceBase, int targetBase) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.printf("Enter number in base %s to convert to base %s (To go back type /back) ",
                    sourceBase, targetBase);
            String number = scanner.next();
            if (number.equals("/back")) {
                System.out.println();
                break;
            }
            if (number.matches("\\w+\\.\\w+")) {
                String[] splitNumbers = number.split("\\.");
                double decimalNumber = decimalToTen(splitNumbers[1], Integer.parseInt(sourceBase));
                BigDecimal bigDecimalNumber = new BigDecimal(decimalNumber);
                BigDecimal scaledBigDecimalNumber = (bigDecimalNumber.setScale(5, RoundingMode.HALF_DOWN));
                BigInteger integerAsDecimal = conversionToDecimal(sourceBase, splitNumbers[0]);
                System.out.printf("Conversion result: %s\n\n", conversionFromDecimal(targetBase, integerAsDecimal) +
                        "." + decimalTo(scaledBigDecimalNumber.doubleValue(), targetBase));
                continue;

            }
            BigInteger decimal = conversionToDecimal(sourceBase, number);
            System.out.printf("Conversion result: %s\n\n", conversionFromDecimal(targetBase, decimal));
        }
    }

    public static String decimalTo(double number, int targetBase) {
        BigDecimal integerPortion;
        BigDecimal remainderPortion;
        BigDecimal transitionNumber = new BigDecimal(number);
        int count = 1;
        MathContext mc = new MathContext(25);
        StringBuilder toReturn = new StringBuilder();
        while (true) {
            BigDecimal bigNum = transitionNumber;
            BigDecimal bigTarget = new BigDecimal(targetBase);
            BigDecimal multiplyByTarget = bigTarget.pow(-count, mc);
            BigDecimal[] num = bigNum.divideAndRemainder(multiplyByTarget);
            integerPortion = num[0];
            remainderPortion = num[1];
            int integerConversion = integerPortion.intValue();
            toReturn.append(integerConversion > 9 ? toLetter(integerConversion) : integerConversion);
            transitionNumber = remainderPortion;
            count++;
            if (count > 5) {
                break;
            }
        }
        return toReturn.toString();
    }

    public static BigInteger conversionToDecimal(String sourceBase, String number) {
        int source = Integer.parseInt(sourceBase);
        return new BigInteger(number, source);
    }


    public static String conversionFromDecimal(int targetBase, BigInteger decimal) {
        return decimal.toString(targetBase);

    }


    public static double decimalToTen(String number, int sourceBase) {
        String[] splitDecimal = number.split("");
        char[] charDecimal = Arrays.toString(splitDecimal).toCharArray();
        StringBuilder toDecimal = new StringBuilder();
        for (char x: charDecimal) {
            if (String.valueOf(x).matches("\\d")) {
                toDecimal.append(x).append(",");
            } else if (String.valueOf(x).matches("\\w")) {
                int letterToNum = Character.getNumericValue(x);
                toDecimal.append(letterToNum).append(",");
            }
        }
        String[] x = toDecimal.toString().split(",");
        double sum = 0;
        for (int i = 0; i < x.length; i++) {
            sum += Integer.parseInt(x[i]) * Math.pow(sourceBase, - (i + 1));
        }
        return sum;
    }

    public static String toLetter(int number) {
        if (number < 10) {
            return String.valueOf(number);
        }
        switch (number) {
            case 10:
                return "a";
            case 11:
                return "b";
            case 12:
                return "c";
            case 13:
                return "d";
            case 14:
                return "e";
            case 15:
                return "f";
            case 16:
                return "g";
            case 17:
                return "h";
            case 18:
                return "i";
            case 19:
                return "j";
            case 20:
                return "k";
            case 21:
                return "l";
            case 22:
                return "m";
            case 23:
                return "n";
            case 24:
                return "o";
            case 25:
                return "p";
            case 26:
                return "q";
            case 27:
                return "r";
            case 28:
                return "s";
            case 29:
                return "t";
            case 30:
                return "u";
            case 31:
                return "v";
            case 32:
                return "w";
            case 33:
                return "x";
            case 34:
                return "y";
            default:
                return "z";
        }
    }
}
