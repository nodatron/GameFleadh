package com.finalstand.game.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.finalstand.game.FinalStand;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;


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

    private Label healthLabel;
    private Label scoreLabel;
    private Label mapLabel;
    private Label roundLabel;
    private Label healthLabelHeading;
    private Label MapRoundLabelHeading;
    private Table table;

    public Hud(Integer round, Integer map, Integer health, Integer score) {
        stage = new Stage();
        this.round = round;
        this.map = map;
        viewport = new FitViewport(FinalStand.V_WIDTH, FinalStand.V_HEIGHT, new OrthographicCamera());

        table = new Table();
        table.top();
        table.setFillParent(true);

        scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.BLUE));
        healthLabel = new Label(String.format("%03d", health), new Label.LabelStyle(new BitmapFont(), Color.BLUE));
        roundLabel = new Label(String.format("%02d", round), new Label.LabelStyle(new BitmapFont(), Color.BLUE));
        mapLabel = new Label(String.format("%03d", map), new Label.LabelStyle(new BitmapFont(), Color.BLUE));
        healthLabelHeading = new Label("Health", new Label.LabelStyle(new BitmapFont(), Color.BLUE));
        MapRoundLabelHeading = new Label("Map/Round", new Label.LabelStyle(new BitmapFont(), Color.BLUE));


        table.add(mapLabel).expandX().padTop(5);
        table.add(healthLabelHeading).expandX().padTop(5);
        table.add(healthLabel).expandX().padTop(5);
        table.add(scoreLabel).expandX().padTop(5);
        mapLabel = new Label(String.format("%02d", map), new Label.LabelStyle(new BitmapFont(), Color.BLUE));

        table.add(mapLabel).expandX().padTop(10);
        table.add(healthLabel).expandX().padTop(10);
        table.add(scoreLabel).expandX().padTop(10);
        table.row();
        table.add(roundLabel).expandX();

        stage.addActor(table);
    }

    //updates the hud
    public void update() {
        Array<Actor> t = stage.getActors();
        if(t.get(0) instanceof Table) {
            Array<Cell> c = ((Table) t.get(0)).getCells();
            for (Cell ce : c) {
                if (ce.getActor() instanceof Label)
                    ((Label) ce.getActor()).setText("newTexthere");
            }
            ((Table)t.get(0)).invalidate();
        }
    }

}
