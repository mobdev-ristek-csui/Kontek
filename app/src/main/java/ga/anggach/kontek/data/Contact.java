package ga.anggach.kontek.data;

/**
 * Created by master on 11/17/2017.
 */

public class Contact {
    private static final String TAG = Contact.class.getSimpleName();

    private String name;
    private String phone_number;
    private String email_address;

    public Contact(String name, String phone_number, String email_address){
        this.name = name;
        this.phone_number = phone_number;
        this.email_address = email_address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone_number;
    }

    public void setPhone(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail() {
        return email_address;
    }

    public void setEmail(String email_address) {
        this.email_address = email_address;
    }
}
