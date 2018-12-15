package Gloomhaven;

public final class UsePlayerAbilityCard {
	public static CardDataObject getCardData(PlayerAbilityCard card) {

		String flag=card.getFlag();
		
		if(flag=="Top")
			return card.getTopData();
		
		if(flag=="Botom")
			return card.getBottomData();
		
		return null;
	}
	
	public static int getMove(PlayerAbilityCard card) {
		String flag=card.getFlag();
		
		if(flag=="Top")
			return card.getTopData().getMove();
		
		if(flag=="Botom")
			return card.getBottomData().getMove();
		
		if(flag=="AltTop")
			return 0;
		
		if(flag=="AltBottom")
			return card.getAltBottom();
		
		return 0;
	}
	
	public static int getAttack(PlayerAbilityCard card) {
		String flag=card.getFlag();
		if(flag=="Top")
			return card.getTopData().getAttack();
		
		if(flag=="Botom")
			return card.getBottomData().getAttack();
		
		if(flag=="AltTop")
			return card.getAltTop();
		
		if(flag=="AltBottom")
			return 0;
		
		return 0;
	}
	
	public static int getRange(PlayerAbilityCard card) {
		String flag=card.getFlag();
		if(flag=="Top")
			return card.getTopData().getRange();
		
		if(flag=="Botom")
			return card.getBottomData().getRange();
		
		return 0;
	}
	
	public static boolean hasAugment(PlayerAbilityCard card) {
		
		if(card==null)
			return false;
		
		String flag=card.getFlag();
		
		if(flag=="Top")
			return card.getTopData().hasAugment();
		
		if(flag=="Botom")
			return card.getBottomData().hasAugment();
		
		return false;
	}
	
	public static boolean hasFlying(PlayerAbilityCard card) {
		String flag=card.getFlag();
		if(flag=="Top")
			return card.getTopData().hasFlying();
		
		if(flag=="Botom")
			return card.getBottomData().hasFlying();
		
		return false;
	}
	
	public static boolean hasJump(PlayerAbilityCard card) {
		String flag=card.getFlag();
		if(flag=="Top")
			return card.getTopData().hasJump();
		
		if(flag=="Botom")
			return card.getBottomData().hasJump();
		
		return false;
	}
	
	public static int getPierce(PlayerAbilityCard card) {
		String flag=card.getFlag();
		if(flag=="Top")
			return card.getTopData().getPierce();
		
		if(flag=="Botom")
			return card.getBottomData().getPierce();
		
		return 0;
	}
	
	public static boolean hasTargetHeal(PlayerAbilityCard card) {
		String flag=card.getFlag();
		if(flag=="Top")
			if(card.getTopData().getHeal()>0 && card.getTopData().getRange()>0)
				return true;
		
		if(flag=="Botom")
			if(card.getBottomData().getHeal()>0 && card.getBottomData().getRange()>0)
				return true;
		
		return false;
	}
	
	public static int getHeal(PlayerAbilityCard card) {
		String flag=card.getFlag();
		if(flag=="Top")
			return card.getTopData().getHeal();
		
		if(flag=="Botom")
			return card.getBottomData().getHeal();
		
		return 0;
	}
	
	public static boolean hasMindControl(PlayerAbilityCard card) {
		String flag=card.getFlag();
		if(flag=="Top")
			return card.getTopData().hasMindControl();
		
		if(flag=="Botom")
			return card.getBottomData().hasMindControl();
		
		return false;
	}
}
