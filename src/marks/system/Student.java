package marks.system;

public class Student extends Person {
    private String roll;
    private String name;
    private String dept;
    private String section;
    private int javaMarks;
    private int cMarks;
    private int cppMarks;
    private int pythonMarks;

    public Student(String roll, String name, String dept, String section,
                   int javaMarks, int cMarks, int cppMarks, int pythonMarks) {
        super(roll, name);

        this.roll = roll;
        this.name = name;
        this.dept = dept;
        this.section = section;
        this.javaMarks = javaMarks;
        this.cMarks = cMarks;
        this.cppMarks = cppMarks;
        this.pythonMarks = pythonMarks;
    }

    public String getRoll() { return roll; }
    public String getName() { return name; }
    public String getDept() { return dept; }
    public String getSection() { return section; }
    public int getJavaMarks() { return javaMarks; }
    public int getCMarks() { return cMarks; }
    public int getCppMarks() { return cppMarks; }
    public int getPythonMarks() { return pythonMarks; }

    public int total() { return javaMarks + cMarks + cppMarks + pythonMarks; }
    public double percentage() { return total() / 4.0; }

    public boolean isPassed() {
        return javaMarks >= 50 && cMarks >= 50 && cppMarks >= 50 && pythonMarks >= 50;
    }

    public String grade() {
        double p = percentage();
        if (p >= 90) return "A+";
        else if (p >= 80) return "A";
        else if (p >= 70) return "B";
        else if (p >= 60) return "C";
        else if (p >= 50) return "D";
        else return "F";
    }
}
