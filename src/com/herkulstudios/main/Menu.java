package com.herkulstudios.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Menu {
	
	public String[] options = {"Play Game", "Load Game", "Exit"};
	
	public int currentOption = 0;
	public int maxOption = options.length -1;
	
	public boolean down, up, enter;
	
	
	public void update() {
		if(down) {
			down = false;
			currentOption++;
			if (currentOption > maxOption)
				currentOption = 0;
			
		}
		else if (up) {
			up = false;
			currentOption--;
			if(currentOption < 0)
				currentOption = maxOption;
			
		}
		else if(enter) {
			enter = false;
			
			if(options[currentOption] == "Play Game") {
				Game.gameState = "NORMAL";
			}
			else if(options[currentOption] == "Load Game") {

			}
			else if(options[currentOption] == "Exit") {
				System.exit(1);
			}
		}
	}
	
	public static void saveGame(String[] val1, int[] val2, int encode) {
		BufferedWriter write = null;
		
		try {
			write = new BufferedWriter(new FileWriter("save.txt"));
		} catch(IOException e){
			e.printStackTrace();
		}
		
		for(int i = 0; i < val1.length; i++) {
			
			String current = val1[i];
			current += ":";
			char[] value = Integer.toString(val2[i]).toCharArray();
			
			for(int n = 0; n < value.length; n++) {
				value[n] += encode;
				current += value[n];
			}
			
			try {
				write.write(current);
				
				if( i < val1.length - 1)
					write.newLine();
				
			} catch(IOException e) {}
			
			try {
				write.flush();
				write.close();
			} catch(IOException e) {}
			
		}
	}
	
	public void render(Graphics g) {
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.black);
		g2.setColor(new Color(0, 0, 0, 220));
		
		
		
		g.fillRect(0, 0, Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE);
		
		g.setColor(Color.blue);
		g.setFont(new Font("arial", Font.BOLD, 36));
		g.drawString(">Herkuls Zelda Clone<", (Game.WIDTH * Game.SCALE) / 2 - 190, (Game.HEIGHT * Game.SCALE) - 800 / 2);
		
		//Opções de MENU
		
		g.setColor(Color.yellow);
		g.setFont(new Font("arial", Font.BOLD, 24));
		g.drawString(options[0], (Game.WIDTH * Game.SCALE) / 2 - 65, (Game.HEIGHT * Game.SCALE) - 500 / 2);
		g.drawString(options[1], (Game.WIDTH * Game.SCALE) / 2 - 70, (Game.HEIGHT * Game.SCALE) - 400 / 2);
		g.drawString(options[2], (Game.WIDTH * Game.SCALE) / 2 - 35, (Game.HEIGHT * Game.SCALE) - 300 / 2);
		
		if(options[currentOption] == "Play Game") {
			g.drawString(">", (Game.WIDTH * Game.SCALE) / 2 - 110, (Game.HEIGHT * Game.SCALE) - 500 / 2);
			g.drawString("<", (Game.WIDTH * Game.SCALE) / 2 +  83, (Game.HEIGHT * Game.SCALE) - 500 / 2);
			g.setColor(Color.white);
			g.drawString(options[0], (Game.WIDTH * Game.SCALE) / 2 - 65, (Game.HEIGHT * Game.SCALE) - 500 / 2);
		}
		else if(options[currentOption] == "Load Game") {
			g.drawString(">", (Game.WIDTH * Game.SCALE) / 2 - 110, (Game.HEIGHT * Game.SCALE) - 400 / 2);
			g.drawString("<", (Game.WIDTH * Game.SCALE) / 2 +  83, (Game.HEIGHT * Game.SCALE) - 400 / 2);
			g.setColor(Color.white);
			g.drawString(options[1], (Game.WIDTH * Game.SCALE) / 2 - 70, (Game.HEIGHT * Game.SCALE) - 400 / 2);
		}
		else if(options[currentOption] == "Exit") {
			g.drawString(">", (Game.WIDTH * Game.SCALE) / 2 - 110, (Game.HEIGHT * Game.SCALE) - 300 / 2);
			g.drawString("<", (Game.WIDTH * Game.SCALE) / 2 +  83, (Game.HEIGHT * Game.SCALE) - 300 / 2);
			g.setColor(Color.white);
			g.drawString(options[2], (Game.WIDTH * Game.SCALE) / 2 - 35, (Game.HEIGHT * Game.SCALE) - 300 / 2);
		}
		
	}

}
