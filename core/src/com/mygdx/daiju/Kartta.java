package com.mygdx.daiju;

import java.util.ArrayList;
import java.util.PriorityQueue;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.daiju.Kartta.Node;

public class Kartta {
	TiledMap tiledMap;
	OrthogonalTiledMapRenderer tiledMapRenderer;
	ArrayList<Rectangle> rectangles, cells;
	int[][] grid;
	ArrayList<Node> nodes;
	int dimX, dimY;
	int width, height;	// The size of map in number of tiles
	TiledMapTileLayer blocklayer, cellLayer;
	public Kartta() {
		tiledMap = new TmxMapLoader().load("tiledmaps\\daijulevel1.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        
        blocklayer = (TiledMapTileLayer) tiledMap.getLayers().get("blocks");
        //System.out.println("Tile height: " + blocklayer.getTileHeight()+ ", tile width: " + blocklayer.getTileWidth());
        /*for(int i = 0; i<collisionLayer.getObjects().getCount(); ++i) {
        	palikat.add(collisionLayer.getObjects().get(i));
        }*/
        
        //dimX = tiledMap.
        //dimY = blocklayer.getWidth();
        /*
        rectangles = new ArrayList<Rectangle>();
        for(int i = 0; i<blocklayer.getWidth(); ++i) {
        	for( int j = 0; j<blocklayer.getHeight(); ++j) {
        		TiledMapTileLayer.Cell cell = blocklayer.getCell(i, j);
        		if(cell != null) {
        			if(cell.getTile() != null) {
        				System.out.println("Cell (" + i + ", " +j + ") id: ");
        				rectangles.add(new Rectangle(i*blocklayer.getTileWidth(), j*blocklayer.getTileHeight(), blocklayer.getTileWidth(), blocklayer.getTileHeight()));
        			}
        		}
        	}
        }*/
        width = (int) tiledMap.getProperties().get("width");
        height = (int) tiledMap.getProperties().get("height");
        grid = new int[width][height];
        nodes = new ArrayList<Node>();
        int counter =0;
        int blockcounter = 0;
        for(int i = 0; i<width; ++i) {
        	for(int j=0; j<height; ++j) {
        		Node node = new Node(i, j, counter);
        		if(blocked(i, j)) {
        			node.setBlocked();	
        			System.out.println("blocked node " + i + " " + j);
        			++blockcounter;
        		}
        		nodes.add(node);
        		++counter;
        		
        	}
        }
        System.out.println("Number of nodes: " + counter + "\nBlocks: " + blockcounter );
	}
	public void renderKartta(OrthographicCamera camera) {
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();
	}
	public ArrayList<Rectangle> getBlocks() {
		return rectangles;
	}
	public boolean blocked(float i, float j) {
		int x=(int)i;
		int y=(int)j;
		if(blocklayer.getCell(x, y) != null) {
			System.out.println(x + ", " + y);
			return true;
		}
		return false;
	}
	/**
	 * used for collision detection
	 * @return
	 */
	public float getTileWidth() {
		return blocklayer.getTileWidth();
	}
	/**
	 * used for collision detection
	 * @return
	 */
	public float getTileBeight() {
		return blocklayer.getTileHeight();
	}
	/**
	 * Returns the node at speficied (pixel) co-ordinates
	 * @param i
	 * @param j
	 * @return
	 */
	public Node nodeAt(float i, float j) {
		int x = (int) (i/blocklayer.getTileWidth());
		int y = (int) (j/blocklayer.getTileHeight());
		for(Node n : nodes) {
			if(n.x == x && n.y == y) {
				return n;
			}
		}
		return null;
	}
	public Node getNode(int i, int j) {
		for(Node n : nodes) {
			if(n.x == i && n.y == j) {
				return n;
			}
		}
		return null;
	}
	class Pathfinder {
		int maxJump = 6;
		Node start, goal;
		PriorityQueue<Node> open;
		ArrayList<Node> closed;
		public Pathfinder(Node s, Node g) {
			start = s;
			goal = g;
			open = new PriorityQueue<Node>();
			closed = new ArrayList();
		}
		public float heuristic(Node start, Node goal) {
			int startx = (int) start.x;
			int starty = (int) start.y;
			int endx = (int) goal.x;
			int endy = (int) goal.y;
			
			float distance = Math.abs(startx - endx) + Math.abs(starty - endy);
			
			
			return distance;
		}
		public void mainLoop() {
			open.add(start);
			while(!open.isEmpty()) {
				Node n = (Node) open.remove();
				for(int i = 0; i<n.children.size(); ++i) {
					if(!closed.contains(n.children.get(i))) {
						if(!open.contains(n.children.get(i))) {
							open.add(n.children.get(i));
						}
						float f = n.cost + 1 + heuristic(n.children.get(i), goal);
						if(n.children.get(i).total > f) {
							n.children.get(i).setTotalCost(f);
							n.children.get(i).predecessor = n.index;
						}
						if(n.children.get(i).equals(goal)) {
							returnPath(n.children.get(i));
						}
					}
				}
			}
		}
		private void returnPath(Node node) {
			// TODO Auto-generated method stub
			
		}

	}
	
	class Node {
		float x, y, index;
		float jumpvalue;
		float predecessor;
		boolean blocked;
		ArrayList<Node> children;
		float cost, total;
		public Node(float x, float y, float index) {
			this.x = x;
			this.y = y;
			this.index = index;
			blocked = false;
			
			children = new ArrayList<Node>();
			for(int i=(int) (x-1); i<x+1; ++i) {
				for(int j=(int) (y-1); j<y+1; ++j) {
					if(i>=0 && i<width && j >= 0 && j<height) {
						if(!blocked(i, j)) {
							children.add(getNode(i, j));
						}
					}
				}
			}
			
			cost = -1;	// As in 'infinite'
			total = -1;
			
			// init jump values
			if(blocked(x, y)) {
				jumpvalue = -1;
			}
			else {
				int count = 0;
				int tempY = (int) (y-1);
				while(!blocked(x, tempY) && tempY>0) {
					++count;
					--tempY;
				}
				jumpvalue = count;
			}
			
		}
		public void setTotalCost(float f) {
			// TODO Auto-generated method stub
			total = f;
			
		}
		public void setCost(float c) {
			cost = c;
		}
		public boolean isBlocked() {
			return blocked;
		}
		public void setBlocked() {
			// TODO Auto-generated method stub
			blocked = true;
		}
	}
	/**
	 * Used in A* pathfinding
	 * @author kyllonen
	 *
	 */
	class Cell {
		float f, g, h;
		int x, y;
		Cell parent;
		
	}
}
