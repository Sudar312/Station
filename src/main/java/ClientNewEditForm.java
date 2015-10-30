import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * This Class helps you to create and edit clients
 * Created by User on 23.09.2015.
 */
public class ClientNewEditForm extends JFrame{
    private JPanel panel1;

    private JButton createClientButton;

    private JTextField textFieldName;
    private JTextField textFieldSurname;
    private JTextField textFieldAdress;
    private JTextField textFieldPhone;
    private JTextField textFieldEmail;
    private JTextField textFieldDOB;
    private JTextField textFieldId;

    private StartForm startForm;

    private DefaultListModel<Client> clientDefaultListModel;

    public ClientNewEditForm(DefaultListModel<Client> clientDefaultListModel) {
        this.clientDefaultListModel = clientDefaultListModel;
        setVisible(true);
        setContentPane(panel1);
        setSize(640, 480);
        initNewClient();
    }

    public ClientNewEditForm(StartForm startForm, int ID) {
        this.startForm = startForm;
        setVisible(true);
        setContentPane(panel1);
        setSize(640, 480);
        initEditClient(ID);

    }

    void initNewClient() {
        createClientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                boolean isAddSuccess = false;
                try {
                    isAddSuccess = Administrator.getInstance().addNewClient(
                            textFieldName.getText(), textFieldSurname.getText(),
                            textFieldDOB.getText(), textFieldAdress.getText(),
                            Integer.parseInt(textFieldPhone.getText()),
                            textFieldEmail.getText());
                } catch (Exception exc) {
                }
                if (isAddSuccess & !Administrator.getInstance().checkForIDMatch(Integer.parseInt(textFieldId.getText()))) {

                    Client client = new Client(Integer.parseInt(textFieldId.getText()),
                            textFieldName.getText(), textFieldSurname.getText(),
                            textFieldDOB.getText(), textFieldAdress.getText(),
                            Integer.parseInt(textFieldPhone.getText()),
                            textFieldEmail.getText());

                    clientDefaultListModel.addElement(client);

                    JOptionPane.showMessageDialog(ClientNewEditForm.this, "You add new client " + textFieldName.getText() + " "
                            + textFieldSurname.getText());

                    dispose();
                } else if (Administrator.getInstance().checkForIDMatch(Integer.parseInt(textFieldId.getText()))) {
                    JOptionPane.showMessageDialog(ClientNewEditForm.this, "Sorry, check your ID");
                } else {
                    JOptionPane.showMessageDialog(ClientNewEditForm.this, "Sorry, incorrect data. Check the user information you add");
                }
            }
        });
    } //Create new Client

    void initEditClient(final int ID) {
        final Client client = Administrator.getInstance().getClientForID(ID);

        textFieldId.setText(ID +"");
        textFieldName.setText(client.getFirstName());
        textFieldSurname.setText(client.getLastName());
        textFieldAdress.setText(client.getAdress());
        textFieldPhone.setText(client.getPhone() +"");
        textFieldEmail.setText(client.getEmail());
        textFieldDOB.setText(client.getDateOfBirth());

        createClientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                    client.setFirstName(textFieldName.getText());
                    client.setLastName(textFieldSurname.getText());
                    client.setAdress(textFieldAdress.getText());
                    client.setDateOfBirth(textFieldDOB.getText());
                    client.setEmail(textFieldEmail.getText());
                    client.setPhone(Integer.parseInt(textFieldPhone.getText()));

                    Administrator.getInstance().getDbManager().updateClient(client);
                    startForm.loadClientsToJList();
                    dispose();
            }
        });
    } //Update existing Client

}
