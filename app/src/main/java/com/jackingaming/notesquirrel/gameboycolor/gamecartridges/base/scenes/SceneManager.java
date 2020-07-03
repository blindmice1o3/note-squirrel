package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes;

import android.util.Log;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCamera;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.moveable.Player;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.frogger.scenes.SceneFrogger;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.pocketcritters.scenes.indoors.SceneHome01;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.pocketcritters.scenes.indoors.SceneHome02;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.pocketcritters.scenes.indoors.SceneHomeRival;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.pocketcritters.scenes.indoors.SceneLab;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.pocketcritters.scenes.outdoors.ScenePart01;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.pong.scenes.ScenePong;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.scenes.indoors.SceneChickenCoop;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.scenes.indoors.SceneCowBarn;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.scenes.indoors.SceneHothouse;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.scenes.indoors.SceneHouseLevel01;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.scenes.indoors.SceneHouseLevel02;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.scenes.indoors.SceneHouseLevel03;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.scenes.indoors.SceneSheepPen;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.scenes.outdoors.SceneFarm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.Scene.Id.CHICKEN_COOP;
import static com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.Scene.Id.COW_BARN;
import static com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.Scene.Id.FARM;
import static com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.Scene.Id.FROGGER;
import static com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.Scene.Id.HOME_01;
import static com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.Scene.Id.HOME_02;
import static com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.Scene.Id.HOME_RIVAL;
import static com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.Scene.Id.HOTHOUSE;
import static com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.Scene.Id.HOUSE_01;
import static com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.Scene.Id.HOUSE_02;
import static com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.Scene.Id.HOUSE_03;
import static com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.Scene.Id.LAB;
import static com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.Scene.Id.PART_01;
import static com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.Scene.Id.PONG;
import static com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.Scene.Id.SHEEP_PEN;

public class SceneManager {

    private GameCartridge gameCartridge;

    private Player player;
    private GameCamera gameCamera;

    /////////////////////////////////////////////
    private Map<Scene.Id, Scene> sceneCollection;
    private List<Scene> sceneStack;
    /////////////////////////////////////////////

