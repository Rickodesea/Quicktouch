package com.algodal.quicktouch.screens;

import com.algodal.gdxscreen.GdxTransition;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class IntroScreen extends GdxTransition{
	private Table table;
	private Label loadPercent;
	
	@Override
	public void render(float delta) {
		loadPercent.setText(Float.toString(getNewScreenAssetProgress() * 100f) + "%");
		
		getGameLibrary().getContent("Stage", Stage.class).get().draw();
		getGameLibrary().getContent("Stage", Stage.class).get().act(delta);
		
		if(getNewScreenAssetProgress() == 1.0f) launch("defaulttransition", "credit");
	}

	@Override
	public void show() {
		getGameLibrary().getContent("Stage", Stage.class).get().clear();
		getGameLibrary().getContent("Stage", Stage.class).get().addActor(table);
		startAsynchronousLoadingOfNewScreenAssets();
	}

	@Override
	public void resize(int width, int height) {
		getGameLibrary().getContent("Viewport", FitViewport.class).get().update(width, height);
	}

	@Override
	public void create() {
		table = new Table(getAsset("skin", Skin.class));
		LabelStyle ls = new LabelStyle(
				getAsset("skin", Skin.class).getFont("font_arial_28pt"), 
				getAsset("skin", Skin.class).getColor("yellow"));
		loadPercent = new Label("0%", ls);
		table.setBounds(0f, 0f, 320f, 480f);
		table.add(loadPercent);
	}
}
