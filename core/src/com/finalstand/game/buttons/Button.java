package com.finalstand.game.buttons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.finalstand.game.FinalStand;
import com.finalstand.game.sprites.towers.Tower;

/**
 * Created by Keith on 18/02/2016.
 */
public class Button {
    protected Vector2 position;
    protected Texture buttonTexture;
    protected Sprite buttonSprite;
    protected TextButton button;
    protected TextButton.TextButtonStyle style;
    protected BitmapFont font;
    protected Skin skin;
    protected TextureAtlas atlas;

    protected Tower tower;
    protected float height;
    protected float width;

    protected String buttonText;

    public Button(float x, float y, float w, float h, String filename) {
        position = new Vector2(x, y);
        width = w;
        height = h;
        buttonTexture = new Texture(filename);
        buttonSprite = new Sprite(buttonTexture);
        buttonSprite.setPosition(x / FinalStand.PPM, y / FinalStand.PPM);
        buttonSprite.setSize(w / FinalStand.PPM, h / FinalStand.PPM);
    }

    public Button(float x, float y, float w, float h, String text, Tower tower)
    {
        position = new Vector2(x, y);
        width = w;
        height = h;
        buttonText = new String(text);
        buttonTexture = new Texture("background.jpg");
        buttonSprite = new Sprite(buttonTexture);
        buttonSprite.setPosition(x, y);
        buttonSprite.setSize(width, height);

        /*font = new BitmapFont();
        skin = new Skin();
        atlas = new TextureAtlas();
        style = new TextButton.TextButtonStyle();
        style.font = font;
        button = new TextButton(text, style);*/
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
}
