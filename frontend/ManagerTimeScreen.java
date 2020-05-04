package frontend;

import src.*;
import javax.swing.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ManagerTimeScreen implements ActionListener {

    /* state */
    Manager manager;

    /* UI components */
    JFrame frame;
    JLabel timeLabel;
    JButton dayButton;
    JButton weekButton;
    JButton monthButton;
    JButton yearButton;
    JButton backButton;

    public ManagerTimeScreen() {
        // this.manager = manager;
        createWindow();
        createUI();
    }

    private void createWindow() {
        /* initialize the frame */
        frame = new JFrame("Manager - Change Time");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(null);
    }

    private void createUI() {
        /* timeLabel */
        timeLabel = new JLabel();
        timeLabel.setBounds(50, 50, 600, 50);
        timeLabel.setText("CURRENT TIME: " + Time.getCurrentTime().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        frame.add(timeLabel);

        /* dayButton */
        dayButton = new JButton("Forward 1 Day");
        dayButton.setBounds(50, 100, 200, 50);
        dayButton.addActionListener(this);
        frame.add(dayButton);

        /* weekButton */
        weekButton = new JButton("Forward 1 Week");
        weekButton.setBounds(50, 150, 200, 50);
        weekButton.addActionListener(this);
        frame.add(weekButton);

        /* monthButton */
        monthButton = new JButton("Forward 1 Month");
        monthButton.setBounds(50, 200, 200, 50);
        monthButton.addActionListener(this);
        frame.add(monthButton);

        /* yearButton */
        yearButton = new JButton("Forward 1 Year");
        yearButton.setBounds(50, 250, 200, 50);
        yearButton.addActionListener(this);
        frame.add(yearButton);

        /* backButton */
        backButton = new JButton("Go back to main menu");
        backButton.setBounds(50, 300, 200, 50);
        backButton.addActionListener(this);
        frame.add(backButton);
    }

    public void actionPerformed(ActionEvent e) {

        /* forward 1 day */
        if (e.getSource() == dayButton) {
            Time.setCurrentTime(Time.getCurrentTime().plusDays(1));
            // TODO update time and due loans/interest
            timeLabel.setText("CURRENT TIME: " + Time.getCurrentTime().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        
        /* forward 1 week */
        } else if (e.getSource() == weekButton) {
            Time.setCurrentTime(Time.getCurrentTime().plusWeeks(1));
            // TODO update time and due loans/interest
            timeLabel.setText("CURRENT TIME: " + Time.getCurrentTime().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        
        /* forward 1 month */
        } else if (e.getSource() == monthButton) {
            Time.setCurrentTime(Time.getCurrentTime().plusMonths(1));
            // TODO update time and due loans/interest
            timeLabel.setText("CURRENT TIME: " + Time.getCurrentTime().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        
        /* forward 1 year */
        } else if (e.getSource() == yearButton) {
            Time.setCurrentTime(Time.getCurrentTime().plusYears(1));
            // TODO update time and due loans/interest
            timeLabel.setText("CURRENT TIME: " + Time.getCurrentTime().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        
        /* back -> navigate back to main menu */
        } else if (e.getSource() == backButton) {
            ManagerScreen screen = new ManagerScreen();
            frame.dispose();
            screen.frame.setVisible(true);
        }
    }

    public static void main(String[] args) {
        ManagerTimeScreen screen = new ManagerTimeScreen();
        screen.frame.setVisible(true);
    }
}