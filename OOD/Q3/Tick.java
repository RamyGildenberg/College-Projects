

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javafx.application.Platform;

public class Tick implements Runnable {
	private boolean suspended = false;
	private int sleepTime = 1000;
	private ClockPane clockPane;
	private Lock lock = new ReentrantLock();
	private Condition stopCon = lock.newCondition();
	
	
	
	public Tick(ClockPane clockPane)
	{
		this.clockPane=clockPane;
	}
	@Override
	public void run()
	{
		activateClock();
	}
	
	public void activateClock()
	{
		while(true)
		{
			lock.lock();
			try
			{
					if(suspended)
					{
						try
						{
							stopCon.await();
						}
						catch(InterruptedException ex)
						{
							ex.printStackTrace();
						}
					}
				
					else
					{
						
						Platform.runLater(()->clockPane.paintClock());
						try
						{
							if(clockPane.getSecond()==0)
							{
								new Thread(new AnnounceTimeOnSeparateThread(clockPane.getHour(),clockPane.getMinute())).start();
							}
							clockPane.setCurrentTime();
							Thread.sleep(sleepTime);
							}
						catch(InterruptedException ex)
						{
								ex.printStackTrace();
						}
					}
			}
			finally {
				lock.unlock();
			}
		}

	}
	protected void pause()
	{
		suspended=true;
	}
	protected void play() {
		if (lock.tryLock()) {
			try {
				suspended=false;
				stopCon.signal();
			} finally {
				lock.unlock();
			}
		}
	}
}

