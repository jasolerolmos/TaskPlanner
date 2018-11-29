package model;

public class Projekt {
	private int id;
	private String name;

	public Projekt() {
		this.id = 0;
		this.name = "";
	}
	
	public Projekt(String name) {
		this.id = 0;
		this.name = name;
	}
	
	public Projekt(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		return name;
	}
	
}
