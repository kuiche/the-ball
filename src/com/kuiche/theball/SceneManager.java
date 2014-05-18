package com.kuiche.theball;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.color.Color;

import android.hardware.SensorManager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.kuiche.theball.game.BallSprite;
import com.kuiche.theball.menu.GoButtonSprite;

public class SceneManager {

	public final FixtureDef FIXTURE_BALL = PhysicsFactory.createFixtureDef(10f,
			0.1f, 0f);
	public final FixtureDef FIXTURE_WALL = PhysicsFactory.createFixtureDef(0f,
			0f, 0f);

	public SceneManager(BaseGameActivity activity, Engine engine, Camera camera) {
		this.activity = activity;
		this.engine = engine;
		this.camera = camera;
	}

	private AllScenes currentScene;
	private final BaseGameActivity activity;
	private final SceneManager manager = this;
	private Engine engine;
	private Camera camera;
	private BitmapTextureAtlas splashTA, goTA, ballTA;
	private ITextureRegion splashTR, goTR, ballTR;
	private Scene splashScene, gameScene, menuScene;
	public PhysicsWorld physWorld;

	public enum AllScenes {
		SPLASH, MENU, GAME
	}

	public void loadSplashResources() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		splashTA = new BitmapTextureAtlas(activity.getTextureManager(), 256,
				256);
		splashTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				splashTA, activity, "splash.png", 0, 0);
		splashTA.load();

	}

	public void loadGameResources() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		ballTA = new BitmapTextureAtlas(activity.getTextureManager(), 64, 64);
		ballTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(ballTA,
				activity, "red_ball.png", 0, 0);
		ballTA.load();

		physWorld = new PhysicsWorld(
				new Vector2(0, SensorManager.GRAVITY_PLUTO), false);

	}

	public void loadMenuResources() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		goTA = new BitmapTextureAtlas(activity.getTextureManager(), 512, 256);
		goTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(goTA,
				activity, "go.png", 0, 0);
		goTA.load();
	}

	public Scene createSplashScene() {
		splashScene = new Scene();
		splashScene.setBackground(new Background(0, 0, 0));

		Sprite splashIcon = new Sprite(0, 0, splashTR,
				activity.getVertexBufferObjectManager());

		// center splash
		splashIcon.setPosition((camera.getWidth() - splashIcon.getWidth()) / 2,
				(camera.getHeight() - splashIcon.getHeight()) / 2);

		splashScene.attachChild(splashIcon);
		return splashScene;
	}

	public Scene createMenuScene() {
		menuScene = new Scene();
		menuScene.setBackground(new Background(0, 200, 60));

		GoButtonSprite menuIcon = new GoButtonSprite(0f, 0f, goTR,
				activity.getVertexBufferObjectManager(), this);

		// centre icon
		menuIcon.setPosition((camera.getWidth() - menuIcon.getWidth()) / 2,
				(camera.getHeight() - menuIcon.getHeight()) / 2);

		menuScene.attachChild(menuIcon);

		menuScene.registerTouchArea(menuIcon);
		menuScene.setTouchAreaBindingOnActionDownEnabled(true);
		return menuScene;
	}

	public Scene createGameScene() {

		gameScene = new Scene();
		gameScene.registerUpdateHandler(physWorld);

		gameScene.setBackground(new Background(0, 50, 200));
		BallSprite sBall = new BallSprite(0f, 0f, ballTR,
				activity.getVertexBufferObjectManager(), this);

		sBall.setPosition((camera.getWidth() - sBall.getWidth()) / 2,
				(camera.getHeight() - sBall.getHeight()) / 2);

		Rectangle floor = new Rectangle(0, camera.getHeight() - 15,
				camera.getWidth(), 15, activity.getVertexBufferObjectManager());
		floor.setColor(new Color(1, 1, 1));

		Body ballBody = PhysicsFactory.createCircleBody(physWorld, sBall,
				BodyType.DynamicBody, FIXTURE_BALL);
		ballBody.setUserData("ball");

		Body floorBody = PhysicsFactory.createBoxBody(physWorld, floor,
				BodyType.StaticBody, FIXTURE_WALL);
		floorBody.setUserData("floor");

		sBall.setBody(ballBody);
		physWorld.registerPhysicsConnector(new PhysicsConnector(sBall,
				ballBody, true, false));

		physWorld.setContactListener(new ContactListener() {

			@Override
			public void beginContact(final Contact contact) {
				final Body bodyA = contact.getFixtureA().getBody();
				final Body bodyB = contact.getFixtureB().getBody();

				if ((bodyA.getUserData() == "ball"
						&& bodyB.getUserData() == "floor")
						|| (bodyA.getUserData() == "floor"
						&& bodyB.getUserData() == "ball")) {
				manager.engine.setScene(manager.createMenuScene());
				}
			}

			@Override
			public void endContact(Contact contact) {
				// TODO Auto-generated method stub

			}

			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {
				// TODO Auto-generated method stub

			}

			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {
				// TODO Auto-generated method stub

			}

		});

		gameScene.attachChild(sBall);
		gameScene.attachChild(floor);
		gameScene.registerTouchArea(sBall);
		gameScene.setTouchAreaBindingOnActionDownEnabled(true);

		return gameScene;
	}

	public AllScenes getCurrentScene() {
		return currentScene;
	}

	public Engine getEngine() {
		return engine;
	}

	public void setCurrentScene(AllScenes currentScene) {
		this.currentScene = currentScene;
		switch (currentScene) {
		case SPLASH:
			break;
		case MENU:
			this.engine.setScene(menuScene);
			break;
		case GAME:
			this.engine.setScene(gameScene);
			break;
		default:
			break;
		}
	}

	public Camera getCamera() {
		return camera;
	}
}
