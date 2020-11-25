
class CalculatedTimeSegment {
	long startTime;
	long endTime;
	long duration;

	public CalculatedTimeSegment(TimeSegment timeSegment) {
		startTime = timeSegment.startTime;
		endTime = timeSegment.endTime;
		duration = Methods.deltaInSeconds(Methods.epochToDate(startTime), Methods.epochToDate(endTime));
	}

}