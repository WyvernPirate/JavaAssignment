import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.Arrays;

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
                opt[0] = "YES";
                opt[1] = "NO";
                String choice = (String) JOptionPane.showInputDialog(loginFrame, "Do you want to create a new Account",
                        "Confirmation", JOptionPane.QUESTION_MESSAGE, null, opt, opt[0]);
                if (choice.equals("YES")) {
                    // create new account
                    loginFrame.dispose();
                    registrationFrame();
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
        welcome.setLocation(1, 1);
        JButton logoutButton = new JButton("Logout");
        logoutButton.setLocation(1, 3);
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

        // create panels for registration
        JPanel tPanel = new JPanel();
        JPanel cPanel = new JPanel(new GridLayout(7, 2));
        JPanel bPanel = new JPanel(new GridLayout(1, 2));

        // create components for panels
        JLabel create = new JLabel("Create a new Account");
        JLabel fname = new JLabel("Enter First Name");
        JTextArea fnameArea = new JTextArea();
        JLabel lname = new JLabel("Enter Last Name");
        JTextArea lnameArea = new JTextArea();
        JLabel program = new JLabel("Enter you Program");
        JTextArea programTextArea = new JTextArea();
        JLabel yearLabel = new JLabel("Enter Year of Study");
        JTextArea yJTextArea = new JTextArea();
        JLabel dobLabel = new JLabel("Enter Date of Birth");
        JTextArea dobTextArea = new JTextArea();
        JLabel passwordLabel = new JLabel("Enter your password");
        JPasswordField passwordTextArea = new JPasswordField(20);
        JLabel passCon = new JLabel("Re-enter your password");
        JPasswordField passConArea = new JPasswordField();
        JButton registerButton = new JButton("Register");
        JButton cancelButton = new JButton("Cancel");

        // add functionality to buttons
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // account creation
                try {
                    // check field values
                    if (fnameArea.getText().isEmpty() || lnameArea.getText().isEmpty() ||
                            programTextArea.getText().isEmpty() || yJTextArea.getText().isEmpty() ||
                            dobTextArea.getText().isEmpty() || passwordTextArea.getPassword().length == 0 ||
                            passConArea.getPassword().length == 0) {
                        JOptionPane.showMessageDialog(null, "Please fill in all fields");
                        return;
                    }

                    // get field values
                    String fname = fnameArea.getText().trim();
                    String lname = lnameArea.getText().trim();
                    String program = programTextArea.getText().trim();
                    String yearStr = yJTextArea.getText().trim();
                    String dobStr = dobTextArea.getText().trim();
                    char[] password = passwordTextArea.getPassword();
                    char[] confirmPassword = passConArea.getPassword();

                    // Validate first name and last name
                    if (!fname.matches("[a-zA-Z]+")) {
                        JOptionPane.showMessageDialog(null, "First name can only contain letters");
                        return;
                    }
                    if (!lname.matches("[a-zA-Z]+")) {
                        JOptionPane.showMessageDialog(null, "Last name can only contain letters");
                        return;
                    }

                    // Validate program
                    if (!program.matches("[a-zA-Z ]+")) {
                        JOptionPane.showMessageDialog(null, "Program can only contain letters and spaces");
                        return;
                    }

                    // Validate year of study
                    int year;
                    try {
                        year = Integer.parseInt(yearStr);
                        if (year < 1900 || year > 2100) {
                            throw new NumberFormatException();
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null,
                                "Invalid year of study. Please enter a valid year (e.g., 2023).");
                        return;
                    }

                    // Validate date of birth
                    Date dob;
                    try {
                        dob = Date.valueOf(dobStr); // Ensure dobStr is in the correct format (yyyy-MM-dd)
                    } catch (IllegalArgumentException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid date of birth. Please use the format yyyy-MM-dd.");
                        return;
                    }

                    // Check if passwords match
                    if (!Arrays.equals(password, confirmPassword)) {
                        JOptionPane.showMessageDialog(null, "Passwords do not match");
                        return;
                    }

                    // create new student account
                    Student newStudent = new Student(fname, lname, program, year, dob, password);
                    // save student to database or file
                    JOptionPane.showMessageDialog(null, "Account created successfully");
                    registrationFrame.dispose();
                    loginFrame();

                } catch (Exception g) {
                    g.printStackTrace();
                    JOptionPane.showMessageDialog(null, "An unexpected error occurred. Please try again.");
                }
            }
        });

        // add components to panels
        tPanel.add(create);
        cPanel.add(fname);
        cPanel.add(fnameArea);
        cPanel.add(lname);
        cPanel.add(lnameArea);
        cPanel.add(program);
        cPanel.add(programTextArea);
        cPanel.add(yearLabel);
        cPanel.add(yJTextArea);
        cPanel.add(dobLabel);
        cPanel.add(dobTextArea);
        cPanel.add(passwordLabel);
        cPanel.add(passwordTextArea);
        cPanel.add(passCon);
        cPanel.add(passConArea);
        bPanel.add(registerButton);
        bPanel.add(cancelButton);

        registrationFrame.add(tPanel, BorderLayout.NORTH);
        registrationFrame.add(cPanel, BorderLayout.CENTER);
        registrationFrame.add(bPanel, BorderLayout.SOUTH);

        registrationFrame.setVisible(true);

    }

}
