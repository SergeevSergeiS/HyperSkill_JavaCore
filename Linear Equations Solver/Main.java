package solver;

import java.io.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

class InputParameters {
    private String importFile = "";
    private String exportFile = "";

    public String getImportFile() {
        return importFile;
    }

    public void setImportFile(String importFile) {
        this.importFile = importFile;
    }

    public String getExportFile() {
        return exportFile;
    }

    public void setExportFile(String exportFile) {
        this.exportFile = exportFile;
    }

    InputParameters(String[] args) {
        for (int i = 0; i < args.length; i += 2) {
            switch (args[i]) {
                case "-in":
                    this.setImportFile(args[i + 1]);
                    break;
                case "-out":
                    this.setExportFile(args[i + 1]);
                    break;
                default:
                    break;
            }
        }
    }
}

class ComplexNum {
    private static final double EPSILON = 0.001;
    private double real;
    private double imagine;

    ComplexNum(double real, double imagine) {
        this.real = real;
        this.imagine = imagine;
    }

    ComplexNum(String num) {
        String[] toParse = getTwoParts(num);

        if ("+".equals(toParse[1])) {
            toParse[1] = "1";
        }

        if ("-".equals(toParse[1])) {
            toParse[1] = "-1";
        }

        real = Double.parseDouble(toParse[0]);
        imagine = Double.parseDouble(toParse[1]);
    }

    private String[] getTwoParts(String num) {
        StringBuilder re = new StringBuilder();
        String im = "0";
        int length = num.length();

        if (length == 1) {
            if ("i".equals(num)) {
                return new String[]{"0", "1"};
            }

            return new String[]{num, "0"};
        }

        for (int i = 1; i < length; i++) {
            if (num.charAt(i) == '+' || num.charAt(i) == '-') {
                re.append(num, 0, i);
                im = num.substring(i, length - 1).replace("i", "");
                break;
            }

            else if (i == length - 1) {
                if (num.contains("i")) {
                    re.append("0");
                    im = num.substring(0, length - 1).replace("i", "");
                } else {
                    re.append(num);
                    im = "0";
                }
            }
        }

        return new String[]{re.toString(), im};
    }

    static ComplexNum sum(ComplexNum a, ComplexNum bi) {
        return new ComplexNum(a.real + bi.real, a.imagine + bi.imagine);
    }

    static ComplexNum multiply(ComplexNum a, ComplexNum bi) {
        return new ComplexNum(a.real * bi.real - a.imagine * bi.imagine, a.real * bi.imagine + a.imagine * bi.real);
    }

    static ComplexNum subtract(ComplexNum first, ComplexNum second) {
        return new ComplexNum(first.real - second.real, first.imagine - second.imagine);
    }

    static ComplexNum divide(ComplexNum top, ComplexNum bot) {
        ComplexNum temp = multiply(top, conjugate(bot));
        double divider = bot.real * bot.real + bot.imagine * bot.imagine;

        return new ComplexNum(temp.real / divider, temp.imagine / divider);
    }

    private static ComplexNum conjugate(ComplexNum a) {
        return new ComplexNum(a.real, -a.imagine);
    }

    boolean isZero() {
        return real == 0 && imagine == 0;
    }

    @Override
    public String toString() {
        final DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        final DecimalFormat realFormat = new DecimalFormat("0.####", symbols);
        final DecimalFormat imagFormat = new DecimalFormat("0.####i", symbols);

        if (Math.abs(imagine) < EPSILON) {
            return realFormat.format(real);
        }

        if (Math.abs(real) < EPSILON) {
            return imagFormat.format(imagine);
        }

        imagFormat.setPositivePrefix("+");

        return String.format("%s%s", realFormat.format(real), imagFormat.format(imagine));
    }
}

class LinearEquationSolver {
    private List<ComplexNum[]> matrix = new LinkedList<>();
    private StringBuilder answer = new StringBuilder();
    private int height;
    private int width;

    void fillRow(ComplexNum[] line) {
        matrix.add(line);
        height = matrix.size();
        width = line.length;
    }

    StringBuilder getAnswer() {
        return answer;
    }

