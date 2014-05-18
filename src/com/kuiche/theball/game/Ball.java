package com.kuiche.theball.game;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.BaseGameActivity;

import com.badlogic.gdx.physics.box2d.Body;

public class Ball {

	public static final String TEXTURE_RED_BALL_PATH = "gfx/ball_red.png";
	public static final String TEXTURE_BLUE_BALL_PATH = "gfx/ball_blue.png";
	public static final String TEXTURE_YELLOW_BALL_PATH = "gfx/ball_yellow.png";
	public static final String TEXTURE_GREEN_BALL_PATH = "gfx/ball_green.png";

	private final BaseGameActivity activity;
	private Body body;
	private Sprite sprite;
	private String textureAtlasPath;
	private BitmapTextureAtlas ballTA;
	private ITextureRegion ballTR;

	public Ball(String AtlasPath, BaseGameActivity activity) {
		this.textureAtlasPath = AtlasPath;
		this.activity = activity;

	}

	public void loadResources() {
		ballTA = new BitmapTextureAtlas(activity.getTextureManager(), 64, 64);
		ballTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(ballTA,
				activity, textureAtlasPath, 0, 0);
		ballTA.load();
	}
	
	public void create(){
		
	}

}
