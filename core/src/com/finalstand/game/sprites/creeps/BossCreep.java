package com.finalstand.game.sprites.creeps;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.finalstand.game.FinalStand;


/**
 * Created by Niall on 09/02/2016.
 */
public class BossCreep extends Creep {

    public BossCreep(float x, float y, World world) {
        super(world);
        position = new Vector2(x, y);
        texture = new Texture("creeps/BossCreep.png");
        sprite = new Sprite(texture);

        defineCreep();

        health = 1000;
        speed = 0.5f;

    }

    @Override
    public void checkDir() {
        if(getWaypointHit() == dir.size) {
            gameOver();
        } else {
            if (dir.get(getWaypointHit()).equals("right")) {
                setMovement(0);
                unsetMovement(1);
                unsetMovement(2);
                unsetMovement(3);
            } else if (dir.get(getWaypointHit()).equals("left")) {
                setMovement(1);
                unsetMovement(0);
                unsetMovement(2);
                unsetMovement(3);
            } else if (dir.get(getWaypointHit()).equals("up")) {
                setMovement(2);
                unsetMovement(1);
                unsetMovement(0);
                unsetMovement(3);
            } else if (dir.get(getWaypointHit()).equals("down")) {
                setMovement(3);
                unsetMovement(1);
                unsetMovement(2);
                unsetMovement(0);
            }
        }
    }

    public void dispose() {
        sprite.getTexture().dispose();
    }

    public void gameOver() {
        FinalStand.gameOver = true;
    }


}
