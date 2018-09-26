package Gloomhaven.TempStorage;

public class CardsMindthief implements Cards {

	@Override
	public CardDataObject getTop(String name) {
		CardDataObject top = new CardDataObject();
		
		if(name=="Submissive Afflication") {
			top.text="Attack +2; Add +1 attack for each negative condition on the target. +1 Exp";
			top.attack=2;
			top.dependsOnTarget=true;//haven't dealt with this yet
			top.experience=1;
		}
		else if(name=="Into the Night") {
			top.text="Loot +1 and creates dark";
			top.loot=1;
			top.createsElement=true;//haven't set element anywhere
		}
		else if(name=="Fearsome Blade") {
			top.text="Attack +2, Push +3, Exp +1";
			top.attack=2;
			top.push=3;
			top.experience=1;
		}
		else if(name=="Feedback Loop") {
			top.text = "Augment: On your melee attacks: Gain +1 shield.  Attack +1, Exp +1. Discard";
			top.augument=true;
			top.attack=1;
			top.experience=1;
			top.discard=true;
		}
		else if(name=="Gnawing Horde") {
			top.text = "Summom Rat Swarm: Health 6, Movement 1, Attack 2, Range 0, Poison. Exp +2. Continous. Lost";
			top.summons=true;//need to implement
			top.experience=2;
			top.continuous=true;
			top.lost=true;//need to be careful to only lose it after it expires
		}
		else if(name=="The Mind's Weakness") {
			top.text = "Augument: On your melee attacks: add +2 Attack. Attack +1. Exp +1";
			top.augument=true;//need to implment
			top.attack=2;
			top.experience=1;
		}
		else if(name=="Parasite Influence") {
			top.text = "Augument: Heal +2 Self. Attack +1. Exp +1";
			top.augument=true;//need to implment
			top.attack=1;
			top.experience=1;
		}
		else if(name=="Scurry") {
			top.text = "Move +3, Attack +1";
			top.move=3;
			top.attack=1;
		}
		else if(name=="Perverse Edge") {
			top.text = "Attack +3. Add +2 Attack and gain +1 exp for each negative condition on the target. Lost";
			top.attack=3;
			top.dependsOnTarget=true;//need to implement
			top.lost=true;
		}
		else if(name=="Empathetic Assault") {
			top.text = "Attack +4, Range +5, Disarm, Create Ice, Exp +2. Lost";
			top.attack=4;
			top.range=5;
			top.statusEffect=true;//need to implement
			top.createsElement=true;//need to implment
			top.experience=2;
			top.lost=true;
		}
			
		return top;
	}

	@Override
	public CardDataObject getBottom(String name) {
		CardDataObject bottom = new CardDataObject();
		
		if(name=="Submissive Afflication") {
			bottom.text="Force one enemy within +5 range to perform +2 Attack/+0 Range targeting another enemy with you controlling the action.";
			bottom.mindcontrol=true;//need to implment
			bottom.range=5;
		}
		else if(name=="Into the Night") {
			bottom.text="Invisibility on Self";
			bottom.invisible=true;
		}
		else if(name=="Fearsome Blade") {
			bottom.text="Move +4, Attack +2, Exp+2, Lost";
			bottom.move=4;
			bottom.attack=2;
			bottom.experience=2;
			bottom.lost=true;
		}
		else if(name=="Feedback Loop") {
			bottom.text = "Move +4, Jump, If you end the movement in the same hex you started in, perform Muddle. Target all enemeies moved through";
			bottom.move=4;
			bottom.jump=true;
			bottom.complex=true;//need to implement
		}
		else if(name=="Gnawing Horde") {
			bottom.text = "Move +4";
			bottom.move=4;
		}
		else if(name=="The Mind's Weakness") {
			bottom.text = "Attack +1. Wound";
			bottom.attack=1;
			bottom.statusEffect=true;//need to implement
		}
		else if(name=="Parasite Influence") {
			bottom.text = "Force one eney within Range +4 to perform Move +1 with you controlling the action.";
			bottom.mindcontrol=true;//need to implement
			bottom.range=1;
		}
		else if(name=="Scurry") {
			bottom.text = "Loot +2, Exp +1";
			bottom.loot=2;
			bottom.experience=1;
		}
		else if(name=="Perverse Edge") {
			bottom.text = "Attack +1, Range +2, Stun, Create Ice, Exp +1";
			bottom.attack=1;
			bottom.range=2;
			bottom.statusEffect=true;//need to implement
			bottom.createsElement=true;//need to implement
			bottom.experience=1;
		}
		else if(name=="Empathetic Assault") {
			bottom.text = "Move +2, Heal +2 (Self)";
			bottom.move=2;
			bottom.heal=2;
		}
	
		return bottom;
	}

	@Override
	public CardDataObject getData(int i) {
		// TODO Auto-generated method stub
		return null;
	}


	
}
