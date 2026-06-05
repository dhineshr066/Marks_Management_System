package marks.system;

import java.sql.*;
import java.util.*;

public class Teacher extends Person {
    private final Connection conn;
    private final Scanner sc = new Scanner(System.in);

    public Teacher(Connection conn) {
        super("teacher", "Teacher");

        this.conn = conn;
    }

    public boolean authenticate(String username, String password) throws SQLException {
        String sql = "SELECT COUNT(*) FROM teacher WHERE username=? AND password=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, username);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        rs.next();
        boolean ok = rs.getInt(1) > 0;
        rs.close();
        ps.close();
        return ok;
    }

    public void menu() {
        while (true) {
            try {
                System.out.println("\n--- Teacher Menu ---");
                System.out.println("1) Add Student");
                System.out.println("2) Add Marks");
                System.out.println("3) Update Marks");
                System.out.println("4) View All Students");
                System.out.println("5) Pass/Fail Summary");
                System.out.println("6) Filter by Percentage Range");
                System.out.println("7) Filter by Subject Marks Range");
                System.out.println("8) Delete Student");
                System.out.println("9) Logout");
                System.out.print("Enter choice: ");
                String choice = sc.nextLine().trim();

                switch (choice) {
                    case "1" -> addStudent();
                    case "2" -> addMarks();
                    case "3" -> updateMarks();
                    case "4" -> viewAllStudents();
                    case "5" -> passFailSummary();
                    case "6" -> filterByPercentageRange();
                    case "7" -> filterBySubjectMarksRange();
                    case "8" -> deleteStudentByRoll(conn);
                    case "9" -> { return; }
                    default -> System.out.println("Invalid choice!");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                sc.nextLine();
            }
        }
    }


    // Delete student by roll number
    // Method to delete a student by roll number
    private void deleteStudentByRoll(Connection conn) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Roll Number to delete: ");
        String roll = sc.nextLine();

        try {
            // Delete from marks table first (foreign key dependency)
            String sqlMarks = "DELETE FROM marks WHERE roll = ?";
            try (PreparedStatement psMarks = conn.prepareStatement(sqlMarks)) {
                psMarks.setString(1, roll);
                psMarks.executeUpdate();
            }

            // Delete from student table
            String sqlStudent = "DELETE FROM student WHERE roll = ?";
            try (PreparedStatement psStudent = conn.prepareStatement(sqlStudent)) {
                int rows = 0;
                psStudent.setString(1, roll);
                rows = psStudent.executeUpdate();

                if (rows > 0) {
                    System.out.println(" Student with Roll No " + roll + " deleted successfully.");
                } else {
                    System.out.println(" No student found with Roll No " + roll + ".");
                }
            }
        } catch (SQLException e) {
            System.out.println(" Error while deleting student: " + e.getMessage());
        }
    }



    private void addStudent() throws SQLException {
        System.out.print("Roll Number: ");
        String roll = sc.nextLine().trim();

        PreparedStatement check = conn.prepareStatement("SELECT COUNT(*) FROM student WHERE roll=?");
        check.setString(1, roll);
        ResultSet rsCheck = check.executeQuery();
        rsCheck.next();
        if (rsCheck.getInt(1) > 0) {
            System.out.println(" Student with this roll number already exists!");
            rsCheck.close();
            check.close();
            return;
        }
        rsCheck.close();
        check.close();

        // ArrayList to store student fields
        ArrayList<String> studentInfo = new ArrayList<>();

        System.out.print("Name: ");
        studentInfo.add(sc.nextLine());
        System.out.print("Department: ");
        studentInfo.add(sc.nextLine());
        System.out.print("Section: ");
        studentInfo.add(sc.nextLine());

        String sql = "INSERT INTO student(roll, name, department, section) VALUES(?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, roll);
        ps.setString(2, studentInfo.get(0)); // name
        ps.setString(3, studentInfo.get(1)); // department
        ps.setString(4, studentInfo.get(2)); // section
        ps.executeUpdate();
        ps.close();

        // ArrayList for initial marks (all zeros)
        ArrayList<Integer> initialMarks = new ArrayList<>(Arrays.asList(0, 0, 0, 0));

        PreparedStatement ps2 = conn.prepareStatement("INSERT INTO marks(roll, java, c, cpp, python) VALUES(?,?,?,?,?)");
        ps2.setString(1, roll);
        for (int i = 0; i < initialMarks.size(); i++) {
            ps2.setInt(i + 2, initialMarks.get(i));
        }
        ps2.executeUpdate();
        ps2.close();

        System.out.println(" Student added successfully!");
    }

