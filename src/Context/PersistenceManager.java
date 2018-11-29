package Context;

import connection.ConnectionManager;
import connection.ConnectionManagerCSV;
//import connection.ConnectionManagerMySQL;

public class PersistenceManager {
	private PersistenceAnwender Anwender;
	private PersistenceProjekt Projekt;
	private PersistenceAufgabe Aufgabe;
	private ConnectionManager connection;
	
	public PersistenceManager() {
		connection = new ConnectionManagerCSV();
		Anwender = new PersistenceAnwender(connection);
		Projekt = new PersistenceProjekt(connection);
		Aufgabe = new PersistenceAufgabe(connection);
	}

	public PersistenceAnwender getAnwenderPersistence() {
		return Anwender;
	}
	
	public PersistenceProjekt getProjektPersistence() {
		return Projekt;
	}
	
	public PersistenceAufgabe getAufgabePersistence() {
		return Aufgabe;
	}
	
	public void setSource(String source) {
		connection.setAufgabenDatei(source);
	}
	
	public String getSource() {
		return connection.getAufgabenDatei();
	}
}
