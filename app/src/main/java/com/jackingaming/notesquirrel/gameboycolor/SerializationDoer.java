package com.jackingaming.notesquirrel.gameboycolor;

import android.content.Context;
import android.util.Log;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.Handler;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.pocketcritters.states.GameState;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.pocketcritters.states.State;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.entities.Player;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.scenes.GameCamera;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.scenes.Scene;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class SerializationDoer {

    public static void saveWriteToFile(GameCartridge gameCartridge) {
        Log.d(MainActivity.DEBUG_TAG, "SerializationDoer.saveWriteToFile(Handler)");
        try {
            Log.d(MainActivity.DEBUG_TAG, "SerializationDoer.saveWriteToFile(Handler) beginning.");

            //FileOutputStream fs = new FileOutputStream("savedStateFile.ser");
            ////////////////////////////////////////////////////////////////////////////////////////
            FileOutputStream fs = gameCartridge.getContext().openFileOutput("savedStateFile.ser", Context.MODE_PRIVATE);
            ////////////////////////////////////////////////////////////////////////////////////////
            ObjectOutputStream os = new ObjectOutputStream(fs);
            Log.d(MainActivity.DEBUG_TAG, "SerializationDoer.saveWriteToFile(Handler) opened \"savedStateFile.ser\".");




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

                //////////////////////
                os.writeObject(scene);
                //////////////////////
            }



            Log.d(MainActivity.DEBUG_TAG, "SerializationDoer.saveWriteToFile(Handler) closing \"savedStateFile.ser\".");
            ////////////
            os.close();
            ////////////

            ///////////
            fs.close();
            ///////////
            Log.d(MainActivity.DEBUG_TAG, "SerializationDoer.saveWriteToFile(Handler) ending.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadReadFromFile(GameCartridge gameCartridge) {
        Log.d(MainActivity.DEBUG_TAG, "SerializationDoer.loadReadFromFile(Handler)");
        try {
            Log.d(MainActivity.DEBUG_TAG, "SerializationDoer.loadReadFromFile(Handler) beginning.");

            ////////////////////////////////////////////////////////////////////////////////////////
            FileInputStream fi = gameCartridge.getContext().openFileInput("savedStateFile.ser");
            ////////////////////////////////////////////////////////////////////////////////////////
            ObjectInputStream os = new ObjectInputStream(fi);
            Log.d(MainActivity.DEBUG_TAG, "SerializationDoer.loadReadFromFile(Handler) opened \"savedStateFile.ser\".");

            ///////////////////////////////////////////////////////////////////////////////////
            GameCamera gameCamera = (GameCamera) os.readObject();
            Player player = (Player) os.readObject();
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

            //SCENE (CURRENT)
            for (int i = 0; i < sceneIdsFromSceneStack.size(); i++) {
                Scene.Id id = sceneIdsFromSceneStack.get(i);
                //////////////////////////////////////
                Scene scene = (Scene) os.readObject();
                //////////////////////////////////////
                scene.setHandler(handler);

                gameCartridge.getSceneManager().putScene(id, scene);
            }
            handler.getGameCartridge().getSceneManager().restoreSceneStack(sceneIdsFromSceneStack,
                    handler, gameCamera, player);

            //STATE (/STATE_MANAGER)
            State gameState = handler.getGameCartridge().getStateManager().getState(State.Id.GAME);
            ((GameState)gameState).setPlayer(player);



            Log.d(MainActivity.DEBUG_TAG, "SerializationDoer.loadReadFromFile(Handler) closing \"savedStateFile.ser\".");
            ////////////
            os.close();
            ////////////

            ////////////
            fi.close();
            ////////////
            Log.d(MainActivity.DEBUG_TAG, "SerializationDoer.loadReadFromFile(Handler) ending.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}