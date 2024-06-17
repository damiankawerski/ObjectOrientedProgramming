package org.example.k2_client;

import java.time.LocalTime;

public class Word {
    private LocalTime time;
    private String word;

    public Word(LocalTime time, String word) {
        this.time = time;
        this.word = word;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getWord() {
        return word;
    }
}
