package com.herkulstudios.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.herkulstudios.entities.Bullet;
import com.herkulstudios.entities.Enemy;
import com.herkulstudios.entities.Entity;
import com.herkulstudios.entities.Lifepack;
import com.herkulstudios.entities.Player;
import com.herkulstudios.entities.Weapon;
import com.herkulstudios.graficos.Spritesheet;
import com.herkulstudios.main.Game;

public class World {
	
	public static Tile[] tiles;
	public static int WIDTH, HEIGHT;
	public static final int TILE_SIZE = 16;
	
	public World(String path) {
		
		//Player
		Game.player.setX(0);
		Game.player.setY(0);
		WIDTH = 100;
		HEIGHT = 100;
		tiles = new Tile[WIDTH * HEIGHT];
		
		for(int xx = 0; xx < WIDTH; xx++) {
			
			for(int yy = 0; yy < HEIGHT; yy++) {
				
				if(Game.rand.nextInt(100) < 10) {
					
					
					tiles[xx + yy * WIDTH] = new WallTile(xx * 16, yy * 16, Tile.TILE_WALL);
				}
				else {
					tiles[xx + yy * WIDTH] = new Tile(xx * 16, yy * 16, Tile.TILE_FLOOR);
				}
				
				
			}
			
		}
		
		int dir = 0;
		int xx = 0, yy = 0;
		
		for(int i = 0; i < 200; i++) {
			
			tiles[xx + yy * WIDTH] = new Tile(xx * 16, yy * 16, Tile.TILE_FLOOR);
			
			if(dir == 0) {
				
				//Right

				if(xx < WIDTH) {
					xx++;
				}
				
			}
			else if(dir == 1) {
				
				//Left

				if(xx > 0) {
					xx--;
				}
				
			}
			else if(dir == 2) {
				
				//Down
				
				if(yy < HEIGHT) {
					yy++;
				}
				
			}
			else if(dir == 3) {
				
				//Up
				
				if(yy > 0) {
					yy--;
				}
				
			}
			
			if(Game.rand.nextInt(100) < 30) {
				dir = Game.rand.nextInt(4);
			}
			
			
			
		}
							
		
	}
	
	public static boolean isFree(int xnext, int ynext) {
		
		int x1 = xnext / TILE_SIZE;
		int y1 = xnext / TILE_SIZE;
		
		int x2 = (xnext + TILE_SIZE - 1) / TILE_SIZE;
		int y2 = ynext / TILE_SIZE;
		
		int x3 = xnext / TILE_SIZE;
		int y3 = (ynext + TILE_SIZE -1) / TILE_SIZE;
		
		int x4 = (xnext + TILE_SIZE - 1) / TILE_SIZE;
		int y4 = (ynext + TILE_SIZE -1) / TILE_SIZE;
		
		/*System.out.print(!((tiles[x1 + (y1 * World.WIDTH)] instanceof WallTile) ||
				 (tiles[x2 + (y2 * World.WIDTH)] instanceof WallTile) ||
				 (tiles[x3 + (y3 * World.WIDTH)] instanceof WallTile) || 
				 (tiles[x4 + (y4 * World.WIDTH)] instanceof WallTile))); */
		
		if(!((tiles[x1 + (y1 * World.WIDTH)] instanceof WallTile) ||
				 (tiles[x2 + (y2 * World.WIDTH)] instanceof WallTile) ||
				 (tiles[x3 + (y3 * World.WIDTH)] instanceof WallTile) || 
				 (tiles[x4 + (y4 * World.WIDTH)] instanceof WallTile))) {
			
			return true;
		}
		if(Game.player.getZ() > 0) {
			return true;
		}
		
		return false;

	}
	
	public static void RestartGame(String level) {
		Game.bullets.clear();
		Game.entities.clear();
		Game.enemies.clear();
		
		Game.entities = new ArrayList<Entity>();
		Game.enemies = new ArrayList<Enemy>();

		Game.spritesheet = new Spritesheet("/spritesheet.png");
		Game.player = new Player(0, 0, 0, 16, 16, Game.spritesheet.getSprite(32, 0, 16, 16));
		Game.entities.add(Game.player);
		Game.world = new World("/"+level);
	}
	
	public void render(Graphics g) {
		
		int xstart = Camera.x >> 4;
		int ystart = Camera.y >> 4;
		
		int xfinal = xstart + (Game.WIDTH >> 4);
		int yfinal = ystart + (Game.HEIGHT >> 4);
		
		for(int xx = xstart; xx <= xfinal; xx++) {
			
			for(int yy = ystart; yy <= yfinal; yy++) {
				
				if(xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT) {
					continue;
					
				}
					
				
				Tile tile = tiles[xx + (yy * WIDTH)];
				tile.render(g);
			}
			
		}
		
	}
	
	public static void renderMiniMap() {
		
		for(int i = 0; i < Game.miniMapaPixels.length; i++) {
			Game.miniMapaPixels[i] = 0;
		}
		
		
		for(int xx = 0; xx < WIDTH; xx ++) {
			
			for(int yy = 0; yy < HEIGHT; yy ++) {

				if(tiles[xx + (yy * WIDTH)] instanceof WallTile) {
					
					Game.miniMapaPixels[xx + (yy * WIDTH)] = 0xff0000;
				}
												
			}
					
		}
		
		int xPlayer = Game.player.getX() / 16;
		int yPlayer = Game.player.getY() / 16;
		
		Game.miniMapaPixels[xPlayer + (yPlayer * WIDTH)] = 0x0000ff;
		
	}

}
