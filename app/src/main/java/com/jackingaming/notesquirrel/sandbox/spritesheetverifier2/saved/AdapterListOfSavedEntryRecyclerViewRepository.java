package com.jackingaming.notesquirrel.sandbox.spritesheetverifier2.saved;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.sandbox.spritesheetverifier2.Frame;
import com.jackingaming.notesquirrel.sandbox.spritesheetverifier2.SavedEntry;
import com.jackingaming.notesquirrel.sandbox.spritesheetverifier2.SpriteSheetVerifier2Activity;
import com.jackingaming.notesquirrel.sandbox.spritesheetverifier2.staging.AdapterFrameRecyclerViewStagingArea;
import com.jackingaming.notesquirrel.sandbox.spritesheetverifier2.staging.ImageViewAnimationRunner;

import java.util.List;

public class AdapterListOfSavedEntryRecyclerViewRepository extends RecyclerView.Adapter<AdapterListOfSavedEntryRecyclerViewRepository.ListOfSavedEntryViewHolder> {
    public interface ItemClickListener {
        void onItemClick(View view, int position, AdapterListOfSavedEntryRecyclerViewRepository adapter);
    }
    private AdapterListOfSavedEntryRecyclerViewRepository.ItemClickListener itemClickListener;

    private Context context;
    private List<SavedEntry> savedList;

    class ListOfSavedEntryViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        public TextView textView;
        public Button buttonPlay;
        public RecyclerView recyclerView;

        public ListOfSavedEntryViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            textView = itemView.findViewById(R.id.textview_repository_for_saved_list);
            buttonPlay = itemView.findViewById(R.id.button_play_selected_sequence);
            recyclerView = itemView.findViewById(R.id.recyclerview_under_textview_repository_for_saved_list);
        }

        @Override
        public void onClick(View v) {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(v, getAdapterPosition(), AdapterListOfSavedEntryRecyclerViewRepository.this);
            }
        }
    }

    public AdapterListOfSavedEntryRecyclerViewRepository(Context context, List<SavedEntry> savedList, AdapterListOfSavedEntryRecyclerViewRepository.ItemClickListener itemClickListener) {
        this.context = context;
        this.savedList = savedList;
        this.itemClickListener = itemClickListener;
        for (SavedEntry savedEtry : savedList) {
            Log.d(MainActivity.DEBUG_TAG, savedEtry.getFileName());
        }
    }

    @NonNull
    @Override
    public ListOfSavedEntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_repository_for_saved_list, parent, false);
        ListOfSavedEntryViewHolder listOfSavedEntryViewHolder = new ListOfSavedEntryViewHolder(view);
        return listOfSavedEntryViewHolder;
    }

    private void displayAnimationAlertDialog(List<Frame> sequenceOfFramesSelectedByUser) {
        Log.d(MainActivity.DEBUG_TAG, getClass().getSimpleName() + ".displayAnimationAlertDialog()");
        View viewContainingImageView = LayoutInflater.from(context).inflate(R.layout.dialog_animation_user_selected_bitmaps, null);
        final ImageView imageView = viewContainingImageView.findViewById(R.id.imageview_animation_user_selected_bitmaps);
        imageView.setImageBitmap(sequenceOfFramesSelectedByUser.get(0).getImageUserSelected());

        final ImageViewAnimationRunner runnable = new ImageViewAnimationRunner(imageView, sequenceOfFramesSelectedByUser);
        final Thread threadAnimationRunner = new Thread(runnable);

        AlertDialog animationDialog = new AlertDialog.Builder(context)
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
                    Toast.makeText(context, "animationDialog's OnDismissListener.onDismission() about to call threadAnimationRunner.join()", Toast.LENGTH_SHORT).show();
                    runnable.shutdown();
                    threadAnimationRunner.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        animationDialog.show();
    }

    @Override
    public void onBindViewHolder(@NonNull ListOfSavedEntryViewHolder holder, int position) {
        SavedEntry savedEntry = savedList.get(position);
        String fileName = savedEntry.getFileName();
        final List<Frame> sequenceOfFramesSelectedByUser = savedEntry.getSequenceOfFramesSelectedByUser();



        holder.textView.setText(fileName);

        holder.buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayAnimationAlertDialog(sequenceOfFramesSelectedByUser);
            }
        });

        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        AdapterFrameRecyclerViewStagingArea adapterFrameRecyclerViewStagingArea = new AdapterFrameRecyclerViewStagingArea(
                context,
                sequenceOfFramesSelectedByUser,
                new AdapterFrameRecyclerViewStagingArea.ItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position, AdapterFrameRecyclerViewStagingArea adapter) {
                        Frame frameSelected = sequenceOfFramesSelectedByUser.get(position);
                        int indexColumn = frameSelected.getIndexColumn();
                        int indexRow = frameSelected.getIndexRow();

                        Toast.makeText(context, "indexColumn, indexRow: " + indexColumn + ", " + indexRow, Toast.LENGTH_SHORT).show();
                    }
                });
        holder.recyclerView.setAdapter(adapterFrameRecyclerViewStagingArea);
    }

    @Override
    public int getItemCount() {
        return savedList.size();
    }
}