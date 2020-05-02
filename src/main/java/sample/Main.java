package sample;


import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.controllers.Controller;
import sample.models.Note;
import sample.services.FileWorker;
import sample.services.IdFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main extends Application {

    public static Controller mainScenecontroller;
    public static List<Integer> lengths = new ArrayList<>();
    public static ObservableList<String> categories = FXCollections.observableArrayList();

    @Override
    public void stop(){
        System.out.println("Stage is closing");
        FileWorker.writeAll(mainScenecontroller.notes, mainScenecontroller.deletedNotes);
    }
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = null;
        Stage stage = new Stage();
        FXMLLoader loader;
        try {
            loader = new FXMLLoader(getClass().getResource("../sample.fxml"));
            root = loader.load();
            Controller controller = loader.getController();
            controller.setData(Main.loadNotes());
            stage.setTitle("Мои заметки");
            stage.setScene(new Scene(root, 900, 700));
            stage.show();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static List<Note> loadNotes() {
        List<Note> notes = new ArrayList<>();
        File file = new File("data.txt");
        if(!file.exists()){
            file = new File("data.txt");
        }
        Scanner scanner;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new IllegalStateException(e);
        }
        String s;
        String[] strings;
        Note note;
        while(scanner.hasNextLine()){
            s = scanner.nextLine();
            lengths.add(s.length());
            strings = s.split(" ");
            String text = getCurrentText(strings[2]);
            Long id = Long.parseLong(strings[0]);
            checkLastId(id);
            categories.add(strings[3]);
            boolean deleted = Boolean.valueOf(strings[4]);
            note = new Note(strings[1],text,strings[3], id, deleted);
            notes.add(note);
        }
        return notes;
    }

    private static void checkLastId(Long id) {
        if(id > IdFactory.currentId){
            IdFactory.setCurrentId(id);
        }
    }

    private static String getCurrentText(String text) {
        String a = "";
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '\t') {
                a += "\n";
            } else if (text.charAt(i) == '\f') {
                a += " ";
            } else {
                a += text.charAt(i);
            }
        }
        return a;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
