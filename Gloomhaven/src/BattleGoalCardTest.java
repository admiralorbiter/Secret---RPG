import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import Gloomhaven.BattleGoals.BattleGoalCard;
import Gloomhaven.BattleGoals.BattleGoalCardUtilities;
import Gloomhaven.BattleGoals.BattleGoalSelection;
import Gloomhaven.Characters.Player;
import Unsorted.CharacterDataObject;
import Unsorted.Setting;
import Unsorted.StatsTracker;

class BattleGoalCardTest {
	BattleGoalSelection battleGoalLogic = new BattleGoalSelection(BattleGoalCardUtilities.loadFullDeck());
	
	@Test
	void battleGoalCardTest() {
		
	}
	
	@PrepareForTest(Player.class)
	@Test
	void battleGoalLogicTest() {
		Player player = Mockito.spy(new Player(0, Setting.playerClass));
		player.setStats(Mockito.mock(StatsTracker.class));
		player.setData(Mockito.mock(CharacterDataObject.class));
		
		assertNotNull(battleGoalLogic.getCard1());
		assertNotNull(battleGoalLogic.getCard2());

		BattleGoalCard card =null;
		
		//Testing Card 458
		card = loadBattleGoalCard(458, player);
		Mockito.when(player.getHandAndDiscardSize()).thenReturn(20);
		evaluateSuccess(player, card);
		
		//Testing Card 459
		card = loadBattleGoalCard(459, player);
		Mockito.when(player.getStats().getScenarioExperience()).thenReturn(1);
		evaluateSuccess(player, card);
		
		//Testing Card 460
		card = loadBattleGoalCard(460, player);
		Mockito.when(player.getStats().getScenarioExperience()).thenReturn(14);
		evaluateSuccess(player, card);
		
		//Testing Card 461
		card = loadBattleGoalCard(461, player);
		Mockito.when(player.getHandAndDiscardSize()).thenReturn(3);
		evaluateSuccess(player, card);
		
		//Testing Card 462
		card = loadBattleGoalCard(462, player);
		Mockito.when(player.getCharacterData().getHealth()).thenReturn(1);
		evaluateSuccess(player, card);
		
		//Testing Card 463
		card = loadBattleGoalCard(463, player);
		Mockito.when(player.getCharacterData().getMaxHealth()).thenReturn(10);
		Mockito.when(player.getCharacterData().getHealth()).thenReturn(10);
		evaluateSuccess(player, card);
		
		//Testing Card 464
		card = loadBattleGoalCard(464, player);
		//Mockito.when(player.getStats().trap).thenReturn(20);
		//evaluateSuccess(player, card);
		
		//Testing Card 465
		card = loadBattleGoalCard(465, player);
		Mockito.when(player.getStats().getTreasureLootTotal()).thenReturn(2);
		evaluateSuccess(player, card);
		
		//Testing Card 466
		card = loadBattleGoalCard(466, player);
		//Mockito.when(player.getStats().getScenarioExperience()).thenReturn(1);
		//evaluateSuccess(player, card);
		
		//Testing Card 467
		card = loadBattleGoalCard(467, player);
		//Mockito.when(player.getHandAndDiscardSize()).thenReturn(20);
		//evaluateSuccess(player, card);
		
		//Testing Card 468
		card = loadBattleGoalCard(468, player);
		Mockito.when(player.getStats().getGoldLootTotal()).thenReturn(10);
		evaluateSuccess(player, card);
		
		//Testing Card 469
		card = loadBattleGoalCard(469, player);
		Mockito.when(player.getStats().getGoldLootTotal()).thenReturn(0);
		evaluateSuccess(player, card);

		//Testing Card 470
		card = loadBattleGoalCard(470, player);
		Mockito.when(player.getStats().getNumberOfScenarioKills()).thenReturn(1);
		evaluateSuccess(player, card);
		
		//Testing Card 471
		card = loadBattleGoalCard(471, player);
		Mockito.when(player.getStats().getNumberOfScenarioKills()).thenReturn(7);
		evaluateSuccess(player, card);
		
		//Testing Card 472
		card = loadBattleGoalCard(472, player);
		Mockito.when(player.getStats().getScenarioEliteEnemiesKilled()).thenReturn(7);
		evaluateSuccess(player, card);
		
		//Testing Card 473
		card = loadBattleGoalCard(473, player);
		Mockito.when(player.getStats().getScenarioItemsUsed()).thenReturn(200);
		evaluateSuccess(player, card);
		
		//Testing Card 474
		card = loadBattleGoalCard(474, player);
		//Mockito.when(player.getStats().getScenarioExperience()).thenReturn(1);
		//evaluateSuccess(player, card);
		
		//Testing Card 475
		card = loadBattleGoalCard(475, player);
		Mockito.when(player.getStats().overkillComplete()).thenReturn(true);
		evaluateSuccess(player, card);
		
		//Testing Card 476
		card = loadBattleGoalCard(476, player);
		Mockito.when(player.getStats().getScenarioItemsUsed()).thenReturn(0);
		evaluateSuccess(player, card);
		
		//Testing Card 477
		card = loadBattleGoalCard(477, player);
		Mockito.when(player.getStats().firstBlood()).thenReturn(true);
		evaluateSuccess(player, card);
		
		//Testing Card 478
		card = loadBattleGoalCard(478, player);
		//Mockito.when(player.getStats().getGoldLootTotal()).thenReturn(10);
		//evaluateSuccess(player, card);
		
		//Testing Card 479
		card = loadBattleGoalCard(479, player);
		Mockito.when(player.getStats().singleBlow()).thenReturn(true);
		evaluateSuccess(player, card);
		
		//Testing Card 480
		card = loadBattleGoalCard(480, player);
		Mockito.when(player.getStats().getScenarioShortRests()).thenReturn(0);
		evaluateSuccess(player, card);
		
		//Testing Card 481
		card = loadBattleGoalCard(481, player);
		Mockito.when(player.getStats().getScenarioLongRests()).thenReturn(0);
		evaluateSuccess(player, card);
	}
	
	private BattleGoalCard loadBattleGoalCard(int index, Player player) {
		BattleGoalCard card=BattleGoalCardUtilities.load(index);
		Mockito.when(player.getBattleGoalCard()).thenReturn(card);
		return card;
	}
	
	private void evaluateSuccess(Player player, BattleGoalCard card) {
		player.setBattleGoalTotal(0);
		BattleGoalCardUtilities.evaluateBattleGoals(player);
		assertEquals(card.getReward(), player.getBattleGoalTotal());
	}

}
