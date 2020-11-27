
class CalculatedTimeSegment {
	long startTime;
	long endTime;
	long duration;
	long minutes;
	long hours;

	public CalculatedTimeSegment(TimeSegment timeSegment) {
		startTime = timeSegment.startTime;
		endTime = timeSegment.endTime;
		duration = Methods.deltaInSeconds(Methods.epochToDate(startTime), Methods.epochToDate(endTime));
		hours = duration / 60 / 60;
		minutes = (duration / 60) % 60;

	}
}