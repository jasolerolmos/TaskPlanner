package Context;

import connection.ConnectionManager;
import model.Projekt;

import java.util.List;

public class PersistenceProjekt {
	private ConnectionManager conex;
	private List<Projekt> list;

	public PersistenceProjekt() {
		list = conex.getProjektenListe();
	}
	
	public PersistenceProjekt(ConnectionManager conex) {
		this.conex = conex;
		list = conex.getProjektenListe();
	}
	
	public Projekt Load(int id) {
		return conex.getProjekt(id);
	}
	
	public Projekt Load(String name) {
		return conex.getProjekt(name);
	}

	public boolean isProjekt(String name) {
		boolean registriert = false;
		Projekt projekt = Load(name);
		
		if(projekt.getId()!=0)
			registriert = true;
		
		return registriert;
	}
	
	public void Save(Projekt projekt) {
		if(projekt.getId()>0) {
			conex.updateProjekt(projekt);
		}
		else {
			list.add(projekt);
			conex.addProjekt(projekt);
		}
	}
	
	public List<Projekt> List() {
		return list;
	}
	
	public void reloadList() {
		list = conex.getProjektenListe();
	}
	
	public void Remove(Projekt projekt) {
		list.remove(projekt);
		conex.writeListe(list);
	}
	
}
