package common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static common.ErrorMessage.error;

/**
 * A representation of time used in the Enduro program.
 * 
 */
public class EnduroTime implements Comparable<Object> {

	/* The number of hours. */
	private int hours;
	/* The number of minutes. */
	private int minutes;

	/* The number of seconds. */
	private int seconds;

	/**
	 * Creates an EnduroTime with the current time
	 */
	public EnduroTime() {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
		Date date = new Date();
		String tid = dateFormat.format(date).replace(':', '.');
		set(tid);
	}

	/**
	 * Creates an EnduroTime.
	 * 
	 * @param sec
	 *            Number of seconds since 00:00:00.
	 */
	public EnduroTime(int sec) {
	 set(sec);
	}

	/**
	 * Creates an EnduroTime.
	 * 
	 * @param hour
	 *            Initial hours
	 * @param minute
	 *            Initial minutes
	 * @param second
	 *            Initial seconds
	 */
	public EnduroTime(int hour, int minute, int second) {
		set(hour, minute, second);
	}

	/**
	 * Creates an EnduroTime.
	 * 
	 * @param time
	 *            String must be in xx:xx:xx format
	 */
	public EnduroTime(String time) {
		set(time);
	}

	/**
	 * Set the start time to entered values
	 * 
	 * @param h
	 *            Hours to set
	 * @param m
	 *            Minutes to set
	 * @param s
	 *            Seconds to set
	 */
	public void set(int h, int m, int s) {
		hours = h;
		minutes = m;
		seconds = s;
	}

	/**
	 * Set a time from string.
	 * 
	 * @param time
	 *            It must must be in format xx.xx.xx
	 */
	public void set(String time) {
		String t = time.trim();
		String[] times = t.split("\\.");
		if (times.length < 2 || times[0].equals("--")) {
			hours = 0;
			minutes = 0;
			seconds = 0;
			return;
		}

		hours = Integer.parseInt(times[0]);
		minutes = Integer.parseInt(times[1]);
		seconds = Integer.parseInt(times[2]);
	}

	/**
	 * Set a time from seconds since 00:00:00.
	 * 
	 * @param second
	 *            Number of seconds.
	 */
	public void set(int second) {
		hours = (second / 3600) % 24;
		minutes = (second / 60) % 60;
		seconds = second % 60;
	}

	/**
	 * Makes a string representation of the time.
	 * 
	 * @return String The time as a String (00:00:00)
	 */
	public String toString() {
		String h = "" + ((hours < 10) ? "0" + hours : hours);
		String m = "" + ((minutes < 10) ? "0" + minutes : minutes);
		String s = "" + ((seconds < 10) ? "0" + seconds : seconds);
		return h + "." + m + "." + s;
	}

	/**
	 * Compares the time and returns a new EnduroTime with the
	 * differential time.
	 * 
	 * @param rhs
	 *            Time to compare to.
	 * @return EnduroTime The differential time.
	 */
	public EnduroTime compare(EnduroTime rhs) {
		int time1 = this.toSeconds();
		int time2 = rhs.toSeconds();

		return new EnduroTime(time2 - time1);
	}

	/**
	 * Get the time in seconds.
	 * 
	 * @return seconds The number of seconds in this time.
	 */
	public int toSeconds() {
		return (hours * 60 * 60) + (minutes * 60) + seconds;
	}

	/**
	 * Compares the times.
	 * 
	 * @return Returns a boolean, True if equal, false if not.
	 */
	public boolean equals(Object o) {
		EnduroTime rhs = null;
		try {
			rhs = (EnduroTime) o;
		} catch (ClassCastException e) {
			error(e, "The parameter object was not of type EnduroTime");
			return false;
		}
		return hours == rhs.hours && minutes == rhs.minutes
				&& seconds == rhs.seconds;
	}

	/**
	 * Adds rhs to the time
	 * 
	 * @param rhs
	 */
	public void add(EnduroTime rhs) {
		set(toSeconds() + rhs.toSeconds());
	}

	/**
	 * Compares two EnduroTimes
	 * 
	 * @param rhs the Object to compare to.
	 * @return returns an Interger with the differance (in seconds)
	 */
	public int compareTo(Object rhs) {
		EnduroTime theOtherTotalTime = (EnduroTime)rhs;
		return toSeconds() - theOtherTotalTime.toSeconds();
	}
	
	/**
	 * Amplifies the time with a factor aplifier
	 * @param amplifier The amplifier
	 * @return A new EnduroTime amplified by amplifier
	 */
	public EnduroTime mul(int amplifier) {
		return new EnduroTime(toSeconds()*amplifier);
	}
	
}
