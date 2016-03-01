package com.finalstand.game.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.finalstand.game.Screens.ControlScreen;
import com.finalstand.game.Screens.MenuScreen;
import com.finalstand.game.Screens.SplashScreen;

/**
 * Created by Niall on 2/26/2016.
 */
public class ResumeButton extends Button {

    public ResumeButton (String filename, float x, float y, float w, float h) {
        super(x, y, w, h, filename);
    }

    @Override
    public void update() {
        if(Gdx.input.justTouched()) {
            Vector3 mouse = MenuScreen.getWorldMousePos();

            if(mouse.x > position.x && mouse.x < position.x + width &&
                    mouse.y > position.y && mouse.y < position.y + height) {
                MenuScreen.resumeButtonPressed();
                System.out.println("here");
            }
        }
    }
}
