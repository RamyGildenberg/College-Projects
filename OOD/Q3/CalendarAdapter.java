

import java.util.Calendar;
import java.util.GregorianCalendar;

public class CalendarAdapter implements CalendarInterface {
	private Calendar calendar = new GregorianCalendar();

	
	@Override
	public int getMinute() {
		return calendar.get(Calendar.MINUTE);
	}
	
	@Override
	public int getHour() {
		return calendar.get(Calendar.HOUR);
	}

	@Override
	public int getSecond() {
		return calendar.get(Calendar.SECOND);
	}

}