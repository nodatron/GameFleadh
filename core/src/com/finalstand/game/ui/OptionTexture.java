package com.finalstand.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.finalstand.game.FinalStand;
import com.finalstand.game.Screens.PlayScreen;
import com.finalstand.game.Screens.PlayScreen;
import com.finalstand.game.sprites.towers.*;
import com.finalstand.game.traps.Barricade;
import com.finalstand.game.traps.Bomb;
import com.finalstand.game.traps.Glue;
import com.finalstand.game.traps.Spike;

import javax.xml.soap.Text;

/**
 * Created by Keith on 16/02/2016.
 */
public class OptionTexture {
    private Sprite sprite;
    private Vector3 position;

    private int towerOption;
    private float rotation;

    private World world;

    public OptionTexture(World world)
    {
        position = new Vector3(0, 0, 0);
        this.world = world;
        rotation = 0;
    }

    public void update()
    {
        if(Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            sprite.setOriginCenter();
            rotation += 90;
            if(rotation == 360)
            {
                rotation = 0;
            }
            sprite.setRotation(rotation);
        }
        position = PlayScreen.getWorldMousePos();
        position.x -= sprite.getWidth() / 2;
        position.y -= sprite.getHeight() / 2;
        sprite.setPosition(position.x, position.y);

        if(Gdx.input.justTouched())
        {
            PlayScreen.optionChosen = false;

            switch (towerOption) {
                case 0: {
                    PlayScreen.towers.add(new com.finalstand.game.sprites.towers.SingleShotTower(position.x, position.y, world, rotation - 180));
                    break;
                }
                case 1: {
                    PlayScreen.towers.add(new com.finalstand.game.sprites.towers.AOETower(position.x, position.y, world, rotation - 180));
                    break;
                }
                case 2: {
                    PlayScreen.towers.add(new com.finalstand.game.sprites.towers.DOTTower(position.x, position.y, world, rotation - 180));
                    break;
                }
                case 3: {
                    PlayScreen.towers.add(new com.finalstand.game.sprites.towers.LaserTower(position.x, position.y, world, rotation - 180));
                    break;
                }
                case 4: {
                    PlayScreen.traps.add(new Barricade(position.x, position.y, world));
                    break;
                }
                case 5: {
                    PlayScreen.traps.add(new Bomb(position.x, position.y, world));
                    break;
                }
                case 6: {
                    PlayScreen.traps.add(new Glue(position.x, position.y, world));
                    break;
                }
                case 7: {
                    PlayScreen.traps.add(new Spike(position.x, position.y, world));
                    break;
                }
            }
        }
    }

    public Sprite getSprite(){ return sprite; }

    public Vector3 getPosition() {
        return position;
    }

    public void setTexture(Texture texture, float width, float height) {
        sprite = new Sprite(texture);
        sprite.setSize(width, height);
    }

    public void setTowerOption(int towerOption) {
        this.towerOption = towerOption;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }
}
