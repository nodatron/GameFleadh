package com.finalstand.game.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
    private ArrayList<String> controls;
    private float fontX;
    private float fontY;

    public Stage stage;
    private Viewport viewport;

    private ArrayList<Label> labels;
    private BitmapFont font;


    public Controls(String filename) {
        labels = new ArrayList<Label>();
        stage = new Stage();
        viewport = new FitViewport(FinalStand.V_WIDTH / FinalStand.PPM, FinalStand.V_HEIGHT / FinalStand.PPM, new OrthographicCamera());
        font = new BitmapFont();
        fontX = 1;
        fontY = 2;
        font.getData().setScale(fontX, fontY);

//        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/myfont.ttf"));
//        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
//        parameter.size = 12;
//        BitmapFont font12 = generator.generateFont(parameter); // font size 12 pixels
//        generator.dispose(); // don't forget to dispose to avoid memory leaks!

        readControls(filename);
        Table table = new Table();
        table.center();
        table.setFillParent(true);

        for (int i = 0 ; i < labels.size() ; i ++) {
            table.add(labels.get(i)).expandX().padTop(fontY);
            table.row();
        }

        stage.addActor(table);
    }

    public void readControls(String filename) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));

            String line;
            while((line = br.readLine()) != null) {
                Label l = new Label(line, new Label.LabelStyle(font, Color.BLACK));
                labels.add(l);
            }

            br.close();

        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
