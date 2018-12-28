package Gloomhaven;

public class StatsTracker {

	private String what_is_being_tracked;
	private int count=1;
	
	public StatsTracker(String whatItIs, int count) {
		this.count=count;
		what_is_being_tracked=whatItIs;
	}
	
	public String getWhatIsBeingTracked() {return what_is_being_tracked;}
	public int getCount() {return count;}
	public void setCount(int count) {this.count=count;}
	
}