    public SceneManager(GameCartridge gameCartridge) {
        Log.d(MainActivity.DEBUG_TAG, "SceneManager(GameCartridge) constructor");

        this.gameCartridge = gameCartridge;

        //////////////////////////////////////////////////////////////////////////////////////////
        // Must call initSceneCollection() before Scene.init() (starting scene instantiated here).
        initSceneCollection();
        //////////////////////////////////////////////////////////////////////////////////////////

        player = gameCartridge.getPlayer();
        gameCamera = gameCartridge.getGameCamera();

        ////////////////////////////////////
        sceneStack = new ArrayList<Scene>();
        ////////////////////////////////////

        // Determine starting scene based on GameCartridge.Id.
        Scene.Id id = null;
        switch (gameCartridge.getIdGameCartridge()) {
            case POOH_FARMER:
                id = Scene.Id.FARM;
                break;
            case POCKET_CRITTERS:
                id = Scene.Id.PART_01;
                break;
            case PONG:
                id = Scene.Id.PONG;
                break;
            case FROGGER:
                id = Scene.Id.FROGGER;
                break;
            default:
                Log.d(MainActivity.DEBUG_TAG, "SceneManager() constructor's switch construct's default block.");
        }

        Scene startScene = sceneCollection.get(id);
        ////////////////////////////////////
        startScene.init(gameCartridge, player, gameCamera, this);
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

        /*
        //TODO: instead of instantiating all scenes from ALL games... only load what's needed.
        switch (handler.getGameCartridge().getIdGameCartridge()) {
            case POOH_FARMER:
                //store POOH_FARMER scenes
                break;
            case POCKET_CRITTERS:
                //store POCKET_CRITTERS scenes
                break;
            case FROGGER:
                //store FROGGER scenes
                break;
            default:
                Log.d(MainActivity.DEBUG_TAG, "SceneManager() constructor's switch construct's default block.");
        }
        */

        //TODO: redo this.
        // IMAGEs are not loaded yet (not until Scene.init() is called).
        // This should help with memory issues (on-the-fly loading).
        for (Scene.Id id : Scene.Id.values()) {
            if (id == FROGGER) {
                ///////////////////////////////////////////////////////
                sceneCollection.put(id, new SceneFrogger(gameCartridge, id));
                ///////////////////////////////////////////////////////
            } else if (id == PONG) {
                ////////////////////////////////////////////////////
                sceneCollection.put(id, new ScenePong(gameCartridge, id));
                ////////////////////////////////////////////////////
            } else if (id == FARM) {
                ////////////////////////////////////////////////////
                sceneCollection.put(id, new SceneFarm(gameCartridge, id));
                ////////////////////////////////////////////////////
            } else if (id == HOTHOUSE) {
                ////////////////////////////////////////////////////
                sceneCollection.put(id, new SceneHothouse(gameCartridge, id));
                ////////////////////////////////////////////////////
            } else if (id == SHEEP_PEN) {
                ////////////////////////////////////////////////////
                sceneCollection.put(id, new SceneSheepPen(gameCartridge, id));
                ////////////////////////////////////////////////////
            } else if (id == CHICKEN_COOP) {
                ////////////////////////////////////////////////////
                sceneCollection.put(id, new SceneChickenCoop(gameCartridge, id));
                ////////////////////////////////////////////////////
            } else if (id == COW_BARN) {
                ////////////////////////////////////////////////////
                sceneCollection.put(id, new SceneCowBarn(gameCartridge, id));
                ////////////////////////////////////////////////////
            } else if (id == HOUSE_01) {
                ////////////////////////////////////////////////////
                sceneCollection.put(id, new SceneHouseLevel01(gameCartridge, id));
                ////////////////////////////////////////////////////
            } else if (id == HOUSE_02) {
                ////////////////////////////////////////////////////
                sceneCollection.put(id, new SceneHouseLevel02(gameCartridge, id));
                ////////////////////////////////////////////////////
            } else if (id == HOUSE_03) {
                ////////////////////////////////////////////////////
                sceneCollection.put(id, new SceneHouseLevel03(gameCartridge, id));
                ////////////////////////////////////////////////////
            } else if (id == PART_01) {
                ////////////////////////////////////////////////////
                sceneCollection.put(id, new ScenePart01(gameCartridge, id));
                ////////////////////////////////////////////////////
            } else if (id == HOME_01) {
                ////////////////////////////////////////////////////
                sceneCollection.put(id, new SceneHome01(gameCartridge, id));
                ////////////////////////////////////////////////////
            } else if (id == HOME_02) {
                ////////////////////////////////////////////////////
                sceneCollection.put(id, new SceneHome02(gameCartridge, id));
                ////////////////////////////////////////////////////
            } else if (id == HOME_RIVAL) {
                ////////////////////////////////////////////////////
                sceneCollection.put(id, new SceneHomeRival(gameCartridge, id));
                ////////////////////////////////////////////////////
            } else if (id == LAB) {
                ////////////////////////////////////////////////////
                sceneCollection.put(id, new SceneLab(gameCartridge, id));
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
        if (nextScene.getTileMap().getTexture() == null) {
            ///////////////////////////////////
            nextScene.init(gameCartridge, player, gameCamera, this);
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
        if (getCurrentScene().getTileMap().getTexture() == null) {
            ///////////////////////////////////////////
            getCurrentScene().init(gameCartridge, player, gameCamera, this);
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

    public Scene getScene(Scene.Id id) {
        return sceneCollection.get(id);
    }

    public void putScene(Scene.Id id, Scene scene) {
        sceneCollection.put(id, scene);
    }

    public void setGameCamera(GameCamera gameCamera) {
        this.gameCamera = gameCamera;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Map<Scene.Id, Scene> getSceneCollection() {
        return sceneCollection;
    }

    public void setSceneCollection(Map<Scene.Id, Scene> sceneCollection) {
        this.sceneCollection = sceneCollection;
    }

    /**
     * MAKE LIST OF Scene.Id CURRENTLY IN SceneManager.sceneStack
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
            //////////////
            scene.enter();
            //////////////

            sceneStack.add(scene);
        }
    }

}