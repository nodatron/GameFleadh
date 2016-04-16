package com.finalstand.game.sprites.creeps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;


/**
 * Created by Niall on 09/02/2016.
 */
public class BasicCreep extends Creep {

    public BasicCreep(float x, float y, World world) {
        super(world);
        position = new Vector2(x, y);
        texture = new Texture(Gdx.files.internal("creeps/basiccreep3.png"));
        sprite = new Sprite(texture);
        defineCreep();
        health = 100;
        score = 10;
    }

    public void changeSprite( String filename) {
        sprite.getTexture().dispose();
        sprite.setTexture(new Texture(Gdx.files.internal(filename)));
        setDamaged(false);
    }

    public void dispose() {
        sprite.getTexture().dispose();
    }

}
