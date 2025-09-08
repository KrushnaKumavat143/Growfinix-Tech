package com.example;

import com.example.dao.StudentDao;
import com.example.model.Student;
import com.example.util.HibernateUtil;

import java.util.List;
import java.util.Scanner;

public class StudentManagementApp {
    private static StudentDao studentDao = new StudentDao();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            printMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    viewStudentById();
                    break;
                case 3:
                    viewAllStudents();
                    break;
                case 4:
                    updateStudent();
                    break;
                case 5:
                    deleteStudent();
                    break;
                case 6:
                    System.out.println("Exiting application...");
                    HibernateUtil.shutdown(); // Close SessionFactory
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n--- Student Management System ---");
        System.out.println("1. Add a new Student");
        System.out.println("2. View Student by ID");
        System.out.println("3. View All Students");
        System.out.println("4. Update a Student");
        System.out.println("5. Delete a Student");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void addStudent() {
        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        Student newStudent = new Student(firstName, lastName, email);
        studentDao.saveStudent(newStudent);
        System.out.println("Student added successfully!");
    }

    private static void viewStudentById() {
        System.out.print("Enter Student ID to view: ");
        int id = scanner.nextInt();
        Student student = studentDao.getStudentById(id);
        if (student != null) {
            System.out.println("Student Details: " + student);
        } else {
            System.out.println("Student with ID " + id + " not found.");
        }
    }

    private static void viewAllStudents() {
        List<Student> students = studentDao.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("No students found in the database.");
        } else {
            System.out.println("--- List of All Students ---");
            students.forEach(System.out::println);
        }
    }

    private static void updateStudent() {
        System.out.print("Enter Student ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Student student = studentDao.getStudentById(id);
        if (student != null) {
            System.out.print("Enter new First Name: ");
            student.setFirstName(scanner.nextLine());
            System.out.print("Enter new Last Name: ");
            student.setLastName(scanner.nextLine());
            System.out.print("Enter new Email: ");
            student.setEmail(scanner.nextLine());

            studentDao.updateStudent(student);
            System.out.println("Student updated successfully!");
        } else {
            System.out.println("Student with ID " + id + " not found.");
        }
    }

    private static void deleteStudent() {
        System.out.print("Enter Student ID to delete: ");
        int id = scanner.nextInt();
        
        if (studentDao.deleteStudent(id)) {
            System.out.println("Student with ID " + id + " deleted successfully.");
        } else {
            System.out.println("Student with ID " + id + " not found or could not be deleted.");
        }
    }
}