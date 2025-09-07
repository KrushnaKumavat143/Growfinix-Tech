package app.view;

import app.controller.StudentController;
import app.model.Student;

import java.util.*;

public class StudentView {
    private Scanner scanner = new Scanner(System.in);
    private StudentController controller;

    public StudentView(StudentController controller) {
        this.controller = controller;
    }

    public void start() {
        while (true) {
            showMenu();
            int choice = readInt("Enter choice: ");
            switch (choice) {
                case 1: addStudent(); break;
                case 2: viewAll(); break;
                case 3: viewStudent(); break;
                case 4: updateStudent(); break;
                case 5: deleteStudent(); break;
                case 6: System.out.println("Exiting..."); return;
                default: System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void showMenu() {
        System.out.println("\n=== Growfinix Student Management ===");
        System.out.println("1. Add Student");
        System.out.println("2. View All Students");
        System.out.println("3. View Student By ID");
        System.out.println("4. Update Student");
        System.out.println("5. Delete Student");
        System.out.println("6. Exit");
    }

    private void addStudent() {
        System.out.println("\n--- Add Student ---");
        String name = readString("Name: ");
        int age = readInt("Age: ");
        Map<String, Integer> marks = readMarks();
        Student s = controller.addStudent(name, age, marks);
        System.out.println("Added: " + s);
    }

    private void viewAll() {
        System.out.println("\n--- All Students ---");
        List<Student> list = controller.getAllStudents();
        if (list.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        for (Student s : list) {
            System.out.println(s);
        }
    }

    private void viewStudent() {
        int id = readInt("Enter student id: ");
        Student s = controller.findById(id);
        if (s == null) System.out.println("Not found.");
        else System.out.println(s);
    }

    private void updateStudent() {
        int id = readInt("Enter student id to update: ");
        Student s = controller.findById(id);
        if (s == null) {
            System.out.println("Student not found.");
            return;
        }
        System.out.println("Current: " + s);
        String name = readStringAllowEmpty("New name (leave blank to keep): ");
        Integer age = readIntAllowEmpty("New age (enter 0 or leave blank to keep): ");
        System.out.print("Do you want to update marks? (y/n): ");
        String yes = scanner.nextLine().trim().toLowerCase();
        Map<String, Integer> marks = null;
        if ("y".equals(yes) || "yes".equals(yes)) {
            marks = readMarks();
        }
        boolean ok = controller.updateStudent(id, name.isEmpty() ? null : name,
                (age == null || age == 0) ? null : age, marks);
        System.out.println(ok ? "Updated." : "Update failed.");
    }

    private void deleteStudent() {
        int id = readInt("Enter student id to delete: ");
        boolean ok = controller.deleteStudent(id);
        System.out.println(ok ? "Deleted." : "Not found.");
    }

    private String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private String readStringAllowEmpty(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String line = scanner.nextLine().trim();
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    private Integer readIntAllowEmpty(String prompt) {
        try {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) return null;
            int v = Integer.parseInt(line);
            return v;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Map<String, Integer> readMarks() {
        Map<String, Integer> marks = new LinkedHashMap<>();
        System.out.println("Enter subjects and marks. Type DONE when finished.");
        while (true) {
            System.out.print("Subject name (or DONE): ");
            String sub = scanner.nextLine().trim();
            if (sub.equalsIgnoreCase("DONE")) break;
            if (sub.isEmpty()) {
                System.out.println("Subject name cannot be empty.");
                continue;
            }
            int mark = -1;
            while (mark < 0 || mark > 100) {
                try {
                    System.out.print("Mark for " + sub + " (0-100): ");
                    String line = scanner.nextLine().trim();
                    mark = Integer.parseInt(line);
                    if (mark < 0 || mark > 100) System.out.println("Mark must be between 0 and 100.");
                } catch (NumberFormatException e) {
                    System.out.println("Enter a valid integer.");
                }
            }
            marks.put(sub, mark);
        }
        return marks;
    }
}
