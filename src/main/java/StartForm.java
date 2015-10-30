import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by User on 22.09.2015.
 */
public class StartForm {
    private JFrame frame;

    private JPanel panel1;

    private JList listClient;
    private JList listCar;
    private JList listOrder;

    private JButton removeClientButton;
    private JButton editClientButton;
    private JButton newClientButton;

    private JButton newCarButton;
    private JButton editCarButton;
    private JButton removeCarButton;

    private JButton newOrderButton;
    private JButton editOrderButton;
    private JButton removeOrderButton;

    private DefaultListModel<Client> clientDefaultListModel;
    private DefaultListModel<Car> carDefaultListModel;
    private DefaultListModel<Order> orderDefaultListModel;

    private Integer clientSelectionID; //Id of the client you choose in JList
    private Integer carSelectionID; //Id of the car you choose in JList
    private Integer orderSelectionID; //Id of the oder you choose in JList

    private Integer clientSelectedIndex;
    private Integer carSelectedIndex;
    private Integer orderSelectedIndex;

    public StartForm() {
        clientSelectionID = -1;
        carSelectionID = -1;
        orderSelectionID = -1;

        startButtonsSettings();
        initFrame();
        initBTNListeners();

        setCellRender();

        loadClientsToJList();
        initJListModelsListeners();
    }

    private void initFrame() {
        frame = new JFrame("Service");
        frame.setContentPane(panel1);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(640, 480);
    } //Initialize JFrame