    private void addMarks() throws SQLException {
        System.out.print("Enter Roll Number: ");
        String roll = sc.nextLine().trim();

        if (!studentExists(roll)) {
            System.out.println(" No student found with that roll number!");
            return;
        }

        // Check if marks already exist
        String checkSql = "SELECT java, c, cpp, python FROM marks WHERE roll=?";
        PreparedStatement checkPs = conn.prepareStatement(checkSql);
        checkPs.setString(1, roll);
        ResultSet rs = checkPs.executeQuery();

        if (rs.next()) {
            // ArrayList to check existing marks
            ArrayList<Integer> existingMarks = new ArrayList<>();
            existingMarks.add(rs.getInt("java"));
            existingMarks.add(rs.getInt("c"));
            existingMarks.add(rs.getInt("cpp"));
            existingMarks.add(rs.getInt("python"));

            // Check if any mark is greater than 0
            boolean hasMarks = false;
            for (Integer mark : existingMarks) {
                if (mark > 0) {
                    hasMarks = true;
                    break;
                }
            }

            if (hasMarks) {
                System.out.println(" Marks already added! Use 'Update Marks' option.");
                rs.close();
                checkPs.close();
                return;
            }
        }

        rs.close();
        checkPs.close();

        // ArrayList to store subject names
        ArrayList<String> subjects = new ArrayList<>(Arrays.asList("Java", "C", "C++", "Python"));

        // ArrayList to store marks
        ArrayList<Integer> marksList = new ArrayList<>();
        for (String subject : subjects) {
            marksList.add(getValidMarks(subject));
        }

        String sql = "UPDATE marks SET java=?, c=?, cpp=?, python=? WHERE roll=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        for (int i = 0; i < marksList.size(); i++) {
            ps.setInt(i + 1, marksList.get(i));
        }
        ps.setString(5, roll);
        ps.executeUpdate();
        ps.close();

