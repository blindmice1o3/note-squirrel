package com.jackingaming.notesquirrel.sandbox.spritesheetverifier2.tinker.draganddrop.chess.board;

import androidx.annotation.Nullable;

public class Position {
    private int rowIndex;
    private int columnIndex;

    public Position(int rowIndex, int columnIndex) {
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        // If the object is compared with itself, then return true.
        if (obj == this) {
            return true;
        }

        // Check if obj is an instance of Position or not
        //  ("null instanceof [type]" also returns false).
        if (!(obj instanceof Position)) {
            return false;
        }

        // Typecast to Position we can compare data members.
        Position newPosition = (Position) obj;
        boolean areRowIndexesEqual = (rowIndex == newPosition.getRowIndex());
        boolean areColumnIndexesEqual = (columnIndex == newPosition.getColumnIndex());

        // Compare the data members and return accordingly.
        return (areRowIndexesEqual && areColumnIndexesEqual);
    }

    @Override
    public int hashCode() {
        return (rowIndex + columnIndex);
    }
}
