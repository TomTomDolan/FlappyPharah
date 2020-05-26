package com.tbdolan.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Pharah {
    private static final int GRAVITY = -15;
    private static final int MOVEMENT = 100;

    private Vector3 position;
    private Vector3 velocity;
    private Rectangle bounds;

    private Texture animation;

    private Animation pharahAnimation;


    public Pharah(int x, int y) {
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);
        animation = new Texture("birdanimation.png");
        pharahAnimation = new Animation(new TextureRegion(animation), 3, 0.5f);
        bounds = new Rectangle(x, y, animation.getWidth() / 3, animation.getHeight() / 3);
    }

    public Vector3 getPosition() {
        return position;
    }

    public TextureRegion getPharahAnimation() {
        return pharahAnimation.getFrame();
    }

    public void update(float dt) {
        pharahAnimation.update(dt);
        if(position.y > 0) {
            velocity.add(0, GRAVITY, 0);
        }
        velocity.scl(dt);
        position.add(MOVEMENT*dt, velocity.y, 0);
        if(position.y < 0) {
            position.y = 0;
        }
        velocity.scl(1/dt);
        bounds.setPosition(position.x, position.y);
    }

    public void jump() {
        velocity.y = 250;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void dispose() {
        animation.dispose();
    }
}
