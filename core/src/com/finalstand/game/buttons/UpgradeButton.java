package com.finalstand.game.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.finalstand.game.FinalStand;
import com.finalstand.game.sprites.towers.Tower;
import com.finalstand.game.states.PlayState;

/**
 * Created by Keith on 18/02/2016.
 */
public class UpgradeButton extends Button{
    private float cost;

    public UpgradeButton(float cost, Tower tower)
    {
        super((PlayState.ui.getWidth() / 4) * 3, PlayState.ui.getHeight(), PlayState.ui.getWidth() / 8,
                PlayState.ui.getHeight() / 2, "Upgrade: " + cost, "background.jpg", tower);
        this.cost = cost;
    }

    public void update()
    {
        if(Gdx.input.justTouched())
        {
            Vector3 mouse = PlayState.getWorldMousePos();
            if(mouse.x > position.x && mouse.x < position.x + width &&
                    mouse.y > position.y && mouse.y < position.y + height)
            {
                tower.upgrade();
            }
        }
    }
}
