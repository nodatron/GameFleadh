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
public class MediumCreep extends Creep {

    public MediumCreep(float x, float y, World world) {
        super(world);
        position = new Vector2(x, y);
        texture = new Texture(Gdx.files.internal("creeps/mediumcreep3.png"));
        sprite = new Sprite(texture);
        defineCreep();
        health = 225;
        score = 20;
    }

    @Override
    public void changeDmgSprite() {
        sprite.getTexture().dispose();
        sprite.setTexture(new Texture(Gdx.files.internal("creeps/medcreepdmg.png")));
//        setDamaged(false);
    }

    @Override
    public void changeNormalSprite() {
        sprite.getTexture().dispose();
        sprite.setTexture(new Texture(Gdx.files.internal("creeps/mediumcreep3.png")));
//        setChanged(true);
    }

    public void dispose() {
        sprite.getTexture().dispose();
    }
}
