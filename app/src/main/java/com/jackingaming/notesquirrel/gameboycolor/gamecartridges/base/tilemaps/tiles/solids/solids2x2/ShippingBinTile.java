package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.solids.solids2x2;

import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.JackInActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.TimeManager;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.moveable.Player;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.sprites.Assets;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles.Tile;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.products.Sellable;

import java.util.ArrayList;
import java.util.List;

public class ShippingBinTile extends Tile
        implements TimeManager.TimeManagerListener {

    public enum Quadrant { TOP_LEFT, TOP_RIGHT, BOTTOM_LFET, BOTTOM_RIGHT; }

    private Quadrant quadrant;

    public static List<Sellable> stash = new ArrayList<Sellable>();

    public ShippingBinTile(GameCartridge gameCartridge, int xIndex, int yIndex, Quadrant quadrant) {
        super(gameCartridge, xIndex, yIndex);

        this.quadrant = quadrant;
        init(gameCartridge);
    }

    public void init(GameCartridge gameCartridge) {
        super.init(gameCartridge);
        
        image = Assets.cropShippingBinTile(gameCartridge.getContext().getResources(), quadrant);

        /////////////////////////////////////////////////////////////////////
        //ONLY WANT TO REGISTER ONE INSTANCE OF ShippingBinTile AS A LISTENER
        if (quadrant == Quadrant.TOP_LEFT) {
            gameCartridge.getTimeManager().registerTimeManagerListener(this);
        }
        /////////////////////////////////////////////////////////////////////
    }

    public void addSellable(Sellable sellable) {
        stash.add(sellable);
    }

    private int calculateIncomeFromStash() {
        int total = 0;
        for (Sellable sellable : stash) {
            Log.d(MainActivity.DEBUG_TAG, "ShippingBinTile.calculateIncomeFromStash()... " + sellable.getPrice());
            total += sellable.getPrice();
        }
        Log.d(MainActivity.DEBUG_TAG, "ShippingBinTile.calculateIncomeFromStash()... TOTAL: " + total);
        return total;
    }

    private void sellStashAt5pm(Player player) {
        int incomeFromStash = calculateIncomeFromStash();

        int newNetWorth = player.getCurrencyNuggets() + incomeFromStash;

        player.setCurrencyNuggets(newNetWorth);

        //////////////
        stash.clear();
        //////////////
        Log.d(MainActivity.DEBUG_TAG, "ShippingBinTile.sellStashAt5pm(Player)... POST stash.clear()!!!");
    }

    @Override
    public void executeTimedEvent(int hour, int minute, boolean isPM, Player player) {
        String hourFormatted = String.format("%02d", hour);
        String minuteFormatted = String.format("%02d", minute);
        String amOrPm = (isPM) ? "pm" : "am";
        final String message = hourFormatted + ":" + minuteFormatted + amOrPm;
        Log.d(MainActivity.DEBUG_TAG, message);

        //5pm
        if ( (hour == 5) && (minute == 0) && (isPM) ) {
            /////////////////
            sellStashAt5pm(player);
            /////////////////
        }
    }

}