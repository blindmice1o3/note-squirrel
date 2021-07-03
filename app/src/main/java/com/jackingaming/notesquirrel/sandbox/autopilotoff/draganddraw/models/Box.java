package com.jackingaming.notesquirrel.sandbox.autopilotoff.draganddraw.models;

import android.graphics.PointF;

import java.io.Serializable;

public class Box implements Serializable {
    private PointF origin;
    private PointF current;

    public Box(PointF origin) {
        this.origin = current = origin;
    }

    public PointF getCurrent() {
        return current;
    }

    public void setCurrent(PointF current) {
        this.current = current;
    }

    public PointF getOrigin() {
        return origin;
    }
}