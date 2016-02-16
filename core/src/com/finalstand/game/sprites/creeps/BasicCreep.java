package com.finalstand.game.sprites.creeps;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.finalstand.game.FinalStand;
import com.finalstand.game.sprites.creeps.Creep;


/**
 * Created by Niall on 09/02/2016.
 */
public class BasicCreep extends Creep {

    public BasicCreep(float x, float y, World world) {
        super(world);
        position = new Vector2(x, y);
        texture = new Texture("creeps/BasicCreep.png");
        sprite = new Sprite(texture);
//        ball.setOrigin(ball.getWidth()/2,ball.getHeight()/2);
        sprite.setOrigin((getWidth() / 2) / FinalStand.PPM, (getHeight() / 2) / FinalStand.PPM);
        sprite.setPosition(getPosition().x / FinalStand.PPM, getPosition().y / FinalStand.PPM);

        health = 100;
        armour = 10;
        status = new boolean[5];
        defineCreep();
    }

    public void update() {

    }

    public void render(SpriteBatch batch) {
//        batch.begin();
        sprite.draw(batch);
//        batch.end();
    }

    public void dispose() {

    }

}
