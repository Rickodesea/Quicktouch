package com.algodal.quicktouch.gameobject;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

/**
 * The background of the game.
 * It changes color over time.
 *
 */
public class Background extends GameObject{
	private ChangerColor color;
	
	@Override
	public void create() {
		color = new ChangerColor();
	}
	
	@Override
	public void update(float delta) {
		color.change(delta);
	}

	@Override
	public void draw(float x, float y, float width, float height) {
		screen.shape.begin(ShapeType.Filled);
		screen.shape.setColor(color.color);
		screen.shape.rect(x, y, width, height);
		screen.shape.end();
	}
	
	public class ChangerColor{
		static final float CHANGE_SPAN = 10.0f; //ten seconds for a complete change of color
		
		Color color;
		int risingIndex;
		int descendingIndex;
		float changeAccumulator;
		
		public ChangerColor() {
			color = new Color(Color.BLACK);
			setRisingIndex(0);
		}
		
		public void setRisingIndex(int index){
			risingIndex = (index > -1 ) ? ( (index < 3) ? index : 2 ) : 0;
			descendingIndex = (risingIndex == 0) ? 2 : risingIndex - 1;
			changeAccumulator = 0.0f;
		}
		
		public void adjustColor(int index, float adjustment){
			/**
			 * libGdx renders 60 times PER SECOND
			 * Each adjustment (delta) is 1 / 60
			 * For the span: 1 / (60 * CHANGE_SPAN)
			 */
			adjustment /= CHANGE_SPAN; //apply change span to color components
			switch(index){
			case 0: color.r += adjustment; break;
			case 1: color.g += adjustment; break;
			case 2: color.b += adjustment; break;
			}
			color.clamp();
		}
		
		public void changeColor(float delta){
			changeAccumulator += delta;
			if(changeAccumulator >= CHANGE_SPAN) setRisingIndex((risingIndex == 2) ? 0 : risingIndex + 1);
		}
		
		public void change(float delta){
			adjustColor(risingIndex, delta);
			adjustColor(descendingIndex, -delta);
			changeColor(delta);
		}
	}
}