    void solve() {
        solveDown();
        int solutions = numOfSolutions();

        if (solutions == -1) {
            answer.append("No solutions");
        } else if (solutions == 1) {
            answer.append("Infinitely many solutions");
        } else {
            solveUp();
            oneSolution();
        }
    }

    private void solveDown() {
        for (int i = 0; i < height - 1; i++) {
            ComplexNum[] topRow = matrix.get(i);

            if (zeroLine(topRow)) {
                matrix.remove(topRow);
                height = matrix.size();
            } else if (topRow[i].isZero()) {
                moveRow(i);
            }

            topRow = matrix.get(i);

            for (int j = i + 1; j < height; j++) {
                ComplexNum[] botRow = matrix.get(j);
                ComplexNum index = ComplexNum.divide(botRow[i], topRow[i]);

                for (int k = 0; k < width; k++) {
                    ComplexNum temp = ComplexNum.multiply(topRow[k], index);
                    botRow[k] = ComplexNum.subtract(botRow[k], temp);
                }
            }
        }
    }

    private void solveUp() {
        for (int i = height - 1; i > 0; i--) {
            ComplexNum[] botRow = matrix.get(i);

            for (int j = i - 1; j > -1; j--) {
                ComplexNum[] topRow = matrix.get(j);
                ComplexNum index = ComplexNum.divide(topRow[i], botRow[i]);

                for (int k = width - 1; k > i; k--) {
                    ComplexNum temp = ComplexNum.multiply(botRow[k], index);
                    topRow[k] = ComplexNum.subtract(topRow[k], temp);
                }
            }
        }
    }

    private boolean zeroLine(ComplexNum[] row) {
        for (ComplexNum num : row) {
            if (!num.isZero()) {
                return false;
            }
        }

        return true;
    }

    private void moveRow(int index) {
        while (matrix.get(index)[index].isZero()) {
            ComplexNum[] tempRow = matrix.get(index);
            matrix.remove(tempRow);
            matrix.add(tempRow);
        }
    }


    private int numOfSolutions() {
        matrix.removeIf(this::zeroLine);
        height = matrix.size();

        for (ComplexNum[] line : matrix) {
            if (noSolutions(line)) {
                return -1;
            }
        }

        if (height < width - 1) {
            return 1;
        }

        return 0;
    }


    private boolean noSolutions(ComplexNum[] line) {
        int numOfZeros = width - 1;

        for (int i = 0; i < width - 1; i++) {
            if (line[i].isZero()) {
                numOfZeros--;
            }
        }

        return numOfZeros == 0;
    }


    private void oneSolution() {

        for (int i = 0; i < height; i++) {
            ComplexNum a = matrix.get(i)[width - 1];
            ComplexNum b = matrix.get(i)[i];
            ComplexNum answ = ComplexNum.divide(a, b);
            answer.append(answ.toString()).append("\n");
        }
    }
}

class Reader {
    static void getLinEq(String fileName, LinearEquationSolver les)  {
        File file = new File(fileName);

        try (Scanner scanner = new Scanner(file)) {
            String ignoreThis = scanner.nextLine();

            while (scanner.hasNextLine()) {
                String[] temp = scanner.nextLine().split("\\s+");
                int length = temp.length;
                ComplexNum[] line = new ComplexNum[length];

                for (int i = 0; i < length; i++) {
                    line[i] = new ComplexNum(temp[i]);
                }
                les.fillRow(line);
            }
        } catch (FileNotFoundException e) {
            String message = "File not found.";
            System.out.println(message);
        }
    }
}

class Writer {
    static void AnswerToFile(String fileName, LinearEquationSolver les) {
        File file = new File(fileName);

        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.append(les.getAnswer());
        }
        catch (IOException e) {
            String message = String.format("An exception occurs %s", e.getMessage());

            System.out.println(message);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        InputParameters input = new InputParameters(args);
        LinearEquationSolver solver = new LinearEquationSolver();
        Reader.getLinEq(input.getImportFile(), solver);
        solver.solve();
        Writer.AnswerToFile(input.getExportFile(), solver);
    }
}
