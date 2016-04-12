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
public class Laser extends Projectile{

    //keep track of the number of creeps hit by the projectile
    private int creepHits;
    //the max number of creeps the projectile can hit before being removed
    private int maxCreepHits;

    public Laser(float x, float y, float angle, int level, float w, float h, World world)
    {
        super(x, y + (h/2), angle, level, 8 / FinalStand.PPM, world, w, h);
        position.x = x;
        position.y = y;
        //if shot from a level 1 or 2 tower
        if(level < 3) {
            projectileSprite = new Sprite(new Texture(Gdx.files.internal("projectiles/laser_projectile_level1.png")));
            maxCreepHits = 3;
            if(level == 1)
            {
                damage = 20;
            }
            //if level 2
            else
            {
                damage = 40;
            }
        }
        else {
            projectileSprite = new Sprite(new Texture(Gdx.files.internal("projectiles/laser_projectile_level3.png")));
            maxCreepHits = 6;
            damage = 40;
        }
        projectileSprite.setSize(w, h);
        projectileSprite.setPosition(x, y);
        creepHits = 0;
    }

    public void onCreepProjHit(Creep creep)
    {
        creep.setHealth(creep.getHealth() - damage);
        creepHits++;
        //when max number of creeps have been hit, remove the projectile
        if(creepHits >= maxCreepHits) {
            isDead = true;
        }
    }
}