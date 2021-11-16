package com.jackingaming.notesquirrel.sandbox.spritesheetverifier2.tinker.draganddrop.chess.tokens;

import com.jackingaming.notesquirrel.sandbox.spritesheetverifier2.tinker.draganddrop.chess.board.Position;
import com.jackingaming.notesquirrel.sandbox.spritesheetverifier2.tinker.draganddrop.chess.resources.Assets;

import java.util.ArrayList;
import java.util.List;

public class Rook extends ChessPiece {

    public Rook(Color color) {
        super(color);

        image = (color == Color.LIGHT) ? Assets.spriteRookLight : Assets.spriteRookDark;
    }

    @Override
    public List<Position> findPotentialNewPositions(Position currentPosition) {
        List<Position> potentialNewPositions = new ArrayList<Position>();
        int currentRowIndex = currentPosition.getRowIndex();
        int currentColumnIndex = currentPosition.getColumnIndex();

        for (int i = 0; i < 8; i++) {
            Position left = new Position(currentRowIndex, currentColumnIndex - i);
            potentialNewPositions.add(left);
            Position right = new Position(currentRowIndex, currentColumnIndex + i);
            potentialNewPositions.add(right);
            Position up = new Position(currentRowIndex - i, currentColumnIndex);
            potentialNewPositions.add(up);
            Position down = new Position(currentRowIndex + i, currentColumnIndex);
            potentialNewPositions.add(down);
        }

        return potentialNewPositions;
    }
}