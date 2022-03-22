package contacts;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Contact {
    private String name;
    private String phoneNumber;
    private LocalDateTime created;
    private LocalDateTime edit;


    public Contact (String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.created = LocalDateTime.now();
        this.edit = LocalDateTime.now();
    }

    public String[] getAllEditableFields() {
        return new String[] {"name", "number", "created", "lastModified"};
    }


    public void setPhoneNumber(String phoneNumber) {
        if (validNumber(phoneNumber)) {
            this.phoneNumber = phoneNumber;
        } else {
            System.out.println("Wrong number format!");
            this.phoneNumber = "";
        }

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        if (hasNumber()) {
            return phoneNumber;
        }
        return "[no number]";
    }

    private boolean validNumber(String number) {
        String text = "((\\+?(([a-zA-z0-9]+[- ][a-zA-z0-9]{2,})|([a-zA-z0-9]+[- ]\\([a-zA-z0-9]{2,}\\))|(\\([a-zA-z0-9]+\\)[- ][a-zA-z0-9]{2,})))|(\\+?)\\(?[a-zA-Z0-9]+\\)?)([- ][a-zA-z0-9]{2,})*";
        Pattern pattern = Pattern.compile(text);
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }

    public boolean hasNumber() {
        return !"".equals(phoneNumber);
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getEdit() {
        return edit;
    }

    public void setEdit(LocalDateTime edit) {
        this.edit = edit;
    }

    public abstract String infoName();

    @Override
    public String toString() {
        return "Number: " + phoneNumber +
                "\nTime created: " + created +
                "\nTime last edit: " + edit;
    }

    public abstract void editField(String field, String value);

    public abstract String getField(String name);
}
