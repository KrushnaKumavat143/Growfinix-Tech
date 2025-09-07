import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.*;
import java.nio.file.Files;
import java.util.List;
import java.util.regex.Pattern;

public class ContactController {
    @FXML private TextField nameField;
    @FXML private TextField phoneField;
    @FXML private TextField emailField;
    @FXML private Button addBtn;
    @FXML private Button updateBtn;
    @FXML private Button deleteBtn;
    @FXML private Button saveBtn;
    @FXML private Button loadBtn;
    @FXML private TableView<Contact> table;
    @FXML private TableColumn<Contact, String> nameCol;
    @FXML private TableColumn<Contact, String> phoneCol;
    @FXML private TableColumn<Contact, String> emailCol;
    @FXML private Label statusLabel;

    private final ObservableList<Contact> contacts = FXCollections.observableArrayList();
    private final Pattern emailPattern = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private final Pattern phonePattern = Pattern.compile("^[0-9]{7,15}$");

    @FXML
    public void initialize() {
        nameCol.setCellValueFactory(cell -> cell.getValue().nameProperty());
        phoneCol.setCellValueFactory(cell -> cell.getValue().phoneProperty());
        emailCol.setCellValueFactory(cell -> cell.getValue().emailProperty());
        table.setItems(contacts);

        // Handle row selection
        table.setOnMouseClicked((MouseEvent event) -> onRowSelected());
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> onRowSelected());

        updateBtn.setDisable(true);
        deleteBtn.setDisable(true);
    }

    private void onRowSelected() {
        Contact c = table.getSelectionModel().getSelectedItem();
        if (c != null) {
            nameField.setText(c.getName());
            phoneField.setText(c.getPhone());
            emailField.setText(c.getEmail());
            statusLabel.setText("Selected: " + c.getName());

            // Enable buttons when a contact is selected
            updateBtn.setDisable(false);
            deleteBtn.setDisable(false);
        }
    }

    @FXML
    private void onAdd() {
        clearStatus();
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();
        if (!validate(name, phone, email)) return;
        Contact c = new Contact(name, phone, email);
        contacts.add(c);
        clearForm();
        statusLabel.setText("Contact added.");
    }

    @FXML
    private void onUpdate() {
        clearStatus();
        Contact selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) { statusLabel.setText("No contact selected."); return; }
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();
        if (!validate(name, phone, email)) return;
        selected.setName(name);
        selected.setPhone(phone);
        selected.setEmail(email);
        table.refresh();
        clearForm();
        statusLabel.setText("Contact updated.");
    }

    @FXML
    private void onDelete() {
        clearStatus();
        Contact selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) { statusLabel.setText("No contact selected."); return; }
        contacts.remove(selected);
        clearForm();
        statusLabel.setText("Contact deleted.");
    }

    @FXML
    private void onSave() {
        clearStatus();
        FileChooser chooser = new FileChooser();
        chooser.setInitialFileName("contacts.csv");
        File f = chooser.showSaveDialog(table.getScene().getWindow());
        if (f == null) return;
        try (PrintWriter pw = new PrintWriter(f)) {
            for (Contact c : contacts) {
                pw.println(escape(c.getName()) + "," + escape(c.getPhone()) + "," + escape(c.getEmail()));
            }
            statusLabel.setText("Saved " + contacts.size() + " contacts to " + f.getName());
        } catch (Exception ex) {
            statusLabel.setText("Save failed: " + ex.getMessage());
        }
    }

    @FXML
    private void onLoad() {
        clearStatus();
        FileChooser chooser = new FileChooser();
        File f = chooser.showOpenDialog(table.getScene().getWindow());
        if (f == null) return;
        try {
            List<String> lines = Files.readAllLines(f.toPath());
            contacts.clear();
            for (String line : lines) {
                String[] parts = line.split(",", -1);
                if (parts.length >= 3) {
                    contacts.add(new Contact(unescape(parts[0]), unescape(parts[1]), unescape(parts[2])));
                }
            }
            statusLabel.setText("Loaded " + contacts.size() + " contacts from " + f.getName());
        } catch (IOException ex) {
            statusLabel.setText("Load failed: " + ex.getMessage());
        }
    }

    private boolean validate(String name, String phone, String email) {
        if (name.isEmpty()) { statusLabel.setText("Name cannot be empty."); return false; }
        if (!phonePattern.matcher(phone).matches()) { statusLabel.setText("Phone must be 7-15 digits."); return false; }
        if (!emailPattern.matcher(email).matches()) { statusLabel.setText("Email is invalid."); return false; }
        return true;
    }

    private void clearForm() {
        nameField.clear();
        phoneField.clear();
        emailField.clear();
        table.getSelectionModel().clearSelection();
        updateBtn.setDisable(true);
        deleteBtn.setDisable(true);
    }

    private void clearStatus() {
        statusLabel.setText("");
    }

    private String escape(String s) { return s.replace("\\", "\\\\").replace(",", "\\,"); }
    private String unescape(String s) { return s.replace("\\,", ",").replace("\\\\", "\\"); }
}
