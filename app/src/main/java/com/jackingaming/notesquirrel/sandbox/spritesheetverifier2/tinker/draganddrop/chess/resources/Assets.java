package com.jackingaming.notesquirrel.sandbox.spritesheetverifier2.tinker.draganddrop.chess.resources;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.jackingaming.notesquirrel.R;

public class Assets {
    public static Bitmap spritePawnLight, spriteKingLight, spriteKnightLight,
            spriteRookLight, spriteBishopLight, spriteQueenLight;
    public static Bitmap spritePawnDark, spriteKingDark, spriteKnightDark,
            spriteRookDark, spriteBishopDark, spriteQueenDark;

    public static void init(Resources resources) {
        Bitmap spriteSheetTheChessMaster = BitmapFactory.decodeResource(resources, R.drawable.snes_the_chessmaster_chess_pieces_transparent);

        // LIGHT (player1)
        spritePawnLight = Bitmap.createBitmap(spriteSheetTheChessMaster, 6, 5, 22, 36);
        spriteKingLight = Bitmap.createBitmap(spriteSheetTheChessMaster, 40, 5, 22, 36);
        spriteKnightLight = Bitmap.createBitmap(spriteSheetTheChessMaster, 73, 5, 22, 36);
        spriteRookLight = Bitmap.createBitmap(spriteSheetTheChessMaster, 105, 5, 22, 36);
        spriteBishopLight = Bitmap.createBitmap(spriteSheetTheChessMaster, 138, 5, 22, 36);
        spriteQueenLight = Bitmap.createBitmap(spriteSheetTheChessMaster, 171, 5, 22, 36);

        // DARK (player2)
        spritePawnDark = Bitmap.createBitmap(spriteSheetTheChessMaster, 6, 46, 22, 36);
        spriteKingDark = Bitmap.createBitmap(spriteSheetTheChessMaster, 40, 46, 22, 36);
        spriteKnightDark = Bitmap.createBitmap(spriteSheetTheChessMaster, 73, 46, 22, 36);
        spriteRookDark = Bitmap.createBitmap(spriteSheetTheChessMaster, 105, 46, 22, 36);
        spriteBishopDark = Bitmap.createBitmap(spriteSheetTheChessMaster, 138, 46, 22, 36);
        spriteQueenDark = Bitmap.createBitmap(spriteSheetTheChessMaster, 171, 46, 22, 36);
    }
}