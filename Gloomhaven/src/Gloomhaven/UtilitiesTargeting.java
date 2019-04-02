package Gloomhaven;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import Gloomhaven.Characters.Enemy;
import Gloomhaven.Characters.Player;
import Gloomhaven.Hex.Draw;
import Gloomhaven.Hex.Hex;
import Gloomhaven.Hex.HexCoordinate;
import Gloomhaven.Hex.UtilitiesHex;
public final class UtilitiesTargeting {
	
	public static List<Player> createTargetListPlayer(Hex board[][], int range, HexCoordinate starting, Point dimensions, List<Player> party, boolean flatlayout){
		List<Point> targetPoints = new ArrayList<Point>();
		
		targetPoints=createTargetList(board, range, starting, "P", dimensions, flatlayout);
	
		List<Player> targets = new ArrayList<Player>();
		
		for(int i=0; i<targetPoints.size(); i++) {
			for(int j=0; j<party.size(); j++) {
				
				if(targetPoints.get(i).equals(party.get(j).getCoordinates()))
					targets.add(party.get(j));

			}
		}
		
		return targets;
	}
	
	public static List<Point> createTargetList(Hex board[][], int range, HexCoordinate starting, String quickID, Point dimensions, boolean flatlayout){
		List<Point> targets = new ArrayList<Point>();
		
		for(int q=-range; q<=range; q++) {
			int r1=Math.max(-range, -q-range);
			int r2 = Math.min(range, -q+range);
			for(int r = r1; r<=r2; r++) {
				int s=-q-r;
				HexCoordinate hex = UtilitiesHex.add(new HexCoordinate(q, s, r), starting);
				Point p = UtilitiesHex.getOffset(flatlayout, hex);
				if(p.x>=0 && p.x<dimensions.x) {
					if(p.y>=0 && p.y<dimensions.y) {
						if(board[p.x][p.y]!=null) {
							if(board[p.x][p.y].getQuickID().equals(quickID)){
								targets.add(new Point(p));
							}
						}
					}
				}
				
			}
		}
		
		return targets;
	}

	public static void highlightTargets(List<Point> targets, Graphics2D g, boolean flatlayout) {
		for(int i=0; i<targets.size(); i++) {
			g.setColor(Setting.highlightColor);
			Draw.drawHex(g, targets.get(i), null, flatlayout);
			g.setColor(Setting.defaultColor);
		}
	}
	
	public static void drawAttack(Graphics2D g, HexCoordinate center, int direction, int num, boolean flatlayout) {
		g.setColor(Color.cyan);
		Draw.drawHex(g, center, Setting.size, flatlayout, Setting.center, null);
		g.setColor(Color.cyan);

		HexCoordinate hex = UtilitiesHex.add(center, UtilitiesHex.direction(direction));
		Draw.drawHex(g, hex, Setting.size, flatlayout, Setting.center, null);
		
		/*
		 * Note: I no longer need to check if the direction is out of bounds since I do
		 * direciton modulo 6 in the direction function.
		 */
		
		if(num>=2) {	
			if(direction+1<=5) 
				hex = UtilitiesHex.add(center, UtilitiesHex.direction(direction+1));
			else
				hex = UtilitiesHex.add(center, UtilitiesHex.direction(0));
			
			Draw.drawHex(g, hex, Setting.size, flatlayout, Setting.center, null);
		}
		if(num>=3) {
			if(direction-1>=0) 
				hex = UtilitiesHex.add(center, UtilitiesHex.direction(direction-1));
			else
				hex = UtilitiesHex.add(center, UtilitiesHex.direction(5));
					
			Draw.drawHex(g, hex, Setting.size, flatlayout, Setting.center, null);
		}
		if(num>=4) {
			if(direction+2<=5) 
				hex = UtilitiesHex.add(center, UtilitiesHex.direction(direction+2));
			else if(direction+2==6)
				hex = UtilitiesHex.add(center, UtilitiesHex.direction(0));
			else
				hex = UtilitiesHex.add(center, UtilitiesHex.direction(1));
			
			Draw.drawHex(g, hex, Setting.size, flatlayout, Setting.center, null);
		}
		if(num>=5) {
			if(direction-2>=0) 
				hex = UtilitiesHex.add(center, UtilitiesHex.direction(direction-2));
			else if(direction-2==-1)
				hex = UtilitiesHex.add(center, UtilitiesHex.direction(5));
			else
				hex = UtilitiesHex.add(center, UtilitiesHex.direction(4));
					
			Draw.drawHex(g, hex, Setting.size, flatlayout, Setting.center, null);
		}
		if(num>=6) {
			if(direction+3<=5) 
				hex = UtilitiesHex.add(center, UtilitiesHex.direction(direction+3));
			else if(direction+3==6)
				hex = UtilitiesHex.add(center, UtilitiesHex.direction(0));
			else if(direction+3==7)
				hex = UtilitiesHex.add(center, UtilitiesHex.direction(1));
			else if(direction+3==8)
				hex = UtilitiesHex.add(center, UtilitiesHex.direction(2));
			
			Draw.drawHex(g, hex, Setting.size, flatlayout, Setting.center, null);
		}
	}
	
	
	public static boolean targetAloneToAlly(Enemy enemy, Hex[][] board, Point dimensions, boolean flatlayout) {
		List<Point> targets = new ArrayList<Point>();
		targets=createTargetList(board, 1, enemy.getCubeCoordiantes(flatlayout), "E", dimensions, flatlayout);
		
		if(targets.size()>0)
			return false;
		
		return true;
	}
	
	@SuppressWarnings("ucd")
	public static boolean targetAdjacentToAlly(Enemy enemy, List<Player> party, int playerIndex, Hex[][] board, Point dimensions) {
		
		List<Point> targets = new ArrayList<Point>();
		targets=createTargetList(board, 1, enemy.getCubeCoordiantes(board[enemy.getStartingPosition().x][enemy.getStartingPosition().y].getLayout()), "P", dimensions, board[enemy.getStartingPosition().x][enemy.getStartingPosition().y].getLayout());

		if(targets.size()>0) {
			for(int i=0; i<targets.size(); i++) {
				if(targets.get(i).equals(party.get(playerIndex).getCoordinates())) {
					targets.remove(i);
				}
			}
		}

		if(targets.size()>0)
			return true;
		else
			return false;
	}
	
}
