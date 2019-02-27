package com.mygdx.daiju;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.daiju.Kartta.Node;

class Tonttu {
	Vector2 pos, vel, accel;
	float posX, posY;	// co-ordinates as in pixels
	float x, y;
	float velocityY, velocityX;
	Sprite spriteLeft, spriteRight, spriteJump;
	Sprite currentSprite;
	Texture textureLeft, textureRight;
	
	// Tsekkaa joka kulma
	Rectangle boundingRectangle, up, down, left, right;
	
	
	
	
	TextureAtlas leftAtlas, rightAtlas;
	Animation animation, rightAnimation, leftAnimation, jumpAnimation;
	TextureRegion animationFrame;
	// animaatio statet
	final static int LEFT = 0;
	final static int RIGHT = 1;
	final static int MOVING_RIGHT = 2;
	final static int MOVING_LEFT = 3;
	final static int JUMP = 4;
	final static int DEATH = 5;	
	final static int GROUND = 6;
	final static int FALLING = 7;
	int animationState = RIGHT;
	
	final static float maxVelX = 200;
	static float acc = 50;
	float MAX_JUMP_SPEED = 600;
	
	float PADDING = 20;
	
	float DIMENSION = 32;
	float BLOCK_DIMENSION = 32;
	
	float GRAVITY_ACC = -20;
	
	// Animation states

	
	
	
	float state;
	
	
	
	float HORIZONTAL = 600;
	
	private float elapsedTime = 0;
	
	public Tonttu(float x, float y, float velY, float animState) {
		this.posX = x;
		this.posY = y;
		velocityY = -30;
		velocityX = 0;
		animationState = RIGHT;
		boundingRectangle = new Rectangle(posX, posY, DIMENSION, DIMENSION);
		
		textureLeft = new Texture(Gdx.files.internal("tontut\\vasen\\tonttu_1.PNG"));
		spriteLeft = new Sprite(textureLeft);
		spriteLeft.setSize(DIMENSION, DIMENSION);
		textureRight = new Texture(Gdx.files.internal("tontut\\oikea\\tonttu_3.PNG"));
		spriteRight = new Sprite(textureRight);
		spriteLeft.setSize(DIMENSION, DIMENSION);
		spriteRight.setSize(DIMENSION, DIMENSION);
		spriteJump = new Sprite(new Texture(Gdx.files.internal("tontut\\tonttu_5.PNG")));
		rightAtlas = new TextureAtlas(Gdx.files.internal("tontut\\oikeatonttu.atlas"));
		rightAnimation = new Animation(1/2f, rightAtlas.getRegions());
		leftAtlas = new TextureAtlas(Gdx.files.internal("tontut\\vasentonttu.atlas"));
		leftAnimation = new Animation(1/2f, rightAtlas.getRegions());
		
		state = GROUND;
		
	}
	public void collisionFloorCheck(float delta) {
		if(!hitsFloor()) {
			velocityY -= 50 * delta;
			boundingRectangle.y += velocityY;
		}
		else {
			velocityY = 0;
			state = GROUND;
		}
	}
	public void update(float delta) {
		velocityY += GRAVITY_ACC;
		//checkIfFalling();
		if(velocityX < 0) {
			if(!collisionLeft()) {
				boundingRectangle.x += velocityX * delta;
			}
			else {
				velocityX = 0;
			}
		}
		else if(velocityX > 0) {
			if(!collisionRight()) {
				boundingRectangle.x += velocityX * delta;
			}
			else {
				velocityX = 0;
			}
		}
		if(velocityY < 0) {
			if(!collisionDown()) {
				
				boundingRectangle.y += velocityY * delta;
			}
			else {
				velocityY = 0;
				state = GROUND;
			}
		}
		else if(velocityY > 0 && collisionRoof()) {		// character hits roof
			velocityY = 0;
			state = FALLING;
		}
		
		
		
		
		boundingRectangle.y += velocityY * delta;
		boundingRectangle.x += velocityX * delta;
		// spriteLeft.setPosition(playerRectangle.x, playerRectangle.y);
		//setPosition();
		
	}
	public boolean collisionLeft() {
		float newX = getX() - PADDING;
		Node n = Daiju.kartta.nodeAt(newX, this.getY());
		if(n.blocked==true)
			return true;
		else
			return false;
	

	}
	public boolean collisionRight() {
		float newX = getX() + Daiju.kartta.getTileWidth() + PADDING;

		Node n = Daiju.kartta.nodeAt(newX, this.getY());
		if(n.blocked==true)
			return true;
		else
			return false;
	}
	public boolean collisionRoof() {
		float newY = getY() + PADDING + DIMENSION;
		Node n = Daiju.kartta.nodeAt(this.getX(), newY);
		if(n.blocked==true)
			return true;
		else
			return false;
	}
	public boolean collisionDown() {
		float newY = getY() - PADDING;
		Node n = Daiju.kartta.nodeAt(this.getX(), newY);
		if(n.blocked==true)
			return true;
		else
			return false;
	}
	public boolean collisionY() {
		if(velocityY > 0) {
			
		}
		return false;
	}
	public boolean hitsFloor() {
		if(isFalling() && Daiju.kartta.blocked(boundingRectangle.x, boundingRectangle.y - 5)) {
			System.out.println("Hit ground");
			return true;
		}
		return false;
	}

