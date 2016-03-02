package com.finalstand.game.sprites.creeps;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Niall on 09/02/2016.
 */
public class MediumCreep extends Creep {

    public MediumCreep(float x, float y, World world) {
        super(world);
        position = new Vector2(x, y);
        texture = new Texture("creeps/mediumcreep2.png");
        sprite = new Sprite(texture);

        defineCreep();
//        movement = new boolean[8];
//        setMovement();

        health = 100;
    }

    public void dispose() {
        sprite.getTexture().dispose();
    }
}
