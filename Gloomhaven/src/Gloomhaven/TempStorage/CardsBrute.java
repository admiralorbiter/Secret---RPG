package Gloomhaven.TempStorage;

public class CardsBrute implements Cards {
	@Override
	public CardDataObject getTop(String name) {
		CardDataObject top = new CardDataObject();
		
		if(name=="Temple") {
			top.text="Attack +3; Pierce +2";
			top.attack=3;
			top.pierce=2;
		}
		else if(name=="Eye for an Eye") {
			top.text="Retaliate +2 (Self); Gain +1 Exp for each time you retaliate this round. Round bonus.";
			top.complex=true;
			top.retaliate=true;
			top.roundbonus=true;
		}
		else if(name=="Sweeping Blow") {
			top.text="Attack +2; AOE";
			top.aoe=true;
		}
		else if(name=="Provoking Roar") {
			top.text="Attack +2; Disarm";
			top.attack=2;
			top.statusEffect=true;
		}
		else if(name=="Overwhelming Assault") {
			top.text="Attack +6; Exp +2; Lost.";
			top.attack=6;
			top.experience=2;
			top.lost=true;
		}
		else if(name=="Grab and Go") {
			top.text="Loot +1";
			top.loot=1;
		}
		else if(name=="Warding Strength") {
			top.text="Attack +3; Push +2";
			top.attack=3;
			top.push=2;
		}
		else if(name=="Shield Bash") {
			top.text="Attack +4; Stun; Exp +2; Lost.";
			top.attack=4;
			top.statusEffect=true;
			top.experience=2;
			top.lost=true;
		}
		else if(name=="Leaping Cleave") {
			top.text="Attack +3; Exp +1; AOE";
			top.attack=3;
			top.experience=1;
			top.aoe=true;
		}
		else if(name=="Spare Dagger") {
			top.text="Attack +3; Range +3; Exp +1";
			top.attack=3;
			top.range=3;
			top.experience=1;
		}
			
		return top;
	}

	@Override
	public CardDataObject getBottom(String name) {
		CardDataObject bottom = new CardDataObject();
		
		if(name=="Temple") {
			bottom.text="Move +4; Jump; Attack +2; Target all enemies moved through; Exp +2; Lost.";
			bottom.complex=true;
			bottom.move=4;
			bottom.jump=true;
			bottom.attack=2;
			bottom.experience=2;
			bottom.lost=true;
		}
		else if(name=="Eye for an Eye") {
			bottom.text="Heal +2; Range +1; Create earth.";
			bottom.heal=2;
			bottom.range=1;
			bottom.createsElement=true;
		}
		else if(name=="Sweeping Blow") {
			bottom.text="Move +3; Push +1; Target all adjacent enemies";
			bottom.move=3;
			bottom.push=1;
			bottom.complex=true;
		}
		else if(name=="Provoking Roar") {
			bottom.text="Any enemy who targets one of your adjacent allies with an attack this round targets you with that attack instead, regardless of attack's range. Round Bonus.";
			bottom.complex=true;
			bottom.roundbonus=true;
		}
		else if(name=="Overwhelming Assault") {
			bottom.text="Move +3; Push +2; Target one adjacent enemy";
			bottom.move=3;
			bottom.push=2;
			bottom.complex=true;
		}
		else if(name=="Grab and Go") {
			bottom.text="Move +4";
			bottom.move=4;
		}
		else if(name=="Warding Strength") {
			bottom.text="One the next six sources of damage from attacks targeting you, gain Shield +1. Continuous; Lost.";
			bottom.continuous=true;
			bottom.complex=true;
			bottom.lost=true;
		}
		else if(name=="Shield Bash") {
			bottom.text="Shield +1 (Self); Round bonus.";
			bottom.shield=1;
			bottom.roundbonus=true;
		}
		else if(name=="Leaping Cleave") {
			bottom.text="Move +3; Jump; Create Air.";
			bottom.move=3;
			bottom.jump=true;
			bottom.createsElement=true;
		}
		else if(name=="Spare Dagger") {
			bottom.text="Attack +2";
			bottom.attack=2;
		}
		
		return bottom;
	}

	@Override
	public CardDataObject getData(int i) {
		// TODO Auto-generated method stub
		return null;
	}
}
