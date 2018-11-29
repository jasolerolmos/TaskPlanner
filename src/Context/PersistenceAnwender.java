package Context;

import java.util.List;

import connection.ConnectionManager;
import model.Anwender;

public class PersistenceAnwender {
	private ConnectionManager conex;
	private List<Anwender> list;

	public PersistenceAnwender() {
	}
	
	public PersistenceAnwender(ConnectionManager conex) {
		this.conex = conex;
	}
	
	public Anwender Load(int id) {
		return conex.getAnwender(id);
	}
	
	public Anwender Load(String vorname, String nachname) {
		return conex.getAnwender(vorname, nachname);
	}
	
	public List<Anwender> List(){
		list = conex.getAnwenderenList();
		return list;
	}
	
	public Anwender Load(String benutzername) {
		String vorname = benutzername.split("\\.")[0];
		String nachname = benutzername.split("\\.")[1];
		
		return conex.getAnwender(vorname, nachname);
	}	
	
	public boolean Login(String benutzername, String kennwort) {
		boolean login=false;

		String vorname = benutzername.split("\\.")[0];
		String nachname = benutzername.split("\\.")[1];
		
		Anwender a = Load(vorname, nachname);
		if(a.getKennwort().equals(kennwort)&&a.getId()>0) {
			login = true;
		}
		
		return login;
	}
	
	public boolean isAnwender(String vorname, String nachname) {
		boolean registriert = false;
		Anwender a = Load(vorname, nachname);
		
		if(a.getId()!=0)
			registriert = true;
		
		return registriert;
	}
	
	public void Save(Anwender a) {
		if(isAnwender(a.getVorname(), a.getNachname()))
			conex.updateAnwender(a);
		else
			conex.addAnwender(a);
	}
}
