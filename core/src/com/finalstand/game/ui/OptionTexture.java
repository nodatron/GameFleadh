package com.finalstand.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.finalstand.game.Screens.PlayScreen;
import com.finalstand.game.Screens.PlayScreen;
import com.finalstand.game.sprites.towers.*;

import javax.xml.soap.Text;

/**
 * Created by Keith on 16/02/2016.
 */
public class OptionTexture {
    private Texture texture;
    private Vector3 position;
    private float textureWidth;
    private float textureHeight;

    private int towerOption;

    private World world;

    public OptionTexture(float w, float h, World world)
    {
        textureWidth = w;
        textureHeight = h;
        position = new Vector3(0, 0, 0);
        this.world = world;
    }

    public void update()
    {
        position = PlayScreen.getWorldMousePos();
        position.x -= textureWidth / 2;
        position.y -= textureHeight / 2;

        if(Gdx.input.justTouched())
        {
            PlayScreen.optionChosen = false;
            position = PlayScreen.getWorldMousePos();

            switch (towerOption) {
                case 0: {
                    //position.x -= SingleShotTower.getCurrentTexture().getWidth() / 2;
                    //position.y -= SingleShotTower.getCurrentTexture().getHeight() / 2;
                    PlayScreen.towers.add(new com.finalstand.game.sprites.towers.SingleShotTower(position.x, position.y, world));
                    break;
                }
                case 1: {
                    PlayScreen.towers.add(new com.finalstand.game.sprites.towers.AOETower(position.x, position.y, world));
                    break;
                }
                case 2: {
                    PlayScreen.towers.add(new com.finalstand.game.sprites.towers.DOTTower(position.x, position.y, world));
                    break;
                }
                case 3: {
                    PlayScreen.towers.add(new com.finalstand.game.sprites.towers.LaserTower(position.x, position.y, world));
                    break;
                }
            }
        }
    }

    public Texture getTexture() {
        return texture;
    }

    public Vector3 getPosition() {
        return position;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public void setTowerOption(int towerOption) {
        this.towerOption = towerOption;
    }
}
