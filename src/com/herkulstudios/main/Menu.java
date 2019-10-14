package com.herkulstudios.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.herkulstudios.world.World;

public class Menu {
	
	public String[] options = {"Play Game", "Load Game", "Exit"};
	
	public int currentOption = 0;
	public int maxOption = options.length -1;
	
	public boolean down, up, enter;
	
	public static boolean pause = false;
	public static boolean saveExist = false;
	public static boolean saveGame = false;
	
	
	public void update() {
		
		File file = new File("save.txt");
		
		if(file.exists()) {
			saveExist = true;
		}
		else {
			saveExist = false;
		}
		
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
				file = new File("save.txt");
				file.delete();
				
			}
			else if(options[currentOption] == "Load Game") {
				
				file = new File("save.txt");
				
				
				if(file.exists()) {
					String saver = loadGame(10);
					applySave(saver);
					System.out.print("AAAAA");
					
				}
				
			}
			else if(options[currentOption] == "Exit") {
				System.exit(1);
			}
		}
	}
	
	public static void applySave(String str) {
		
		String[] spl = str.split("/");
		
		for(int i = 0; i < spl.length; i++) {
			String[] spl2 = spl[i].split(":");

			
			switch(spl2[0]) 
			{
				
				case "level":
					System.out.print("EntrouNoCaseDoSwitch");
					World.RestartGame("level" + spl2[1] + ".png");
					Game.gameState = "NORMAL";
					pause = false;
					break;
					
				case "vida":
					Game.player.life = Integer.parseInt(spl2[1]);
					break;
				
			}
			
		}
		
	}
	
	public static String loadGame(int encode) {
		String line = "";
		File file = new File("save.txt");
		
		if(file.exists()) {
			try {
				String singleLine = null;
				BufferedReader reader = new BufferedReader(new FileReader("save.txt"));
				
				try {
					
					while((singleLine = reader.readLine()) != null) {
						
						String[] trans = singleLine.split(":");
						char[] val = trans[1].toCharArray();
						trans[1] = "";
						
						for (int i = 0; i < val.length; i++) {
							val[i] -= encode;
							trans[1] += val[i];
						}
						
						line += trans[0];
						line += ":";
						line += trans[1];
						line += "/";
						
					}
					
				} catch(IOException e) {}
				
				
			} catch(FileNotFoundException e) {}
		}
		return line;
		
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
