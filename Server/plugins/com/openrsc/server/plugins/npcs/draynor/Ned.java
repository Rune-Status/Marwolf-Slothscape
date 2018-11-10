package com.openrsc.server.plugins.npcs.draynor;

import com.openrsc.server.Constants;
import com.openrsc.server.Constants.Quests;
import com.openrsc.server.model.container.Item;
import com.openrsc.server.model.entity.npc.Npc;
import com.openrsc.server.model.entity.player.Player;
import com.openrsc.server.plugins.listeners.action.TalkToNpcListener;
import com.openrsc.server.plugins.listeners.executive.TalkToNpcExecutiveListener;

import static com.openrsc.server.plugins.Functions.*;

public final class Ned implements TalkToNpcExecutiveListener, TalkToNpcListener {

	@Override
	public boolean blockTalkToNpc(final Player p, final Npc n) {
		return n.getID() == 124;
	}

	@Override
	public void onTalkToNpc(final Player p, final Npc n) {
		npcTalk(p, n, "Why hello there, me friends call me Ned",
			"I was a man of the sea, but its past me now",
			"Could I be making or selling you some Rope?"
		);
		String[] menu = new String[] { // Default Menu
			"Yes, I would like some Rope",
			"No thanks Ned, I don't need any"
		};
		if (p.getQuestStage(Quests.DRAGON_SLAYER) == 2 && !p.getCache().hasKey("ned_hired")) {
			if (p.getQuestStage(Constants.Quests.PRINCE_ALI_RESCUE) == 2) {
				menu = new String[] { // Dragon Slayer + Prince Ali Rescue
					"You're a sailor? Could you take me to the Isle of Crandor",
					"Yes, I would like some Rope",
					"Ned, could you make other things from wool?",
					"No thanks Ned, I don't need any"
				};
				int choice = showMenu(p, n, menu);
				makeChoice(p, n, choice);
			}

			else {
				menu = new String[] { // Dragon Slayer
					"You're a sailor? Could you take me to the Isle of Crandor",
					"Yes, I would like some Rope",
					"No thanks Ned, I don't need any"
				};
				int choice = showMenu(p, n, menu);
				if (choice >= 2)
					makeChoice(p, n, 3);
				else
					makeChoice(p, n, choice);
			}
		}
		else if (p.getQuestStage(Constants.Quests.PRINCE_ALI_RESCUE) == 2) {
			menu = new String[] { // Prince Ali Rescue
				"Yes, I would like some Rope",
				"Ned, could you make other things from wool?",
				"No thanks Ned, I don't need any"
			};
			int choice = showMenu(p, n, menu);
			makeChoice(p, n, choice + 1);
		}
		else {
			int choice = showMenu(p, n, menu);
			if (choice == 0)
				makeChoice(p, n, 1);
			else if (choice == 1)
				makeChoice(p, n, 3);
		}
	}

	public void makeChoice(Player p, Npc n, int option) {
		if (option == 0) { // Dragon Slayer
			npcTalk(p, n, "Well I was a sailor",
					"I've not been able to get work at sea these days though",
					"They say I am too old"
			);
			message(p, "There is a wistfull look in Ned's eyes");
			npcTalk(p, n, "I miss those days",
					"If you could get me a ship I would take you anywhere"
			);
			if (p.getCache().hasKey("ship_fixed")) {
				playerTalk(p, n, "As it happens I do have a ship ready to sail");
				npcTalk(p, n, "That'd be grand, where is it");
				playerTalk(p, n, "It's called the Lumbridge Lady and it's docked in Port Sarim");
				npcTalk(p, n, "I'll go right over there and check her out then",
						"See you over there"
				);
				p.getCache().store("ned_hired", true);
			} else {
				playerTalk(p, n, "I will work on finding a sea worthy ship then");
			}
		}

		else if (option == 1) { // Buy Rope
			npcTalk(p, n, "Well, I can sell you some rope for 15 coins",
					"Or I can be making you some if you gets me 4 balls of wool",
					"I strands them together I does, makes em strong"
			);
			int choice = showMenu(p, n, false, new String[] {
				"Okay, please sell me some Rope",
				"Thats a little more than I want to pay",
				"I will go and get some wool"
			});
			if (choice == 0) {
				if (p.getInventory().countId(10) <= 15) {
					p.message("You Don't have enough coins to buy any rope!");
				} else {
					playerTalk(p, n, "Okay, please sell me some Rope");
					p.message("You hand Ned 15 coins");
					npcTalk(p, n, "There you go, finest rope in Runescape");
					p.getInventory().add(new Item(237, 1));
					p.getInventory().remove(10, 15);
					p.message("Ned gives you a coil of rope");
				}
			}
			else if (choice == 1) {
				playerTalk(p, n, "Thats a little more than I want to pay");
				npcTalk(p, n, "Well, if you ever need some rope. thats the price. sorry",
						"An old sailor needs money for a little drop o rum."
				);
			}
			else if (choice == 2) {
				playerTalk(p, n, "I will go and get some wool");
				npcTalk(p, n, "Aye, you do that",
						"Remember, it takes 4 balls of wool to make strong rope");
			}
		}

		else if (option == 2) { // Prince Ali's Rescue
			npcTalk(p, n, "Well... Thats an interesting thought",
					"yes, I think I could do something",
					"Give me 3 balls of wool and I might be able to do it"
			);
			if (p.getInventory().countId(207) >= 3) {
				int choice = showMenu(p, n, new String[] {
					"I have that now. Please, make me a wig",
					"I will come back when I need you to make one"
				});
				if (choice == 0) {
					npcTalk(p, n, "Okay. I will have a go.");
					message(p, "You hand Ned 3 balls of wool",
							"Ned works with the wool. His hands move with a speed you couldn't imagine"
					);
					removeItem(p, 207, 3);
					npcTalk(p, n, "Here you go, hows that for a quick effort? Not bad I think!");
					p.message("Ned gives you a pretty good wig");
					addItem(p, 245, 1);
					playerTalk(p, n, "Thanks Ned, theres more to you than meets the eye");
				}
				else if (choice == 1) {
					npcTalk(p, n, "Well, it sounds like a challenge",
							"Come to me if you need one"
					);
				}
			} else {
				playerTalk(p, n, "great, I will get some. I think a wig would be useful");
			}
		}

		else if (option == 3) { // No thanks
			npcTalk(p, n, "Well, old Neddy is always here if you do",
				"Tell your friends, I can always be using the business"
			);
		}
	}
}
