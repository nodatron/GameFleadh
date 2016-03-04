package com.finalstand.game.sprites.projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.finalstand.game.FinalStand;
import com.finalstand.game.Screens.PlayScreen;
import com.finalstand.game.sprites.creeps.Creep;
import com.finalstand.game.sprites.towers.DOTTower;

/**
 * Created by Keith on 14/02/2016.
 */
public class DOTGas extends Projectile{
    private float counter;
    private float removeTime;
    private Vector2 initialPos;
    private float range;
    private float angle;

    public DOTGas(float x, float y, float angle, int level, float w, float h, World world)
    {
        super(x, y, angle, level, 2 / FinalStand.PPM, world, w, h);
        this.angle = angle;
        projectileSprite = new Sprite(new Texture("projectiles/dot_projectile.png"));
        projectileSprite.setSize(w, h);
        projectileSprite.setPosition(x, y);
        initialPos = new Vector2(x, y);
        counter = 0.0f;
        removeTime = 1.5f;
        range = DOTTower.size.y;
        damage = 1;
    }

    @Override
    public void update()
    {
        counter += 0.01f;
        if(counter > removeTime)
        {
            PlayScreen.projectiles.remove(this);
        }

        switch((int)angle)
        {
            case -180:
            {
                if(projectileSprite.getY() < initialPos.y + range) {
                    position.add(forward);
                    projectileSprite.setPosition(position.x, position.y);
                }
                break;
            }
            case -90:
            {
                if(projectileSprite.getX() > initialPos.x - range) {
                    position.add(forward);
                    projectileSprite.setPosition(position.x, position.y);
                }
                break;
            }
            case 0:
            {
                if(projectileSprite.getY() > initialPos.y - range) {
                    position.add(forward);
                    projectileSprite.setPosition(position.x, position.y);
                }
                break;
            }
            case 90:
            {
                if(projectileSprite.getX() < initialPos.x + range) {
                    position.add(forward);
                    projectileSprite.setPosition(position.x, position.y);
                }
                break;
            }
        }
    }

    public void onCreepProjHit(Creep creep)
    {
        creep.setHealth(creep.getHealth() - damage);
    }
}