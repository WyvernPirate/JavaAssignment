public class Student {
    private String firstName;
    private String lastName;
    private String program;
    private int year;
    private int studentId;
    private String dateOfBirth;

    // constructor
    public Student(String firstName, String lastName, String program, int year, int studentId, String dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.program = program;
        this.year = year;
        this.studentId = studentId;
        this.dateOfBirth = dateOfBirth;
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

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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