package sample.models;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Callback;

public class Note {
    public String name;
    public String text;
    public String category;
    public Long id;
    public boolean deleted;

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        String text = "";
        return text + this.name + ": " + this.text + "\n category: " + this.category;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Note(String name, String text, String category) {
        this.name = name;
        this.text = text;
        this.category = category;
    }

    public Note(String name, String text, String category, Long id) {
        this.name = name;
        this.text = text;
        this.category = category;
        this.id = id;
    }

    public Note(String name, String text, String category, Long id, boolean deleted) {
        this.name = name;
        this.text = text;
        this.category = category;
        this.id = id;
        this.deleted = deleted;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
