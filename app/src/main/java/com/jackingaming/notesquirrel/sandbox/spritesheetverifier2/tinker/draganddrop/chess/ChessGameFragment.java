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

    private Map<String, Tile> tilesViaFileAndRank = new HashMap<String, Tile>();
    private ChessPiece chessPieceBeingMoved;

    public ChessGameFragment() {
        // Required empty public constructor
    }

    public void initTiles(LinearLayout rootLinearLayout) {
        View.OnTouchListener dragStartListener = new DragStartListener();
        View.OnDragListener myDragEventListener = new MyDragEventListener();

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

                String file = convertColumnIndexToFile(columnIndex);
                String rank = convertRowIndexToRank(rowIndex);
                String fileAndRank = file + rank;
                imageView.setTag(fileAndRank);

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
                } else {
                    imageView.setBackground(getResources().getDrawable(R.drawable.tile_default_light));
                }

                //////////////////////////////////////////
                horizontalLinearLayout.addView(imageView);
                //////////////////////////////////////////
                Tile currentTile = new Tile(rowIndex, columnIndex, tileBackgroundColor);
                tilesViaFileAndRank.put(fileAndRank, currentTile);

                imageView.setOnTouchListener(dragStartListener);
                imageView.setOnDragListener(myDragEventListener);
            }
        }
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

    private void updateChessPieceAndImageBitmap(String fileAndRank, ChessPiece chessPiece) {
        Tile tile = tilesViaFileAndRank.get(fileAndRank);
        tile.setChessPiece(chessPiece);

        ImageView imageView = (ImageView) getView().findViewWithTag(fileAndRank);
        if (chessPiece != null) {
            imageView.setImageBitmap(chessPiece.getImage());
        } else {
            imageView.setImageBitmap(null);
        }
        imageView.invalidate();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Images for ChessPiece subclasses.
        Assets.init(getResources());

        // PAWNS
        for (int columnIndex = 0; columnIndex < NUM_OF_COLUMNS; columnIndex++) {
            String file = convertColumnIndexToFile(columnIndex);
            String rankPawnDark = convertRowIndexToRank(1);
            String fileAndRankPawnDark = file + rankPawnDark;
            String rankPawnLight = convertRowIndexToRank(6);
            String fileAndRankPawnLight = file + rankPawnLight;

            ChessPiece pawnDark = new Pawn(ChessPiece.Color.DARK);
            updateChessPieceAndImageBitmap(fileAndRankPawnDark, pawnDark);

            ChessPiece pawnLight = new Pawn(ChessPiece.Color.LIGHT);
            updateChessPieceAndImageBitmap(fileAndRankPawnLight, pawnLight);
        }
        // PAWNS (testing - opposite ends of the board)
        String rankPawnDarkOppositeEnd = convertRowIndexToRank(7);
        String fileAndRankPawnDarkOppositeEnd = convertColumnIndexToFile(7) + rankPawnDarkOppositeEnd;

        String rankPawnLightOppositeEnd = convertRowIndexToRank(0);
        String fileAndRankPawnLightOppositeEnd = convertColumnIndexToFile(7) + rankPawnLightOppositeEnd;

        updateChessPieceAndImageBitmap(fileAndRankPawnDarkOppositeEnd, new Pawn(ChessPiece.Color.DARK));
        updateChessPieceAndImageBitmap(fileAndRankPawnLightOppositeEnd, new Pawn(ChessPiece.Color.LIGHT));

        // KINGS
        String rankKingDark = convertRowIndexToRank(0);
        String fileAndRankKingDark = convertColumnIndexToFile(4) + rankKingDark;

        String rankKingLight = convertRowIndexToRank(7);
        String fileAndRankKingLight = convertColumnIndexToFile(4) + rankKingLight;

        updateChessPieceAndImageBitmap(fileAndRankKingDark, new King(ChessPiece.Color.DARK));
        updateChessPieceAndImageBitmap(fileAndRankKingLight, new King(ChessPiece.Color.LIGHT));
    }

    private String convertColumnIndexToFile(int columnIndex) {
        String fileAsString = null;
        switch (columnIndex) {
            case 0:
                fileAsString = "a";
                break;
            case 1:
                fileAsString = "b";
                break;
            case 2:
                fileAsString = "c";
                break;
            case 3:
                fileAsString = "d";
                break;
            case 4:
                fileAsString = "e";
                break;
            case 5:
                fileAsString = "f";
                break;
            case 6:
                fileAsString = "g";
                break;
            case 7:
                fileAsString = "h";
                break;
            default:
                Log.e("convertColumnIndexToFi", "switch's default block... columnIndex: " + columnIndex);
                fileAsString = "-";
                break;
        }
        return fileAsString;
    }

    private String convertRowIndexToRank(int rowIndex) {
        String rankAsString = null;
        switch (rowIndex) {
            case 0:
                rankAsString = "8";
                break;
            case 1:
                rankAsString = "7";
                break;
            case 2:
                rankAsString = "6";
                break;
            case 3:
                rankAsString = "5";
                break;
            case 4:
                rankAsString = "4";
                break;
            case 5:
                rankAsString = "3";
                break;
            case 6:
                rankAsString = "2";
                break;
            case 7:
                rankAsString = "1";
                break;
            default:
                Log.e("convertRowIndexToRank()", "switch's default block... rowIndex: " + rowIndex);
                rankAsString = "-";
                break;
        }
        return rankAsString;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    class DragStartListener implements View.OnTouchListener {
        @TargetApi(24)
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            // Get tileToMoveFrom using the View object from the arguments.
            ImageView imageView = (ImageView) v;
            String fileAndRank = (String) imageView.getTag();
            Tile tileToMoveFrom = tilesViaFileAndRank.get(fileAndRank);

            // Do not start a drag/drop operation for tiles without a chess piece.
            if (tileToMoveFrom.getChessPiece() == null) {
                Log.d("DragStartListener", "tileToMoveFrom.getChessPiece() is null");
                return false;
            }

            // ***** Tile has a chess piece *****
            Log.d("DragStartListener", "tileToMoveFrom.getChessPiece() is: " + tileToMoveFrom.getChessPiece().getClass().getSimpleName());
            Log.d("DragStartListener", "fileAndRank: " + fileAndRank);
            ClipData.Item item = new ClipData.Item(fileAndRank);

            ClipData dragData = new ClipData(
                    fileAndRank,
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
            chessPieceBeingMoved = tileToMoveFrom.getChessPiece();
            updateChessPieceAndImageBitmap(fileAndRank, null);
            ////////////////////////////////////////////////////////////////////

            // Get list of potential new positions that the selected ChessPiece can move to.
            Position currentPosition = new Position(tileToMoveFrom.getRowIndex(), tileToMoveFrom.getColumnIndex());
            List<Position> tilesPotentialNewPositions = chessPieceBeingMoved.findPotentialNewPositions(currentPosition);
            for (Position position : tilesPotentialNewPositions) {
                int rowIndex = position.getRowIndex();
                int columnIndex = position.getColumnIndex();

                if (rowIndex < 0 || columnIndex < 0 ||
                        rowIndex >= NUM_OF_ROWS || columnIndex >= NUM_OF_COLUMNS) {
                    continue;
                }

                String file = convertColumnIndexToFile(columnIndex);
                String rank = convertRowIndexToRank(rowIndex);
                String fileAndRankOfPotentialNewPositions = file + rank;
                ImageView imageViewOfPotentialNewPositions = ChessGameFragment.this.getView().findViewWithTag(fileAndRankOfPotentialNewPositions);
                imageViewOfPotentialNewPositions.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
            }

            return true;
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    class MyDragEventListener implements View.OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            // Get tileToMoveTo using the View object from the arguments.
            ImageView imageViewToMoveTo = (ImageView) v;
            String fileAndRankToMoveTo = (String) imageViewToMoveTo.getTag();
            Tile tileToMoveTo = tilesViaFileAndRank.get(fileAndRankToMoveTo);

            int action = event.getAction();
            switch (action) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // getClipData(), getX(), getY(), and getResult() are not valid in this state.

                    // do nothing
                    return true;
                case DragEvent.ACTION_DRAG_ENTERED:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        if (imageViewToMoveTo.getBackground().getColorFilter() != null) {
                            return true;
                        }
                    }

                    String tileBackgroundColor = tileToMoveTo.getBackgroundColor();
                    if (tileBackgroundColor.equals("blue")) {
                        imageViewToMoveTo.setBackground(getResources().getDrawable(R.drawable.tile_highlighted_dark));
                    } else {
                        imageViewToMoveTo.setBackground(getResources().getDrawable(R.drawable.tile_highlighted_light));
                    }
                    imageViewToMoveTo.invalidate();
                    return true;
                case DragEvent.ACTION_DRAG_LOCATION:
                    // ignore the event
                    return true;
                case DragEvent.ACTION_DRAG_EXITED:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        if (imageViewToMoveTo.getBackground().getColorFilter() != null) {
                            return true;
                        }
                    }

                    tileBackgroundColor = tileToMoveTo.getBackgroundColor();
                    if (tileBackgroundColor.equals("blue")) {
                        imageViewToMoveTo.setBackground(getResources().getDrawable(R.drawable.tile_default_dark));
                    } else {
                        imageViewToMoveTo.setBackground(getResources().getDrawable(R.drawable.tile_default_light));
                    }
                    imageViewToMoveTo.invalidate();
                    return true;
                case DragEvent.ACTION_DROP:
                    ClipData clipData = event.getClipData();
                    Tile tileToMoveFrom = getTileToMoveFromViaClipData(clipData);
                    // Get list of potential new positions that the selected ChessPiece can move to.
                    Position currentPosition = new Position(tileToMoveFrom.getRowIndex(), tileToMoveFrom.getColumnIndex());
                    List<Position> tilesPotentialNewPositions = chessPieceBeingMoved.findPotentialNewPositions(currentPosition);
                    // Turn off green color filter for the potential new positions.
                    for (Position position : tilesPotentialNewPositions) {
                        int rowIndex = position.getRowIndex();
                        int columnIndex = position.getColumnIndex();

                        if (rowIndex < 0 || columnIndex < 0 ||
                                rowIndex >= NUM_OF_ROWS || columnIndex >= NUM_OF_COLUMNS) {
                            continue;
                        }

                        String file = convertColumnIndexToFile(columnIndex);
                        String rank = convertRowIndexToRank(rowIndex);
                        String fileAndRankOfPotentialNewPositions = file + rank;
                        ImageView imageViewOfPotentialNewPositions = ChessGameFragment.this.getView().findViewWithTag(fileAndRankOfPotentialNewPositions);
                        imageViewOfPotentialNewPositions.getBackground().clearColorFilter();
                    }

                    // TODO: MOVE CHESS PIECE (OR RETURN IT TO tileToMoveFrom),
                    //  problematic when user releases drag shadow on action bar
                    //  (DragEvent.ACTION_DROP does NOT get called).
                    if (tileToMoveTo.getChessPiece() == null) {
                        updateChessPieceAndImageBitmap(fileAndRankToMoveTo, chessPieceBeingMoved);

                        // ChessPiece was successfully moved, turn off firstMove for Pawn.
                        if (chessPieceBeingMoved instanceof Pawn && tileToMoveFrom != tileToMoveTo) {
                            ((Pawn) chessPieceBeingMoved).setFirstMove(false);
                        }
                    } else {
                        Log.d("MyDragEventListener",
                                "tileToMoveTo is already occupied with a chess piece: " + tileToMoveTo.getChessPiece().getClass().getSimpleName());

                        ClipData.Item item = clipData.getItemAt(0);
                        String fileAndRankToMoveFrom = item.getText().toString();

                        updateChessPieceAndImageBitmap(fileAndRankToMoveFrom, chessPieceBeingMoved);
                    }
                    ///////////////////////////////////////////////////////////////////////

                    tileBackgroundColor = tileToMoveTo.getBackgroundColor();
                    if (tileBackgroundColor.equals("blue")) {
                        imageViewToMoveTo.setBackground(getResources().getDrawable(R.drawable.tile_default_dark));
                    } else {
                        imageViewToMoveTo.setBackground(getResources().getDrawable(R.drawable.tile_default_light));
                    }

                    chessPieceBeingMoved = null;
                    return true;
                case DragEvent.ACTION_DRAG_ENDED:
                    Log.d("MyDragEventListener", "ACTION_DRAG_ENDED");
                    return true;
                default:
                    Log.e("MyDragEventListener", "Unknown action type received by OnDragListener.");
                    return false;
            }
        }

        /**
         * Get tileToMoveFrom via the drag data.
         * @param clipData contains the fileAndRank (as a String) of the selected tile.
         * @return the tile that we're moving from.
         */
        private Tile getTileToMoveFromViaClipData(ClipData clipData) {
            ClipData.Item item = clipData.getItemAt(0);
            String fileAndRankToMoveFrom = item.getText().toString();
            Tile tileToMoveFrom = tilesViaFileAndRank.get(fileAndRankToMoveFrom);
            return tileToMoveFrom;
        }
    }

}