import java.sql.Date;

public class Student {
    private String firstName;
    private String lastName;
    private String program;
    private int year;
    private int studentId;
    private Date dateOfBirth;
    protected String username;

    public Student() {
        this.studentId = 0;
        this.lastName = "eric";
    };

    // constructor
    public Student(String firstName, String lastName, String program, int year, Date dob) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.program = program;
        this.year = year;
        this.dateOfBirth = dob;
    }

    // getters and setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public Date getDob() {
        return dateOfBirth;
    }

    public void setDob(Date doB) {
        this.dateOfBirth = doB;
    }

    // check credentials for existing acc from database
    public boolean checkCredentials(String username, String password) {
        return username.equals("student123") && password.equals("12345");
    }

    @Override
    public String toString() {
        return "Student{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", program='" + program + '\'' +
                ", year=" + year +
                ", studentId=" + studentId +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                '}';
    }
}
