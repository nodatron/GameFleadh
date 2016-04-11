package com.finalstand.game.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.finalstand.game.FinalStand;
import com.finalstand.game.sprites.towers.Tower;

/**
 * Created by Keith on 18/02/2016.
 */
public class Button {
    protected Vector2 position;
    protected Texture buttonTexture;
    protected Sprite buttonSprite;
    protected Label buttonLabel;
    protected BitmapFont bitmapFonttext;

    protected Tower tower;
    protected float height;
    protected float width;

    protected String buttonText;

    public Button(float x, float y, float w, float h, String filename) {
        position = new Vector2(x, y);
        width = w;
        height = h;
        buttonTexture = new Texture(Gdx.files.internal(filename));
        buttonSprite = new Sprite(buttonTexture);
        buttonSprite.setPosition(x / FinalStand.PPM, y / FinalStand.PPM);
        buttonSprite.setSize(w / FinalStand.PPM, h / FinalStand.PPM);
    }

    public Button(float x, float y, float w, float h, String text, Texture texture, Tower tower)
    {
        position = new Vector2(x, y);
        width = w;
        height = h;
        buttonText = new String(text);
        buttonTexture = texture;
        buttonSprite = new Sprite(buttonTexture);
        buttonSprite.setPosition(x, y);
        buttonSprite.setSize(width, height);

        this.tower = tower;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Texture getButtonTexture() {
        return buttonTexture;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public String getButtonText() {
        return buttonText;
    }

    public Sprite getButtonSprite() {
        return buttonSprite;
    }

    public void update(){};

    public Label getButtonLabel() {
        return buttonLabel;
    }

    public BitmapFont getBitmapFonttext() {
        return bitmapFonttext;
    }
}