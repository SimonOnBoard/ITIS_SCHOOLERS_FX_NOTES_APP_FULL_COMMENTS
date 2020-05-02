package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.models.Note;

import java.net.URL;
import java.util.ResourceBundle;

public class EditingModelController implements Initializable {
    @FXML
    public TextField name;
    @FXML
    public TextField category;
    @FXML
    public TextArea text;

    public Note note;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    public void setData(Note note) {
        this.note = note;
        name.setText(note.getName());
        category.setText(note.getCategory());
        text.setText(note.getText());
    }

    public void onClose() {
        note.setName(name.getText());
        note.setCategory(category.getText());
        note.setText(text.getText());
    }
}
