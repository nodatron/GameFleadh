package com.finalstand.game.sprites.towers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.finalstand.game.FinalStand;
import com.finalstand.game.Screens.PlayScreen;
import com.finalstand.game.sprites.projectiles.AOERing;
import com.finalstand.game.sprites.projectiles.Projectile;

/**
 * Created by Keith on 09/02/2016.
 */
public class AOETower extends Tower{

    //default size of area of effect tower
    public static Vector2 size = new Vector2(32 / FinalStand.PPM, 32 / FinalStand.PPM);

    public AOETower(float x, float y, World world, float angle){
        super(x, y, world, angle);
        //set the time between firing projectile
        maxTime = 100.0f;

        //loading in the different textures for the different levels
        level1 = new Texture(Gdx.files.internal("towers/aoe_level1.png"));
        level2 = new Texture(Gdx.files.internal("towers/aoe_level2.png"));
        level3 = new Texture(Gdx.files.internal("towers/aoe_level3.png"));
        currentTexture = level1;
        towerSprite = new Sprite(currentTexture);
        towerSprite.setPosition(x, y);
        towerSprite.setSize(size.x, size.y);

        level1cost = 200;
        level2cost = 200;
        level3cost = 350;
        upgradeCost = level2cost;
        sellPrice = level1cost / 2;
        if(FinalStand.score >= level1cost) {
            FinalStand.score -= level1cost;
        } else {
            isDead = true;
        }

        projectilePos = new Vector2(x + (getTowerSprite().getWidth() / 2),
                                    y + (getTowerSprite().getHeight() / 2));
        towerRange = (getCurrentTexture().getHeight() * 2.0f) / FinalStand.PPM;

        defineTower();
    }

    @Override
    public void createProjectile()
    {
        Projectile p = new AOERing(projectilePos.x, projectilePos.y, towerAngle, level, world);
        PlayScreen.projectiles.add(p);
    }
}
