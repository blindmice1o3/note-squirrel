package com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes;

import android.graphics.Canvas;

import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.Game;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities.player.Player;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.evo.SceneEvo;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.frogger.SceneFrogger;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.pocketcritters.SceneHome01;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.pocketcritters.SceneHome02;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.pocketcritters.SceneHomeRival;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.pocketcritters.SceneLab;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.pocketcritters.SceneWorldMapPart01;
import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.pong.ScenePong;
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

    public SceneManager(String gameTitle) {
        sceneStack = new ArrayList<Scene>();

        switch (gameTitle) {
            case "Pocket Critters":
                sceneStack.add(SceneWorldMapPart01.getInstance());
                break;
            case "Pooh Farmer":
                sceneStack.add(SceneFarm.getInstance());
                break;
            case "Evo":
                sceneStack.add(SceneEvo.getInstance());
                break;
            case "Frogger":
                sceneStack.add(SceneFrogger.getInstance());
                break;
            case "Pong":
                sceneStack.add(ScenePong.getInstance());
                break;
            default:
                sceneStack.add(SceneWorldMapPart01.getInstance());
                break;
        }

    }

    public void init(Game game) {
        this.game = game;

        for (int i = 0; i < sceneStack.size(); i++) {
            Scene scene = sceneStack.get(i);

            scene.init(game);
            scene.enter();
        }
    }

    public void update(long elapsed) {
        getCurrentScene().update(elapsed);
    }

    public void drawCurrentFrame(Canvas canvas) {
        getCurrentScene().drawCurrentFrame(canvas);
    }

    private int getIndexOfTop() {
        return sceneStack.size() - 1;
    }

    public Scene getCurrentScene() {
        return sceneStack.get( getIndexOfTop() );
    }

    public void pop() {
        Scene sceneCurrent = getCurrentScene();
        sceneCurrent.exit();

        sceneStack.remove(sceneCurrent);

        getCurrentScene().enter();
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
                if ( !((SceneFarm)getCurrentScene()).isInSeedShopDialogState() ) {
                    ((SceneFarm) getCurrentScene()).showSeedShopDialog();
                }
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