import com.mysql.fabric.jdbc.FabricMySQLDriver;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by User on 06.10.2015.
 */
public class DatabaseConnection {

    private final String URL;
    private final String USERNAME;
    private final String PASSWORD;

    Connection connection = null;
    Statement statement = null;


    public DatabaseConnection(ArrayList<Client> clients, ArrayList<Car> cars, ArrayList<Order> orders) {
        URL = "jdbc:mysql://localhost:3306/service_station_database";
        USERNAME = "root";
        PASSWORD = "root";
        initDriver();
        initArrayListsClients(clients);
        initArrayListCars(cars);
        initArrayListOrders(orders);
    }

    private void initDriver() {
        try {
            Driver driver = new FabricMySQLDriver();
            DriverManager.registerDriver(driver);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initArrayListsClients(ArrayList<Client> clients) {
        ResultSet clientValues = null;

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            statement = connection.createStatement();
            clientValues = statement.executeQuery("SELECT * FROM clients");

            while (clientValues.next()) {
                int idclients = clientValues.getInt("idclients");
                String firstname = clientValues.getString("firstname");
                String lastname = clientValues.getString("lastname");
                String dob = clientValues.getString("dob");
                String adress = clientValues.getString("adress");
                int phone = clientValues.getInt("phone");
                String email = clientValues.getString("email");
                clients.add(new Client(idclients, firstname, lastname, dob, adress, phone, email));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) { try { connection.close(); } catch (SQLException ex) {ex.printStackTrace();} }
            if (statement != null) { try { statement.close(); } catch (SQLException ex) {ex.printStackTrace();} }
            if (clientValues != null) { try { clientValues.close(); } catch (SQLException ex) {ex.printStackTrace();} }
        }

    }

    private void initArrayListCars(ArrayList<Car> cars) {
        ResultSet carsValues = null;

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            statement = connection.createStatement();
            carsValues = statement.executeQuery("SELECT * FROM cars");
            while (carsValues.next()) {
                int idclients = carsValues.getInt("idclients");
                int vid = carsValues.getInt("vid");
                String make = carsValues.getString("make");
                String model = carsValues.getString("model");
                String year = carsValues.getString("year");
                //Administrator.getInstance().getClientForID(idclients).getClientCars().add(new Car(make, model, year, vid, idclients));
                cars.add(new Car(make, model, year, vid, idclients));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) { try { connection.close(); } catch (SQLException ex) {} }
            if (statement != null) { try { statement.close(); } catch (SQLException ex) {} }
            if (carsValues != null) { try { carsValues.close(); } catch (SQLException ex) {} }
        }
    }

    private void initArrayListOrders(ArrayList<Order> orders) {
        ResultSet ordersValues = null;

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            statement = connection.createStatement();
            ordersValues = statement.executeQuery("SELECT * FROM orders");
            while (ordersValues.next()) {
                int idorders = ordersValues.getInt("idorders");
                int amount = ordersValues.getInt("amount");
                String date = ordersValues.getString("date");
                int carVID = ordersValues.getInt("carVID");
                String stringStatus = ordersValues.getString("status");

                OrderStatus status;
                if (stringStatus.equals("COMPLETED")) {
                    status = OrderStatus.COMPLETED;
                } else if (stringStatus.equals("IN_PROGRESS")) {
                    status = OrderStatus.IN_PROGRESS;
                } else {
                    status = OrderStatus.CANCELLED;
                }

                orders.add(new Order(idorders, carVID, date, amount, status));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) { try { connection.close(); } catch (SQLException ex) {} }
            if (statement != null) { try { statement.close(); } catch (SQLException ex) {} }
            if (ordersValues != null) { try { ordersValues.close(); } catch (SQLException ex) {} }
        }
    }

    public void addClient(String firstName, String lastName, String DOB, String adress, int phone, String email) {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO clients (firstname, lastname, dob, adress, phone, email) " +
                    "VALUES " + "('" + firstName + "', '" + lastName + "', '" + DOB + "', '" + adress + "', " + phone + ", '" + email + "')");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) { try { connection.close(); } catch (SQLException ex) {} }
            if (statement != null) { try { statement.close(); } catch (SQLException ex) {} }
        }
    }

    public void deleteClient(int ID) {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM clients WHERE idclients=" +ID);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) { try { connection.close(); } catch (SQLException ex) {} }
            if (statement != null) { try { statement.close(); } catch (SQLException ex) {} }
        }
    }

    public void updateClient(Client client) {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            statement = connection.createStatement();
            statement.executeUpdate("UPDATE clients SET firstname='" +client.getFirstName() +"', lastname='" +client.getLastName()
                    +"', dob='" +client.getDateOfBirth() +"', adress='" +client.getAdress() +"', phone=" +client.getPhone()
                    +", email='" +client.getEmail() +"' WHERE idclients=" +client.getID());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) { try { connection.close(); } catch (SQLException ex) {} }
            if (statement != null) { try { statement.close(); } catch (SQLException ex) {} }
        }
    }

    public void addCar(int VID, int clientID, String make, String model, String year){
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO cars (vid, idclients, make, model, year) " +
                    "VALUES " + "('" + VID + "', " + clientID + ", '" + make + "', '" + model + "', '" + year + "')");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) { try { connection.close(); } catch (SQLException ex) {} }
            if (statement != null) { try { statement.close(); } catch (SQLException ex) {} }
        }
    }

    public void deleteCar(int VID){
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM cars WHERE vid=" +VID);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) { try { connection.close(); } catch (SQLException ex) {} }
            if (statement != null) { try { statement.close(); } catch (SQLException ex) {} }
        }
    }

    public void updateCar(Car car){
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            statement = connection.createStatement();
            statement.executeUpdate("UPDATE cars SET vid='" +car.getVID() +"', idclients='" +car.getClientID()
                    +"', make='" +car.getMake() +"', model='" +car.getModel() +"', year=" +car.getYear());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) { try { connection.close(); } catch (SQLException ex) {} }
            if (statement != null) { try { statement.close(); } catch (SQLException ex) {} }
        }
    }

    public void addOrder(int idorders, String date, int amount, OrderStatus status, int carVID){
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO orders (idorders, date, amount, status, carVID) " +
                    "VALUES " + "('" + idorders + "', " + date + ", '" + amount + "', '" + status + "', '" + carVID + "')");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) { try { connection.close(); } catch (SQLException ex) {} }
            if (statement != null) { try { statement.close(); } catch (SQLException ex) {} }
        }
    }

    public void deleteOrder(int orderID){
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM orders WHERE idorders=" +orderID);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) { try { connection.close(); } catch (SQLException ex) {} }
            if (statement != null) { try { statement.close(); } catch (SQLException ex) {} }
        }
    }

    public void updateOrder(Order order){
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            statement = connection.createStatement();
            statement.executeUpdate("UPDATE orders SET idorders='" +order.getID() +"', date='" +order.getDate()
                    +"', amount='" +order.getOrderAmount() +"', status='" +order.getOrderStatus() +"', carVID=" +order.getCarID());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) { try { connection.close(); } catch (SQLException ex) {} }
            if (statement != null) { try { statement.close(); } catch (SQLException ex) {} }
        }
    }


}
