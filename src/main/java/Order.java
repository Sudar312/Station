/**
 * Created by User on 21.09.2015.
 */
public class Order {
    public static final String ORDER_DB_NAME = "database_order.csv";

    private int ID;
    private int carID;
    private String date;
    private int orderAmount;
    private OrderStatus orderStatus;

    public Order(int ID, int carID, String date, int orderAmount, OrderStatus orderStatus) {
        this.ID = ID;
        this.carID = carID;
        this.date = date;
        this.orderAmount = orderAmount;
        this.orderStatus = orderStatus;
    }

    public String getDate() {
        return date;
    }

    public int getOrderAmount() {
        return orderAmount;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public int getID() {
        return ID;
    }

    public int getCarID() {
        return carID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setOrderAmount(int orderAmount) {
        this.orderAmount = orderAmount;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
