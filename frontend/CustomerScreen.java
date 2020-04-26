package frontend;

import javax.swing.*;
import java.awt.event.*;

public class CustomerScreen {
    
    /* UI components */
    JFrame frame;
    JLabel label;

    public CustomerScreen() {
        createWindow();
        createUI();
    }

    private void createWindow() {

        /* initialize the frame */
        frame = new JFrame("Customer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(null);
    }

    private void createUI() {

        /* add welcomeLabel */
        label = new JLabel();
        label.setBounds(50, 50, 400, 50);
        label.setText("Welcome back, valued customer. Select an action to view more.");
        frame.add(label);
    }

    public static void main(String[] args) {
        CustomerScreen screen = new CustomerScreen();
        screen.frame.setVisible(true);
    }
}