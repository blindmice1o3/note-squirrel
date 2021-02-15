package com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.adapters.ItemRecyclerViewAdapter;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.SceneManager;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities.Creature;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities.Player;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.items.BugCatchingNet;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.items.Item;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.items.Shovel;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.time.TimeManager;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.statsdisplayer.StatsDisplayerFragment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Game {
    public interface StatsChangeListener {
        void onCurrencyChange(int currency);
        void onTimeChange(long timePlayedInMilliseconds);
        void onButtonHolderAChange(Bitmap image);
        void onButtonHolderBChange(Bitmap image);
    }
    private StatsChangeListener statsChangeListener;
    public void setStatsChangeListener(StatsChangeListener statsChangeListener) {
        this.statsChangeListener = statsChangeListener;
    }

    private Context context;
    private SurfaceHolder holder;
    private int widthViewport;
    private int heightViewport;
    private boolean loadNeeded;

    private TimeManager timeManager;
    private SceneManager sceneManager;
//    private Scene scene;
    private GameCamera gameCamera;

    /**
     * Displayed in StatsDisplayerFragment through Game.StatsChangeListener.onCurrencyChange(int currency).
     * Triggered via Player.respondToItemCollisionViaMove(Item item) when item is instanceof HoneyPot.
     */
    private int currency;

    private List<Item> backpack;
    private List<Item> backpackWithoutItemsDisplayingInButtonHolders;
    private ItemRecyclerViewAdapter itemRecyclerViewAdapter;
    private Dialog backpackDialog;

    private Item itemStoredInButtonHolderA;
    private Item itemStoredInButtonHolderB;
    private StatsDisplayerFragment.ButtonHolder buttonHolderCurrentlySelected;

    private boolean paused;
    private boolean inBackpackDialogState;

    public Game() {
        loadNeeded = false;

        timeManager = new TimeManager();
        sceneManager = new SceneManager();
//        scene = SceneWorldMapPart01.getInstance();
//        scene = SceneHome01.getInstance();
        gameCamera = GameCamera.getInstance();

        currency = 0;

        backpack = new ArrayList<Item>();
        backpack.add(new BugCatchingNet());
        backpack.add(new Shovel());
        backpackWithoutItemsDisplayingInButtonHolders = new ArrayList<Item>();

        itemStoredInButtonHolderA = null;
        itemStoredInButtonHolderB = null;
        buttonHolderCurrentlySelected = StatsDisplayerFragment.ButtonHolder.A;

        paused = false;
        inBackpackDialogState = false;
    }

    public void addItemToBackpack(Item item) {
        backpack.add(item);
    }

    public void init(Context context, SurfaceHolder holder, int widthViewport, int heightViewport) {
        this.context = context;
        this.holder = holder;
        this.widthViewport = widthViewport;
        this.heightViewport = heightViewport;

        timeManager.init(this, statsChangeListener);
        sceneManager.init(this);
        gameCamera.init(Player.getInstance(), widthViewport, heightViewport,
                sceneManager.getCurrentScene().getTileManager().getWidthScene(), sceneManager.getCurrentScene().getTileManager().getHeightScene());

        for (Item item : backpack) {
            item.init(this);
        }

        final Context contextFinal = context;
        itemRecyclerViewAdapter = new ItemRecyclerViewAdapter(context, backpackWithoutItemsDisplayingInButtonHolders);
        ItemRecyclerViewAdapter.ItemClickListener itemClickListener = new ItemRecyclerViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(contextFinal, "Game() constructor ItemRecyclerViewAdapter.ItemClickListener.onItemClick(View view, int position): " + backpack.get(position), Toast.LENGTH_SHORT).show();

                Item item = backpackWithoutItemsDisplayingInButtonHolders.get(position);
                switch (buttonHolderCurrentlySelected) {
                    case A:
                        itemStoredInButtonHolderA = item;
                        statsChangeListener.onButtonHolderAChange(item.getImage());
                        backpackDialog.dismiss();
                        break;
                    case B:
                        itemStoredInButtonHolderB = item;
                        statsChangeListener.onButtonHolderBChange(item.getImage());
                        backpackDialog.dismiss();
                        break;
                }
            }
        };
        itemRecyclerViewAdapter.setClickListener(itemClickListener);

        View viewContainingRecyclerView = LayoutInflater.from(context).inflate(R.layout.view_cart_recyclerview, null);
        RecyclerView recyclerView = (RecyclerView) viewContainingRecyclerView.findViewById(R.id.recyclerview_view_cart);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(itemRecyclerViewAdapter);
        int numberOfColumns = 4;
        recyclerView.setLayoutManager(new GridLayoutManager(context, numberOfColumns));

        backpackDialog = new AlertDialog.Builder(context)
                .setTitle("Backpack")
                .setView(viewContainingRecyclerView)
                .create();
        backpackDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                paused = false;
                inBackpackDialogState = false;
            }
        });

        if (loadNeeded) {
            loadViaOS(widthViewport, heightViewport);
            loadNeeded = false;
        }
    }

    private String savedFileViaOSFileName = "savedFileViaOS" + getClass().getSimpleName() + ".ser";
    public void saveViaOS() {
        saveToFile(savedFileViaOSFileName);
    }
    private void loadViaOS(int widthViewport, int heightViewport) {
        loadFromFile(savedFileViaOSFileName, widthViewport, heightViewport);
    }

    private void saveToFile(String fileName) {
        Log.d(MainActivity.DEBUG_TAG, getClass().getSimpleName() + ".saveToFile(String fileName) fileName: " + fileName);
        try (FileOutputStream fs = context.openFileOutput(fileName, Context.MODE_PRIVATE);
             ObjectOutputStream os = new ObjectOutputStream(fs)) {
            // Record player's xLastKnown and yLastKnown for the current scene.
            sceneManager.getCurrentScene().exit();

            os.writeObject(timeManager);
            os.writeObject(sceneManager);
            os.writeObject(gameCamera);
            os.writeInt(currency);

            os.writeObject(backpack);
            refreshBackpackWithoutItemsDisplayingButtonHolders();
            os.writeObject(backpackWithoutItemsDisplayingInButtonHolders);

            boolean emptyItemStoredInButtonHolderA = (itemStoredInButtonHolderA == null);
            os.writeBoolean(emptyItemStoredInButtonHolderA);
            if (!emptyItemStoredInButtonHolderA) {
                os.writeObject(itemStoredInButtonHolderA);
            }
            boolean emptyItemStoredInButtonHolderB = (itemStoredInButtonHolderB == null);
            os.writeBoolean(emptyItemStoredInButtonHolderB);
            if (!emptyItemStoredInButtonHolderB) {
                os.writeObject(itemStoredInButtonHolderB);
            }
            int ordinalValueOfButtonHolderCurrentlySelected = buttonHolderCurrentlySelected.ordinal();
            Log.d(MainActivity.DEBUG_TAG, getClass().getSimpleName() + ".saveToFile(String filName) ordinalValueOfButtonHolderCurrenlySelected: " + ordinalValueOfButtonHolderCurrentlySelected);
            os.writeInt(ordinalValueOfButtonHolderCurrentlySelected);

            os.writeBoolean(paused);
            os.writeBoolean(inBackpackDialogState);
            if (inBackpackDialogState) {
                // If Dialog is open during an emergency shutdown, dismiss Dialog to prevent Exception.
                backpackDialog.dismiss();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadFromFile(String fileName, int widthViewport, int heightViewport) {
        Log.d(MainActivity.DEBUG_TAG, getClass().getSimpleName() + ".loadFromFile(String fileName) fileName: " + fileName);
        try (FileInputStream fi = context.openFileInput(fileName);
             ObjectInputStream os = new ObjectInputStream(fi)) {
            timeManager = (TimeManager) os.readObject();
            timeManager.init(this, statsChangeListener);

            sceneManager = (SceneManager) os.readObject();
            sceneManager.init(this);

            gameCamera = (GameCamera) os.readObject();
            gameCamera.init(Player.getInstance(), widthViewport, heightViewport,
                    sceneManager.getCurrentScene().getTileManager().getWidthScene(),
                    sceneManager.getCurrentScene().getTileManager().getHeightScene());
//            gameCamera.restore(Player.getInstance(), widthViewport, heightViewport);

            currency = os.readInt();
            statsChangeListener.onCurrencyChange(currency);

            backpack = (List<Item>) os.readObject();
            for (Item item : backpack) {
                item.init(this);
            }
            backpackWithoutItemsDisplayingInButtonHolders = (List<Item>) os.readObject();
            for (Item item : backpackWithoutItemsDisplayingInButtonHolders) {
                item.init(this);
            }
            itemRecyclerViewAdapter.setBackpack(backpackWithoutItemsDisplayingInButtonHolders);

            boolean emptyItemStoredInButtonHolderA = os.readBoolean();
            if (!emptyItemStoredInButtonHolderA) {
                itemStoredInButtonHolderA = (Item) os.readObject();
                itemStoredInButtonHolderA.init(this);
                statsChangeListener.onButtonHolderAChange(itemStoredInButtonHolderA.getImage());
            }
            boolean emptyItemStoredInButtonHolderB = os.readBoolean();
            if (!emptyItemStoredInButtonHolderB) {
                itemStoredInButtonHolderB = (Item) os.readObject();
                itemStoredInButtonHolderB.init(this);
                statsChangeListener.onButtonHolderBChange(itemStoredInButtonHolderB.getImage());
            }
            int ordinalValueOfButtonHolderCurrentlySelected = os.readInt();
            Log.d(MainActivity.DEBUG_TAG, getClass().getSimpleName() + ".saveToFile(String filName) ordinalValueOfButtonHolderCurrenlySelected: " + ordinalValueOfButtonHolderCurrentlySelected);
            buttonHolderCurrentlySelected = StatsDisplayerFragment.ButtonHolder.values()[ordinalValueOfButtonHolderCurrentlySelected];

            paused = os.readBoolean();
            sceneManager.getCurrentScene().drawCurrentFrame(holder);
            inBackpackDialogState = os.readBoolean();
            if (inBackpackDialogState) {
                showBackpackDialog();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void update(long elapsed) {
        if (paused) {
            return;
        }
        timeManager.update(elapsed);
        sceneManager.update(elapsed);
        gameCamera.update(elapsed);
    }

    public void draw() {
        if (paused) {
            return;
        }
        sceneManager.drawCurrentFrame(holder);
    }

    public void doPressingUp() {
        if (paused) {
            return;
        }
        Player.getInstance().setDirection(Creature.Direction.UP);
    }

    public void doPressingDown() {
        if (paused) {
            return;
        }
        Player.getInstance().setDirection(Creature.Direction.DOWN);
    }

    public void doPressingLeft() {
        if (paused) {
            return;
        }
        Player.getInstance().setDirection(Creature.Direction.LEFT);
    }

    public void doPressingRight() {
        if (paused) {
            return;
        }
        Player.getInstance().setDirection(Creature.Direction.RIGHT);
    }

    public void doPressingUpLeft() {
        if (paused) {
            return;
        }
        Player.getInstance().setDirection(Creature.Direction.UP_LEFT);
    }

    public void doPressingUpRight() {
        if (paused) {
            return;
        }
        Player.getInstance().setDirection(Creature.Direction.UP_RIGHT);
    }

    public void doPressingDownLeft() {
        if (paused) {
            return;
        }
        Player.getInstance().setDirection(Creature.Direction.DOWN_LEFT);
    }

    public void doPressingDownRight() {
        if (paused) {
            return;
        }
        Player.getInstance().setDirection(Creature.Direction.DOWN_RIGHT);
    }

    public void doJustPressedButtonMenu() {
        Log.d(MainActivity.DEBUG_TAG, getClass().getSimpleName() + ".doJustPressedButtonMenu()");
        paused = !paused;
    }

    public void doClickButtonHolder(StatsDisplayerFragment.ButtonHolder buttonHolder) {
        buttonHolderCurrentlySelected = buttonHolder;

        refreshBackpackWithoutItemsDisplayingButtonHolders();

        showBackpackDialog();
    }

    private void refreshBackpackWithoutItemsDisplayingButtonHolders() {
        backpackWithoutItemsDisplayingInButtonHolders.clear();
        backpackWithoutItemsDisplayingInButtonHolders.addAll(backpack);
        if (itemStoredInButtonHolderA != null) {
            Toast.makeText(context, getClass().getSimpleName() + ".doClickButtonHolder(StatsDisplayerFragment.ButtonHolder buttonHolder) itemStoredInButtonHolderA: " + itemStoredInButtonHolderA, Toast.LENGTH_SHORT).show();
            backpackWithoutItemsDisplayingInButtonHolders.remove(itemStoredInButtonHolderA);
            itemRecyclerViewAdapter.notifyDataSetChanged();
        }
        if (itemStoredInButtonHolderB != null) {
            Toast.makeText(context, getClass().getSimpleName() + ".doClickButtonHolder(StatsDisplayerFragment.ButtonHolder buttonHolder) itemStoredInButtonHolderB: " + itemStoredInButtonHolderB, Toast.LENGTH_SHORT).show();
            backpackWithoutItemsDisplayingInButtonHolders.remove(itemStoredInButtonHolderB);
            itemRecyclerViewAdapter.notifyDataSetChanged();
        }
    }

    private void showBackpackDialog() {
        paused = true;
        inBackpackDialogState = true;
        backpackDialog.show();
    }

    public void doJustPressedButtonA() {
        Log.d(MainActivity.DEBUG_TAG, getClass().getSimpleName() + ".doJustPressedButtonA()");
        if (paused) {
            return;
        }
        // TODO: suppose to execute() the item stored in itemStoredInButtonHolderA.

        // TODO: currently using to test picking up item.
        Player.getInstance().doCheckItemCollisionViaClick();

        // TODO: leads to buggy save/load between gameCamera and scene's tiles.
//        int doubleClipWidthInTile = 2 * gameCamera.getClipWidthInTile();
//        int doubleClipHeightInTile = 2 * gameCamera.getClipHeightInTile();
//        gameCamera.updateClipWidthInTile(doubleClipWidthInTile);
//        gameCamera.updateClipHeightInTile(doubleClipHeightInTile);
    }

    public void doJustPressedButtonB() {
        Log.d(MainActivity.DEBUG_TAG, getClass().getSimpleName() + ".doJustPressedButtonB()");
        if (paused) {
            return;
        }
        // TODO: suppose to execute() the item stored in itemStoredInButtonHolderB.

        // TODO: leads to buggy save/load between gameCamera and scene's tiles.
//        int halfClipWidthInTile = gameCamera.getClipWidthInTile() / 2;
//        int halfClipHeightInTile = gameCamera.getClipHeightInTile() / 2;
//        gameCamera.updateClipWidthInTile(halfClipWidthInTile);
//        gameCamera.updateClipHeightInTile(halfClipHeightInTile);
    }

    public Context getContext() {
        return context;
    }

    public int getWidthViewport() {
        return widthViewport;
    }

    public int getHeightViewport() {
        return heightViewport;
    }

    public SceneManager getSceneManager() {
        return sceneManager;
    }

    public GameCamera getGameCamera() {
        return gameCamera;
    }

    public void setLoadNeeded(boolean loadNeeded) {
        this.loadNeeded = loadNeeded;
    }

    public void incrementCurrency() {
        currency++;
        ///////////////////////////////////////////////
        statsChangeListener.onCurrencyChange(currency);
        ///////////////////////////////////////////////
    }
}