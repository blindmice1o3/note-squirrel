package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.pocketcritters.states;

import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.Handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StateManager {

    private Handler handler;

    private Map<State.Id, State> stateHashMap;
    private List<State> stateStack;

    public StateManager(Handler handler) {
        this.handler = handler;
        stateHashMap = new HashMap<State.Id, State>();

        stateHashMap.put(State.Id.GAME, new GameState(handler));
        stateHashMap.put(State.Id.START_MENU, new StartMenuState(handler));

        stateStack = new ArrayList<State>();
        stateStack.add(stateHashMap.get(State.Id.GAME));
    }

    public State getState(State.Id id) {
        if (stateHashMap.containsKey(id)) {
            return stateHashMap.get(id);
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

    public State getCurrentState() { return stateStack.get( getIndexOfTop() ); }

    public void savePresentState() {
        for (State state : stateHashMap.values()) {
            state.savePresentState();
        }
        for (int i = 0; i < stateStack.size(); i++) {
            //TODO: write to file.
        }
    }

    public void loadSavedState() {
        //TODO: restore every state in stateHashMap.
        //TODO: restore stateStack.
    }

}
