package Gloomhaven;

public class BattleGoalCard {
	private String name="";
	private String text="";
	private int reward=0;
	private int id;
	
	public BattleGoalCard(int id) {
		this.id=id;
	}
	
	public void setName(String name) {this.name=name;}
	public void setText(String text) {this.text=text;}
	public void setReward(int reward) {this.reward=reward;}
	public String getName() {return name;}
	public String getText() {return text;}
	public int getReward() {return reward;}
}
