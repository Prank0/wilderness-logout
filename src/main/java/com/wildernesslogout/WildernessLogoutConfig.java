package com.wildernesslogout;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

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
		keyName = "wildernessPlayerBeep",
		name = "Wilderness player beep",
		description = "Play a persistent tone while another player is visibly rendered in the Wilderness",
		position = 1
	)
	default boolean wildernessPlayerBeep()
	{
		return false;
	}
}
