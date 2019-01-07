package Gloomhaven.AbilityCards;

import Gloomhaven.CardDataObject.CardDataObject;

public final class UsePlayerAbilityCard {
	public static CardDataObject getCardData(PlayerAbilityCard card) {

		String flag=card.getFlag();
		
		if(flag=="Top")
			return card.getTopData();
		
		if(flag=="Bottom")
			return card.getBottomData();
		/*
		if(flag=="AltTop")
			return null;
		
		if(flag=="AltBottom")
			return null;
		*/
		return new CardDataObject();
	}
	
	public static int getMove(PlayerAbilityCard card) {
		String flag=card.getFlag();
		
		if(flag=="Top")
			return card.getTopData().getData().getMove();
		
		if(flag=="Bottom")
			return card.getBottomData().getData().getMove();
		
		if(flag=="AltTop")
			return 0;
		
		if(flag=="AltBottom")
			return card.getAltBottom();
		
		return 0;
	}
	
	public static int getAttack(PlayerAbilityCard card) {
		String flag=card.getFlag();
		if(flag=="Top")
			return card.getTopData().getData().getAttack();
		
		if(flag=="Bottom")
			return card.getBottomData().getData().getAttack();
		
		if(flag=="AltTop")
			return card.getAltTop();
		
		if(flag=="AltBottom")
			return 0;
		
		return 0;
	}
	
	public static int getRange(PlayerAbilityCard card) {
		String flag=card.getFlag();
		if(flag=="Top")
			return card.getTopData().getData().getRange();
		
		if(flag=="Bottom")
			return card.getBottomData().getData().getRange();
		
		return 0;
	}
	
	public static boolean hasAugment(CardDataObject augment) {
		
		if(augment==null)
			return false;
		
		return augment.hasAugment();

	}
	
	public static boolean hasFlying(PlayerAbilityCard card) {
		String flag=card.getFlag();
		if(flag=="Top")
			return card.getTopData().getData().getFlyFlag();
		
		if(flag=="Bottom")
			return card.getBottomData().getData().getFlyFlag();
		
		return false;
	}
	
	public static boolean hasJump(PlayerAbilityCard card) {
		String flag=card.getFlag();
		if(flag=="Top")
			return card.getTopData().getData().getJumpFlag();
		
		if(flag=="Bottom")
			return card.getBottomData().getData().getJumpFlag();
		
		return false;
	}
	
	public static int getPierce(PlayerAbilityCard card) {
		String flag=card.getFlag();
		
		if(flag=="Top")
			if(card.getTopData().getEffects()!=null)
				return card.getTopData().getEffects().getPierce();
		
		if(flag=="Bottom")
			if(card.getBottomData().getEffects()!=null)
				return card.getBottomData().getEffects().getPierce();
		
		return 0;
	}
	
	public static boolean hasTargetHeal(PlayerAbilityCard card) {
		String flag=card.getFlag();
		
		if(flag=="Top")
			if(card.getTopData().getEffects()!=null)
				if(card.getTopData().getEffects().getHeal()>0 && card.getTopData().getEffects().getRange()>0)
					return true;
		
		if(flag=="Bottom")
			if(card.getBottomData().getEffects()!=null)
				if(card.getBottomData().getEffects().getHeal()>0 && card.getBottomData().getEffects().getRange()>0)
					return true;
		
		return false;
	}
	
	public static int getHeal(PlayerAbilityCard card) {
		String flag=card.getFlag();
		
		if(flag=="Top")
			if(card.getTopData().getEffects()!=null)
				return card.getTopData().getEffects().getHeal();
		
		if(flag=="Bottom")
			if(card.getBottomData().getEffects()!=null)
				return card.getBottomData().getEffects().getHeal();
		
		return 0;
	}
	
	public static boolean hasMindControl(PlayerAbilityCard card) {
		String flag=card.getFlag();
		
		if(flag=="Top")
			return card.getTopData().getMindControl();
		
		if(flag=="Bottom")
			return card.getBottomData().getMindControl();
		
		return false;
	}
}
