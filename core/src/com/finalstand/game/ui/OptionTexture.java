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

    public OptionTexture(Texture texture, float w, float h)
    {
        this.texture = texture;
        textureWidth = w;
        textureHeight = h;
        position = new Vector3(0, 0, 0);
        PlayState.optionPicked = true;
    }

    public void update()
    {
        position = PlayState.getWorldMousePos();
        position.x -= textureWidth / 2;
        position.y -= textureHeight / 2;
    }

    public Texture getTexture() {
        return texture;
    }

    public Vector3 getPosition() {
        return position;
    }
}
