# JavaFX Contact Book

Simple JavaFX Contact Book (Task 2) — Add / Edit / Delete contacts with validation.

## Files
- `src/Main.java` - Application entry (loads FXML)
- `src/Contact.java` - Model with JavaFX properties
- `src/ContactController.java` - Controller: handles UI logic
- `resources/ContactView.fxml` - FXML layout
- `styles.css` - Optional CSS

## Requirements
- Java 11+
- JavaFX SDK (17+ recommended)

## Run (example)
1. Compile:
   ```bash
   javac --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml -d out src/*.java
   ```
2. Run:
   ```bash
   java --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml -cp out Main
   ```

If you use an IDE (IntelliJ/Eclipse), create a Java project, add JavaFX library, and set VM options:
`--module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml`
