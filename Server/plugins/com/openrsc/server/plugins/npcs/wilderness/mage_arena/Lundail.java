package com.openrsc.server.plugins.npcs.wilderness.mage_arena;

import com.openrsc.server.model.Shop;
import com.openrsc.server.model.container.Item;
import com.openrsc.server.model.entity.npc.Npc;
import com.openrsc.server.model.entity.player.Player;
import com.openrsc.server.net.rsc.ActionSender;
import com.openrsc.server.plugins.ShopInterface;
import com.openrsc.server.plugins.listeners.action.TalkToNpcListener;
import com.openrsc.server.plugins.listeners.executive.TalkToNpcExecutiveListener;

import static com.openrsc.server.plugins.Functions.npcTalk;
import static com.openrsc.server.plugins.Functions.showMenu;

public final class Lundail implements ShopInterface,
		TalkToNpcExecutiveListener, TalkToNpcListener {

	private final Shop shop = new Shop(false, 6000, 190, 60, 10, new Item(33,
			100), new Item(31, 100), new Item(32, 100), new Item(34,
			100), new Item(35, 100), new Item(36, 100));

	@Override
	public void onTalkToNpc(Player p, final Npc n) {
		npcTalk(p, n, "well hello sir", "hello brave adventurer",
				"how can i help you?");

		int option = showMenu(p, n, "what are you selling?",
				"what's that big old building behind us?");
		switch (option) {
		case 0:
			npcTalk(p, n, "why, i sell rune stones",
					"i've got some good stuff, real powerful little rocks",
					"take a look");
			p.setAccessingShop(shop);
			ActionSender.showShop(p, shop);
			break;

		case 1:
			npcTalk(p, n, "why that my friend...",
					"...is the mage battle arena",
					"top mages come from all over to compete in the arena",
					"few return back, most get fried...hence the smell");
			npcTalk(p, n, "hmmm.. i did notice");
			break;

		}
	}

	@Override
	public boolean blockTalkToNpc(Player p, Npc n) {
		return n.getID() == 793;
	}

	@Override
	public Shop[] getShops() {
		return new Shop[] { shop };
	}

	@Override
	public boolean isMembers() {
		return true;
	}

}
