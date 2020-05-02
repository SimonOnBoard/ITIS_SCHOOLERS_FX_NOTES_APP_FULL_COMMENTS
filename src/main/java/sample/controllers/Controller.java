package sample.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.Main;
import sample.models.Note;
import sample.models.SearchParametr;
import sample.services.SearchPointInitializer;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    public Pane pane;
    @FXML
    public Button addButton;
    @FXML
    public ListView<Note> listView;
    @FXML
    public TextField searchField;

    @FXML
    public ChoiceBox<SearchParametr> searchPoint;

    @FXML
    public ChoiceBox<String> categories;

    @FXML
    public CheckBox useCategoriesFilter;

    public ObservableList<Note> notes = FXCollections.observableArrayList();

    public ArrayList<Note> deletedNotes = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //ObservableList<Note> noteList = Main.loadNotes();
        //listView.setCellFactory(listView -> new NoteView());
        ObservableList<SearchParametr> parametrs = SearchPointInitializer.getSearchParametrs();
        searchPoint.getItems().addAll(parametrs);
        searchPoint.setValue(parametrs.get(0));
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        Main.mainScenecontroller = this;
        addButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Parent root = null;
                Stage stage = new Stage();
                try {
                    root = FXMLLoader.load(getClass().getResource("/sample/scenes/newNote.fxml"));
                    stage.setTitle("Добавить заметку");
                    stage.setScene(new Scene(root, 800, 650));
                    stage.show();
                } catch (IOException e) {
                    throw new IllegalStateException(e);
                }
            }
        });
        searchPoint.getSelectionModel()
                .selectedItemProperty()
                .addListener(new ChangeListener<SearchParametr>() {
                    @Override
                    public void changed(ObservableValue<? extends SearchParametr> observable, SearchParametr oldValue, SearchParametr newValue) {
                        getFilteredNotes(searchField.getText(), newValue, useCategoriesFilter.selectedProperty().get());
                    }
                });
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("")) {
                setListView(notes);
            } else {
                getFilteredNotes(newValue, searchPoint.getValue(), useCategoriesFilter.selectedProperty().get());
            }
        });
        useCategoriesFilter.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                getFilteredNotes(searchField.getText(), searchPoint.getValue(), newValue);
            }
        });
        categories.getSelectionModel()
                .selectedItemProperty()
                .addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        getFilteredNotes(searchField.getText(), searchPoint.getValue(), useCategoriesFilter.selectedProperty().get());
                    }
                });
    }

    private void getFilteredNotes(String text, SearchParametr newValue, boolean b) {
        ObservableList<Note> filtered = getCurrentNotes(text, newValue, b);
        setListView(filtered);
    }

    private void setListView(ObservableList<Note> filtered) {
        listView.setItems(filtered);
    }

    private ObservableList<Note> getCurrentNotes(String text, SearchParametr value, boolean b) {
        ObservableList<Note> result = FXCollections.observableArrayList();
        List<Note> preparedResult = new ArrayList<>();
        System.out.println(value.getCode());
        switch (value.getCode()) {
            case 1:
                for (Note note : notes) {
                    if (note.getName().contains(text)) {
                        preparedResult.add(note);
                    }
                }
                break;
            case 2:
                for (Note note : notes) {
                    if (note.getText().contains(text)) {
                        preparedResult.add(note);
                    }
                }
                break;
            case 3:
                for (Note note : notes) {
                    if (note.getName().contains(text) | note.getText().contains(text)) {
                        preparedResult.add(note);
                    }
                }
                break;
        }
        if (b) {
            String category = categories.getValue();
            for (Note note : preparedResult) {
                if (note.getCategory().equals(category)) {
                    result.add(note);
                }
            }
        } else {
            result.addAll(preparedResult);
        }
        return result;
    }

    @FXML
    public void handleMouseClick(MouseEvent mouseEvent) {
        Note note = listView.getSelectionModel().getSelectedItem();
        if (note != null) {
            Parent root = null;
            Stage stage = new Stage();
            FXMLLoader loader;
            try {
                loader = new FXMLLoader(getClass().getResource("/sample/scenes/showNote.fxml"));
                root = loader.load();
                ShowNoteController controller = loader.getController();
                controller.setData(note);
                stage.setTitle("Заметка");
                stage.setScene(new Scene(root, 900, 700));
                stage.show();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    public void setData(List<Note> noteList) {
        for(Note note: noteList){
            if(note.isDeleted()){
                deletedNotes.add(note);
            }
            else{
                notes.add(note);
            }
        }
        listView.setItems(notes);
        categories.setItems(Main.categories);
        categories.setValue(categories.getItems().get(0));
    }
}
