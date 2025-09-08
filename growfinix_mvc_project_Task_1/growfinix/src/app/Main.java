package app;

import app.controller.StudentController;
import app.view.StudentView;

public class Main {
    public static void main(String[] args) {
        StudentController controller = new StudentController();
        StudentView view = new StudentView(controller);
        view.start();
    }
}
