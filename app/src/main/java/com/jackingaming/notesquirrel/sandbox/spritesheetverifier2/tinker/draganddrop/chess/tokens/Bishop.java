package com.jackingaming.notesquirrel.sandbox.spritesheetverifier2.tinker.draganddrop.chess.tokens;

import com.jackingaming.notesquirrel.sandbox.spritesheetverifier2.tinker.draganddrop.chess.board.Position;
import com.jackingaming.notesquirrel.sandbox.spritesheetverifier2.tinker.draganddrop.chess.resources.Assets;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends ChessPiece {
    public Bishop(Color color) {
        super(color);

        image = (color == Color.LIGHT) ? Assets.spriteBishopLight : Assets.spriteBishopDark;
    }

    @Override
    public List<Position> findPotentialNewPositions(Position currentPosition) {
        List<Position> potentialNewPositions = new ArrayList<Position>();
        int currentRowIndex = currentPosition.getRowIndex();
        int currentColumnIndex = currentPosition.getColumnIndex();

        for (int i = 0; i < 8; i++) {
            Position upLeft = new Position(currentRowIndex - i, currentColumnIndex - i);
            potentialNewPositions.add(upLeft);
            Position upRight = new Position(currentRowIndex - i, currentColumnIndex + i);
            potentialNewPositions.add(upRight);
            Position downLeft = new Position(currentRowIndex + i, currentColumnIndex - i);
            potentialNewPositions.add(downLeft);
            Position downRight = new Position(currentRowIndex + i, currentColumnIndex + i);
            potentialNewPositions.add(downRight);
        }

        return potentialNewPositions;
    }
}