package com.finalstand.game.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.finalstand.game.FinalStand;
import com.finalstand.game.Screens.PlayScreen;

/**
 * Created by Keith on 19/04/2016.
 */
public class CancelButton extends Button {

    public CancelButton() {
        super((FinalStand.V_WIDTH / FinalStand.PPM) * 0.8f, 0, (FinalStand.V_WIDTH / FinalStand.PPM) * 0.2f,
                PlayScreen.ui.getHeight(), "x.png");
    }
}
