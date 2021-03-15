package com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.poohfarmer.seedshop;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.Game;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.items.BugCatchingNet;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.items.HoneyPot;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.items.Item;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.poohfarmer.SceneFarm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SeedShopDialogFragment extends DialogFragment
        implements Serializable {
    transient private Game game;
    transient Bitmap seedShopBackgroundTop;
    transient Bitmap seedShopBackgroundBottom;
    private List<Item> seedShopInventory;
    transient private ItemRecyclerViewAdapterSeedShop itemRecyclerViewAdapterSeedShop;

    public SeedShopDialogFragment() {
        seedShopInventory = new ArrayList<Item>();
        seedShopInventory.add(new BugCatchingNet());
        seedShopInventory.add(new HoneyPot());
        seedShopInventory.add(new BugCatchingNet());
        seedShopInventory.add(new BugCatchingNet());
        seedShopInventory.add(new BugCatchingNet());
        seedShopInventory.add(new HoneyPot());
        seedShopInventory.add(new BugCatchingNet());
    }

    public void init(Game game) {
        this.game = game;

        for (Item item : seedShopInventory) {
            item.init(game);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final Context contextFinal = getContext();
        itemRecyclerViewAdapterSeedShop = new ItemRecyclerViewAdapterSeedShop(getContext(), seedShopInventory);
        ItemRecyclerViewAdapterSeedShop.ItemClickListener itemClickListener = new ItemRecyclerViewAdapterSeedShop.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(contextFinal, "ItemRecyclerViewAdapterSeedShop.ItemClickListener.onItemClick(View view, int position): " + seedShopInventory.get(position), Toast.LENGTH_SHORT).show();
                // TODO: buy/sell transactions.
//                Item item = seedShopInventory.get(position);
            }
        };
        itemRecyclerViewAdapterSeedShop.setClickListener(itemClickListener);


        View viewContainingRecyclerView = inflater.inflate(R.layout.dialog_seed_shop, null);

        RecyclerView recyclerView = (RecyclerView) viewContainingRecyclerView.findViewById(R.id.recyclerview_seed_shop_inventory);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(itemRecyclerViewAdapterSeedShop);
        int numberOfRows = 1;
        GridLayoutManager gridLayoutManagerHorizontal =
                new GridLayoutManager(getContext(), numberOfRows, GridLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(gridLayoutManagerHorizontal);


        Bitmap seedShopSpriteSheet = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.gbc_hm_seeds_shop);
        seedShopBackgroundTop = Bitmap.createBitmap(seedShopSpriteSheet, 31, 14, 160, 80);
        seedShopBackgroundBottom = Bitmap.createBitmap(seedShopSpriteSheet, 31, 102, 160, 16);

        ImageView imageViewBackgroundTop = (ImageView) viewContainingRecyclerView.findViewById(R.id.imageview_seed_shop_background_top);
        imageViewBackgroundTop.setImageBitmap(seedShopBackgroundTop);
        ImageView imageViewBackgroundBottom = (ImageView) viewContainingRecyclerView.findViewById(R.id.imageview_seed_shop_background_bottom);
        imageViewBackgroundBottom.setImageBitmap(seedShopBackgroundBottom);

        return viewContainingRecyclerView;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
//            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        Log.d(MainActivity.DEBUG_TAG, getClass().getSimpleName() + ".onSaveInstanceState(Bundle outState)");
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        Log.d(MainActivity.DEBUG_TAG, getClass().getSimpleName() + ".onViewStateRestored(Bundle savedInstanceState)");
        super.onViewStateRestored(savedInstanceState);
//        game = ((GameConsoleFragment) ((PassingThroughActivity) getContext()).getSupportFragmentManager().findFragmentByTag(GameConsoleFragment.TAG)).getGame();
//        for (Item item : seedShopInventory) {
//            item.init(game);
//        }
//        itemRecyclerViewAdapterSeedShop.notifyDataSetChanged();
    }
    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        SceneFarm.getInstance().setInSeedShopDialogState(false);
        game.setPaused(false);
        super.onDismiss(dialog);
    }
}