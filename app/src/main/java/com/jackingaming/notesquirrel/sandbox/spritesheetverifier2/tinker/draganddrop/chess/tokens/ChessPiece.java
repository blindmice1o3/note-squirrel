package com.jackingaming.notesquirrel.sandbox.spritesheetverifier2.tinker.draganddrop.chess.tokens;

import android.graphics.Bitmap;

import com.jackingaming.notesquirrel.sandbox.spritesheetverifier2.tinker.draganddrop.chess.board.Position;

import java.util.List;

public abstract class ChessPiece {
    public enum Color { DARK, LIGHT; }

    protected final Color color;
    protected boolean captured;
    protected Bitmap image;

    public ChessPiece(Color color) {
        this.color = color;
        captured = false;
    }

    public abstract List<Position> findPotentialNewPositions(Position currentPosition);

    public Color getColor() {
        return color;
    }

    public Bitmap getImage() {
        return image;
    }
}