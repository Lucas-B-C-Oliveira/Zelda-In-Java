package com.herkulstudios.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.herkulstudios.entities.*;
import com.herkulstudios.main.Game;

public class World {
	
	public static Tile[] tiles;
	public static int WIDTH, HEIGHT;
	public static final int TILE_SIZE = 16;
	
	public World(String path) {
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			int[] pixels = new int[map.getWidth() * map.getHeight()];
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			tiles = new Tile[map.getWidth() * map.getHeight()];
			
			map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());
			
			for(int xx = 0; xx < map.getHeight(); xx++) {
				
				for(int yy = 0; yy < map.getWidth(); yy++) {
					
					int pixelAtual = pixels[xx + (yy * map.getWidth())];
					
					tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Tile.TILE_FLOOR);
					
					if(pixelAtual == 0xFF000000) {
						
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Tile.TILE_FLOOR);
						
					}
					else if(pixelAtual == 0xFFFFFFFF) {
						
						tiles[xx + (yy * WIDTH)] = new WallTile(xx * 16, yy * 16, Tile.TILE_WALL);
						
						
					}
					else if (pixelAtual == 0xFF0008FF) {
						
						//Player
						Game.player.setX(xx * 16);
						Game.player.setY(yy * 16);
						
					}
					else if(pixelAtual == 0xFFFF0000) {
						Enemy en = new Enemy(xx * 16, yy * 16, 16, 16, Entity.ENEMY_PACK_ENTITY);
						Game.entities.add(en);
						Game.enemies.add(en);

						
					}
					else if (pixelAtual == 0xFFFF4E00) {
						
						Game.entities.add(new Weapon(xx * 16, yy * 16, 16, 16, Entity.WEAPON_PACK_ENTITY));
						
					}
					else if (pixelAtual == 0xFFFF7CAE) {
						//LifePAck
						Lifepack pack = new Lifepack(xx * 16, yy * 16, 16, 16, Entity.LIFE_PACK_ENTITY);
						pack.setMask(8 , 8 , 8 , 8);
						Game.entities.add(pack);
						
					}
					else if (pixelAtual == 0xFFFFFB49) {
						//Bullet
						Game.entities.add(new Bullet(xx * 16, yy * 16, 16, 16, Entity.BULLET_PACK_ENTITY));
					}
				}
				

			}
			
		} catch (IOException e) {
			e.printStackTrace();
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
		
		return !((tiles[x1 + (y1 * World.WIDTH)] instanceof WallTile) ||
				 (tiles[x2 + (y2 * World.WIDTH)] instanceof WallTile) ||
				 (tiles[x3 + (y3 * World.WIDTH)] instanceof WallTile) || 
				 (tiles[x4 + (y4 * World.WIDTH)] instanceof WallTile));
		

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

}
