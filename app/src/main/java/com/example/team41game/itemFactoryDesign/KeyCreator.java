package com.example.team41game.itemFactoryDesign;

import android.content.res.Resources;


public class KeyCreator extends ItemCreator {
    public Item createItem(Resources res) {
        return new Key(res);
    }
}
