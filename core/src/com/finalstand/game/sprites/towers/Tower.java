package com.finalstand.game.sprites.towers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.finalstand.game.FinalStand;
import com.finalstand.game.Screens.PlayScreen;
import com.finalstand.game.buttons.SellButton;
import com.finalstand.game.buttons.UpgradeButton;
import com.finalstand.game.states.PlayState;

/**
 * Created by Keith on 09/02/2016.
 */
public class Tower {
    protected Vector2 position;
    protected Rectangle bounds;

    protected int level;
    protected Texture level1;
    protected Texture level2;
    protected Texture level3;
    protected Texture currentTexture;

    protected Body b2Body;
    protected World world;

    public Tower(float x, float y, World world)
    {
        position = new Vector2(x, y);
        level = 1;

        this.world = world;

        defineTower();
    }

    public void update(){}
    public Texture getCurrentTexture(){return currentTexture;}
    public Vector2 getPosition(){return position;}
    public void upgrade()
    {
        if(level < 3)
        {
            level++;
        }
        if(level == 2)
        {
            currentTexture = level2;
        }
        if(level == 3)
        {
            currentTexture = level3;
        }
    }
    /*public void checkPressed()
    {
        if(Gdx.input.justTouched())
        {
            Vector3 mouse = PlayState.getWorldMousePos();
            if(mouse.x > getPosition().x && mouse.x < getPosition().x + (getCurrentTexture().getWidth() / FinalStand.PPM) &&
                    mouse.y > getPosition().y && mouse.y < getPosition().y + (getCurrentTexture().getHeight() / FinalStand.PPM))
            {
                TowerOptions();
            }
        }
    }*/

    public void defineTower() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(position.x, position.y);
        bdef.type = BodyDef.BodyType.StaticBody;

        b2Body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(8 / FinalStand.PPM);

        fdef.shape = shape;
        b2Body.createFixture(fdef);
    }

    public void TowerOptions()
    {
        PlayScreen.upgradeButton = new UpgradeButton(100, this);
        PlayScreen.sellButton = new SellButton(200, this);
        PlayScreen.displayButtons = true;
    }
}