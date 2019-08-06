package Unsorted;

import java.awt.event.KeyEvent;
import java.util.List;

import Gloomhaven.Characters.EnemyInfo;
import Gloomhaven.Characters.Player;

public final class UtilitiesGeneral {
	//Parses key event into either a character or a number to be used
	public static char parseKeyCharacter(KeyEvent key) {
		char k=Character.MIN_VALUE;
		try{
			k = key.getKeyChar();
			}catch(NullPointerException ex){ 														// handle null pointers for getKeyChar
			   
			}
		return k;
	}
	
	public static int parseKeyNum(KeyEvent key) {
		char k=Character.MIN_VALUE;
		try{
			k = key.getKeyChar();
			}catch(NullPointerException ex){ 														// handle null pointers for getKeyChar
			   
			}
		int num = -1;
		try{
			num=Integer.parseInt(String.valueOf(k));  
			}catch(NumberFormatException ex){ 														// handle if it isn't a number character
			}   
		return num;
	}
	
	public static void setTurnNumbers(List<Player> party, EnemyInfo enemyInfo) {
		int size = party.size()+enemyInfo.getEnemyAbilityDeck().size();
		String[][] turnList = new String[size][3];
		
		/*
		for(int i=0; i<size; i++) {
			System.out.print(turnList[i][0]+" "+turnList[i][2]+" , ");
		}
		System.out.println("");
		*/
		
		int index=0;
		for(int i=0; i<party.size(); i++) {
			turnList[index][0]="Player";
			turnList[index][1]=party.get(i).getID();
			turnList[index][2]=Integer.toString(party.get(i).getInitiative());
			index++;
		}
		
		for(int i=0; i<(enemyInfo.getEnemyAbilityDeck().size()); i++) {
			turnList[index][0]="Enemy";
			turnList[index][1]=enemyInfo.getEnemyAbilityDeck().get(i).getDeckID();
			turnList[index][2]=Integer.toString(enemyInfo.getEnemyAbilityDeck().get(i).getInitiative());
			index++;
		}
		
		for(int i=0; i<size-1; i++) {
			int min = i;
			for(int j=i+1; j<size; j++) {
				if(Integer.valueOf(turnList[j][2])<Integer.valueOf(turnList[min][2])) {
					min=j;
				}
			}
			String tempQuickID = turnList[min][0];
			String tempDeckID = turnList[min][1];
			String tempInit=turnList[min][2];
			
			turnList[i][0]=turnList[min][0];
			turnList[i][1]=turnList[min][1];
			turnList[i][2]=turnList[min][2];
			
			turnList[min][0]=tempQuickID;
			turnList[min][1]=tempDeckID;
			turnList[min][2]=tempInit;
		}
		
		System.out.println("");
		System.out.print("Init List: ");
		for(int i=0; i<size; i++) {
			System.out.print(turnList[i][0]+" "+turnList[i][2]+" , ");
		}
		
		int turnNum=0;
		
		for(int i=0; i<size; i++) {
			for(int j=0; j<party.size(); j++) {
				if(party.get(j).getID().equals(turnList[i][1])) {
					party.get(j).setTurnNumber(turnNum);
					turnNum++;
				}
			}
			
			for(int j=0; j<enemyInfo.getEnemyAbilityDeck().size(); j++) {
				if(enemyInfo.getEnemyAbilityDeck().get(j).getDeckID().equals(turnList[i][1])){
					enemyInfo.getEnemyAbilityDeck().get(j).setTurnNumber(turnNum);
					turnNum++;
				}
			}
		}
	}
	
}
