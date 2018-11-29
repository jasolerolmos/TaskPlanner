package connection;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import model.Aufgabe;
import model.Zeit;

public class Export {
	private String file = "";
	private List<Aufgabe> liste = new ArrayList<Aufgabe>();
	private int gespeichert = 0; 
	
	public Export() {
	}
		 
	public Export(String file) {
		this.file = file;
	}
	
	public Export(String file, List<Aufgabe> l) {
		this.file = file;
		this.liste = l;
	}
	
	public void Save() {
		gespeichert = 0;
		if(!file.equals("") ) {
			String linie = "";
			try {
				FileWriter writer = new FileWriter(file);
				BufferedWriter buffer = new BufferedWriter(writer);
				SimpleDateFormat dtf = new SimpleDateFormat("dd.MM.yyy HH:mm:ss");
				
				for (Aufgabe a : liste) {
					linie = a.getAnwender().getId()+
							";"+a.getName()+
							";"+a.getProjekt().getId()+
							";"+a.getBeschreibungEncode();
					
					for (Zeit zeit : a.getZeitenliste()) {
						linie += ";"+dtf.format(zeit.getAnfang())+";"+dtf.format(zeit.getBeende());
					}
					linie += "\n";
					
					buffer.write(linie);
					gespeichert++;
				}
				buffer.close();
				writer.close();
			}
			catch (Exception e) {
				System.err.println("[Save] ERROR: "+e.getMessage());
			}
		}
		else {
			
		}
	}
	
	public void setListe(List<Aufgabe> l) {
		this.liste = l;
	}

	public int getGespeichert() {
		return gespeichert;
	}
	
}
