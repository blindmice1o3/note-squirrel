package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.derived.pocketcritters.computer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.jackingaming.notesquirrel.R;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.IGameCartridge;
import com.jackingaming.notesquirrel.gameboycolor.input.InputManager;

public class QuadrantActivity extends AppCompatActivity {

    private Bundle savedInstanceState;

    private GameCartridge gameCartridge01;
    private GameCartridge gameCartridge02;
    private GameCartridge gameCartridge03;
    private GameCartridge gameCartridge04;
    private InputManager inputManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quadrant);

        /////////////////////////////////////////////
        this.savedInstanceState = savedInstanceState;
        /////////////////////////////////////////////

        gameCartridge01 = new GameCartridge(this, IGameCartridge.Id.POOH_FARMER);
        gameCartridge02 = new GameCartridge(this, IGameCartridge.Id.POCKET_CRITTERS);
        gameCartridge03 = new GameCartridge(this, IGameCartridge.Id.POCKET_CRITTERS);
        gameCartridge04 = new GameCartridge(this, IGameCartridge.Id.POCKET_CRITTERS);
        inputManager = new InputManager();
    }

    public Bundle getSavedInstanceState() {
        return savedInstanceState;
    }

    public GameCartridge getGameCartridge01() {
        return gameCartridge01;
    }

    public GameCartridge getGameCartridge02() {
        return gameCartridge02;
    }

    public GameCartridge getGameCartridge03() {
        return gameCartridge03;
    }

    public GameCartridge getGameCartridge04() {
        return gameCartridge04;
    }

    public InputManager getInputManager() {
        return inputManager;
    }

}