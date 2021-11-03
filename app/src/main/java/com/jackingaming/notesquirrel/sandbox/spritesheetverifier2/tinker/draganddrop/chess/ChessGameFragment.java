package com.jackingaming.notesquirrel.sandbox.spritesheetverifier2.tinker.draganddrop.chess;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.sandbox.spritesheetverifier2.tinker.draganddrop.chess.board.Position;
import com.jackingaming.notesquirrel.sandbox.spritesheetverifier2.tinker.draganddrop.chess.board.Tile;
import com.jackingaming.notesquirrel.sandbox.spritesheetverifier2.tinker.draganddrop.chess.resources.Assets;
import com.jackingaming.notesquirrel.sandbox.spritesheetverifier2.tinker.draganddrop.chess.tokens.ChessPiece;
import com.jackingaming.notesquirrel.sandbox.spritesheetverifier2.tinker.draganddrop.chess.tokens.King;
import com.jackingaming.notesquirrel.sandbox.spritesheetverifier2.tinker.draganddrop.chess.tokens.Pawn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChessGameFragment extends Fragment {
    private static final int NUM_OF_ROWS = 8;
    private static final int NUM_OF_COLUMNS = 8;

    private Map<ImageView, Tile> tilesViaImageView = new HashMap<ImageView, Tile>();
    private Tile[][] tiles = new Tile[NUM_OF_ROWS][NUM_OF_COLUMNS];
    private ChessPiece chessPieceBeingMoved;
    private Tile selectedTile;

    public ChessGameFragment() {
        // Required empty public constructor
    }

    public void initTiles(LinearLayout rootLinearLayout) {
        for (int rowIndex = 0; rowIndex < NUM_OF_ROWS; rowIndex++) {
            LinearLayout horizontalLinearLayout = new LinearLayout(getContext());
            horizontalLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    0,
                    1));
            horizontalLinearLayout.setOrientation(LinearLayout.HORIZONTAL);

            /////////////////////////////////////////////////
            rootLinearLayout.addView(horizontalLinearLayout);
            /////////////////////////////////////////////////

            for (int columnIndex = 0; columnIndex < NUM_OF_COLUMNS; columnIndex++) {
                int column = columnIndex + 1;

                final ImageView imageView = new ImageView(getContext());
                imageView.setLayoutParams(new LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        1));
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);

                // Determine background color.
                String tileBackgroundColor = null;
                if (rowIndex % 2 == 1) {
                    // Odd row starts with BLUE colored tile.
                    tileBackgroundColor = (column % 2 == 1) ? "blue" : "yellow";
                } else {
                    // Even row starts with YELLOW colored tile.
                    tileBackgroundColor = (column % 2 == 1) ? "yellow" : "blue";
                }
                if (tileBackgroundColor.equals("blue")) {
                    imageView.setBackground(getResources().getDrawable(R.drawable.tile_default_dark));

//                    imageView.setBackgroundColor(getResources().getColor(R.color.blue));
//
//                    // TODO: testing... should be removed/moved.
//                    // TODO: add a field in Tile class to keep track of its background color.
//                    imageView.setBackground(getResources().getDrawable(R.drawable.tile_highlighed_dark));
//                    // TODO: add more ShapeDrawable resources (e.g. for tile_default_dark and tile_default_light).
//                    // If setting background to null, must reset background color, otherwise white.
//                    imageView.setBackground(null);
//                    imageView.setBackgroundColor(getResources().getColor(R.color.blue));
                } else {
                    imageView.setBackground(getResources().getDrawable(R.drawable.tile_default_light));

//                    imageView.setBackgroundColor(getResources().getColor(R.color.yellow));
//
//                    // TODO: testing... should be removed/moved.
//                    imageView.setBackground(getResources().getDrawable(R.drawable.tile_highlighted_light));
                }

                //////////////////////////////////////////
                horizontalLinearLayout.addView(imageView);
                //////////////////////////////////////////
                Tile currentTile = new Tile(rowIndex, columnIndex, imageView, tileBackgroundColor);
                tilesViaImageView.put(imageView, currentTile);
                tiles[rowIndex][columnIndex] = currentTile;

                // TODO: setOnTouchListener
                imageView.setOnTouchListener(new DragStartListener());

                // TODO: setOnDragListener
                imageView.setOnDragListener(new MyDragEventListener());
            }
        }

