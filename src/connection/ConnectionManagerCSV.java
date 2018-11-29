package connection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import model.Anwender;
import model.Aufgabe;
import model.Projekt;
import model.Zeit;

public class ConnectionManagerCSV implements ConnectionManager{
	private String aufgabenDatei = "", aufgabenProjekten = "", aufgabenAnwender = "";
	private FileReader leser;
	private BufferedReader buffer;
	private List<Aufgabe> aufgabenliste = new ArrayList<Aufgabe>();
	private List<Anwender> anwendernliste = new ArrayList<Anwender>(); 
	private List<Projekt> projektliste = new ArrayList<Projekt>();
	int id = 0;
	
	public ConnectionManagerCSV() {
		aufgabenDatei = "aufgaben.csv";
		aufgabenProjekten = "projekten.csv";
		aufgabenAnwender = "anwender.csv";
	}
	
	public String getAufgabenQuelle() {
		return aufgabenDatei;
	}

	public void setAufgabenQuelle(String aufgabenDatei) {
		this.aufgabenDatei = aufgabenDatei;
	}
	
	public Aufgabe getAufgabeVonLinie(String linie) {
		String[] daten = linie.split(";");
		Aufgabe neue = new Aufgabe();
		neue.setId(id);
		neue.setAnwender(getAnwender(Integer.parseInt(daten[0])));
		neue.setName(daten[1]);
		neue.setProjekt(getProjekt(Integer.parseInt(daten[2])));
		String beschreibungEncode = "";
		try {
			
			if(daten.length<4)
				beschreibungEncode = "";
			else
				beschreibungEncode = URLDecoder.decode(daten[3], "UTF-8");
		}
		catch (UnsupportedEncodingException  e) {
			System.err.println("UnsupportedEncodingException");
		}
		
		neue.setBeschreibung(beschreibungEncode);
		try {
			Date anfang;  
			Date beende;
			if(daten.length>3) {
				for(int i=4;i<daten.length; i+=2) {
					anfang=new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse(daten[i]);  
					beende=new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse(daten[(i+1)]);
					neue.addZeit(anfang, beende);
				}
			}
		}
		catch (ParseException e) {
			System.err.println("Date falsch.");
		}

		return neue;
	}

	public void setAufgabenliste(List<Aufgabe> aufgabenliste) {
		this.aufgabenliste = aufgabenliste;
	}

	public List<Aufgabe> getAufgabenliste() {
		if(!aufgabenDatei.isEmpty()) {
			aufgabenliste.clear();
			try {
				leser = new FileReader(aufgabenDatei);
				buffer = new BufferedReader(leser);
				String linie = ""; 
				while( (linie = buffer.readLine())!= null ) {
					if(linie.split(";").length>=3) {
						aufgabenliste.add(getAufgabeVonLinie(linie));
					}
				}
				buffer.close();
			}
			catch (IOException e) {
				System.err.println("[CSV] Error: "+e.getMessage());
			}
		}
		return aufgabenliste;
	}
	
	@Override
	public List<Aufgabe> getAufgabenliste(Anwender anwender) {
		id = 0;
		if(!aufgabenDatei.isEmpty()) {
			aufgabenliste.clear();
			try {
				leser = new FileReader(aufgabenDatei);
				buffer = new BufferedReader(leser);
				String linie = "";
				while( (linie = buffer.readLine())!= null ) {
					if(linie.split(";").length>=3) {
						Anwender a = getAnwender(Integer.parseInt(linie.split(";")[0]));
						if(a!= null && a.getId()==anwender.getId()){
							id++;
							aufgabenliste.add(getAufgabeVonLinie(linie));
						}
					}
				}
				buffer.close();
			}
			catch (IOException e) {
				System.err.println("[CSV] Error: "+e.getMessage());
			}
		}
		return aufgabenliste;
	}
	
	@Override
	public List<Aufgabe> getAufgabenliste(Projekt projekt) {
		if(!aufgabenDatei.isEmpty()) {
			aufgabenliste.clear();
			try {
				leser = new FileReader(aufgabenDatei);
				buffer = new BufferedReader(leser);
				String linie = ""; 
				while( (linie = buffer.readLine())!= null ) {
					if(linie.split(";").length>=6) {
						if(getProjekt(linie.split(";")[2]).getId()==projekt.getId()){
							aufgabenliste.add(getAufgabeVonLinie(linie));	
						}
					}
				}
				buffer.close();
			}
			catch (IOException e) {
				System.err.println("[CSV] Error: "+e.getMessage());
			}
		}
		return aufgabenliste;
	}

	@Override
	public List<Anwender> getAnwenderenList() {
		anwendernliste.clear();
		if(!aufgabenProjekten.isEmpty()) {
			try {
				FileReader leser = new FileReader(aufgabenAnwender);
				BufferedReader buffer = new BufferedReader(leser);
				String linie = ""; 
				while( (linie = buffer.readLine())!= null ) {
					if(linie.split(";").length>=2) {
						try {
							anwendernliste.add(new Anwender(Integer.parseInt(linie.split(";")[0]),linie.split(";")[1], linie.split(";")[2]));
						}
						catch(NumberFormatException e){
							System.err.println("[CSV] Error: "+e.getMessage());
						}
					}	
				}
				buffer.close();
				leser.close();
			}
			catch (IOException e) {
				System.err.println("[CSV] Error: "+e.getMessage());
			}
		}
		return anwendernliste;
	}

