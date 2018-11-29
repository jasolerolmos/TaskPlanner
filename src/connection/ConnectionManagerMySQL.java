package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.util.ArrayList;
import java.util.List;

import model.Anwender;
import model.Aufgabe;
import model.Projekt;
import model.Zeit;

public class ConnectionManagerMySQL implements ConnectionManager{
	private DataConnection data;
	private boolean isConnected;
	private Connection conex;
	private String sql = "";
	private PreparedStatement stm;


	public ConnectionManagerMySQL() {
		data = new DataConnection();
		isConnected = false;
	}

	public ConnectionManagerMySQL(DataConnection data) {
		this.data = data;
		isConnected = false;
	}
	
	public boolean Connect() {
		try {
			conex = DriverManager.getConnection(data.getStringConnection(), data.getUser(), data.getPassword());
			isConnected = true;
		}
		catch (SQLException e) {
			isConnected = false;
		}
		return isConnected;
	}

	public boolean isConnected() {
		return isConnected;
	}

	//	Anwender
	
	@Override
	public Anwender getAnwender(String vorname, String nachname) {
		sql = "SELECT * FROM Anwender WHERE vorname=? AND nachname=?";
		Anwender a = new Anwender();
		try {
			stm = conex.prepareStatement(sql);
			stm.setString(1, vorname);
			stm.setString(2, nachname);
			ResultSet rs = stm.executeQuery();
			if(rs.next()) {
				a = new Anwender(rs.getInt(0), rs.getString(1), rs.getString(2));
			}
			rs.close();
		}
		catch (SQLException e) {
			System.err.println("[MYSQL Model] "+e.getMessage());
		}
		return a;
	}

	@Override
	public Anwender getAnwender(int id) {
		sql = "SELECT * FROM Anwender WHERE id=?";
		Anwender a = new Anwender();
		try {
			stm = conex.prepareStatement(sql);
			stm.setInt(1, id);
			ResultSet rs = stm.executeQuery();
			if(rs.next()) {
				a = new Anwender(rs.getInt(0), rs.getString(1), rs.getString(2));
			}
			rs.close();
		}
		catch (SQLException e) {
			System.err.println("[MYSQL Model] "+e.getMessage());
		}
		return a;
	}

	@Override
	public boolean addAnwender(String vorname, String nachname) {
		sql = "INSERT INTO Anwender (vorname, nachname, kennwort) VALUES (?,?,?)";
		boolean erfolg=false;
		try {
			stm = conex.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
			stm.setString(1,vorname);
			stm.setString(2, nachname);
			stm.setString(3, "123123123");
			
			stm.executeUpdate();
			ResultSet rs = stm.getGeneratedKeys();
			if(rs.next())
				erfolg = true;
		}
		catch (SQLTimeoutException e) {
			System.err.println("[MYSQL Model] "+e.getMessage());
		}
		catch (SQLException e) {
			System.err.println("[MYSQL Model] "+e.getMessage());
		}
		return erfolg;
	}

	@Override
	public boolean addAnwender(Anwender neueAnwender) {
		return addAnwender(neueAnwender.getNachname(), neueAnwender.getNachname());
	}

	public boolean updateAnwender(Anwender anwender) {
		sql = "UPDATE Anwender SET vorname=?, nachname=?, kennwort=? WHERE id=?";
		boolean erfolg=false;
		try {
			stm = conex.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
			stm.setString(1, anwender.getVorname());
			stm.setString(2, anwender.getNachname());
			stm.setString(3, anwender.getKennwort());
			stm.setInt(4, anwender.getId());
			
			stm.executeUpdate();
			ResultSet rs = stm.getGeneratedKeys();
			if(rs.next())
				erfolg = true;
		}
		catch (SQLTimeoutException e) {
			System.err.println("[MYSQL Model] "+e.getMessage());
		}
		catch (SQLException e) {
			System.err.println("[MYSQL Model] "+e.getMessage());
		}
		
		return erfolg;
	}
	
