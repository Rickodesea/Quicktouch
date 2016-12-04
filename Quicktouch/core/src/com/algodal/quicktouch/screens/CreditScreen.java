package com.algodal.quicktouch.screens;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class CreditScreen extends ContentScreen{
	private float timespan;
	
	@Override
	public void show() {
		timespan = 0.0f;
	}

	@Override
	public void render(float delta) {
		batch.begin();
			batch.draw(getAsset("quickie", TextureAtlas.class).findRegion("credit"), 0f, 0f, 320f, 320f);
			batch.draw(getAsset("quickie", TextureAtlas.class).findRegion("banner"), 0f, 320f, 320f, 160f);
		batch.end();
		
		if(timespan >= 5.0f) launch("defaulttransition", "menu");
		timespan += delta;
	}
	
}
