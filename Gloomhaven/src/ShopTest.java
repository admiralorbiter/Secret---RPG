import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;

import Gloomhaven.Shop;
import Gloomhaven.Characters.Player;
import Unsorted.CharacterDataObject;
import Unsorted.Item;
import Unsorted.Setting;

class ShopTest {

	@Test
	void testBuyItem() throws Exception {
		Shop shop = new Shop(1);
		assertNotNull(shop);
		Player player = Mockito.spy(new Player(0, Setting.playerClass));
		Item item = Mockito.mock(Item.class);
		player.setData(Mockito.mock(CharacterDataObject.class));
		
		assertNotNull(player.getCharacterData());
		
		Mockito.when(player.getCharacterData().getGold()).thenReturn(100);
		Mockito.when(item.getGold()).thenReturn(10);
		
		Boolean outcome = Whitebox.invokeMethod(shop, "buyItem", player, item);
		assertTrue(outcome);
		
		Mockito.when(player.getCharacterData().getGold()).thenReturn(10);
		Mockito.when(item.getGold()).thenReturn(100);
		
		Boolean outcomeTwo = Whitebox.invokeMethod(shop, "buyItem", player, item);
		assertFalse(outcomeTwo);
		
		
	}

}
