package com.finalstand.game.sprites.towers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.finalstand.game.FinalStand;
import com.finalstand.game.Screens.PlayScreen;
import com.finalstand.game.sprites.projectiles.DOTGas;
import com.finalstand.game.sprites.projectiles.Projectile;

/**
 * Created by Keith on 09/02/2016.
 */
public class DOTTower extends Tower{

    //default size of damage over time tower
    public static Vector2 size = new Vector2(32 / FinalStand.PPM, 32 / FinalStand.PPM);
    //default size for the projectile
    private Vector2 DOTProjectileSize;
    //used for resizing the sprite only once when upgrading to level 3
    private boolean adjustlevel3 = false;
    //the angles for the level 3's left and right opening for shooting projectiles
    private float level3leftAngle;
    private float level3rightAngle;

    public DOTTower(float x, float y, World world, float angle)
    {
        super(x, y, world, angle);
        //set the time between firing projectile
        maxTime = 200.0f;

        //loading in the different textures for the different levels
        level1 = new Texture(Gdx.files.internal("towers/dot_level1.png"));
        level2 = new Texture(Gdx.files.internal("towers/dot_level2.png"));
        level3 = new Texture(Gdx.files.internal("towers/dot_level3.png"));
        currentTexture = level1;
        towerSprite = new Sprite(currentTexture);
        towerSprite.setPosition(x, y);
        towerSprite.setSize(size.x, size.y);

        level1cost = 250;
        level2cost = 200;
        level3cost = 300;
        upgradeCost = level2cost;
        sellPrice = level1cost / 2;
        if(FinalStand.score >= level1cost) {
            FinalStand.score -= level1cost;
        } else {
            isDead = true;
        }

        projectilePos = new Vector2(x, y);
        DOTProjectileSize = new Vector2(towerSprite.getWidth(), towerSprite.getHeight());
        towerRange = (getCurrentTexture().getHeight() * 2.0f) / FinalStand.PPM;
        level3leftAngle = towerAngle + 90;
        level3rightAngle = towerAngle - 90;

        //if the angle is over 180 it will be changed to -180 as we are working with -180 to 180
        if(level3leftAngle >= 180)
        {
            level3leftAngle = -180;
        }

        if(level3rightAngle <= -270)
        {
            level3rightAngle = 90;
        }

        defineTower();
    }

    @Override
    public void update(){
        if(targetCreep() && elapsedTime == maxTime / 2)
        {
            createProjectile();
        }

        elapsedTime++;
        if(elapsedTime > maxTime)
        {
            elapsedTime = 0;
        }

        //when tower is upgraded to level 3, resize it and reposition it to keep its original position
        if(level == 3 && adjustlevel3 == false)
        {
            towerSprite.setSize(48 / FinalStand.PPM, 32 / FinalStand.PPM);
            towerSprite.setPosition(towerSprite.getX() - 8 / FinalStand.PPM, towerSprite.getY());
            adjustlevel3 = true;
        }

        if(getRoadboundsHit()) {
//            setIsDead(true);
            //TODO make a counter that will display a 'X' to show that the placement was invalid
            // Make a counter variable, make it go up by one every loop, after a certain time make it trigger a if
            // this if will then set the tower to dead
            //TODO make the same kind of code for the traps except that the traps have to be touching the roadbounds

            placementErrorCounter ++;

            if(!getSpriteChanged()) {
                towerSprite.getTexture().dispose();
                towerSprite.setTexture(new Texture(Gdx.files.internal("x.png")));
                setSpriteChanged(true);
            }
            if(getPlacementErrorCounter() == 15) {
                setIsDead(true);
            }

        }
    }

    @Override
    public void createProjectile()
    {
        Projectile p1 = new DOTGas(projectilePos.x, projectilePos.y, towerAngle, level,
                                  DOTProjectileSize.x, DOTProjectileSize.y, world, DOTTower.size.y - 1 / FinalStand.PPM);
        PlayScreen.projectiles.add(p1);

        //level 3 shoots projectiles to the left and right as well as forward
        if(level == 3)
        {
            Projectile p2 = new DOTGas(projectilePos.x, projectilePos.y, level3rightAngle, level,
                                       DOTProjectileSize.x, DOTProjectileSize.y, world, DOTTower.size.y + 2 / FinalStand.PPM);
            Projectile p3 = new DOTGas(projectilePos.x, projectilePos.y, level3leftAngle, level,
                                       DOTProjectileSize.x, DOTProjectileSize.y, world, DOTTower.size.y + 2 / FinalStand.PPM);

            PlayScreen.projectiles.add(p2);
            PlayScreen.projectiles.add(p3);
        }
    }
}
