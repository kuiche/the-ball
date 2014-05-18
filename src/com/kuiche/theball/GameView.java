package com.kuiche.theball;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.BaseGameActivity;

import com.kuiche.theball.SceneManager.AllScenes;

/**
 * Created by Sam on 12/07/13.
 */
public class GameView extends BaseGameActivity {
    private static int CAMERA_HEIGHT = 800;
    private static int CAMERA_WIDTH = 480;
    
    private SceneManager sceneManager;
    private Camera mCamera;

    @Override
    public EngineOptions onCreateEngineOptions() {
        mCamera = new Camera(0,0,CAMERA_WIDTH,CAMERA_HEIGHT);
        return new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED,
                new RatioResolutionPolicy(CAMERA_WIDTH,CAMERA_HEIGHT), mCamera);
    }

	@Override
	public void onCreateResources(
			OnCreateResourcesCallback pOnCreateResourcesCallback)
			throws Exception {
		
    	sceneManager = new SceneManager(this, mEngine, mCamera);
    	sceneManager.loadSplashResources();	
    	
    	pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
			throws Exception {
		
    	pOnCreateSceneCallback.onCreateSceneFinished(sceneManager.createSplashScene());
	}

	@Override
	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
		mEngine.registerUpdateHandler(new TimerHandler(3f, new ITimerCallback() {
			
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				
				mEngine.unregisterUpdateHandler(pTimerHandler);
				
				sceneManager.loadMenuResources();
				sceneManager.createMenuScene();
				sceneManager.setCurrentScene(AllScenes.MENU);
			}
		}));
		
		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}
}
