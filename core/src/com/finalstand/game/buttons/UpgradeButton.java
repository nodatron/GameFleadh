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
public class UpgradeButton extends Button{
    private float cost;

    public UpgradeButton(float cost, Tower tower)
    {
        super(PlayScreen.ui.getWidth() / 2, PlayScreen.ui.getHeight(), PlayScreen.ui.getWidth() / 4,
                PlayScreen.ui.getHeight() / 2, "Upgrade: " + cost, new Texture("buttons/Upgrade.png"), tower);
        this.cost = cost;
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
                if(FinalStand.score >= cost) {
                    tower.upgrade();
                    FinalStand.score -= cost;
                }
            }
        }
    }
}