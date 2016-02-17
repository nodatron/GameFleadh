package com.finalstand.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.finalstand.game.states.PlayState;
import com.finalstand.game.towers.AOETower;
import com.finalstand.game.towers.DOTTower;
import com.finalstand.game.towers.LaserTower;
import com.finalstand.game.towers.SingleShotTower;
import com.finalstand.game.towers.Tower;

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

    public OptionTexture(float w, float h)
    {
        textureWidth = w;
        textureHeight = h;
        position = new Vector3(0, 0, 0);
    }

    public void update()
    {
        position = PlayState.getWorldMousePos();
        position.x -= textureWidth / 2;
        position.y -= textureHeight / 2;

        if(Gdx.input.justTouched())
        {
            PlayState.optionChosen = false;
            position = PlayState.getWorldMousePos();

            switch (towerOption) {
                case 0: {
                    //position.x -= SingleShotTower.getCurrentTexture().getWidth() / 2;
                    //position.y -= SingleShotTower.getCurrentTexture().getHeight() / 2;
                    PlayState.towers.add(new com.finalstand.game.sprites.towers.SingleShotTower(position.x, position.y));
                    break;
                }
                case 1: {
                    //position.x -= AOETower.getCurrentTexture().getWidth() / 2;
                    //position.y -= AOETower.getCurrentTexture().getHeight() / 2;
                    PlayState.towers.add(new com.finalstand.game.sprites.towers.AOETower(position.x, position.y));
                    break;
                }
                case 2: {
                    //position.x -= DOTTower.getCurrentTexture().getWidth() / 2;
                    //position.y -= DOTTower.getCurrentTexture().getHeight() / 2;
                    PlayState.towers.add(new com.finalstand.game.sprites.towers.DOTTower(position.x, position.y));
                    break;
                }
                case 3: {
                    //position.x -= LaserTower.getCurrentTexture().getWidth() / 2;
                    //position.y -= LaserTower.getCurrentTexture().getHeight() / 2;
                    PlayState.towers.add(new com.finalstand.game.sprites.towers.LaserTower(position.x, position.y));
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
