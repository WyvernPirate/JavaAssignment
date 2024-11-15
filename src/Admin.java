public class Admin {
    private String name;
    private String email;
    private String password;

    public Admin(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean checkCredentials() {
        return this.name.equals("registry") && this.password.equals("letmein123");
    }

}
