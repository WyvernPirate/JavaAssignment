import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseController {

    // instance variables
    private Connection conn = null;
    private String url = "jdbc:mysql://localhost:3306/students";
    private String username = "registry";
    private String password = "letmein123";

    // method to connect to the database
    public Connection connect() {
        if (conn != null) {
            return conn;
        }
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connection successful");
            this.conn = connection;
        } catch (SQLException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Error loading database driver: " + e.getMessage());
            e.printStackTrace();
        }
        return conn;
    }

    // check user credentials
    public String checkCredentials(String username, String password) {
        String userType = null;
        if (conn == null) {
            System.out.println("Error: Database connection not established.");
            return null;
        }
        String query = "SELECT Type FROM users WHERE Username = ? AND Password = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    userType = rs.getString("Type");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error checking credentials: " + e.getMessage());
            e.printStackTrace();
        }
        return userType;
    }

    // create a new student account
    public boolean createStudentAccounts(Student p) {
        boolean flag = false;
        try (PreparedStatement pstmt = this.conn.prepareStatement(
                "INSERT INTO Student(StudentID, Name, Surname, Program, Year, Dob) VALUES(?,?,?,?,?,?)")) {
            pstmt.setInt(1, p.getStudentId());
            pstmt.setString(2, p.getFirstName());
            pstmt.setString(3, p.getLastName());
            pstmt.setString(4, p.getProgram());
            pstmt.setInt(5, p.getYear());
            pstmt.setDate(6, p.getDob());
            pstmt.executeUpdate();
            flag = true;
        } catch (SQLException e) {
            System.out.println("Error creating student account: " + e.getMessage());
            e.printStackTrace();
        }
        return flag;
    }

    // retrieve student information
    public Student retrieveStudentInfo(int studentId) {
        Student p = null;
        String query = "SELECT * FROM Student WHERE StudentID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, studentId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    p = new Student();
                    p.setStudentId(rs.getInt("StudentID"));
                    p.setFirstName(rs.getString("Name"));
                    p.setLastName(rs.getString("Surname"));
                    p.setProgram(rs.getString("Program"));
                    p.setYear(rs.getInt("Year"));
                    p.setDob(rs.getDate("Dob"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving student info: " + e.getMessage());
            e.printStackTrace();
        }
        return p;
    }

    // add a module from module object
    public boolean addModule(Module m, int studentID) {
        boolean flag = false;
        String query = "INSERT INTO Modules(StudentID, ModuleID, ModuleName,Marks,ModuleCredits, Semester,Year) VALUES(?,?,?,?,?,?,?)";
        try (PreparedStatement pstmt = this.conn.prepareStatement(query)) {
            pstmt.setInt(1, studentID);
            pstmt.setString(2, m.getCode());
            pstmt.setString(3, m.getName());
            pstmt.setDouble(4, m.getMark());
            pstmt.setInt(5, m.getCredits());
            pstmt.setInt(6, m.getSemester());
            pstmt.setInt(7, m.getYear());
            pstmt.executeUpdate();
            System.out.println("Module successfully added");
            flag = true;
        } catch (SQLException e) {
            System.out.println("Error adding module: " + e.getMessage());
            e.printStackTrace();
        }
        return flag;
    }

    public List<Module> retrieveAllModules(int StudentID) {
        List<Module> modules = new ArrayList<>();
        String query = "SELECT * FROM Modules WHERE StudentID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, StudentID);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Module m = new Module(
                            rs.getString("ModuleID"),
                            rs.getString("ModuleName"),
                            rs.getDouble("Marks"),
                            rs.getInt("ModuleCredits"),
                            rs.getInt("Semester"),
                            rs.getInt("Year"));
                    modules.add(m);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving modules: " + e.getMessage());
            e.printStackTrace();
        }
        return modules;
    }

    // Close the database connection
    public void closeConnection() {
        if (this.conn != null) {
            try {
                this.conn.close();
            } catch (SQLException e) {
                System.out.println("Error closing database connection: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}