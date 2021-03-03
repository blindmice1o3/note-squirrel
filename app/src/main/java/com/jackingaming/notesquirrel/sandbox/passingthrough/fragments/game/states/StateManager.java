package com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.states;

import android.graphics.Canvas;

import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.Game;

import java.util.ArrayList;
import java.util.List;

public class StateManager {
    private Game game;

    private GameStateImpl gameState;
    private MenuStateImpl menuState;
    private TextboxStateImpl textboxState;

    private List<State> stateStack;

    public StateManager() {
        stateStack = new ArrayList<State>();
    }

    public void init(Game game) {
        this.game = game;

        gameState = new GameStateImpl();
        gameState.init(game);
        stateStack.add(gameState);
    }

    public void update(long elapsed) {
        State stateCurrent = stateStack.get( getIndexOfTop() );
        stateCurrent.update(elapsed);
    }

    public void render(Canvas canvas) {
        State stateCurrent = stateStack.get( getIndexOfTop() );
        stateCurrent.render(canvas);
    }

    private int getIndexOfTop() {
        return stateStack.size() - 1;
    }

    private State getCurrentState() {
        return stateStack.get( getIndexOfTop() );
    }

    private void pop() {
        State stateCurrent = getCurrentState();
        stateCurrent.exit();

        stateStack.remove(stateCurrent);

        getCurrentState().enter();
    }

    public void toggleMenuState() {
        if (getCurrentState() instanceof MenuStateImpl) {
            pop();
        } else {
            pushMenuState();
        }
    }

    public void toggleTextboxState() {
        if (getCurrentState() instanceof TextboxStateImpl) {
            pop();
        } else {
            pushTextboxState();
        }
    }

    private void pushMenuState() {
        getCurrentState().exit();

        if (menuState == null) {
            menuState = new MenuStateImpl();
            menuState.init(game);
        }

        stateStack.add(menuState);

        getCurrentState().enter();
    }

    private void pushTextboxState() {
        getCurrentState().exit();

        if (textboxState == null) {
            textboxState = new TextboxStateImpl();
            textboxState.init(game);
        }

        stateStack.add(textboxState);

        getCurrentState().enter();
    }
}
