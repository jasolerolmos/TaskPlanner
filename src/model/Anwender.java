package model;

public class Anwender {
	private int id;
	private String vorname;
	private String nachname;
	private String kennwort;
	
	
	public Anwender() {
		id = 0;
		vorname = "";
		nachname = "";
		kennwort = "";
	}

	public Anwender(String vorname, String nachname) {
		this.vorname = vorname;
		this.nachname = nachname;
	}
	
	public Anwender(String vorname, String nachname, String kennwort) {
		this.vorname = vorname;
		this.nachname = nachname;
		this.kennwort = kennwort;
	}

	public Anwender(int id, String vorname, String nachname) {
		this.id = id;
		this.vorname = vorname;
		this.nachname = nachname;
	}	
	
	public Anwender(int id, String vorname, String nachname, String kennwort) {
		this.id = id;
		this.vorname = vorname;
		this.nachname = nachname;
		this.kennwort = kennwort;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getNachname() {
		return nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	public String getKennwort() {
		return kennwort;
	}

	public void setKennwort(String kennwort) {
		this.kennwort = kennwort;
	}

	
	
}
