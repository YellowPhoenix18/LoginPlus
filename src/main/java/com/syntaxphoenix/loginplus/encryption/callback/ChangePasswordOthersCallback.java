package com.syntaxphoenix.loginplus.encryption.callback;

import java.util.Optional;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.syntaxphoenix.loginplus.accounts.Account;
import com.syntaxphoenix.loginplus.config.MessagesConfig;
import com.syntaxphoenix.loginplus.encryption.EncryptionType;
import com.syntaxphoenix.loginplus.encryption.thread.EncryptionCallback;
import com.syntaxphoenix.loginplus.utils.PluginUtils;

public class ChangePasswordOthersCallback extends BukkitRunnable implements EncryptionCallback {
	
	private PluginUtils pluginUtils;
	
	private CommandSender sender;
	private Player player;
	private String password;
	private EncryptionType type;
	
	public ChangePasswordOthersCallback(PluginUtils pluginUtils, CommandSender sender, Player player, String password) {
		this.pluginUtils = pluginUtils;
		this.player = player;
		this.password = password;
		this.sender = sender;
		
		this.type = pluginUtils.getConfig().getEncryptionType();
	}

	@Override
	public void run() {
		this.pluginUtils.getEncryptionManager().hashPassword(password, type, this);
	}

	@Override
	public void validateCallback(boolean validation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void encryptCallback(String hash) {
		Optional<Account> account;
		try {
			account = this.pluginUtils.getAccountManager().getAccount(player.getName());
			if (account.isPresent()) {
				account.get().setType(type);
				account.get().setHash(hash);
				this.pluginUtils.getAccountManager().updateAccount(account.get());
				
				this.sender.sendMessage(MessagesConfig.prefix + "The account has been updated");
			}
		} catch (Exception exception) {
			// TODO: Proper handling here
			exception.printStackTrace();
		}
		if (sender instanceof Player) {
			this.pluginUtils.getUserHandler().removeAwaitingCallback((Player) sender);
		}
	}

}
