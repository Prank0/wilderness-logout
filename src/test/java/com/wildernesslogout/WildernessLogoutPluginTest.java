package com.wildernesslogout;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class WildernessLogoutPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(WildernessLogoutPlugin.class);
		RuneLite.main(args);
	}
}
