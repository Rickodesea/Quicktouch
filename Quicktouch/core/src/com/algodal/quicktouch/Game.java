package com.algodal.quicktouch;

import com.algodal.gdxscreen.GdxGame;
import com.algodal.gdxscreen.GdxTransition;
import com.algodal.gdxscreen.utils.GdxLibrary.Content;
import com.algodal.quicktouch.screens.PlayScreen;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Game extends GdxGame {
	
	@Override
	public void initialize() {
		//register all assets to be used inside the game
		registerAsset("badlogic", new AssetDescriptor<>("badlogic.jpg", Texture.class));
		
		//register game screens
		registerScreen("playscreen", PlayScreen.class);
		
		//register game transitional screens
		registerTransition("defaulttransition", GdxTransition.class);
		
		//attach assets to specific screens
		attachAssetToScreen("playscreen", "badlogic");
		
		//set the background color
		clearColor.set(Color.GREEN);
		
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
