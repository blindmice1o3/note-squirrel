package com.jackingaming.notesquirrel.sandbox.dvdlibrary.official.datasource;

public class Dvd {
    private int id;
    private String title;
    private boolean available;

    public Dvd() {}
    public Dvd(String title, boolean available) {
        this.title = title;
        this.available = available;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Dvd{" +
                "title='" + title + '\'' +
                '}';
    }
}