	public List<Projekt> getProjektenListe(){
		projektliste.clear();
		Projekt p = new Projekt();
		if(!aufgabenProjekten.isEmpty()) {
			try {
				FileReader leser = new FileReader(aufgabenProjekten);
				BufferedReader buffer = new BufferedReader(leser);
				String linie = ""; 
				while( (linie = buffer.readLine())!= null ) {
					if(linie.split(";").length>=2) {
						try {
							p = new Projekt(Integer.parseInt(linie.split(";")[0]),linie.split(";")[1]);
							projektliste.add(p);
						}
						catch(NumberFormatException e){
							System.err.println("[CSV] Error: "+e.getMessage());
						}
					}	
				}
				buffer.close();
				leser.close();
			}
			catch (IOException e) {
				System.err.println("[CSV] Error: "+e.getMessage());
			}
		}
		return projektliste;
	}
	
	@Override
	public Anwender getAnwender(String vorname, String nachname) {
		Anwender a = new Anwender();
		if(!aufgabenProjekten.isEmpty()) {
			try {
				FileReader leser = new FileReader(aufgabenAnwender);
				BufferedReader buffer = new BufferedReader(leser);
				String linie = ""; 
				while( (linie = buffer.readLine())!= null ) {
					if(linie.split(";").length>=2) {
						if(linie.split(";")[1].equals(vorname) && linie.split(";")[2].equals(nachname)) {
							try {
								a = new Anwender(Integer.parseInt(linie.split(";")[0]),vorname, nachname, linie.split(";")[3]);
							}
							catch(NumberFormatException e){
								System.err.println("[CSV] Error: "+e.getMessage());
							}
						}
					}	
				}
				buffer.close();
				leser.close();
			}
			catch (IOException e) {
				System.err.println("[CSV] Error: "+e.getMessage());
			}
		}
		return a;
	}

	@Override
	public Anwender getAnwender(int id) {
		Anwender a = new Anwender();
		if(!aufgabenProjekten.isEmpty()) {
			try {
				FileReader leser = new FileReader(aufgabenAnwender);
				BufferedReader buffer = new BufferedReader(leser);
				String linie = ""; 
				while( (linie = buffer.readLine())!= null ) {
					if(linie.split(";").length>=2) {
						if(Integer.parseInt(linie.split(";")[0]) == id) {
							try {
								a = new Anwender(Integer.parseInt(linie.split(";")[0]),linie.split(";")[1], linie.split(";")[2], linie.split(";")[3]);
							}
							catch(NumberFormatException e){
								System.err.println("[CSV] Error: "+e.getMessage());
							}
						}
					}	
				}
				buffer.close();
				leser.close();
			}
			catch (IOException e) {
				System.err.println("[CSV] Error: "+e.getMessage());
			}
		}
		return a;
	}

	@Override
	public boolean addAnwender(String vorname, String nachname) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAnwender(Anwender neueAnwender) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean updateAnwender(Anwender anwender) {
		return false;
	}
	
	@Override
	public Projekt getProjekt(String name) {
		Projekt p = new Projekt();
		if(!aufgabenProjekten.isEmpty()) {
			try {
				FileReader leser = new FileReader(aufgabenProjekten);
				BufferedReader buffer = new BufferedReader(leser);
				String linie = ""; 
				while( (linie = buffer.readLine())!= null ) {
					if(linie.split(";").length>=2) {
						if(linie.split(";")[1].equals(name)) {
							try {
								p = new Projekt(Integer.parseInt(linie.split(";")[0]),name);
							}
							catch(NumberFormatException e){
								System.err.println("[CSV] Error: "+e.getMessage());
							}
						}
					}	
				}
				buffer.close();
				leser.close();
			}
			catch (IOException e) {
				System.err.println("[CSV] Error: "+e.getMessage());
			}
		}
		return p;
	}

	@Override
	public Projekt getProjekt(int id) {
		Projekt p = new Projekt();
		if(!aufgabenProjekten.isEmpty()) {
			try {
				FileReader leser = new FileReader(aufgabenProjekten);
				BufferedReader buffer = new BufferedReader(leser);
				String linie = ""; 
				while( (linie = buffer.readLine())!= null ) {
					if(linie.split(";").length>=2) {
						if(Integer.parseInt(linie.split(";")[0]) == id) {
							try {
								p = new Projekt(Integer.parseInt(linie.split(";")[0]),linie.split(";")[1]);
							}
							catch(NumberFormatException e){
								System.err.println("[CSV] Error: "+e.getMessage());
							}
						}
					}	
				}
				buffer.close();
				leser.close();
			}
			catch (IOException e) {
				System.err.println("[CSV] Error: "+e.getMessage());
			}
		}
		return p;
	}

