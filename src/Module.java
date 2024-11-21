public class Module {

    private String code;
    private String name;
    private double mark;
    private int credits;
    private int semester;
    private int year;

    public Module() {
        this.code = "";
        this.name = "";
        this.mark = 0.0;
        this.credits = 0;
        this.semester = 0;
        this.year = 0;
    }

    public Module(String code, String name, double mark, int credits, int semester, int year) {
        this.code = code;
        this.name = name;
        this.mark = mark;
        this.credits = credits;
        this.semester = semester;
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getMark() {
        return mark;
    }

    public void setMark(double mark) {
        this.mark = mark;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Module{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", mark=" + mark +
                ", credits=" + credits +
                ", semester=" + semester +
                ", year=" + year +
                '}';
    }
}