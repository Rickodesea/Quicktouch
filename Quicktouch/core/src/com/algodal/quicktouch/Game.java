 package com.algodal.quicktouch;

import com.algodal.gdxscreen.GdxGame;
import com.algodal.gdxscreen.GdxTransition;
import com.algodal.gdxscreen.utils.GdxLibrary.Content;
import com.algodal.quicktouch.screens.CreditScreen;
import com.algodal.quicktouch.screens.IntroScreen;
import com.algodal.quicktouch.screens.MenuScreen;
import com.algodal.quicktouch.screens.QuickieScreen;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Game extends GdxGame {
	
	@Override
	public void initialize() {
		//register all assets to be used inside the game
		registerAsset("badlogic", new AssetDescriptor<>("badlogic.jpg", Texture.class));
		registerAsset("quickie", new AssetDescriptor<>("quicktouch.pack", TextureAtlas.class));
		registerAsset("skin", new AssetDescriptor<>("uiskin.json", Skin.class));
		registerAsset("sound", new AssetDescriptor<>("quicktouch_sound.wav", Sound.class));
		registerAsset("music", new AssetDescriptor<>("quicktouch_music.wav", Music.class));
		
		//register game screens
		registerScreen("credit", CreditScreen.class);
		registerScreen("quickie", QuickieScreen.class);
		registerScreen("menu", MenuScreen.class);
		
		//register game transitional screens
		registerTransition("intro", IntroScreen.class);
		registerTransition("defaulttransition", GdxTransition.class);
		
		//attach assets to specific screens
		attachAssetToScreen("quickie", "quickie");
		attachAssetToScreen("quickie", "skin");
		attachAssetToScreen("quickie", "sound");
		attachAssetToScreen("quickie", "music");
		attachAssetToScreen("credit", "quickie");
		attachAssetToScreen("credit", "skin");
		attachAssetToScreen("credit", "sound");
		attachAssetToScreen("credit", "music");
		attachAssetToScreen("menu", "quickie");
		attachAssetToScreen("menu", "skin");
		attachAssetToScreen("menu", "sound");
		attachAssetToScreen("menu", "music");
		
		attachAssetToTransition("intro", "skin");
		
		//set the background color
		clearColor.set(Color.SLATE);
		
		//some content initialized here for convenience
		FitViewport viewport = new FitViewport(320, 480, new OrthographicCamera());
		
		//add components to game library
		library.setContent("Viewport", new Content<FitViewport>() {

			@Override
			public void dispose() {
				//
			}

			@Override
			public Content<FitViewport> initialize() {
				object = viewport;
				initialized = true;
				return this;
			}

			@Override
			public Content<FitViewport> load() {
				loaded = true;
				return this;
			}

			@Override
			public void unload() {
				loaded = false;
			}
			
		});
		
		library.setContent("SpriteBatch", new Content<SpriteBatch>() {

			@Override
			public void dispose() {
				object.dispose();
			}

			@Override
			public Content<SpriteBatch> initialize() {
				object = new SpriteBatch();
				initialized = true;
				return this;
			}

			@Override
			public Content<SpriteBatch> load() {
				loaded = true;
				return this;
			}

			@Override
			public void unload() {
				loaded = false;
			}
			
		});
		
		library.setContent("Stage", new Content<Stage>() {

			@Override
			public void dispose() {
				object.dispose();
			}

			@Override
			public Content<Stage> initialize() {
				object = new Stage(viewport); //stage will share the same viewport
				initialized = true;
				return this;
			}

			@Override
			public Content<Stage> load() {
				loaded = true;
				return this;
			}

			@Override
			public void unload() {
				loaded = false;
			}
			
		});
		
		library.setContent("ShapeRenderer", new Content<ShapeRenderer>() {

			@Override
			public void dispose() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public Content<ShapeRenderer> initialize() {
				object = new ShapeRenderer();
				initialized = true;
				return this;
			}

			@Override
			public Content<ShapeRenderer> load() {
				loaded = true;
				return this;
			}

			@Override
			public void unload() {
				loaded = false;
			}
			
		});
		
		//initialize the library's components
		library.create();
	}

	@Override
	public void deinitialize() {
		library.destroy();
	}
	
	
}
