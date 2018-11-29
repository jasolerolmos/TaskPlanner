package model;

import java.util.Calendar;
import java.util.Date;

public class Zeit {
	private int id = 0;
	private int aufgabe = 0;
	private Date anfang;
	private Date beende;
	private Calendar dauer = Calendar.getInstance();
	private String tages = "0";
	private String stunde = "0";
	private String minute = "0";
	
	public Zeit() {
		
	}

	public Zeit(Date anfang, Date beende) {
		this.anfang = anfang;
		this.beende = beende;
	}

	public Zeit(int id, Date anfang, Date beende) {
		this.id = id;
		this.anfang = anfang;
		this.beende = beende;
	}

	public Zeit(int id, int aufgabe, Date anfang, Date beende) {
		this.id = id;
		this.aufgabe = aufgabe;
		this.anfang = anfang;
		this.beende = beende;
	}
	
	public Date getAnfang() {
		return anfang;
	}

	public void setAnfang(Date anfang) {
		this.anfang = anfang;
	}

	public Date getBeende() {
		return beende;
	}

	public void setBeende(Date beende) {
		this.beende = beende;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAufgabe() {
		return aufgabe;
	}

	public void setAufgabe(int aufgabe) {
		this.aufgabe = aufgabe;
	}

	public void dauer() {
		dauer.setTime(new Date(beende.getTime() - anfang.getTime()));
		
		tages = ((dauer.get(Calendar.DAY_OF_MONTH)-1)>9) ? ""+(dauer.get(Calendar.DAY_OF_MONTH)-1) : "0"+(dauer.get(Calendar.DAY_OF_MONTH)-1);
		stunde = ((dauer.get(Calendar.HOUR)-1)>9) ? ""+(dauer.get(Calendar.HOUR)-1) : "0"+(dauer.get(Calendar.HOUR)-1);
		minute = (dauer.get(Calendar.MINUTE)>9) ? ""+dauer.get(Calendar.MINUTE) : "0"+dauer.get(Calendar.MINUTE);
	}

	public String getDauerString() {
		dauer();
		return tages+"Tag "+stunde+":"+minute;
	}

	public Calendar getDauer() {
		dauer();
		return dauer;
	}
	
}