	@Override
	public List<Anwender> getAnwenderenList() {
		List<Anwender> liste = new ArrayList<Anwender>();
		sql = "SELECT * FROM Anwender";
		try {
			stm = conex.prepareStatement(sql);
			ResultSet rs = stm.executeQuery();
			while( rs.next()) {
				liste.add(new Anwender(rs.getInt(0), rs.getString(1), rs.getString(2)));
			}
		}
		catch (SQLTimeoutException e) {
			System.err.println("[MYSQL Model] "+e.getMessage());
		}
		catch (SQLException e) {
			System.err.println("[MYSQL Model] "+e.getMessage());
		}
		return liste;
	}

	// Zeit
	
	@Override
	public Zeit getZeit(int id) {
		sql = "SELECT * FROM Zeit WHERE Id=?";
		Zeit p = null;
		ResultSet rs = null;
		try {
			stm = conex.prepareStatement(sql);
			stm.setInt(1, id);
			rs = stm.executeQuery();
			if(rs.next()) {
				p = new Zeit(rs.getInt(0), rs.getDate(2), rs.getDate(3));
			}
			rs.close();
		}
		catch (SQLException e) {
			System.err.println("[MYSQL Model] "+e.getMessage());
		}
		return p;
	}

	@Override
	public List<Zeit> getZeitenListeAufgabe(int aufgabe) {
		List<Zeit> liste = new ArrayList<Zeit>();
		sql = "SELECT * FROM Zeit WHERE Aufgabe=?";
		try {
			stm = conex.prepareStatement(sql);
			stm.setInt(1, aufgabe);
			ResultSet rs = stm.executeQuery();
			while( rs.next()) {
				liste.add(new Zeit(rs.getInt(0), rs.getInt(1), rs.getDate(2), rs.getDate(3)));
			}
		}
		catch (SQLTimeoutException e) {
			System.err.println("[MYSQL Model] "+e.getMessage());
		}
		catch (SQLException e) {
			System.err.println("[MYSQL Model] "+e.getMessage());
		}
		return null;
	}

	@Override
	public List<Zeit> getZeitenListeAnwender(int anwender) {
		List<Zeit> liste = new ArrayList<Zeit>();
		// TODO:
		sql = "SELECT * FROM Zeit JOIN Anwender ON Anwender.id = WHERE Aufgabe=?";
		try {
			stm = conex.prepareStatement(sql);
			stm.setInt(1, anwender);
			ResultSet rs = stm.executeQuery();
			while( rs.next()) {
				liste.add(new Zeit(rs.getInt(0), rs.getInt(1), rs.getDate(2), rs.getDate(3)));
			}
		}
		catch (SQLTimeoutException e) {
			System.err.println("[MYSQL Model] "+e.getMessage());
		}
		catch (SQLException e) {
			System.err.println("[MYSQL Model] "+e.getMessage());
		}
		return liste;
	}

	// Projekt
	
	@Override
	public Projekt getProjekt(String name) {
		sql = "SELECT * FROM Projekt WHERE name=?";
		Projekt p = new Projekt();
		ResultSet rs = null;
		try {
			stm = conex.prepareStatement(sql);
			stm.setString(1, name);
			rs = stm.executeQuery();
			if(rs.next()) {
				p = new Projekt(rs.getInt(0), rs.getString(1));
			}
			rs.close();
		}
		catch (SQLException e) {
			System.err.println("[MYSQL Model] "+e.getMessage());
		}
		return p;
	}

	@Override
	public Projekt getProjekt(int id) {
		sql = "SELECT * FROM Projekt WHERE Id=?";
		Projekt p = new Projekt();
		ResultSet rs = null;
		try {
			stm = conex.prepareStatement(sql);
			stm.setInt(1, id);
			rs = stm.executeQuery();
			if(rs.next()) {
				p = new Projekt(rs.getInt(0), rs.getString(1));
			}
			rs.close();
		}
		catch (SQLException e) {
			System.err.println("[MYSQL Model] "+e.getMessage());
		}
		return p;
	}

