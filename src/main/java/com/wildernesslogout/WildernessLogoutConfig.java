package com.wildernesslogout;

import java.awt.event.KeyEvent;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.Keybind;

@ConfigGroup(WildernessLogoutPlugin.CONFIG_GROUP)
public interface WildernessLogoutConfig extends Config
{
	@ConfigItem(
		keyName = "showButton",
		name = "Show logout button",
		description = "Show the logout button over the game regardless of the selected tab",
		position = 0
	)
	default boolean showButton()
	{
		return true;
	}

	@ConfigItem(
		keyName = "logoutHotkey",
		name = "Logout hotkey",
		description = "Immediately attempts to log out; normal combat logout restrictions still apply",
		position = 1
	)
	default Keybind logoutHotkey()
	{
		return new Keybind(KeyEvent.VK_F12, 0);
	}

	@ConfigItem(
		keyName = "wildernessPlayerBeep",
		name = "Wilderness player beep",
		description = "Play a persistent tone while another player is visibly rendered in the Wilderness",
		position = 2
	)
	default boolean wildernessPlayerBeep()
	{
		return false;
	}
}
