package Gloomhaven.PersonalQuests;

public class PersonalQuestCard {
	private String title="Person Quest Card Error";
	private String text="Person Quest Card Error";
	private String criteria="Person Quest Card Error";
	
	public PersonalQuestCard(String title, String text, String criteria) {
		this.title=title;
		this.text=text;
		this.criteria=criteria;
	}
	
	//Getters and Setters
	public String getTitle() {return title;}
	public String getText() {return text;}
	public String getCriteria() {return criteria;}
	public void setTitle(String title) {this.title=title;}
	public void setText(String text) {this.text=text;}
	public void setCriteria(String criteria) {this.criteria=criteria;}
}
