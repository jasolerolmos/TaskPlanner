package Context;

import connection.ConnectionManager;
import model.Anwender;
import model.Aufgabe;
import java.util.List;

public class PersistenceAufgabe {
	private ConnectionManager conex;
	private List<Aufgabe> liste = null;
	
	public PersistenceAufgabe() {
//		liste = conex.getAufgabenliste(anwender);
	}
	
	public PersistenceAufgabe(ConnectionManager conex) {
		this.conex = conex;
	}
	
	public Aufgabe Load(int id) {
		return conex.getAufgabe(id);
	}

	public Aufgabe Load(String name, int anwender, int projekt) {
		return conex.getAufgabe(name, anwender, projekt);
	}
	
	public boolean Exist(Aufgabe aufgabe) {
		boolean registriert = false;
//		Aufgabe aux = Load(aufgabe.getName(), aufgabe.getAnwender().getId(), aufgabe.getProjekt().getId());
		
		if(liste.contains(aufgabe))
			registriert = true;
		
		return registriert;
	}
	
	public void Save(Aufgabe aufgabe, int index) {
		if(index>=0) {
			liste.remove(index);
			liste.add(index, aufgabe);
			//liste.set(index, aufgabe);
			conex.setAufgabenliste(liste);
			conex.updateAufgabe(aufgabe);
		}
		else {
			liste.add(aufgabe);
			conex.addAufgabe(aufgabe);
		}
	}
	
	public List<Aufgabe> List(Anwender anwender){		
		liste = conex.getAufgabenliste(anwender);
		return liste;
	}
	
	public void Remove(Aufgabe aufgabe) {
		liste.remove(aufgabe);
		conex.setAufgabenliste(liste);
		conex.updateAufgabe(aufgabe);
	}

	
	public void setListe(List<Aufgabe> liste) {
		this.liste = liste;
	}
}
