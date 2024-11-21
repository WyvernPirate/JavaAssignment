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

        // create components for panels
        JLabel welcome = new JLabel("Welcome to the  Student Portal User " + id);
        JButton logoutButton = new JButton("Logout");
        JButton viewStudentButton = new JButton("Personal Details");
        JButton viewTranscriptButton = new JButton("Transcript");
        JButton modulesButton = new JButton("Modules");
        JButton addMButton = new JButton("Add");
        JButton removeMButton = new JButton("Remove");
        JButton editSButton = new JButton("Edit");
        JButton editMButton = new JButton("Edit");

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
        // add button functionality
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(cPanel, "Are you sure you want to logout?",
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
                buttonPanel.add(addMButton);
                buttonPanel.add(removeMButton);
                buttonPanel.add(editMButton);
                cPanel.add(buttonPanel, BorderLayout.SOUTH);
            }
        });

        addMButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Create a new panel for adding a module
                JPanel addModulePanel = new JPanel();
                addModulePanel.setLayout(new GridLayout(0, 2));

                // Create fields for module details
                JTextField moduleCodeField = new JTextField();
                JTextField moduleNameField = new JTextField();
                JTextField moduleMarkField = new JTextField();
                JTextField moduleCreditsField = new JTextField();
                JTextField moduleYearField = new JTextField();
                JTextField moduleSemesterField = new JTextField();

                // Add fields to the panel
                addModulePanel.add(new JLabel("Module Code:"));
                addModulePanel.add(moduleCodeField);
                addModulePanel.add(new JLabel("Module Name:"));
                addModulePanel.add(moduleNameField);
                addModulePanel.add(new JLabel("Module Mark:"));
                addModulePanel.add(moduleMarkField);
                addModulePanel.add(new JLabel("Module Credits:"));
                addModulePanel.add(moduleCreditsField);
                addModulePanel.add(new JLabel("Module Year:"));
                addModulePanel.add(moduleYearField);
                addModulePanel.add(new JLabel("Module Semester:"));
                addModulePanel.add(moduleSemesterField);

                // Show the panel in a dialog
                int result = JOptionPane.showConfirmDialog(cPanel, addModulePanel, "Add Module",
                        JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    try {
                        // Get the module details from the fields
                        String moduleCode = moduleCodeField.getText();
                        String moduleName = moduleNameField.getText();
                        Double moduleMark = Double.parseDouble(moduleMarkField.getText());
                        int moduleCredits = Integer.parseInt(moduleCreditsField.getText());
                        int moduleYear = Integer.parseInt(moduleYearField.getText());
                        int moduleSemester = Integer.parseInt(moduleSemesterField.getText());

                        // Create a new module
                        Module m = new Module(moduleCode, moduleName, moduleMark, moduleCredits, moduleSemester,
                                moduleYear);

                        // Add the module to the database
                        n.addModule(m, id);

                        // Add the module to the table
                        DefaultTableModel model = (DefaultTableModel) table.getModel();
                        model.addRow(new Object[] { m.getCode(), m.getName(), m.getMark(), m.getCredits(), m.getYear(),
                                m.getSemester() });

                        JOptionPane.showMessageDialog(cPanel, "Module Added successfully");

                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(cPanel,
                                "Invalid input. Please enter valid numbers for credits, year, and semester.");
                    }
                }
            }
        });

        // remove module button functionality
        removeMButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                // remove selected module from table and database
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    // confirmation of removal
                    int result = JOptionPane.showConfirmDialog(cPanel, "Are you sure you want to remove this module?",
                            "Remove Module", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        DefaultTableModel model = (DefaultTableModel) table.getModel();
                        String moduleCode = (String) model.getValueAt(selectedRow, 0);
                        model.removeRow(selectedRow);
                        // retrieve module code from removed row

                        // remove module from database
                        n.deleteModule(id, moduleCode);
                        JOptionPane.showMessageDialog(cPanel, "Module removed successfully");
                    }
                } else {
                    JOptionPane.showMessageDialog(cPanel, "Please select a module to remove");
                }
            }
        });

        // edit a module
        editMButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // retrieve selected module details from table
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    String moduleCode = (String) model.getValueAt(selectedRow, 0);
                    String moduleName = (String) model.getValueAt(selectedRow, 1);
                    String moduleMark = String.valueOf(model.getValueAt(selectedRow, 2));
                    String moduleCredits = String.valueOf(model.getValueAt(selectedRow, 3));
                    String moduleYear = String.valueOf(model.getValueAt(selectedRow, 4));
                    String moduleSemester = String.valueOf(model.getValueAt(selectedRow, 5));

                    // create edit module dialog
                    JPanel editModulePanel = new JPanel();
                    editModulePanel.setLayout(new GridLayout(0, 2));

                    JTextField moduleNameField = new JTextField(moduleName);
                    JTextField moduleMarkField = new JTextField(String.valueOf(moduleMark));
                    JTextField moduleCreditsField = new JTextField(moduleCredits);
                    JTextField moduleYearField = new JTextField(moduleYear);
                    JTextField moduleSemesterField = new JTextField(moduleSemester);
                    editModulePanel.add(new JLabel("Module Name:"));
                    editModulePanel.add(moduleNameField);
                    editModulePanel.add(new JLabel("Module Mark:"));
                    editModulePanel.add(moduleMarkField);
                    editModulePanel.add(new JLabel("Module Credits:"));
                    editModulePanel.add(moduleCreditsField);
                    editModulePanel.add(new JLabel("Module Year:"));
                    editModulePanel.add(moduleYearField);
                    editModulePanel.add(new JLabel("Module Semester:"));
                    editModulePanel.add(moduleSemesterField);

                    int result = JOptionPane.showConfirmDialog(cPanel, editModulePanel, "Edit Module " + moduleCode,
                            JOptionPane.OK_CANCEL_OPTION);
                    if (result == JOptionPane.OK_OPTION) {
                        // update module details
                        try {
                            Double mark = Double.parseDouble(moduleMarkField.getText());
                            int credits = Integer.parseInt(moduleCreditsField.getText());
                            int year = Integer.parseInt(moduleYearField.getText());
                            int semester = Integer.parseInt(moduleSemesterField.getText());

                            Module m = new Module(moduleCode, moduleNameField.getText(), mark, credits,
                                    semester, year);
                            n.editModule(m, id);

                            // update table
                            model.setValueAt(moduleCode, selectedRow, 0);
                            model.setValueAt(moduleNameField.getText(), selectedRow, 1);
                            model.setValueAt(moduleMarkField.getText(), selectedRow, 2);
                            model.setValueAt(moduleCreditsField.getText(), selectedRow, 3);
                            model.setValueAt(moduleYearField.getText(), selectedRow, 4);
                            model.setValueAt(moduleSemesterField.getText(), selectedRow, 5);

                            JOptionPane.showMessageDialog(cPanel, "Module " + moduleCode + " edited successfully");
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(cPanel,
                                    "Invalid input. Please enter valid numbers for credits, year, and semester.");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(cPanel, "Please select a module to edit");
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
                    studentDetailsPanel.add(new JLabel(studentInfo.getFirstName() + " " + studentInfo.getLastName()));
                    studentDetailsPanel.add(new JLabel("Program:"));
                    studentDetailsPanel.add(new JLabel(studentInfo.getProgram()));
                    studentDetailsPanel.add(new JLabel("Year of Study:"));
                    studentDetailsPanel.add(new JLabel(String.valueOf(studentInfo.getYear())));
                    studentDetailsPanel.add(new JLabel("Date of Birth:"));
                    studentDetailsPanel.add(new JLabel(String.valueOf(studentInfo.getDob())));

                    editSButton.setVisible(true);
                    editSButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            // edit student details
                            System.out.println("Edit Student button clicked");
                            cPanel.removeAll();
                            cPanel.setLayout(new BorderLayout());
                            JPanel editStudentPanel = new JPanel();
                            editStudentPanel.setLayout(new GridLayout(0, 2));
                            JTextField firstNameField = new JTextField(studentInfo.getFirstName());
                            JTextField lastNameField = new JTextField(studentInfo.getLastName());
                            JTextField programField = new JTextField(studentInfo.getProgram());
                            JTextField yearField = new JTextField(String.valueOf(studentInfo.getYear()));
                            JTextField dobField = new JTextField(String.valueOf(studentInfo.getDob()));
                            editStudentPanel.add(new JLabel("First Name:"));
                            editStudentPanel.add(firstNameField);
                            editStudentPanel.add(new JLabel("Last Name:"));
                            editStudentPanel.add(lastNameField);
                            editStudentPanel.add(new JLabel("Program:"));
                            editStudentPanel.add(programField);
                            editStudentPanel.add(new JLabel("Year of Study:"));
                            editStudentPanel.add(yearField);
                            editStudentPanel.add(new JLabel("Date of Birth (yyyy-mm-dd):"));
                            editStudentPanel.add(dobField);
                            JButton saveButton = new JButton("Save");
                            saveButton.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    // save edited student details
                                    System.out.println("Save button clicked");
                                    String firstName = firstNameField.getText();
                                    String lastName = lastNameField.getText();
                                    String program = programField.getText();
                                    int year;
                                    try {
                                        year = Integer.parseInt(yearField.getText());
                                    } catch (NumberFormatException ex) {
                                        JOptionPane.showMessageDialog(cPanel,
                                                "Invalid year of study. Please enter a valid year.");
                                        return;
                                    }
                                    Date dob;
                                    try {
                                        dob = Date.valueOf(dobField.getText()); // Ensure dobStr is in the correct
                                                                                // format (yyyy-MM-dd)
                                    } catch (IllegalArgumentException ex) {
                                        JOptionPane.showMessageDialog(cPanel,
                                                "Invalid date of birth. Please use the format yyyy-MM-dd.");
                                        return;
                                    }

                                    // Validate first name and last name
                                    if (!firstName.matches("[a-zA-Z]+")) {
                                        JOptionPane.showMessageDialog(null, "First name can only contain letters");
                                        return;
                                    }
                                    if (!lastName.matches("[a-zA-Z]+")) {
                                        JOptionPane.showMessageDialog(null, "Last name can only contain letters");
                                        return;
                                    }

                                    // Validate program
                                    if (!program.matches("[a-zA-Z ]+")) {
                                        JOptionPane.showMessageDialog(null,
                                                "Program can only contain letters and spaces");
                                        return;
                                    }

                                    Student p = new Student(firstName, lastName, program, year, dob);
                                    // update student info in database
                                    n.updateStudentInfo(p, id);
                                    // refresh student details panel
                                    viewStudentButton.doClick();
                                }
                            });
                            cPanel.add(editStudentPanel, BorderLayout.CENTER);
                            cPanel.add(saveButton, BorderLayout.SOUTH);
                            cPanel.revalidate();
                            cPanel.repaint();
                        }
                    });
                    JPanel buttonPanel = new JPanel();
                    buttonPanel.add(editSButton);
                    cPanel.add(studentDetailsPanel, BorderLayout.CENTER);
                    cPanel.add(buttonPanel, BorderLayout.SOUTH);
                } else {
                    cPanel.add(new JLabel("No student info found"), BorderLayout.CENTER);
                }
                cPanel.revalidate();
                cPanel.repaint();
            }

        });

        // transcript button functionality

        // add panels to frame
        studentFrame.add(tPanel, BorderLayout.NORTH);
        studentFrame.add(lPanel, BorderLayout.WEST);
        studentFrame.add(cPanel, BorderLayout.CENTER);
        studentFrame.add(bPanel, BorderLayout.SOUTH);
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
