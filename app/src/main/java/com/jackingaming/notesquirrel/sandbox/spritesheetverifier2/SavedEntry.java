package com.jackingaming.notesquirrel.sandbox.spritesheetverifier2;

import java.io.Serializable;
import java.util.List;

public class SavedEntry
        implements Serializable {
    private String fileName;
    private List<Frame> sequenceOfFramesSelectedByUser;

    public SavedEntry(String fileName, List<Frame> sequenceOfFramesSelectedByUser) {
        this.fileName = fileName;
        this.sequenceOfFramesSelectedByUser = sequenceOfFramesSelectedByUser;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<Frame> getSequenceOfFramesSelectedByUser() {
        return sequenceOfFramesSelectedByUser;
    }

    public void setSequenceOfFramesSelectedByUser(List<Frame> sequenceOfFramesSelectedByUser) {
        this.sequenceOfFramesSelectedByUser = sequenceOfFramesSelectedByUser;
    }
}
