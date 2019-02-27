package com.mygdx.daiju;

import java.util.ArrayList;
import java.util.PriorityQueue;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.daiju.Kartta.Node;


public class Daiju extends ApplicationAdapter implements ApplicationListener, GestureListener, InputProcessor {
	SpriteBatch batch;
	Texture img;
	private OrthographicCamera camera;
	Sprite sprite, tonttuSprite, peikkoSprite;
	TiledMapRenderer tiledMapRenderer;
	TiledMap tiledMap;
	static Kartta kartta;
	
	Node currentTonttuNode, currentPahisNode;
	
	Tonttu tonttu;
	Pahis pahis;
	
	
	
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		System.out.println(Gdx.files.getLocalStoragePath());
		img = new Texture("tausta.jpg");
		//camera = new OrthographicCamera(1280, 720);
		
	    sprite = new Sprite(img);
	    sprite.setOrigin(0,0);
	    sprite.setPosition(-sprite.getWidth()/2,-sprite.getHeight()/2);
	    tonttu = new Tonttu(250, 400, 0, Tonttu.RIGHT);
	    pahis = new Pahis(200, 500, 0, Tonttu.LEFT);
	    
	    
	    
	    float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false,w,h);
        camera.update();
	    
	    //tiledMap = new TmxMapLoader().load("tiledmaps\\daijulevel1.tmx");
        //tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        
       
        kartta = new Kartta();
        
        
        
        
        Gdx.input.setInputProcessor(this);
        

        

        //peikko = new Character(new Vector2(20, 20));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(img, 0, 0);
		tonttu.draw(batch);
		pahis.draw(batch);
		batch.end();
		kartta.renderKartta(camera);
		
		// Updates
		processKeys();
		processPahis();
		tonttu.update(Gdx.graphics.getDeltaTime());
		pahis.update(Gdx.graphics.getDeltaTime());
		//pahis.simpleSearch(tonttu.getX(), tonttu.getY());
		//tonttu.collisionCheck(Gdx.graphics.getDeltaTime());
		
		
	}

/**
 * Defines the 'bad' search algorithm
 */
	private void processPahis() {
		// TODO Auto-generated method stub
		float x = tonttu.getX();
		float y = tonttu.getY();
		float a = pahis.getX();
		float b = pahis.getY();
		
		if(x<a)
			pahis.moveLeft();
		else
			pahis.moveRight();
		if(y>b)
			pahis.jump();
		
		
	}

	private void processKeys() {
		// TODO Auto-generated method stub
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			tonttu.moveLeft();
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			tonttu.moveRight();
		}
		if(Gdx.input.isKeyPressed(Input.Keys.SPACE) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
			tonttu.jump();
		}
			
	}

	public void dispose() {
		batch.dispose();
	}
	private void drawCharacters(SpriteBatch batch2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		// TODO Auto-generated method stub
		camera.translate(deltaX,0);
		camera.update();
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
}

class AStar {
	float maxJump;	// max jump distances in Nodes
	Node start, goal;
	PriorityQueue<Node> open;
	ArrayList<Node> closed;
	public AStar(Node s, Node g) {
		start = s;
		goal = g;
		open = new PriorityQueue<Node>();
		closed = new ArrayList();
		open.add(start);
	}
	public void AStarLoop() {
		
	}
}
class PathfinderNode {
	Node x;	// this Node
	Node predecessor;
	ArrayList<Node> children;
	
	
}
