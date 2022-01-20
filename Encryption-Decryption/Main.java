package encryptdecrypt;

import java.util.Arrays;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.io.PrintWriter;

// Класс, считывающию информацию из файла
class Input {
    String data;
    public Input(String task) {
        File file = new File(task);
        try (Scanner scanner = new Scanner(file)) {
            data = scanner.nextLine();
        } catch (FileNotFoundException e) {
            System.out.println("No file found: " + task);
        }
    }
}

// Класс, разбивающий переданные аргументы на переменные
class Parser {
    boolean fromFile;
    String operation;
    String task;
    String path;
    String algorithm;
    int step;

    public Parser(String[] args) {
        operation = Arrays.asList(args).contains("-mode") ?
                args[Arrays.asList(args).indexOf("-mode") + 1] : "enc";
        if (Arrays.asList(args).contains("-in")) {
            task = args[Arrays.asList(args).indexOf("-in") + 1];
            fromFile = true;
        }
        if (Arrays.asList(args).contains("-data")) {
            task = args[Arrays.asList(args).indexOf("-data") + 1];
            fromFile = false;
        }
        path = Arrays.asList(args).contains("-out") ?
                args[Arrays.asList(args).indexOf("-out") + 1] : "";
        algorithm = Arrays.asList(args).contains("-alg") ?
                args[Arrays.asList(args).indexOf("-alg") + 1] : "shift";
        step = Arrays.asList(args).contains("-key") ?
                Integer.parseInt(args[Arrays.asList(args).indexOf("-key") + 1]) : 0;
        step = step != 0 ? "enc".equals(operation)? step : - step : 0;
        if (fromFile) {
            Input input = new Input(task);
            task = input.data;
        }
    }
}

//Абстрактный класс для задания и сдвига
abstract class Text {
    String task;
    int step;

    public Text(String task, int step) {
        this.task = task;
        this.step = step;
    }

    abstract String encrypted();
}

//Реализация алгоритма Shift
class ShiftedText extends Text {
    public ShiftedText(String task, int step) {
        super(task, step);
    }

    @Override
    public String encrypted() {
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        String result = "";
        for (int i = 0; i < task.length(); i++) {
            if (alphabet.contains(Character.toString(task.charAt(i)))) {
                if (step >= 0) {
                    result += alphabet.charAt((alphabet.indexOf(String.valueOf(task.charAt(i))) + step) % 26);
                } else {
                    result += alphabet.charAt((26 + alphabet.indexOf(String.valueOf(task.charAt(i))) + step) % 26);
                }
            } else {
                result += task.charAt(i);
            }
        }
        return result;
    }
}

//Реализация алгоритма Unicode
class UnicodedText extends Text {
    public UnicodedText(String task, int step) {
        super(task, step);
    }

    @Override
    public String encrypted() {

        String result = "";
        char[] chars = task.toCharArray();
        for (char symbol : chars) {
            result += Character.toString(symbol + step);
        }
        return result;
    }
}

// Factory method
class TextFactory {
    public static Text encrypt(String task, String algorithm, int step) {
        switch (algorithm) {
            case "shift":
                return new ShiftedText(task, step);
            case "unicode":
                return new UnicodedText(task, step);
            default:
                return null;
        }
    }
}

// Класс для записи в файл/вывода на экран
class Output {
    static String text;

    public Output(String text) {
        this.text = text;
    }

    public static void run(String path) {
        if ("".equals(path)) {
            System.out.println(text);
        } else {
            File file = new File(path);
            try (PrintWriter printWriter = new PrintWriter(file)) {
                printWriter.print(text);
            } catch (IOException e) {
                System.out.printf("An exception occurred %s", e.getMessage());
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Parser parser = new Parser(args);
        TextFactory textFactory = new TextFactory();
        Text text = textFactory.encrypt(parser.task, parser.algorithm, parser.step);
        Output output = new Output(text.encrypted());
        output.run(parser.path);
    }
}
