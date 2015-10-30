import java.util.ArrayList;

/**
 * Created by User on 21.09.2015.
 */
public class Car {
    public static final String CAR_DB_NAME = "database_car.csv";

    private String make;
    private String model;
    private String year;
    private int VID;
    private int clientID; //foreign key

    public Car(String make, String model, String year, int VID, int clientID) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.VID = VID;
        this.clientID = clientID;
    }

    public int getClientID() {
        return clientID;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getYear() {
        return year;
    }

    public int getVID() {
        return VID;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setVID(int VID) {
        this.VID = VID;
    }
}
