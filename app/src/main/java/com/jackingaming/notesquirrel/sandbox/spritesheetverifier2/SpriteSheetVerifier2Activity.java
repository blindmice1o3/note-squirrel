package com.jackingaming.notesquirrel.sandbox.spritesheetverifier2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.sandbox.spritesheetverifier2.saved.AdapterListOfSavedEntryRecyclerViewRepository;
import com.jackingaming.notesquirrel.sandbox.spritesheetverifier2.staging.AdapterFrameRecyclerViewStagingArea;
import com.jackingaming.notesquirrel.sandbox.spritesheetverifier2.staging.ImageViewAnimationRunner;
import com.jackingaming.notesquirrel.sandbox.spritesheetverifier2.tinker.draganddrop.chess.DragAndDropActivity;
import com.jackingaming.notesquirrel.sandbox.spritesheetverifier2.tinker.draganddrop.vogella.DragActivity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class SpriteSheetVerifier2Activity extends AppCompatActivity {
    private static final String SAVED_LIST_FILE_NAME = "savedListFileName";

    private Bitmap[][] mRobotRSeries;
    private List<Bitmap> dataSource;
    private List<Frame> framesSelectedByUser;

    private List<SavedEntry> savedList;

    private AdapterFrameRecyclerViewStagingArea adapterFrameRecyclerViewStagingArea;
    private AdapterListOfSavedEntryRecyclerViewRepository adapterListOfSavedEntryRecyclerViewRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sprite_sheet_verifier2);
        Log.d(MainActivity.DEBUG_TAG, getClass().getSimpleName() + ".onCreate(Bundle)");

        //Initialize mRobotRSeries (crop and store the top portion of R.drawable.snes_chrono_trigger_r_series)
        int rows = 22;
        final int columns = 11;
        mRobotRSeries = new Bitmap[rows][columns];
        Bitmap spriteSheet = BitmapFactory.decodeResource(getResources(), R.drawable.snes_chrono_trigger_r_series);
        int widthSprite = 32;
        int heightSprite = 32;
        int offsetHorizontal = 8;
        int offsetVertical = 8;
        for (int row = 0; row < mRobotRSeries.length; row++) {
            for (int column = 0; column < mRobotRSeries[row].length; column++) {
                int xStart = column * (widthSprite + offsetHorizontal);
                int yStart = row * (heightSprite + offsetVertical);
                mRobotRSeries[row][column] = Bitmap.createBitmap(spriteSheet, xStart, yStart,
                        widthSprite, heightSprite);
            }
        }

        dataSource = new ArrayList<Bitmap>();
        for (int row = 0; row < mRobotRSeries.length; row++) {
            for (int column = 0; column < mRobotRSeries[row].length; column++) {
                dataSource.add(mRobotRSeries[row][column]);
            }
        }

        framesSelectedByUser = new ArrayList<Frame>();

        loadFromFile(SAVED_LIST_FILE_NAME);
        if (savedList == null) {
            savedList = new ArrayList<SavedEntry>();
        } else {
            for (SavedEntry savedEntry : savedList) {
                List<Frame> sequenceOfFramesSelectedByUser = savedEntry.getSequenceOfFramesSelectedByUser();
                for (Frame frame : sequenceOfFramesSelectedByUser) {
                    int indexColumn = frame.getIndexColumn();
                    int indexRow = frame.getIndexRow();
                    Bitmap imageToRestore = mRobotRSeries[indexRow][indexColumn];

                    frame.setImageUserSelected(imageToRestore);
                }
            }
        }


        RecyclerView recyclerViewMain = findViewById(R.id.recyclerview_sprite_sheet_verifier2);
        recyclerViewMain.setLayoutManager(new GridLayoutManager(this, columns));
        AdapterBitmapRecyclerViewMain.ItemClickListener itemClickListener = new AdapterBitmapRecyclerViewMain.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position, AdapterBitmapRecyclerViewMain adapter) {
                int indexRow = position / columns;
                int indexColumn = position % columns;
                Toast.makeText(SpriteSheetVerifier2Activity.this, indexRow + ", " + indexColumn, Toast.LENGTH_SHORT).show();

                Frame frame = new Frame(indexColumn, indexRow, dataSource.get(position));
                framesSelectedByUser.add(frame);

                View viewStagingArea = getLayoutInflater().inflate(R.layout.dialog_staging_area_for_frames_selected_by_user, null);

                Button buttonSave = viewStagingArea.findViewById(R.id.button_save_review_user_selected_bitmaps_dialog);
                buttonSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        displaySaveAlertDialog();
                    }
                });

                Button buttonAnimate = viewStagingArea.findViewById(R.id.button_animate_review_user_selected_bitmaps_dialog);
                buttonAnimate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (framesSelectedByUser.size() > 0) {
                            displayAnimationAlertDialog();
                        } else {
                            Toast.makeText(SpriteSheetVerifier2Activity.this, "no images selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                RecyclerView recyclerViewStagingArea = viewStagingArea.findViewById(R.id.recyclerview_staging_area_for_frames_selected_by_user);
                recyclerViewStagingArea.setLayoutManager(new LinearLayoutManager(SpriteSheetVerifier2Activity.this, LinearLayoutManager.HORIZONTAL, false));
                adapterFrameRecyclerViewStagingArea = new AdapterFrameRecyclerViewStagingArea(
                        SpriteSheetVerifier2Activity.this,
                        framesSelectedByUser,
                        new AdapterFrameRecyclerViewStagingArea.ItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position, AdapterFrameRecyclerViewStagingArea adapter) {
                                framesSelectedByUser.remove(position);
                                adapter.notifyDataSetChanged();
                            }
                        });
                recyclerViewStagingArea.setAdapter(adapterFrameRecyclerViewStagingArea);

                Dialog reviewUserSelectedBitmapsDialog = new AlertDialog.Builder(SpriteSheetVerifier2Activity.this)
                        .setView(viewStagingArea)
                        .create();

                reviewUserSelectedBitmapsDialog.show();
            }
        };
        AdapterBitmapRecyclerViewMain adapterBitmapRecyclerView = new AdapterBitmapRecyclerViewMain(this, dataSource, itemClickListener);
        recyclerViewMain.setAdapter(adapterBitmapRecyclerView);
    }

    private void displaySaveAlertDialog() {
        Log.d(MainActivity.DEBUG_TAG, getClass().getSimpleName() + ".displaySaveAlertDialog()");
        View viewContainingEditText = getLayoutInflater().inflate(R.layout.dialog_save_user_selected_bitmaps, null);
        final EditText editText = viewContainingEditText.findViewById(R.id.edittext_save_user_selected_bitmaps);

        AlertDialog saveDialog = new AlertDialog.Builder(this)
                .setView(viewContainingEditText)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String fileName = editText.getText().toString();
                        if (fileName != null && fileName.length() > 0) {
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

                            List<Frame> sequenceOfFramesSelectedByUser = new ArrayList<Frame>(framesSelectedByUser);
                            SavedEntry savedEntry = new SavedEntry(fileName, sequenceOfFramesSelectedByUser);
                            savedList.add(savedEntry);

                            framesSelectedByUser.clear();
                            adapterFrameRecyclerViewStagingArea.notifyDataSetChanged();
                        } else {
                            Toast toast = Toast.makeText(SpriteSheetVerifier2Activity.this, "SpriteSheetVerifier2Activity saveDialog's fileName is null or empty.", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                            toast.show();
                        }
                    }
                })
                .create();

        saveDialog.show();
    }

    private void displayAnimationAlertDialog() {
        Log.d(MainActivity.DEBUG_TAG, getClass().getSimpleName() + ".displayAnimationAlertDialog()");
        View viewContainingImageView = getLayoutInflater().inflate(R.layout.dialog_animation_user_selected_bitmaps, null);
        final ImageView imageView = viewContainingImageView.findViewById(R.id.imageview_animation_user_selected_bitmaps);
        imageView.setImageBitmap(framesSelectedByUser.get(0).getImageUserSelected());

        final ImageViewAnimationRunner runnable = new ImageViewAnimationRunner(imageView, framesSelectedByUser);
        final Thread threadAnimationRunner = new Thread(runnable);

        AlertDialog animationDialog = new AlertDialog.Builder(this)
                .setView(viewContainingImageView)
                .create();

        animationDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                threadAnimationRunner.start();
            }
        });

        animationDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                try {
                    Toast.makeText(SpriteSheetVerifier2Activity.this, "animationDialog's OnDismissListener.onDismission() about to call threadAnimationRunner.join()", Toast.LENGTH_SHORT).show();
                    runnable.shutdown();
                    threadAnimationRunner.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        animationDialog.show();
    }

    private void displayEditFileNameOfSavedEntry(final SavedEntry savedEntry) {
        View viewContainingEditText = getLayoutInflater().inflate(R.layout.dialog_save_user_selected_bitmaps, null);
        final EditText editText = viewContainingEditText.findViewById(R.id.edittext_save_user_selected_bitmaps);

        String fileNameToBeEditted = savedEntry.getFileName();
        editText.setText(fileNameToBeEditted);

        final AlertDialog editDialog = new AlertDialog.Builder(this)
                .setView(viewContainingEditText)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String fileNameToBeSaved = editText.getText().toString();
                        if (fileNameToBeSaved != null && fileNameToBeSaved.length() > 0) {
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

                            savedEntry.setFileName(fileNameToBeSaved);

                            adapterListOfSavedEntryRecyclerViewRepository.notifyDataSetChanged();
//                            repositoryForSavedListDialog.dismiss();
                        } else {
                            Toast toast = Toast.makeText(SpriteSheetVerifier2Activity.this, "SpriteSheetVerifier2Activity editDialog's fileName is null or empty.", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                            toast.show();
                        }
                    }
                })
                .create();

        editDialog.show();
    }

    private void displaySavedListAlertDialog() {
        Log.d(MainActivity.DEBUG_TAG, getClass().getSimpleName() + ".displaySavedListAlertDialog()");
        View viewContainingRecyclerView = getLayoutInflater().inflate(R.layout.dialog_repository_for_saved_list, null);
        RecyclerView recyclerViewRepositorySavedList = viewContainingRecyclerView.findViewById(R.id.recyclerview_repository_for_saved_list);
        recyclerViewRepositorySavedList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapterListOfSavedEntryRecyclerViewRepository = new AdapterListOfSavedEntryRecyclerViewRepository(
                this,
                savedList,
                new AdapterListOfSavedEntryRecyclerViewRepository.ItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position, AdapterListOfSavedEntryRecyclerViewRepository adapter) {
                        SavedEntry savedEntry = savedList.get(position);
                        Log.d(MainActivity.DEBUG_TAG, "displaySavedListAlertDialog() savedEntry.getFileName(): " + savedEntry.getFileName());

                        displayEditFileNameOfSavedEntry(savedEntry);

                        Toast.makeText(SpriteSheetVerifier2Activity.this, "AdapterListOfFrameRecyclerViewRepository onItemClick() position: " + position, Toast.LENGTH_SHORT).show();
                    }
                }
        );
        recyclerViewRepositorySavedList.setAdapter(adapterListOfSavedEntryRecyclerViewRepository);


        Dialog repositoryForSavedListDialog = new AlertDialog.Builder(this)
                .setView(viewContainingRecyclerView)
                .create();

