package com.example.team41game.itemFactoryDesign;

import android.graphics.Canvas;

import com.example.team41game.interactiveObjFactoryDesign.InteractiveObj;

public interface Item {
    void render(Canvas canvas, int tileWidth, int tileHeight);
    void setContainer(InteractiveObj container);
    void discover();
    boolean justDiscovered();
    void acquire();
}
