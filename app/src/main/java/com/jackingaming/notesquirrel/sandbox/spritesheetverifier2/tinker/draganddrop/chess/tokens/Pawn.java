package com.jackingaming.notesquirrel.sandbox.spritesheetverifier2.tinker.draganddrop.chess.tokens;


import com.jackingaming.notesquirrel.sandbox.spritesheetverifier2.tinker.draganddrop.chess.board.Position;
import com.jackingaming.notesquirrel.sandbox.spritesheetverifier2.tinker.draganddrop.chess.resources.Assets;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends ChessPiece {
    /**
     * Used to determine potential new positions in findPotentialNewPosition(Position).
     */
    private boolean firstMove;

    public Pawn(Color color) {
        super(color);
        firstMove = true;

        image = (color == Color.LIGHT) ? Assets.spritePawnLight : Assets.spritePawnDark;
    }

    @Override
    public List<Position> findPotentialNewPositions(Position currentPosition) {
        List<Position> potentialNewPositions = new ArrayList<Position>();
        int currentRowIndex = currentPosition.getRowIndex();
        int currentColumnIndex = currentPosition.getColumnIndex();

        if (color == Color.LIGHT) {
            if (firstMove) {
                Position upTwice = new Position(currentRowIndex - 2,
                        currentColumnIndex);
                potentialNewPositions.add(upTwice);
            }
            Position up = new Position(currentRowIndex - 1,
                    currentColumnIndex);
            potentialNewPositions.add(up);
            Position upLeft = new Position(currentRowIndex - 1,
                    currentColumnIndex - 1);
            potentialNewPositions.add(upLeft);
            Position upRight = new Position(currentRowIndex - 1,
                    currentColumnIndex + 1);
            potentialNewPositions.add(upRight);
        } else {
            if (firstMove) {
                Position downTwice = new Position(currentRowIndex + 2,
                        currentColumnIndex);
                potentialNewPositions.add(downTwice);
            }
            Position down = new Position(currentRowIndex + 1,
                    currentColumnIndex);
            potentialNewPositions.add(down);
            Position downLeft = new Position(currentRowIndex + 1,
                    currentColumnIndex - 1);
            potentialNewPositions.add(downLeft);
            Position downRight = new Position(currentRowIndex + 1,
                    currentColumnIndex + 1);
            potentialNewPositions.add(downRight);
        }

//        if (color == Color.LIGHT) {
//            if (firstMove) {
//                Position upTwice = new Position(currentRowIndex,
//                        currentColumnIndex - 2);
//                potentialNewPositions.add(upTwice);
//            }
//            Position up = new Position(currentRowIndex,
//                    currentColumnIndex - 1);
//            potentialNewPositions.add(up);
//            Position upLeft = new Position(currentRowIndex - 1,
//                    currentColumnIndex - 1);
//            potentialNewPositions.add(upLeft);
//            Position upRight = new Position(currentRowIndex + 1,
//                    currentColumnIndex - 1);
//            potentialNewPositions.add(upRight);
//        } else {
//            if (firstMove) {
//                Position downTwice = new Position(currentRowIndex,
//                        currentColumnIndex + 2);
//                potentialNewPositions.add(downTwice);
//            }
//            Position down = new Position(currentRowIndex,
//                    currentColumnIndex + 1);
//            potentialNewPositions.add(down);
//            Position downLeft = new Position(currentRowIndex - 1,
//                    currentColumnIndex + 1);
//            potentialNewPositions.add(downLeft);
//            Position downRight = new Position(currentRowIndex + 1,
//                    currentColumnIndex + 1);
//            potentialNewPositions.add(downRight);
//        }

        return potentialNewPositions;
    }

    /*
    @Override
    public boolean isLegalMove(int potentialRowIndex, int potentialColumnIndex) {
        if (color == Color.LIGHT) {
            if (potentialColumnIndex == (currentPosition.getColumnIndex() - 1) &&
                    potentialRowIndex == currentPosition.getRowIndex()) {
                return true;
            }
            return false;
        } else {
            if (potentialColumnIndex == (currentPosition.getColumnIndex() + 1) &&
                    potentialRowIndex == currentPosition.getRowIndex()) {
                return true;
            }
            return false;
        }
    }
     */

    public void setFirstMove(boolean firstMove) {
        this.firstMove = firstMove;
    }
}