package com.algodal.quicktouch.screens;

import java.util.Random;

import com.algodal.gdxscreen.entity.GdxEntity;
import com.algodal.gdxscreen.entity.GdxEntity.Entropy;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.TimeUtils;

public class QuickieScreen extends ContentScreen{
	private GdxEntity quickie, background;
	private Table table;
	private Label timeLabel, scoreLabel;
	private InputMultiplexer im;
	private boolean pauseMusic;
	private Motion quickieMotion;
	private TextButton pauseButton;//, returnButton;
	private boolean confirmFinish;
	private TextButton confirmOKButton;
	private Label confirmMessage, confirmYourScore;
	private Table confirmTable;
	private TextureRegion lastRegion;
	private boolean updated;
	
	private boolean pause;
	private int score;
	private float time;
	private boolean stopMusic;
	
	@Override
	public void create() {
		super.create(); //need to be called first
		
		pause = false;
		
		pauseMusic = false;
		quickieMotion = new Motion();
		
		TextureAtlas qt = getAsset("quickie");
		Skin skin = getAsset("skin");
		quickie = new GdxEntity();
		quickie.putEntropy(
				"straight", 
				new Entropy(
						new Animation(
								2f, 
								qt.findRegion("straight-black"),
								qt.findRegion("left-black"),
								qt.findRegion("straight-black"),
								qt.findRegion("straight-black"),
								qt.findRegion("right-black"),
								qt.findRegion("straight-black"),
								qt.findRegion("straight-black"),
								qt.findRegion("up-black"),
								qt.findRegion("straight-black"),
								qt.findRegion("straight-black"),
								qt.findRegion("down-black"),
								qt.findRegion("straight-black")),					
						null));
		
		background = new GdxEntity();
		background.putEntropy(
				"background", 
				new Entropy(
						new Animation(
								10f, 
								qt.findRegion("background-black")), 
						null));
		
		quickie.setPosition(170f, 170f);
		quickie.setSize(32f, 32f);
		
		background.setPosition(0f, 0f);
		background.setSize(320f, 320f);
		
		quickie.setEntropy("straight"); //need to call at least once before drawing
		background.setEntropy("background"); //need to call at least once before drawing
		
		table = new Table(skin);
		scoreLabel = new Label("0", skin);
		timeLabel = new Label("0", skin);
		pauseButton = new TextButton("PAUSE IS OFF", skin);
		//returnButton = new TextButton("RETURN", skin);
		confirmTable = new Table(skin);
		confirmMessage = new Label("GAME FINSIHED", skin);
		confirmYourScore = new Label("YOUR SCORE IS", skin);
		confirmOKButton = new TextButton("OK", skin);
		confirmOKButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				launch("defaulttransition", "menu");
			}
		});
		confirmTable.setBounds(0, 0, 320, 320);
		confirmTable.columnDefaults(0).align(Align.center).expandX();
		confirmTable.columnDefaults(1).align(Align.center).expandX();
		confirmTable.add(confirmMessage).align(Align.center).padBottom(70f).row();
		confirmTable.add(confirmYourScore).align(Align.center).padBottom(120f).row();
		//confirmTable.add(confirmOKButton).align(Align.bottom).row();
		
		//table.setDebug(true); //uncomment for debugging
		
		table.setBounds(0, 320, 320, 160);
		table.columnDefaults(0).align(Align.center).expandX();
		table.columnDefaults(1).align(Align.center).expandX();
		table.add(new Label("TIME", skin));
		table.add(timeLabel);
		table.row();
		table.add(new Label("SCORE", skin));
		table.add(scoreLabel);
		table.row();
		table.add(pauseButton).padTop(20f);
		table.add(confirmOKButton).padTop(20f).row();
		
		pauseButton.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if(pause){
					pause = false;
					pauseButton.setText("PAUSE IS OFF");
					getAsset("music", Music.class).play();
				}else{
					pause = true;
					pauseButton.setText("PAUSE IS ON");
					getAsset("music", Music.class).pause();
				}
			}
		});
		
		/*returnButton.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				gamePrefs.save();
				launch("defaulttransition", "menu");
			}
		});*/
	}
	
	@Override
	public void show() {
		stage.clear();
		stage.addActor(table);
		
		getAsset("music", Music.class).setLooping(true);
		stopMusic = false;
		
		confirmFinish = false;
		
		//initialize last region
		lastRegion = getAsset("quickie", TextureAtlas.class).findRegion("straight-black");
		
		/*if(gamePrefs.object.isAudio()){
			getAsset("music", Music.class).setVolume(1f);
		}else{
			getAsset("music", Music.class).setVolume(0f);
		}*/
		
		updated  = false;
		
		score = 0;
		time = 60f * 5;
		
		quickie.setInputProccessor(new InputAdapter(){

			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button) {
				Vector2 point = viewport.unproject(new Vector2(screenX, screenY));
				float errorWidth = quickie.getSize().getWidth() + 32f;
				float errorHeight = quickie.getSize().getHeight() + 32f;
				float errorX = quickie.getPosition().getX() - 16f;
				float errorY = quickie.getPosition().getY() - 16f;
				if(point.x >= errorX && point.y >= errorY){
					float w = Math.abs(point.x - errorX);
					float h = Math.abs(point.y - errorY);
					if(w <= errorWidth && h <= errorHeight){
						score = score + 1;
						getAsset("sound", Sound.class).play();
						quickieMotion.accelerate();
					}
				}
				return true;
			}
			
		});
		
		im = new InputMultiplexer(stage, quickie.getInputProccessor());
		Gdx.input.setInputProcessor(im);
		
		confirmOKButton.setDisabled(true);
		pauseButton.setDisabled(false);
	}
	
	private void updateTableAndGameInShow(){
		updated = true;
	}

	@Override
	public void pause() {
		if(getAsset("music", Music.class).isPlaying()){
			getAsset("music", Music.class).pause();
			pauseMusic = true;
		}
		pause = true;
	}

	@Override
	public void resume() {
		if(pauseMusic){
			getAsset("music", Music.class).play();
			pauseMusic = false;
		}
		pause = false;
	}

	@Override
	public void hide() {
		getAsset("music", Music.class).stop();
	}

	@Override
	public void render(float delta) {
		if(!pause){
			lastRegion = quickie.getEntropy().nextFrame(delta);
		}
		
		if(!updated) updateTableAndGameInShow();
		
		batch.begin();
			batch.draw(
				background.getEntropy().nextFrame(delta), 
				background.getPosition().getX(), 
				background.getPosition().getY(),
				background.getSize().getWidth(),
				background.getSize().getHeight());
			batch.draw(
				lastRegion, 
				quickie.getPosition().getX(), 
				quickie.getPosition().getY(),
				quickie.getSize().getWidth(),
				quickie.getSize().getHeight());
		batch.end();
		
		if(!stopMusic){
			if(pause == true){
				if(getAsset("music", Music.class).isPlaying()) getAsset("music", Music.class).pause();
			}else{
				if(!getAsset("music", Music.class).isPlaying()) getAsset("music", Music.class).play();
			}
		}
		
		if(pause == false) stage.act(delta);
		stage.draw();
		
		if(!confirmFinish){
			if(pause == false) quickieMotion.update(delta, quickie);
			
			if(pause == false) updateTable(delta);
			
			if(pause == false) updateGame(delta);
		}
	}
	
	private void updateTable(float delta){
		timeLabel.setText(Float.toString(Math.round(time)));
		scoreLabel.setText(Integer.toString(score));
	}
	
	private void updateGame(float delta){
		if(time <= 0.0f){
			confirmFinish = true;
			confirmYourScore.setText("YOUR SCORE IS   " + score);
			stage.addActor(confirmTable);
			confirmOKButton.setDisabled(false);
			pauseButton.setDisabled(true);
			getAsset("music", Music.class).stop();
			stopMusic = true;
			Gdx.input.setInputProcessor(stage);
		}
		time = time - delta;
	}
	
	public static class Motion{
		private int dx, dy;
		private Random random;
		private final float flightSpeed = (1f / 60f) * 140f;
		private final float defaultSpeed = (1f / 60f) * 32f;
		private float speed;
		private float nitro;
		
		public Motion() {
			random = new Random(TimeUtils.nanoTime());
			dx = +1;
			dy = dx;
			speed = defaultSpeed;
			nitro = 0f;
		}
		
		private void nextDX(){
			dx = (random.nextInt(2) == 0) ? -1 : +1;
		}
		
		private void nextDY(){
			dy = (random.nextInt(2) == 0) ? -1 : +1;
		}
		
		private void restrict(GdxEntity entity){
			if(entity.getPosition().getX() < 0) entity.getPosition().setX(0f);
			else if(entity.getPosition().getX() > 320f - entity.getSize().getWidth()) 
				entity.getPosition().setX(320f - entity.getSize().getWidth());
			
			if(entity.getPosition().getY() < 0) entity.getPosition().setY(0f);
			else if(entity.getPosition().getY() > 320f - entity.getSize().getHeight()) 
				entity.getPosition().setY(320f - entity.getSize().getHeight());
		}
		
		public void update(float delta, GdxEntity entity){
			if(dx == +1){
				entity.getPosition().addX(speed);
			}else if(dx == -1){
				entity.getPosition().addX(-speed);
			}
			
			if(dy == +1){
				entity.getPosition().addY(speed);
			}else if(dy == -1){
				entity.getPosition().addY(-speed);
			}
			
			restrict(entity);
			
			nitro += delta;
			if(nitro >= 1f){
				nitro = 0f;
				speed = defaultSpeed;
				nextDX();
				nextDY();
			}
		}
		
		public void accelerate(){
			speed = flightSpeed;
		}
	}
}
