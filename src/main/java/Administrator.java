import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by User on 21.09.2015.
 */
public class Administrator {  //Singleton
    private static Administrator instance;

    private ArrayList<Client> clients;
    private ArrayList<Car> cars;
    private ArrayList<Order> orders;
    private DatabaseConnection dbManager;

    public static Administrator getInstance() {
        if (instance == null) {
            instance = new Administrator();
        }
        return instance;
    }

    private Administrator() { //when init we write all clients from DB to ArrayList
        clients = new ArrayList<Client>();
        cars = new ArrayList<Car>();
        orders = new ArrayList<Order>();
        dbManager = new DatabaseConnection(clients, cars, orders);
    }

    public ArrayList<Client> getClients() {
        return clients;
    }

    public ArrayList<Car> getCars() {
        return cars;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public DatabaseConnection getDbManager() {
        return dbManager;
    }

    boolean addNewClient(String firstName, String lastName, String DOB,
                      String adress, int phone, String email) {
        if (isRepeat(new Client(0, firstName, lastName, DOB, adress, phone, email))) return false;
        try {
            // TODO clients.add(new Client(ID, firstName, lastName, DOB, adress, phone, email)); ¿…ƒ»ÿÕ» »!!!!!!!!!!!!
            dbManager.addClient(firstName, lastName, DOB, adress, phone, email);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    boolean addNewCar(int VID, int clientID, String make, String model, String year) {
        try {
            cars.add(new Car(make, model, year, VID, clientID));
            dbManager.addCar(VID, clientID, make, model, year);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    boolean addNewOrder(int ID, int carVID, String date, int orderAmount, OrderStatus status) {
        try {
            orders.add(new Order(ID, carVID, date, orderAmount, status));
            dbManager.addOrder(ID, date, orderAmount, status, carVID);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    boolean isRepeat(Client client) {
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i).getFirstName().equals(client.getFirstName()) &&
                    clients.get(i).getLastName().equals(client.getLastName())) {
                return true;
            }
        }
        return false;
    } //Checks for Name and Surname match

    Car getCarForVID(int VID) {
        Car car = null;

        for (int i = 0; i < cars.size(); i++) {
            if (cars.get(i).getVID() == VID) return cars.get(i);
        }

        return car;
    }

    Client getClientForID(int ID) {
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i).getID() == ID) {
                return clients.get(i);
            }
        }
        return null;
    }

    Order getOrderForId(int orderID) {
        for (int i = 0; i < clients.size(); i++) {
            if (orders.get(i).getID() == orderID) {
                return orders.get(i);
            }
        }
        return null;
    }

    public boolean checkForIDMatch(int ID) {
        for (int i = 0; i < clients.size(); i++) {
            if (ID == clients.get(i).getID()) {
                return true;
            }
        }
        return false;
    }

    public boolean checkForVIDMatch(int VID) {
        if (getCarForVID(VID).equals(null)) {
            return false;
        }
        return true;
    }

    boolean isClientHasCars(int clientID) {
        for (int i = 0; i < cars.size(); i++) {
            if (cars.get(i).getClientID() == clientID) {
                return true;
            }
        }
        return false;
    }

    boolean isCarHasOrders(int VID) {
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getCarID() == VID) {
                return true;
            }
        }
        return false;
    }

}
