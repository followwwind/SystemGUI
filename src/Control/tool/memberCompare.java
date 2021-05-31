package Control.tool;
import java.util.*;

import entity.memberRank;

/**
 * compare the member by parameter and rank them
 */
public class memberCompare implements Comparator{

	@Override
	public int compare(Object o1, Object o2) {
		memberRank e1=(memberRank)o1; 
		memberRank e2=(memberRank)o2; 
		double a = Double.valueOf(e1.getMoney());
		double b =Double.valueOf(e2.getMoney());
		if(a<b) 
		return 1; 
		else 
		return 0; 
	}
	
	

}