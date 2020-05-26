package com.tbdolan.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tbdolan.game.FlappyPharah;

public class MenuState extends State {
    private Texture background;
    private Texture play;
    private Texture gameOverText;
    private float playLocX;
    private float playLocY;
    private boolean gameOver;

    public MenuState(GameStateManager gsm, boolean gameOver) {
        super(gsm);
        background = new Texture("flappy_bg.png");
        play = new Texture("play.png");
        gameOverText = new Texture("gameover.png");
        playLocX = FlappyPharah.WIDTH/2 - play.getWidth()/2;
        playLocY = FlappyPharah.HEIGHT/2;
        this.gameOver = gameOver;
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()
        && Gdx.input.getX() >= playLocX && Gdx.input.getX() <= playLocX + play.getWidth()
        && Gdx.input.getY() <= playLocY && Gdx.input.getY() >= playLocY - play.getHeight()) {
            gsm.set(new PlayState(gsm));
            System.out.println("x: " + Gdx.input.getX());
            System.out.println("y: " + Gdx.input.getY());
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background, 0, 0, FlappyPharah.WIDTH, FlappyPharah.HEIGHT);
        if(gameOver) {
            sb.draw(gameOverText, FlappyPharah.WIDTH/2 - gameOverText.getWidth()/2, FlappyPharah.HEIGHT - 200);
        }
        sb.draw(play, playLocX, playLocY);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        play.dispose();
        System.out.println("Menu State Disposed");
    }
}
