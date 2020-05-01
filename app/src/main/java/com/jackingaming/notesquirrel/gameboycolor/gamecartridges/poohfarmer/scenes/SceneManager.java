package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.scenes;

import android.content.Context;
import android.util.Log;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.entities.Player;

import java.util.ArrayList;
import java.util.List;

public class SceneManager {

    private Context context;
    private int widthViewport;
    private int heightViewport;
    private GameCartridge.Id cartridgeID;

    private Player player;
    private GameCamera gameCamera;

    private List<Scene> sceneStack;

    public SceneManager(Context context, int widthViewport, int heightViewport, GameCartridge.Id cartridgeID) {
        this.context = context;
        this.widthViewport = widthViewport;
        this.heightViewport = heightViewport;
        this.cartridgeID = cartridgeID;

        sceneStack = new ArrayList<Scene>();
    }

    public void init(Player player, GameCamera gameCamera) {
        Log.d(MainActivity.DEBUG_TAG, "SceneManager.init(Player, GameCamera)");
        this.player = player;
        this.gameCamera = gameCamera;

        Scene.Id id = null;

        switch (cartridgeID) {
            case POOH_FARMER:
                id = Scene.Id.FARM;
                break;
            case POCKET_CRITTERS:
                id = Scene.Id.PART_01;
                break;
            default:
                Log.d(MainActivity.DEBUG_TAG, "SceneManager.init(Player, GameCamera) switch construct's default block.");
        }

        push(id, null);
    }

    public void push(Scene.Id id, Object[] extra) {
        Log.d(MainActivity.DEBUG_TAG, "SceneManager.push(Scene, Object[])");

        Scene newScene = new Scene(context, widthViewport, heightViewport, id);
        newScene.init(player, gameCamera);

        //////////////////////
        newScene.enter(extra);
        //////////////////////

        sceneStack.add(newScene);
    }

    public void pop() {
        Log.d(MainActivity.DEBUG_TAG, "SceneManager.pop()");

        /////////////////////////////////////////
        sceneStack.get( getIndexOfTop() ).exit();
        /////////////////////////////////////////

        sceneStack.remove( getIndexOfTop() );
    }

    private int getIndexOfTop() {
        return (sceneStack.size() - 1);
    }

    public Scene getCurrentScene() {
        //Top of sceneStack
        return sceneStack.get( getIndexOfTop() );
    }

}