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
    ArrayList<SecurityAccount> securities; // keep database query here
    DefaultListModel<String> securitiesListModel;

    /* UI components */
    JFrame frame;
    JLabel label;
    JList allStocksList;
    JButton buyButton;
    JLabel quantityBuyLabel;
    JTextField quantityBuy;
    JComboBox securitiesBuyDropdown;
    JList myStocksList;
    JButton sellButton;
    JLabel quantitySellLabel;
    JTextField quantitySell;
    JComboBox securitiesSellDropdown;
    JLabel warningLabel;
    JButton backButton;

    /* pass in customer as parameter */
    public CustomerStocksScreen() {
        this.customer = (Customer)StaticVariables.getLoggedInUser();
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
            // TODO need way to show number of stocks owned by customer
            myStocksListModel.addElement(stock.toString() + " (50 shares)");
        }

        /* mockup for securities */
        securities = new ArrayList<SecurityAccount>();
        for (Account account : securities) {
            securitiesListModel.addElement(account.toString());
        }
        securitiesListModel = new DefaultListModel<>();
        securitiesListModel.addElement("<CH88888888>: $15000");
        securitiesListModel.addElement("<US12345678>: $999999");
        securitiesListModel.addElement("<GB87654321>: $1234");
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
        buyButton.setBounds(375, 100, 200, 50);
        buyButton.addActionListener(this);
        frame.add(buyButton);

        /* add quantityBuy label */
        quantityBuyLabel = new JLabel();
        quantityBuyLabel.setBounds(375, 150, 100, 50);
        quantityBuyLabel.setText("Quantity:");
        frame.add(quantityBuyLabel);

        /* add quantityBuy field */
        quantityBuy = new JTextField();
        quantityBuy.setBounds(475, 150, 100, 50);
        frame.add(quantityBuy);

        /* add securitiesBuy dropdown */
        securitiesBuyDropdown = new JComboBox<>(securitiesListModel.toArray());
        securitiesBuyDropdown.setBounds(375, 200, 200, 50);
        frame.add(securitiesBuyDropdown);

        /* add myStocksList */
        myStocksList = new JList<>(myStocksListModel);
        myStocksList.setBounds(50, 325, 300, 200);
        frame.add(myStocksList);

        /* add sell button */
        sellButton = new JButton("Sell selected stock");
        sellButton.setBounds(375, 325, 150, 50);
        sellButton.addActionListener(this);
        frame.add(sellButton);

        /* add quantitySell label */
        quantitySellLabel = new JLabel();
        quantitySellLabel.setBounds(375, 375, 100, 50);
        quantitySellLabel.setText("Quantity:");
        frame.add(quantitySellLabel);

        /* add quantitySell field */
        quantitySell = new JTextField();
        quantitySell.setBounds(475, 375, 100, 50);
        frame.add(quantitySell);

        /* add securitiesSell dropdown */
        securitiesSellDropdown = new JComboBox<>(securitiesListModel.toArray());
        securitiesSellDropdown.setBounds(375, 425, 200, 50);
        frame.add(securitiesSellDropdown);

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
            int stockIndex = allStocksList.getSelectedIndex();

            myStocksListModel.add(allStocksList.getSelectedIndex(), allStocks.get(stockIndex).toString());
            allStocksListModel.remove(allStocksList.getSelectedIndex());
            myStocks.add(allStocks.get(stockIndex));
            allStocks.remove(allStocks.get(stockIndex));

            int accountIndex = securitiesBuyDropdown.getSelectedIndex();
            if (stockIndex == -1 || accountIndex == -1 || quantityBuy.getText().equals("")) {
                warningLabel.setText("Select a stock, a quantity, and an account to buy.");

            /* try to buy the stock */
            } else {
                // TODO: check if the user has enough money/there's enough stock/etc
                // if it's okay, make the purchase and set success message in warningLabel
                Stock stock = allStocks.get(stockIndex);
//                //SecurityAccount secAcc = securities.get(accountIndex);
//                if (secAcc.getBalanceInLocalCurrency() > stock.getCurrentPrice()) {
//                    if (stock.getCurrentlyAvailableShares() > 0) {
//                        boolean bought = customer.buyStock(stock, secAcc);
//                        if (!bought) {
//                            warningLabel.setText("Purchase of this stock was unsuccessful.");
//                        }
//                    }
//                    else {
//                        warningLabel.setText("This stock has no available shares to be purchased.");
//                    }
//                }
//                else {
//                    warningLabel.setText("You don't have enough money in this Security Account to purchase this stock.");
//                }
            }
        /* pay -> pay the amount on the selected due loan */
        } else if (e.getSource() == sellButton) {

            /* if selcted stock is invalid, display warning */
            int stockIndex = myStocksList.getSelectedIndex();
            allStocksListModel.add(myStocksList.getSelectedIndex(), myStocks.get(stockIndex).toString());
            myStocksListModel.remove(myStocksList.getSelectedIndex());
            allStocks.add(myStocks.get(stockIndex));
            myStocks.remove(myStocks.get(stockIndex));

            int accountIndex = securitiesSellDropdown.getSelectedIndex();
            if (stockIndex == -1 || accountIndex == -1 || quantitySell.getText().equals("")) {
                warningLabel.setText("Select a stock, a quantity, and an account to sell.");

            /* try to sell the stock */
            } else {
                // TODO: try to sell the quantity of stock and put money in chosen account
                // if it's okay, make the purchase and set success message in warningLabel
                Stock stock = allStocks.get(stockIndex);
                SecurityAccount secAcc = securities.get(accountIndex);
                // REALIZED/UNREALIZED PROFIT
            }
        } 
    }

    public static void main(String[] args) {
        CustomerStocksScreen screen = new CustomerStocksScreen();
        screen.frame.setVisible(true);
    }
}