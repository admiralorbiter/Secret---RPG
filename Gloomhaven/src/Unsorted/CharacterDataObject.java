package Unsorted;

public class CharacterDataObject {
	
	private int maxHealth;
	
	private int level;
	private int health;
	private int xp;
	private int shield=0;
	private int gold;
	private boolean bossFlag=false;
	
	public CharacterDataObject(String flag, boolean eliteFlag) {
		
		//TODO: Move this into settings or class
		int TEST_HEALTH=Setting.TEST_HEALTH;
		int TEST_SHIELD=Setting.TEST_SHIELD;
		int scenarioLevel=1;
		
		if(Setting.TestStats && flag!="Player") {
			health=TEST_HEALTH;
			shield=TEST_SHIELD;
		}else {
			switch(flag) {
				case "Bandit Guard":
					health=5;
					shield=0;
					break;
				case "Bandit Archer":
					health=4;
					shield=0;
					break;
				case "Living Bones":
					health=5;
					shield=0;
					break;
				case "Bandit Commander":
					health=TEST_HEALTH;
					shield=TEST_SHIELD;
					maxHealth=TEST_HEALTH;
					bossFlag=true;
					break;
				case "Inox Guard":
					health=TEST_HEALTH;
					shield=TEST_SHIELD;
					maxHealth=TEST_HEALTH;
					break;
				case "Inox Archer":
					health=TEST_HEALTH;
					shield=TEST_SHIELD;
					maxHealth=TEST_HEALTH;
					break;
				case "Inox Shaman":
					health=TEST_HEALTH;
					shield=TEST_SHIELD;
					maxHealth=TEST_HEALTH;
					break;
				default:
					level=1;
					health=450;
					xp=0;
					shield=0;
					level=1;
					maxHealth=450;
					gold=100;
			}
		}	
		
		maxHealth=health;
		
		if(eliteFlag) {
			health=health+scenarioLevel;
			shield=shield+scenarioLevel;
			maxHealth=maxHealth+scenarioLevel;
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
	public boolean getBossFlag() {return bossFlag;}
	
	public void changeGold(int change) {
		gold=gold+change;
		if(gold<0)
			gold=0;
		}
}
