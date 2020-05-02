package sample.controllers;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.Main;
import sample.models.Note;
import sample.services.FileWorker;

import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.Optional;
import java.util.ResourceBundle;


public class ShowNoteController implements Initializable {
    @FXML
    public Label name;
    @FXML
    public Label category;
    @FXML
    public Label text;
    @FXML
    public Button editButton;
    @FXML
    public Button closeButton;
    @FXML
    public Button deleteButton;

    public Note note;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        closeButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Stage stage = (Stage) closeButton.getScene().getWindow();
                stage.close();
            }
        });
        deleteButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                note.setDeleted(true);
                Main.mainScenecontroller.listView.getItems().remove(note);
                Main.mainScenecontroller.notes.remove(note);
                Main.mainScenecontroller.deletedNotes.add(note);
                closeButton.getOnMouseClicked().handle(event);
            }
        });
        editButton.setOnMouseClicked(event -> {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/scenes/editingPage.fxml"));
            VBox content = null;
            try {
                content = loader.load();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
            EditingModelController controller = loader.getController();
            controller.setData(note);
            alert.getDialogPane().setContent(content);
            alert.setTitle("Изменение заметки");
            alert.setHeaderText("Форма для изменения");

            Optional<ButtonType> result = alert.showAndWait();
            if (!result.isPresent()) {
                throw new IllegalStateException("Пользователь просто закрыл");
            } else if (result.get() == ButtonType.OK) {
                controller.onClose();
                this.setData();
                //Доделать добавление в изначальный лист
                //Main.mainScenecontroller.listView.getItems().remove(null);
                Main.mainScenecontroller.listView.getItems().sort(new Comparator<Note>() {
                    @Override
                    public int compare(Note o1, Note o2) {
                        return o1.getText().length() - o2.getText().length();
                    }
                });
            }
        });
    }

    private void setData() {
        name.setText(note.getName());
        category.setText(note.getCategory());
        text.setText(note.getText());
    }

    public void setData(Note note1) {
        note = note1;
        name.setText(note.getName());
        category.setText(note.getCategory());
        text.setWrapText(true);
        text.setText(note.getText());
    }
}
