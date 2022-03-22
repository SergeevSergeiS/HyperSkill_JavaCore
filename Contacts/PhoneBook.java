package contacts;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class PhoneBook implements Serializable {
    private List<Contact> contactsList;
    private static final long serialVersionUID = 1L;

    public PhoneBook() {
        contactsList = new ArrayList<>();
    }

    public List<Contact> getContactsList() {
        return contactsList;
    }

    public void addPerson(String name, String surname, String phone, String birthDate, String gender) {
        Contact added = new Person(phone, name, surname, birthDate, gender);
        contactsList.add(added);
        System.out.println("The record added.\n");
    }

    public void addOrganization(String name, String phone, String address) {
        Contact added = new Organization(phone, name, address);
        contactsList.add(added);
        System.out.println("The record added.\n");
    }

    public void remove(int num) {
        if (num > 0 && num < contactsList.size()) {
            contactsList.remove(num);
            System.out.println("The record removed!");
        } else {
            System.out.println("Wrong record number!");
        }
    }


    public void list() {
        for (int i = 0; i < this.contactsList.size(); i++) {
            System.out.println(i + 1 + ". " + this.contactsList.get(i).infoName());
        }
    }

    public void editRecord(int num, Scanner scanner) {
        if (num > 0 && num < contactsList.size() + 1) {
            String[] fields = contactsList.get(num - 1).getAllEditableFields();
            System.out.print("Select a field (");
            for (String field : fields) {
                System.out.print(field + ", ");
            }
            System.out.print(")");
            String field = scanner.nextLine();
            if (Arrays.asList(fields).contains(field)) {
                if (field.equals("name")) {
                    System.out.println("Enter name: ");
                    Contact newContact = contactsList.get(num - 1);
                    newContact.editField("name", scanner.nextLine());
                    newContact.setEdit(LocalDateTime.now());
                    contactsList.set(num - 1, newContact);
                } else if (field.equals("surname")) {
                    System.out.println("Enter surname: ");
                    Person newPerson = (Person) contactsList.get(num - 1);
                    newPerson.editField("surname", scanner.nextLine());
                    newPerson.setEdit(LocalDateTime.now());
                    contactsList.set(num - 1, newPerson);
                } else if (field.equals("number")) {
                    System.out.println("Enter number: ");
                    Contact newContact = contactsList.get(num- 1);
                    newContact.editField("number", scanner.nextLine());
                    newContact.setEdit(LocalDateTime.now());
                    contactsList.set(num - 1, newContact);
                } else if (field.equals("address")) {
                    System.out.println("Enter address: ");
                    Organization newOrganization = (Organization) contactsList.get(num - 1);
                    newOrganization.editField("address", scanner.nextLine());
                    newOrganization.setEdit(LocalDateTime.now());
                    contactsList.set(num - 1, newOrganization);
                } else if (field.equals("birth")) {
                    System.out.println("Enter birth date: ");
                    Person newPerson = (Person) contactsList.get(num - 1);
                    newPerson.editField("birth", scanner.nextLine());
                    newPerson.setEdit(LocalDateTime.now());
                    contactsList.set(num - 1, newPerson);
                } else if (field.equals("gender")) {
                    System.out.println("Enter gender: ");
                    Person newPerson = (Person) contactsList.get(num - 1);
                    newPerson.editField("gender", scanner.nextLine());
                    newPerson.setEdit(LocalDateTime.now());
                    contactsList.set(num - 1, newPerson);
                } else {
                    System.out.println("Wrong field name!");
                }
            } else {
                System.out.println("Wrong record number!");
            }
        }
    }

    public int count() {
        return contactsList.size();
    }

    public Contact get(int index) {
        return contactsList.get(index);
    }

    public static void saveToFile(PhoneBook data, File filename) {
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(filename);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static PhoneBook loadTheFile(File filename) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(filename);
            ObjectInputStream bis = new ObjectInputStream(fis);
            return (PhoneBook) bis.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

}
