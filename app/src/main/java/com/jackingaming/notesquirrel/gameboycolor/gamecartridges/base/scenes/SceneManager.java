package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes;

import android.util.Log;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCamera;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.Handler;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.Player;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.frogger.scenes.SceneFrogger;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.scenes.SceneFarm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.Scene.Id.FARM;
import static com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.Scene.Id.FROGGER;

public class SceneManager {

    private Handler handler;

    private Player player;
    private GameCamera gameCamera;

    /////////////////////////////////////////////
    private Map<Scene.Id, Scene> sceneCollection;
    private List<Scene> sceneStack;
    /////////////////////////////////////////////

    public SceneManager(Handler handler) {
        Log.d(MainActivity.DEBUG_TAG, "SceneManager(Handler) constructor");

        this.handler = handler;

        //////////////////////////////////////////////////////////////////////////////////////////
        // Must call initSceneCollection() before Scene.init() (starting scene instantiated here).
        initSceneCollection();
        //////////////////////////////////////////////////////////////////////////////////////////

        player = handler.getGameCartridge().getPlayer();
        gameCamera = handler.getGameCartridge().getGameCamera();

        ////////////////////////////////////
        sceneStack = new ArrayList<Scene>();
        ////////////////////////////////////

        // Determine starting scene based on GameCartridge.Id.
        Scene.Id id = null;
        switch (handler.getGameCartridge().getIdGameCartridge()) {
            case POOH_FARMER:
                id = Scene.Id.FARM;
                break;
            case POCKET_CRITTERS:
                id = Scene.Id.PART_01;
                break;
            case FROGGER:
                id = FROGGER;
                break;
            default:
                Log.d(MainActivity.DEBUG_TAG, "SceneManager() constructor's switch construct's default block.");
        }

        Scene startScene = sceneCollection.get(id);
        ////////////////////////////////////
        startScene.init(player, gameCamera);
        ////////////////////////////////////

        ///////////////////
        startScene.enter();
        ///////////////////

        sceneStack.add(startScene);
    }

    /**
     * Instantiate all the scenes in the game (only once per run).
     */
    public void initSceneCollection() {
        Log.d(MainActivity.DEBUG_TAG, "SceneManager.initSceneCollection()");

        /////////////////////////////////////////////////
        sceneCollection = new HashMap<Scene.Id, Scene>();
        /////////////////////////////////////////////////

        // IMAGEs are not loaded yet (not until Scene.init() is called).
        // This should help with memory issues (on-the-fly loading).
        for (Scene.Id id : Scene.Id.values()) {
            if ( (id != FROGGER) && (id != FARM) ) {
                sceneCollection.put(id, new Scene(handler, id));
            } else if (id == FROGGER) {
                ///////////////////////////////////////////////////////
                sceneCollection.put(id, new SceneFrogger(handler, id));
                ///////////////////////////////////////////////////////
            } else if (id == FARM) {
                ////////////////////////////////////////////////////
                sceneCollection.put(id, new SceneFarm(handler, id));
                ////////////////////////////////////////////////////
            }
        }
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

    public Scene getScene(Scene.Id id) {
        return sceneCollection.get(id);
    }

    public void putScene(Scene.Id id, Scene scene) {
        sceneCollection.put(id, scene);
    }

    /**
     * Retrieves most recent scene (top of sceneStack).
     */
    public Scene getCurrentScene() {
        return sceneStack.get( getIndexOfTop() );
    }

    public void setGameCamera(GameCamera gameCamera) {
        this.gameCamera = gameCamera;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * MAKE LIST OF Scene.ID CURRENTLY IN SceneManager.sceneStack
     */
    public ArrayList<Scene.Id> retrieveSceneIdsFromSceneStack() {
        ArrayList<Scene.Id> sceneIdsFromSceneStack = new ArrayList<Scene.Id>();

        for (int i = 0; i < sceneStack.size(); i++) {
            Scene scene = sceneStack.get(i);
            sceneIdsFromSceneStack.add(scene.getSceneID());
            Log.d(MainActivity.DEBUG_TAG, "SceneManager.retrieveSceneIdsFromSceneStack() added: " + scene.getSceneID());
        }

        return sceneIdsFromSceneStack;
    }

    public void restoreSceneStack(ArrayList<Scene.Id> sceneIdsFromSceneStack) {
        // MUST CLEAR sceneStack (possible scenario of not loading from very beginning of game).
        sceneStack.clear();

        for (int i = 0; i < sceneIdsFromSceneStack.size(); i++) {
            Scene.Id id = sceneIdsFromSceneStack.get(i);

            Scene scene = sceneCollection.get(id);
            ///////////////////////////////
            scene.init(player, gameCamera);
            scene.enter();
            ///////////////////////////////

            sceneStack.add(scene);
        }
    }

}