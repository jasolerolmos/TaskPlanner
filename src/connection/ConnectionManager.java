package connection;

import java.util.List;

import model.Anwender;
import model.Aufgabe;
import model.Projekt;
import model.Zeit;

public interface ConnectionManager {
	public abstract List<Aufgabe> getAufgabenliste();
	public abstract List<Aufgabe> getAufgabenliste(Anwender anwender);
	public abstract List<Aufgabe> getAufgabenliste(Projekt projekt);
	public abstract List<Anwender> getAnwenderenList();
	
	public abstract void setAufgabenliste(List<Aufgabe> liste);
	
	public abstract Anwender getAnwender(String vorname, String nachname);
	public abstract Anwender getAnwender(int id);
	public abstract boolean addAnwender(String vorname, String nachname);
	public abstract boolean addAnwender(Anwender neueAnwender);
	public abstract boolean updateAnwender(Anwender a);	
	
	public abstract Projekt getProjekt(String name);
	public abstract Projekt getProjekt(int id);
	public abstract boolean addProjekt(String name);
	public abstract boolean addProjekt(Projekt neueProjekt);
	public boolean updateProjekt(Projekt projekt);
	public abstract List<Projekt> getProjektenListe();
	
	public abstract Aufgabe getAufgabe(int id);
	public abstract Aufgabe getAufgabe(String name, int anwender, int projekt);
	public abstract boolean addAufgabe(Aufgabe aufgabe);
	public abstract boolean updateAufgabe(Aufgabe aufgabe);
	
	public abstract Zeit getZeit(int id);
	public abstract List<Zeit> getZeitenListeAnwender(int anwender);
	public abstract List<Zeit> getZeitenListeAufgabe(int aufgabe);
	public abstract void writeListe(List<Projekt> list);

	public String getAufgabenDatei();

	public void setAufgabenDatei(String aufgabenDatei) ;


	public String getAufgabenProjekten() ;


	public void setAufgabenProjekten(String aufgabenProjekten) ;


	public String getAufgabenAnwender();


	public void setAufgabenAnwender(String aufgabenAnwender) ;


}

