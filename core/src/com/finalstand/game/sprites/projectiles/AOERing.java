package com.finalstand.game.sprites.projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.World;
import com.finalstand.game.FinalStand;
import com.finalstand.game.Screens.PlayScreen;
import com.finalstand.game.sprites.creeps.Creep;

/**
 * Created by Keith on 14/02/2016.
 */
public class AOERing extends Projectile{
    private float counter;
    private float maxSize;
    private boolean flameActive;

    public AOERing(float x, float y, float angle, int level, World world)
    {
        super(x, y, angle, level, 6 / FinalStand.PPM, world, 1, 1);
        if(level < 3) {
            projectileSprite = new Sprite(new Texture("projectiles/aoe_projectile_level1.png"));
            flameActive = false;
            if(level == 1)
            {
                damage = 1;
            }
            else
            {
                damage = 2;
            }
        }
        else {
            projectileSprite = new Sprite(new Texture("projectiles/aoe_projectile_level3.png"));
            flameActive = true;
            damage = 2;
        }
        projectileSprite.setSize(1, 1);
        projectileSprite.setCenter(x, y);
        counter = 0.0f;
        projectileSprite.setScale(counter);
        maxSize = 1.5f;
    }

    @Override
    public void update()
    {
        projectileSprite.setOriginCenter();
        counter += 0.03f;
        projectileSprite.setScale(counter);
        if(counter > maxSize)
        {
            isDead = true;
        }
    }

    public void onCreepProjHit(Creep creep)
    {
        creep.setHealth(creep.getHealth() - damage);
        if(flameActive == true)
        {
            creep.setDOTActive();
        }
    }
}