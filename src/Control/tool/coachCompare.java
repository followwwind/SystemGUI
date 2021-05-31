package Control.tool;
import java.util.*;

import entity.coachRank;

/**
 * compare the coach by parameter and rank them
 */
public class coachCompare implements Comparator{

	@Override
	public int compare(Object o1, Object o2) {
		coachRank e1=(coachRank)o1; 
		coachRank e2=(coachRank)o2; 
		int a = Integer.parseInt(e1.getNumber());
		int b = Integer.parseInt(e2.getNumber());
		if(a<b) 
		return 1; 
		else 
		return 0; 
	}
	
	

}