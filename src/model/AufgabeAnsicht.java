package model;

public class AufgabeAnsicht {
	private String name = "";
	private String projekt = "";
	private Zeit zeit = new Zeit();
	
	public AufgabeAnsicht() {
	}

	public AufgabeAnsicht(String name, String projekt, Zeit zeit) {
		this.name = name;
		this.projekt = projekt;
		this.zeit = zeit;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProjekt() {
		return projekt;
	}

	public void setProjekt(String projekt) {
		this.projekt = projekt;
	}

	public Zeit getZeit() {
		return zeit;
	}

	public void setZeit(Zeit zeit) {
		this.zeit = zeit;
	}
	
	
}
