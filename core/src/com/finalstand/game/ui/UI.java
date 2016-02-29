package com.finalstand.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.finalstand.game.Screens.PlayScreen;
import com.finalstand.game.Screens.PlayScreen;
import com.finalstand.game.sprites.towers.AOETower;
import com.finalstand.game.sprites.towers.DOTTower;
import com.finalstand.game.sprites.towers.LaserTower;
import com.finalstand.game.sprites.towers.SingleShotTower;

/**
 * Created by Keith on 16/02/2016.
 */
public class UI {
    private static final int NUM_OF_BOUNDS = 4; //number of options in the UI
    private static final int OPTION1_COST = 100;
    private static final int OPTION2_COST = 200;
    private static final int OPTION3_COST = 250;
    private static final int OPTION4_COST = 400;

    private Vector2 position;
    private float height, width;

    private Texture background;
    private Texture option1Texture;
    private Texture option2Texture;
    private Texture option3Texture;
    private Texture option4Texture;
    private float textureWidth;
    private float textureHeight;

    private Vector2 option1Pos;
    private Vector2 option2Pos;
    private Vector2 option3Pos;
    private Vector2 option4Pos;

    private float boundsHeight;
    private float boundsWidth;

    public UI(float x, float y, float w, float h)
    {
        position = new Vector2(x, y);
        height = h;
        width = w;

        background = new Texture("background.jpg");
        option1Texture = new Texture("towers/singleshot_level1.png");
        option2Texture = new Texture("towers/aoe_level1.png");
        option3Texture = new Texture("towers/dot_level1.png");
        option4Texture = new Texture("towers/laserTower_level1.png");

        boundsWidth = width / NUM_OF_BOUNDS;
        boundsHeight = height;

        option1Pos = new Vector2(boundsWidth / 4, boundsHeight / 6);
        option2Pos = new Vector2((boundsWidth / 6) + boundsWidth, boundsHeight / 6);
        option3Pos = new Vector2((boundsWidth / 6) + (boundsWidth * 2), boundsHeight / 6);
        option4Pos = new Vector2((boundsWidth / 6) + (boundsWidth * 3), boundsHeight / 6);
    }

    public void optionClicked(Vector3 mousePos)
    {
        for(int counter = 0; counter < NUM_OF_BOUNDS; counter++)
        {
            if(mousePos.x < boundsWidth * (counter + 1) && mousePos.x > boundsWidth * counter && mousePos.y >= position.y && mousePos.y <= position.y + boundsHeight)
            {
                optionPicked(counter);
            }
        }
    }

    public void optionPicked(int optionNum)
    {
        PlayScreen.optionChosen = true;
        PlayScreen.optionTexture.setTowerOption(optionNum);
        PlayScreen.optionTexture.setRotation(0.0f);

        switch(optionNum)
        {
            case 0: {
                PlayScreen.optionTexture.setTexture(option1Texture, SingleShotTower.size.x, SingleShotTower.size.y);
                break;
            }
            case 1: {PlayScreen.optionTexture.setTexture(option2Texture, AOETower.size.x, AOETower.size.y); break;}
            case 2: {PlayScreen.optionTexture.setTexture(option3Texture, DOTTower.size.x, DOTTower.size.y); break;}
            case 3: {PlayScreen.optionTexture.setTexture(option4Texture, LaserTower.size.x, LaserTower.size.y); break;}
        }
    }

    public Texture getBackground()
    {
        return background;
    }

    public Texture getOption1Texture() {
        return option1Texture;
    }

    public Texture getOption2Texture() {
        return option2Texture;
    }

    public Texture getOption3Texture() {
        return option3Texture;
    }

    public Texture getOption4Texture() {
        return option4Texture;
    }

    public Vector2 getOption1Pos() {
        return option1Pos;
    }

    public Vector2 getOption2Pos() {
        return option2Pos;
    }

    public Vector2 getOption3Pos() {
        return option3Pos;
    }

    public Vector2 getOption4Pos() {
        return option4Pos;
    }

    public Vector2 getPosition() {
        return position;
    }

    public float getHeight() {
        return height;
    }

    public float getWidth() {
        return width;
    }

    public float getBoundsHeight() {
        return boundsHeight;
    }

    public float getBoundsWidth() {
        return boundsWidth;
    }

    public float getTextureWidth() {
        return textureWidth;
    }

    public float getTextureHeight() {
        return textureHeight;
    }
}
