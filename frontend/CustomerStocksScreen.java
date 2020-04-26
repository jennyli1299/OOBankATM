package frontend;

import javax.swing.*;
import java.awt.event.*;

public class CustomerStocksScreen implements ActionListener {

    /* state - hold a copy of the logged in customer
        pass to other screens and use to do database queries/backend calls */
    // Customer customer;

    /* UI components */
    JFrame frame;
    JLabel label;
    
    JButton backButton;

    /* pass in customer as parameter */
    public CustomerStocksScreen() {
        // this.customer = customer;
        createWindow();
        createUI();
    }

    private void createWindow() {

        /* initialize the frame */
        frame = new JFrame("Customer - Manage Stocks");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(null);
    }

    private void createUI() {

        /* add label */
        label = new JLabel();
        label.setBounds(50, 50, 400, 50);
        label.setText("Stocks Screen");
        frame.add(label);

        /* add back button */
        backButton = new JButton("Go back to main menu");
        backButton.setBounds(50, 400, 200, 50);
        backButton.addActionListener(this);
        frame.add(backButton);

    }
    public void actionPerformed(ActionEvent e) {

        /* back button -> navigate to customer screen */
        if (e.getSource() == backButton) {
            CustomerScreen customerScreen = new CustomerScreen();
            frame.dispose();
            customerScreen.frame.setVisible(true);
        }
    }

    public static void main(String[] args) {
        CustomerStocksScreen screen = new CustomerStocksScreen();
        screen.frame.setVisible(true);
    }
}