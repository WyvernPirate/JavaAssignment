import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        JLabel usernameLabel = new JLabel("Username");
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

        // adding functionality
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // login functionality
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // check credentials
                Admin one = new Admin(username, password);
                if (one.checkCredentials()) {
                    // login successful
                    JOptionPane.showMessageDialog(loginButton, "Login successful");
                    loginFrame.dispose();
                    adminFrame();
                } else {
                    // login failed
                    JOptionPane.showMessageDialog(loginFrame, "Invalid username or password");
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

    }

    // add functionality to loginFrame buttons

    public void adminFrame() {
        // create frames for admin
        JFrame adminFrame = new JFrame("Admin");
        adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        adminFrame.setSize(700, 400);

        adminFrame.setVisible(true);
    }

}
