package com.openrsc.server.plugins.itemactions;

import com.openrsc.server.model.container.Item;
import com.openrsc.server.model.entity.player.Player;
import com.openrsc.server.net.rsc.ActionSender;
import com.openrsc.server.plugins.listeners.action.InvActionListener;
import com.openrsc.server.plugins.listeners.executive.InvActionExecutiveListener;

import static com.openrsc.server.plugins.Functions.*;

public class Eating implements InvActionListener, InvActionExecutiveListener {

	@Override
	public boolean blockInvAction(Item item, Player p) {
		if(item.isEdible()) {
			return true;
		}
		return false;
	}

	@Override
	public void onInvAction(Item item, Player p) {
		if (item.isEdible()) {
			if (p.cantConsume()) {
				return;
			}
			p.setConsumeTimer(325);
			ActionSender.sendSound(p, "eat");
			if (item.getID() == 228 || item.getID() == 18) { // Cabbage
				p.message("You eat the " + item.getDef().getName()
						+ ". Yuck!");
				if (item.getID() == 228) { // Special defense cabbage
					int lv = p.getSkills().getMaxStat(1);
					int newStat = p.getSkills().getLevel(1) + 1;
					if (newStat <= (lv + 1))
						p.getSkills().setLevel(1, newStat);
				}
			} else if(item.getID() == 907 || item.getID() == 950) { // Chocolate bomb
				message(p, "You eat the choc bomb");
				p.message("it tastes great");
			} else if(item.getID() == 908 || item.getID() == 951) { // Vegball
				message(p, "You eat the veg ball");
				p.message("it tastes quite good");
			} else if(item.getID() == 909 || item.getID() == 952) { // worm hole
				message(p, "You eat the " + item.getDef().getName().toLowerCase());
				playerTalk(p,null, "yuck");
				p.message("that was awful");
			} else if(item.getID() == 910 || item.getID() == 953) { // Tangled toads legs
				message(p, "You eat the tangled toads legs");
				p.message("it tastes.....slimey");
			} else if(item.getID() == 1061) { // Rock cake
				message(p, "You eat the " + item.getDef().getName().toLowerCase());
				playerTalk(p,null, "Ow! I nearly broke a tooth!");
				p.message("You feel strangely heavier and more tired");
			} else if(item.getID() == 873) { // Equa leaves
				p.message("You eat the leaves..chewy but tasty");
			} else if(item.getID() == 855) { // lemon
				p.message("You eat the lemon. Yuck!");
			} else if(item.getID() == 856 || item.getID() == 860) { // lemon slices
				p.message("You eat the " + item.getDef().getName().toLowerCase() + " ..they're very sour");
			} else if(item.getID() == 765) { // dwellberries
				p.message("You eat the berrys..quite tasty");
			} else if(item.getID() == 863) { // lime
				p.message("You eat the lime ..it's quite sour");
			} else if(item.getID() == 865 || item.getID() == 865) { // lime slices
				p.message("You eat the " + item.getDef().getName().toLowerCase() + "..they're quite sour");
			} else if(item.getID() == 858) { // orange slices
				p.message("You eat the orange slices ...yum");
			} else if(item.getID() == 859) { // Diced orange
				p.message("You eat the orange cubes ...yum");
			} else if(item.getID() == 861) { // Fresh Pineapple
				p.message("You eat the pineapple ...yum");
			} else if(item.getID() == 862) { // Pineapple chunks
				p.message("You eat the pineapple chunks ..yum");
			} else if(item.getID() == 871) { // cream
				p.message("You eat the cream..you get some on your nose");
			} else if(item.getID() == 885) { // gnomebowl
				message(p, 1200, "You eat the gnome bowl");
				p.message("it's pretty tastless");
				resetGnomeCooking(p);
			} else if(item.getID() == 900) { // gnomecrunchie
				p.message("You eat the gnome crunchies");
				resetGnomeCooking(p);
			} else if(item.getID() == 901 || item.getID() == 944) { // cheese and tomato batta
				message(p, "You eat the cheese and tomato batta");
				p.message("it's quite tasty");
			} else if(item.getID() == 902 || item.getID() == 945 ||
					item.getID() == 904 || item.getID() == 947) {
				// toad batta & worm batta
				message(p, "You eat the " + item.getDef().getName().toLowerCase());
				p.message("it's a bit chewy");
			} else if(item.getID() == 905 || item.getID() == 948 ||
					item.getID() == 906 || item.getID() == 949) {
				// fruit batta & veg batta
				message(p, "You eat the " + item.getDef().getName().toLowerCase());
				p.message("it's tastes pretty good");
			} else if(item.getID() == 911 || item.getID() == 954 ||
					item.getID() == 914 || item.getID() == 957) {
				// Choc crunchies & Spice crunchies
				message(p, "You eat the " + item.getDef().getName().toLowerCase());
				p.message("they're very tasty");
			} else if(item.getID() == 912 || item.getID() == 955 ||
					item.getID() == 913 || item.getID() == 956) {
				// Worm crunchies & Toad crunchies
				message(p, "You eat the " + item.getDef().getName().toLowerCase());
				p.message("they're a bit chewy");
			} else if(item.getID() == 2112) { // Blood egg (custom)
				p.message("You drink the blood from the egg ...aeehm");
			} else if(item.getID() == 2113) { // Easter egg (custom)
				p.message("You eat the easter egg ...yumm.. you get some on your nose");
			} else
				p.message("You eat the "
						+ item.getDef().getName().toLowerCase());
			
			final boolean heals = p.getSkills().getLevel(3) < p.getSkills().getMaxStat(3);
			if (heals) {
				int newHp = p.getSkills().getLevel(3) + item.eatingHeals();
				if (newHp > p.getSkills().getMaxStat(3)) {
					newHp = p.getSkills().getMaxStat(3);
				}
				p.getSkills().setLevel(3, newHp);
			}
			sleep(325);
			if (heals) {
				p.message("It heals some health");
			}
			p.getInventory().remove(item);
			switch (item.getID()) {
			case 326: // Meat pizza
				p.getInventory().add(new Item(328));
				break;
			case 327: // Anchovie pizza
				p.getInventory().add(new Item(329));
				break;
			case 330: // Cake
				p.getInventory().add(new Item(333));
				break;
			case 333: // Partical cake
				p.getInventory().add(new Item(335));
				break;
			case 332: // Choc cake
				p.getInventory().add(new Item(334));
				break;
			case 334: // Partical choc cake
				p.getInventory().add(new Item(336));
				break;
			case 257: // Apple pie
				p.getInventory().add(new Item(263));
				break;
			case 261: // Half apple pie
				p.getInventory().add(new Item(251));
				break;
			case 258: // Redberry pie
				p.getInventory().add(new Item(262));
				break;
			case 262: // Half redberry pie
				p.getInventory().add(new Item(251));
				break;
			case 259: // Meat pie
				p.getInventory().add(new Item(261));
				break;
			case 263: // Half meat pie
				p.getInventory().add(new Item(251));
				break;
			}
		}
	}
}
