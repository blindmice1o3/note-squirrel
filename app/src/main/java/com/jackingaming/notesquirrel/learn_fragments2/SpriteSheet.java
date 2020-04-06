package com.jackingaming.notesquirrel.learn_fragments2;

import androidx.annotation.NonNull;

public class SpriteSheet {

    private int imageId;
    private int widthInPx;
    private int heightInPx;
    private String dimension;
    private String title;
    private String description;

    public SpriteSheet(int imageId, int widthInPx, int heightInPx, String title, String description) {
        this.imageId = imageId;
        this.widthInPx = widthInPx;
        this.heightInPx = heightInPx;
        dimension = widthInPx + "x" + heightInPx;
        this.title = title;
        this.description = description;
    }

    @NonNull
    @Override
    public String toString() {
        return title;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getWidthInPx() {
        return widthInPx;
    }

    public void setWidthInPx(int widthInPx) {
        this.widthInPx = widthInPx;
    }

    public int getHeightInPx() {
        return heightInPx;
    }

    public void setHeightInPx(int heightInPx) {
        this.heightInPx = heightInPx;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}