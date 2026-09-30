package com.wildernesslogout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.client.input.MouseAdapter;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;

final class WildernessLogoutOverlay extends Overlay
{
	private static final int WIDTH = 126;
	private static final int HEIGHT = 38;
	private static final Color BACKGROUND = new Color(103, 25, 25, 225);
	private static final Color BACKGROUND_HOVER = new Color(158, 36, 36, 240);
	private static final Color BORDER = new Color(231, 184, 77);
	private static final Color TEXT = Color.WHITE;

	private final Client client;
	private final WildernessLogoutPlugin plugin;
	private final WildernessLogoutConfig config;
	private boolean pressedOnButton;

	private final MouseAdapter mouseListener = new MouseAdapter()
	{
		@Override
		public MouseEvent mousePressed(MouseEvent event)
		{
			if (event.getButton() == MouseEvent.BUTTON1 && !event.isAltDown() && isButtonHit(event))
			{
				pressedOnButton = true;
				event.consume();
			}
			return event;
		}

		@Override
		public MouseEvent mouseReleased(MouseEvent event)
		{
			if (pressedOnButton && event.getButton() == MouseEvent.BUTTON1)
			{
				pressedOnButton = false;
				if (isButtonHit(event))
				{
					plugin.requestLogout();
				}
				event.consume();
			}
			return event;
		}

		@Override
		public MouseEvent mouseClicked(MouseEvent event)
		{
			if (event.getButton() == MouseEvent.BUTTON1 && isButtonHit(event))
			{
				event.consume();
			}
			return event;
		}
	};

	@Inject
	private WildernessLogoutOverlay(
		Client client,
		WildernessLogoutPlugin plugin,
		WildernessLogoutConfig config)
	{
		super(plugin);
		this.client = client;
		this.plugin = plugin;
		this.config = config;
		setPosition(OverlayPosition.TOP_RIGHT);
		setLayer(OverlayLayer.ABOVE_WIDGETS);
		setPriority(PRIORITY_HIGH);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
	}

	MouseAdapter getMouseListener()
	{
		return mouseListener;
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		if (!config.showButton() || client.getGameState() != GameState.LOGGED_IN)
		{
			return null;
		}

		net.runelite.api.Point mouse = client.getMouseCanvasPosition();
		boolean hovered = getBounds().contains(mouse.getX(), mouse.getY());
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics.setColor(hovered ? BACKGROUND_HOVER : BACKGROUND);
		graphics.fillRoundRect(0, 0, WIDTH, HEIGHT, 8, 8);
		graphics.setColor(BORDER);
		graphics.drawRoundRect(0, 0, WIDTH - 1, HEIGHT - 1, 8, 8);

		graphics.setFont(FontManager.getRunescapeBoldFont());
		String label = "LOG OUT";
		FontMetrics metrics = graphics.getFontMetrics();
		int x = (WIDTH - metrics.stringWidth(label)) / 2;
		int y = (HEIGHT - metrics.getHeight()) / 2 + metrics.getAscent();
		graphics.setColor(TEXT);
		graphics.drawString(label, Math.max(5, x), y);

		return new Dimension(WIDTH, HEIGHT);
	}

	private boolean isButtonHit(MouseEvent event)
	{
		return config.showButton()
			&& client.getGameState() == GameState.LOGGED_IN
			&& getBounds().contains(event.getPoint());
	}
}
