package com.finalstand.game.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;
import com.finalstand.game.Screens.ControlScreen;
import com.finalstand.game.Screens.SplashScreen;

/**
 * Created by Niall PC on 25/02/2016.
 */
public class ExitButton extends Button {

    public ExitButton(String filename, float x, float y, float w, float h) {
        super(x, y, w, h, filename);
    }

    @Override
    public void update() {
        if(Gdx.input.justTouched()) {
            Vector3 mouse = SplashScreen.getWorldMousePos();

            if(mouse.x > position.x && mouse.x < position.x + width &&
                    mouse.y > position.y && mouse.y < position.y + height) {
                Gdx.app.exit();
            }
        }

        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }
}
