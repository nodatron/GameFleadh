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

    public DOTTower(float x, float y, World world, float angle)
    {
        super(x, y, world, angle);

        level1 = new Texture("towers/dot_level1.png");
        level2 = new Texture("towers/dot_level2.png");
        level3 = new Texture("towers/dot_level3.png");
        currentTexture = level1;
        towerSprite = new Sprite(currentTexture);
        towerSprite.setPosition(x, y);
        towerSprite.setSize(towerSprite.getWidth() / FinalStand.PPM, towerSprite.getHeight() / FinalStand.PPM);

        projectilePos = new Vector2(x, y);
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
        Projectile p = new DOTGas(projectilePos.x, projectilePos.y, towerAngle, level,
                                  getCurrentTexture().getWidth() / FinalStand.PPM, getCurrentTexture().getHeight() / FinalStand.PPM);
        PlayScreen.projectiles.add(p);
    }
}
