package com.openrsc.server.plugins.quests.members;

import com.openrsc.server.Constants;
import com.openrsc.server.model.container.Item;
import com.openrsc.server.model.entity.GameObject;
import com.openrsc.server.model.entity.npc.Npc;
import com.openrsc.server.model.entity.player.Player;
import com.openrsc.server.plugins.QuestInterface;
import com.openrsc.server.plugins.listeners.action.InvUseOnNpcListener;
import com.openrsc.server.plugins.listeners.action.InvUseOnWallObjectListener;
import com.openrsc.server.plugins.listeners.action.TalkToNpcListener;
import com.openrsc.server.plugins.listeners.action.WallObjectActionListener;
import com.openrsc.server.plugins.listeners.executive.InvUseOnNpcExecutiveListener;
import com.openrsc.server.plugins.listeners.executive.InvUseOnWallObjectExecutiveListener;
import com.openrsc.server.plugins.listeners.executive.TalkToNpcExecutiveListener;
import com.openrsc.server.plugins.listeners.executive.WallObjectActionExecutiveListener;

import static com.openrsc.server.plugins.Functions.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class ScorpionCatcher implements QuestInterface,TalkToNpcListener,
TalkToNpcExecutiveListener, InvUseOnNpcListener,
InvUseOnNpcExecutiveListener, InvUseOnWallObjectListener,
InvUseOnWallObjectExecutiveListener, WallObjectActionListener,
WallObjectActionExecutiveListener {

	public static int THORMAC = 300;
	public static int SEER = 301;
	public static int VELRAK_THE_EXPLORER = 272;

	public static final int NONE = 678;
	public static final int ONE = 679; // (Taverly)
	public static final int TWO = 686; // (Barbarian)
	public static final int THREE = 687; // (Monastery)
	public static final int ONE_AND_TWO = 680;
	public static final int ONE_AND_THREE = 688;
	public static final int TWO_AND_THREE = 689;
	public static final int ONE_TWO_AND_THREE = 681;

	@Override
	public int getQuestId() {
		return Constants.Quests.SCORPION_CATCHER;
	}

	@Override
	public String getQuestName() {
		return "Scorpion catcher (members)";
	}

	@Override
	public boolean isMembers() {
		return true;
	}

	@Override
	public void handleReward(Player p) {
		p.message("Well done you have completed the scorpion catcher quest");
		p.incQuestPoints(1);
		p.message("@gre@You haved gained 1 quest point!");
		p.incQuestExp(2, p.getSkills().getMaxStat(2) * 500 + 1500);

	}

	class SEER_NPC {
		private static final int LOCATE_SCORPIONS = 0;
		private static final int PRIMARY_DIALOGUE = 1;
	}

	@Override
	public boolean blockTalkToNpc(Player p, Npc n) {
		if (n.getID() == THORMAC) {
			return true;
		}
		if (n.getID() == SEER) {
			return true;
		}
		if (n.getID() == VELRAK_THE_EXPLORER) {
			return true;
		}
		return false;
	}

	private void seerDialogue(Player p, Npc n, int cID) {
		if (cID == -1) {
			switch (p.getQuestStage(this)) {
			case 0:
			case -1:
				seerDialogue(p, n, SEER_NPC.PRIMARY_DIALOGUE);
				break;
			case 1:
				npcTalk(p, n, "Many greetings");
				int first = showMenu(p, n, 
						"I need to locate some scorpions",
						"Your friend Thormac sent me to speak to you",
						"I seek knowledge and power");
				if (first == 0) {
					seerDialogue(p, n, SEER_NPC.LOCATE_SCORPIONS);
				} else if (first == 1) {
					npcTalk(p, n, "What does the old fellow want");
					playerTalk(p, n, "He's lost his valuable lesser kharid scorpions");
					seerDialogue(p, n, SEER_NPC.LOCATE_SCORPIONS);
				} else if (first == 2) {
					npcTalk(p, n, "Knowledge comes from experience, power comes from battleaxes");
				}
				break;
			case 2:

				// Still needs first scorpion
				if (!hasItem(p, ONE) &&
						!hasItem(p, ONE_AND_TWO) &&
						!hasItem(p, ONE_TWO_AND_THREE) &&
						!hasItem(p, ONE_AND_THREE)) {
					if (!hasItem(p, NONE)) {
						playerTalk(p, n, "I need to locate some scorpions");
						seerDialogue(p, n, SEER_NPC.LOCATE_SCORPIONS);
					}
					else {
						npcTalk(p, n, "Many greetings");
						playerTalk(p, n, "Where did you say that scorpion was again?");
						npcTalk(p, n, "Let me look into my looking glass");
						message(p, "The seer produces a small mirror",
							"The seer gazes into the mirror",
							"The seer smoothes his hair with his hand");
						npcTalk(p, n,
							"I can see a scorpion that you seek",
							"It would appear to be near some  nasty looking spiders",
							"I can see two coffins there as well",
							"The scorpion seems to be going through some crack in the wall",
							"He's gone into some sort of secret room",
							"Well see if you can find that scorpion then",
							"And I'll try and get you some information on the others");
					}
				}

				// Still needs second scorpion
				else if (!hasItem(p, TWO) &&
						!hasItem(p, ONE_AND_TWO) &&
						!hasItem(p, TWO_AND_THREE) &&
						!hasItem(p, ONE_TWO_AND_THREE)) {
					playerTalk(p, n, "Hi I have retrieved the scorpion from near the spiders");
					npcTalk(p, n, "Well I've checked my looking glass",
						"There seems to be a kharid scorpion in a village full of  axe wielding warriors",
						"One of the warriors there, dressed mainly in black has picked it up",
						"That's all I can tell you about that scorpion");
				}

				// Still needs third scorpion
				else if (!hasItem(p, THREE) &&
						!hasItem(p, ONE_AND_THREE) &&
						!hasItem(p, TWO_AND_THREE) &&
						!hasItem(p, ONE_TWO_AND_THREE)) {
					npcTalk(p, n, "Many greetings");
					playerTalk(p, n, "I have retrieved a second scorpion");
					npcTalk(p, n,
						"That's lucky because I've got some information on the last scorpion for you",
						"It seems to be in some sort of upstairs room",
						"There seems to be some sort of brown clothing lying on the floor");
				}
				else seerDialogue(p, n, SEER_NPC.PRIMARY_DIALOGUE);
				break;
			}

		}
		switch (cID) {
			case SEER_NPC.LOCATE_SCORPIONS:
				npcTalk(p, n, "Well you have come to the right place",
					"I am a master of animal detection",
					"Do you need to locate any particular scorpion",
					"Scorpions are a creature somewhat in abundance");
				playerTalk(p, n, "I'm looking for some lesser kharid scorpions",
					"They belong to Thormac the sorceror");
				npcTalk(p, n, "Let me look into my looking glass");
				message(p, "The seer produces a small mirror",
					"The seer gazes into the mirror",
					"The seer smoothes his hair with his hand");
				npcTalk(p, n,
					"I can see a scorpion that you seek",
					"It would appear to be near some  nasty looking spiders",
					"I can see two coffins there as well",
					"The scorpion seems to be going through some crack in the wall",
					"He's gone into some sort of secret room",
					"Well see if you can find that scorpion then",
					"And I'll try and get you some information on the others");
				if(p.getQuestStage(this) == 1) {
					p.updateQuestStage(getQuestId(), 2);
				}
				break;

			case SEER_NPC.PRIMARY_DIALOGUE:
				npcTalk(p,n, "Many greetings");
				int menu = showMenu(p, n, "Many greetings", "I seek knowledge and power");
				if(menu == 1) {
					npcTalk(p, n, "Knowledge comes from experience, power comes from battleaxes");
				}
				break;
		}
	}

	public void velrakDialogue(Player p, Npc n, int choice) {
		if (choice == -1) {
			if (hasItem(p, 596)) {
				playerTalk(p, n, "Are you still here?");
				npcTalk(p, n, "Yes, I'm still plucking up courage",
					"To run out past those black knights");
				return;
			}
			npcTalk(p, n, "Thankyou for rescuing me",
				"It isn't comfy in this cell");
			choice = showMenu(p, n,
				"So do you know anywhere good to explore?",
				"Do I get a reward?");

			if (choice > -1)
				velrakDialogue(p, n, choice);

		} else if (choice == 0) {
			npcTalk(p, n, "Well this dungeon was quite good to explore",
				"Till I got captured",
				"I got given a key to an inner part of this dungeon",
				"By a mysterious cloaked stranger",
				"It's rather to tough for me to get that far though",
				"I keep getting captured",
				"Would you like to give it a go");
			choice = showMenu(p, n, "Yes please",
				"No it's too dangerous for me");
			if (choice == 0) {
				message(p,
					"Velrak reaches inside his boot and passes you a key");
				addItem(p, 596, 1);
			}

		} else if (choice == 1) {
			npcTalk(p, n,
				"Well not really the black knights took all my stuff before throwing me in here");
		}
	}

	public void thormacDialogue(Player p, Npc n, int choice) {
		if (choice == -1) {
			switch (p.getQuestStage(this)) {
				case 0:
					npcTalk(p, n, "Hello I am Thormac the sorceror",
							"I don't suppose you could be of assistance to me?");
					int first = showMenu(p, n,
							"What do you need assistance with?",
							"I'm a little busy");
					if (first == 0) {
						thormacDialogue(p, n, 0);
					}
					break;
				case 1:
				case 2:
					npcTalk(p, n, "How goes your quest?");
					if(!hasItem(p, NONE) && !hasItem(p, ONE_TWO_AND_THREE)) { // No empty cage, no full cage
						int menu = showMenu(p,n,
							"I've lost my cage",
							"I've not caught all the scorpions yet");
						if(menu == 0) {
							npcTalk(p,n, "Ok here is another cage",
									"You're almost as bad at loosing things as me");
							addItem(p, NONE, 1);
						} else if(menu == 1) {
							npcTalk(p, n, "Well remember, go speak to the seers north of here if you need any help");
						}
					}
					else if (hasItem(p, ONE_TWO_AND_THREE)) { // full cage
						playerTalk(p, n, "I have retrieved all your scorpions");
						npcTalk(p, n, "aha my little scorpions home at last");
						removeItem(p, ONE_TWO_AND_THREE, 1);
						p.sendQuestComplete(Constants.Quests.SCORPION_CATCHER);
					} else {
						playerTalk(p, n, "I've not caught all the scorpions yet");
						npcTalk(p, n, "Well remember, go speak to the seers north of here if you need any help");
					}
					break;
				case -1:
					npcTalk(p, n, "Thankyou for rescuing my scorpions");
					int four = showMenu(p, n, "That's ok",
							"You said you'd enchant my battlestaff for me");
					if (four == 1) {
						npcTalk(p, n,
								"Yes it'll cost you 40000 coins for the materials needed mind you",
								"Which sort of staff did you want enchanting?");
						int five = showMenu(p, n,
								"battlestaff of fire", "battlestaff of water",
								"battlestaff of air", "battlestaff of earth",
						"I won't bother yet actually");
						if (five == 0) {
							if(!hasItem(p, 615)) {
								playerTalk(p, n, "I don't have a battlestaff of fire yet though");
								return;
							}
							if(!hasItem(p, 10, 40000)) {
								playerTalk(p, n, "I'll just get the money for you");
								return;
							}
							if(removeItem(p, new Item(10, 40000), new Item(615,1)))  {
								addItem(p, 682, 1);
								p.message("Thormac enchants your staff");
							}
						} else if (five == 1) {
							if(!hasItem(p, 616)) {
								playerTalk(p, n, "I don't have a battlestaff of water yet though");
								return;
							}
							if(!hasItem(p, 10, 40000)) {
								playerTalk(p, n, "I'll just get the money for you");
								return;
							}
							if(removeItem(p, new Item(10, 40000), new Item(616,1)))  {
								addItem(p, 683, 1);
								p.message("Thormac enchants your staff");
							}
						} else if (five == 2) {
							if(!hasItem(p, 617)) {
								playerTalk(p, n, "I don't have a battlestaff of air yet though");
								return;
							}
							if(!hasItem(p, 10, 40000)) {
								playerTalk(p, n, "I'll just get the money for you");
								return;
							}
							if(removeItem(p, new Item(10, 40000), new Item(617,1)))  {
								addItem(p, 684, 1);
								p.message("Thormac enchants your staff");
							}
						} else if (five == 3) {
							if(!hasItem(p, 618)) {
								playerTalk(p, n, "I don't have a battlestaff of earth yet though");
								return;
							}
							if(!hasItem(p, 10, 40000)) {
								playerTalk(p, n, "I'll just get the money for you");
								return;
							}
							if(removeItem(p, new Item(10, 40000), new Item(618,1)))  {
								addItem(p, 685, 1);
								p.message("Thormac enchants your staff");
							}
						}
					}
					break;
			}
		}

		// What do you need assistance with?
		else if (choice == 0) {
			npcTalk(p, n,
				"I've lost my pet scorpions",
				"They're lesser kharid scorpions, a very rare breed",
				"I left there cage door open",
				"now I don't know where they have gone",
				"There's 3 of them and they're quick little beasties",
				"They're all over runescape");
			choice = showMenu(p, n,
				"So how would I go about catching them then?",
				"What's in it for me?", "I'm not interested then");
			if (choice == 0) thormacDialogue(p, n, 1);
			else if (choice == 1) thormacDialogue(p, n, 2);
			else if (choice == 2) {
				npcTalk(p, n, "Blast, I suppose I will have to have find someone else then");
			}
		}

		// So how would I go about catching them then?
		else if (choice == 1) {
			npcTalk(p, n, "Well I have a scorpion cage here",
				"Which you can use to catch them in");
			addItem(p, NONE, 1);
			message(p, "Thormac gives you a cage");
			npcTalk(p, n,
				"If you go up to the village of seers to the north of here",
				"One of them will be able to tell you where the scorpions are now");

			p.updateQuestStage(getQuestId(), 1); // STARTED QUEST

			choice = showMenu(p, n,
				"What's in it for me?",
				"Ok I will do it then");

			if (choice == 0) thormacDialogue(p, n, 2);

		}

		// What's in it for me?
		else if (choice == 2) {
			npcTalk(p, n,
				"Well I suppose I can aid you with my skills as a staff sorcerer",
				"Most the battlestaffs around here are pretty puny",
				"I can beef them up for you a bit");

			choice = showMenu(p, n,
				"So how would I go about catching them then?",
				"Ok I will do it then");

			if (choice == 0) thormacDialogue(p, n, 1);
		}
	}

	@Override
	public void onTalkToNpc(Player p, Npc n) {
		if (n.getID() == SEER) {
			seerDialogue(p, n, -1);
		}
		if (n.getID() == VELRAK_THE_EXPLORER) {
			velrakDialogue(p, n, -1);
		}
		if (n.getID() == THORMAC) {
			thormacDialogue(p, n, -1);
		}
	}

	@Override
	public boolean blockInvUseOnNpc(Player player, Npc n, Item i) {
		int cageId = i.getID();
		if (player.getInventory().countId(cageId) <= 0) return false;

		if (n.getID() == 302 && // First Scorpion (Taverly)
			cageId != ONE && cageId != ONE_AND_TWO &&
			cageId != ONE_AND_THREE && cageId != ONE_TWO_AND_THREE
		) {
			return true;
		}
		else if (n.getID() == 303 && // Second Scorpion (Barbarian)
				cageId != TWO && cageId != ONE_AND_TWO &&
				cageId != TWO_AND_THREE && cageId != ONE_TWO_AND_THREE
		) {
			return true;
		}
		else if (n.getID() == 304 && // Third Scorpion (Monastery)
				cageId != THREE && cageId != ONE_AND_THREE &&
				cageId != TWO_AND_THREE && cageId != ONE_TWO_AND_THREE
		) {
			return true;
		}
		return false;
	}

	@Override
	public void onInvUseOnNpc(Player p, Npc n, Item i) {

		if (p.getQuestStage(this) == 2) {

			List<Integer> cages = new ArrayList<Integer>(Arrays.asList(
				NONE, ONE, TWO, THREE, ONE_AND_TWO, ONE_AND_THREE, TWO_AND_THREE
			));

			int itemId = i.getID();

			if (!cages.contains(itemId)) {
				p.message("Nothing interesting happens");
				return;
			}

			int toRemove = -1;
			int toAdd = -1;

			// Taverly scorpion
			if (n.getID() == 302) {
				switch (itemId) {
					case NONE:
						toRemove = itemId;
						toAdd = ONE;
						break;
					case TWO:
						toRemove = itemId;
						toAdd = ONE_AND_TWO;
						break;
					case THREE:
						toRemove = itemId;
						toAdd = ONE_AND_THREE;
						break;
					case TWO_AND_THREE:
						toRemove = itemId;
						toAdd = ONE_TWO_AND_THREE;
						break;
				}
			}

			// Barbarian scorpion
			else if (n.getID() == 303) {
				switch (itemId) {
					case NONE:
						toRemove = itemId;
						toAdd = TWO;
						break;
					case ONE:
						toRemove = itemId;
						toAdd = ONE_AND_TWO;
						break;
					case THREE:
						toRemove = itemId;
						toAdd = TWO_AND_THREE;
						break;
					case ONE_AND_THREE:
						toRemove = itemId;
						toAdd = ONE_TWO_AND_THREE;
						break;
				}
			}

			// Monastery scorpion
			else if (n.getID() == 304) {
				switch (itemId) {
					case NONE:
						toRemove = itemId;
						toAdd = THREE;
						break;
					case ONE:
						toRemove = itemId;
						toAdd = ONE_AND_THREE;
						break;
					case TWO:
						toRemove = itemId;
						toAdd = TWO_AND_THREE;
						break;
					case ONE_AND_TWO:
						toRemove = itemId;
						toAdd = ONE_TWO_AND_THREE;
						break;
				}
			}

			p.message("You catch a scorpion");

			if (toRemove > -1) removeItem(p, toRemove, 1);
			if (toAdd > -1) addItem(p, toAdd, 1);
			temporaryRemoveNpc(n);
		}
		else
			p.message("Talk to Seer before you attempt catching this scorpion");
	}

	@Override
	public boolean blockInvUseOnWallObject(GameObject obj, Item item,
			Player player) {
		if (obj.getID() == 83 && obj.getY() == 3428 && item.getID() == 595) {
			return true;
		}
		if (obj.getID() == 83 && obj.getY() == 3425 && item.getID() == 595) {
			return true;
		}
		if (obj.getID() == 84 && obj.getY() == 3353 && item.getID() == 596) {
			return true;
		}
		return false;
	}

	@Override
	public void onInvUseOnWallObject(GameObject obj, Item item, Player player) {
		/*
		 * Velrak cell door
		 */
		if (obj.getID() == 83 && obj.getY() == 3428 && item.getID() == 595) {
			showBubble(player, item);
			doDoor(obj, player);
		}
		/*
		 * Below door infront of Velrak cell has nothing todo with quest or
		 * anything important at all - replicated it anyway.
		 */
		if (obj.getID() == 83 && obj.getY() == 3425 && item.getID() == 595) {
			showBubble(player, item);
			doDoor(obj, player);
		}
		/*
		 * Dusty key door into blue dragons lair in Taverly dungeon
		 */
		if (obj.getID() == 84 && obj.getY() == 3353 && item.getID() == 596) {
			showBubble(player, item);
			doDoor(obj, player);
		}
	}

	@Override
	public boolean blockWallObjectAction(GameObject obj, Integer click,
			Player player) {
		if (obj.getID() == 87 && obj.getY() == 3353) {
			return true;
		}
		return false;
	}

	@Override
	public void onWallObjectAction(GameObject obj, Integer click, Player p) {
		if (obj.getID() == 87 && obj.getY() == 3353 && p.getQuestStage(this) == 2) {
			doDoor(obj, p);
			p.message("You just went through a secret door");
		}
	}
}
