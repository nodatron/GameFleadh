package com.finalstand.game.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.finalstand.game.Screens.ControlScreen;
import com.finalstand.game.Screens.FailureScreen;
import com.finalstand.game.Screens.PlayScreen;
import com.finalstand.game.Screens.SplashScreen;
import com.finalstand.game.Screens.VictoryScreen;

/**
 * Created by Niall PC on 25/02/2016.
 */
public class BackButton extends Button {

    private String whatScreenOn;
    public BackButton(String filename, float x, float y, float w, float h, String whatScreenOn) {
        super(x, y, w, h, filename);
        this.whatScreenOn = whatScreenOn;
    }

    public String getWhatScreenOn() {
        return whatScreenOn;
    }

    @Override
    public void update() {
        if(Gdx.input.justTouched()) {
            Vector3 mouse = null;
            if(getWhatScreenOn().equals("Fail")) {
                mouse = FailureScreen.getWorldMousePos();
            } else if(getWhatScreenOn().equals("Control")) {
                mouse = ControlScreen.getWorldMousePos();
            } else if(getWhatScreenOn().equals("Victory")) {
                mouse = VictoryScreen.getWorldMousePos();
            }

            if(mouse.x > position.x && mouse.x < position.x + width &&
                    mouse.y > position.y && mouse.y < position.y + height) {
                if(getWhatScreenOn().equals("Fail")) {
                    FailureScreen.backButtonPressed();
                } else {
                    ControlScreen.backButtonPressed();
                }
            }
        }
    }
}
