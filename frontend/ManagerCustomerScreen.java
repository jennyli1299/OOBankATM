package frontend;

import src.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ManagerCustomerScreen implements ActionListener {

    /* state */
    Manager manager;
    ArrayList<Customer> customers;
    DefaultListModel<String> customersListModel;

    /* UI components */
    JFrame frame;
    JLabel customersListLabel;
    JList customersList; 
    JLabel warningLabel;
    JButton selectButton;
    JButton backButton;

    public ManagerCustomerScreen() {
        // this.manager = manager;
        createWindow();
        initState();
        createUI();
    }

    private void createWindow() {
        /* initialize the frame */
        frame = new JFrame("Manager - All Customers");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(null);
    }

    private void initState() {
        customers = StaticVariables.getDatabaseManager().getCustomers();
        customersListModel = new DefaultListModel<String>();
        for (Customer customer : customers) {
            customersListModel.addElement(customer.toString());
        }


    }

    private void createUI() {
        /* add customerslist label */
        customersListLabel = new JLabel();
        customersListLabel.setBounds(50, 25, 600, 50);
        customersListLabel.setText("Table of all customers - Select a customer to view their accounts/transactions.");
        frame.add(customersListLabel);

        /* add customers list */
        customersList = new JList<>(customersListModel);
        customersList.setBounds(50, 75, 400, 325);
        frame.add(customersList);

        /* add warning label */
        warningLabel = new JLabel();
        warningLabel.setBounds(50, 400, 600, 50);
        frame.add(warningLabel);

        /* add select button */
        selectButton = new JButton("View details");
        selectButton.setBounds(450, 75, 200, 50);
        selectButton.addActionListener(this);
        frame.add(selectButton);

        /* add backButton */
        backButton = new JButton("Back to main menu");
        backButton.setBounds(450, 125, 200, 50);
        backButton.addActionListener(this);
        frame.add(backButton);
    }

    public void actionPerformed(ActionEvent e) {

        /* select -> navigate to chosen customer details screen */
        if (e.getSource() == selectButton) {

            int index = customersList.getSelectedIndex();
            if (index == -1) {
                warningLabel.setText("Select a customer first.");
            } else {
                Customer customer = customers.get(index);
                StaticVariables.setSelectedCustomer(customer);
                ManagerDetailsScreen screen = new ManagerDetailsScreen();
                frame.dispose();
                screen.frame.setVisible(true);
            }
        /* back -> go back */
        } else if (e.getSource() == backButton) {
            ManagerScreen screen = new ManagerScreen();
            frame.dispose();
            screen.frame.setVisible(true);
        }
    }
    public static void main(String[] args) {
        ManagerCustomerScreen screen = new ManagerCustomerScreen();
        screen.frame.setVisible(true);
    }
}