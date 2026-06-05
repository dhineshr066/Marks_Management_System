import java.sql.Connection;
import java.util.Scanner;
import marks.system.ThreadManager;

import marks.system.Teacher;

public class Main {
    public static void main(String[] args) {
        try (Connection conn = DBConnect.getConnection()) {
            // Start background autosave thread (marks.system)
            ThreadManager tm = new ThreadManager(conn);
            tm.startBackground();
            Scanner sc = new Scanner(System.in);
            Teacher teacher = new Teacher(conn);

            while (true) {
                System.out.println("\n=== Marks Management System ===");
                System.out.println("1) Teacher Login");
                System.out.println("2) Student Login");
                System.out.println("3) Exit");
                System.out.print("Enter choice: ");
                int ch = Integer.parseInt(sc.nextLine());

                if (ch == 1) {
                    System.out.print("Username: ");
                    String u = sc.nextLine();
                    System.out.print("Password: ");
                    String p = sc.nextLine();
                    if (teacher.authenticate(u, p)) teacher.menu();
                    else System.out.println("Invalid login!");
                } else if (ch == 2) {
                    System.out.print("Enter Roll Number: ");
                    String roll = sc.nextLine();
                    StudentActions.viewStudent(conn, roll);
                } else if (ch == 3) break;
                else System.out.println("Invalid choice!");
            }
            // Stop background thread before exiting
            try { tm.stopBackground(); } catch (Exception ignore) {}
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}