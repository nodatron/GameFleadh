package com.finalstand.game.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
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

    private Integer map;
    public Stage stage;
    private Viewport viewport;

    private Label healthLabel;
    private Label scoreLabel;
    private Label mapLabel;
    private Label roundLabel;
    private Table table;

    private BitmapFont font;

    public Hud(Integer round, Integer map, Integer health, Integer score) {
        stage = new Stage();
        this.map = map;
        viewport = new FitViewport(FinalStand.V_WIDTH, FinalStand.V_HEIGHT, new OrthographicCamera());


        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("OpenSans-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 32;
        font = generator.generateFont(parameter);

        table = new Table();
        table.right();
        table.setFillParent(true);

        scoreLabel = new Label(String.format("Score: %06d", FinalStand.score), new Label.LabelStyle(font, Color.BLUE));
        healthLabel = new Label(String.format("Health: %03d", FinalStand.health), new Label.LabelStyle(font, Color.BLUE));
        roundLabel = new Label(String.format("Round: %02d", FinalStand.round), new Label.LabelStyle(font, Color.BLUE));
        mapLabel = new Label(String.format("Map: %03d", FinalStand.mapNumber), new Label.LabelStyle(font, Color.BLUE));

        table.add(mapLabel);
        table.row();
        table.add(roundLabel);
        table.row();
        table.add(healthLabel);
        table.row();
        table.add(scoreLabel);
        stage.addActor(table);
    }

    //updates the hud
    public void update() {
        Array<Actor> t = stage.getActors();
        Array<Label> l = new Array<Label>();
        if(t.get(0) instanceof Table) {
            Array<Cell> c = ((Table) t.get(0)).getCells();
            for (Cell ce : c) {
                if (ce.getActor() instanceof Label) {
                    l.add(((Label) ce.getActor()));
                }
            }
            l.get(2).setText(String.format("Map: %03d", FinalStand.mapNumber));
            l.get(3).setText(String.format("Round: %02d", FinalStand.round));
            l.get(1).setText(String.format("Health: %03d", FinalStand.health));
            l.get(0).setText(String.format("Score: %06d", FinalStand.score));
//            ((Label) c.getActor()).setText("newTexthere");
//            ((Table)t.get(0)).invalidate();
        }

    }

}
