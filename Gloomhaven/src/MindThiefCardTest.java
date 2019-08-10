import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.powermock.core.classloader.annotations.PrepareForTest;

import Gloomhaven.AbilityCards.PlayerAbilityCard;
import Gloomhaven.CardDataObject.CardDataObject;
import Gloomhaven.CardInterfaces.CardInterface;
import Gloomhaven.CardInterfaces.MindThief;
import Gloomhaven.Characters.Enemy;
import Gloomhaven.Characters.Player;
import Gloomhaven.Hex.Hex;
import Gloomhaven.Scenario.ScenarioData;
import Unsorted.InfusionTable;
import Unsorted.Setting;
import Unsorted.UtilitiesAB;


@PrepareForTest(Hex.class)
class MindThiefCardTest {

	@Test
	void CardDataTest() {
		CardInterface classCardData = new MindThief();
		CardDataObject card = null;
		
		//1
		card = classCardData.getTop(1);
		assertEquals(card.getData().getAttack(), 2);
		assertNotNull(card.getTrigger());
		assertEquals(card.getTrigger().getBonusData().getAttack(), 1);
		assertEquals(card.getData().getXpOnUse(), 1);
		
		//2
		card = classCardData.getTop(2);
		assertEquals(card.getInfuseElemental(), "Dark");
		assertNotNull(card.getEffects());
		assertEquals(card.getEffects().getLoot(), 1);
		assertEquals(card.getEffects().getFlag(), "Loot");
		assertEquals(card.getEffects().getRange(), 1);
	}

	//@PowerMock.mockStatic(Hex.class)
	@Test
	void CardTest() {
		CardInterface classCardData = new MindThief();
		Player player = new Player(1, "Mind Thief");
	
		Hex[][] board = new Hex[1][1];
		Hex[][] spy = Mockito.spy(board);
		
		//Hex[][] board = Mockito.mock(Hex.class);
		
		Enemy enemy = Mockito.spy(Enemy.class);
		InfusionTable elements = Mockito.spy(InfusionTable.class);
		ScenarioData data = Mockito.spy(ScenarioData.class);
		
		//1
		int id=1;
		PlayerAbilityCard card = new PlayerAbilityCard(player.getClassID(), id, 1);
		player.setAbilityCard(card);
		assertNotNull(player.getAbilityCardData("Top", 1));
		assertEquals(player.getAbilityCardData("Top", 1).getData().getAttack(), 2);
		//I need a hex board that has enemies next to each other or mock those methods
		
		UtilitiesAB.resolveAttack(enemy, player, card, board, true, elements, data, false);
		
	}

}
