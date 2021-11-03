package com.jackingaming.notesquirrel.sandbox.spritesheetverifier2.tinker.draganddrop.chess;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.jackingaming.notesquirrel.R;

public class DragAndDropActivity extends AppCompatActivity {

/*
    private ImageView imageViewLeft;
    private ImageView imageViewCenter;
    private ImageView imageViewRight;
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_and_drop);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragmentChessBoard = fm.findFragmentById(R.id.fragmentContainerChessBoard);

        if (fragmentChessBoard == null) {
            fragmentChessBoard = new ChessGameFragment();
        }

        fm.beginTransaction()
                .add(R.id.fragmentContainerChessBoard, fragmentChessBoard)
                .commit();

/*
        Bitmap spriteSheetTheChessMaster = BitmapFactory.decodeResource(getResources(), R.drawable.snes_the_chessmaster_chess_pieces);
        Bitmap spritePawnLight = Bitmap.createBitmap(spriteSheetTheChessMaster, 7, 8, 20, 33);
        Bitmap spritePawnDark = Bitmap.createBitmap(spriteSheetTheChessMaster, 7, 49, 20, 33);
        Bitmap spriteKnightLight = Bitmap.createBitmap(spriteSheetTheChessMaster, 73, 6, 22, 35);

        MyDragListener dragListener = new MyDragListener();

        imageViewLeft = findViewById(R.id.imageview_drag_and_drop_left);
        imageViewLeft.setImageBitmap(spritePawnLight);
        imageViewLeft.setOnDragListener(dragListener);

        imageViewCenter = findViewById(R.id.imageview_drag_and_drop_center);
        imageViewCenter.setImageBitmap(spriteKnightLight);
        imageViewCenter.setOnDragListener(dragListener);

        imageViewRight = findViewById(R.id.imageview_drag_and_drop_right);
        imageViewRight.setImageBitmap(spritePawnDark);
        imageViewRight.setOnDragListener(dragListener);
 */
    }

}
