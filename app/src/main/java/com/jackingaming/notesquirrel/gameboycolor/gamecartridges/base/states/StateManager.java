package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.states;

import android.util.Log;

import com.jackingaming.notesquirrel.MainActivity;
import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.GameCartridge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StateManager {

    private Map<State.Id, State> stateCollection;
    private List<State> stateStack;

    public StateManager(GameCartridge gameCartridge) {
        initStateCollection(gameCartridge);

        stateStack = new ArrayList<State>();
        stateStack.add(stateCollection.get(State.Id.GAME));
    }

    public void initStateCollection(GameCartridge gameCartridge) {
        stateCollection = new HashMap<State.Id, State>();

        GameState gameState = new GameState(gameCartridge);
        stateCollection.put(State.Id.GAME, gameState);
        stateCollection.put(State.Id.START_MENU, new StartMenuState(gameCartridge, gameState.getSceneManager()));
        stateCollection.put(State.Id.TEXTBOX, new TextboxState(gameCartridge, gameState.getSceneManager()));
    }

    public State getState(State.Id id) {
        if (stateCollection.containsKey(id)) {
            return stateCollection.get(id);
        }

        return null;
    }

    public void push(State.Id id, Object[] args) {
        getState(id).enter(args);
        stateStack.add( getState(id) );
    }

    public void pop() {
        stateStack.get( getIndexOfTop() ).exit();
        stateStack.remove( getIndexOfTop() );
    }

    public int getIndexOfTop() {
        return (stateStack.size() - 1);
    }

    public State getCurrentState() {
        return stateStack.get( getIndexOfTop() );
    }

    public Map<State.Id, State> getStateCollection() {
        return stateCollection;
    }

    public void setStateCollection(Map<State.Id, State> stateCollection) {
        this.stateCollection = stateCollection;
    }

    /**
     * MAKE LIST OF State.Id CURRENTLY IN StateManager.stateStack
     */
    public ArrayList<State.Id> retrieveStateIdsFromStateStack() {
        ArrayList<State.Id> stateIdsFromStateStack = new ArrayList<State.Id>();

        for (int i = 0; i < stateStack.size(); i++) {
            State state = stateStack.get(i);
            stateIdsFromStateStack.add(state.getId());
            Log.d(MainActivity.DEBUG_TAG, "StateManager.retrieveStateIdsFromStateStack() added: " + state.getId());
        }

        return stateIdsFromStateStack;
    }

    public void restoreStateStack(ArrayList<State.Id> stateIdsFromStateStack) {
        // MUST CLEAR stateStack (possible scenario of not loading from very beginning of game).
        stateStack.clear();

        for (int i = 0; i < stateIdsFromStateStack.size(); i++) {
            State.Id id = stateIdsFromStateStack.get(i);

            State state = stateCollection.get(id);

            stateStack.add(state);
        }
    }

}
