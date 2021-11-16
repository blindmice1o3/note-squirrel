package com.jackingaming.notesquirrel.sandbox.spritesheetverifier2.tinker.draganddrop.chess.tokens;

import com.jackingaming.notesquirrel.sandbox.spritesheetverifier2.tinker.draganddrop.chess.board.Position;
import com.jackingaming.notesquirrel.sandbox.spritesheetverifier2.tinker.draganddrop.chess.resources.Assets;

import java.util.ArrayList;
import java.util.List;

public class Knight extends ChessPiece {

    public Knight(Color color) {
        super(color);

        image = (color == Color.LIGHT) ? Assets.spriteKnightLight : Assets.spriteKnightDark;
    }

    @Override
    public List<Position> findPotentialNewPositions(Position currentPosition) {
        List<Position> potentialNewPositions = new ArrayList<Position>();
        int currentRowIndex = currentPosition.getRowIndex();
        int currentColumnIndex = currentPosition.getColumnIndex();

        // Up 2 - Left
        Position up2Left1 = new Position(currentRowIndex - 2, currentColumnIndex - 1);
        potentialNewPositions.add(up2Left1);
        // Up 2 - Right
        Position up2Right1 = new Position(currentRowIndex - 2, currentColumnIndex + 1);
        potentialNewPositions.add(up2Right1);
        // Down 2 - Left
        Position down2Left1 = new Position(currentRowIndex + 2, currentColumnIndex - 1);
        potentialNewPositions.add(down2Left1);
        // Down 2 - Right
        Position down2Right1 = new Position(currentRowIndex + 2, currentColumnIndex + 1);
        potentialNewPositions.add(down2Right1);
        // Left 2 - Up
        Position left2Up1 = new Position(currentRowIndex - 1, currentColumnIndex - 2);
        potentialNewPositions.add(left2Up1);
        // Left 2 - Down
        Position left2Down1 = new Position(currentRowIndex + 1, currentColumnIndex - 2);
        potentialNewPositions.add(left2Down1);
        // Right 2 - Up
        Position right2Up1 = new Position(currentRowIndex - 1, currentColumnIndex + 2);
        potentialNewPositions.add(right2Up1);
        // Right 2 - Down
        Position right2Down1 = new Position(currentRowIndex + 1, currentColumnIndex + 2);
        potentialNewPositions.add(right2Down1);

        return potentialNewPositions;
    }
}