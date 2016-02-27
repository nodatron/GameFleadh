package com.finalstand.game.sprites.towers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
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

    public AOETower(float x, float y, World world){
        super(x, y, world);

        level1 = new Texture("towers/aoe_level1.png");
        level2 = new Texture("towers/aoe_level2.png");
        level3 = new Texture("towers/aoe_level3.png");
        currentTexture = level1;

        projectilePos = new Vector2(x + ((getCurrentTexture().getWidth() / 2) / FinalStand.PPM),
                y + ((getCurrentTexture().getHeight() / 2) / FinalStand.PPM));
        towerAngle = 0;
    }


    @Override
    public void update()
    {
        if(Gdx.input.justTouched())
        {
            createProjectile();
        }
    }

    @Override
    public void createProjectile()
    {
        Projectile p = new AOERing(projectilePos.x, projectilePos.y, towerAngle, level);
        PlayScreen.projectiles.add(p);
    }
}
