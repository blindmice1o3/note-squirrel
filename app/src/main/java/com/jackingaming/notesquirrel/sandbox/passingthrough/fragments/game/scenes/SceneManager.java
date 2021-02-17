package com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes;

import android.app.Dialog;
import android.content.DialogInterface;
import android.view.SurfaceHolder;

import androidx.appcompat.app.AlertDialog;

import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.Game;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities.Player;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.pocketcritters.SceneHome01;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.pocketcritters.SceneHome02;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.pocketcritters.SceneHomeRival;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.pocketcritters.SceneLab;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.pocketcritters.SceneWorldMapPart01;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.poohfarmer.SceneChickenCoop;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.poohfarmer.SceneCowBarn;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.poohfarmer.SceneFarm;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.poohfarmer.SceneHothouse;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.poohfarmer.SceneHouseLevel01;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.poohfarmer.SceneSheepPen;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SceneManager
        implements Serializable {
    transient private Game game;

    private List<Scene> sceneStack;

    public SceneManager() {
        sceneStack = new ArrayList<Scene>();
        sceneStack.add(SceneWorldMapPart01.getInstance());
    }

    public void init(Game game) {
        this.game = game;

        for (Scene scene : sceneStack) {
            scene.init(game);
        }
    }

    public void update(long elapsed) {
        getCurrentScene().update(elapsed);
    }

    public void drawCurrentFrame(SurfaceHolder holder) {
        getCurrentScene().drawCurrentFrame(holder);
    }

    public void push(Scene newScene) {
        getCurrentScene().exit();

        newScene.getEntityManager().addEntity(Player.getInstance());
        if (newScene.getTileManager().getTiles() == null) {
            newScene.init(game);
        }
        sceneStack.add(newScene);

        getCurrentScene().enter();
    }

    public void pop() {
        getCurrentScene().exit();
        int indexOfLast = sceneStack.size() - 1;
        sceneStack.remove(indexOfLast);
        getCurrentScene().enter();
    }

    public Scene getCurrentScene() {
        int indexOfLast = sceneStack.size() - 1;
        return sceneStack.get(indexOfLast);
    }

    public void changeScene(String idOfCollidedTransferPoint) {
        if (getCurrentScene() instanceof SceneWorldMapPart01) {
            if (idOfCollidedTransferPoint.equals("HOME_01")) {
                push(SceneHome01.getInstance());
            } else if (idOfCollidedTransferPoint.equals("HOME_RIVAL")) {
                push(SceneHomeRival.getInstance());
            } else if (idOfCollidedTransferPoint.equals("LAB")) {
                push(SceneLab.getInstance());
            }
        } else if (getCurrentScene() instanceof SceneHome01) {
            if (idOfCollidedTransferPoint.equals("HOME_02")) {
                push(SceneHome02.getInstance());
            } else if (idOfCollidedTransferPoint.equals("PART_01")) {
                pop();
            }
        } else if (getCurrentScene() instanceof SceneHome02) {
            if (idOfCollidedTransferPoint.equals("HOME_01")) {
                pop();
            } else if (idOfCollidedTransferPoint.equals("FARM")) {
                push(SceneFarm.getInstance());
            }
        } else if (getCurrentScene() instanceof SceneHomeRival) {
            if (idOfCollidedTransferPoint.equals("PART_01")) {
                pop();
            }
        } else if (getCurrentScene() instanceof SceneLab) {
            if (idOfCollidedTransferPoint.equals("PART_01")) {
                pop();
            }
        } else if (getCurrentScene() instanceof SceneFarm) {
            if (idOfCollidedTransferPoint.equals("HOUSE_LEVEL_01")) {
                push(SceneHouseLevel01.getInstance());
            } else if (idOfCollidedTransferPoint.equals("HOTHOUSE")) {
                push(SceneHothouse.getInstance());
            } else if (idOfCollidedTransferPoint.equals("SHEEP_PEN")) {
                push(SceneSheepPen.getInstance());
            } else if (idOfCollidedTransferPoint.equals("CHICKEN_COOP")) {
                push(SceneChickenCoop.getInstance());
            } else if (idOfCollidedTransferPoint.equals("COW_BARN")) {
                push(SceneCowBarn.getInstance());
            } else if (idOfCollidedTransferPoint.equals("SEEDS_SHOP")) {
                // TODO:
                game.showSeedShopDialog();
            }
        } else if (getCurrentScene() instanceof SceneHouseLevel01) {
            if (idOfCollidedTransferPoint.equals("FARM")) {
                pop();
            }
        } else if (getCurrentScene() instanceof SceneChickenCoop) {
            if (idOfCollidedTransferPoint.equals("FARM")) {
                pop();
            }
        } else if (getCurrentScene() instanceof SceneCowBarn) {
            if (idOfCollidedTransferPoint.equals("FARM")) {
                pop();
            }
        } else if (getCurrentScene() instanceof SceneSheepPen) {
            if (idOfCollidedTransferPoint.equals("FARM")) {
                pop();
            }
        } else if (getCurrentScene() instanceof SceneHothouse) {
            if (idOfCollidedTransferPoint.equals("FARM")) {
                pop();
            }
        }
    }
}