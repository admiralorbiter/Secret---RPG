package Gloomhaven.CardDataObject;

import javax.swing.ImageIcon;

public class SimpleCardData {
	
	private Target target = new Target();
	private String cardText="Default Text";
	private int attack=0;
	private int move=0;
	private int range=0;
	private int xpOnUse=0;
	
	private boolean discardFlag=false;
	private boolean consumeFlag=false;
	private boolean roundBonusFlag=false;
	
	private boolean jumpFlag=false;
	private boolean flyFlag=false;
	
	public SimpleCardData() {
		discardFlag=true;
	}
	
	public SimpleCardData(int attack, int move, int range) {
		this.attack=attack;
		this.move=move;
		this.range=range;
	}
	
	//Getters and Setters
	public boolean getJumpFlag() {return jumpFlag;}
	public void setJumpFlag(boolean jumpFlag) {this.jumpFlag = jumpFlag;}
	public boolean getFlyFlag() {return flyFlag;}
	public void setFlyFlag(boolean flyFlag) {this.flyFlag = flyFlag;}
	public String getCardText() {return cardText;}
	public void setCardText(String cardText) {this.cardText = cardText;}
	public int getAttack() {return attack;}
	public void setAttack(int attack) {this.attack = attack;}
	public int getMove() {return move;}
	public void setMove(int move) {this.move = move;}
	public int getRange() {return range;}
	public void setRange(int range) {this.range = range;}
	public int getXpOnUse() {return xpOnUse;}
	public void setXpOnUse(int xpOnUse) {this.xpOnUse = xpOnUse;}
	public boolean getDiscardFlag() {return discardFlag;}
	public boolean getConsumeFlag() {return consumeFlag;}
	public boolean getRoundBonusFlag() {return roundBonusFlag;}
	public void setRoundBonusFlag(boolean roundBonus) {this.roundBonusFlag = roundBonus;}
	public Target getTarget() {return target;}
	public void setTarget(Target target) {this.target = target;}

	public void setConsumeFlag(boolean consume) {
		this.consumeFlag = consume;
		this.discardFlag=!consume;
	}
	public void setDiscardFlag(boolean discard) {
		this.discardFlag = discard;
		this.consumeFlag = !discard;
	}
	
}
