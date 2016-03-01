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

    public static Vector2 size = new Vector2(32 / FinalStand.PPM, 32 / FinalStand.PPM);

    public AOETower(float x, float y, World world, float angle){
        super(x, y, world, angle);
        maxTime = 100.0f;

        level1 = new Texture("towers/aoe_level1.png");
        level2 = new Texture("towers/aoe_level2.png");
        level3 = new Texture("towers/aoe_level3.png");
        currentTexture = level1;
        towerSprite = new Sprite(currentTexture);
        towerSprite.setPosition(x, y);
        towerSprite.setSize(size.x, size.y);

        projectilePos = new Vector2(x + (getTowerSprite().getWidth() / 2),
                                    y + (getTowerSprite().getHeight() / 2));
        towerRange = (getCurrentTexture().getHeight() * 2.0f) / FinalStand.PPM;
    }

    @Override
    public void createProjectile()
    {
        Projectile p = new AOERing(projectilePos.x, projectilePos.y, towerAngle, level);
        PlayScreen.projectiles.add(p);
    }
}
