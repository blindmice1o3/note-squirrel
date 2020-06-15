package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.states;

import com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.Handler;

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
        stateHashMap.put(State.Id.TEXTBOX, new TextboxState(handler));

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

}
