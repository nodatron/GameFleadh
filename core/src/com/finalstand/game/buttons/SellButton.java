package com.finalstand.game.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.finalstand.game.sprites.towers.Tower;
import com.finalstand.game.states.PlayState;

/**
 * Created by Keith on 18/02/2016.
 */
public class SellButton extends Button {
    private float sellPrice;

    public SellButton(float sellPrice, Tower tower)
    {
        super((PlayState.ui.getWidth() / 8) * 7, PlayState.ui.getHeight(), PlayState.ui.getWidth() / 8,
                PlayState.ui.getHeight() / 2, "Upgrade: " + sellPrice, "background.jpg", tower);
        this.sellPrice = sellPrice;
    }

    public void update()
    {
        if(Gdx.input.justTouched())
        {
            Vector3 mouse = PlayState.getWorldMousePos();
            if(mouse.x > position.x && mouse.x < position.x + width &&
                    mouse.y > position.y && mouse.y < position.y + height)
            {
                PlayState.towers.remove(tower);
            }
        }
    }
}
