package com.tbdolan.game.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tbdolan.game.FlappyPharah;

public class Hud {

    public Stage stage;
    private Viewport viewport;

    private static Integer score;
    private static Label scoreLabel;

    public Hud() {
        viewport = new FitViewport(FlappyPharah.WIDTH, FlappyPharah.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport);
        score = 0;

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        scoreLabel = new Label(String.format("%03d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel.setFontScale(2, 2);

        table.add(scoreLabel).expandX().padTop(10);

        stage.addActor(table);
    }

    public void update(float dt) {
        score += 1;
        scoreLabel.setText(String.format("%03d", score));
    }

    public void dispose() {
        stage.dispose();
    }

    public int getScore() {
        return score;
    }

}
