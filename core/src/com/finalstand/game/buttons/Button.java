package com.finalstand.game.buttons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.finalstand.game.sprites.towers.Tower;
import com.finalstand.game.states.PlayState;

/**
 * Created by Keith on 18/02/2016.
 */
public class Button {
    protected Vector2 position;
    protected Texture buttonTexture;

    protected Tower tower;
    protected float height;
    protected float width;

    protected String buttonText;

    public Button(float x, float y, float w, float h, String text, String texture, Tower tower)
    {
        position.x = x;
        position.y = y;
        width = w;
        height = h;
        buttonText = new String(text);
        buttonTexture = new Texture(texture);
        this.tower = tower;
    }
}
