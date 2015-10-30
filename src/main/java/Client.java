import java.util.ArrayList;

/**
 * Created by User on 21.09.2015.
 */
public class Client {
    public static final String CLIENT_DB_NAME = "database_client.csv";

    private int ID;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String adress;
    private int phone;
    private String email;

    public Client(int ID, String firstName, String lastName, String dateOfBirth,
                  String adress, int phone, String email) {
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.adress = adress;
        this.phone = phone;
        this.email = email;
    }

    public int getID() {
        return ID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getAdress() {
        return adress;
    }

    public long getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