    private void initJListModelsListeners() {

        listClient.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                clientClickedButtonsSettings();

                ListSelectionModel lsm = (ListSelectionModel) e.getSource();

                clientSelectedIndex = new Integer(lsm.getLeadSelectionIndex());

                final Client client = (Client) listClient.getSelectedValue();

                if (client != null) {
                    clientSelectionID = new Integer(client.getID());
                }

                if (carDefaultListModel != null) carDefaultListModel.removeAllElements();  //Clear all fields
                if (orderDefaultListModel != null) orderDefaultListModel.removeAllElements();

                loadCarsToJList(clientSelectionID); //Loads the second JList for the choosen Client
            }
        });

        listCar.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                carClickedButtonsSettings();

                ListSelectionModel lsm = (ListSelectionModel) e.getSource();

                carSelectedIndex = new Integer(lsm.getLeadSelectionIndex());

                final Car car = (Car) listCar.getSelectedValue();

                if (orderDefaultListModel != null) orderDefaultListModel.removeAllElements();

                if (car != null) {
                    carSelectionID = new Integer(car.getVID());
                    loadOrdersToJList(carSelectionID);
                }


            }
        });

        listOrder.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                orderClickedButtonsSettings();

                ListSelectionModel lsm = (ListSelectionModel) e.getSource();

                orderSelectedIndex = new Integer(lsm.getLeadSelectionIndex());

                final Order order = (Order) listOrder.getSelectedValue();

                if (order != null) {
                    orderSelectionID = new Integer(order.getID());
                }

            }
        });
    } //Initialize JList Listeners

    void initBTNListeners() {
        newClientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ClientNewEditForm(clientDefaultListModel);
            }
        });

        editClientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ClientNewEditForm(StartForm.this, clientSelectionID);
            }
        });

        removeClientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Administrator.getInstance().isClientHasCars(clientSelectionID)) {
                    JOptionPane.showMessageDialog(frame, "Sorry! First del all cars and orders of the client!");
                } else {
                    clientDefaultListModel.removeElementAt(clientSelectedIndex);
                    Administrator.getInstance().getDbManager().deleteClient(clientSelectionID);
                    clientSelectionID = -1;
                }
            }
        });

        newCarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CarNewEditForm(clientSelectionID, carDefaultListModel, StartForm.this);
            }
        });

        editCarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CarNewEditForm(carSelectionID, clientSelectionID, carDefaultListModel, StartForm.this);
            }
        });

        removeCarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Administrator.getInstance().isCarHasOrders(carSelectionID)) {

                    JOptionPane.showMessageDialog(frame, "Sorry! The car of the client has order!");

                } else {

                    carDefaultListModel.removeElementAt(carSelectedIndex);
                    Administrator.getInstance().getDbManager().deleteCar(carSelectionID);
                    carSelectionID = -1;
                }
            }
        });

        newOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new OrderNewEditForm(carSelectionID, clientSelectionID, orderDefaultListModel);
            }
        });

        editOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new OrderNewEditForm(orderSelectionID, carSelectionID, clientSelectionID, orderDefaultListModel, StartForm.this);
            }
        });

        removeOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                orderDefaultListModel.removeElementAt(orderSelectedIndex);

                Administrator.getInstance().getDbManager().deleteOrder(orderSelectionID);

                clientSelectionID = new Integer(-2);
            }
        });
    } //Initialize button Listeners

    void setCellRender() {
        listClient.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (renderer instanceof JLabel && value instanceof Client) {
                    ((JLabel) renderer).setText(((Client) value).getFirstName() + " " + ((Client) value).getLastName());
                }
                return renderer;
            }
        });

        listCar.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (renderer instanceof JLabel && value instanceof Car) {
                    ((JLabel) renderer).setText(((Car) value).getMake() + " " + ((Car) value).getModel());
                }
                return renderer;
            }
        });

        listOrder.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (renderer instanceof JLabel && value instanceof Order) {
                    ((JLabel) renderer).setText(((Order) value).getDate() + " " + ((Order) value).getOrderStatus()
                            + " " + ((Order) value).getOrderAmount() + "$");
                }
                return renderer;
            }
        });
    } //Helps us to display the String we want to user on JList

    public void loadClientsToJList() {
        clientDefaultListModel = new DefaultListModel<Client>();
        for (int i = 0; i < Administrator.getInstance().getClients().size(); i++) {
            clientDefaultListModel.addElement(Administrator.getInstance().getClients().get(i));
        }
        listClient.setModel(clientDefaultListModel);
    } //Load Clients from DB to JList

    public void loadCarsToJList(int clientID) {

        if (Administrator.getInstance().isClientHasCars(clientID)) {
            carDefaultListModel = new DefaultListModel<Car>();

            for (int i = 0; i < Administrator.getInstance().getCars().size(); i++) {
                if (Administrator.getInstance().getCars().get(i).getClientID() == clientID) {
                    carDefaultListModel.addElement(Administrator.getInstance().getCars().get(i));
                }
            }
            listCar.setModel(carDefaultListModel);
        }
    } //Load Cars from DB to JList

    public void loadOrdersToJList(int VID) {

        if (Administrator.getInstance().isCarHasOrders(VID)) {
            orderDefaultListModel = new DefaultListModel<Order>();

            for (int i = 0; i < Administrator.getInstance().getOrders().size(); i++) {
                if (Administrator.getInstance().getOrders().get(i).getCarID() == VID) {
                    orderDefaultListModel.addElement(Administrator.getInstance().getOrders().get(i));
                }
            }
            listOrder.setModel(orderDefaultListModel);
        }


    } //Load Orders from DB to JList

    private void startButtonsSettings() {
        removeClientButton.setEnabled(false);
        editClientButton.setEnabled(false);
        newClientButton.setEnabled(true);

        newCarButton.setEnabled(false);
        editCarButton.setEnabled(false);
        removeCarButton.setEnabled(false);

        newOrderButton.setEnabled(false);
        editOrderButton.setEnabled(false);
        removeOrderButton.setEnabled(false);
    }

    private void clientClickedButtonsSettings() {
        removeClientButton.setEnabled(true);
        editClientButton.setEnabled(true);
        newClientButton.setEnabled(true);

        newCarButton.setEnabled(true);
        editCarButton.setEnabled(false);
        removeCarButton.setEnabled(false);

        newOrderButton.setEnabled(false);
        editOrderButton.setEnabled(false);
        removeOrderButton.setEnabled(false);

    }

    private void carClickedButtonsSettings() {
        removeClientButton.setEnabled(true);
        editClientButton.setEnabled(true);
        newClientButton.setEnabled(true);

        newCarButton.setEnabled(true);
        editCarButton.setEnabled(true);
        removeCarButton.setEnabled(true);

        newOrderButton.setEnabled(true);
        editOrderButton.setEnabled(false);
        removeOrderButton.setEnabled(false);
    }

    private void orderClickedButtonsSettings() {
        removeClientButton.setEnabled(true);
        editClientButton.setEnabled(true);
        newClientButton.setEnabled(true);

        newCarButton.setEnabled(true);
        editCarButton.setEnabled(true);
        removeCarButton.setEnabled(true);

        newOrderButton.setEnabled(true);
        editOrderButton.setEnabled(true);
        removeOrderButton.setEnabled(true);
    }



}
