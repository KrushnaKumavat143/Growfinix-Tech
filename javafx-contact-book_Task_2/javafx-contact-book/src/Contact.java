import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Contact {
    private final StringProperty name = new SimpleStringProperty(this, "name", "");
    private final StringProperty phone = new SimpleStringProperty(this, "phone", "");
    private final StringProperty email = new SimpleStringProperty(this, "email", "");

    public Contact() {}

    public Contact(String name, String phone, String email) {
        this.name.set(name);
        this.phone.set(phone);
        this.email.set(email);
    }

    public String getName() { return name.get(); }
    public void setName(String value) { name.set(value); }
    public StringProperty nameProperty() { return name; }

    public String getPhone() { return phone.get(); }
    public void setPhone(String value) { phone.set(value); }
    public StringProperty phoneProperty() { return phone; }

    public String getEmail() { return email.get(); }
    public void setEmail(String value) { email.set(value); }
    public StringProperty emailProperty() { return email; }
}
