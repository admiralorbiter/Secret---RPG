package Gloomhaven;

public class Trigger extends SimpleCards{
	public int count=0;
	public int triggerCount=0;
	public String triggerName="";
	public String name="";
	boolean finished=false;
	public Trigger() {
		finished=false;
	}
	
	public int getCurrentTriggerCount() {return count;}
	public int getTriggerAmountNeeded() {return triggerCount;}
	public String getTriggerName() {return triggerName;}
	
	public boolean aloneBonus;
	public SimpleCards aloneBonusData = new SimpleCards();
	public SimpleCards getAloneBonusData() {return aloneBonusData;}
	public void addToTrigger() {
		count++;
		if(count==(triggerCount))
			triggerReset();
	}
	
	private void triggerReset() {
		attack=0;
		move=0;
		experience=0;
		range=0;
		count=0;
		triggerCount=0;
		shield=0;
		aloneBonus=false;
		aloneBonusData = new SimpleCards();
		finished=true;
	}
	
	public boolean isFinished() {return finished;}
	public String getName() {return name;}
}
