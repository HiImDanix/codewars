package tortoiseRacing;

import static org.junit.Assert.assertArrayEquals;

import java.util.Arrays;

public class Tortoise {
	public static void main(String[] args) {
		System.out.println(Arrays.toString(race(680, 742, 118)));
	}
	
	// My first version - whacky
    public static int[] race1(int v1, int v2, int g) {
    	if (v1 >= v2) {
    		return null;
    	}
    	// get feet / h difference (how much faster B is compared to A in feet/h)
    	int feetPerHourDiff = v2 - v1;
    	
    	// Calculate time in hours for A to reach B (This is the final result)
    	double timeInHours = (double) g / feetPerHourDiff;
    	
    	// Get hours, minutes & seconds for A to reach B as whole numbers.
    	// Calculate hours
    	int resultantTimeInHours = (int)Math.floor((timeInHours));
    	// Calculate minutes
    	int resultantTimeInMinutes = (int)Math.floor(((timeInHours * 60) - (resultantTimeInHours * 60)));
    	// calculate seconds
    	int resultantTimeInSeconds = (int)Math.floor((timeInHours * 3600) - (resultantTimeInHours * 3600) - (resultantTimeInMinutes * 60));
    	
    	// Return the time as array of [h, m, s]
    	return new int[] {
    			resultantTimeInHours,
    			resultantTimeInMinutes,
    			resultantTimeInSeconds
    	};
    }
    
    
    // My second version - after refactoring
    public static int[] race(int v1, int v2, int g) {
    	final int SECONDS_IN_MINUTE = 60;
    	final int MINUTES_IN_HOUR = 60;
    	
    	if (v1 >= v2) return null;

    	// Seconds for A to reach B
    	int feetPerHourDiff = v2 - v1;
    	int seconds = g * 3600 / feetPerHourDiff;
    	// Minutes
    	int minutes = seconds / 60;
    	seconds -= minutes * SECONDS_IN_MINUTE;
    	// hours
    	int hours = minutes / 60;
    	minutes -= hours * MINUTES_IN_HOUR;
    	
    	return new int[] {hours, minutes, seconds};
    }
    
}