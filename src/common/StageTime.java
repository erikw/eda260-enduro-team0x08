package common;

/**
 * 
 * Representation of a stage. Holds a start time and a finish time.
 *
 */
public class StageTime {

	/* The start time. */ 
	private EnduroTime startTime;
	
	/* The finish time. */
	private EnduroTime finishTime;
	
	/* Multiply the time with this value to get it's weighted value. */
	private int amplifier;
	
	/**
	 * Create an empty StageTime
	 */
	public StageTime() {
		amplifier = 1;
	}
	
	/**
	 * Creates a StageTime for holding stage data
	 * 
	 * @param startTime
	 *            The Startime for the stage
	 * @param finishTime
	 *            The Finishtime for the stage
	 */
	public StageTime(EnduroTime startTime, EnduroTime finishTime) {
		this.startTime = startTime;
		this.finishTime = finishTime;
		amplifier = 1;
	}
	/**
	 * Creates a StageTime for holding stage data
	 * @param startTime The Startime for the stage
	 * @param finishTime The Finishtime for the stage
	 * @param amplifier The amplifier for the stage
	 */
	public StageTime(EnduroTime startTime, EnduroTime finishTime, int amplifier) {
		this.startTime = startTime;
		this.finishTime = finishTime;
		this.amplifier = amplifier;
	}
	

	/**
	 * Sets the start time from an EnduroTime
	 * 
	 * @param startTime
	 *            The time to set
	 */
	public void setStartTime(EnduroTime startTime) {
		this.startTime = startTime;
	}

	/**
	 * Sets the finish time from an EnduroTime
	 * 
	 * @param finishTime
	 *            The time to set
	 */
	public void setFinishTime(EnduroTime finishTime) {
		this.finishTime = finishTime;
	}

	/**
	 * Returns an EnduroTime with the start time
	 * 
	 * @return Start time
	 */
	public EnduroTime getStartTime() {
		return startTime;
	}

	/**
	 * Returns a string with the start time
	 * 
	 * @return Start time
	 */
	public String getStartTimeString() {
		if (startTime == null)
			return "Start?";
		return startTime.toString();
	}

	/**
	 * Returns an EnduroTime with the finish time
	 * 
	 * @return Finish time
	 */
	public EnduroTime getFinishTime() {
		return finishTime;
	}

	/**
	 * Returns a string with the finish time
	 * 
	 * @return Finish time
	 */
	public String getFinishTimeString() {
		if (finishTime == null)
			return "Slut?";
		return finishTime.toString();
	}

	/**
	 * Returns the total time for the stage as an EnduroTime
	 * 
	 * @return Totaltime
	 */
	public EnduroTime getTotal() {
		return startTime.compare(finishTime).mul(amplifier);
	}
	
	/**
	 * Returns the total time for the stage as an EnduroTime.
	 * 
	 * @return The total time.
	 */
	public String getTotalString() {
		if(startTime == null)
			return "";
					
		return startTime.compare(finishTime).mul(amplifier).toString();
	}


	public int getAmplifier() {
		return amplifier;
	}

	public void setAmplifier(int i) {
		if(i >= 1){
			amplifier = i;
		}
		
	}
	
}


