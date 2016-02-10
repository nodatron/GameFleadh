package com.finalstand.game.creeps;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Niall on 09/02/2016.
 */
public abstract class Creep {

    protected Texture texture;
//    protected Sprite sprite;
    protected SpriteBatch batch;

    protected Vector2 position;

    protected int health;
    protected int armour;
    protected boolean[] status;

    abstract void update();
    abstract void render(SpriteBatch batch);
    abstract void dispose();

}
