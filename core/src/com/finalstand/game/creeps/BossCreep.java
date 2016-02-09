package com.finalstand.game.creeps;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Niall PC on 09/02/2016.
 */
public class BossCreep extends Creep {

    public BasicCreep(float x, float y, String textureFilename) {
        texture = new Texture(textureFilename);
        position = new Vector2(x, y);
    }

    public void update() {

    }

    public void render() {

    }

    public void dispose() {

    }
}
