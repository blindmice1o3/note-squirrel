package com.jackingaming.notesquirrel.sandbox.spritesheetverifier2.tinker;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.widget.ImageView;

import com.jackingaming.notesquirrel.R;

public class DragAndDropActivity extends AppCompatActivity {

    private ImageView imageViewLeft;
    private ImageView imageViewCenter;
    private ImageView imageViewRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_and_drop);

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
    }

    class MyDragListener implements View.OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    return true;
                case DragEvent.ACTION_DRAG_ENTERED:
                    return true;
                case DragEvent.ACTION_DRAG_LOCATION:
                    return true;
                case DragEvent.ACTION_DRAG_EXITED:
                    return true;
                case DragEvent.ACTION_DROP:
                    return true;
                case DragEvent.ACTION_DRAG_ENDED:
                    return true;
                default:
                    return false;
            }
        }
    }
}
