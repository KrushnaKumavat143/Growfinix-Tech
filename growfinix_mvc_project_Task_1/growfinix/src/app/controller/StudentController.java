package app.controller;

import app.model.Student;
import app.model.Result;

import java.io.*;
import java.util.*;

public class StudentController {
    private List<Student> students = new ArrayList<>();
    private final File storeFile = new File("students.dat");

    public StudentController() {
        loadFromFile();
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }

    public Student addStudent(String name, int age, Map<String, Integer> marks) {
        Result r = new Result(marks);
        Student s = new Student(name, age, r);
        students.add(s);
        saveToFile();
        return s;
    }

    public boolean updateStudent(int id, String name, Integer age, Map<String, Integer> marks) {
        Student s = findById(id);
        if (s == null) return false;
        if (name != null) s.setName(name);
        if (age != null) s.setAge(age);
        if (marks != null) s.setResult(new Result(marks));
        saveToFile();
        return true;
    }

    public boolean deleteStudent(int id) {
        Student s = findById(id);
        if (s == null) return false;
        students.remove(s);
        saveToFile();
        return true;
    }

    public Student findById(int id) {
        for (Student s : students) if (s.getId() == id) return s;
        return null;
    }

    @SuppressWarnings("unchecked")
    private void loadFromFile() {
        if (!storeFile.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storeFile))) {
            Object obj = ois.readObject();
            if (obj instanceof List) {
                students = (List<Student>) obj;
            }
        } catch (Exception e) {
            System.err.println("Failed to load students: " + e.getMessage());
        }
    }

    private void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storeFile))) {
            oos.writeObject(students);
        } catch (IOException e) {
            System.err.println("Failed to save students: " + e.getMessage());
        }
    }
}