//        // ********************* TESTING CAPABILITIES *********************
//        ImageView imageViewD8 = tiles[0][3].getImageView();
//        imageViewD8.setBackgroundColor(getResources().getColor(android.R.color.black));
//        ImageView imageViewH2 = tiles[6][7].getImageView();
//        imageViewH2.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
//        ImageView imageViewD5 = tiles[3][3].getImageView();
//        imageViewD5.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
    }

    private String getCharForNumber(int i) {
        return i > 0 && i < 27 ? String.valueOf((char) (i + 'A' - 1)) : null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chess_board_8x8, container, false);

        //////////////////////////////
        initTiles((LinearLayout) view);
        //////////////////////////////

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Images for ChessPiece subclasses.
        Assets.init(getResources());

        // PAWNS
        for (int columnIndex = 0; columnIndex < NUM_OF_COLUMNS; columnIndex++) {
            ChessPiece pawnDark = new Pawn(ChessPiece.Color.DARK);
            tiles[1][columnIndex].setChessPieceAndImageBitmap(pawnDark);

            ChessPiece pawnLight = new Pawn(ChessPiece.Color.LIGHT);
            tiles[6][columnIndex].setChessPieceAndImageBitmap(pawnLight);
        }
        // PAWNS (testing - opposite ends of the board)
        tiles[0][7].setChessPieceAndImageBitmap(new Pawn(ChessPiece.Color.LIGHT));
        tiles[7][7].setChessPieceAndImageBitmap(new Pawn(ChessPiece.Color.DARK));

        // KINGS
        tiles[7][4].setChessPieceAndImageBitmap(new King(ChessPiece.Color.LIGHT));
        tiles[0][4].setChessPieceAndImageBitmap(new King(ChessPiece.Color.DARK));
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    class DragStartListener implements View.OnTouchListener {
        @TargetApi(24)
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            ImageView imageView = (ImageView) v;

            selectedTile = tilesViaImageView.get(imageView);

            // Do not start a drag/drop operation for tiles without a chess piece.
            if (selectedTile.getChessPiece() == null) {
                Log.d("OnTouchListener", "selectedTile.getChessPiece() is null");
                return false;
            }

            // ***** Tile has a chess piece *****
            String rowAndColumnIndexesToMoveFrom =
                    selectedTile.getRowIndex() + "*" +
                            selectedTile.getColumnIndex();
            ClipData.Item item = new ClipData.Item(rowAndColumnIndexesToMoveFrom);

            ClipData dragData = new ClipData(
                    "[rowIndex][columnIndex]: [" + selectedTile.getRowIndex() + "][" + selectedTile.getColumnIndex() + "]",
                    new String[]{ClipDescription.MIMETYPE_TEXT_PLAIN},
                    item
            );

            // Using default drag shadow instead of MyDragShadowBuilder
            View.DragShadowBuilder myShadow = new View.DragShadowBuilder(imageView);

            v.startDragAndDrop(
                    dragData,           // the data to be dragged
                    myShadow,           // the drag shadow builder
                    null,   // no need to use local data
                    0             // flags (not currently used, set to 0)
            );
            ////////////////////////////////////////////////////////////////////
            // Drag/drop operation started, token no longer "on the board", it's
            // currently being dragged/dropped (meaning the token is being held
            // by the player [represented as a drag shadow]).

            // TODO: potentially wrong logic (not all drag events are successfully dropped)...
            //  in which case, return the token to the tile that started the drag/drop operation.
            chessPieceBeingMoved = selectedTile.getChessPiece();
            selectedTile.setChessPieceAndImageBitmap(null);
            ////////////////////////////////////////////////////////////////////
            return true;
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    class MyDragEventListener implements View.OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            ImageView imageViewToMoveTo = (ImageView) v;
            Tile tileToMoveTo = tilesViaImageView.get(imageViewToMoveTo);

            int action = event.getAction();
            switch (action) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // getClipData(), getX(), getY(), and getResult() are not valid in this state.
                    // TODO: get list of tiles that chessPieceBeingMoved can legally move into.
                    Position currentPosition = new Position(selectedTile.getRowIndex(), selectedTile.getColumnIndex());
                    List<Position> tilesPotentialNewPositions = chessPieceBeingMoved.findPotentialNewPositions(currentPosition);
                    for (Position position : tilesPotentialNewPositions) {
                        int rowIndex = position.getRowIndex();
                        int columnIndex = position.getColumnIndex();

                        if (rowIndex < 0 || columnIndex < 0 ||
                                rowIndex >= NUM_OF_ROWS || columnIndex >= NUM_OF_COLUMNS) {
                            continue;
                        }

                        tiles[rowIndex][columnIndex].getImageView().getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
                    }

                    // do nothing
                    return true;
                case DragEvent.ACTION_DRAG_ENTERED:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        if (((ImageView) v).getBackground().getColorFilter() != null) {
                            return true;
                        }
                    }

                    String tileBackgroundColor = tileToMoveTo.getBackgroundColor();
                    if (tileBackgroundColor.equals("blue")) {
                        imageViewToMoveTo.setBackground(getResources().getDrawable(R.drawable.tile_highlighted_dark));
                    } else {
                        imageViewToMoveTo.setBackground(getResources().getDrawable(R.drawable.tile_highlighted_light));
                    }
