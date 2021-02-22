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

    private List<State> statesStack;

    public StateManager() {
        statesStack = new ArrayList<State>();
    }

    public void init(Game game) {
        this.game = game;

        gameState = new GameStateImpl();
        gameState.init(game);
        statesStack.add(gameState);
    }

    public void update(long elapsed) {
        State stateCurrent = statesStack.get( getIndexOfTop() );
        stateCurrent.update(elapsed);
    }

    public void render(Canvas canvas) {
        State stateCurrent = statesStack.get( getIndexOfTop() );
        stateCurrent.render(canvas);
    }

    private int getIndexOfTop() {
        return statesStack.size() - 1;
    }

    public void toggleMenuState() {
        State stateCurrent = statesStack.get( getIndexOfTop() );
        if (stateCurrent instanceof MenuStateImpl) {
            pop();
        } else {
            if (menuState == null) {
                menuState = new MenuStateImpl();
                menuState.init(game);
            }
            statesStack.add(menuState);
        }
    }

    public void pushTextboxState() {
        if (textboxState == null) {
            textboxState = new TextboxStateImpl();
            textboxState.init(game);
        }
        statesStack.add(textboxState);
    }

    public void pop() {
        statesStack.remove( getIndexOfTop() );
    }
}
