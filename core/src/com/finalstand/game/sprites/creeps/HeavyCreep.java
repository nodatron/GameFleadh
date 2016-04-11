package com.finalstand.game.sprites.creeps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Niall on 09/02/2016.
 */
public class HeavyCreep extends Creep {

    public HeavyCreep(float x, float y, World world) {
        super(world);
        position = new Vector2(x, y);
        texture = new Texture(Gdx.files.internal("creeps/heavycreep3.png"));
        sprite = new Sprite(texture);
        defineCreep();
        health = 200;
        score = 30;
    }

    public void dispose() {
        sprite.getTexture().dispose();
    }
}