	@Override
	public boolean addProjekt(String name) {
		sql = "INSERT INTO Projekt (name) VALUES (?)";
		boolean erfolg=false;
		try {
			stm = conex.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
			stm.setString(1,name);
			
			stm.executeUpdate();
			ResultSet rs = stm.getGeneratedKeys();
			if(rs.next())
				erfolg = true;
		}
		catch (SQLTimeoutException e) {
			System.err.println("[MYSQL Model] "+e.getMessage());
		}
		catch (SQLException e) {
			System.err.println("[MYSQL Model] "+e.getMessage());
		}
		return erfolg;
	}

	@Override
	public boolean addProjekt(Projekt neueProjekt) {
		return addProjekt(neueProjekt.getName());
		
	}

	public boolean updateProjekt(Projekt projekt) {
		sql = "UPDATE Projekt SET name=? WHERE id=?";
		boolean erfolg=false;
		try {
			stm = conex.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
			stm.setString(1, projekt.getName());
			stm.setInt(2, projekt.getId());
			
			stm.executeUpdate();
			ResultSet rs = stm.getGeneratedKeys();
			if(rs.next())
				erfolg = true;
		}
		catch (SQLTimeoutException e) {
			System.err.println("[MYSQL Model] "+e.getMessage());
		}
		catch (SQLException e) {
			System.err.println("[MYSQL Model] "+e.getMessage());
		}
		
		return erfolg;
	}
	
	public List<Projekt> getProjektenListe(){
		return null;
	}
	
	// Aufgabe
	
	@Override
	public List<Aufgabe> getAufgabenliste() {
		sql = "SELECT * FROM Aufgabe";
		List<Aufgabe> liste = new ArrayList<Aufgabe>();
		
		try {
			stm = conex.prepareStatement(sql);
			ResultSet rs = stm.executeQuery();
			if(rs.next()) {
				Aufgabe a = new Aufgabe();
				a.setAnwender(getAnwender(rs.getInt(3)));
				a.setName(rs.getString(1));
				a.setProjekt(getProjekt(rs.getInt(4)));
				a.setBeschreibung(rs.getString(2));
				liste.add(a);
			}
			rs.close();
		}
		catch (SQLException e) {
			System.err.println("[MYSQL Model] "+e.getMessage());
		}
		return liste;
	}
	
	@Override
	public List<Aufgabe> getAufgabenliste(Anwender anwender) {
		sql = "SELECT * FROM Aufgabe WHERE Anwender=?";
		List<Aufgabe> liste = new ArrayList<Aufgabe>();
		
		try {
			stm = conex.prepareStatement(sql);
			stm.setInt(1, anwender.getId());
			ResultSet rs = stm.executeQuery();
			while(rs.next()) {
				Aufgabe a = new Aufgabe();
				a.setAnwender(getAnwender(rs.getInt(3)));
				a.setName(rs.getString(1));
				a.setProjekt(getProjekt(rs.getInt(4)));
				a.setBeschreibung(rs.getString(2));
				liste.add(a);
			}
			rs.close();
		}
		catch (SQLException e) {
			System.err.println("[MYSQL Model] "+e.getMessage());
		}
		return liste;
	}

	@Override
	public List<Aufgabe> getAufgabenliste(Projekt projekt) {
		sql = "SELECT * FROM Aufgabe WHERE Projekt=?";
		List<Aufgabe> liste = new ArrayList<Aufgabe>();
		
		try {
			stm = conex.prepareStatement(sql);
			stm.setInt(1, projekt.getId());
			ResultSet rs = stm.executeQuery();
			while(rs.next()) {
				Aufgabe a = new Aufgabe();
				a.setAnwender(getAnwender(rs.getInt(3)));
				a.setName(rs.getString(1));
				a.setProjekt(getProjekt(rs.getInt(4)));
				a.setBeschreibung(rs.getString(2));
				liste.add(a);
			}
			rs.close();
		}
		catch (SQLException e) {
			System.err.println("[MYSQL Model] "+e.getMessage());
		}
		return liste;
	}
	
