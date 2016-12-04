package com.algodal.quicktouch.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class MenuScreen extends ContentScreen{
	private Table table;
	private TextButton newGame;
	//private TextButton continueGame;
	//private TextButton audioGame;
	private TextButton quitGame;
	
	@Override
	public void create() {
		super.create();
		
		Skin skin = getAsset("skin");
		
		table = new Table(skin);
		newGame = new TextButton("NEW", skin);
		//continueGame = new TextButton("CONTINUE", skin);
		/*if(gamePrefs.object.isAudio()){
			audioGame = new TextButton("AUDIO IS ON", skin);
		}else{
			audioGame = new TextButton("AUDIO IS OFF", skin);
		}*/
		quitGame = new TextButton("QUIT", skin);
		
		table.setBounds(0, 0, width, height);
		installButtonListeners();
	}

	@Override
	public void show() {
		stage.clear();
		resetTable();
		stage.addActor(table);
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render(float delta) {
		batch.begin();
			batch.draw(getAsset("quickie", TextureAtlas.class).findRegion("banner"), 0f, 320f, 320f, 160f);
		batch.end();
	
		stage.act(delta);
		stage.draw();
	}
	
	private void resetTable(){
		table.clear();
		//System.out.println(gamePrefs.object.isNewGame());
		//if(gamePrefs.object.isNewGame() == false) table.add(continueGame).padBottom(25f).row();
		table.add(newGame).padBottom(25f).row();
		//table.add(audioGame).padBottom(25f).row();
		table.add(quitGame).padBottom(25f).row();
	}
	
	private void installButtonListeners(){
		newGame.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				launch("defaulttransition", "quickie");
			}
		});
		
		/*continueGame.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				launch("defaulttransition", "quickie");
			}
		});*/
		
		/*audioGame.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if(gamePrefs.object.isAudio()){
					gamePrefs.object.setAudio(false);
					audioGame.setText("AUDIO IS OFF");
				}else{
					gamePrefs.object.setAudio(true);
					audioGame.setText("AUDIO IS ON");
				}
			}
		});*/
		
		quitGame.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Gdx.app.exit();
			}
		});
	}
}
