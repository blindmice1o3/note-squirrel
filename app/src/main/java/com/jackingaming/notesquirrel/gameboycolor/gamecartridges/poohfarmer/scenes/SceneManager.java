package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.scenes;

import android.content.Context;
import android.util.Log;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.Handler;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.entities.EntityManager;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.entities.Player;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.tiles.TileMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SceneManager {

    private Handler handler;

    private Map<Scene.Id, Scene> sceneCollection;

    private Player player;
    private GameCamera gameCamera;
    ///////////////////////////////
    private List<Scene> sceneStack;
    ///////////////////////////////

    public SceneManager(Handler handler) {
        Log.d(MainActivity.DEBUG_TAG, "SceneManager(Handler) constructor");
    //public SceneManager(Context context, int widthViewport, int heightViewport,
    //                    GameCartridge.Id cartridgeID, Player player, GameCamera gameCamera) {
    //    Log.d(MainActivity.DEBUG_TAG, "SceneManager(Context, int, int, GameCartridge.Id, Player, GameCamera) constructor... cartridgeID: " + cartridgeID.name());
        this.handler = handler;

        //////////////////////////////////////////////////////////////////////////////////////////
        // Must call initSceneCollection() before Scene.init() (starting scene instantiated here).
        initSceneCollection();
        //initSceneCollection(
        //        handler.getGameCartridge().getContext(),
        //        handler.getGameCartridge().getWidthViewport(),
        //        handler.getGameCartridge().getHeightViewport() );
        //initSceneCollection(context, widthViewport, heightViewport);
        //////////////////////////////////////////////////////////////////////////////////////////

        player = handler.getGameCartridge().getPlayer();
        //this.player = player;
        gameCamera = handler.getGameCartridge().getGameCamera();
        //this.gameCamera = gameCamera;

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
                id = Scene.Id.FROGGER;
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
    private void initSceneCollection() {
    //private void initSceneCollection(Context context, int widthViewport, int heightViewport) {
        Log.d(MainActivity.DEBUG_TAG, "SceneManager.initSceneCollection()");
        //Log.d(MainActivity.DEBUG_TAG, "SceneManager.initSceneCollection(Context, int, int)");

        /////////////////////////////////////////////////
        sceneCollection = new HashMap<Scene.Id, Scene>();
        /////////////////////////////////////////////////

        // IMAGEs are not loaded yet (not until Scene.init() is called).
        // This should help with memory issues (on-the-fly loading).
        for (Scene.Id id : Scene.Id.values()) {
            sceneCollection.put(id, new Scene(handler, id));
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

    public void savePresentState() {
        for (Scene scene : sceneCollection.values()) {
            scene.savePresentState();
        }
        for (int i = 0; i < sceneStack.size(); i++) {
            //TODO: write to file.
        }
    }

    public void loadSavedState() {
        //TODO: restore every scene in sceneCollection.
        //TODO: restore sceneStack.
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

    public void restoreSceneStack(ArrayList<Scene.Id> sceneIdsFromSceneStack,
                                  Handler handler, GameCamera gameCamera, Player player) {

        for (int i = 0; i < sceneIdsFromSceneStack.size(); i++) {
            Scene.Id id = sceneIdsFromSceneStack.get(i);
            Scene scene = sceneCollection.get(id);

            //!!!DON'T ADD THE INITIAL SCENE TO sceneStack (it's already there)!!!
            if ((sceneStack.size() == 1) && (id == Scene.Id.PART_01)) {
                Log.d(MainActivity.DEBUG_TAG, "SceneManager.restoreSceneStack(ArrayList<Scene.Id>, GameCamera, Player) INITIAL SCENE... don't add to stack");
                scene.setGameCamera(gameCamera);
                scene.setPlayer(player);

                EntityManager entityManager = scene.getEntityManager();
                if (entityManager != null) {
                    entityManager.setHandler(handler);
                    entityManager.removePreviousPlayer();
                    entityManager.setPlayer(player);
                    entityManager.addEntity(player);
                } else {
                    Log.d(MainActivity.DEBUG_TAG, "SceneManager.restoreSceneStack(ArrayList<Scene.Id>, GameCamera, Player) INITIAL SCENE... entityManager is null.");
                }

                TileMap tileMap = scene.getTileMap();
                if (tileMap != null) {
                    tileMap.setHandler(handler);
                } else {
                    Log.d(MainActivity.DEBUG_TAG, "SceneManager.restoreSceneStack(ArrayList<Scene.Id>, GameCamera, Player) INITIAL SCENE... tileMap is null.");
                }
            } else {
                scene.init(player, gameCamera);
                scene.getEntityManager().setHandler(handler);
                scene.getTileMap().setHandler(handler);
                sceneStack.add(scene);
            }
        }



    }

}