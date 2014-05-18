package com.kuiche.theball.menu;

import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.kuiche.theball.SceneManager;

public class GoButtonSprite extends Sprite{
	
	SceneManager manager;
	
	public GoButtonSprite(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager vertexBufferObjectManager, SceneManager manager) {
		super(pX, pY, pTextureRegion, vertexBufferObjectManager);
		
		this.manager = manager;
	}
	
public boolean onAreaTouched(final TouchEvent event, final float pTouchX, final float pTouchY){
		
		int eventAction = event.getAction();
		switch(eventAction){
		case TouchEvent.ACTION_DOWN:
			break;
		case TouchEvent.ACTION_UP:
			manager.loadSplashResources();
			manager.getEngine().setScene(manager.createSplashScene());
			
			manager.loadGameResources();
			manager.getEngine().setScene(manager.createGameScene());
			break;
		case TouchEvent.ACTION_MOVE:
			break;
		default:
			break;
		}

		
		return true;
	}

}
