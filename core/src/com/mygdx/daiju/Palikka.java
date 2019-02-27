package com.mygdx.daiju;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Palikka {
	Rectangle rectangle;
	Sprite sprite;
	Texture texture;
	
	
	final int LEAVES = 0;
	final static int GROUND = 1;
	final static int TREE = 2;
	
	
	public void draw(SpriteBatch batch) {
		sprite.draw(batch);
	}
}
