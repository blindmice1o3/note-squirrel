package com.jackingaming.notesquirrel.sandbox.passingthrough.fragments.game.scenes.entities;

public interface Damageable {
    void takeDamage(int incomingDamage);
    void die();
}