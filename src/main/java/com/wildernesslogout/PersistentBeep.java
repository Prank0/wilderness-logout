package com.wildernesslogout;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

final class PersistentBeep implements AutoCloseable
{
	private static final float SAMPLE_RATE = 8_000f;
	private static final int FREQUENCY_HZ = 740;
	private static final int SAMPLE_COUNT = 800;

	private Clip clip;
	private boolean unavailable;

	synchronized void setActive(boolean active)
	{
		if (!active)
		{
			stop();
			return;
		}

		if (unavailable || (clip != null && clip.isRunning()))
		{
			return;
		}

		try
		{
			if (clip == null)
			{
				AudioFormat format = new AudioFormat(SAMPLE_RATE, 8, 1, true, false);
				byte[] samples = createTone();
				clip = AudioSystem.getClip();
				clip.open(format, samples, 0, samples.length);
			}

			clip.setFramePosition(0);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}
		catch (LineUnavailableException | IllegalArgumentException ex)
		{
			unavailable = true;
			close();
		}
	}

	private static byte[] createTone()
	{
		byte[] samples = new byte[SAMPLE_COUNT];
		for (int i = 0; i < samples.length; i++)
		{
			double angle = 2.0 * Math.PI * FREQUENCY_HZ * i / SAMPLE_RATE;
			samples[i] = (byte) (Math.sin(angle) * 42.0);
		}
		return samples;
	}

	private void stop()
	{
		if (clip != null && clip.isRunning())
		{
			clip.stop();
		}
	}

	@Override
	public synchronized void close()
	{
		if (clip != null)
		{
			clip.stop();
			clip.close();
			clip = null;
		}
	}
}
