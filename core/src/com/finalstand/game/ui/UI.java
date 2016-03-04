package com.finalstand.game.ui;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.finalstand.game.Screens.PlayScreen;
import com.finalstand.game.sprites.towers.AOETower;
import com.finalstand.game.sprites.towers.DOTTower;
import com.finalstand.game.sprites.towers.LaserTower;
import com.finalstand.game.sprites.towers.SingleShotTower;
import com.finalstand.game.sprites.traps.Trap;


/**
 * Created by Keith on 16/02/2016.
 */
public class UI {
    private static final int NUM_OF_BOUNDS = 8; //number of options in the UI
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
    private Texture option5Texture;
    private Texture option6Texture;
    private Texture option7Texture;
    private Texture option8Texture;

    private Vector2 option1Pos;
    private Vector2 option2Pos;
    private Vector2 option3Pos;
    private Vector2 option4Pos;
    private Vector2 option5Pos;
    private Vector2 option6Pos;
    private Vector2 option7Pos;
    private Vector2 option8Pos;

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
        option5Texture = new Texture("traps/barricade2.png");
        option6Texture = new Texture("traps/bomb.png");
        option7Texture = new Texture("traps/glue2.png");
        option8Texture = new Texture("traps/spikes3.png");

        boundsWidth = width / NUM_OF_BOUNDS;
        boundsHeight = height;

        option1Pos = new Vector2(boundsWidth / 4, boundsHeight / 6);
        option2Pos = new Vector2((boundsWidth / 6) + boundsWidth, boundsHeight / 6);
        option3Pos = new Vector2((boundsWidth / 6) + (boundsWidth * 2), boundsHeight / 6);
        option4Pos = new Vector2((boundsWidth / 6) + (boundsWidth * 3), boundsHeight / 6);
        option5Pos = new Vector2((boundsWidth / 6) + (boundsWidth * 4), boundsHeight / 6);
        option6Pos = new Vector2((boundsWidth / 6) + (boundsWidth * 5), boundsHeight / 6);
        option7Pos = new Vector2((boundsWidth / 6) + (boundsWidth * 6), boundsHeight / 6);
        option8Pos = new Vector2((boundsWidth / 6) + (boundsWidth * 7), boundsHeight / 6);
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
            case 0: {PlayScreen.optionTexture.setTexture(option1Texture, SingleShotTower.size.x, SingleShotTower.size.y); break;}
            case 1: {PlayScreen.optionTexture.setTexture(option2Texture, AOETower.size.x, AOETower.size.y); break;}
            case 2: {PlayScreen.optionTexture.setTexture(option3Texture, DOTTower.size.x, DOTTower.size.y); break;}
            case 3: {PlayScreen.optionTexture.setTexture(option4Texture, LaserTower.size.x, LaserTower.size.y); break;}
            case 4: {PlayScreen.optionTexture.setTexture(option5Texture, Trap.trapSize.x, Trap.trapSize.y); break;}
            case 5: {PlayScreen.optionTexture.setTexture(option6Texture, Trap.trapSize.x, Trap.trapSize.y); break;}
            case 6: {PlayScreen.optionTexture.setTexture(option7Texture, Trap.trapSize.x, Trap.trapSize.y); break;}
            case 7: {PlayScreen.optionTexture.setTexture(option8Texture, Trap.trapSize.x, Trap.trapSize.y); break;}
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

    public Texture getOption5Texture() {
        return option5Texture;
    }

    public Texture getOption6Texture() {
        return option6Texture;
    }

    public Texture getOption7Texture() {
        return option7Texture;
    }

    public Texture getOption8Texture() {
        return option8Texture;
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

    public Vector2 getOption5Pos() {
        return option5Pos;
    }

    public Vector2 getOption6Pos() {
        return option6Pos;
    }

    public Vector2 getOption7Pos() {
        return option7Pos;
    }

    public Vector2 getOption8Pos() {
        return option8Pos;
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
}
