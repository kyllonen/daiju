package com.mygdx.daiju;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Pahis extends Tonttu{

	final static float kerroin = 0.5f;
	public Pahis(float x, float y, float velY, float animState) {
		super(x, y, velY, animState);
		// TODO Auto-generated constructor stub
		
		boundingRectangle = new Rectangle(posX, posY, DIMENSION, DIMENSION);
		
		textureLeft = new Texture(Gdx.files.internal("pahis\\Tonttu.PNG"));
		spriteLeft = new Sprite(textureLeft);
		
		/*
		 *set max speed less than with super class
		 */
		acc=15; 
	}
	
	public void draw(SpriteBatch batch) {
		currentSprite = spriteLeft;
		currentSprite.setPosition(getX(), getY());
		currentSprite.setSize(DIMENSION, DIMENSION);
		currentSprite.draw(batch);
	}
	/**
	 * 
	 * @param x
	 * @param y
	 */
	public void simpleSearch(float x, float y) {
		//return new Vector2(0, 0);
		if(this.posX < x) {
			moveRight();
			state = RIGHT;
			
		}
		else {
			state = LEFT;
			moveLeft();
		}
		
	}
	public void pahisAI(float x, float y) {
		
	}
	                       
}
