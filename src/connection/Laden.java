package connection;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Anwender;
import model.Aufgabe;
import model.Projekt;

public class Laden {
	private String file = "";
	private List<Aufgabe> liste = new ArrayList<Aufgabe>();
	private List<Anwender> anwendernliste = new ArrayList<Anwender>(); 
	private List<Projekt> projektliste = new ArrayList<Projekt>();
	private int gelesen = 0;
	private int benutzerID = 0;

	public Laden() {

	}
	 
	public Laden(String file) {
		this.file = file;
	}
	
	public List<Aufgabe> getAufgaben() {
		gelesen=0;
		if(!file.isEmpty()) {
			liste.clear();
			try {
				FileReader leser = new FileReader(file);
				BufferedReader buffer = new BufferedReader(leser);
				String linie = ""; 
				while( (linie = buffer.readLine())!= null ) {
					if(linie.split(";").length>=6) {
						try {
							Aufgabe aux = getAufgabeVonLinie(linie, gelesen);
							if(aux.getId()!=0 && aux.getAnwender().getId()==benutzerID) {
								liste.add(aux);
								gelesen++;
							}
							else{
//								System.err.println("ERROR => "+aux.getAnwender().getId()+" -> "+benutzerID+"\n"+linie);
							}
						}
						catch (ParseException e) {
							System.err.println("[ParseException] "+e.getMessage());
						}
						catch (NumberFormatException e) {
							System.err.println("[NumberFormatException] "+e.getMessage());
						}
						
					}
				}
				buffer.close();
			}
			catch (IOException e) {
				gelesen--;
				System.err.println("[CSV] Error: "+e.getMessage());
			}
		}
		return liste;
	}
	
	public Aufgabe getAufgabeVonLinie(String linie, int id) throws ParseException, NumberFormatException{
		Aufgabe neue = new Aufgabe();

		String[] daten = linie.split(";");
		neue.setId(++id);
		neue.setName(daten[1]);
		neue.setAnwender(loadAnwender(Integer.parseInt(daten[0])));
		neue.setProjekt(loadProjekt(Integer.parseInt(daten[2])));
		String beschreibungEncode = "";
		try {
			beschreibungEncode = URLDecoder.decode(daten[3], "UTF-8");
		}
		catch (UnsupportedEncodingException  e) {
			System.err.println("UnsupportedEncodingException");
		}
		
		neue.setBeschreibung(beschreibungEncode);
		Date anfang;  
		Date beende;
		if(daten.length>7) {
			for(int i=4;i<daten.length; i+=2) {
				anfang=new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse(daten[i]);  
				beende=new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse(daten[(i+1)]);
				neue.addZeit(anfang, beende);
			}
		}
		else {
			anfang = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse(daten[4]);
			beende = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse(daten[5]);
			neue.addZeit(anfang, beende);
		}
		return neue;
	}

	public Anwender loadAnwender(int id) {
		for (Anwender anwender : anwendernliste) {
			if(anwender.getId()==id)
				return anwender;
		}
		return new Anwender();
	}
	
	public Projekt loadProjekt(int id) {
		for (Projekt projekt : projektliste) {
			if(projekt.getId()==id)
				return projekt;
		}
		return new Projekt();
	}
	
	public List<Anwender> getAnwendernliste() {
		return anwendernliste;
	}

	public void setAnwendernliste(List<Anwender> anwendernliste) {
		this.anwendernliste = anwendernliste;
	}

	public List<Projekt> getProjektliste() {
		return projektliste;
	}

	public void setProjektliste(List<Projekt> projektliste) {
		this.projektliste = projektliste;
	}

	public int getGelesen() {
		return gelesen;
	}

	public void setGelesen(int gelesen) {
		this.gelesen = gelesen;
	}

	public int getBenutzerID() {
		return benutzerID;
	}

	public void setBenutzerID(int benutzerID) {
		this.benutzerID = benutzerID;
	}
	
	
}
