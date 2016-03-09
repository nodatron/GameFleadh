package com.finalstand.game.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.finalstand.game.FinalStand;
import com.finalstand.game.Screens.PlayScreen;
import com.finalstand.game.sprites.towers.Tower;

/**
 * Created by Keith on 18/02/2016.
 */
public class SellButton extends Button {
    private float sellPrice;

    public SellButton(float sellPrice, Tower tower)
    {

        super((PlayScreen.ui.getWidth() / 4) * 3, PlayScreen.ui.getHeight(), PlayScreen.ui.getWidth() / 4,
                PlayScreen.ui.getHeight() / 2, "Sell: " + sellPrice, new Texture("buttons/Sell.png"), tower);
        this.sellPrice = sellPrice;
    }

    @Override
    public void update()
    {
        if(Gdx.input.justTouched())
        {
            Vector3 mouse = PlayScreen.getWorldMousePos();
            if(mouse.x > position.x && mouse.x < position.x + width &&
                    mouse.y > position.y + (height / 2) && mouse.y < position.y + height + (height / 2))
            {
                PlayScreen.towers.remove(tower);
                FinalStand.score += sellPrice;
            }
        }
    }
}