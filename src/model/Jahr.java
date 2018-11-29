package model;

import java.util.ArrayList;
import java.util.List;

public class Jahr {
	private List<Monate> jahr = new ArrayList<Monate>();
	
	public Jahr() {
		jahr.add(new Monate(1, "Januar"));
		jahr.add(new Monate(2, "Februar"));
		jahr.add(new Monate(3, "März"));
		jahr.add(new Monate(4, "April"));
		jahr.add(new Monate(5, "Mai"));
		jahr.add(new Monate(6, "Juni"));
		jahr.add(new Monate(7, "Juli"));
		jahr.add(new Monate(8, "August"));
		jahr.add(new Monate(9, "September"));
		jahr.add(new Monate(10, "Oktober"));
		jahr.add(new Monate(11, "November"));
		jahr.add(new Monate(12, "Dezember"));
	}

	public void add(Monate mon) {
		jahr.add(mon);
	}
	
	public Monate get(int index) {
		return jahr.get(index);
	}
	
	public int size() {
		return jahr.size();
	}
}
