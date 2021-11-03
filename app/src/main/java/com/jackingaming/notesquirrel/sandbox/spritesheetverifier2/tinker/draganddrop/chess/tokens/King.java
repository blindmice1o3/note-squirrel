package com.jackingaming.notesquirrel.sandbox.spritesheetverifier2.tinker.draganddrop.chess.tokens;

import com.jackingaming.notesquirrel.sandbox.spritesheetverifier2.tinker.draganddrop.chess.board.Position;
import com.jackingaming.notesquirrel.sandbox.spritesheetverifier2.tinker.draganddrop.chess.resources.Assets;

import java.util.ArrayList;
import java.util.List;

public class King extends ChessPiece {

    public King(Color color) {
        super(color);

        image = (color == Color.LIGHT) ? Assets.spriteKingLight : Assets.spriteKingDark;
    }

    @Override
    public List<Position> findPotentialNewPositions(Position currentPosition) {
        List<Position> potentialNewPositions = new ArrayList<Position>();
        int currentRowIndex = currentPosition.getRowIndex();
        int currentColumnIndex = currentPosition.getColumnIndex();

        Position up = new Position(currentRowIndex - 1, currentColumnIndex);
        potentialNewPositions.add(up);
        Position down = new Position(currentRowIndex + 1, currentColumnIndex);
        potentialNewPositions.add(down);
        Position left = new Position(currentRowIndex, currentColumnIndex - 1);
        potentialNewPositions.add(left);
        Position right = new Position(currentRowIndex, currentColumnIndex + 1);
        potentialNewPositions.add(right);

        Position upLeft = new Position(currentRowIndex - 1, currentColumnIndex - 1);
        potentialNewPositions.add(upLeft);
        Position upRight = new Position(currentRowIndex - 1, currentColumnIndex + 1);
        potentialNewPositions.add(upRight);
        Position downLeft = new Position(currentRowIndex + 1, currentColumnIndex - 1);
        potentialNewPositions.add(downLeft);
        Position downRight = new Position(currentRowIndex + 1, currentColumnIndex + 1);
        potentialNewPositions.add(downRight);

//        Position up = new Position(currentRowIndex, currentColumnIndex - 1);
//        potentialNewPositions.add(up);
//        Position down = new Position(currentRowIndex, currentColumnIndex + 1);
//        potentialNewPositions.add(down);
//        Position left = new Position(currentRowIndex - 1, currentColumnIndex);
//        potentialNewPositions.add(left);
//        Position right = new Position(currentRowIndex + 1, currentColumnIndex);
//        potentialNewPositions.add(right);
//
//        Position upLeft = new Position(currentRowIndex - 1, currentColumnIndex - 1);
//        potentialNewPositions.add(upLeft);
//        Position upRight = new Position(currentRowIndex + 1, currentColumnIndex - 1);
//        potentialNewPositions.add(upRight);
//        Position downLeft = new Position(currentRowIndex - 1, currentColumnIndex + 1);
//        potentialNewPositions.add(downLeft);
//        Position downRight = new Position(currentRowIndex + 1, currentColumnIndex + 1);
//        potentialNewPositions.add(downRight);

        return potentialNewPositions;
    }

}