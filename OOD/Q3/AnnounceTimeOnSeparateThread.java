

import java.applet.*;

public class AnnounceTimeOnSeparateThread implements Runnable {
	private final static int NUM_OF_HOURS = 12;
	private final static int NUM_OF_MINUTES = 60;
	private AudioClip amAudio = Applet.newAudioClip(this.getClass().getResource("/audio/am.au"));
	private AudioClip pmAudio = Applet.newAudioClip(this.getClass().getResource("/audio/pm.au"));
	private int hour;
	private int minute;
	
	//basic default constructor
	public AnnounceTimeOnSeparateThread(int hour,int minute)
	{
		this.hour=hour;
		this.minute=minute;
	}
	
	@Override
	public void run() {
		callOutTheTime();
	}
	
	protected void callOutTheTime()
	{
		try {
			Applet.newAudioClip(this.getClass().getResource("/audio/hour" + getHour() % NUM_OF_HOURS + ".au")).play();
			Thread.sleep(1500);
			Applet.newAudioClip(this.getClass().getResource("/audio/minute" + getMinute() % NUM_OF_MINUTES + ".au")).play();
			Thread.sleep(1500);
		} catch (InterruptedException ex) {
		}
		if (hour < NUM_OF_HOURS) {
			amAudio.play();
		} else {
				pmAudio.play();
			}
	}
	protected int getHour() 
	{
		return hour;
	}

	protected void setHour(int hour)
	{
		this.hour = hour;
	}

	protected int getMinute()
	{
		return minute;
	}

	protected void setMinute(int minute) 
	{
		this.minute = minute;
	}
		
}

