package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base;

import android.content.Context;
import android.util.Log;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.states.GameState;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.states.State;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.entities.Player;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.scenes.Scene;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class SerializationDoer {

    public static void saveWriteToFile(GameCartridge gameCartridge, boolean isViaPlayerChoice) {

        String fileName = (isViaPlayerChoice) ? ("savedStateFileViaMenu") : ("savedStateFileViaOS.ser");

        Log.d(MainActivity.DEBUG_TAG, "SerializationDoer.saveWriteToFile(GameCartridge, boolean): " + fileName);
        try {
            Log.d(MainActivity.DEBUG_TAG, "SerializationDoer.saveWriteToFile(GameCartridge, boolean) beginning.");
            ////////////////////////////////////////////////////////////////////////////////////////
            //FileOutputStream fs = new FileOutputStream("savedStateFile.ser");
            FileOutputStream fs = gameCartridge.getContext().openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            ////////////////////////////////////////////////////////////////////////////////////////



            //PLAYER AND GAME_CAMERA
            //////////////////////////////////////////////
            os.writeObject(gameCartridge.getGameCamera());
            os.writeObject(gameCartridge.getPlayer());
            //////////////////////////////////////////////

            //SCENE_MANAGER (list of scenes from sceneStack)
            ArrayList<Scene.Id> sceneIdsFromSceneStack = gameCartridge.getSceneManager().retrieveSceneIdsFromSceneStack();
            ///////////////////////////////////////
            os.writeObject(sceneIdsFromSceneStack);
            ///////////////////////////////////////

            //SCENE
            for (int i = 0; i < sceneIdsFromSceneStack.size(); i++) {
                Scene.Id id = sceneIdsFromSceneStack.get(i);
                Scene scene = gameCartridge.getSceneManager().getScene(id);
                Log.d(MainActivity.DEBUG_TAG, "SerializationDoer.saveWriteToFile(GameCartridge, boolean) saving scene that has id: " + id);

                //////////////////////
                os.writeObject(scene);
                //////////////////////
            }

            //TODO: STATE_MANAGER (list of states from stateStack)

            //TODO: STATE



            Log.d(MainActivity.DEBUG_TAG, "SerializationDoer.saveWriteToFile(GameCartridge, boolean) closing: " + fileName);
            ///////////
            os.close();
            fs.close();
            ///////////
            Log.d(MainActivity.DEBUG_TAG, "SerializationDoer.saveWriteToFile(GameCartridge, boolean) ending.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadReadFromFile(GameCartridge gameCartridge, boolean isViaPlayerChoice) {

        String fileName = (isViaPlayerChoice) ? ("savedStateFileViaMenu") : ("savedStateFileViaOS.ser");

        Log.d(MainActivity.DEBUG_TAG, "SerializationDoer.loadReadFromFile(GameCartridge, boolean): " + fileName);
        try {
            Log.d(MainActivity.DEBUG_TAG, "SerializationDoer.loadReadFromFile(GameCartridge, boolean) beginning.");
            ////////////////////////////////////////////////////////////////////////////////////////
            FileInputStream fi = gameCartridge.getContext().openFileInput(fileName);
            ObjectInputStream os = new ObjectInputStream(fi);
            ////////////////////////////////////////////////////////////////////////////////////////


            ///////////////////////////////////////////////////////////////////////////////////
            GameCamera gameCamera = (GameCamera) os.readObject();
            Player player = (Player) os.readObject();
            //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
            player.setName("EeyoreDeserialized");
            //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
            ArrayList<Scene.Id> sceneIdsFromSceneStack = (ArrayList<Scene.Id>) os.readObject();
            ///////////////////////////////////////////////////////////////////////////////////

            Handler handler = gameCartridge.getHandler();

            //HANDLER
            gameCamera.setHandler(handler);
            player.setHandler(handler);

            //PLAYER AND GAME_CAMERA
            player.init();
            player.setGameCamera(gameCamera);
            gameCamera.setEntity(player);

            //GAME_CARTRIDGE
            gameCartridge.setGameCamera(gameCamera);
            gameCartridge.setPlayer(player);

            //SCENE_MANAGER
            gameCartridge.getSceneManager().setGameCamera(gameCamera);
            gameCartridge.getSceneManager().setPlayer(player);
            //////////////////////////////////////////////////////
            gameCartridge.getSceneManager().initSceneCollection();
            //////////////////////////////////////////////////////

            //SCENE (CURRENT)
            for (int i = 0; i < sceneIdsFromSceneStack.size(); i++) {
                Scene.Id id = sceneIdsFromSceneStack.get(i);
                //////////////////////////////////////
                Scene scene = (Scene) os.readObject();
                //////////////////////////////////////
                Log.d(MainActivity.DEBUG_TAG, "SerializationDoer.loadReadFromFile(GameCartridge, boolean) loading scene that has id: " + id);
                scene.setHandler(handler);
//                scene.setGameCamera(gameCamera);
//                scene.setPlayer(player);
//                scene.initEntityManager(player);
//                scene.initTileMap();

                // Write-over the key-value pair of SceneManager.sceneCollection.
                gameCartridge.getSceneManager().putScene(id, scene);
            }
            handler.getGameCartridge().getSceneManager().restoreSceneStack(sceneIdsFromSceneStack);

            //STATE (/STATE_MANAGER)
            State gameState = handler.getGameCartridge().getStateManager().getState(State.Id.GAME);
            ((GameState)gameState).setPlayer(player);



            Log.d(MainActivity.DEBUG_TAG, "SerializationDoer.loadReadFromFile(GameCartridge, boolean) closing: " + fileName);
            ////////////
            os.close();
            fi.close();
            ////////////
            Log.d(MainActivity.DEBUG_TAG, "SerializationDoer.loadReadFromFile(GameCartridge, boolean) ending.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}