        System.out.println(" Marks added successfully!");
    }

    private void updateMarks() throws SQLException {
        System.out.print("Enter Roll Number: ");
        String roll = sc.nextLine().trim();

        if (!studentExists(roll)) {
            System.out.println(" No student found with that roll number!");
            return;
        }

        PreparedStatement psShow = conn.prepareStatement("SELECT java, c, cpp, python FROM marks WHERE roll=?");
        psShow.setString(1, roll);
        ResultSet rs = psShow.executeQuery();

        // ArrayList to store subject names
        ArrayList<String> subjectNames = new ArrayList<>(Arrays.asList("Java", "C", "C++", "Python"));

        // ArrayList to store current marks
        ArrayList<Integer> currentMarks = new ArrayList<>();

        if (rs.next()) {
            currentMarks.add(rs.getInt("java"));
            currentMarks.add(rs.getInt("c"));
            currentMarks.add(rs.getInt("cpp"));
            currentMarks.add(rs.getInt("python"));

            System.out.println("\n Current Marks:");
            System.out.printf("Java: %d | C: %d | C++: %d | Python: %d\n",
                    currentMarks.get(0), currentMarks.get(1), currentMarks.get(2), currentMarks.get(3));
        } else {
            System.out.println(" No marks found for this roll number!");
            rs.close();
            psShow.close();
            return;
        }
        rs.close();
        psShow.close();

        // Update loop
        while (true) {
            System.out.print("\nEnter subject to update (Java/C/CPP/Python) or type 'Exit' to stop: ");
            String subject = sc.nextLine().trim().toLowerCase();

            if (subject.equals("exit")) {
                System.out.println(" Marks update completed!");
                break;
            }

            String column = switch (subject) {
                case "java" -> "java";
                case "c" -> "c";
                case "cpp" -> "cpp";
                case "python" -> "python";
                default -> null;
            };

            if (column == null) {
                System.out.println(" Invalid subject name! Try again.");
                continue;
            }

            int newMarks = getValidMarks(subject.substring(0, 1).toUpperCase() + subject.substring(1));
            String sql = "UPDATE marks SET " + column + "=? WHERE roll=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, newMarks);
            ps.setString(2, roll);
            ps.executeUpdate();
            ps.close();

            System.out.println(" " + subject.toUpperCase() + " marks updated successfully!");
        }
    }

    private boolean studentExists(String roll) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM student WHERE roll=?");
        ps.setString(1, roll);
        ResultSet rs = ps.executeQuery();
        rs.next();
        boolean exists = rs.getInt(1) > 0;
        rs.close();
        ps.close();
        return exists;
    }

    private int getValidMarks(String subject) {
        while (true) {
            try {
                System.out.print("Enter " + subject + " marks (0–100): ");
                int marks = Integer.parseInt(sc.nextLine());
                if (marks >= 0 && marks <= 100) return marks;
                System.out.println(" Marks must be between 0 and 100.");
            } catch (NumberFormatException e) {
                System.out.println(" Invalid input! Enter numbers only.");
            }
        }
    }

    private void viewAllStudents() throws SQLException {
        String sql = """
        SELECT s.roll, s.name, s.department, s.section,
               m.java, m.c, m.cpp, m.python
        FROM student s
        LEFT JOIN marks m ON s.roll = m.roll
        ORDER BY s.roll ASC
    """;

        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        // ArrayList to store all students
        ArrayList<Student> allStudents = new ArrayList<>();

        while (rs.next()) {
            Student student = new Student(
                    rs.getString("roll"),
                    rs.getString("name"),
                    rs.getString("department"),
                    rs.getString("section"),
                    rs.getInt("java"),
                    rs.getInt("c"),
                    rs.getInt("cpp"),
                    rs.getInt("python")
            );
            allStudents.add(student);
        }

        rs.close();
        ps.close();

        System.out.println("\n--- STUDENT MARKS LIST ---");
        System.out.printf("%-10s %-22s %-10s %-6s %-7s %-7s %-7s %-9s %-9s%n",
                "Roll", "Name", "Dept", "Sec", "Java", "C", "C++", "Python", "Avg(%)");
        System.out.println("------------------------------------------------------------------------------------");

        // Display from ArrayList
        for (Student student : allStudents) {
            System.out.printf("%-10s %-22s %-10s %-6s %-7d %-7d %-7d %-9d %-9.2f%n",
                    student.getRoll(), student.getName(), student.getDept(),
                    student.getSection(), student.getJavaMarks(), student.getCMarks(),
                    student.getCppMarks(), student.getPythonMarks(), student.percentage());
        }

        System.out.println("\n📊 Total students: " + allStudents.size());
    }

    private void passFailSummary() throws SQLException {
        String sql = """
        SELECT s.roll, s.name, s.department, s.section,
               m.java, m.c, m.cpp, m.python
        FROM student s
        JOIN marks m ON s.roll = m.roll
        ORDER BY s.roll ASC
    """;

        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        javax.swing.table.DefaultTableModel passModel = new javax.swing.table.DefaultTableModel(
                new String[]{"Roll No", "Name", "Department", "Section", "Java", "C", "C++", "Python", "Overall %"}, 0);
        javax.swing.table.DefaultTableModel failModel = new javax.swing.table.DefaultTableModel(
                new String[]{"Roll No", "Name", "Department", "Section", "Java", "C", "C++", "Python", "Overall %"}, 0);

        int passCount = 0, failCount = 0, total = 0;

        while (rs.next()) {
            String roll = rs.getString("roll");
            String name = rs.getString("name");
            String dept = rs.getString("department");
            String sec = rs.getString("section");
            int java = rs.getInt("java");
            int c = rs.getInt("c");
            int cpp = rs.getInt("cpp");
            int python = rs.getInt("python");

            double overall = (java + c + cpp + python) / 4.0;
            boolean passed = (java >= 50 && c >= 50 && cpp >= 50 && python >= 50);

            Object[] row = {roll, name, dept, sec, java, c, cpp, python, String.format("%.2f", overall)};
            if (passed) {
                passModel.addRow(row);
                passCount++;
            } else {
                failModel.addRow(row);
                failCount++;
            }
            total++;
        }

        rs.close();
        ps.close();

        // ---------- GUI DESIGN ----------
        javax.swing.JFrame frame = new javax.swing.JFrame("Pass/Fail Summary");
        frame.setLayout(new java.awt.BorderLayout());

        // Panel for pass/fail tables
        javax.swing.JPanel panel = new javax.swing.JPanel();
        panel.setLayout(new javax.swing.BoxLayout(panel, javax.swing.BoxLayout.Y_AXIS));

        // ---- Pass Students ----
        javax.swing.JLabel passLabel = new javax.swing.JLabel(
                " PASSED STUDENTS (" + passCount + " / " + total + ")", javax.swing.SwingConstants.CENTER);
        passLabel.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));
        javax.swing.JTable passTable = new javax.swing.JTable(passModel);
        passTable.setEnabled(false);
        javax.swing.JScrollPane passScroll = new javax.swing.JScrollPane(passTable);

        // ---- Fail Students ----
        javax.swing.JLabel failLabel = new javax.swing.JLabel(
                " FAILED STUDENTS (" + failCount + " / " + total + ")", javax.swing.SwingConstants.CENTER);
        failLabel.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));
        javax.swing.JTable failTable = new javax.swing.JTable(failModel);
        failTable.setEnabled(false);
        javax.swing.JScrollPane failScroll = new javax.swing.JScrollPane(failTable);

        // Add components to panel
        panel.add(passLabel);
        panel.add(passScroll);
        panel.add(javax.swing.Box.createVerticalStrut(20));
        panel.add(failLabel);
        panel.add(failScroll);

        // Add to frame
        frame.add(panel, java.awt.BorderLayout.CENTER);

        // Final window settings
        frame.setSize(900, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }


    private void filterByPercentageRange() throws SQLException {
        System.out.print("Enter minimum percentage: ");
        double min = sc.nextDouble();
        System.out.print("Enter maximum percentage: ");
        double max = sc.nextDouble();
        sc.nextLine(); // clear newline

        String sql = """
        SELECT s.roll, s.name, s.department, s.section, m.java, m.c, m.cpp, m.python
        FROM student s
        JOIN marks m ON s.roll = m.roll
    """;

        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        // ArrayList to store filtered students
        ArrayList<Student> filteredList = new ArrayList<>();

        while (rs.next()) {
            Student student = new Student(
                    rs.getString("roll"),
                    rs.getString("name"),
                    rs.getString("department"),
                    rs.getString("section"),
                    rs.getInt("java"),
                    rs.getInt("c"),
                    rs.getInt("cpp"),
                    rs.getInt("python")
            );

            if (student.percentage() >= min && student.percentage() <= max) {
                filteredList.add(student);
            }
        }

        rs.close();
        ps.close();

        // Sort ArrayList by percentage
        filteredList.sort(Comparator.comparingDouble(Student::percentage));

        System.out.println("\n --- STUDENTS WITH PERCENTAGE BETWEEN " + min + " AND " + max + " ---");
        if (filteredList.isEmpty()) {
            System.out.println(" No students found.");
            return;
        }

        for (Student s : filteredList) {
            System.out.printf("%-10s %-15s | %.2f%% | %s\n",
                    s.getRoll(), s.getName(), s.percentage(),
                    s.isPassed() ? " Pass" : " Fail");
        }
    }

    private void filterBySubjectMarksRange() throws SQLException {
        System.out.print("Enter subject (Java/C/CPP/Python): ");
        String subjectInput = sc.next().trim().toLowerCase();

        String column;
        switch (subjectInput) {
            case "java" -> column = "m.java";
            case "c" -> column = "m.c";
            case "cpp" -> column = "m.cpp";
            case "python" -> column = "m.python";
            default -> {
                System.out.println(" Invalid subject!");
                sc.nextLine();
                return;
            }
        }

        System.out.print("Enter minimum marks (0–100): ");
        int min = sc.nextInt();
        System.out.print("Enter maximum marks (0–100): ");
        int max = sc.nextInt();
        sc.nextLine(); // clear buffer

        String sql = "SELECT s.roll, s.name, s.department, s.section, m.java, m.c, m.cpp, m.python " +
                "FROM student s JOIN marks m ON s.roll=m.roll " +
                "WHERE " + column + " BETWEEN ? AND ? ORDER BY " + column + " ASC";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, min);
        ps.setInt(2, max);
        ResultSet rs = ps.executeQuery();

        // ArrayList to store filtered students
        ArrayList<Student> filteredList = new ArrayList<>();

        while (rs.next()) {
            Student student = new Student(
                    rs.getString("roll"),
                    rs.getString("name"),
                    rs.getString("department"),
                    rs.getString("section"),
                    rs.getInt("java"),
                    rs.getInt("c"),
                    rs.getInt("cpp"),
                    rs.getInt("python")
            );
            filteredList.add(student);
        }

        rs.close();
        ps.close();

        System.out.println("\n --- STUDENTS WITH " + subjectInput.toUpperCase()
                + " MARKS BETWEEN " + min + " AND " + max + " ---");

        if (filteredList.isEmpty()) {
            System.out.println(" No students found.");
        } else {
            for (Student s : filteredList) {
                int subjectMarks = switch (subjectInput) {
                    case "java" -> s.getJavaMarks();
                    case "c" -> s.getCMarks();
                    case "cpp" -> s.getCppMarks();
                    case "python" -> s.getPythonMarks();
                    default -> 0;
                };

                System.out.printf("%-10s %-15s | %-10s: %3d | %.2f%% | %s\n",
                        s.getRoll(), s.getName(), subjectInput.toUpperCase(),
                        subjectMarks, s.percentage(),
                        s.isPassed() ? " Pass" : " Fail");
            }
        }
    }
}