package com.finalstand.game.creeps;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Niall on 09/02/2016.
 */
public class MediumCreep extends Creep{

    public MediumCreep(float x, float y) {
        texture = new Texture("MediumCreep.png");
        position = new Vector2(x, y);
    }

    public void update() {

    }

    public void render(SpriteBatch batch) {

    }

    public void dispose() {

    }
}
