package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.scenes;

import android.content.Context;
import android.util.Log;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.entities.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SceneManager {

    private Map<Scene.Id, Scene> sceneCollection;

    private Player player;
    private GameCamera gameCamera;
    ///////////////////////////////
    private List<Scene> sceneStack;
    ///////////////////////////////

    public SceneManager(Context context, int widthViewport, int heightViewport,
                        GameCartridge.Id cartridgeID, Player player, GameCamera gameCamera) {
        Log.d(MainActivity.DEBUG_TAG, "SceneManager(Context, int, int, GameCartridge.Id, Player, GameCamera) constructor... cartridgeID: " + cartridgeID.name());

        // Must call initSceneCollection() before init() (starting scene instantiated here).
        initSceneCollection(context, widthViewport, heightViewport);

        init(cartridgeID, player, gameCamera);
    }

    /**
     * Instantiate all the scenes in the game (only once per run).
     */
    private void initSceneCollection(Context context, int widthViewport, int heightViewport) {
        Log.d(MainActivity.DEBUG_TAG, "SceneManager.initSceneCollection(Context, int, int)");

        /////////////////////////////////////////////////
        sceneCollection = new HashMap<Scene.Id, Scene>();
        /////////////////////////////////////////////////

        // IMAGEs are not loaded yet (not until Scene.init() is called).
        // This should help with memory issues (on-the-fly loading).
        for (Scene.Id id : Scene.Id.values()) {
            sceneCollection.put(id, new Scene(context, widthViewport, heightViewport, id));
        }
    }

    public void init(GameCartridge.Id cartridgeID, Player player, GameCamera gameCamera) {
        Log.d(MainActivity.DEBUG_TAG, "SceneManager.init(GameCartridge.Id, Player, GameCamera)");

        this.player = player;
        this.gameCamera = gameCamera;

        ////////////////////////////////////
        sceneStack = new ArrayList<Scene>();
        ////////////////////////////////////

        // Determine starting scene based on GameCartridge.Id.
        Scene.Id id = null;
        switch (cartridgeID) {
            case POOH_FARMER:
                id = Scene.Id.FARM;
                break;
            case POCKET_CRITTERS:
                id = Scene.Id.PART_01;
                break;
            default:
                Log.d(MainActivity.DEBUG_TAG, "SceneManager.init(GameCartridge.Id, Player, GameCamera) switch construct's default block.");
        }

        Scene startScene = sceneCollection.get(id);
        ////////////////////////////////////
        startScene.init(player, gameCamera);
        ////////////////////////////////////

        sceneStack.add(startScene);
    }

    /**
     * Handles scene transition.
     *
     * Responsible for calling:
     * -currentScene's exit()
     * -nextScene's init() (if needed)
     * -nextScene's enter()
     *
     * @param id To find instance of nextScene from collection of all the scenes in the game.
     * @param extra
     */
    /*
    public void change(Scene.Id id, Object[] extra) {
        Log.d(MainActivity.DEBUG_TAG, "SceneManager.change(Scene.Id, Object[])");

        /////////////////////////
        getCurrentScene().exit();
        /////////////////////////

        Scene nextScene = sceneCollection.get(id);
        //TODO: improve on the if-condition.
        if (nextScene.getTileMap() == null) {
            ///////////////////////////////////
            nextScene.init(player, gameCamera);
            ///////////////////////////////////
        }

        ///////////////////////
        nextScene.enter(extra);
        ///////////////////////

        push(id);
    }
    */


    public void push(Scene.Id id, Object[] extra) {
        Log.d(MainActivity.DEBUG_TAG, "SceneManager.push(Scene.Id, Object[])");

        //////////////////////////////
        getCurrentScene().exit(extra);
        //////////////////////////////

        Scene nextScene = sceneCollection.get(id);
        //TODO: improve on the if-condition.
        if (nextScene.getTileMap() == null) {
            ///////////////////////////////////
            nextScene.init(player, gameCamera);
            ///////////////////////////////////
        }

        //////////////////
        nextScene.enter();
        //////////////////

        sceneStack.add(nextScene);
    }

    public void pop(Object[] extra) {
        Log.d(MainActivity.DEBUG_TAG, "SceneManager.pop()");

        //////////////////////////////
        getCurrentScene().exit(extra);
        //////////////////////////////

        sceneStack.remove( getIndexOfTop() );

        //TODO: improve on the if-condition.
        if (getCurrentScene().getTileMap() == null) {
            ///////////////////////////////////////////
            getCurrentScene().init(player, gameCamera);
            ///////////////////////////////////////////
        }

        //////////////////////////
        getCurrentScene().enter();
        //////////////////////////
    }

    private int getIndexOfTop() {
        return (sceneStack.size() - 1);
    }

    /**
     * Retrieves most recent scene (top of sceneStack).
     */
    public Scene getCurrentScene() {
        return sceneStack.get( getIndexOfTop() );
    }

}