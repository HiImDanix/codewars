package pickPeaks;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PickPeaks {

	public static void main(String[] args) {
		Map<String, List<Integer>> returned = getPeaks(new int[] {3,2,3,6,4,1,2,3,2,1,2,2,2,1});
		System.out.println("pos: " + returned.get("pos"));
		System.out.println("peaks: " + returned.get("peaks"));

	}
	    
    public static Map<String,List<Integer>> getPeaks(int[] arr) {
    	List<Integer> positions = new ArrayList<>();
    	List<Integer> peaks = new ArrayList<>();
    	
    	Integer lastGrowthPos = null;
    	for (int i = 1; i < arr.length; i++) {
    		// if curr > previous, store
			if (arr[i] > arr[i-1]) {
				lastGrowthPos = i;
				continue;
			// If curr < previous and lastGrowthPos exists, add it to the peaks/positions!
			} else if (arr[i] < arr[i-1] && lastGrowthPos != null) {
				positions.add(lastGrowthPos);
				peaks.add(arr[lastGrowthPos]);
				lastGrowthPos = null;
			}
    		
    	}
    	
    	// Return positions & peaks
    	Map<String, List<Integer>> returnMap = new HashMap<>();
    	returnMap.put("pos", positions);
    	returnMap.put("peaks", peaks);
        return returnMap;
    }

}
