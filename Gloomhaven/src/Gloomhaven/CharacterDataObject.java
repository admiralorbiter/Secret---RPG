package Gloomhaven;

public class CharacterDataObject {
	
	private int maxHealth;
	
	private int level;
	private int health;
	private int xp;
	private int shield;
	private int gold;
	
	Setting setting = new Setting();
	
	public CharacterDataObject(String flag) {
		
		//TODO: Move this into settings or class
		int TEST_HEALTH=50;
		int TEST_SHIELD=0;
		int scenarioLevel=1;
		
		switch(flag) {
			case "Test":
				health=TEST_HEALTH;
				shield=TEST_SHIELD;
				maxHealth=TEST_HEALTH;
				break;
			case "TestElite":
				health=TEST_HEALTH+scenarioLevel;
				shield=TEST_SHIELD+scenarioLevel;
				maxHealth=maxHealth+scenarioLevel;
			default:
				level=1;
				health=450;
				xp=0;
				shield=0;
				level=1;
		}
	}
	
	//GETTERS and SETTERS
	public int getMaxHealth() {return maxHealth;}
	public void setMaxHealth(int maxHealth) {this.maxHealth = maxHealth;}
	public int getLevel() {return level;}
	public void setLevel(int level) {this.level = level;}
	public int getHealth() {return health;}
	public void setHealth(int health) {this.health = health;}
	public int getXp() {return xp;}
	public void setXp(int xp) {this.xp = xp;}
	public int getShield() {return shield;}
	public void setShield(int shield) {this.shield = shield;}
	public int getGold() {return gold;}
	public void setGold(int gold) {this.gold = gold;}
}
