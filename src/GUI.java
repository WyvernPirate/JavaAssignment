import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.Arrays;

public class GUI {

    // connect to database
    DatabaseController n = new DatabaseController();

    public void loginFrame() {

        // Create frames for login
        JFrame loginFrame = new JFrame("Login");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(700, 400);
        loginFrame.setLayout(new BorderLayout());

        // Create panels
        JPanel tPanel = new JPanel();
        JPanel cPanel = new JPanel(new GridLayout(2, 2, 10, 30));
        cPanel.setBorder(BorderFactory.createEmptyBorder(80, 110, 90, 110)); // add padding around form center panel

        JPanel bPanel = new JPanel();

        // Create labels and their text fields with preferred sizes for better
        // appearance
        JLabel welcome = new JLabel("Welcome User");
        welcome.setSize(new Dimension(20, 1));
        JLabel usernameLabel = new JLabel("Username/ID:");
        usernameLabel.setSize(new Dimension(20, 1));
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setSize(new Dimension(20, 1));
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        // Create buttons
        JButton loginButton = new JButton("Login");

        // Adding functionality to buttons
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Login functionality
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                n.connect();
                String type = n.checkCredentials(username, password);
                if (type != null) {
                    switch (type) {
                        case "admin":
                            loginFrame.dispose();
                            adminFrame();
                            break;
                        case "student":
                            loginFrame.dispose();
                            studentFrame(Integer.parseInt(username));
                            break;
                        default:
                            JOptionPane.showMessageDialog(null, "Invalid credentials");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid credentials");
                }
            }
        });

        // Add components to panels
        tPanel.add(welcome);
        cPanel.add(usernameLabel);
        cPanel.add(usernameField);
        cPanel.add(passwordLabel);
        cPanel.add(passwordField);
        bPanel.add(loginButton);

        // Add panels to frame
        loginFrame.add(tPanel, BorderLayout.NORTH);
        loginFrame.add(cPanel, BorderLayout.CENTER);
        loginFrame.add(bPanel, BorderLayout.SOUTH);

        // Display the frame
        loginFrame.setVisible(true);
    }

    public void adminFrame() {
        // create frames for admin
        JFrame adminFrame = new JFrame("Admin");
        adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        adminFrame.setSize(700, 400);

        // create the panels
        JPanel tPanel = new JPanel(new GridLayout(1, 3));
        JPanel cPanel = new JPanel();
        JPanel lPanel = new JPanel(new GridLayout(4, 1));
        JPanel bPanel = new JPanel(new GridLayout());

        // create components for panels
        JLabel fname = new JLabel("Enter First Name");
        JTextArea fnameArea = new JTextArea();
        fname.setSize(20, 1);
        JLabel lname = new JLabel("Enter Last Name");
        JTextArea lnameArea = new JTextArea();
        JLabel program = new JLabel("Enter the Program");
        JTextArea programTextArea = new JTextArea();
        JLabel yearLabel = new JLabel("Enter Year of Study");
        JTextArea yJTextArea = new JTextArea();
        JLabel dobLabel = new JLabel("Enter Date of Birth");
        JTextArea dobTextArea = new JTextArea();
        JLabel passwordLabel = new JLabel("Set Password");
        JTextArea passwordTextArea = new JTextArea();
        JButton submitButton = new JButton("Submit");

        JLabel welcome = new JLabel("Welcome Admin");

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // logout functionality
                JOptionPane.showConfirmDialog(null, "Confirmation of logging out");
                adminFrame.dispose();
                loginFrame();
            }
        });
        JButton addStudentButton = new JButton("Add Student");
        addStudentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // add student functional
                cPanel.removeAll();
                cPanel.setLayout(new GridLayout(0, 2));
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
                cPanel.add(submitButton);
                cPanel.revalidate();
                cPanel.repaint();

            }
        });

        JButton viewStudentButton = new JButton("View Student");
        viewStudentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // view student functionality
                cPanel.removeAll();
                cPanel.setLayout(new BorderLayout());
                DefaultTableModel model = new DefaultTableModel();
                JTable table = new JTable(model);
                model.addColumn("Student ID");
                model.addColumn("Name");
                model.addColumn("Program");
                model.addColumn("Year of Study");
                model.addColumn("Date of Birth");
                // retrieve student data from database
                // for example:

                JScrollPane scrollPane = new JScrollPane(table);
                cPanel.add(scrollPane, BorderLayout.CENTER);
                cPanel.revalidate();
                cPanel.repaint();
            }
        });

        JButton transcriptButton = new JButton("Transcript");
        transcriptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // transcript functionality
            }
        });

        // add components to panels
        tPanel.add(welcome);
        tPanel.add(logoutButton);
        lPanel.add(viewStudentButton);
        lPanel.add(addStudentButton);
        lPanel.add(transcriptButton);

        // add components to frame
        adminFrame.add(tPanel, BorderLayout.NORTH);
        adminFrame.add(lPanel, BorderLayout.WEST);
        adminFrame.add(cPanel, BorderLayout.CENTER);
        adminFrame.add(bPanel, BorderLayout.SOUTH);

        adminFrame.setVisible(true);
    }

    public void studentFrame(int id) {
        // create frames for student
        JFrame studentFrame = new JFrame("Student");
        studentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        studentFrame.setSize(700, 400);

        // create panels
        JPanel tPanel = new JPanel(new GridLayout(2, 2));
        JPanel lPanel = new JPanel(new GridLayout(3, 1));
        JPanel cPanel = new JPanel();
        JPanel bPanel = new JPanel();
        JPanel wJPanel = new JPanel(new GridLayout(4, 1));

        // create components for panels
        JLabel welcome = new JLabel("Welcome to the  Student Portal User " + id);
        JButton logoutButton = new JButton("Logout");
        JButton viewStudentButton = new JButton("Personal Details");
        JButton viewTranscriptButton = new JButton("Transcript");
        JButton modulesButton = new JButton("Modules");
        JButton addMButton = new JButton("Add");
        addMButton.setVisible(false);
        JButton removeMButton = new JButton("Remove");
        removeMButton.setVisible(false);

        // create table for grades
        String[] columns = { "Module Code", "Name", "Marks", "Credits", "Year", "Semester" };
        DefaultTableModel grades = new DefaultTableModel(columns, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return String.class;
            }
        };
        JTable table = new JTable(grades);

        // add components to panels
        tPanel.add(welcome);
        tPanel.add(logoutButton);
        lPanel.add(viewStudentButton);
        lPanel.add(viewTranscriptButton);
        lPanel.add(modulesButton);
        wJPanel.add(addMButton);
        wJPanel.add(removeMButton);
        // add button functionality
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to logout?",
                        "Logout Confirmation", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    System.out.println("Logout button clicked");
                    studentFrame.dispose();
                    loginFrame();
                }
            }
        });

        modulesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cPanel.removeAll();
                cPanel.setLayout(new BorderLayout());
                JScrollPane scrollPane = new JScrollPane(table);
                cPanel.add(scrollPane, BorderLayout.CENTER);
                addMButton.setVisible(true);
                removeMButton.setVisible(true);
                wJPanel.revalidate();
                wJPanel.repaint();
                cPanel.revalidate();
                cPanel.repaint();
                // Add a refresh button to update the table
                JButton refreshButton = new JButton("Refresh");
                refreshButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // Refresh the table
                        DefaultTableModel model = (DefaultTableModel) table.getModel();
                        model.setRowCount(0);
                        // Retrieve data from database and add to table
                        for (Module m : n.retrieveAllModules(id)) {
                            model.addRow(new Object[] { m.getCode(), m.getName(), m.getMark(), m.getCredits(),
                                    m.getSemester(), m.getYear() });
                        }
                    }
                });
                JPanel buttonPanel = new JPanel();
                buttonPanel.add(refreshButton);
                cPanel.add(buttonPanel, BorderLayout.SOUTH);
            }
        });

        addMButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // add new module to table using input dialog
                String moduleName = JOptionPane.showInputDialog(null, "Enter Module Name");
                String moduleCode = JOptionPane.showInputDialog(null, "Enter Module Code");
                String moduleMark = JOptionPane.showInputDialog(null, "Enter Module marks");
                String moduleCredits = JOptionPane.showInputDialog(null, "Enter Module Credits");
                String moduleYear = JOptionPane.showInputDialog(null, "Enter Module Year");
                String moduleSemester = JOptionPane.showInputDialog(null, "Enter Module Semester");

                if (moduleName != null && moduleCode != null && moduleCredits != null && moduleYear != null
                        && moduleSemester != null) {
                    try {
                        // add new module to database
                        Double mark = Double.parseDouble(moduleMark);
                        int credits = Integer.parseInt(moduleCredits);
                        int year = Integer.parseInt(moduleYear);
                        int semester = Integer.parseInt(moduleSemester);

                        Module m = new Module(moduleCode, moduleName, mark, credits, semester, year);
                        n.addModule(m, id);

                        // add module to table
                        DefaultTableModel model = (DefaultTableModel) table.getModel();
                        model.addRow(new Object[] { m.getCode(), m.getName(), m.getMark(), m.getCredits(),
                                m.getYear(), m.getSemester() });
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null,
                                "Invalid input. Please enter valid numbers for credits, year, and semester.");
                    }
                }
            }
        });
        viewStudentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // display student details
                System.out.println("View Student button clicked");
                cPanel.removeAll();
                cPanel.setLayout(new BorderLayout());
                Student studentInfo = n.retrieveStudentInfo(id);
                if (studentInfo != null) {
                    JPanel studentDetailsPanel = new JPanel();
                    studentDetailsPanel.setLayout(new GridLayout(0, 2));
                    studentDetailsPanel.add(new JLabel("Student ID:"));
                    studentDetailsPanel.add(new JLabel(String.valueOf(id)));
                    studentDetailsPanel.add(new JLabel("Name:"));
                    studentDetailsPanel.add(new JLabel(studentInfo.getFirstName()));
                    studentDetailsPanel.add(new JLabel("Program:"));
                    studentDetailsPanel.add(new JLabel(studentInfo.getProgram()));
                    studentDetailsPanel.add(new JLabel("Year of Study:"));
                    studentDetailsPanel.add(new JLabel(String.valueOf(studentInfo.getYear())));
                    studentDetailsPanel.add(new JLabel("Date of Birth:"));
                    studentDetailsPanel.add(new JLabel(String.valueOf(studentInfo.getDob())));
                    studentDetailsPanel.add(new JButton("Edit"));
                    cPanel.add(studentDetailsPanel, BorderLayout.CENTER);
                } else {
                    cPanel.add(new JLabel("No student info found"), BorderLayout.CENTER);
                }
                cPanel.revalidate();
                cPanel.repaint();
            }
        });

        // add panels to frame
        studentFrame.add(tPanel, BorderLayout.NORTH);
        studentFrame.add(lPanel, BorderLayout.WEST);
        studentFrame.add(cPanel, BorderLayout.CENTER);
        studentFrame.add(bPanel, BorderLayout.SOUTH);
        studentFrame.add(wJPanel, BorderLayout.EAST);

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
        /*
         * registerButton.addActionListener(new ActionListener() {
         * public void actionPerformed(ActionEvent e) {
         * // account creation
         * try {
         * // check field values
         * if (fnameArea.getText().isEmpty() || lnameArea.getText().isEmpty() ||
         * programTextArea.getText().isEmpty() || yJTextArea.getText().isEmpty() ||
         * dobTextArea.getText().isEmpty() || passwordTextArea.getPassword().length == 0
         * ||
         * passConArea.getPassword().length == 0) {
         * JOptionPane.showMessageDialog(null, "Please fill in all fields");
         * return;
         * }
         * 
         * // get field values
         * String fname = fnameArea.getText().trim();
         * String lname = lnameArea.getText().trim();
         * String program = programTextArea.getText().trim();
         * String yearStr = yJTextArea.getText().trim();
         * String dobStr = dobTextArea.getText().trim();
         * char[] password = passwordTextArea.getPassword();
         * char[] confirmPassword = passConArea.getPassword();
         * 
         * // Validate first name and last name
         * if (!fname.matches("[a-zA-Z]+")) {
         * JOptionPane.showMessageDialog(null, "First name can only contain letters");
         * return;
         * }
         * if (!lname.matches("[a-zA-Z]+")) {
         * JOptionPane.showMessageDialog(null, "Last name can only contain letters");
         * return;
         * }
         * 
         * // Validate program
         * if (!program.matches("[a-zA-Z ]+")) {
         * JOptionPane.showMessageDialog(null,
         * "Program can only contain letters and spaces");
         * return;
         * }
         * 
         * // Validate year of study
         * int year;
         * try {
         * year = Integer.parseInt(yearStr);
         * if (year < 1900 || year > 2100) {
         * throw new NumberFormatException();
         * }
         * } catch (NumberFormatException ex) {
         * JOptionPane.showMessageDialog(null,
         * "Invalid year of study. Please enter a valid year (e.g., 2023).");
         * return;
         * }
         * 
         * // Validate date of birth
         * Date dob;
         * try {
         * dob = Date.valueOf(dobStr); // Ensure dobStr is in the correct format
         * (yyyy-MM-dd)
         * } catch (IllegalArgumentException ex) {
         * JOptionPane.showMessageDialog(null,
         * "Invalid date of birth. Please use the format yyyy-MM-dd.");
         * return;
         * }
         * 
         * // Check if passwords match
         * if (!Arrays.equals(password, confirmPassword)) {
         * JOptionPane.showMessageDialog(null, "Passwords do not match");
         * return;
         * }
         * 
         * // create new student account
         * Student newStudent = new Student(fname, lname, program, year, dob);
         * // save student to database or file
         * JOptionPane.showMessageDialog(null, "Account created successfully");
         * registrationFrame.dispose();
         * loginFrame();
         * 
         * } catch (Exception g) {
         * g.printStackTrace();
         * JOptionPane.showMessageDialog(null,
         * "An unexpected error occurred. Please try again.");
         * }
         * }
         * });
         */

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
