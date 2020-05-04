package frontend;

import src.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class AddStockScreen implements ActionListener {
// public Stock(Float price, int totalShares, int currentlyAvailableShares, String name)

    /* state */
    Manager manager;

    /* UI components */
    JFrame frame;
    JLabel titleLabel;
    JLabel nameLabel;
    JTextField name;
    JLabel priceLabel;
    JTextField price;
    JLabel totalSharesLabel;
    JTextField totalShares;
    JLabel availableLabel;
    JTextField available;
    JLabel warningLabel;
    JButton createButton;
    JButton backButton;

    public AddStockScreen() {
        // this.manager = manager;
        createWindow();
        createUI();
    }

    private void createWindow() {
        /* initialize the frame */
        frame = new JFrame("Manager - View High Balance Accounts");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(null);
    }

    private void createUI() {
        /* titleLabel */
        titleLabel = new JLabel();
        titleLabel.setBounds(50, 50, 600, 50);
        titleLabel.setText("Enter the following fields to add a new stock.");
        frame.add(titleLabel);

        /* nameLabel */
        nameLabel = new JLabel();
        nameLabel.setBounds(50, 100, 200, 50);
        nameLabel.setText("Stock Abbreviation:");
        frame.add(nameLabel);

        /* name field */
        name = new JTextField();
        name.setBounds(250, 100, 200, 50);
        frame.add(name);

        /* priceLabel */
        priceLabel = new JLabel();
        priceLabel.setBounds(50, 150, 200, 50);
        priceLabel.setText("Price per share:");
        frame.add(priceLabel);

        /* price field */
        price = new JTextField();
        price.setBounds(250, 150, 200, 50);
        frame.add(price);

        /* totalSharesLabel */
        totalSharesLabel = new JLabel();
        totalSharesLabel.setBounds(50, 200, 200, 50);
        totalSharesLabel.setText("Total shares:");
        frame.add(totalSharesLabel);

        /* totalShares field */
        totalShares = new JTextField();
        totalShares.setBounds(250, 200, 200, 50);
        frame.add(totalShares);

        /* availableLabel */
        availableLabel = new JLabel();
        availableLabel.setBounds(50, 250, 200, 50);
        availableLabel.setText("Shares available:");
        frame.add(availableLabel);

        /* available */
        available = new JTextField();
        available.setBounds(250, 250, 200, 50);
        frame.add(available);

        /* warningLabel */
        warningLabel = new JLabel();
        warningLabel.setBounds(50, 300, 600, 50);
        frame.add(warningLabel);

        /* createButton */
        createButton = new JButton("Create new stock");
        createButton.setBounds(50, 350, 200, 50);
        createButton.addActionListener(this);
        frame.add(createButton);

        /* backButton */
        backButton = new JButton("Go back to main menu");
        backButton.setBounds(50, 400, 200, 50);
        backButton.addActionListener(this);
        frame.add(backButton);
    }

    public void actionPerformed(ActionEvent e) {

        /* add -> try to add stock */
        if (e.getSource() == createButton) {

            /* if any of the fields are null, display error */
            if (name.getText().equals("") || price.getText().equals("") || totalShares.getText().equals("") || available.getText().equals("")) {
                warningLabel.setText("One or more fields is missing. Enter all the fields to continue.");

            /* okay, try to add the stock */
            } else {
                // TODO add stock DATABASE
                Stock newStock = new Stock(Float.parseFloat(price.getText()), Integer.parseInt(totalShares.getText()), Integer.parseInt(available.getText()), name.getText());

            }

        /* back -> navigate back to stocks */
        } else if (e.getSource() == backButton) {
            ManagerStocksScreen screen = new ManagerStocksScreen();
            frame.dispose();
            screen.frame.setVisible(true);
        }
    }

    public static void main(String[] args) {
        AddStockScreen screen = new AddStockScreen();
        screen.frame.setVisible(true);
    }
}