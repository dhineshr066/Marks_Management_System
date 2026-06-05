import java.sql.*;
import java.util.ArrayList;

import marks.system.Student;

public class StudentActions {

    public static void viewStudent(Connection conn, String roll) {
        try {
            String sql = """
                    SELECT s.roll, s.name, s.department, s.section, 
                           m.java, m.c, m.cpp, m.python
                    FROM student s 
                    LEFT JOIN marks m ON s.roll = m.roll 
                    WHERE s.roll = ?
                    """;

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, roll);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                System.out.println(" Student not found.");
                return;
            }

            String name = rs.getString("name");
            String dept = rs.getString("department");
            String section = rs.getString("section");

            int java = rs.getInt("java");
            int c = rs.getInt("c");
            int cpp = rs.getInt("cpp");
            int python = rs.getInt("python");

            // If marks not added yet (all 0)
            if (java == 0 && c == 0 && cpp == 0 && python == 0) {
                System.out.println("\nStudent: " + name + " (" + roll + ")");
                System.out.println(" Department: " + dept);
                System.out.println("Section: " + section);
                System.out.println("  Marks not yet added by teacher.");
                return;
            }

            // Store subject marks in ArrayList for better organization
            ArrayList<SubjectMarks> subjectList = new ArrayList<>();
            subjectList.add(new SubjectMarks("Java", java));
            subjectList.add(new SubjectMarks("C", c));
            subjectList.add(new SubjectMarks("C++", cpp));
            subjectList.add(new SubjectMarks("Python", python));

            int total = java + c + cpp + python;
            double perc = total / 4.0;

            System.out.println("\n---  Student Marks Summary ---");
            System.out.println("Name      : " + name);
            System.out.println("Roll No   : " + roll);
            System.out.println("Dept: " + dept);
            System.out.println("Section: " + section);
            System.out.println("---------------------------------------------");
            System.out.printf("%-10s %-10s %-10s\n", "Subject", "Marks", "Status");
            System.out.println("---------------------------------------------");

            // Display subjects from ArrayList
            for (SubjectMarks subject : subjectList) {
                System.out.printf("%-10s %-10d %-10s\n",
                        subject.name,
                        subject.marks,
                        (subject.marks >= 50 ? " Pass" : " Fail"));
            }

            System.out.println("---------------------------------------------");
            System.out.println("Total      : " + total);
            System.out.printf("Percentage : %.2f%%\n", perc);
            System.out.println("Grade      : " + getGrade(perc));

            // Overall pass/fail
            if (java >= 50 && c >= 50 && cpp >= 50 && python >= 50)
                System.out.println(" Overall Result:  PASSED");
            else
                System.out.println("⚠  Overall Result:  FAILED");

            rs.close();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Helper class to store subject marks in ArrayList
    private static class SubjectMarks {
        String name;
        int marks;

        SubjectMarks(String name, int marks) {
            this.name = name;
            this.marks = marks;
        }
    }

    private static String getGrade(double p) {
        if (p >= 90) return "A+";
        else if (p >= 80) return "A";
        else if (p >= 70) return "B";
        else if (p >= 60) return "C";
        else if (p >= 50) return "D";
        else return "F";
    }
}