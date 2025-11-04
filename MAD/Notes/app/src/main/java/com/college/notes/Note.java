package com.college.notes;

public class Note {
    private final String title;
    private final String date;
    private final String snippet;

    public Note(String title, String date, String snippet) {
        this.title = title;
        this.date = date;
        this.snippet = snippet;
    }

    public String getTitle() { return title; }
    public String getDate() { return date; }
    public String getSnippet() { return snippet; }
}