package com.algodal.quicktouch.gameobject;

import com.algodal.quicktouch.screens.ContentScreen;

public abstract class GameObject {
	protected ContentScreen screen;
	
	public abstract void create();
	public abstract void update(float delta);
	public abstract void draw(float x, float y, float width, float height);
	
	/**
	 * For the purpose of using the content screen convenient variables.
	 * This is to be called first before any other method.
	 * Please do not call any Screen method - you will cause problem!
	 * @param screen screen that owns this object
	 * @return this GameObject
	 */
	public GameObject setContentScreen(ContentScreen screen){
		this.screen = screen;
		return this;
	}
}
