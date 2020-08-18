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
//    private InputManager inputManager;
    private InputManager inputManager01;
    private InputManager inputManager02;
    private InputManager inputManager03;
    private InputManager inputManager04;

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

//        inputManager = new InputManager();
        inputManager01 = new InputManager();
        QuadrantSurfaceView quadrantSurfaceView01 = findViewById(R.id.surfaceview_quadrant_01);
        quadrantSurfaceView01.setOnTouchListener(inputManager01);
        inputManager02 = new InputManager();
        QuadrantSurfaceView quadrantSurfaceView02 = findViewById(R.id.surfaceview_quadrant_02);
        quadrantSurfaceView02.setOnTouchListener(inputManager02);
        inputManager03 = new InputManager();
        QuadrantSurfaceView quadrantSurfaceView03 = findViewById(R.id.surfaceview_quadrant_03);
        quadrantSurfaceView03.setOnTouchListener(inputManager03);
        inputManager04 = new InputManager();
        QuadrantSurfaceView quadrantSurfaceView04 = findViewById(R.id.surfaceview_quadrant_04);
        quadrantSurfaceView04.setOnTouchListener(inputManager04);
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

//    public InputManager getInputManager() {
//        return inputManager;
//    }

    public InputManager getInputManager01() {
        return inputManager01;
    }

    public InputManager getInputManager02() {
        return inputManager02;
    }

    public InputManager getInputManager03() {
        return inputManager03;
    }

    public InputManager getInputManager04() {
        return inputManager04;
    }

}