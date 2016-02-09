package com.finalstand.game.creeps;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Niall on 09/02/2016.
 */
public abstract class Creep {

    private Texture texture;
    private Sprite sprite;
    private SpriteBatch batch;

    private Vector2 position;

    private int health;
    private int armour;
    private boolean status;

    abstract void update();
    abstract void render();
    abstract void dispose();

}
