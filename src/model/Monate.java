package model;

public class Monate {

	private int monate;
	private String name;
	
	public Monate(int m, String n) {
		monate = m;
		name = n;
	}
	public int getMonate() {
		return monate;
	}
	public void setMonate(int monate) {
		this.monate = monate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String toString() {
		return name.substring(0,3);
	}

}
