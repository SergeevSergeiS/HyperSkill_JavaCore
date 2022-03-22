package contacts;

public class Organization extends Contact{
    private String address;

    public Organization (String phoneNumber, String name, String address) {
        super(name, phoneNumber);
        this.address = address;
    }
    public String[] getAllEditableFields() {
        return new String[] {"name", "address", "number",  "created", "lastModified"};
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String infoName() {
        return this.getName();
    }

    @Override
    public String toString() {
        return "Organization name: " + super.getName() +
                "\nAddress: " + address +
                '\n' + super.toString();
    }

    @Override
    public void editField(String field, String value) {
        switch (field) {
            case "name":
                super.setName(value);
                break;
            case "address":
                setAddress(value);
                break;
            case "number":
                setPhoneNumber(value);
                break;
        }
    }

    @Override
    public String getField(String name) {
        switch (name) {
            case "name":
                return super.getName();
            case "address":
                return getAddress();
            case "number":
                return super.getPhoneNumber();
            default:
                return null;
        }
    }
}
