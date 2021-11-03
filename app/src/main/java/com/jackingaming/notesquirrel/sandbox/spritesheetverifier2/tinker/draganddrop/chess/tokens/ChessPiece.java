package com.jackingaming.notesquirrel.sandbox.spritesheetverifier2.tinker.draganddrop.chess.tokens;

import android.graphics.Bitmap;

import com.jackingaming.notesquirrel.sandbox.spritesheetverifier2.tinker.draganddrop.chess.board.Position;
import com.jackingaming.notesquirrel.sandbox.spritesheetverifier2.tinker.draganddrop.chess.board.Tile;

import java.util.ArrayList;
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
//    public abstract boolean isLegalMove(int potentialRowIndex, int potentialColumnIndex);

    /*
    public List<Tile> findPotentialNewPositions(Tile currentPosition, Tile[][] tiles) {
        List<Tile> potentialNewPositions = new ArrayList<Tile>();

        for (int rowIndex = 0; rowIndex < tiles.length; rowIndex++) {
            for (int columnIndex = 0; columnIndex < tiles[rowIndex].length; columnIndex++) {
                if (isLegalMove(rowIndex, columnIndex)) {
                    potentialNewPositions.add(tiles[rowIndex][columnIndex]);
                }
            }
        }

        return potentialNewPositions;
    }
     */

    public Color getColor() {
        return color;
    }

    public Bitmap getImage() {
        return image;
    }
}