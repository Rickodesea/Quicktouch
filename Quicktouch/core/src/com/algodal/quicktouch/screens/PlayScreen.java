package com.algodal.quicktouch.screens;

import com.algodal.quicktouch.gameobject.Background;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

/**
 * The game is played here.
 *
 */
public class PlayScreen extends ContentScreen{
	private Background bg;
	
	@Override
	public void create() {
		super.create(); //must call ContentScreen's create
		bg = (Background) new Background().setContentScreen(this); //extremely important to set the screen
		bg.create(); //must create the GameObject
	}

	@Override
	public void render(float delta) {
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(getAsset("badlogic", Texture.class), 0, 0, width, height);
		batch.end();
		
		shape.setProjectionMatrix(camera.combined);
		shape.begin(ShapeType.Filled);
		shape.setColor(Color.BLUE);
		shape.rect(0, 0, width, height);
		shape.end();
		
		bg.draw(0, 0, width, height);
		bg.update(delta);
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
	}
	
	
}
