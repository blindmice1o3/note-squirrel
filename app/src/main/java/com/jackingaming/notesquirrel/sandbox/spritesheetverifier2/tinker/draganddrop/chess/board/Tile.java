package com.jackingaming.notesquirrel.sandbox.spritesheetverifier2.tinker.draganddrop.chess.board;

import android.widget.ImageView;

import com.jackingaming.notesquirrel.sandbox.spritesheetverifier2.tinker.draganddrop.chess.tokens.ChessPiece;

public class Tile {
    private int rowIndex;
    private int columnIndex;
    private ImageView imageView;
    private String backgroundColor;
    private ChessPiece chessPiece;

    public Tile(int rowIndex, int columnIndex, ImageView imageView, String backgroundColor) {
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
        this.imageView = imageView;
        this.backgroundColor = backgroundColor;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public ChessPiece getChessPiece() {
        return chessPiece;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setChessPieceAndImageBitmap(ChessPiece chessPiece) {
        this.chessPiece = chessPiece;

        if (chessPiece != null) {
            imageView.setImageBitmap(chessPiece.getImage());
        } else {
            imageView.setImageBitmap(null);
        }
        imageView.invalidate();
    }
}