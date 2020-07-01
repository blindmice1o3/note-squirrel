package com.jackingaming.notesquirrel.gameboycolor.gamecartridges.base.tilemaps.tiles;

public class Tile {

    public enum Walkability { SOLID, WALKABLE; }

    protected Walkability walkability;

    public Walkability getWalkability() {
        return walkability;
    }
}