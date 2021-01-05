import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Methods {

	static Date epochToDate(Long epoch) {
		Date expiry = new Date(epoch * 1000);
		return expiry;
	}

	public static long deltaInSeconds(Date startTime, Date endTime) {
		long diffInMillies = Math.abs(endTime.getTime() - startTime.getTime());
		long diff = TimeUnit.SECONDS.convert(diffInMillies, TimeUnit.MILLISECONDS);
		return diff;
	}

	static String secondsToTimeString(long seconds) {
		long hours = seconds / 60 / 60;
		long minutes = (seconds / 60) % 60;
		String ret = hours + " hours and " + minutes + " minutes";
		return ret;
	}
}
