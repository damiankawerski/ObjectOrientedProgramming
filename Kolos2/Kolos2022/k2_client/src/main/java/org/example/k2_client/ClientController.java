package org.example.k2_client;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ClientController {

    public Label wordCountLabel;
    public ListView wordList;
    public TextField filterField;

    public ClientController() {
        ClientReceiver.controller = this;
    }

    List<Word> listOfWords = new ArrayList<>();

    public void onWordReceived(String word) {
        listOfWords.add(new Word(LocalTime.now(), word));
        Platform.runLater(() -> {
            wordCountLabel.setText(Integer.toString(listOfWords.size()));
            update();
        });
    }

    public void update() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss ");
        wordList.setItems(FXCollections.observableArrayList(listOfWords.stream()
                        .filter((word) -> word.getWord().startsWith(filterField.getText()))
                        .sorted(Comparator.comparing(word -> word.getWord()))
                        .map((item) -> item.getTime().format(formatter) + item.getWord())
                        .toList()
        ));
    }

    public void onEnter() {
        Platform.runLater(this::update);
    }
}