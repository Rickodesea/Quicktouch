package com.algodal.quicktouch.screens;

import com.algodal.gdxscreen.GdxScreen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * For convenience. 
 *
 */
public class ContentScreen extends GdxScreen{
	public SpriteBatch batch;
	public Stage stage;
	public FitViewport viewport;
	public float width, height;
	public OrthographicCamera camera;
	public ShapeRenderer shape;
	//public GdxPrefs<GameData> gamePrefs;
	
	@Override
	public void create() {
		batch = getGameLibrary().getContent("SpriteBatch", SpriteBatch.class).get();
		stage = getGameLibrary().getContent("Stage", Stage.class).get();
		viewport =  getGameLibrary().getContent("Viewport", FitViewport.class).get();
		width = viewport.getWorldWidth();
		height = viewport.getWorldHeight();
		camera = (OrthographicCamera) viewport.getCamera(); camera.position.set(width / 2f, height / 2f, 0);
		shape = getGameLibrary().getContent("ShapeRenderer", ShapeRenderer.class).get();
		//gamePrefs = getGameLibrary().getContent("GamePrefs", GdxPrefs.class).get();
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
	}
}
