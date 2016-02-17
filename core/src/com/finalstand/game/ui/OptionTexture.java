package com.finalstand.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.finalstand.game.states.PlayState;

import javax.xml.soap.Text;

/**
 * Created by Keith on 16/02/2016.
 */
public class OptionTexture {
    private Texture texture;
    private Vector3 position;
    private float textureWidth;
    private float textureHeight;

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
}
