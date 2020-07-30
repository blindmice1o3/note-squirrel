package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base;

import android.content.Context;
import android.util.Log;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.SceneManager;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.states.GameState;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.states.StartMenuState;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.states.State;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.moveable.Player;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.Scene;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.states.TextboxState;
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
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.scenes.indoors.SceneSeedsShop;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.scenes.indoors.SceneSheepPen;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.poohfarmer.scenes.outdoors.SceneFarm;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SerializationDoer {

    public static final String FILE_NAME_VIA_OS = "savedFileViaOS.ser";

    public static void saveViaOS(GameCartridge gameCartridge) {
        Log.d(MainActivity.DEBUG_TAG, "SerializationDoer.saveViaOS(GameCartridge)");
        ///////////////////////////////////////////////////////////
        String fileName = "savedFileViaOS" +
                gameCartridge.getIdGameCartridge().name() + ".ser";
        ///////////////////////////////////////////////////////////
        saveWriteToFile(gameCartridge, fileName);
    }

    public static void loadViaOS(GameCartridge gameCartridge) {
        Log.d(MainActivity.DEBUG_TAG, "SerializationDoer.loadViaOS(GameCartridge)");
        ///////////////////////////////////////////////////////////
        String fileName = "savedFileViaOS" +
                gameCartridge.getIdGameCartridge().name() + ".ser";
        ///////////////////////////////////////////////////////////
        loadReadFromFile(gameCartridge, fileName);
    }

    //==============================================================================================

    public static void saveViaPlayerChoice(GameCartridge gameCartridge) {
        Log.d(MainActivity.DEBUG_TAG, "SerializationDoer.saveViaPlayerChoice(GameCartridge)");
        ///////////////////////////////////////////////////////////
        String fileName = "savedFileViaPlayerChoice" +
                gameCartridge.getIdGameCartridge().name() + ".ser";
        ///////////////////////////////////////////////////////////
        saveWriteToFile(gameCartridge, fileName);
    }

    public static void loadViaPlayerChoice(GameCartridge gameCartridge) {
        Log.d(MainActivity.DEBUG_TAG, "SerializationDoer.loadViaPlayerChoice(GameCartridge)");
        ///////////////////////////////////////////////////////////
        String fileName = "savedFileViaPlayerChoice" +
                gameCartridge.getIdGameCartridge().name() + ".ser";
        ///////////////////////////////////////////////////////////
        loadReadFromFile(gameCartridge, fileName);
    }

    //==============================================================================================

    private static void saveWriteToFile(GameCartridge gameCartridge, String fileName) {
        Log.d(MainActivity.DEBUG_TAG, "SerializationDoer.saveWriteToFile(GameCartridge, String): " + fileName);
        try {
            Log.d(MainActivity.DEBUG_TAG, "SerializationDoer.saveWriteToFile(GameCartridge, String) beginning.");
            ////////////////////////////////////////////////////////////////////////////////////////
            //FileOutputStream fs = new FileOutputStream("savedStateFile.ser");
            FileOutputStream fs = gameCartridge.getContext().openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            ////////////////////////////////////////////////////////////////////////////////////////



            //PLAYER, GAME_CAMERA, HEAD_UP_DISPLAY
            //////////////////////////////////////////////
            os.writeObject(gameCartridge.getGameCamera());
            os.writeObject(gameCartridge.getPlayer());
            os.writeObject(gameCartridge.getHeadUpDisplay());
            os.writeObject(gameCartridge.getTimeManager());
            //////////////////////////////////////////////



            //STATE (stateCollection)
            int sizeOfStateCollection = gameCartridge.getStateManager().getStateCollection().size();
            ///////////////////////////////////
            os.writeInt(sizeOfStateCollection);
            ///////////////////////////////////
            for (int i = 0; i < sizeOfStateCollection; i++) {
                State.Id id = State.Id.values()[i];
                switch (id) {
                    case GAME:
                        GameState gameState = (GameState) gameCartridge.getStateManager().getStateCollection().get(id);
                        Log.d(MainActivity.DEBUG_TAG, "SerializationDoer.saveWriteToFile(GameCartridge, String) saving state that has id: " + id);
                        //////////////////////////
                        os.writeObject(gameState);
                        //////////////////////////
                        break;
                    case START_MENU:
                        StartMenuState startMenuState = (StartMenuState) gameCartridge.getStateManager().getStateCollection().get(id);
                        Log.d(MainActivity.DEBUG_TAG, "SerializationDoer.saveWriteToFile(GameCartridge, String) saving state that has id: " + id);
                        ///////////////////////////////
                        os.writeObject(startMenuState);
                        ///////////////////////////////
                        break;
                    case TEXTBOX:
                        TextboxState textboxState = (TextboxState) gameCartridge.getStateManager().getStateCollection().get(id);
                        Log.d(MainActivity.DEBUG_TAG, "SerializationDoer.saveWriteToFile(GameCartridge, String) saving state that has id: " + id);
                        /////////////////////////////
                        os.writeObject(textboxState);
                        /////////////////////////////
                        break;
                    default:
                        Log.d(MainActivity.DEBUG_TAG, "@@@@@SerializationDoer.saveWriteToFile(GameCartridge, String) switch (State.Id) construct's default block.@@@@@");
                        break;
                }
            }

            //SCENE (sceneCollection)
            int sizeOfSceneCollection = ((GameState) gameCartridge.getStateManager().getStateCollection().get(State.Id.GAME)).getSceneManager().getSceneCollection().size();
            ///////////////////////////////////
            os.writeInt(sizeOfSceneCollection);
            ///////////////////////////////////
            for (int i = 0; i < sizeOfSceneCollection; i++) {
                Scene.Id id = Scene.Id.values()[i];
                Scene scene = ((GameState) gameCartridge.getStateManager().getStateCollection().get(State.Id.GAME)).getSceneManager().getScene(id);

                Log.d(MainActivity.DEBUG_TAG, "SerializationDoer.saveWriteToFile(GameCartridge, String) saving scene that has id: " + id);
                //////////////////////
                os.writeObject(scene);
                //////////////////////
            }



            //STATE_MANAGER (list of State.Id from stateStack)
            ArrayList<State.Id> stateIdsFromStateStack = gameCartridge.getStateManager().retrieveStateIdsFromStateStack();
            ///////////////////////////////////////
            os.writeObject(stateIdsFromStateStack);
            ///////////////////////////////////////

            //SCENE_MANAGER (list of Scene.Id from sceneStack)
            //((GameState)stateManager.getState(State.Id.GAME)).getSceneManager()
            ArrayList<Scene.Id> sceneIdsFromSceneStack = gameCartridge.getSceneManager().retrieveSceneIdsFromSceneStack();
            ///////////////////////////////////////
            os.writeObject(sceneIdsFromSceneStack);
            ///////////////////////////////////////



            Log.d(MainActivity.DEBUG_TAG, "SerializationDoer.saveWriteToFile(GameCartridge, String) closing: " + fileName);
            ///////////
            os.close();
            fs.close();
            ///////////
            Log.d(MainActivity.DEBUG_TAG, "SerializationDoer.saveWriteToFile(GameCartridge, String) ending.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadReadFromFile(GameCartridge gameCartridge, String fileName) {
        Log.d(MainActivity.DEBUG_TAG, "SerializationDoer.loadReadFromFile(GameCartridge, String): " + fileName);
        try {
            Log.d(MainActivity.DEBUG_TAG, "SerializationDoer.loadReadFromFile(GameCartridge, String) beginning.");
            ////////////////////////////////////////////////////////////////////////////////////////
            FileInputStream fi = gameCartridge.getContext().openFileInput(fileName);
            ObjectInputStream os = new ObjectInputStream(fi);
            ////////////////////////////////////////////////////////////////////////////////////////



            ///////////////////////////////////////////////////////////////////////////////////
            GameCamera gameCamera = (GameCamera) os.readObject();
            Player player = (Player) os.readObject();
            //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
            player.setName("EeyoreDeserialized");
            //TODO: [BUG] loading of ACTUAL/CURRENT position being overwritten
            // when Scene.restoreSceneStack(ArrayList<Scene.Id>) (it calls
            // Scene.enter() which sets player's position to xPriorScene and
            // yPriorScene) gets called.
            //Will restore ACTUAL/CURRENT position at the end of this loading method.
            float xCurrent = player.getxCurrent();
            float yCurrent = player.getyCurrent();
            //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
            HeadUpDisplay headUpDisplay = (HeadUpDisplay) os.readObject();
            TimeManager timeManager = (TimeManager) os.readObject();
            ///////////////////////////////////////////////////////////////////////////////////

            //PLAYER, GAME_CAMERA, HEAD_UP_DISPLAY
            player.init(gameCartridge);
            gameCamera.setEntity(player);

            //GAME_CARTRIDGE
            gameCartridge.setGameCamera(gameCamera);
            gameCartridge.setPlayer(player);

            //!!!THIS MUST COME AFTER gameCartridge.setPlayer()!!!
            headUpDisplay.init(gameCartridge);
            gameCartridge.setHeadUpDisplay(headUpDisplay);
            timeManager.init(gameCartridge);
            gameCartridge.setTimeManager(timeManager);



            //STATE (stateCollection)
            int sizeOfStateCollection = (int) os.readInt();
            Map<State.Id, State> stateCollection = new HashMap<State.Id, State>();
            GameState gameState = null;
            for (int i = 0; i < sizeOfStateCollection; i++) {
                State.Id id = State.Id.values()[i];
                switch (id) {
                    case GAME:
                        //////////////////////////////////////////////////
                        gameState = (GameState) os.readObject();
                        //////////////////////////////////////////////////
                        gameState.init(gameCartridge);
                        ///////////////////////////////////
                        stateCollection.put(id, gameState);
                        ///////////////////////////////////
                        break;
                    case START_MENU:
                        /////////////////////////////////////////////////////////////////
                        StartMenuState startMenuState = (StartMenuState) os.readObject();
                        /////////////////////////////////////////////////////////////////
                        gameState = (GameState) stateCollection.get(State.Id.GAME);
                        startMenuState.init(gameCartridge);
                        ////////////////////////////////////////
                        stateCollection.put(id, startMenuState);
                        ////////////////////////////////////////
                        break;
                    case TEXTBOX:
                        ///////////////////////////////////////////////////////////
                        TextboxState textboxState = (TextboxState) os.readObject();
                        ///////////////////////////////////////////////////////////
                        gameState = (GameState) stateCollection.get(State.Id.GAME);
                        textboxState.init(gameCartridge);
                        //////////////////////////////////////
                        stateCollection.put(id, textboxState);
                        //////////////////////////////////////
                        break;
                    default:
                        Log.d(MainActivity.DEBUG_TAG, "@@@@@SerializationDoer.loadReadFromFile(GameCartridge, String) switch (State.Id) construct's default block.@@@@@");
                        break;
                }
            }
            ////////////////////////////////////////////////////////////////////
            gameCartridge.getStateManager().setStateCollection(stateCollection);
            ////////////////////////////////////////////////////////////////////

            //SCENE (sceneCollection)
            int sizeOfSceneCollection = (int) os.readInt();
            SceneManager sceneManager = ((GameState)gameCartridge.getStateManager().getState(State.Id.GAME)).getSceneManager();
            Map<Scene.Id, Scene> sceneCollection = new HashMap<Scene.Id, Scene>();
            for (int i = 0; i < sizeOfSceneCollection; i++) {
                Scene.Id id = Scene.Id.values()[i];
                switch (id) {
                    case FROGGER:
                        SceneFrogger sceneFrogger = (SceneFrogger) os.readObject();
                        sceneFrogger.init(gameCartridge, player, gameCamera, sceneManager);
                        //////////////////////////////////////
                        sceneCollection.put(id, sceneFrogger);
                        //////////////////////////////////////
                        break;
                    case PONG:
                        ScenePong scenePong = (ScenePong) os.readObject();
                        scenePong.init(gameCartridge, player, gameCamera, sceneManager);
                        //////////////////////////////////////
                        sceneCollection.put(id, scenePong);
                        //////////////////////////////////////
                        break;
                    case FARM:
                        SceneFarm sceneFarm = (SceneFarm) os.readObject();
                        sceneFarm.init(gameCartridge, player, gameCamera, sceneManager);
                        ////////////////////////////////////////////////////
                        sceneCollection.put(id, sceneFarm);
                        ////////////////////////////////////////////////////
                        break;
                    case HOTHOUSE:
                        SceneHothouse sceneHothouse = (SceneHothouse) os.readObject();
                        sceneHothouse.init(gameCartridge, player, gameCamera, sceneManager);
                        ////////////////////////////////////////////////////
                        sceneCollection.put(id, sceneHothouse);
                        ////////////////////////////////////////////////////
                        break;
                    case SHEEP_PEN:
                        SceneSheepPen sceneSheepPen = (SceneSheepPen) os.readObject();
                        sceneSheepPen.init(gameCartridge, player, gameCamera, sceneManager);
                        ////////////////////////////////////////////////////
                        sceneCollection.put(id, sceneSheepPen);
                        ////////////////////////////////////////////////////
                        break;
                    case CHICKEN_COOP:
                        SceneChickenCoop sceneChickenCoop = (SceneChickenCoop) os.readObject();
                        sceneChickenCoop.init(gameCartridge, player, gameCamera, sceneManager);
                        ////////////////////////////////////////////////////
                        sceneCollection.put(id, sceneChickenCoop);
                        ////////////////////////////////////////////////////
                        break;
                    case COW_BARN:
                        SceneCowBarn sceneCowBarn = (SceneCowBarn) os.readObject();
                        sceneCowBarn.init(gameCartridge, player, gameCamera, sceneManager);
                        ////////////////////////////////////////////////////
                        sceneCollection.put(id, sceneCowBarn);
                        ////////////////////////////////////////////////////
                        break;
                    case SEEDS_SHOP:
                        SceneSeedsShop sceneSeedsShop = (SceneSeedsShop) os.readObject();
                        sceneSeedsShop.init(gameCartridge, player, gameCamera, sceneManager);
                        ////////////////////////////////////////////////////
                        sceneCollection.put(id, sceneSeedsShop);
                        ////////////////////////////////////////////////////
                        break;
                    case HOUSE_01:
                        SceneHouseLevel01 sceneHouseLevel01 = (SceneHouseLevel01) os.readObject();
                        sceneHouseLevel01.init(gameCartridge, player, gameCamera, sceneManager);
                        ////////////////////////////////////////////////////
                        sceneCollection.put(id, sceneHouseLevel01);
                        ////////////////////////////////////////////////////
                        break;
                    case HOUSE_02:
                        SceneHouseLevel02 sceneHouseLevel02 = (SceneHouseLevel02) os.readObject();
                        sceneHouseLevel02.init(gameCartridge, player, gameCamera, sceneManager);
                        ////////////////////////////////////////////////////
                        sceneCollection.put(id, sceneHouseLevel02);
                        ////////////////////////////////////////////////////
                        break;
                    case HOUSE_03:
                        SceneHouseLevel03 sceneHouseLevel03 = (SceneHouseLevel03) os.readObject();
                        sceneHouseLevel03.init(gameCartridge, player, gameCamera, sceneManager);
                        ////////////////////////////////////////////////////
                        sceneCollection.put(id, sceneHouseLevel03);
                        ////////////////////////////////////////////////////
                        break;
                    case PART_01:
                        ScenePart01 scenePart01 = (ScenePart01) os.readObject();
                        scenePart01.init(gameCartridge, player, gameCamera, sceneManager);
                        ////////////////////////////////////////////////////
                        sceneCollection.put(id, scenePart01);
                        ////////////////////////////////////////////////////
                        break;
                    case HOME_01:
                        SceneHome01 sceneHome01 = (SceneHome01) os.readObject();
                        sceneHome01.init(gameCartridge, player, gameCamera, sceneManager);
                        ////////////////////////////////////////////////////
                        sceneCollection.put(id, sceneHome01);
                        ////////////////////////////////////////////////////
                        break;
                    case HOME_02:
                        SceneHome02 sceneHome02 = (SceneHome02) os.readObject();
                        sceneHome02.init(gameCartridge, player, gameCamera, sceneManager);
                        ////////////////////////////////////////////////////
                        sceneCollection.put(id, sceneHome02);
                        ////////////////////////////////////////////////////
                        break;
                    case HOME_RIVAL:
                        SceneHomeRival sceneHomeRival = (SceneHomeRival) os.readObject();
                        sceneHomeRival.init(gameCartridge, player, gameCamera, sceneManager);
                        ////////////////////////////////////////////////////
                        sceneCollection.put(id, sceneHomeRival);
                        ////////////////////////////////////////////////////
                        break;
                    case LAB:
                        SceneLab sceneLab = (SceneLab) os.readObject();
                        sceneLab.init(gameCartridge, player, gameCamera, sceneManager);
                        ////////////////////////////////////////////////////
                        sceneCollection.put(id, sceneLab);
                        ////////////////////////////////////////////////////
                        break;
                    default:
                        Log.d(MainActivity.DEBUG_TAG, "@@@@@SerializationDoer.loadReadFromFile(GameCartridge, String) switch (Scene.Id) construct's default block.@@@@@");
                        break;
                }
            }
            /////////////////////////////////////////////////
            sceneManager.setSceneCollection(sceneCollection);
            /////////////////////////////////////////////////



            //STATE_MANAGER (list of states from stateStack)
            ArrayList<State.Id> stateIdsFromStateStack = (ArrayList<State.Id>) os.readObject();
            gameCartridge.getStateManager().restoreStateStack(stateIdsFromStateStack);

            //SCENE_MANAGER (list of scenes from sceneStack)
            ArrayList<Scene.Id> sceneIdsFromSceneStack = (ArrayList<Scene.Id>) os.readObject();
            ((GameState)gameCartridge.getStateManager().getStateCollection().get(State.Id.GAME)).getSceneManager().setGameCamera(gameCamera);
            ((GameState)gameCartridge.getStateManager().getStateCollection().get(State.Id.GAME)).getSceneManager().setPlayer(player);
            ((GameState)gameCartridge.getStateManager().getStateCollection().get(State.Id.GAME)).getSceneManager().restoreSceneStack(sceneIdsFromSceneStack);




            //[FIX] position-bug, player's ACTUAL/CURRENT was set to xPriorScene and yPriorScene.
            //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
            gameCartridge.getPlayer().setxCurrent(xCurrent);
            gameCartridge.getPlayer().setyCurrent(yCurrent);
            //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@



            Log.d(MainActivity.DEBUG_TAG, "SerializationDoer.loadReadFromFile(GameCartridge, String) closing: " + fileName);
            ////////////
            os.close();
            fi.close();
            ////////////
            Log.d(MainActivity.DEBUG_TAG, "SerializationDoer.loadReadFromFile(GameCartridge, String) ending.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}