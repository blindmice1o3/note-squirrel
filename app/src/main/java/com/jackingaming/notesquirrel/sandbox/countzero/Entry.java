package com.jackingaming.notesquirrel.sandbox.countzero;

public class Entry {

    private String date;
    private boolean isActive;

    public Entry(String date, boolean isActive) {
        this.date = date;
        this.isActive = isActive;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

}