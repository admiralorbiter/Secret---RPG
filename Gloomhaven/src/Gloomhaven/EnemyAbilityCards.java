package Gloomhaven;

import java.awt.Graphics;

public class EnemyAbilityCards {
	private String name;
	private int initiative;
	private boolean lost;
	private boolean discard;
	private boolean inPlay;
	private int level;
	Setting setting = new Setting();
	CardDataObject data = new CardDataObject();
	CardInterface card;
	
	public EnemyAbilityCards(int level, int id, String Class) {
		if(Class=="Test") {
			card = new CardsEnemyTest();
		}
		
		this.level=level;
		data=card.getData(id);
		name=Class;
		initiative=data.initiative;
	}
	
	public int getAttack() {return data.attack;}
	public int getMove() {return data.move;}
	public int getRange() {return data.range;}
	
	public void graphicsAbilityCard(Graphics g) {
		g.drawString("Enemy Ability Card", setting.getGraphicsXMid(), setting.getGraphicsYBottom());
		g.drawString("Attack: "+data.attack+"  Move: "+data.move+" Range: "+data.range, setting.getGraphicsXMid(), setting.getGraphicsYBottom()+15);
	}
	
	public int getInitiatve() {return initiative;}
	
	public boolean cardFree() {
		if(lost)
			return false;
		if(discard)
			return false;
		if(inPlay)
			return false;
		
		return true;
	}
}
