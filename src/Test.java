public class Test {
    public static void main(String[] args) {
        GUI n = new GUI();
        n.loginFrame();
        DatabaseController d = new DatabaseController();
        d.connect();
    }

}
