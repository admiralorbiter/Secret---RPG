package Gloomhaven;

import javax.swing.ImageIcon;

public class SimpleCardData {
	
	Target target = new Target();
	
	String name;
	String cardText;
	int initiative;
	ImageIcon image;
	int attack;
	int move;
	int range;
	int xpOnUse;
	
	boolean discardFlag;
	boolean consumeFlag;
	boolean roundBonusFlag;
	
	boolean jumpFlag;
	boolean flyFlag;
	
	public SimpleCardData() {
		name="Default Name";
		cardText="Default Text";
		initiative=-99;
		image=null;
		attack=0;
		move=0;
		range=0;
		xpOnUse=0;
		
		discardFlag=true;
		consumeFlag=false;
		roundBonusFlag=false;
		
		jumpFlag=false;
		flyFlag=false;
	}
	
	public boolean getJumpFlag() {
		return jumpFlag;
	}

	public void setJumpFlag(boolean jumpFlag) {
		this.jumpFlag = jumpFlag;
	}

	public boolean getFlyFlag() {
		return flyFlag;
	}

	public void setFlyFlag(boolean flyFlag) {
		this.flyFlag = flyFlag;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCardText() {
		return cardText;
	}

	public void setCardText(String cardText) {
		this.cardText = cardText;
	}

	public int getInitiative() {
		return initiative;
	}

	public void setInitiative(int initiative) {
		this.initiative = initiative;
	}

	public ImageIcon getImage() {
		return image;
	}

	public void setImage(ImageIcon image) {
		this.image = image;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getMove() {
		return move;
	}

	public void setMove(int move) {
		this.move = move;
	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public int getXpOnUse() {
		return xpOnUse;
	}

	public void setXpOnUse(int xpOnUse) {
		this.xpOnUse = xpOnUse;
	}

	public boolean getDiscardFlag() {
		return discardFlag;
	}

	public void setDiscardFlag(boolean discard) {
		this.discardFlag = discard;
		this.consumeFlag = !discard;
	}

	public boolean getConsumeFlag() {
		return consumeFlag;
	}

	public void setConsumeFlag(boolean consume) {
		this.consumeFlag = consume;
		this.discardFlag=!consume;
	}

	public boolean getRoundBonusFlag() {
		return roundBonusFlag;
	}

	public void setRoundBonusFlag(boolean roundBonus) {
		this.roundBonusFlag = roundBonus;
	}
}
