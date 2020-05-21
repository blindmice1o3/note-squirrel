package com.jackingaming.notesquirrel.gameboycolor;

import android.content.Context;
import android.util.Log;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.Handler;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.entities.Entity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.entities.EntityManager;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.entities.Player;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.poohfarmer.scenes.GameCamera;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializationDoer {


    public static void saveWriteToFile(Handler handler) {
        Log.d(MainActivity.DEBUG_TAG, "SerializationDoer.saveWriteToFile(Handler)");
        try {
            Log.d(MainActivity.DEBUG_TAG, "SerializationDoer.saveWriteToFile(Handler) beginning.");

            //FileOutputStream fs = new FileOutputStream("savedStateFile.ser");
            ////////////////////////////////////////////////////////////////////////////////////////
            FileOutputStream fs = handler.getGameCartridge().getContext().openFileOutput("savedStateFile.ser", Context.MODE_PRIVATE);
            ////////////////////////////////////////////////////////////////////////////////////////
            ObjectOutputStream os = new ObjectOutputStream(fs);
            Log.d(MainActivity.DEBUG_TAG, "SerializationDoer.saveWriteToFile(Handler) opened \"savedStateFile.ser\".");



            os.writeObject(handler.getGameCartridge().getGameCamera());
            Log.d(MainActivity.DEBUG_TAG, "SerializationDoer.saveWriteToFile(Handler) serialized GameCamera.");
            os.writeObject(handler.getGameCartridge().getPlayer());
            Log.d(MainActivity.DEBUG_TAG, "SerializationDoer.saveWriteToFile(Handler) serialized Player.");



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

    public static void loadReadFromFile(Handler handler) {
        Log.d(MainActivity.DEBUG_TAG, "SerializationDoer.loadReadFromFile(Handler)");
        try {
            Log.d(MainActivity.DEBUG_TAG, "SerializationDoer.loadReadFromFile(Handler) beginning.");

            //FileInputStream fi = new FileInputStream("savedStateFile.ser");
            Log.d(MainActivity.DEBUG_TAG, "is Handler null? " + handler);
            Log.d(MainActivity.DEBUG_TAG, "is GameCartridge null? " + handler.getGameCartridge());
            Log.d(MainActivity.DEBUG_TAG, "is Context null? " + handler.getGameCartridge().getContext());
            ////////////////////////////////////////////////////////////////////////////////////////
            FileInputStream fi = handler.getGameCartridge().getContext().openFileInput("savedStateFile.ser");
            ////////////////////////////////////////////////////////////////////////////////////////
            ObjectInputStream os = new ObjectInputStream(fi);
            Log.d(MainActivity.DEBUG_TAG, "SerializationDoer.loadReadFromFile(Handler) opened \"savedStateFile.ser\".");



            handler.getGameCartridge().setGameCamera( (GameCamera)os.readObject() );
            Log.d(MainActivity.DEBUG_TAG, "SerializationDoer.loadReadFromFile(Handler) de-serialized GameCamera.");
            handler.getGameCartridge().setPlayer( (Player)os.readObject() );
            Log.d(MainActivity.DEBUG_TAG, "SerializationDoer.loadReadFromFile(Handler) de-serialized Player.");
            ///////////////////////////////////////////////////////////////
            handler.getGameCartridge().getGameCamera().setHandler(handler);
            handler.getGameCartridge().getPlayer().setHandler(handler);
            handler.getGameCartridge().getPlayer().init();

            GameCamera gameCamera = handler.getGameCartridge().getGameCamera();
            Player player = handler.getGameCartridge().getPlayer();

            handler.getGameCartridge().getSceneManager().setGameCamera(gameCamera);
            handler.getGameCartridge().getSceneManager().getCurrentScene().setGameCamera(gameCamera);
            handler.getGameCartridge().getSceneManager().setPlayer(player);
            handler.getGameCartridge().getSceneManager().getCurrentScene().setPlayer(player);

            EntityManager entityManager = handler.getGameCartridge().getSceneManager().getCurrentScene().getEntityManager();
            for (Entity e : entityManager.getEntities()) {
                if (e instanceof Player) {
                    e.setActive(false);
                }
            }
            entityManager.update(0L);
            entityManager.setPlayer(player);
            entityManager.addEntity(player);
            ///////////////////////////////////////////////////////////////



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