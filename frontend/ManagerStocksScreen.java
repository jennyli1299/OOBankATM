package frontend;

import src.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ManagerStocksScreen implements ActionListener {

    /* state */
    Manager manager;
    ArrayList<Stock> stocks;
    DefaultListModel<String> stocksListModel;

    /* UI components */
    JFrame frame;
    JLabel stocksListLabel;
    JList stocksList;
    JLabel priceLabel;
    JTextField price;
    JButton priceButton;
    JLabel quantityLabel;
    JTextField quantity;
    JButton quantityButton;
    JLabel warningLabel;
    JButton addButton;
    JButton backButton;

    public ManagerStocksScreen() {
        // this.manager = manager;
        createWindow();
        initState();
        createUI();
    }

    private void createWindow() {
        /* initialize the frame */
        frame = new JFrame("Manager - View High Balance Accounts");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(null);
    }

    private void initState() {
        // TODO get stocks from database
        stocks = new ArrayList<Stock>();
        stocksListModel = new DefaultListModel<String>();
        for (Stock stock : stocks) {
            stocksListModel.addElement(stock.toString());
        }

        /* mock data */
        stocksListModel.addElement("<ABC> - 2500 available (10000 total) - $35 per share");
        stocksListModel.addElement("<XYZ> - 50 available (250 total) - $100 per share");
    }

    private void createUI() {
        /* add stocksListLabel */
        stocksListLabel = new JLabel();
        stocksListLabel.setBounds(50, 25, 600, 50);
        stocksListLabel.setText("Stock Market - Select a stock to change price or quantity.");
        frame.add(stocksListLabel);

        /* add stocksList */
        stocksList = new JList<>(stocksListModel);
        stocksList.setBounds(50, 75, 400, 225);
        frame.add(stocksList);

        /* add priceLabel */
        priceLabel = new JLabel();
        priceLabel.setBounds(50, 350, 150, 50);
        priceLabel.setText("New Price:");
        frame.add(priceLabel);

        /* add price field */
        price = new JTextField();
        price.setBounds(200, 350, 150, 50);
        frame.add(price);

        /* add priceButton */
        priceButton = new JButton("Change price");
        priceButton.setBounds(350, 350, 200, 50);
        priceButton.addActionListener(this);
        frame.add(priceButton);

        /* add quantityLabel */
        quantityLabel = new JLabel();
        quantityLabel.setBounds(50, 400, 150, 50);
        quantityLabel.setText("Quantity:");
        frame.add(quantityLabel);

        /* add quantity field */
        quantity = new JTextField();
        quantity.setBounds(200, 400, 150, 50);
        frame.add(quantity);

        /* add quantity button */
        quantityButton = new JButton("Increase/decrease quantity");
        quantityButton.setBounds(350, 400, 200, 50);
        quantityButton.addActionListener(this);
        frame.add(quantityButton);

        /* add warning label */
        warningLabel = new JLabel();
        warningLabel.setBounds(50, 450, 600, 50);
        frame.add(warningLabel);

        /* add addButton */
        addButton = new JButton("Add new stock");
        addButton.setBounds(450, 75, 200, 50);
        addButton.addActionListener(this);
        frame.add(addButton);

        /* add back button */
        backButton = new JButton("Go back to main menu");
        backButton.setBounds(450, 125, 200, 50);
        backButton.addActionListener(this);
        frame.add(backButton);
    }

    public void actionPerformed(ActionEvent e) {

        /* add -> navigate to add stock screen */
        if (e.getSource() == addButton) {
            //AddStockScreen screen = new AddStockScreen();
            frame.dispose();
            //screen.frame.setVisible(true);

        /* back -> navigate back to main menu */
        } else if (e.getSource() == backButton) {
            ManagerScreen screen = new ManagerScreen();
            frame.dispose();
            screen.frame.setVisible(true);

        /* price -> change price of selected stock */
        } else if (e.getSource() == priceButton) {
            
            /* if index is invalid or no price, display warning */
            int index = stocksList.getSelectedIndex();
            if (index == -1 || price.getText().equals("")) {
                warningLabel.setText("Select a stock first and enter a price.");
            
            /* okay, try to change the price */
            } else {
                // TODO update price of selected stock, remember to update list and listmodel too
                Stock stock = stocks.get(index);
                manager.setStockSharePrice(stock, Float.parseFloat(price.getText()));
                stocksListModel.set(index, "Updated stock.toString here!"); //TODO what is this?
            }

        /* quantity -> increase/decrease price of selected stock */
        } else if (e.getSource() == quantityButton) {

            /* if index is invalid or no quantity, display warning */
            int index = stocksList.getSelectedIndex();
            if (index == -1 || quantity.getText().equals("")) {
                warningLabel.setText("Select a stock first and enter a quantity.");

            /* okay, try to change the quantity */
            } else {
                // TODO update quantity by given amount, rememebr to update list and listmodel too
                // NOTE: backend should have an elegant way to represent changing stock because people may own shares and setting total number of shares suddenly to 0 doesn't really make sense
                Stock stock = stocks.get(index);
                manager.setTotalStockShares(stock, Integer.parseInt(quantity.getText()));
                manager.updateAvailableStockShares(stock, Integer.parseInt(quantity.getText()));
                stocksListModel.set(index, "Updated stock.toString here!");
            }
        }
    }

    public static void main(String[] args) {
        ManagerStocksScreen screen = new ManagerStocksScreen();
        screen.frame.setVisible(true);
    }
}