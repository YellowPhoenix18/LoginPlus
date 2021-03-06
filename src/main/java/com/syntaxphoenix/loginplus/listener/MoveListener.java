package com.syntaxphoenix.loginplus.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.syntaxphoenix.loginplus.utils.PluginUtils;
import com.syntaxphoenix.loginplus.utils.login.Status;

public class MoveListener implements Listener {
	
	private PluginUtils pluginUtils;
	
	public MoveListener(PluginUtils pluginUtils) {
		this.pluginUtils = pluginUtils;
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void on(PlayerMoveEvent event) {
		Status status = pluginUtils.getUserHandler().getStatus(event.getPlayer());
		if (status == Status.LOGIN || status == Status.REGISTER || status == Status.CAPTCHA || status == Status.LOGGEDIN) {
			if (event.getFrom().getX() != event.getTo().getX() ||
				event.getFrom().getZ() != event.getTo().getZ()) {
				event.setCancelled(true);
			}
		}
	}
}
