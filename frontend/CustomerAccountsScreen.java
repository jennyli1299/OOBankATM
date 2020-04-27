package frontend;

import javax.swing.*;

import java.awt.event.*;
import java.awt.*;
import javax.swing.border.MatteBorder;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class CustomerAccountsScreen implements ActionListener {

    /* state - hold a copy of the logged in customer
        pass to other screens and use to do database queries/backend calls */
    // Customer customer;

    /* UI components */
    JFrame frame;
    JLabel label;

    JPanel listContainer;
    JPanel list;
    JButton addButton;
    
    JButton backButton;

    /* pass in customer as parameter */
    public CustomerAccountsScreen() {
        // this.customer = customer;
        createWindow();
        createUI();
        createListContainer();
        frame.pack();
        frame.setLocationRelativeTo(null);
    }

    private void createWindow() {

        /* initialize the frame */
        frame = new JFrame("Customer - Manage Accounts");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void createUI() {

        /* add label */
        label = new JLabel();
        // label.setBounds(50, 50, 400, 50);
        label.setText("Accounts Screen");
        frame.add(label);

        /* add back button */
        backButton = new JButton("Go back to main menu");
        // backButton.setBounds(50, 400, 200, 50);
        backButton.addActionListener(this);
        frame.add(backButton);
    }

    private void createListContainer() {

        listContainer = new JPanel();
        listContainer.setLayout(new BorderLayout());
        
        list = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        gbc.weighty = 1;
        list.add(new JPanel(), gbc);
        listContainer.add(new JScrollPane(list));

        addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                JPanel panel = new JPanel();
                panel.add(new JLabel("Hello"));
                panel.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridwidth = GridBagConstraints.REMAINDER;
                gbc.weightx = 1;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                list.add(panel, gbc, 0);

                listContainer.validate();
                listContainer.repaint();
            }
        });
        listContainer.add(addButton, BorderLayout.SOUTH);

        frame.add(listContainer);
    }

    public Dimension getPreferredSize() {
        return new Dimension(200, 200);
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
        CustomerAccountsScreen screen = new CustomerAccountsScreen();
        screen.frame.setVisible(true);
    }
}