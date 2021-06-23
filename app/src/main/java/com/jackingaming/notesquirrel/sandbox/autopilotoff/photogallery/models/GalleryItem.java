package com.jackingaming.notesquirrel.sandbox.autopilotoff.photogallery.models;

import androidx.annotation.NonNull;

public class GalleryItem {
    private String caption;
    private String id;
    private String url;

    @NonNull
    @Override
    public String toString() {
        return caption;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}