package simulationProgram.util;

import java.util.ArrayList;

public abstract class MinArrayList {

	public static double minArrayList(ArrayList<Double> arrayList) {
		double min = Double.MAX_VALUE;
		for (Double arrayListItem : arrayList) {
			if(arrayListItem < min) {
				min = arrayListItem;
			}
		}
		return min;
	}
}
