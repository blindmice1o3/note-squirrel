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

        Scene startScene = null;

        switch (cartridgeID) {
            case POOH_FARMER:
                startScene = new Scene(context, widthViewport, heightViewport, Scene.Id.FARM);
                break;
            case POCKET_CRITTERS:
                startScene = new Scene(context, widthViewport, heightViewport, Scene.Id.PART_01);
                break;
            default:
                Log.d(MainActivity.DEBUG_TAG, "SceneManager.init(Player, GameCamera) switch construct's default block.");
        }

        startScene.init(player, gameCamera); //Scene.init(Player, GameCamera) is called.
        push(startScene, null);        //Scene.enter() is called.
    }

    public void push(Scene scene, Object[] extra) {
        Log.d(MainActivity.DEBUG_TAG, "SceneManager.push(Scene, Object[])");

        ///////////////////
        scene.enter(extra);
        ///////////////////

        sceneStack.add(scene);
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