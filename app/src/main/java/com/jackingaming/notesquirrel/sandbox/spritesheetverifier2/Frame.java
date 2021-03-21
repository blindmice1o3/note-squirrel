package com.jackingaming.notesquirrel.sandbox.spritesheetverifier2;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Frame
        implements Serializable {
    private int indexColumn;
    private int indexRow;
    transient private Bitmap imageUserSelected;

    public Frame(int indexColumn, int indexRow, Bitmap imageUserSelected) {
        this.indexColumn = indexColumn;
        this.indexRow = indexRow;
        this.imageUserSelected = imageUserSelected;
    }

    public int getIndexColumn() {
        return indexColumn;
    }

    public void setIndexColumn(int indexColumn) {
        this.indexColumn = indexColumn;
    }

    public int getIndexRow() {
        return indexRow;
    }

    public void setIndexRow(int indexRow) {
        this.indexRow = indexRow;
    }

    public Bitmap getImageUserSelected() {
        return imageUserSelected;
    }

    public void setImageUserSelected(Bitmap imageUserSelected) {
        this.imageUserSelected = imageUserSelected;
    }
}