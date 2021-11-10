package com.jackingaming.notesquirrel.sandbox.spritesheetverifier2.tinker.draganddrop.chess.board;

import com.jackingaming.notesquirrel.sandbox.spritesheetverifier2.tinker.draganddrop.chess.tokens.ChessPiece;

public class Tile {
    private int rowIndex;
    private int columnIndex;
    private String backgroundColor;
    private ChessPiece chessPiece;

    public Tile(int rowIndex, int columnIndex, String backgroundColor) {
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
        this.backgroundColor = backgroundColor;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public ChessPiece getChessPiece() {
        return chessPiece;
    }

    public void setChessPiece(ChessPiece chessPiece) {
        this.chessPiece = chessPiece;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }
}