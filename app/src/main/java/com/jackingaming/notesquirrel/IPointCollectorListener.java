package com.jackingaming.notesquirrel;

import android.graphics.Point;

import java.util.List;

public interface IPointCollectorListener {
    public void pointsCollected(List<Point> points);
    public void singlePointCollected(Point point);
}