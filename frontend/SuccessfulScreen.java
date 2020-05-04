package frontend;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class SuccessfulScreen {

    /* UI components */
    JFrame frame;
    JLabel displayLabel;

    public SuccessfulScreen(String display) {
        createWindow();
        createUI(display);
        frame.setVisible(true);
    }
    
    private void createWindow() {
        /* initialize the frame */
        frame = new JFrame("Bank ATM");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setAlwaysOnTop(true);
        frame.setBounds(200, 200, 300, 150);
        frame.setLayout(null);
    }

    private void createUI(String text) {
        /* add display text */
        displayLabel = new JLabel();
        displayLabel.setBounds(110, 30, 400, 50);
        displayLabel.setText(text);
        frame.add(displayLabel);
    }

    public static void main(String[] args) {
        SuccessfulScreen screen = new SuccessfulScreen("Take your money!");
    }
}