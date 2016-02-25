package com.finalstand.game.sprites.projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.finalstand.game.Screens.PlayScreen;

/**
 * Created by Keith on 14/02/2016.
 */
public class DOTGas extends Projectile{
    private float counter;
    private float maxTime;
    private Vector2 initialPos;

    public DOTGas(float x, float y, double angle, int level, float w, float h)
    {
        super(x, y, angle, level);
        projectileSprite = new Sprite(new Texture("projectiles/dot_projectile.png"));
        projectileSprite.setSize(w, h);
        projectileSprite.setPosition(x, y);
        counter = 0.0f;
        maxTime = 1.5f;
    }

    @Override
    public void update()
    {
        counter += 0.01f;
        if(counter > maxTime)
        {
            PlayScreen.projectiles.remove(this);
        }
        if(counter < maxTime / 2) {
            position.add(forward);
            projectileSprite.setPosition(position.x, position.y);
        }
    }
}
