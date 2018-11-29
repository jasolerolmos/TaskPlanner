package Context;

import connection.ConnectionManagerMySQL;
import model.Anwender;
import model.Zeit;

public class PersistenceZeit {
	private ConnectionManagerMySQL conex;

	public PersistenceZeit() {
	}
	
	public PersistenceZeit(ConnectionManagerMySQL conex) {
		this.conex = conex;
	}
	
	public Anwender Load(int id) {
		return conex.getAnwender(id);
	}
	
	public Anwender Load(String vorname, String nachname) {
		return conex.getAnwender(vorname, nachname);
	}
	
	public boolean isFreiZeit(Zeit zeit) {
		boolean registriert = false;
		// TODO: Freie- oder beschäftigen Zeit 
		
		return registriert;
	}
	
	public void Save(Zeit zeit) {
//		if(isFreiZeit(zeit))
//			conex.updateZeit(zeit);
//		else
//			conex.addZeit(zeit);
	}
}
