package Gloomhaven.Scenario;

import Gloomhaven.Door;
import Gloomhaven.Setting;
import Gloomhaven.Hex.Hex;

public final class ScenarioBoardLoader {
	
	public static Hex[][] loadBoardLayout(int id, ScenarioData data){
		Hex[][] board = new Hex[data.getBoardSize().x][data.getBoardSize().y];
		
		for(int x=0; x<data.getBoardSize().x; x++) {
			for(int y=0; y<data.getBoardSize().y; y++) {
				board[x][y] = new Hex(x, y, data.getHexLayout());
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
				
				board[7][8].setDoor(new Door());
				board[7][8].setRoomID(1);
				//room1
				board[6][0].setHidden(true);
				board[8][0].setHidden(true);
				for(int x=6; x<=8; x++) {
					for(int y=1; y<=7; y++)
						board[x][y].setHidden(true);
				}
				board[5][4].setHidden(true);
				board[5][4].setDoor(new Door());
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
				board[7][4].setDoor(new Door());
				board[7][4].setRoomID(1);
				
				board[10][8].setDoor(new Door(true));
				board[10][8].setRoomID(2);
				
				board[9][11].setDoor(new Door(true));
				board[9][11].setRoomID(3);
				
				board[5][11].setDoor(new Door(true));
				board[5][11].setRoomID(4);
				
				board[4][8].setDoor(new Door(true));
				board[4][8].setRoomID(5);
				
				//Sarcophagus
				board[7][9].setObstacle(true);
				board[7][9].setID("Sarcophagus");
				board[8][9].setObstacle(true);
				board[8][9].setID("Sarcophagus");
				board[6][6].setObstacle(true);
				board[6][6].setID("Sarcophagus");
				board[6][7].setObstacle(true);
				board[6][7].setID("Sarcophagus");
				board[8][6].setObstacle(true);
				board[8][6].setID("Sarcophagus");
				board[9][7].setObstacle(true);
				board[9][7].setID("Sarcophagus");
				
				board[9][15].setLootID("67");
				board[9][15].setID("Treasure");
				
				//room 1
				board[4][8].setHidden(true);
				for(int x=6; x<=9; x++) {
					board[x][5].setHidden(true);
				}
				for(int x=5; x<=9; x++) {
					for(int y=6; y<=11; y++) {
						board[x][y].setHidden(true);
					}
				}
				board[10][7].setHidden(true);
				board[10][8].setHidden(true);
				board[10][9].setHidden(true);
				board[10][11].setHidden(true);
				
				//room 2
				for(int x=12; x<16; x++) {
					board[x][7].setHidden(true);
					board[x-1][8].setHidden(true);
				}
				
				//room 3
				board[10][14].setHidden(true);
				board[11][11].setHidden(true);
				board[9][12].setHidden(true);
				board[10][12].setHidden(true);
				board[9][13].setHidden(true);
				board[10][13].setHidden(true);
				board[8][14].setHidden(true);
				board[9][14].setHidden(true);
				board[9][15].setHidden(true);
				
				//room 4
				board[3][11].setHidden(true);
				board[4][11].setHidden(true);
				board[3][12].setHidden(true);
				board[4][12].setHidden(true);
				board[4][13].setHidden(true);
				board[5][13].setHidden(true);
				board[4][14].setHidden(true);
				board[5][14].setHidden(true);
				board[5][15].setHidden(true);
				
				//room 5
				for(int x=0; x<=3; x++) {
					board[x][7].setHidden(true);
					board[x][8].setHidden(true);
				}
				
				for(int x=0; x<6; x++) {
					for(int y=0; y<6; y++) {
						board[x][y]=null;
					}
				}
				board[9][0]=null;
				board[9][2]=null;
				board[9][4]=null;
				for(int x=10; x<16; x++) {
					for(int y=0; y<6; y++) {
						board[x][y]=null;
					}
				}
				board[6][4]=null;
				board[8][4]=null;
				
				for(int x=0; x<5; x++) {
					board[x][6]=null;
					board[x][9]=null;
					board[x][10]=null;
				}
				board[10][6]=null;
				for(int x=11; x<16; x++) {
					board[x][6]=null;
					board[x][9]=null;
					board[x][10]=null;
				}
				
				int add=0;
				for(int rows=0; rows<5; rows++) {
					if(rows==2 || rows==4)
						add++;
					
					for(int x=0; x<3+add; x++) {
						board[x][11+rows]=null;
					}
				}
				
				board[10][10]=null;
				
				int sub=0;
				for(int rows=0; rows<5; rows++) {
					if(rows==1 || rows==3)
						sub++;
					
					for(int x=12-sub; x<16; x++) {
						board[x][11+rows]=null;
					}
				}
				
				board[7][15]=null;
				board[6][14]=null;
				board[7][14]=null;
				board[6][13]=null;
				board[7][13]=null;
				board[8][13]=null;
				board[5][12]=null;
				board[6][12]=null;
				board[7][12]=null;
				board[8][12]=null;
				board[8][15]=null;
				board[6][15]=null;
				board[4][7]=null;
				board[11][7]=null;
				board[15][8]=null;
				break;
			case 3:
				//room 1
				board[7][4].setHidden(true);
				board[9][4].setHidden(true);
				for(int x=6; x<=10; x++) {
					for(int y=0; y<=3; y++){
						board[x][y].setHidden(true);
					}
				}
				
				//room 2
				board[1][8].setHidden(true);
				board[3][8].setHidden(true);
				for(int x=0; x<=3; x++) {
					for(int y=5; y<=7; y++) {
						board[x][y].setHidden(true);
					}
				}
				
				//room 3
				board[13][8].setHidden(true);
				board[15][8].setHidden(true);
				for(int x=13; x<=16; x++) {
					for(int y=5; y<=7; y++) {
						board[x][y].setHidden(true);
					}
				}
				
				//room 4
				board[1][13].setHidden(true);
				board[3][13].setHidden(true);
				for(int x=0; x<=3; x++) {
					for(int y=10; y<=12; y++) {
						board[x][y].setHidden(true);
					}
				}
				
				//room 5
				board[13][13].setHidden(true);
				board[15][13].setHidden(true);
				for(int x=13; x<=16; x++) {
					for(int y=10; y<=12; y++) {
						board[x][y].setHidden(true);
					}
				}
				
				board[8][4].setDoor(new Door());
				board[8][4].setRoomID(1);
				board[4][6].setDoor(new Door());
				board[4][6].setRoomID(2);
				board[12][6].setDoor(new Door());
				board[12][6].setRoomID(3);
				board[4][11].setDoor(new Door());
				board[4][11].setRoomID(4);
				board[12][11].setDoor(new Door());
				board[12][11].setRoomID(5);
				
				board[7][0].setLootID("65");
				board[7][0].setID("Treasure");
				
				board[6][0].setLootID("Gold");
				board[6][0].setID("Gold");
				board[10][0].setLootID("Gold");
				board[10][0].setID("Gold");
				board[10][1].setLootID("Gold");
				board[10][1].setID("Gold");
				
				board[0][5].setLootID("Gold");
				board[0][5].setID("Gold");
				board[1][5].setLootID("Gold");
				board[1][5].setID("Gold");
				
				board[15][5].setLootID("Gold");
				board[15][5].setID("Gold");
				board[16][5].setLootID("Gold");
				board[16][5].setID("Gold");
				
				board[0][12].setLootID("Gold");
				board[0][12].setID("Gold");
				board[16][12].setLootID("Gold");
				board[16][12].setID("Gold");
				
				board[1][8].setObstacle(true);
				board[1][8].setID("Crate");
				board[14][5].setObstacle(true);
				board[14][5].setID("Crate");
				
				board[1][10].setObstacle(true);
				board[1][10].setID("Barrel");
				board[14][12].setObstacle(true);
				board[14][12].setID("Barrel");
				
				board[7][9].setObstacle(true);
				board[7][9].setID("Barrel");
				board[8][9].setObstacle(true);
				board[8][9].setID("Barrel");
				board[9][9].setObstacle(true);
				board[9][9].setID("Barrel");
				
				
				for(int x=0; x<6; x++) {
					for(int y=0; y<5; y++) {
						board[x][y]=null;
					}
				}
				board[6][4]=null;
				board[10][4]=null;
				
				for(int x=11; x<=16; x++) {
					for(int y=0; y<5; y++) {
						board[x][y]=null;
					}
				}
				board[0][8]=null;
				board[2][8]=null;

				board[4][5]=null;

				for(int y=7; y<=10; y++) {
					board[4][y]=null;
				}
				board[4][12]=null;
				board[4][13]=null;
				
				board[12][5]=null;

				for(int y=7; y<=10; y++) {
					board[12][y]=null;
				}
				board[12][12]=null;
				board[12][13]=null;
				
				board[0][13]=null;
				board[2][13]=null;
				board[14][13]=null;
				board[16][13]=null;
				
				for(int x=0; x<=3; x++) {
					board[x][9]=null;
					board[x][14]=null;
				}
				board[4][14]=null;
				for(int x=13; x<=16; x++) {
					board[x][9]=null;
					board[x][14]=null;
				}
				board[12][14]=null;
				board[14][8]=null;
				board[16][8]=null;
				board[6][14]=null;
				board[8][14]=null;
				board[10][14]=null;
				break;
			
		}
		
		return board;
	}
	
	public static void showRoom(Hex[][] board, int id, int room) {
		switch(id) {
			case 1:
				if(room==1) {
					board[7][8].getDoor().setDoorOpen(true);
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
					board[5][4].getDoor().setDoorOpen(true);
				}
				break;
			case 2:
				if(room==1) {
					board[7][4].getDoor().setDoorOpen(true);
					board[4][8].setHidden(false);
					for(int x=6; x<=9; x++) {
						board[x][5].setHidden(false);
					}
					for(int x=5; x<=9; x++) {
						for(int y=6; y<=11; y++) {
							board[x][y].setHidden(false);
						}
					}
					board[10][7].setHidden(false);
					board[10][8].setHidden(false);
					board[10][9].setHidden(false);
					board[10][11].setHidden(false);
				}else if(room==2) {
					board[10][8].getDoor().setDoorOpen(true);
					for(int x=12; x<16; x++) {
						board[x][7].setHidden(false);
						board[x-1][8].setHidden(false);
					}
				}else if(room==3) {
					board[9][11].getDoor().setDoorOpen(true);
					//room 3
					board[10][14].setHidden(false);
					board[11][11].setHidden(false);
					board[9][12].setHidden(false);
					board[10][12].setHidden(false);
					board[9][13].setHidden(false);
					board[10][13].setHidden(false);
					board[8][14].setHidden(false);
					board[9][14].setHidden(false);
					board[9][15].setHidden(false);
				}else if(room==4) {
					board[5][11].getDoor().setDoorOpen(true);
					board[3][11].setHidden(false);
					board[4][11].setHidden(false);
					board[3][12].setHidden(false);
					board[4][12].setHidden(false);
					board[4][13].setHidden(false);
					board[5][13].setHidden(false);
					board[4][14].setHidden(false);
					board[5][14].setHidden(false);
					board[5][15].setHidden(false);
				}else if(room==5) {
					board[4][8].getDoor().setDoorOpen(true);
					for(int x=0; x<=3; x++) {
						board[x][7].setHidden(false);
						board[x][8].setHidden(false);
					}
				}
				break;
			case 3:
				if(room==1) {
					board[7][4].setHidden(false);
					board[9][4].setHidden(false);
					for(int x=6; x<=10; x++) {
						for(int y=0; y<=3; y++){
							board[x][y].setHidden(false);
						}
					}
				}else if(room==2) {
					//room 2
					board[1][8].setHidden(false);
					board[3][8].setHidden(false);
					for(int x=0; x<=3; x++) {
						for(int y=5; y<=7; y++) {
							board[x][y].setHidden(false);
						}
					}
				}else if(room==3) {
					//room 3
					board[13][8].setHidden(false);
					board[15][8].setHidden(false);
					for(int x=13; x<=16; x++) {
						for(int y=5; y<=7; y++) {
							board[x][y].setHidden(false);
						}
					}
				}else if(room==4) {
					//room 4
					board[1][13].setHidden(false);
					board[3][13].setHidden(false);
					for(int x=0; x<=3; x++) {
						for(int y=10; y<=12; y++) {
							board[x][y].setHidden(false);
						}
					}
				}else if(room==5) {
					//room 5
					board[13][13].setHidden(false);
					board[15][13].setHidden(false);
					for(int x=13; x<=16; x++) {
						for(int y=10; y<=12; y++) {
							board[x][y].setHidden(false);
						}
					}
				}
				break;
		}
	}
}
