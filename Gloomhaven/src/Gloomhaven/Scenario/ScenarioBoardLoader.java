package Gloomhaven.Scenario;

import Gloomhaven.Setting;
import Gloomhaven.Hex.Hex;

public final class ScenarioBoardLoader {
	
	public static Hex[][] loadBoardLayout(int id, ScenarioData data){
		Hex[][] board = new Hex[data.getBoardSize().x][data.getBoardSize().y];
		
		for(int x=0; x<data.getBoardSize().x; x++) {
			for(int y=0; y<data.getBoardSize().y; y++) {
				board[x][y] = new Hex(x, y, Setting.flatlayout);
			}
		}
		
		switch(id) {
			case 1:
				//Set Gold
				board[1][6].setLootID("Gold");
				board[1][6].setID("Gold");
				board[2][6].setLootID("Gold");
				board[2][6].setID("Gold");
				board[0][1].setLootID("Gold");
				board[0][1].setID("Gold");
				board[1][2].setLootID("Gold");
				board[1][2].setID("Gold");
				board[2][1].setLootID("Gold");
				board[2][1].setID("Gold");
				
				//tables
				board[3][2].setObstacle(true);
				board[3][2].setID("Table");
				board[3][3].setObstacle(true);
				board[3][3].setID("Table");
				board[3][5].setObstacle(true);
				board[3][5].setID("Table");
				board[3][6].setObstacle(true);
				board[3][6].setID("Table");
				
				board[0][6].setLootID("7");
				board[0][6].setID("Treasure");
				
				board[10][0]=null;
				board[10][1]=null;
				board[10][2]=null;
				board[10][3]=null;
				board[10][4]=null;
				board[10][5]=null;
				board[10][6]=null;
				board[10][7]=null;
				board[9][0]=null;
				board[9][1]=null;
				board[9][2]=null;
				board[9][3]=null;
				board[9][4]=null;
				board[9][5]=null;
				board[9][6]=null;
				board[9][7]=null;
				board[9][8]=null;
				board[1][0]=null;
				board[3][0]=null;
				board[5][0]=null;
				board[7][0]=null;
				board[9][0]=null;
				board[0][0]=null;
				board[1][1]=null;
				board[2][0]=null;
				board[3][1]=null;
				board[4][0]=null;
				board[5][1]=null;
				board[0][7]=null;
				board[0][8]=null;
				board[0][9]=null;
				board[0][10]=null;
				board[0][11]=null;
				board[0][12]=null;
				board[1][7]=null;
				board[2][7]=null;
				board[3][7]=null;
				board[4][7]=null;
				board[5][7]=null;
				board[5][2]=null;
				board[5][3]=null;
				board[5][5]=null;
				board[5][6]=null;
				board[1][8]=null;
				board[2][8]=null;
				board[3][8]=null;
				board[1][9]=null;
				board[2][9]=null;
				board[3][9]=null;
				board[1][10]=null;
				board[2][10]=null;
				board[3][10]=null;
				board[1][11]=null;
				board[2][11]=null;
				board[3][11]=null;
				board[1][12]=null;
				board[2][12]=null;
				board[3][12]=null;
				board[5][8]=null;
				
				board[7][8].setDoor(true);
				board[7][8].setRoomID(1);
				//room1
				board[6][0].setHidden(true);
				board[8][0].setHidden(true);
				for(int x=6; x<=8; x++) {
					for(int y=1; y<=7; y++)
						board[x][y].setHidden(true);
				}
				board[5][4].setHidden(true);
				board[5][4].setDoor(true);
				board[5][4].setRoomID(2);
				
				//room2
				board[0][1].setHidden(true);
				board[2][1].setHidden(true);
				board[4][1].setHidden(true);
				for(int x=0; x<=4; x++) {
					for(int y=2; y<=6; y++) {
						board[x][y].setHidden(true);
					}
				}
				break;
			case 2:
				
				break;
		}
		
		return board;
	}
	
	public static void showRoom(Hex[][] board, int id, int room) {
		switch(id) {
			case 1:
				if(room==1) {
					board[7][8].setDoorOpen(true);
					board[6][0].setHidden(false);
					board[8][0].setHidden(false);
					for(int x=6; x<=8; x++) {
						for(int y=1; y<=7; y++)
							board[x][y].setHidden(false);
					}
					board[5][4].setHidden(false);
				}else if(room==2) {
					//room2
					board[0][1].setHidden(false);
					board[2][1].setHidden(false);
					board[4][1].setHidden(false);
					for(int x=0; x<=4; x++) {
						for(int y=2; y<=6; y++) {
							board[x][y].setHidden(false);
						}
					}
					board[5][4].setDoorOpen(true);
				}
				break;
		}
	}
}
