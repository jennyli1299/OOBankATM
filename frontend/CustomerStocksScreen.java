package frontend;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

import src.*;

public class CustomerStocksScreen implements ActionListener {

    /* state */
    Customer customer;
    ArrayList<Stock> allStocks; // keep database query here
    DefaultListModel<String> allStocksListModel;
    ArrayList<Stock> myStocks; // keep database query here
    DefaultListModel<String> myStocksListModel;


    /* UI components */
    JFrame frame;
    JLabel label;
    JList allStocksList;
    JButton buyButton;
    JList myStocksList;
    JButton sellButton;
    JLabel warningLabel;
    JButton backButton;

    /* pass in customer as parameter */
    public CustomerStocksScreen() {
        // this.customer = customer;
        createWindow();
        initDummyState();
        createUI();
    }

    private void createWindow() {

        /* initialize the frame */
        frame = new JFrame("Customer - Manage Stocks");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(null);
    }

    private void initDummyState() {
        
        /* mock data - Float price, int totalShares, int currentlyAvailableShares, String name) */
        allStocks = new ArrayList<Stock>();
        allStocks.add(new Stock((float) 50, 1000, 500, "TOP"));
        allStocks.add(new Stock((float) 30, 1000, 500, "BOT"));
        allStocks.add(new Stock((float) 20.35, 1000, 500, "MID"));
        
        /* add string representation to list */
        allStocksListModel = new DefaultListModel<>();
        for (Stock stock : allStocks) {
            allStocksListModel.addElement(stock.toString());
        }

        myStocks = allStocks;

        myStocksListModel = new DefaultListModel<>();
        for (Stock stock : myStocks) {
            myStocksListModel.addElement(stock.toString());
        }
    }

    private void createUI() {

        /* add label */
        label = new JLabel();
        label.setBounds(50, 50, 600, 50);
        label.setText("Stock market shown at top. Your own stocks shown at bottom.");
        frame.add(label);

        /* add loans list */
        allStocksList = new JList<>(allStocksListModel);
        allStocksList.setBounds(50, 100, 300, 200);
        frame.add(allStocksList);

        /* add request button */
        buyButton = new JButton("Buy selected stock");
        buyButton.setBounds(375, 100, 150, 50);
        buyButton.addActionListener(this);
        frame.add(buyButton);

        /* add myStocksList */
        myStocksList = new JList<>(myStocksListModel);
        myStocksList.setBounds(50, 325, 300, 200);
        frame.add(myStocksList);

        /* add pay button */
        sellButton = new JButton("Sell selected stock");
        sellButton.setBounds(375, 325, 150, 50);
        sellButton.addActionListener(this);
        frame.add(sellButton);

        /* add warning label */
        warningLabel = new JLabel();
        warningLabel.setBounds(50, 525, 600, 50);
        frame.add(warningLabel);

        /* add back button */
        backButton = new JButton("Go back to main menu");
        backButton.setBounds(575, 50, 200, 50);
        backButton.addActionListener(this);
        frame.add(backButton);

    }
    public void actionPerformed(ActionEvent e) {

        /* back -> go back to customer screen */
        if (e.getSource() == backButton) {
            CustomerScreen screen = new CustomerScreen();
            frame.dispose();
            screen.frame.setVisible(true);

        /* buy -> navigate to buy screen */
        } else if (e.getSource() == buyButton) {
            
            /* if selcted stock is invalid, display warning */
            int index = allStocksList.getSelectedIndex();
            if (index == -1) {
                warningLabel.setText("Select a stock first.");
            } else {
                Stock stock = allStocks.get(index);
                StockBuyScreen screen = new StockBuyScreen();
                frame.dispose();
                // screen.frame.setVisible(true);
            }
            
        
        /* pay -> pay the amount on the selected due loan */
        } else if (e.getSource() == sellButton) {

            /* if selcted stock is invalid, display warning */
            int index = myStocksList.getSelectedIndex();
            if (index == -1) {
                warningLabel.setText("Select a stock first.");
            } else {
                Stock stock = myStocks.get(index);
                StockSellScreen screen = new StockSellScreen();
                frame.dispose();
                // screen.frame.setVisible(true);
            }
        } 
    }

    public static void main(String[] args) {
        CustomerStocksScreen screen = new CustomerStocksScreen();
        screen.frame.setVisible(true);
    }
}