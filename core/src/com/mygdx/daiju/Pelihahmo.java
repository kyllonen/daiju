package com.mygdx.daiju;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Pelihahmo {
	Vector2 pos, vel, accel;
	float posX, posY;
	float velocityY;
	Sprite spriteLeft, spriteRight;
	Texture textureLeft, textureRight;
	Rectangle playerRectangle, up, left, right;
	Animation animation;
	
	final int LEFT = 0;
	final int RIGHT = 1;
	final int MOVING_RIGHT = 2;
	final int MOVING_LEFT = 3;
	final int DEATH = 4;
	
	
	
	float state;
	
	int direction;
	
	float HORIZONTAL;
	
	
	float DIMENSION;
	
	
	
}
