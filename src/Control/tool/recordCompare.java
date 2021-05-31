package Control.tool;
import java.util.*;

import entity.recordCourseRank;

/**
 * compare the recorded course's ordered times
 */
public class recordCompare implements Comparator{

	@Override
	public int compare(Object o1, Object o2) {
		recordCourseRank e1=(recordCourseRank)o1; 
		recordCourseRank e2=(recordCourseRank)o2; 
		int a = Integer.parseInt(e1.getNumber());
		int b = Integer.parseInt(e2.getNumber());
		if(a<b) 
		return 1; 
		else 
		return 0; 
	}
	
	

}