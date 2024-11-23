import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Date;
import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
        JLabel welcome = new JLabel("Welcome Admin");
        JButton logoutButton = new JButton("Logout");
        JButton viewStudentButton = new JButton("View Student");
        JButton transcriptButton = new JButton("Transcript");
        JButton addSButton = new JButton("Add");
        JButton removeSButton = new JButton("Remove");
        JButton editSButton = new JButton("Edit");

        // create table for students
        String[] columns = { "Student ID", "Name", "Surname", "Program", "Year", "Date of Birth" };
        DefaultTableModel students = new DefaultTableModel(columns, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0)
                    return Integer.class;
                else
                    return String.class;
            }
        };
        JTable table = new JTable(students);

        // add button functionality
        viewStudentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // view student functionality
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
                        for (Student m : n.retrieveAllStudents()) {
                            model.addRow(
                                    new Object[] { m.getStudentId(), m.getFirstName(), m.getLastName(), m.getProgram(),
                                            m.getYear(), m.getDob() });
                        }
                    }
                });
                refreshButton.doClick();

                JPanel buttonPanel = new JPanel();
                buttonPanel.add(refreshButton);
                buttonPanel.add(addSButton);
                buttonPanel.add(removeSButton);
                buttonPanel.add(editSButton);
                buttonPanel.add(transcriptButton);
                cPanel.add(buttonPanel, BorderLayout.SOUTH);
            }
        });

        viewStudentButton.doClick();

        // add student button functionality
        addSButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // create a new panel ofr adding a student
                JPanel addPanel = new JPanel();
                addPanel.setLayout(new GridLayout(0, 2));

                // create fields for student details

                JTextField studentIdField = new JTextField();
                JTextField firstNameField = new JTextField();
                JTextField lastNameField = new JTextField();
                JTextField programField = new JTextField();
                JTextField yearField = new JTextField();
                JTextField dobField = new JTextField();

                // add fields to the panel
                addPanel.add(new JLabel("Student ID"));
                addPanel.add(studentIdField);
                addPanel.add(new JLabel("First Name"));
                addPanel.add(firstNameField);
                addPanel.add(new JLabel("Last Name"));
                addPanel.add(lastNameField);
                addPanel.add(new JLabel("Program"));
                addPanel.add(programField);
                addPanel.add(new JLabel("Year"));
                addPanel.add(yearField);
                addPanel.add(new JLabel("Date of Birth"));
                addPanel.add(dobField);

                // show the panel in a dialog
                int result = JOptionPane.showConfirmDialog(cPanel, addPanel, "Add Student",
                        JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    try {
                        // get the student details from the fields
                        int studentId = Integer.parseInt(studentIdField.getText());
                        String firstName = firstNameField.getText();
                        String lastName = lastNameField.getText();
                        String program = programField.getText();
                        int year = Integer.parseInt(yearField.getText());
                        Date dob = Date.valueOf(dobField.getText());

                        // check all fields are none empty
                        if (studentId != 0 && !firstName.isEmpty() && !lastName.isEmpty() &&
                                !program.isEmpty() && year != 0 && dob != null) {

                            // validate student id
                            if (studentId < 0) {
                                JOptionPane.showMessageDialog(cPanel, "Student ID must be a positive integer");
                                return;
                            }

                            // Validate first name and last name
                            if (!firstName.matches("[a-zA-Z]+")) {
                                JOptionPane.showMessageDialog(cPanel, "First name can only contain letters");
                                return;
                            }
                            if (!lastName.matches("[a-zA-Z]+")) {
                                JOptionPane.showMessageDialog(cPanel, "Last name can only contain letters");
                                return;
                            }

                            // Validate program
                            if (!program.matches("[a-zA-Z ]+")) {
                                JOptionPane.showMessageDialog(cPanel,
                                        "Program can only contain letters and spaces");
                                return;
                            }
                            // validate year so its between 1 and 5
                            if (year < 1 || year > 5) {
                                JOptionPane.showMessageDialog(cPanel, "Year must be between 1 and 5");
                            }

                            // create a new student
                            Student student = new Student(firstName, lastName, program, year, dob);
                            // add the student to the database
                            if (n.createStudentAccounts(student) == true) {

                                // add student to the table
                                DefaultTableModel model = (DefaultTableModel) table.getModel();
                                model.addRow(new Object[] { student.getStudentId(), student.getFirstName(),
                                        student.getLastName(), student
                                                .getProgram(),
                                        student.getYear(), student.getDob() });

                                JOptionPane.showMessageDialog(cPanel, "Student Added Successfully");
                            } else {
                                JOptionPane.showMessageDialog(cPanel, "Failed to add student");
                            }
                        } else {
                            JOptionPane.showMessageDialog(cPanel, "Please fill in all fields");
                        }
                    } catch (DateTimeException f) {
                        JOptionPane.showMessageDialog(cPanel, "Invalid Date Format");
                    } catch (NumberFormatException n) {
                        JOptionPane.showMessageDialog(cPanel, "Invalid Input");
                    } catch (Exception r) {
                        JOptionPane.showMessageDialog(cPanel, "Error Adding Student");
                        r.printStackTrace();
                    }
                }

            }
        });

        // remove student button functionality
        removeSButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // get the selected row
                int row = table.getSelectedRow();
                if (row >= 0) {
                    // confirmation of removal
                    int confirm = JOptionPane.showConfirmDialog(cPanel, "Are you sure you want to remove this student?",
                            "Confirm Removal", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        // get the student id from the selected row
                        int studentId = (int) table.getValueAt(row, 0);
                        // remove the student from the database
                        if (n.removeStudent(studentId) == true) {
                            // remove the row from the table
                            DefaultTableModel model = (DefaultTableModel) table.getModel();
                            model.removeRow(row);
                            JOptionPane.showMessageDialog(cPanel, "Student Removed Successfully");
                        } else {
                            JOptionPane.showMessageDialog(cPanel, "Error Removing Student");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(cPanel, "Please select a student to remove");
                }
            }
        });
        // edit a student
        editSButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // get the selected row
                int row = table.getSelectedRow();
                if (row >= 0) {
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    // get the student id from the selected row
                    int studentId = (int) model.getValueAt(row, 0);
                    String studentFirstName = (String) model.getValueAt(row, 1);
                    String studentLastName = (String) model.getValueAt(row, 2);
                    String program = (String) model.getValueAt(row, 3);
                    String year = String.valueOf(model.getValueAt(row, 4));
                    String dob = String.valueOf(model.getValueAt(row, 5));

                    // create edit student dialog
                    JPanel editSPanel = new JPanel();
                    editSPanel.setLayout(new GridLayout(0, 2));

                    JTextField sNameField = new JTextField(studentFirstName);
                    JTextField sLastNameField = new JTextField(studentLastName);
                    JTextField pField = new JTextField(program);
                    JTextField yField = new JTextField(year);
                    JTextField dField = new JTextField(String.valueOf(dob));

                    editSPanel.add(new JLabel("First Name"));
                    editSPanel.add(sNameField);
                    editSPanel.add(new JLabel("Last Surname"));
                    editSPanel.add(sLastNameField);
                    editSPanel.add(new JLabel("Program"));
                    editSPanel.add(pField);
                    editSPanel.add(new JLabel("Year"));
                    editSPanel.add(yField);
                    editSPanel.add(new JLabel("Date of Birth"));
                    editSPanel.add(dField);

                    // create a confirmation dialog
                    int confirm = JOptionPane.showConfirmDialog(cPanel, editSPanel, "Edit Student",
                            JOptionPane.OK_CANCEL_OPTION);
                    if (confirm == JOptionPane.OK_OPTION) {
                        // update the student in the database
                        try {
                            // get student details from fields
                            String newFirstName = sNameField.getText();
                            String newLastName = sLastNameField.getText();
                            String newProgram = pField.getText();
                            int newYear = Integer.parseInt(yField.getText());
                            Date newDob = Date.valueOf(dField.getText());

                            // check if all fields are non empty
                            if (!newFirstName.isEmpty() && !newLastName.isEmpty() && !newProgram.isEmpty()
                                    && newYear != 0 && newDob != null) {

                                // Validate first name and last name
                                if (!newFirstName.matches("[a-zA-Z]+")) {
                                    JOptionPane.showMessageDialog(cPanel, "First name can only contain letters");
                                    return;
                                }
                                if (!newLastName.matches("[a-zA-Z]+")) {
                                    JOptionPane.showMessageDialog(cPanel, "Last name can only contain letters");
                                    return;
                                }

                                // Validate program
                                if (!newProgram.matches("[a-zA-Z ]+")) {
                                    JOptionPane.showMessageDialog(cPanel,
                                            "Program can only contain letters and spaces");
                                    return;
                                }

                                // validate year
                                if (newYear < 1 || newYear > 5) {
                                    JOptionPane.showMessageDialog(cPanel, "Please enter a valid year");
                                    return;
                                }

                                // create a new student
                                Student student = new Student(newFirstName, newLastName, newProgram, newYear, newDob);
                                // update the student in the database
                                if (n.updateStudentInfo(student, studentId) == true) {
                                    // update the table
                                    model.setValueAt(newFirstName, row, 1);
                                    model.setValueAt(newLastName, row, 2);
                                    model.setValueAt(newProgram, row, 3);
                                    model.setValueAt(newYear, row, 4);
                                    model.setValueAt(newDob, row, 5);

                                    JOptionPane.showMessageDialog(cPanel,
                                            "Student " + studentId + " edited successfully");
                                } else {
                                    JOptionPane.showMessageDialog(cPanel, "Failed to edit student");
                                }

                            } else {
                                JOptionPane.showMessageDialog(cPanel, "Please fill in all fields");
                            }
                        } catch (NumberFormatException g) {
                            JOptionPane.showMessageDialog(cPanel, "Please enter a valid year");
                        } catch (DateTimeException f) {
                            JOptionPane.showMessageDialog(cPanel, "Please enter a valid date");
                        }
                    }
                }
            }
        });

        transcriptButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                // transcript functionality
                JPanel selStdPanel = new JPanel();
                selStdPanel.setLayout(new GridLayout(0, 2));

                // create components
                JLabel selStdLabel = new JLabel("Select Student:");
                JComboBox<String> selStdComboBox = new JComboBox<>();
                for (Student m : n.retrieveAllStudents()) {
                    selStdComboBox.addItem(String.valueOf(m.getStudentId()));
                }
                selStdPanel.add(selStdLabel);
                selStdPanel.add(selStdComboBox);

                int result = JOptionPane.showConfirmDialog(cPanel, selStdPanel, "Select Student",
                        JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    String selectedStudentId = (String) selStdComboBox.getSelectedItem();
                    int stdid = Integer.parseInt(selectedStudentId);
                    JFrame transcript = new JFrame();
                    transcript.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                    transcript.add(generateTranscript(stdid));
                    transcript.pack();
                    transcript.setVisible(true);
                }
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(cPanel, "Are you sure you want to logout?",
                        "Logout Confirmation", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    System.out.println("Logout button clicked");
                    adminFrame.dispose();
                    loginFrame();
                }
            }
        });

        // add components to panels
        tPanel.add(welcome);
        tPanel.add(logoutButton);
        lPanel.add(viewStudentButton);
        // add components to frame
        adminFrame.add(tPanel, BorderLayout.NORTH);
        adminFrame.add(lPanel, BorderLayout.WEST);
        adminFrame.add(cPanel, BorderLayout.CENTER);
        adminFrame.add(bPanel, BorderLayout.SOUTH);

        adminFrame.setVisible(true);
    }

    public void studentFrame(int id) {
        // create frames for student
        JFrame studentFrame = new JFrame("Student Portal");
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
        JButton transcriptButton = new JButton("Transcript");
        JButton modulesButton = new JButton("Modules");
        JButton addMButton = new JButton("Add");
        JButton removeMButton = new JButton("Remove");
        JButton editSButton = new JButton("Edit");
        JButton editMButton = new JButton("Edit");

        // create table for grades
        String[] columns = { "Module Code", "Name", "Type", "Marks", "Credits", "Year", "Semester" };
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
        lPanel.add(transcriptButton);
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
                            model.addRow(
                                    new Object[] { m.getCode(), m.getName(), m.getType(), m.getMark(), m.getCredits(),
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
                JComboBox<String> moduleTypeComboBox = new JComboBox<>(new String[] { "Core", "Elective",
                        "Core Elective" });// use a combo box for type of modules
                JTextField moduleMarkField = new JTextField();
                JTextField moduleCreditsField = new JTextField();
                JTextField moduleYearField = new JTextField();
                JTextField moduleSemesterField = new JTextField();

                // Add fields to the panel
                addModulePanel.add(new JLabel("Module Code:"));
                addModulePanel.add(moduleCodeField);
                addModulePanel.add(new JLabel("Module Name:"));
                addModulePanel.add(moduleNameField);
                addModulePanel.add(new JLabel("Module type:"));
                addModulePanel.add(moduleTypeComboBox);
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
                        String moduleType = (String) moduleTypeComboBox.getSelectedItem();
                        Double moduleMark = Double.parseDouble(moduleMarkField.getText());
                        int moduleCredits = Integer.parseInt(moduleCreditsField.getText());
                        int moduleYear = Integer.parseInt(moduleYearField.getText());
                        int moduleSemester = Integer.parseInt(moduleSemesterField.getText());

                        // check all fields are non empty
                        if (!moduleCode.isEmpty() || !moduleName.isEmpty() || !moduleMarkField.getText().isEmpty()
                                || !moduleCreditsField.getText().isEmpty()
                                || !moduleYearField.getText().isEmpty() || !moduleSemesterField.getText().isEmpty()) {

                            // validate that module name only contains letters
                            if (!moduleName.matches("[a-zA-Z]+")) {
                                JOptionPane.showMessageDialog(cPanel, "Module name can only contain letters");
                                return;
                            }

                            // validate module mark
                            if (moduleMark < 0 || moduleMark > 100) {
                                JOptionPane.showMessageDialog(cPanel, "Module mark must be between 0 and 100");
                                return;
                            }
                            // validate year
                            if (moduleYear < 1 || moduleYear > 5) {
                                JOptionPane.showMessageDialog(cPanel, "Module year must be between 1 and 5");
                                return;
                            }
                            // validate semester
                            if (moduleSemester < 1 || moduleSemester > 2) {
                                JOptionPane.showMessageDialog(cPanel, "Module semester must be between 1 and 2");
                                return;
                            }

                            // Create a new module
                            Module m = new Module(moduleCode, moduleName, moduleType, moduleMark, moduleCredits,
                                    moduleSemester,
                                    moduleYear);

                            // Add the module to the database
                            if (n.addModule(m, id) == true) {

                                // Add the module to the table
                                DefaultTableModel model = (DefaultTableModel) table.getModel();
                                model.addRow(
                                        new Object[] { m.getCode(), m.getName(), m.getType(), m.getMark(),
                                                m.getCredits(),
                                                m.getYear(),
                                                m.getSemester() });

                                JOptionPane.showMessageDialog(cPanel, "Module Added successfully");
                            } else {
                                JOptionPane.showMessageDialog(cPanel, "Module not added");
                            }
                        } else {
                            JOptionPane.showMessageDialog(cPanel, "Please fill in all fields");

                        }
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

                        // remove module from database
                        if (n.deleteModule(id, moduleCode) == true) {
                            model.removeRow(selectedRow);
                            JOptionPane.showMessageDialog(cPanel, "Module removed successfully");
                        } else {
                            JOptionPane.showMessageDialog(cPanel, "Module not removed");
                        }
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
                    String moduleType = (String) model.getValueAt(selectedRow, 2);
                    String moduleMark = String.valueOf(model.getValueAt(selectedRow, 3));
                    String moduleCredits = String.valueOf(model.getValueAt(selectedRow, 4));
                    String moduleYear = String.valueOf(model.getValueAt(selectedRow, 5));
                    String moduleSemester = String.valueOf(model.getValueAt(selectedRow, 6));

                    // create edit module dialog
                    JPanel editModulePanel = new JPanel();
                    editModulePanel.setLayout(new GridLayout(0, 2));

                    JTextField moduleNameField = new JTextField(moduleName);
                    JComboBox<String> moduleTypeComboBox = new JComboBox<>(new String[] { "Core", "Elective",
                            "Core Elective" });// use a combo box for type of modules
                    JTextField moduleMarkField = new JTextField(String.valueOf(moduleMark));
                    JTextField moduleCreditsField = new JTextField(moduleCredits);
                    JTextField moduleYearField = new JTextField(moduleYear);
                    JTextField moduleSemesterField = new JTextField(moduleSemester);

                    editModulePanel.add(new JLabel("Module Name:"));
                    editModulePanel.add(moduleNameField);
                    editModulePanel.add(new JLabel("Module Type:"));
                    editModulePanel.add(moduleTypeComboBox);
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
                        try {
                            // Get the module details from the fields
                            String newModuleName = moduleNameField.getText();
                            String newModuleType = (String) moduleTypeComboBox.getSelectedItem();
                            Double newModuleMark = Double.parseDouble(moduleMarkField.getText());
                            int newModuleCredits = Integer.parseInt(moduleCreditsField.getText());
                            int newModuleYear = Integer.parseInt(moduleYearField.getText());
                            int newModuleSemester = Integer.parseInt(moduleSemesterField.getText());

                            // Validate semester
                            if (newModuleSemester != 1 && newModuleSemester != 2) {
                                JOptionPane.showMessageDialog(cPanel, "Invalid semester can be 1 or 2");
                                return;
                            }

                            // validate credits to be between 1 and 4
                            if (newModuleCredits < 1 || newModuleCredits > 4) {
                                JOptionPane.showMessageDialog(cPanel, "Invalid credits (1-4)");
                                return;
                            }

                            // validate marks
                            if (newModuleMark < 0 || newModuleMark > 100) {
                                JOptionPane.showMessageDialog(cPanel, "Invalid marks (0-100)");
                                return;
                            }

                            // Create a new module
                            Module m = new Module(moduleCode, newModuleName, newModuleType, newModuleMark,
                                    newModuleCredits,
                                    newModuleSemester, newModuleYear);

                            // Update the module in the database
                            if (n.editModule(m, id) == true) {

                                // Update the table
                                model.setValueAt(moduleCode, selectedRow, 0);
                                model.setValueAt(moduleName, selectedRow, 1);
                                model.setValueAt(moduleType, selectedRow, 2);
                                model.setValueAt(moduleMark, selectedRow, 3);
                                model.setValueAt(moduleCredits, selectedRow, 4);
                                model.setValueAt(moduleYear, selectedRow, 5);
                                model.setValueAt(moduleSemester, selectedRow, 6);

                                JOptionPane.showMessageDialog(cPanel, "Module " + moduleCode + " edited successfully");
                            } else {
                                JOptionPane.showMessageDialog(cPanel, "Failed to edit module " + moduleCode);
                            }

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
                                        JOptionPane.showMessageDialog(cPanel, "First name can only contain letters");
                                        return;
                                    }
                                    if (!lastName.matches("[a-zA-Z]+")) {
                                        JOptionPane.showMessageDialog(cPanel, "Last name can only contain letters");
                                        return;
                                    }

                                    // Validate program
                                    if (!program.matches("[a-zA-Z ]+")) {
                                        JOptionPane.showMessageDialog(cPanel,
                                                "Program can only contain letters and spaces");
                                        return;
                                    }

                                    Student p = new Student(firstName, lastName, program, year, dob);
                                    // update student info in database
                                    if (n.updateStudentInfo(p, id) == true) {
                                        // update student info in GUI
                                        JOptionPane.showMessageDialog(cPanel, "Student details edited successfully");
                                        // refresh student details panel
                                        viewStudentButton.doClick();
                                    } else {
                                        JOptionPane.showMessageDialog(cPanel, "Failed to edit student details");
                                    }
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

        transcriptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cPanel.removeAll();
                cPanel.setLayout(new BorderLayout());
                cPanel.add(generateTranscript(id));

                cPanel.revalidate();
                cPanel.repaint();
            }

        });

        // add panels to frame
        studentFrame.add(tPanel, BorderLayout.NORTH);
        studentFrame.add(lPanel, BorderLayout.WEST);
        studentFrame.add(cPanel, BorderLayout.CENTER);
        studentFrame.add(bPanel, BorderLayout.SOUTH);
        studentFrame.setVisible(true);
    }

    public JPanel generateTranscript(int id) {

        // generate transcript for student with id
        // return transcript as string
        JPanel cPanel = new JPanel(new BorderLayout());
        JTextArea transcriptArea = new JTextArea();
        transcriptArea.setEditable(false);
        transcriptArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        // Retrieve student details
        Student studentInfo = n.retrieveStudentInfo(id);

        // Build header
        StringBuilder transcript = new StringBuilder();
        transcript.append("BOTSWANA INTERNATIONAL UNIVERSITY OF SCIENCE AND TECHNOLOGY\n");
        transcript.append("================== ACADEMIC TRANSCRIPT ==================\n\n");
        transcript.append(String.format("Student ID: %d\n", id));
        transcript
                .append(String.format("Name: %s %s\n", studentInfo.getFirstName(), studentInfo.getLastName()));
        transcript.append(String.format("Program: %s\n", studentInfo.getProgram()));
        transcript.append(String.format("Year of Study: %d\n", studentInfo.getYear()));
        transcript.append(String.format("Date of Birth: %s\n", studentInfo.getDob()));
        transcript.append("\n=======================================================\n\n");

        // Retrieve all modules
        List<Module> allModules = n.retrieveAllModules(id);

        // Group modules by year and semester
        Map<Integer, Map<Integer, List<Module>>> modulesByYearAndSemester = new TreeMap<>();

        for (Module module : allModules) {
            modulesByYearAndSemester
                    .computeIfAbsent(module.getYear(), k -> new TreeMap<>())
                    .computeIfAbsent(module.getSemester(), k -> new ArrayList<>())
                    .add(module);
        }

        double overallGPASum = 0.0;
        int totalCredits = 0;

        // Process each year and semester
        for (Map.Entry<Integer, Map<Integer, List<Module>>> yearEntry : modulesByYearAndSemester.entrySet()) {
            int year = yearEntry.getKey();
            transcript.append(String.format("Year %d\n", year));
            transcript.append("-------------------------------------------------------\n");

            for (Map.Entry<Integer, List<Module>> semesterEntry : yearEntry.getValue().entrySet()) {
                int semester = semesterEntry.getKey();
                List<Module> modules = semesterEntry.getValue();

                transcript.append(String.format("\nSemester %d\n", semester));
                transcript
                        .append("Module Code  Module Name             Type           Credits  Mark  Grade\n");
                transcript.append(
                        "----------------------------------------------------------------------------\n");

                double semesterGPASum = 0.0;
                int semesterCredits = 0;

                // Process modules for this semester
                for (Module module : modules) {
                    transcript.append(String.format("%-11s %-24s %-13s %7d %6.1f %6s\n",
                            module.getCode(),
                            truncateString(module.getName(), 24),
                            module.getType(),
                            module.getCredits(),
                            module.getMark(),
                            module.getGrade(module.getMark())));

                    // Calculate GPA contribution
                    double gpaPoints = calculateGPAPoints(module.getMark());
                    semesterGPASum += gpaPoints * module.getCredits();
                    semesterCredits += module.getCredits();

                    // Add to overall totals
                    overallGPASum += gpaPoints * module.getCredits();
                    totalCredits += module.getCredits();
                }

                // Calculate and display Semester GPA
                double semesterGPA = semesterCredits > 0 ? semesterGPASum / semesterCredits : 0.0;
                transcript.append("\n");
                transcript.append(String.format("Semester GPA: %.2f\n", semesterGPA));

                // Calculate and display Cumulative GPA up to this point
                double cumulativeGPA = totalCredits > 0 ? overallGPASum / totalCredits : 0.0;
                transcript.append(String.format("Cumulative GPA: %.2f\n", cumulativeGPA));
                transcript.append("\n");

                // Determine Academic Standing
                String academicStanding = determineAcademicStanding(semesterGPA);
                transcript.append(String.format("Academic Standing: %s\n", academicStanding));
                transcript.append("\n");
            }
            transcript.append("=======================================================\n\n");
        }

        // Display final CGPA
        double finalCGPA = totalCredits > 0 ? overallGPASum / totalCredits : 0.0;
        transcript.append(String.format("Final Cumulative GPA: %.2f\n", finalCGPA));

        transcriptArea.setText(transcript.toString());
        cPanel.add(new JScrollPane(transcriptArea), BorderLayout.CENTER);

        // Add print button to txt file
        JButton printButton = new JButton("Print Transcript");
        printButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // print to txt file
                try (PrintWriter writer = new PrintWriter("transcript.txt")) {
                    writer.println(transcript.toString());
                    JOptionPane.showMessageDialog(cPanel, "Transcript Printed to transcript.txt");
                } catch (FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, "Error printing transcript: " + ex.getMessage());
                }
            }
        });

        JButton printButtonPDF = new JButton("Print Transcript to PDF");
        printButtonPDF.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // print to pdf file

            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(printButton);
        buttonPanel.add(printButtonPDF);
        cPanel.add(buttonPanel, BorderLayout.SOUTH);
        return cPanel;
    }

    // method to truncate strings that are too long
    private String truncateString(String str, int length) {
        if (str.length() <= length)
            return str;
        return str.substring(0, length - 3) + "...";
    }

    // method to determine academic standing
    private String determineAcademicStanding(double semesterGPA) {
        if (semesterGPA >= 2.5)
            return "GOOD STANDING";
        else if (semesterGPA >= 1.8)
            return "WARNING";
        else
            return "ACADEMIC PROBATION";
    }

    // method to calculate GPA points based on marks
    private double calculateGPAPoints(double mark) {
        if (mark >= 85)
            return 4.0; // A
        else if (mark >= 80)
            return 3.7; // A-
        else if (mark >= 75)
            return 3.3; // B+
        else if (mark >= 70)
            return 3.0; // B
        else if (mark >= 65)
            return 2.7; // B-
        else if (mark >= 60)
            return 2.3; // C+
        else if (mark >= 55)
            return 2.0; // C
        else if (mark >= 50)
            return 1.7; // C-
        else
            return 0.0; // F
    }

}
