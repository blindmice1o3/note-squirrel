package com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.items;

import com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities.Consumer;

public interface Consumeable {
    void integrateWithHost(Consumer consumer);
}