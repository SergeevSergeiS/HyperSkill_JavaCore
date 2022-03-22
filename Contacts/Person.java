package contacts;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Person extends Contact{
    private String surname;
    private String birthDay;
    private String gender;

    public Person (String phoneNumber, String name, String surname, String birthDay, String gender) {
        super(name, phoneNumber);
        this.surname = surname;
        this.birthDay = birthDay;
        this.gender = gender;
    }

    public String[] getAllEditableFields() {
        return new String[] {"name", "surname", "birth",  "gender", "number"};
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSurname() {
        return surname;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        if (gender.equals("F") || gender.equals("M")) {
            this.gender = gender;
        } else {
            this.gender ="[no date]";
            System.out.println("Bad gender!");
        }

    }

    @Override
    public String infoName() {
        return this.getName() + " " + this.getSurname();
    }

    @Override
    public String toString() {
        return "Name: " + getName() +
                "\nSurname: " + surname +
                "\nBirth date: " + (birthDay == null || birthDay.equals("") ? "[no data]" : birthDay)+
                "\nGender: " + (gender == null || gender.equals("") ? "[no data]" : gender)+
                '\n' + super.toString();
    }

    @Override
    public void editField(String field, String value) {
        switch (field) {
            case "name":
                super.setName(value);
                break;
            case "surname":
                setSurname(value);
                break;
            case "birth":
                try {
                    setBirthDay(value);
                }catch (DateTimeParseException e){
                    System.out.println("Bad birth date!");
                }
                break;
            case "gender":
                setGender(value);
                break;
            case "number":
                super.setPhoneNumber(value);
                break;
        }
    }

    @Override
    public String getField(String name) {
        switch (name) {
            case "name":
                return super.getName();
            case "number":
                return super.getPhoneNumber();
            case "surname":
                return getSurname();
            case "birth":
                return getBirthDay().toString();
            case "gender":
                return getGender();
            default:
                return null;
        }
    }
}
