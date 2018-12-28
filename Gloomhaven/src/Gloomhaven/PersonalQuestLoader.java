package Gloomhaven;

public final class PersonalQuestLoader {
	public static PersonalQuestCard load(int id) {
		
		PersonalQuestCard card = new PersonalQuestCard("Error", "Personal Quest Error", "Nothing personel about this error");
		
		switch(id) {
			case 510:
				card.setTitle("Seeker of Xorn");
				card.setText("Placeholder.");
				card.setCriteria("Complete three Crypt scenarios. Then unlock Noxious Cellar (Scene 52) and follow it to a conclusion.");
				return card;
			case 511:
				card.setTitle("Merchant Class");
				card.setText("Placeholder.");
				card.setCriteria("Own two head items, two body items, two boot items, three one/two hand items and four small items.");
				return card;
			case 512:
				card.setTitle("Greed");
				card.setText("Placeholder.");
				card.setCriteria("Have 200 gold in your possession.");
				return card;
			case 513:
				card.setTitle("Finding the Cure");
				card.setText("Placeholder.");
				card.setCriteria("Kill eight Forest Imps. Then unlock Forgotten Grove (Scene 59) and follow it to a conclusion.");
				return card;
			case 514:
				card.setTitle("A Study of Anatomy");
				card.setText("Placeholder.");
				card.setCriteria("Experience your party members becoming exhausted fiften times.");
				return card;
			case 515:
				card.setTitle("Law Bringer");
				card.setText("Placeholder.");
				card.setCriteria("Kill twenty Bandits or Cultists");
				return card;
			case 516:
				card.setTitle("Poinds of Flesh");
				card.setText("Placeholder.");
				card.setCriteria("Kill fifteen vermlings");
				return card;
			case 517:
				card.setTitle("Trophy Hunt");
				card.setText("Placeholder.");
				card.setCriteria("Kill twenty different types of monsters");
				return card;
			case 518:
				card.setTitle("Eternal Wanderer");
				card.setText("Placeholder.");
				card.setCriteria("Complete fifteen different scenarios.");
				return card;
			case 519:
				card.setTitle("Battle Legend");
				card.setText("Placeholder.");
				card.setCriteria("Earn fifteen battle goal checkmarks.");
				return card;
			case 520:
				card.setTitle("Implement of Light");
				card.setText("Placeholder.");
				card.setCriteria("Find the skullbane Axe in the Necromancer's Sanctum and then use it to kill seven Living Bones, Living Corpses, or Living Spirits.");
				return card;
			case 521:
				card.setTitle("Take Back the Trees");
				card.setText("Placeholder.");
				card.setCriteria("Complete three scenarios in the Dagger Forest. Then unlock Foggy Thicket (Scene 55) and follow it to a conclusion.");
				return card;
			case 522:
				card.setTitle("The Thin Places");
				card.setText("Placeholder.");
				card.setCriteria("Complete six side scenarios (scene >51).");
				return card;
			case 523:
				card.setTitle("Aberrant Slayer");
				card.setText("Placeholder.");
				card.setCriteria("Kill one Flame Demon, one Frost Demon, one Wind Demon, one Earth Demon, one Night Demon, and one Sun Demon");
				return card;
			case 524:
				card.setTitle("Fearless Stand");
				card.setText("Placeholder.");
				card.setCriteria("Kill twenty elite monsters.");
				return card;
			case 525:
				card.setTitle("Piety in All Things");
				card.setText("Placeholder.");
				card.setCriteria("Donate 120 gold to the Sanctuary of the Great Oak");
				return card;
			case 526:
				card.setTitle("Vengeance");
				card.setText("Placeholder.");
				card.setCriteria("Complete four scenarios in Gloomhaven. then unlock Investigation (Scene 57) and follow it to a conclusion.");
				return card;
			case 527:
				card.setTitle("Zealot of the Blood God");
				card.setText("Placeholder.");
				card.setCriteria("Become exhausted twelve times.");
				return card;
			case 528:
				card.setTitle("Goliath Toppler");
				card.setText("Placeholder.");
				card.setCriteria("Complete four boss scenarios.");
				return card;
			case 529:
				card.setTitle("The Fall of Man");
				card.setText("Placeholder.");
				card.setCriteria("Complete two scenarios in the Lingering Swamp. Then unlock Fading Lighthouse (Scene 61) and follow it a conclusion.");
				return card;
			case 530:
				card.setTitle("Augmented Abilities");
				card.setText("Placeholder.");
				card.setCriteria("Purchase four enchancements (requires The Power of Enhancement Globla).");
				return card;
			case 531:
				card.setTitle("Etemental Samples");
				card.setText("Placeholder.");
				card.setCriteria("Complete a scenario in each of the following areas: Gloomhaven, Dagger Forest, Lingering Swamp, Watching Mountains, Copperneck Mountains, and Misty Sea");
				return card;
			case 532:
				card.setTitle("A Helping Hand");
				card.setText("Placeholder.");
				card.setCriteria("Experience two other characters achieving their person questions.");
				return card;
			case 533:
				card.setTitle("The Perfect Poison");
				card.setText("Placeholder.");
				card.setCriteria("Kill three oozes, three lukers, and three spitting drakes.");
				return card;
				
		}
		
		return card;
	}
}
