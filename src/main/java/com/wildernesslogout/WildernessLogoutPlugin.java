package com.wildernesslogout;

import com.google.inject.Provides;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.MenuAction;
import net.runelite.api.Player;
import net.runelite.api.WorldView;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;
import net.runelite.api.gameval.InterfaceID;
import net.runelite.api.gameval.VarClientID;
import net.runelite.api.widgets.Widget;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.input.MouseManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

@PluginDescriptor(
	name = "Wilderness Logout",
	description = "Adds an always-visible logout button and optional wilderness player tone",
	tags = {"wilderness", "logout", "panic", "pvp"},
	enabledByDefault = false
)
public class WildernessLogoutPlugin extends Plugin
{
	static final String CONFIG_GROUP = "wildernesslogout";

	@Inject
	private Client client;

	@Inject
	private ClientThread clientThread;

	@Inject
	private WildernessLogoutConfig config;

	@Inject
	private WildernessLogoutOverlay overlay;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private MouseManager mouseManager;

	private final PersistentBeep persistentBeep = new PersistentBeep();

	@Override
	protected void startUp()
	{
		overlayManager.add(overlay);
		mouseManager.registerMouseListener(overlay.getMouseListener());
	}

	@Override
	protected void shutDown()
	{
		persistentBeep.close();
		mouseManager.unregisterMouseListener(overlay.getMouseListener());
		overlayManager.remove(overlay);
	}

	@Subscribe
	public void onGameTick(GameTick event)
	{
		persistentBeep.setActive(config.wildernessPlayerBeep() && hasVisibleWildernessPlayer());
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged event)
	{
		if (event.getGameState() != GameState.LOGGED_IN)
		{
			persistentBeep.setActive(false);
		}
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged event)
	{
		if (CONFIG_GROUP.equals(event.getGroup()) && !config.wildernessPlayerBeep())
		{
			persistentBeep.setActive(false);
		}
	}

	void requestLogout()
	{
		clientThread.invoke(this::logout);
	}

	private void logout()
	{
		if (client.getGameState() != GameState.LOGGED_IN)
		{
			return;
		}

		Widget logoutButton = client.getWidget(InterfaceID.Logout.LOGOUT);
		if (logoutButton == null)
		{
			return;
		}

		client.menuAction(
			-1,
			logoutButton.getId(),
			MenuAction.CC_OP,
			1,
			-1,
			"Logout",
			"");
	}

	private boolean hasVisibleWildernessPlayer()
	{
		if (client.getGameState() != GameState.LOGGED_IN
			|| client.getVarcIntValue(VarClientID.COMBAT_INSIDEWILDERNESS) == 0)
		{
			return false;
		}

		Player localPlayer = client.getLocalPlayer();
		WorldView worldView = client.getTopLevelWorldView();
		if (localPlayer == null || worldView == null)
		{
			return false;
		}

		return worldView.players().stream()
			.anyMatch(player -> player != localPlayer && player.getCanvasTilePoly() != null);
	}

	@Provides
	WildernessLogoutConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(WildernessLogoutConfig.class);
	}
}
