package com.openrsc.server.plugins.listeners.action;

import com.openrsc.server.model.entity.npc.Npc;
import com.openrsc.server.model.entity.player.Player;

public interface PlayerAttackNpcListener {
	
	 public void onPlayerAttackNpc(Player p, Npc affectedmob);

}