	public boolean isFalling() {
		return velocityY < 0;
	}
	public boolean hits(Rectangle r) {
		if(boundingRectangle.overlaps(r))
			return true;
		else
			return false;
	}
	void jump() {
		/*if(!Daiju.kartta.blocked(playerRectangle.x, playerRectangle.y + Daiju.kartta.layer.getHeight())) {
			velocityY = 25;
		}*/
		if(state != JUMP && state != FALLING) {
			velocityY = MAX_JUMP_SPEED;
			state = JUMP;
		}
		
	}
	public Node currentNode() {
		
	}

	public void moveLeft() {
		/*float tempX = boundingRectangle.x - (delta * velocityX);
		Rectangle temp = new Rectangle(tempX, getY(), DIMENSION, DIMENSION);
		
		if(Daiju.kartta.blocked(tempX, boundingRectangle.y)) {
			System.out.println("blocked");
			velocityX = 0;
		}
		for(Rectangle r : Daiju.kartta.getBlocks()) {
			if(temp.overlaps(r)) {
				velocityX=0;
				return false;
			}
		}
		
		
			velocityX -= acc;
			if(Math.abs(velocityX) > maxVelX) {
				velocityX = maxVelX * (-1);
			}
			Rectangle r = new Rectangle(tempX, boundingRectangle.y, DIMENSION, DIMENSION);
			boundingRectangle.x += delta * velocityX;
			animationState = MOVING_LEFT;
			return true;
			spriteLeft.setPosition(playerRectangle.x, playerRectangle.y);*/
		velocityX -= acc;
		if(velocityX < -maxVelX) {
			velocityX = -maxVelX;
		}
		state = LEFT;
	}
	public void moveRight() {
		/*float tempX = boundingRectangle.x + (delta * velocityX);
		Rectangle temp = new Rectangle(tempX, getY(), DIMENSION, DIMENSION);
		if(Daiju.kartta.blocked(tempX, boundingRectangle.y)) {
			System.out.println("blocked");
			velocityX = 0;
		}
		
		
		for(Rectangle r : Daiju.kartta.getBlocks()) {
			if(temp.overlaps(r)) {
				velocityX=0;
				return false;
			}
		}
			velocityX += acc;
			if(Math.abs(velocityX) > maxVelX) {
				velocityX = maxVelX;
			}
			
			boundingRectangle.x += delta * velocityX;
			//spriteLeft.setPosition(boundingRectangle.x, boundingRectangle.y);
			animationState = MOVING_RIGHT;
			return true;*/
		velocityX += acc;
		if(velocityX > maxVelX) {
			velocityX = maxVelX;
		}
		state = RIGHT;
	}
	public void draw(SpriteBatch batch) {
		//spriteLeft.draw(batch);
		
		
		
		
		switch (animationState) {
		case LEFT:
			currentSprite = spriteLeft;
			break;
		case RIGHT:
			currentSprite = spriteRight;
			break;
		case JUMP:
			currentSprite = spriteJump;
			break;
		case MOVING_LEFT:
			animationFrame = leftAnimation.getKeyFrame(Gdx.graphics.getDeltaTime(), true);
			currentSprite = new Sprite(animationFrame);
			break;
		case MOVING_RIGHT:
			animationFrame = rightAnimation.getKeyFrame(Gdx.graphics.getDeltaTime(), true);
			currentSprite = new Sprite(animationFrame);
			break;
		}
		currentSprite.setPosition(getX(), getY());
		currentSprite.setSize(DIMENSION, DIMENSION);
		currentSprite.draw(batch);
	}

	public float getX() {
		return boundingRectangle.x;
	}
	public float getY() {
		return boundingRectangle.y;
	}
	
	
}