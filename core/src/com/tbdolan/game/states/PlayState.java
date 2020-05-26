package com.tbdolan.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.tbdolan.game.FlappyPharah;
import com.tbdolan.game.scenes.Hud;
import com.tbdolan.game.sprites.Pharah;
import com.tbdolan.game.sprites.Tube;

import java.util.Random;

public class PlayState extends State {
    private static final int TUBE_SPACING = 125;
    private static final int TUBE_WIDTH = 52;
    private static final int TUBE_COUNT = 4;
    private static final int GROUND_Y_OFFSET = -50;

    private Array<Tube> tubes;

    private Pharah pharah;
    private Texture bg;
    private Texture ground;

    private Hud hud;

    private Vector2 groundPosition1, groundPosition2;
    private Sound dead;
    private Sound[] levelUp;

    int voiceLine;

    protected PlayState(GameStateManager gsm) {
        super(gsm);
        pharah = new Pharah(50, 300);
        bg = new Texture("flappy_bg.png");
        ground = new Texture("ground.png");
        groundPosition1 = new Vector2(cam.position.x - cam.viewportWidth/2, GROUND_Y_OFFSET);
        groundPosition2 = new Vector2(cam.position.x - cam.viewportWidth/2 + ground.getWidth(), GROUND_Y_OFFSET);

        hud = new Hud();

        cam.setToOrtho(false, FlappyPharah.WIDTH/2, FlappyPharah.HEIGHT/2);

        tubes = new Array<Tube>();
        for(int i = 1; i <= TUBE_COUNT; i++) {
            tubes.add(new Tube(i *(TUBE_SPACING + TUBE_WIDTH)));
        }
        dead = Gdx.audio.newSound(Gdx.files.internal("pharahdead.ogg"));
        levelUp = new Sound[3];
        levelUp[0] = Gdx.audio.newSound(Gdx.files.internal("empowered.ogg"));
        levelUp[1] = Gdx.audio.newSound(Gdx.files.internal("egyptian.ogg"));
        levelUp[2] = Gdx.audio.newSound(Gdx.files.internal("friendly.ogg"));

        voiceLine = 0;

    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()) {
            pharah.jump();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        updateGround();
        pharah.update(dt);
        cam.position.x = pharah.getPosition().x + 80;

        for(Tube tube : tubes) {
            if(cam.position.x - (cam.viewportWidth/2) > tube.getPosTopTube().x + tube.getTopTube().getWidth()) {
                tube.reposition(tube.getPosTopTube().x + (TUBE_WIDTH + TUBE_SPACING)*TUBE_COUNT);
            }

            if(tube.collides(pharah.getBounds())) {
                gsm.set(new MenuState(gsm, true));
                dead.play();
                break;
            }
            if(pharah.getBounds().x >= tube.getPosTopTube().x && pharah.getBounds().x <= tube.getPosTopTube().x + tube.getTopTube().getWidth() && !tube.getPassthrough()) {
                hud.update(dt);
                if(hud.getScore() % 10 == 0) {
                    voiceLine++;
                    levelUp[voiceLine % 3].play();
                }
                tube.setPassthrough();
            }
        }

        if(pharah.getPosition().y <= ground.getHeight() + GROUND_Y_OFFSET) {
            gsm.set(new MenuState(gsm, true));
            dead.play();
        }
        cam.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(bg, cam.position.x - (cam.viewportWidth / 2), 0);
        sb.draw(pharah.getPharahAnimation(), pharah.getPosition().x, pharah.getPosition().y);
        for(Tube tube : tubes) {
            sb.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
            sb.draw(tube.getBottomTube(), tube.getPosBottomTube().x, tube.getPosBottomTube().y);
        }
        sb.draw(ground, groundPosition1.x, groundPosition1.y);
        sb.draw(ground, groundPosition2.x, groundPosition2.y);
        sb.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
        sb.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        pharah.dispose();
        for(Tube tube : tubes) {
            tube.dispose();
        }
        ground.dispose();
        System.out.println("Play State Disposed");
    }

    private void updateGround() {
        if(cam.position.x - (cam.viewportWidth/2) > groundPosition1.x + ground.getWidth()) {
            groundPosition1.add(ground.getWidth()*2, 0);
        }

        if(cam.position.x - (cam.viewportWidth/2) > groundPosition2.x + ground.getWidth()) {
            groundPosition2.add(ground.getWidth()*2, 0);
        }
    }
}
