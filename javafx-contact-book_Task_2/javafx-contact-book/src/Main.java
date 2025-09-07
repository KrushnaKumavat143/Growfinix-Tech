

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Use absolute path to FXML
        File fxmlFile = new File("D:/New folder/intership/javafx-contact-book_Task_2/javafx-contact-book/src/ContactView.fxml");
        FXMLLoader loader = new FXMLLoader(fxmlFile.toURI().toURL());

        Parent root = loader.load();

        primaryStage.setTitle("Contact Book - Growfinix (Task 2)");
        Scene scene = new Scene(root, 700, 450);

        // Use absolute path for CSS
        File cssFile = new File("D:/New folder/intership/javafx-contact-book/javafx-contact-book/src/styles.css");
        scene.getStylesheets().add(cssFile.toURI().toURL().toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
