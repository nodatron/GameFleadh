package com.finalstand.game.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.finalstand.game.Screens.PlayScreen;
import com.finalstand.game.sprites.towers.Tower;
<<<<<<< HEAD
=======
import com.finalstand.game.Screens.PlayScreen;
>>>>>>> origin/keith

/**
 * Created by Keith on 18/02/2016.
 */
public class SellButton extends Button {
    private float sellPrice;

    public SellButton(float sellPrice, Tower tower)
    {
<<<<<<< HEAD
        super((PlayScreen.ui.getWidth() / 8) * 7, PlayScreen.ui.getHeight(), PlayScreen.ui.getWidth() / 8,
=======
        super((PlayScreen.ui.getWidth() / 4) * 3, PlayScreen.ui.getHeight(), PlayScreen.ui.getWidth() / 4,
>>>>>>> origin/keith
                PlayScreen.ui.getHeight() / 2, "Upgrade: " + sellPrice, tower);
        this.sellPrice = sellPrice;
    }

    @Override
    public void update()
    {
        if(Gdx.input.justTouched())
        {
            Vector3 mouse = PlayScreen.getWorldMousePos();
            if(mouse.x > position.x && mouse.x < position.x + width &&
                    mouse.y > position.y && mouse.y < position.y + height)
            {
                PlayScreen.towers.remove(tower);
            }
        }
    }
}
