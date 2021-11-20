package com.jackingaming.notesquirrel.sandbox.spritesheetverifier2.tinker.draganddrop.chess.board;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.sandbox.spritesheetverifier2.tinker.draganddrop.chess.tokens.ChessPiece;

public class Tile extends AppCompatImageView {
    private int rowIndex;
    private int columnIndex;
    private String backgroundColor;
    private ChessPiece chessPiece;

    public Tile(@NonNull Context context, int rowIndex, int columnIndex) {
        super(context);
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;

        initBackgroundColor();

        changeBackgroundColorToDefault();
    }

    private void initBackgroundColor() {
        // Determine background color.
        int column = columnIndex + 1;
        if (rowIndex % 2 == 1) {
            // Odd row starts with BLUE colored tile.
            backgroundColor = (column % 2 == 1) ? "blue" : "yellow";
        } else {
            // Even row starts with YELLOW colored tile.
            backgroundColor = (column % 2 == 1) ? "yellow" : "blue";
        }
    }

    public void changeBackgroundColorToDefault() {
        if (backgroundColor.equals("blue")) {
            setBackground(getResources().getDrawable(R.drawable.tile_default_dark));
        } else {
            setBackground(getResources().getDrawable(R.drawable.tile_default_light));
        }
        invalidate();
    }

    public void changeBackgroundColorToHighlighted() {
        if (backgroundColor.equals("blue")) {
            setBackground(getResources().getDrawable(R.drawable.tile_highlighted_dark));
        } else {
            setBackground(getResources().getDrawable(R.drawable.tile_highlighted_light));
        }
        invalidate();
    }

    public Tile(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
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