//        repositoryForSavedListDialog.setOnShowListener(new DialogInterface.OnShowListener() {
//            @Override
//            public void onShow(DialogInterface dialog) {
//                repositoryForSavedListDialog.getWindow().setLayout(
//                        ViewGroup.LayoutParams.MATCH_PARENT,
//                        ViewGroup.LayoutParams.MATCH_PARENT);
////            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//            }
//        });

        repositoryForSavedListDialog.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveToFile(SAVED_LIST_FILE_NAME);
    }

    private void saveToFile(String savedListFileName) {
        Log.d(MainActivity.DEBUG_TAG, getClass().getSimpleName() + ".saveToFile(String savedListFileName) START savedListFileName: " + savedListFileName);
        try (FileOutputStream fs = openFileOutput(savedListFileName, Context.MODE_PRIVATE);
             ObjectOutputStream os = new ObjectOutputStream(fs)) {
            os.writeObject(savedList);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(MainActivity.DEBUG_TAG, getClass().getSimpleName() + ".saveToFile(String savedListFileName) FINISHED.");
    }

    private void loadFromFile(String savedListFileName) {
        Log.d(MainActivity.DEBUG_TAG, getClass().getSimpleName() + ".loadFromFile(String savedListFileName) START savedListFileName: " + savedListFileName);
        try (FileInputStream fi = openFileInput(savedListFileName);
             ObjectInputStream os = new ObjectInputStream(fi)) {
            savedList = (List<SavedEntry>) os.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Log.d(MainActivity.DEBUG_TAG, getClass().getSimpleName() + ".loadFromFile(String savedListFileName) FINISHED.");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu_activity_sprite_sheet_verifier2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case  R.id.menu_item_see_saved_sequences:
                displaySavedListAlertDialog();
                return true;
            case R.id.menu_item_drag:
                Intent intentDrag = new Intent(this, DragActivity.class);
                startActivity(intentDrag);
                return true;
            case R.id.menu_item_drag_and_drop:
                Intent intentDragAndDrop = new Intent(this, DragAndDropActivity.class);
                startActivity(intentDragAndDrop);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}