	@Override
	public boolean addProjekt(String name) {
		// TODO Auto-generated method stub
		return false;
		
	}

	@Override
	public boolean addProjekt(Projekt neueProjekt) {		
		try {
			FileWriter writer = new FileWriter(aufgabenProjekten, true);
			BufferedWriter buffer = new BufferedWriter(writer);
			
			int nuevo = projektliste.stream().max(Comparator.comparing(p -> p.getId())).get().getId()+1;
			
			buffer.write(nuevo+";"+neueProjekt.getName()+"\n");			
			buffer.close();
			writer.close();
		}
		catch (IOException e) {
			System.err.println("[CSV] Error: "+e.getMessage());
		}
		
		return false;
		
	}

	public boolean updateProjekt(Projekt projekt) {
		String linie = "";
		try {
			FileWriter file = new FileWriter(aufgabenProjekten);
			BufferedWriter buffer = new BufferedWriter(file);
			for (Projekt p : projektliste) {
				if(p.getId()==projekt.getId()) {
					linie = projekt.getId()+";"+projekt.getName()+"\n";
				}
				else {
					linie = p.getId()+";"+p.getName()+"\n";
				}
				buffer.write(linie);
			}
			buffer.close();
			file.close();
		}
		catch (Exception e) {
			// TODO: handle exception
		}

		
		return false;
	}
	
	public void writeListe(List<Projekt> l) {
		String linie = "";
		try {
			FileWriter file = new FileWriter(aufgabenProjekten);
			BufferedWriter buffer = new BufferedWriter(file);
			
			for (Projekt p : l) {
				
				linie = p.getId()+";"+p.getName();
				linie += "\n";
				
				buffer.write(linie);
			}
			buffer.close();
			file.close();
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}

	
	@Override
	public Aufgabe getAufgabe(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Aufgabe getAufgabe(String name, int anwenderid, int projektid) {
		Anwender anwender = getAnwender(anwenderid);
		Aufgabe aufgabe = new Aufgabe();
		if(anwender.getId()!=0) {
			List<Aufgabe> liste = getAufgabenliste(anwender);
			for (Aufgabe aufgabeaux : liste) {
				if(aufgabeaux.getName().equals(name) && aufgabeaux.getProjekt().getId()==projektid) {
					aufgabe = aufgabeaux;
				}
				else {
				}
			}
		}
		else {
		}
			
		return aufgabe;
	}

	@Override
	public boolean addAufgabe(Aufgabe aufgabe) {
		LocalDateTime anfang = LocalDateTime.now().plusDays(1);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyy HH:mm:ss");
		String neueAufgabe = aufgabe.getAnwender().getId()+
				";"+aufgabe.getName()+
				";"+aufgabe.getProjekt().getId()+
				";"+aufgabe.getBeschreibungEncode()+
				";"+dtf.format(anfang)+";"+dtf.format(anfang)+"\n";
		
		try {
			FileWriter writer = new FileWriter(aufgabenDatei, true);
			BufferedWriter buffer = new BufferedWriter(writer);
			buffer.write(neueAufgabe);			
			buffer.close();
			writer.close();
		}
		catch (IOException e) {
			System.err.println("[CSV] Error: "+e.getMessage());
		}
		
		return false;
	}

	@Override
	public boolean updateAufgabe(Aufgabe aufgabe) {
		
		String linie = "";
		try {
			FileWriter file = new FileWriter(aufgabenDatei);
			BufferedWriter buffer = new BufferedWriter(file);
			SimpleDateFormat dtf = new SimpleDateFormat("dd.MM.yyy HH:mm:ss");
			for (Aufgabe a : aufgabenliste) {
				linie = a.getAnwender().getId()+
						";"+a.getName()+
						";"+a.getProjekt().getId()+
						";"+a.getBeschreibungEncode()+";";

				for (Zeit zeit : a.getZeitenliste()) {
					linie += dtf.format(zeit.getAnfang())+";"+dtf.format(zeit.getBeende())+";";
				}
				if(a.getZeitenliste().size()>0 && linie.endsWith(";"))
					linie = linie.substring(0, linie.length()-1);
				linie += "\n";
				
				buffer.write(linie);
			}
			buffer.close();
			file.close();
		}
		catch (Exception e) {
			System.err.println("[CM-CSV] "+e.getMessage());
		}

		
		return false;
	}

	

	@Override
	public Zeit getZeit(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Zeit> getZeitenListeAnwender(int anwender) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Zeit> getZeitenListeAufgabe(int aufgabe) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getAufgabenDatei() {
		return aufgabenDatei;
	}

	public void setAufgabenDatei(String aufgabenDatei) {
		this.aufgabenDatei = aufgabenDatei;
	}

	public String getAufgabenProjekten() {
		return aufgabenProjekten;
	}

	public void setAufgabenProjekten(String aufgabenProjekten) {
		this.aufgabenProjekten = aufgabenProjekten;
	}

	public String getAufgabenAnwender() {
		return aufgabenAnwender;
	}

	public void setAufgabenAnwender(String aufgabenAnwender) {
		this.aufgabenAnwender = aufgabenAnwender;
	}


	
	
}
