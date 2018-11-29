package model;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Aufgabe {
	private int Id=0;
	private String name="";
	private Anwender anwender;
	private String beschreibung = "", beschreibungEncode = "";
	private Projekt projekt = new Projekt();
	private List<Zeit> zeitenliste = new ArrayList<Zeit>();
	private Calendar dauer;
	
	public Aufgabe() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Anwender getAnwender() {
		return anwender;
	}

	public void setAnwender(Anwender anwender) {
		this.anwender = anwender;
	}

	public String getBeschreibung() {
		return beschreibung;
	}
	
	public String getBeschreibungEncode() {	
		return beschreibungEncode;
	}

	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
		try {
			this.beschreibungEncode = URLEncoder.encode(beschreibung, "UTF-8");
		}
		catch (UnsupportedEncodingException  e) {
			System.err.println("UnsupportedEncodingException");
		}
	}

	public Projekt getProjekt() {
		return projekt;
	}

	public void setProjekt(Projekt projekt) {
		this.projekt = projekt;
	}

	public List<Zeit> getZeitenliste() {
		return zeitenliste;
	}

	public void setZeitenliste(List<Zeit> zeitenliste) {
		this.zeitenliste = zeitenliste;
	}

	public void addZeit(Date anfang, Date beende) {
		zeitenliste.add(new Zeit(anfang, beende));
	}
	
	public String getDauer() {
		String ausgabe = "";
		dauer = Calendar.getInstance();
		dauer.set(2018, 1,1,0,0);

		for (Zeit zeit : zeitenliste) {
//			Calendar cal = zeit.getDauer();
			
//			int tag = zeit.getDauer().get(Calendar.DAY_OF_MONTH)-1;
//			int stunden = zeit.getDauer().get(Calendar.HOUR)-1;
//			int min = zeit.getDauer().get(Calendar.MINUTE);
			dauer.add(Calendar.DAY_OF_MONTH, zeit.getDauer().get(Calendar.DAY_OF_MONTH)-1);
			dauer.add(Calendar.HOUR, zeit.getDauer().get(Calendar.HOUR)-1);
			dauer.add(Calendar.MINUTE, zeit.getDauer().get(Calendar.MINUTE));
		}
		String tages = ((dauer.get(Calendar.DAY_OF_MONTH)-1)>9) ? ""+(dauer.get(Calendar.DAY_OF_MONTH)-1) : "0"+(dauer.get(Calendar.DAY_OF_MONTH)-1);
		String stunde = ((dauer.get(Calendar.HOUR))>9) ? ""+(dauer.get(Calendar.HOUR)) : "0"+(dauer.get(Calendar.HOUR));
		String minute = (dauer.get(Calendar.MINUTE)>9) ? ""+dauer.get(Calendar.MINUTE) : "0"+dauer.get(Calendar.MINUTE);
		ausgabe = tages+"T "+stunde+":"+minute+"";
		
		return ausgabe;
	}
}
