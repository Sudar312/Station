import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by User on 25.09.2015.
 */
public class OrderNewEditForm extends JFrame{
    private JTextField textFieldOrderId;
    private JTextField textFieldOrderAmount;
    private JTextField textFieldYear;
    private JButton confirmButton;
    private JComboBox comboBoxStatus;
    private JPanel orderPanel;

    private int carVID;
    private int clientID;
    private int orderID;
    private StartForm startForm;
    private DefaultListModel<Order> orderDefaultListModel;

    public OrderNewEditForm(int carVID, int clientID, DefaultListModel<Order> orderDefaultListModel) {
        this.clientID = clientID;
        this.carVID = carVID;
        this.orderDefaultListModel = orderDefaultListModel;
        initNewOrder();
        initFrame();
        initJComboBoxStatus();
    }

    public OrderNewEditForm(int orderID, int carVID, int clientID,
                            DefaultListModel<Order> orderDefaultListModel, StartForm startForm) {
        this.startForm = startForm;
        this.orderID = orderID;
        this.clientID = clientID;
        this.carVID = carVID;
        this.orderDefaultListModel = orderDefaultListModel;
        initEditOrder();
        initFrame();
        initJComboBoxStatus();
    }

    void initFrame() {
        setVisible(true);
        setContentPane(orderPanel);
        setSize(640, 480);
    }

    void initJComboBoxStatus() {
        comboBoxStatus.addItem(OrderStatus.IN_PROGRESS);
        comboBoxStatus.addItem(OrderStatus.COMPLETED);
        comboBoxStatus.addItem(OrderStatus.CANCELLED);
    }

    void initNewOrder() {
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Order order;
                boolean isAdd = false;
                try {
                    isAdd = Administrator.getInstance().addNewOrder(Integer.parseInt(textFieldOrderId.getText()),
                            carVID, textFieldYear.getText(), Integer.parseInt(textFieldOrderAmount.getText()),
                            ((OrderStatus) comboBoxStatus.getSelectedItem()));

                } catch (Exception exc) {
                }
                if (isAdd) {

                    order = new Order( // I know this is bad but I have no time to refactor :(
                            Integer.parseInt(textFieldOrderId.getText()),
                            carVID, textFieldYear.getText(),
                            Integer.parseInt(textFieldOrderAmount.getText()),
                            ((OrderStatus) comboBoxStatus.getSelectedItem()));

                    orderDefaultListModel.addElement(order);

                    JOptionPane.showMessageDialog(OrderNewEditForm.this, "You add new order");

                    dispose();
                } else {
                    JOptionPane.showMessageDialog(OrderNewEditForm.this, "Sorry, smth goes wrong :( Try to check your data");
                }
            }
        });
    }

    void initEditOrder() {
        Order order = Administrator.getInstance().getOrderForId(orderID);
        textFieldOrderId.setText(orderID +"");
        textFieldYear.setText(order.getDate());
        comboBoxStatus.setSelectedItem(order.getOrderStatus());
        textFieldOrderAmount.setText(order.getOrderAmount() +"");

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int realVID = Integer.parseInt(textFieldOrderId.getText());

                if (realVID == orderID) {
                    writeData(realVID);
                } else if (!Administrator.getInstance().checkForVIDMatch(realVID)) {
                    writeData(realVID);
                } else {
                    JOptionPane.showMessageDialog(OrderNewEditForm.this, "Sorry smth goes wrong");
                }

            }
        });
    }

    private void writeData(int ID) {
        Order order = Administrator.getInstance().getOrderForId(ID);

        order.setID(ID);
        order.setDate(textFieldYear.getText());
        order.setOrderAmount(Integer.parseInt(textFieldOrderAmount.getText()));
        order.setOrderStatus((OrderStatus) comboBoxStatus.getSelectedItem());

        Administrator.getInstance().getDbManager().updateOrder(order);

        startForm.loadOrdersToJList(clientID);

        dispose();
    }


}
