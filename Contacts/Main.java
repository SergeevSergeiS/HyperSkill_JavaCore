package contacts;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main implements Serializable {
    private static final long serialVersionUID = 1L;
    public static Scanner scanner = new Scanner(System.in);
    public static PhoneBook phoneBook = new PhoneBook();
    private static File file;

    public static void main(String[] args) {
        if (args.length > 0) {
            file = new File(args[0]);
            try {
                if (file.createNewFile()) {

                } else {
                    phoneBook = PhoneBook.loadTheFile(file);
                    System.out.println("open " + args[0]);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            file = null;
        }
        mainMenu();
    }

    public static void mainMenu() {
        while (true) {
            System.out.println("[menu] Enter action (add, list, search, count, exit):");
            String takeAction = scanner.nextLine();
            if (takeAction.equals("exit")) {
                return;
            } else {
                action(takeAction);
            }
        }
    }


    public static void recordMenu(int number) {
        System.out.println("[record] Enter action (edit, delete, menu):");
        String action = scanner.nextLine();
        if ("edit".equals(action)) {
            phoneBook.editRecord(number, scanner);
            System.out.println("Saved\n");
        } else if ("delete".equals(action)) {
            remove();
        } else if ("menu".equals(action)) {
            System.out.println();
            return;
        }
    }

    public static void listMenu() {
        System.out.println("[list] Enter action ([number], back):");
        String action = scanner.nextLine();
        Pattern digits = Pattern.compile("[0-9]+", Pattern.CASE_INSENSITIVE);
        if (action.equals("back")) {
            return;
        } else if (digits.matcher(action).find()) {
            int number = Integer.parseInt(action);
            if (number > 0 && number < phoneBook.count() + 1) {
                System.out.println(phoneBook.get(number - 1).toString());
            }
            System.out.println();
            recordMenu(number);
        } else {
            System.out.println("Unknown command!");
        }
    }


    public static void action(String takeAction) {
        switch (takeAction) {
            case "add":
                add();
                break;
            case "list":
                list();
                break;
            case "search":
                search();
                break;
            case "count":
                count();
                System.out.println();
                break;
        }
    }

    private static void search() {
        System.out.println("Enter search query:");
        String query = scanner.nextLine();
        ArrayList<Integer> foundRecords = new ArrayList<>();
        for (int i = 0; i < phoneBook.count(); i++) {
            String[] editableFields = phoneBook.get(i).getAllEditableFields();
            String data = "";
            for (int j = 0; j < editableFields.length; j++) {
                data += phoneBook.get(i).getField(editableFields[j]);
            }
            Pattern dataQuery = Pattern.compile(query, Pattern.CASE_INSENSITIVE);
            Matcher matcher = dataQuery.matcher(data);
            if (matcher.find()) {
                foundRecords.add(i);
            }
        }
        if (foundRecords.size() != 0) {
            System.out.println("Found " + foundRecords.size() + " results");
            for (int i : foundRecords) {
                if (phoneBook.get(i).getField("surname") != null) {
                    System.out.println(phoneBook.get(i).getField("name") + " " + phoneBook.get(i).getField("surname"));
                } else {
                    System.out.println(phoneBook.get(i).getField("name"));
                }
            }
        } else {
            System.out.println("Found 0 results");
        }
        System.out.println();

        System.out.println("[search] Enter action ([number], back, again):");
        String action = scanner.nextLine();
        Pattern digits = Pattern.compile("[0-9]+", Pattern.CASE_INSENSITIVE);
        if (action.equals("back")) {
            return;
        } else if (action.equals("again")) {
            search();
        } else if (digits.matcher(action).find()) {
            int index = foundRecords.get(Integer.parseInt(action) - 1);
            System.out.println(phoneBook.get(index).toString());
            recordMenu(index);
        } else {
            System.out.println("Unknown command!");
        }
    }

    private static void list() {
        phoneBook.list();
        System.out.println();
        listMenu();
    }

    private static void add() {
        System.out.println("Enter the type (person, organization):");
        String type = scanner.nextLine();
        if (type.equals("person")) {
            System.out.println("Enter the name:");
            String name = scanner.nextLine();
            System.out.println("Enter the surname:");
            String surname = scanner.nextLine();
            System.out.println("Enter the birth date:");
            String birthDate = scanner.nextLine();
            System.out.println("Enter the gender (M, F):");
            String gender = scanner.nextLine();
            System.out.println("Enter the number:");
            String phone = scanner.nextLine();
            phoneBook.addPerson(name, surname, phone, birthDate, gender);
            if (file != null) {
                PhoneBook.saveToFile(phoneBook, file);
            }
        } else if (type.equals("organization")) {
            System.out.println("Enter the organization name:");
            String name = scanner.nextLine();
            System.out.println("Enter the address:");
            String address = scanner.nextLine();
            System.out.println("Enter the number:");
            String phone = scanner.nextLine();
            phoneBook.addOrganization(name, phone, address);
            if (file != null) {
                PhoneBook.saveToFile(phoneBook, file);
            }
        } else {
            System.out.println("Wrong type!");
        }

    }

    public static void remove() {
        if (phoneBook.count() == 0) {
            System.out.println("No records to remove!\n");
        } else {
            phoneBook.list();
            System.out.println("Select a record:");
            int record = scanner.nextInt();
            String n = scanner.nextLine();
            phoneBook.remove(record - 1);
            if (file != null) {
                PhoneBook.saveToFile(phoneBook, file);
            }
            System.out.println("The record removed!\n");
        }
    }

    public static void count() {
        System.out.println("The Phone Book has " + phoneBook.count() + " records.\n");
    }

    //add for file
    public static void edit() {
        if (phoneBook.count() > 0) {
            phoneBook.list();
            System.out.println("Select a record:");
            int number = Integer.parseInt(scanner.nextLine());
            phoneBook.editRecord(number, scanner);
            if (file != null) {
                PhoneBook.saveToFile(phoneBook, file);
            }
            System.out.println("The record updated!");
        } else {
            System.out.println("No records to edit!");
        }
    }

}
