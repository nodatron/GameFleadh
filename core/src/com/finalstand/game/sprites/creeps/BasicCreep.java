package com.finalstand.game.sprites.creeps;

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
        System.out.println(position);
        texture = new Texture("creeps/basiccreep3.png");
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
