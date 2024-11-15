import java.sql.*;

public class DatabaseController {

    // instance variables
    private Connection conn = null;
    private String url = "jdbc:mysql://localhost:3306/students";
    private String username = "root";
    private String password = "root";

    // method to connect to the database
    public boolean connect() {
        boolean flag = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            // establish the connection
            conn = DriverManager.getConnection(url);
            System.out.println("Connection successful");

            flag = true;
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");
            e.printStackTrace();

        } catch (SQLException e) {
            System.out.println("Error connecting to the database: ");
            e.printStackTrace();
        }
        return flag;
    }

    // create a new student account
    public boolean createStudentAccounts(Student p) {
        boolean flag = false;
        try {
            String query = "INSERT INTO Student(StudentID, Name, Surname, Program, Year, Dob) VALUES(?,?,?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, p.getStudentId());
            pstmt.setString(2, p.getFirstName());
            pstmt.setString(3, p.getLastName());
            pstmt.setString(4, p.getProgram());
            pstmt.setInt(5, p.getYear());
            pstmt.setDate(6, p.getDob());
            pstmt.executeUpdate();
            flag = true;
        } catch (SQLException e) {
            System.out.println("Error creating student account: ");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("An error occurred: ");
            e.printStackTrace();
        }
        return flag;
    }

}
