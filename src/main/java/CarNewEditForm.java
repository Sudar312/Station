import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by User on 25.09.2015.
 */
public class CarNewEditForm extends JFrame{
    private JTextField textFieldVID;
    private JTextField textFieldMake;
    private JTextField textFieldModel;
    private JTextField textFieldYear;
    private JButton confirmButton;
    private JPanel carPanel;
    private JLabel mainLabel;

    private int clientID;
    private int VID;
    private StartForm startForm;

    private DefaultListModel<Car> carDefaultListModel;

    public CarNewEditForm(int clientID, DefaultListModel<Car> carDefaultListModel, StartForm startForm) {
        initFrame();
        this.startForm = startForm;
        this.clientID = clientID;
        this.carDefaultListModel = carDefaultListModel;
        initNewCar();
    }

    public CarNewEditForm(int VID, int clientID, DefaultListModel<Car> carDefaultListModel, StartForm startForm) {
        initFrame();
        this.startForm = startForm;
        this.VID = VID;
        this.clientID = clientID;
        this.carDefaultListModel = carDefaultListModel;
        initEditCar();
    }

    void initFrame() {
        setVisible(true);
        setContentPane(carPanel);
        setSize(640, 480);
    }

    void initNewCar() {
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Car car;
                boolean isAdd = false;
                try {
                    isAdd = Administrator.getInstance().addNewCar(
                            Integer.parseInt(textFieldVID.getText()),
                            clientID, textFieldMake.getText(),
                            textFieldModel.getText(), textFieldYear.getText());
                } catch (Exception exc) {
                }
                if (isAdd) {

                    car = new Car( // I know this is bad but I have no time to refactor :(
                            textFieldMake.getText(),
                            textFieldModel.getText(),
                            textFieldYear.getText(),
                            Integer.parseInt(textFieldVID.getText()), clientID);

                    carDefaultListModel.addElement(car);

                    JOptionPane.showMessageDialog(CarNewEditForm.this, "You add new car " + textFieldMake.getText());

                    startForm.loadCarsToJList(clientID);

                    dispose();
                } else {
                    JOptionPane.showMessageDialog(CarNewEditForm.this, "Sorry, smth goes wrong :( Try to check your data");
                }
            }
        });
    } //Create new Car

    void initEditCar() {
        Car car = Administrator.getInstance().getCarForVID(VID);

        textFieldVID.setText(VID +"");
        textFieldMake.setText(car.getMake());
        textFieldModel.setText(car.getModel());
        textFieldYear.setText(car.getYear());

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                writeData(VID);
            }
        });
    } //Update Car

    private void writeData(int realVID) {
        Car car = Administrator.getInstance().getCarForVID(VID);

        car.setVID(realVID);
        car.setMake(textFieldMake.getText());
        car.setModel(textFieldModel.getText());
        car.setYear(textFieldYear.getText());

        Administrator.getInstance().getDbManager().updateCar(car);

        startForm.loadCarsToJList(clientID);

        dispose();
    }
}
