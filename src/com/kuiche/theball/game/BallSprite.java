package com.kuiche.theball.game;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.physics.box2d.Body;
import com.kuiche.theball.SceneManager;

public class BallSprite extends Sprite {

	SceneManager manager;
	private Body body;

	public BallSprite(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager vertexBufferObjectManager,
			SceneManager manager) {
		super(pX, pY, pTextureRegion, vertexBufferObjectManager);

		this.manager = manager;
	}

	@Override
	public boolean onAreaTouched(final TouchEvent event, final float pTouchX,
			final float pTouchY) {

		int eventAction = event.getAction();
		switch (eventAction) {
		case TouchEvent.ACTION_DOWN:
			Camera cam = manager.getCamera();
			body.setTransform((float) (this.getWidth() / 2 + (Math.random()
					* (cam.getWidth() - this.getWidth()))) / 32,
					(float) (this.getY() - 100) / 32, 0f);

			break;
		case TouchEvent.ACTION_UP:
			break;
		case TouchEvent.ACTION_MOVE:
			break;
		default:
			break;
		}

		return true;
	}

	public void setBody(Body body) {
		this.body = body;
	}

}
