package com.finalstand.game.player;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.finalstand.game.FinalStand;

/**
 * Created by Niall PC on 23/02/2016.
 */
public class Hud {

    private Integer health;
    private Integer score;
    private Integer round;
    private Integer map;
    public Stage stage;
    private Viewport viewport;

    Label healthLabel;
    Label scoreLabel;
    Label mapLabel;
    Label roundLabel;

    public Hud(SpriteBatch sb) {
        stage = new Stage();

        score = 0;
        health = 10;
        round = 1;
        map = 1;
        viewport = new FitViewport(FinalStand.V_WIDTH, FinalStand.V_HEIGHT, new OrthographicCamera());

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.BLUE));
        healthLabel = new Label(String.format("%03d", health), new Label.LabelStyle(new BitmapFont(), Color.BLUE));
        roundLabel = new Label(String.format("%02d", round), new Label.LabelStyle(new BitmapFont(), Color.BLUE));
        mapLabel = new Label(String.format("%02d", map), new Label.LabelStyle(new BitmapFont(), Color.BLUE));

        table.add(mapLabel).expandX().padTop(10);
        table.add(healthLabel).expandX().padTop(10);
        table.add(scoreLabel).expandX().padTop(10);
        table.row();
        table.add(roundLabel).expandX();

        stage.addActor(table);
    }

}
