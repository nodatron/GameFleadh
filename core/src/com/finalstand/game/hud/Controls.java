package com.finalstand.game.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.finalstand.game.FinalStand;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Niall PC on 07/03/2016.
 */
public class Controls {
    public Stage stage;
    private Viewport viewport;

    private ArrayList<Label> labels;
    private BitmapFont font32;


    //creating the scene2d stuff for the text
    public Controls(String filename) {
        labels = new ArrayList<Label>();
        stage = new Stage();
        viewport = new FitViewport(FinalStand.V_WIDTH / FinalStand.PPM, FinalStand.V_HEIGHT / FinalStand.PPM, new OrthographicCamera());

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("OpenSans-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 26;
        font32 = generator.generateFont(parameter);


        readControls(filename);
        Table table = new Table();
        table.center();
        table.setFillParent(true);

        for (int i = 0 ; i < labels.size() ; i ++) {
            table.add(labels.get(i)).expandX();
            table.row();
        }

        stage.addActor(table);
        generator.dispose();
    }

    //reading in the instructions from the file
    public void readControls(String filename) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));

            String line;
            while((line = br.readLine()) != null) {
                Label l = new Label(line, new Label.LabelStyle(font32, Color.BLACK));
                labels.add(l);
            }

            br.close();

        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }
}
