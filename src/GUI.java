import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class GUI {

    public void loginFrame() {

        // create frames for login
        JFrame loginFrame = new JFrame("Login");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(700, 400);

        // create panels
        JPanel tPanel = new JPanel();
        JPanel cPanel = new JPanel(new GridLayout(4, 2, 50, 50));

        // create labels and their text fields
        JLabel welcome = new JLabel("Welcome User");
        JLabel usernameLabel = new JLabel("Username/ID");
        JLabel passwordLabel = new JLabel("Password");
        JTextField usernameField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);

        // create radio buttons for choosing between admin or student
        JRadioButton adminRadioButton = new JRadioButton("Admin");
        JRadioButton studentRadioButton = new JRadioButton("Student");
        ButtonGroup group = new ButtonGroup();
        group.add(adminRadioButton);
        group.add(studentRadioButton);

        // create buttons
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        // adding functionality to buttons
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // login functionality
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // check credentials for admin or student
                if (adminRadioButton.isSelected()) {
                    Admin one = new Admin(username, password);
                    if (one.checkCredentials() == true) {
                        // login successful
                        JOptionPane.showMessageDialog(loginFrame, "Login successful");
                        loginFrame.dispose();
                        adminFrame();
                    } else {
                        // login failed
                        JOptionPane.showMessageDialog(loginFrame, "Invalid username or password");
                    }

                } else if (studentRadioButton.isSelected()) {
                    Student two = new Student();
                    if (two.checkCredentials(username, password) == true) {
                        // login successful
                        JOptionPane.showMessageDialog(loginFrame, "Login successful");
                        loginFrame.dispose();
                        studentFrame();
                    } else {
                        // login failed
                        JOptionPane.showMessageDialog(loginFrame, "Invalid username or password");
                    }

                } else {
                    JOptionPane.showMessageDialog(loginFrame, "Please select a role");
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // register functionality
                String[] opt = new String[2];
                opt[0] = "Admin";
                opt[1] = "NO";
                String choice = (String) JOptionPane.showInputDialog(loginFrame, "Do you want to create a new Account",
                        "Confirmation", JOptionPane.QUESTION_MESSAGE, null, opt, opt[0]);
                if (choice.equals("YES")) {
                    adminFrame();
                } else {

                }
            }
        });

        // add components to panel
        tPanel.add(welcome);
        cPanel.add(adminRadioButton);
        cPanel.add(studentRadioButton);
        cPanel.add(usernameLabel);
        cPanel.add(usernameField);
        cPanel.add(passwordLabel);
        cPanel.add(passwordField);
        cPanel.add(loginButton);
        cPanel.add(registerButton);

        // add panels to components
        loginFrame.add(tPanel, BorderLayout.NORTH);
        loginFrame.add(cPanel, BorderLayout.CENTER);
        loginFrame.setVisible(true);

        // add functionality to loginFrame buttons
    }

    public void adminFrame() {
        // create frames for admin
        JFrame adminFrame = new JFrame("Admin");
        adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        adminFrame.setSize(700, 400);

        // create the panels
        JPanel tPanel = new JPanel(new GridLayout(2, 3));
        JPanel cPanel = new JPanel(new GridLayout(2, 3, 50, 50));
        JPanel bPanel = new JPanel();
        JPanel lPanel = new JPanel(new GridLayout(4, 1));

        // create components for panels
        JLabel welcome = new JLabel("Welcome Admin");
        welcome.setLocation(0, 1);
        JButton logoutButton = new JButton("Logout");
        logoutButton.setLocation(1, 2);
        JButton addStudentButton = new JButton("Add Student");
        JButton viewStudentButton = new JButton("View Student");

        // add components to panels
        tPanel.add(welcome);
        tPanel.add(logoutButton);
        lPanel.add(addStudentButton);
        lPanel.add(viewStudentButton);

        // add components to frame
        adminFrame.add(tPanel, BorderLayout.NORTH);
        adminFrame.add(lPanel, BorderLayout.WEST);
        adminFrame.add(cPanel, BorderLayout.CENTER);
        adminFrame.add(bPanel, BorderLayout.SOUTH);

        adminFrame.setVisible(true);
    }

    public void studentFrame() {
        // create frames for admin
        JFrame studentFrame = new JFrame("Student");
        studentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        studentFrame.setSize(700, 400);

        studentFrame.setVisible(true);
    }

    // registration frame
    public void registrationFrame() {
        // create frames for registration
        JFrame registrationFrame = new JFrame("Registration");
        registrationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        registrationFrame.setSize(700, 400);

        registrationFrame.setVisible(true);

    }
}
