package sample.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.models.SearchParametr;

public class SearchPointInitializer {


    public static ObservableList<SearchParametr> getSearchParametrs(){
        SearchParametr parametr = new SearchParametr(1,"Поиск в названии");
        SearchParametr parametr1 = new SearchParametr(2,"Поиск в тексте");
        SearchParametr parametr2 = new SearchParametr(3, "Поиск в названии и тексте");
        ObservableList<SearchParametr> parametrs = FXCollections.observableArrayList();
        parametrs.addAll(parametr,parametr1,parametr2);
        return parametrs;
    }
}