	@Override
	public Aufgabe getAufgabe(int id) {
		sql = "SELECT * FROM Aufgabe WHERE id=?";
		Aufgabe a = new Aufgabe();
		try {
			stm = conex.prepareStatement(sql);
			stm.setInt(1, id);
			ResultSet rs = stm.executeQuery();
			if(rs.next()) {
				a.setId(rs.getInt(0));
				a.setName(rs.getString(1));
				a.setBeschreibung(rs.getString(2));
				a.setAnwender(getAnwender(rs.getInt(3)));
				a.setProjekt(getProjekt(rs.getInt(4)));
			}
			rs.close();
		}
		catch (SQLException e) {
			System.err.println("[MYSQL Model] "+e.getMessage());
		}
		return a;
	}
	
	@Override
	public Aufgabe getAufgabe(String name, int anwender, int projekt) {
		sql = "SELECT * FROM Aufgabe WHERE name=? AND anwender=? AND projekt=?";
		Aufgabe a = new Aufgabe();
		try {
			stm = conex.prepareStatement(sql);
			stm.setString(1, name);
			stm.setInt(2, anwender);
			stm.setInt(3, projekt);
			ResultSet rs = stm.executeQuery();
			if(rs.next()) {
				a.setId(rs.getInt(0));
				a.setName(rs.getString(1));
				a.setBeschreibung(rs.getString(2));
				a.setAnwender(getAnwender(rs.getInt(3)));
				a.setProjekt(getProjekt(rs.getInt(4)));
			}
			rs.close();
		}
		catch (SQLException e) {
			System.err.println("[MYSQL Model] "+e.getMessage());
		}
		return a;
	}
	
	@Override
	public boolean addAufgabe(Aufgabe aufgabe) {
		sql = "INSERT INTO Aufgabe (name, beschreibung, anwender, projekt) VALUES (?,?,?,?)";
		boolean erfolg=false;
		try {
			stm = conex.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
			stm.setString(1, aufgabe.getName());
			stm.setString(2, aufgabe.getBeschreibung());
			stm.setInt(3, aufgabe.getAnwender().getId());
			stm.setInt(4, aufgabe.getProjekt().getId());
			
			stm.executeUpdate();
			ResultSet rs = stm.getGeneratedKeys();
			if(rs.next())
				erfolg = true;
		}
		catch (SQLTimeoutException e) {
			System.err.println("[MYSQL Model] "+e.getMessage());
		}
		catch (SQLException e) {
			System.err.println("[MYSQL Model] "+e.getMessage());
		}
		return erfolg;
	}

	@Override
	public boolean updateAufgabe(Aufgabe aufgabe) {
		sql = "UPDATE Aufgabe SET name=?, beschreibung=?, anwender=?, projekt=? WHERE id=?";
		boolean erfolg=false;
		try {
			stm = conex.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
			stm.setString(1, aufgabe.getName());
			stm.setString(2, aufgabe.getBeschreibung());
			stm.setInt(3, aufgabe.getAnwender().getId());
			stm.setInt(3, aufgabe.getProjekt().getId());
			stm.setInt(4, aufgabe.getId());
			
			stm.executeUpdate();
			ResultSet rs = stm.getGeneratedKeys();
			if(rs.next())
				erfolg = true;
		}
		catch (SQLTimeoutException e) {
			System.err.println("[MYSQL Model] "+e.getMessage());
		}
		catch (SQLException e) {
			System.err.println("[MYSQL Model] "+e.getMessage());
		}
		
		return erfolg;
	}

	@Override
	public void writeListe(List<Projekt> list) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAufgabenliste(List<Aufgabe> liste) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getAufgabenDatei() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAufgabenDatei(String aufgabenDatei) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getAufgabenProjekten() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAufgabenProjekten(String aufgabenProjekten) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getAufgabenAnwender() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAufgabenAnwender(String aufgabenAnwender) {
		
	}


}
