package com.finalstand.game.sprites.towers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.finalstand.game.FinalStand;
import com.finalstand.game.Screens.PlayScreen;
import com.finalstand.game.sprites.projectiles.Laser;
import com.finalstand.game.sprites.projectiles.Projectile;

/**
 * Created by Keith on 09/02/2016.
 */
public class LaserTower extends Tower{

    //default size of laser tower
    public static Vector2 size = new Vector2(16 / FinalStand.PPM, 32 / FinalStand.PPM);
    //seperate texture switched to when level 3 tower is firing
    private Texture level3Firing;
    //used for resizing the sprite only once when upgrading to level 3
    private boolean adjustlevel3 = false;
    private Vector2 laserProjectileSize;

    public LaserTower(float x, float y, World world, float angle)
    {
        super(x, y, world, angle);
        //set the time between firing projectile
        maxTime = 80.0f;

        //loading in the different textures for the different levels
        level1 = new Texture(Gdx.files.internal("towers/laserTower_level1.png"));
        level2 = new Texture(Gdx.files.internal("towers/laserTower_level2.png"));
        level3 = new Texture(Gdx.files.internal("towers/laserTower_level3_notFiring.png"));
        level3Firing = new Texture(Gdx.files.internal("towers/laserTower_level3_firing.png"));
        currentTexture = level1;
        towerSprite = new Sprite(currentTexture);
        towerSprite.setPosition(x, y);
        towerSprite.setSize(size.x, size.y);

        level1cost = 400;
        level2cost = 200;
        level3cost = 250;
        upgradeCost = level2cost;
        sellPrice = level1cost / 2;
        if(FinalStand.score >= level1cost) {
            FinalStand.score -= level1cost;
        } else {
            isDead = true;
        }

        projectilePos =  new Vector2(x + (towerSprite.getWidth() / 2.5f), y);
        laserProjectileSize = new Vector2(towerSprite.getWidth() / 4.0f, towerSprite.getHeight());
        towerRange = (getCurrentTexture().getHeight() * 2.0f) / FinalStand.PPM;

        defineTower();
    }

    @Override
    public void update(){
        if(targetCreep() && elapsedTime == maxTime / 2)
        {
            //change to firing texture
            if(level == 3)
            {
                towerSprite.setTexture(level3Firing);
            }
            createProjectile();
        }

        elapsedTime++;
        if(elapsedTime > maxTime)
        {
            elapsedTime = 0;
            //return to the non firing texture
            if(level == 3)
            {
                towerSprite.setTexture(level3);
            }
        }

        //when tower is upgraded to level 3, resize it and reposition it to keep its original position
        if(level == 3 && adjustlevel3 == false)
        {
            towerSprite.setSize(48 / FinalStand.PPM, 32 / FinalStand.PPM);
            towerSprite.setPosition(towerSprite.getX() - 16 / FinalStand.PPM, towerSprite.getY());
            adjustlevel3 = true;
        }
    }

    @Override
    public void createProjectile()
    {
        Projectile p = new Laser(projectilePos.x, projectilePos.y, towerAngle, level,
                                laserProjectileSize.x, laserProjectileSize.y, world);
        PlayScreen.projectiles.add(p);
    }
}
