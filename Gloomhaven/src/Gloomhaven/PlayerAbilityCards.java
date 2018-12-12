package Gloomhaven;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PlayerAbilityCards {
	private String name;
	private int initiative;
	private boolean lost;
	private boolean discard;
	private boolean inPlay;
	private int index;												//Index in the full ability deck - pretty close to the id but I want to change the id
	CardDataObject top = new CardDataObject();
	CardDataObject bottom = new CardDataObject();
	CardInterface card;
	private int flag;												//-1 not used. 0 top 1 bottom 2 alt top 3 alt bottom
	CardDataObject altTop = new CardDataObject();
	CardDataObject altBottom = new CardDataObject();
	BufferedImage image = null;
	
	public PlayerAbilityCards(int level, int id, String Class) {
		index=id-1;
		flag=-1;
		altTop.attack=2;
		altBottom.move=2;
		if(Class=="Test") {
			card = new CardsTest();
			image = CardImages.getMindThiefCard(id);
		}
		else if(Class=="Mind Thief") {
			card = new MindThief();
			image = CardImages.getMindThiefCard(id);
		}
		else if(Class=="Brute") {
			card = new Brute();
			image = CardImages.getBruteCard(id);
		}
		else if(Class=="Scoundrel") {
			card = new Scoundrel();
			image = CardImages.getScoundrelCard(id);
		}
		else if(Class=="Spellweaver") {
			card = new Spellweaver();
			image = CardImages.getSpellweaverCard(id);
		}
		else if(Class=="Cragheart") {
			card = new Cragheart();
			image = CardImages.getCragheartCard(id);
		}
		else if(Class=="Tinkerer") {
			card = new Tinkerer();
			image = CardImages.getTinkererCard(id);
		}
		
		if(level==1) {
			top=card.getTop(id);
			bottom=card.getBottom(id);
			name=top.getName();
			initiative=top.getInitiative();
		}
		
	}
	
	public boolean getLostFlag() {return lost;}
	
	PlayerAbilityCards(PlayerAbilityCards a) {
		this.name=a.name;
		this.initiative=a.initiative;
		this.lost=a.lost;
		this.discard=a.discard;
		this.inPlay=a.inPlay;
		this.index=a.index;
		this.top=a.top;
		this.bottom=a.bottom;
		this.flag=flag;
	}
	public String getName() {return name;}
	public PlayerAbilityCards() {
		index=-1;
		flag=-1;
		altTop.attack=2;
		altBottom.move=2;
	}

	public boolean cardInDiscardPile() {return discard;}
	
	public void takeOutOfDiscard() {
		discard=false;
	}
	
	CardDataObject getTop() {return top;}
	CardDataObject getBottom() {return bottom;}
	
	String getText() {
		String text=initiative+": "+top.text+" - "+bottom.text;
		return text;
	}
	
	int getInitiative() {
		return initiative;
	}
	
	public int getFlag() {return flag;}
	public void useTop() {flag=0;}
	public void useBottom() {flag=1;}
	public void useTopAlt() {flag=2;}
	public void useBottomAlt() {flag=3;}
	
	public boolean cardFree() {
		if(lost)
			return false;
		if(discard)
			return false;
		if(inPlay)
			return false;
		
		return true;
	}
	
	public void reset() {
		inPlay=false;
		lost=false;
		discard=false;
	}
	
	public void setInPlay() {
		inPlay=true;
		lost=false;
		discard=false;
	}
	
	public void lostPile() {
		inPlay=false;
		discard=false;
		lost=true;
	}
	
	public void discardPile() {
		inPlay=false;
		lost=false;
		discard=true;
	}
	
	public int getIndex() {return index;}
	
	public CardDataObject getData() {
		if(flag==0)
			return top;
	
		if(flag==1)
			return bottom;
		
		if(flag==2)
			return altTop;
		
		if(flag==3)
			return altBottom;
		
		return null;
	}
	
	//[Temp]
	public int getMove() {
		if(flag==0)
			return top.getMove();
	
		if(flag==1)
			return bottom.getMove();
		
		if(flag==2)
			return 0;
		
		if(flag==3)
			return 2;
		
		return 0;
	}
	
	public int getAttack() {
		if(flag==0)
			return top.getAttack();
	
		if(flag==1)
			return bottom.getAttack();
		
		if(flag==2)
			return 2;
		
		if(flag==3)
			return 0;
		
		return 0;
	}
	
	public int getRange() {
		if(flag==0)
			return top.getRange();
	
		if(flag==1)
			return bottom.getRange();
		
		if(flag==2)
			return 0;
		
		if(flag==3)
			return 0;
		
		return 0;
	}
	
	public boolean getAugment() {
	
		if(flag==0)
			return top.getAugment();
	
		if(flag==1)
			return bottom.getAugment();
	
		return false;
	}

	public boolean getJump() {
		if(flag==0)
			return top.getJump();
	
		if(flag==1)
			return bottom.getJump();
	
		return false;
	}
	
	public boolean getFlying() {
		if(flag==0)
			return top.getFlying();
	
		if(flag==1)
			return bottom.getFlying();
	
		return false;
	}
	
	public int getPierce() {
		if(flag==0)
			return top.getPierce();
	
		if(flag==1)
			return bottom.getPierce();
	
		return 0;
	}
	
	public boolean getTargetHeal() {
		if(flag==0)
			if(top.getHeal()>0 && top.getRange()>0)
				return true;
	
		if(flag==1)
			if(bottom.getHeal()>0 && bottom.getRange()>0)
				return true;
	
		return false;
	}
	
	public int getHeal() {
		if(flag==0)
			return top.getHeal();
	
		if(flag==1)
			return bottom.getHeal();
	
		return 0;
	}
	
	public boolean getMindControl() {
		if(flag==0)
			return top.getMindControl();
		
		if(flag==1)
			return bottom.getMindControl();
	
		return false;
	}
	
	public void showCard(Graphics g) {
		Setting setting = new Setting();
		g.drawImage(image, 10, setting.getGraphicsYTop()+70, 285, 425  , null);
		g.drawString("Press Space to Select this card. Card selection "+Integer.toString(index), 20, setting.getGraphicsYTop()+70+450);
	}
}
