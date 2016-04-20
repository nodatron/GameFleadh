package com.finalstand.game.sprites.projectiles;

import com.badlogic.gdx.Gdx;
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
    //used for increasing the size of the projectile
    private float counter;
    //max size the projectile can be before being removed
    private float maxSize;
    //if level 3, cause a dot effect
    private boolean flameActive;
    private int DOTTime;
    //the damage of the dot effect
    private int fireDamage;

    public AOERing(float x, float y, float angle, int level, World world)
    {
        super(x, y, angle, level, 6 / FinalStand.PPM, world, 1, 1);
        //if shot from a level 1 or 2 tower
        if(level < 3) {
            projectileSprite = new Sprite(new Texture(Gdx.files.internal("projectiles/aoe_projectile_level1.png")));
            flameActive = false;
            if(level == 1)
            {
                damage = 15;
            }
            //if level 2
            else
            {
                damage = 30;
            }
        }
        else {
            projectileSprite = new Sprite(new Texture(Gdx.files.internal("projectiles/aoe_projectile_level3.png")));
            flameActive = true;
            damage = 40;
            fireDamage = 10;
            DOTTime = 100;
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
        //set the origin to the center so it will expand from the middle
        projectileSprite.setOriginCenter();
        counter += 0.03f;
        //increase the size of the projectile
        projectileSprite.setScale(counter);
        //when its reached its max size, remove it
        if(counter > maxSize)
        {
            isDead = true;
        }
    }

    public void onCreepProjHit(Creep creep)
    {
        creep.setHealth(creep.getHealth() - damage);
        //apply dot effect if level 3
        if(flameActive == true)
        {
            creep.setDOTActive(DOTTime, fireDamage);
        }
        creep.setDamaged(true);
    }
}