//                    ((ImageView) v).getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
                    v.invalidate();
                    return true;
                case DragEvent.ACTION_DRAG_LOCATION:
                    // ignore the event
                    return true;
                case DragEvent.ACTION_DRAG_EXITED:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        if (((ImageView) v).getBackground().getColorFilter() != null) {
                            return true;
                        }
                    }

                    tileBackgroundColor = tileToMoveTo.getBackgroundColor();
                    if (tileBackgroundColor.equals("blue")) {
                        imageViewToMoveTo.setBackground(getResources().getDrawable(R.drawable.tile_default_dark));
                    } else {
                        imageViewToMoveTo.setBackground(getResources().getDrawable(R.drawable.tile_default_light));
                    }

//                    ((ImageView) v).getBackground().clearColorFilter();
                    v.invalidate();
                    return true;
                case DragEvent.ACTION_DROP:
                    // Gets the item containing the dragged data
                    ClipData.Item item = event.getClipData().getItemAt(0);
                    String dragData = item.getText().toString();
                    Log.d("ACTION_DROP", "The drag data is: " + dragData);
                    String[] dragDataSplittedByAsterisk = dragData.split("\\*");
                    String rowIndexToMoveFromAsString = dragDataSplittedByAsterisk[0];
                    String columnIndexToMoveFromAsString = dragDataSplittedByAsterisk[1];
                    int rowIndexToMoveFrom = Integer.parseInt(rowIndexToMoveFromAsString);
                    int columnIndexToMoveFrom = Integer.parseInt(columnIndexToMoveFromAsString);
                    Tile tileToMoveFrom = tiles[rowIndexToMoveFrom][columnIndexToMoveFrom];

                    ///////////////////////////////////////////////////////////////////////
                    // TODO: with the new Map<ImageView, Tile> tilesViaImageView, no longer
                    //  have to moveChessPiece(...) to find tileToMoveTo. Can simply
                    //  pass tileToMoveTo into moveChessPiece(...) to use its business
                    //  logic.
                    moveChessPiece(tileToMoveTo, tileToMoveFrom);
                    ///////////////////////////////////////////////////////////////////////

                    tileBackgroundColor = tileToMoveTo.getBackgroundColor();
                    if (tileBackgroundColor.equals("blue")) {
                        imageViewToMoveTo.setBackground(getResources().getDrawable(R.drawable.tile_default_dark));
                    } else {
                        imageViewToMoveTo.setBackground(getResources().getDrawable(R.drawable.tile_default_light));
                    }

                    chessPieceBeingMoved = null;
                    selectedTile = null;
                    return true;
                case DragEvent.ACTION_DRAG_ENDED:
                    Log.d("MyDragEventListener", "ACTION_DRAG_ENDED");
                    return true;
                default:
                    Log.e("MyDragListener", "Unknown action type received by OnDragListener.");
                    return false;
            }
        }

        private void moveChessPiece(Tile tileToMoveTo, Tile tileToMoveFrom) {
            Log.d("MyDragEventListener",
                    "ChessPiece being moved is: " + chessPieceBeingMoved.getClass().getSimpleName());

            // Potential new positions (turn off green color filter)
            Position currentPosition = new Position(selectedTile.getRowIndex(), selectedTile.getColumnIndex());
            List<Position> tilesPotentialNewPositions = chessPieceBeingMoved.findPotentialNewPositions(currentPosition);
            for (Position position : tilesPotentialNewPositions) {
                int rowIndex = position.getRowIndex();
                int columnIndex = position.getColumnIndex();

                if (rowIndex < 0 || columnIndex < 0 ||
                        rowIndex >= NUM_OF_ROWS || columnIndex >= NUM_OF_COLUMNS) {
                    continue;
                }

                tiles[rowIndex][columnIndex].getImageView().getBackground().clearColorFilter();
            }
//                    imageViewToMoveTo.getBackground().clearColorFilter();
            tileToMoveTo.getImageView().invalidate();
//            v.invalidate();

            if (tileToMoveTo.getChessPiece() == null) {
                tileToMoveTo.setChessPieceAndImageBitmap(chessPieceBeingMoved);

                // ChessPiece was successfully moved, turn off firstMove for Pawn.
                if (chessPieceBeingMoved instanceof Pawn && tileToMoveFrom != tileToMoveTo) {
                    ((Pawn) chessPieceBeingMoved).setFirstMove(false);
                }
            } else {
                // TODO: dropping onto ActionBar currently does NOT return ChessPiece to tileToMoveFrom.
                Log.d("MyDragEventListener",
                        "tileToMoveTo is already occupied with a chess piece: " + tileToMoveTo.getChessPiece().getClass().getSimpleName());

                tileToMoveFrom.setChessPieceAndImageBitmap(chessPieceBeingMoved);
            }
        }
    }

}