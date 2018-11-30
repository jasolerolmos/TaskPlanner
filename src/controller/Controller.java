package controller;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Insets;
import java.awt.KeyEventDispatcher;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.ColorUIResource;

import Context.PersistenceManager;
import connection.Export;
import connection.Laden;
import model.Anwender;
import model.Aufgabe;
import model.AufgabeAnsicht;
import model.Projekt;
import model.Zeit;
import view.AdminSecret;
import view.AufgabeReihe;
import view.AufgabenListe;
import view.BearbeitenAufgabe;
import view.BeschreibungAnsicht;
import view.Design;
//import view.AufgabeReihe;
import view.Hauptfenster;
//import view.LoginFenster;
import view.LoginFenster;
import view.ProjektFenster;
import view.ProjektReihe;
import view.SpeichernFenster;
import view.WocheControl;
import view.Wochenauswertung;

public class Controller implements KeyEventDispatcher{
	
	private enum OrderType {
		NAME(	"nach Name", 		aufgabe -> aufgabe.getName()),
		PROJEKT("nach Projetkt", 	aufgabe -> aufgabe.getProjekt().getName()),
		DAUER(	"nach Dauer", 		aufgabe -> aufgabe.getDauer()),
		ZEIT(	"nach Zeit",		aufgabe -> aufgabe.getZeitenliste().get(0).getAnfang().toString() );
		
		private String displayName;
		private Function<Aufgabe, String> sortKriterium;

		private OrderType(String displayName, Function<Aufgabe, String> sortKriterium) {
			this.displayName = displayName;
			this.sortKriterium = sortKriterium;
			
		}
		
		@Override
		public String toString() {
			return 	displayName;
		}
	}
	
	private final Hauptfenster view = new Hauptfenster(this);
	private PersistenceManager manager = new PersistenceManager();
	private final LoginFenster loginWin = new LoginFenster();
	private final ProjektFenster projektWin = new ProjektFenster();
	private final Wochenauswertung wochenauswertung = new Wochenauswertung();
	private AufgabenListe panelAufgaben;
	private final BearbeitenAufgabe bearbeitenFenster = new BearbeitenAufgabe();
//	private int aufgaben = -1, itAufgaben=-1, aktuelle = -1;
	private Anwender anwender;
	private SpeichernFenster exportWin = new SpeichernFenster();
	private ImageIcon icono = new ImageIcon("image/icono.png");
//	private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
	private final JFileChooser quellDatei = new JFileChooser();
	private static int __maxLenghtNameAufgabe = 10;
	public Controller() {
		SetActionListeners();
        try {
        	TrayIcon trayIcon = new TrayIcon(new ImageIcon("image/trayicon.png").getImage(), "TaskDone");
        	trayIcon.addActionListener(e -> {view.setVisible(true);view.setExtendedState(JFrame.NORMAL);});
        	SystemTray.getSystemTray().add(trayIcon);
        	
		} catch (AWTException e) {
			System.err.println("Error loading Trayicon: "+e.getMessage());
		}
        
		bearbeitenFenster.setIconImage(icono.getImage());
		view.setIconImage(icono.getImage());
		projektWin.setIconImage(icono.getImage());
		exportWin.setIconImage(icono.getImage());
		
		
	}
	
	public void start() {
		view.buttonStatus(false);

		view.setVisible(true);

		loginWin.setFenster();
		loginWin.setVisible(true);
		if(view.isVisible()) {
			view.getSortieren().addItem(OrderType.NAME);
			view.getSortieren().addItem(OrderType.PROJEKT);
	//		view.getSortieren().addItem(OrderType.ZEIT);
			view.getSortieren().addItem(OrderType.DAUER);
			
	
			wochenauswertung.getWocheControl().getVor().addActionListener(e -> actionVorWoche(wochenauswertung.getWocheControl()));
			wochenauswertung.getWocheControl().getNach().addActionListener(e -> actionNachWoche(wochenauswertung.getWocheControl()));
		}
		
	}
	

	public void stop() {
		if(JOptionPane.showConfirmDialog(view, "Bist du sicher?", "Task Planner schlieﬂen", JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION) {
			loginWin.setVisible(false);
			view.setVisible(false);
			System.exit(0);
		}
	}
	
	private void actionLogout(ActionEvent e) {
		view.buttonStatus(false);
		view.setUsername("" ,"");		
		panelAufgaben = view.getScrollPanel();
		panelAufgaben.setBackground(Design.background2);
		panelAufgaben.setAufgaben(new ArrayList<Aufgabe>());
		panelAufgaben.paintAufgabenListe();
		view.setScrollPanel(panelAufgaben);
		loginWin.setVisible(true);
	}
	
	private void actionNeueAufgabe(ActionEvent e) {
		bearbeitenFenster.setAufgabe(new Aufgabe());
		bearbeitenFenster.setSpeicherEnable(false);

		setListenersButtonsZeitenlist();
		
		setProjekteComboBox();
		bearbeitenFenster.setFenster();
		bearbeitenFenster.setCaretNameText(c -> setCaretNameAufgate());
		bearbeitenFenster.setProjektAction(p -> setCaretNameAufgate());
		bearbeitenFenster.setKeyTypedNameText(actionKeyTypedNameAufgabe());
		
		bearbeitenFenster.setVisible(true);
	}
	
	
	private void actionLaden(ActionEvent e) {
		quellDatei.setFileFilter(new FileNameExtensionFilter("*.csv", "csv") );
		
		switch(quellDatei.showSaveDialog(view)) {
		case JFileChooser.APPROVE_OPTION:
			if(quellDatei.getSelectedFile()!=null) {
				String fileName = quellDatei.getSelectedFile().getPath();
				
				if(!fileName.endsWith(".csv")) {
					fileName += ".csv";
				}
				Laden laden = new Laden(fileName);
				laden.setBenutzerID(anwender.getId());
				laden.setAnwendernliste(manager.getAnwenderPersistence().List());
				laden.setProjektliste(manager.getProjektPersistence().List());
				
				// TODO: Persistencia tiene que decidir si guarda en CSV o en BD lo cargado con Laden
				// Si no, no ser· compatible con DB
				Export save = new Export("aufgaben.csv", laden.getAufgaben());
				save.Save();
				 
				int geladet = laden.getAufgaben().size();
				manager.getAufgabePersistence().List(anwender);
				
				setAufgabenliste();
				actionSortierung(null);
				if(geladet>0)
					JOptionPane.showMessageDialog(exportWin, "Es wurden "+panelAufgaben.getAufgaben().size()+" Aufgaben gespeichert.", "Speichern", JOptionPane.INFORMATION_MESSAGE);
				else
					JOptionPane.showMessageDialog(exportWin, "Es gibt keiner Aufgabe in der Datei", "Speichern", JOptionPane.ERROR_MESSAGE);
			}
			else {
				JOptionPane.showMessageDialog(exportWin, "Es gibt ein Problem mit den Datei", "Laden", JOptionPane.ERROR_MESSAGE);
			}
			break;
		case JFileChooser.CANCEL_OPTION:
			break;
		}
	}
	
	private void actionSpeichern(ActionEvent e) {
		if(!bearbeitenFenster.getNameJTF().isEmpty()) {
			Aufgabe aufgabe = bearbeitenFenster.getAufgabe();

			boolean exist = false;
			int index = aufgabe.getId()-1;
			

			for(Aufgabe auf: manager.getAufgabePersistence().List(anwender)) {
				if(auf.getName().equals(aufgabe.getName()) && auf.getProjekt().getId() == aufgabe.getProjekt().getId() && auf.getId() != aufgabe.getId()) {
					exist = true;
				}
			}
			
			
			if(!exist) {
				if(index>=0) {
					manager.getAufgabePersistence().List(anwender).get(index).setName(bearbeitenFenster.getNameJTF());
					manager.getAufgabePersistence().List(anwender).get(index).setBeschreibung(bearbeitenFenster.getBeschreibungJTA().getText());
				}
				else {
					aufgabe.setId(aufgabe.getId());
					aufgabe.setName(bearbeitenFenster.getNameJTF().trim());
					aufgabe.setBeschreibung(bearbeitenFenster.getBeschreibungJTA().getText());
					aufgabe.setAnwender(anwender);
					aufgabe.setProjekt((Projekt) bearbeitenFenster.getAlleProjekten().getSelectedItem());
				}
				
				manager.getAufgabePersistence().Save(aufgabe, index);
				setAufgabenliste();
				bearbeitenFenster.setVisible(false);
			}
			else {
				JOptionPane.showMessageDialog(bearbeitenFenster, "Die Aufgabe ist bereits vorhanden, ¸berpr¸fen Sie den Namen und das Projekt.", "Bearbeiten", JOptionPane.ERROR_MESSAGE);
			}
		}
		else {
			JOptionPane.showMessageDialog(bearbeitenFenster, "Die Aufgabe muss ein Name haben.", "Bearbeiten", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void actionNeueZeit(ActionEvent e) {
		bearbeitenFenster.setCanESC(false);
		DateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
		bearbeitenFenster.getZeitenliste().getNeueZeit().checkZeit();
		String neueAnfang = bearbeitenFenster.getZeitenliste().getNeueZeit().getNeueAnfang();
		String neueBeende = bearbeitenFenster.getZeitenliste().getNeueZeit().getNeueBeende();
		Date dateAnfang = new Date();
		Date dateBeende = new Date();
		try {
			dateAnfang = format.parse(neueAnfang);
			dateBeende = format.parse(neueBeende);
		} catch (Exception e2) {
			// TODO: handle exception
		}

		if(dateAnfang.equals(dateBeende)) {
			JOptionPane.showMessageDialog(bearbeitenFenster, "Anfang und Beende sind gleich", "Bearbeiten", JOptionPane.ERROR_MESSAGE);
		}
		else {
			if(JOptionPane.showConfirmDialog(bearbeitenFenster, "Bist du sicher?", "Neue Zietenarbeit", JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION) {

				bearbeitenFenster.setCanESC(false);
				boolean dateOK = true;
				for (Aufgabe auf : manager.getAufgabePersistence().List(anwender)) {
					for (Zeit z : auf.getZeitenliste()) {
						
						if(    ( dateAnfang.before(z.getBeende()) && dateAnfang.after(z.getAnfang()) )
							|| ( dateBeende.after(z.getAnfang()) && dateBeende.before(z.getBeende()) )
							|| ( dateAnfang.before(z.getAnfang()) && dateBeende.after(z.getBeende()) )
							) {
							dateOK = false;
						}
					}
				}
				if(!dateOK) {
					JOptionPane.showMessageDialog(bearbeitenFenster, "Es gibt andere Aufgabe in diese Zeitplanung", "Bearbeiten", JOptionPane.ERROR_MESSAGE);
				}
				else {
					try {
						bearbeitenFenster.getAufgabe().addZeit(format.parse(neueAnfang), format.parse(neueBeende));
						bearbeitenFenster.getZeitenliste().setListe(bearbeitenFenster.getAufgabe().getZeitenliste());
					}
					catch (ParseException pe) {
					}
				}
			}
		}
		bearbeitenFenster.setCanESC(true);
	}
	
	private void actionWochenauswertung() {
		wochenauswertung.setVisible(true);
	}

	private void actionVorWoche(WocheControl wc) {
		try {
			wc.getHeute().add(Calendar.WEEK_OF_YEAR, -1);
			wc.getLabel().setText("Woche: "+wc.getHeute().get(Calendar.WEEK_OF_YEAR));
			wc.setWochenText();
		} catch (Exception e) {
			System.out.println("ERROR");
		}
//		getAufgabenDate();
		getAufgabenTag();
	}
	
	private void actionNachWoche(WocheControl wc) {
		try {
			wc.getHeute().add(Calendar.WEEK_OF_YEAR, 1);
			wc.getLabel().setText("Woche: "+wc.getHeute().get(Calendar.WEEK_OF_YEAR));
			wc.setWochenText();
		} catch (Exception e) {
			System.out.println("ERROR");
		}
//		getAufgabenDate();
		getAufgabenTag();
	}
	
	public void getAufgabenDate() {
		Date montagDate = wochenauswertung.getWocheControl().getMontagDate();
		Date sonntagDate = wochenauswertung.getWocheControl().getSonntagDate();
		
		for (Aufgabe auf : manager.getAufgabePersistence().List(anwender)) {
			for (Zeit zeit : auf.getZeitenliste()) {
				try {
					if(zeit.getAnfang().after(montagDate) && zeit.getAnfang().before(sonntagDate)) {
						System.out.println(zeit.getAnfang().toString());
					}
				} catch (Exception e) {
					System.out.println("ERROR: "+e.getMessage());
				}
			}
		}
	}
	
	private WindowListener actionClose() {
		return new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent e) {

				stop();
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		};
	}

	
	private void setCaretNameAufgate() {
		if(bearbeitenFenster.getNameJTF().length()>0) {
			
			bearbeitenFenster.setSpeicherEnable(true);
			int id  = -1;
			if(bearbeitenFenster.getAlleProjekten().getSelectedIndex()>0)
				id = ((Projekt)bearbeitenFenster.getAlleProjekten().getSelectedItem()).getId();
			for (Aufgabe aufgabe : manager.getAufgabePersistence().List(anwender)) {
				if(aufgabe.getName().toLowerCase().equals(bearbeitenFenster.getNameJTF().toLowerCase()) 
						&& aufgabe.getProjekt().getId()==id) {
					bearbeitenFenster.setSpeicherEnable(false);
//					bearbeitenFenster.setMessageFehler("Das Aufgabe existiert schon");
					System.out.println("Ya existe");
				}
			}
			
		}
		else {
			bearbeitenFenster.setSpeicherEnable(false);
		}
	}
	
	private KeyListener actionKeyTypedNameAufgabe() {
		return new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {

				if(bearbeitenFenster.getNameJTF().length()>__maxLenghtNameAufgabe) {
					e.consume();
				}
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				if(bearbeitenFenster.getNameJTF().length()>__maxLenghtNameAufgabe) {
					e.consume();
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				System.out.println("Tecla: "+e.getID());
				if(bearbeitenFenster.getNameJTF().length()>__maxLenghtNameAufgabe) {
					e.consume();
				}
				if(e.isControlDown() && e.getID() == KeyEvent.VK_V) {
					System.out.println("Paste");
					e.consume();
				}
			}
		};
		
	}
	
	
	private void getAufgabenTag() {
		List<AufgabeAnsicht> zuZeien = new  ArrayList<AufgabeAnsicht>();
		Date montagDate = wochenauswertung.getWocheControl().getMontagDate();
		Calendar datum = Calendar.getInstance();
		Calendar datum2 = Calendar.getInstance();
		datum.setTime(montagDate);
		datum.set(datum.get(Calendar.YEAR), datum.get(Calendar.MONTH), datum.get(Calendar.DATE), 0, 0);
//		datum2.setTime(montagDate);
		datum2.set(datum.get(Calendar.YEAR), datum.get(Calendar.MONTH), datum.get(Calendar.DATE), 23, 59);
		datum.add(Calendar.DATE, wochenauswertung.getMainPanel().getSelectedIndex());
		datum2.add(Calendar.DATE, wochenauswertung.getMainPanel().getSelectedIndex());
		
		for (Aufgabe auf : manager.getAufgabePersistence().List(anwender)) {
			for (Zeit zeit : auf.getZeitenliste()) {
				try {
					if(zeit.getAnfang().after(datum.getTime()) && zeit.getAnfang().before(datum2.getTime())) {
						zuZeien.add(new AufgabeAnsicht(auf.getName(), auf.getProjekt().getName(), zeit));
					}
				} catch (Exception excp) {
					System.out.println("ERROR: "+excp.getMessage());
				}
			}
		}
		switch(wochenauswertung.getMode()) {
			case 0:
				wochenauswertung.getNachProjekt().setBackground(Design.buttonBG);
				wochenauswertung.getNachStunde().setBackground(Design.buttonBG2);
				wochenauswertung.setAufgabeListeAnfang(zuZeien);
				break;
			case 1:
				wochenauswertung.getNachProjekt().setBackground(Design.buttonBG2);
				wochenauswertung.getNachStunde().setBackground(Design.buttonBG);
				wochenauswertung.setAufgabeListeProjekt(zuZeien);
				break;
		}
	}
	/*
	 * Es festlegt die Listeners von dem Statics-Buttons
	 */
	public void SetActionListeners() {
		view.getWochenauswertungBTN().addActionListener(e -> actionWochenauswertung());
		
		view.getLogoutBTN().addActionListener(this::actionLogout);
		
		view.getNeueAufgabeBTN().addActionListener(this::actionNeueAufgabe);
		
		view.getNeueProjektBTN().addActionListener(e -> projektFensterVisible());
		
		view.getSpeichernBTN().addActionListener(e -> SpeichernFensterVisible());
		
		view.getLadenBTN().addActionListener(this::actionLaden);
		
		loginWin.getEnter().addActionListener(e -> EnterLogin());
		
		loginWin.getExit().addActionListener(e -> {stop();});
		
		bearbeitenFenster.getAbbrechenBTN().addActionListener(e -> bearbeitenFenster.setVisible(false));
		
		bearbeitenFenster.getSpeichernBTN().addActionListener(this::actionSpeichern);

		bearbeitenFenster.getZeitenliste().getNeueZeit().getSpeichern().addActionListener(this::actionNeueZeit);
	
		view.getSortieren().addActionListener(this::actionSortierung);
		
		wochenauswertung.getMainPanel().addChangeListener(e -> getAufgabenTag());
		
		wochenauswertung.getNachProjekt().addActionListener(e -> {wochenauswertung.setMode(1);getAufgabenTag();});
		
		wochenauswertung.getNachStunde().addActionListener(e -> {wochenauswertung.setMode(0);getAufgabenTag();});
		
		view.setCloseAction(actionClose());
	}

	
	private void actionSortierung(ActionEvent e) {
		OrderType sortBy = (OrderType) view.getSortieren().getSelectedItem();
		List<Aufgabe> l = manager.getAufgabePersistence().List(anwender);
		l.sort(Comparator.comparing(sortBy.sortKriterium));
		new Export("aufgaben.csv", l).Save();
		setAufgabenliste();
	}
	 
	/////////////////////////////////////////////////////////////////////
	/// Aufgabe Management
	/////////////////////////////////////////////////////////////////////
	
	public void setAufgabeListevonAnwender() {
		manager = new PersistenceManager();

		setAufgabenliste();
	}
	
	public void setAufgabenliste() {		
		panelAufgaben = view.getScrollPanel();
		panelAufgaben.setAufgaben(manager.getAufgabePersistence().List(anwender));
		panelAufgaben.paintAufgabenListe();
		view.setScrollPanel(panelAufgaben);
		setListenersButtonsAufgaben();
	}
	
	
	/*
	 * Es festlegt die Listeners von jeder Aufgabe 
	 */
	public void setListenersButtonsAufgaben() {
		panelAufgaben.getAufgabenListe().stream().forEach(ar -> {
				ar.getBearbeiten().addActionListener(e -> actionAufgabeBearbeiten(ar) );
				ar.getLoeschen().addActionListener(e -> actionAufgabeLoeschen(ar));
			});
	}

	private void actionAufgabeBearbeiten(AufgabeReihe ar) {
		bearbeitenFenster.setAufgabe(ar.getAufgabe());
		setListenersButtonsZeitenlist();

		setProjekteComboBox();
		bearbeitenFenster.setFenster();
		bearbeitenFenster.setVisible(true);
	}
	
	private void actionAufgabeLoeschen(AufgabeReihe ar) {
		manager.getAufgabePersistence().Remove(ar.getAufgabe());
		panelAufgaben.setAufgaben(manager.getAufgabePersistence().List(anwender));
		panelAufgaben.paintAufgabenListe();
		setListenersButtonsAufgaben();
	}
	
	public void setListenersButtonsZeitenlist() {
		bearbeitenFenster.getZeitenliste().getLoeschenButtonListe()
			.stream().forEach(ar -> ar.addActionListener(e -> actionZeitLoeschen(ar)));

	}

	private void actionZeitLoeschen(JButton ar) {
		if(JOptionPane.showConfirmDialog(bearbeitenFenster, "Bist du sicher?", "Zeit Lˆschen", JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION) {
			int index = bearbeitenFenster.getZeitenliste().getLoeschenButtonListe().indexOf(ar);		
			bearbeitenFenster.getAufgabe().getZeitenliste().remove(index);
			manager.getAufgabePersistence().Save(bearbeitenFenster.getAufgabe(), bearbeitenFenster.getAufgabe().getId()-1);
			bearbeitenFenster.getZeitenliste().setListe(bearbeitenFenster.getAufgabe().getZeitenliste());
		}
	}
	
	/*
	 * Man male ist die ausw‰hlte Reihe gemaltet und wir schrollen die Scrollbar. 
	 */
	/*
	public void PaintSelected() {
		int blaeter = panelAufgaben.getViewport().getHeight();
		
		if(aktuelle>=0)
			panelAufgaben.getAufgabenListe().get(aktuelle).setBackground(new Color(210, 210, 210));
		panelAufgaben.getAufgabenListe().get(itAufgaben).setBackground(new Color(139, 139, 139));

		JScrollBar verticalScrollBar = panelAufgaben.getVerticalScrollBar();
		
		if( ((itAufgaben*58) > ( blaeter+Math.abs(panelAufgaben.getViewport().getView().getY()))) ) {
			verticalScrollBar.setValue(verticalScrollBar.getValue()+blaeter);
		}
		else {
			if(( Math.abs(panelAufgaben.getViewport().getView().getY())>(itAufgaben*58)) ){
				verticalScrollBar.setValue(verticalScrollBar.getValue()-blaeter);
			}
		}
		panelAufgaben.revalidate();
		panelAufgaben.repaint();
	}
*/

	/////////////////////////////////////////////////////////////////////
	/// Aufgabe Management
	/////////////////////////////////////////////////////////////////////
	
	/*
	 *  Speichern Fenster
	 */
	public void SpeichernFensterVisible() {
		int x = view.getX() + (view.getWidth()/2)-(exportWin.getWidth()/2);
		int y = view.getY() + (view.getHeight()/2)-(exportWin.getHeight()/2);
		
		exportWin.setListe(manager.getAufgabePersistence().List(anwender));
		exportWin.setFenster(x,y);
		
		exportWin.getSpeichern().addActionListener(this::actionExport);
		
//		exportWin.getQuellBTN().addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				JFileChooser quellDatei = new JFileChooser();
//				quellDatei.setFileFilter(new FileNameExtensionFilter("*.csv", "csv") );
//				quellDatei.showOpenDialog(exportWin);
//				if(quellDatei.getSelectedFile()!=null) {
//					String fileName = quellDatei.getSelectedFile().getPath();
//					
//					if(!fileName.endsWith(".csv")) {
//						fileName += ".csv";
//					}
//					exportWin.getSpeichern().setEnabled(true);
//					exportWin.ShowDatei(fileName);
//				}
//			}
//		});

		exportWin.setVisible(true);
	}

	private void actionExport(ActionEvent e) {
		JFileChooser quellDatei = new JFileChooser();
		quellDatei.setFileFilter(new FileNameExtensionFilter("*.csv", "csv") );
		quellDatei.setDialogTitle("Speichern");
		quellDatei.setApproveButtonText("Save");
		quellDatei.setApproveButtonToolTipText("Die Aufgaben speichern");
		quellDatei.showOpenDialog(exportWin);
		if(quellDatei.getSelectedFile()!=null) {
			String fileName = quellDatei.getSelectedFile().getPath();
			
			if(!fileName.endsWith(".csv")) {
				fileName += ".csv";
			}
			exportWin.ShowDatei(fileName);
			
			Export export = new Export(exportWin.getDatei(), manager.getAufgabePersistence().List(anwender));
			JOptionPane.showMessageDialog(exportWin, 
					"Es wurden "+export.getGespeichert()+" Aufgaben gespeichert.",
					"Speichern", 
					JOptionPane.INFORMATION_MESSAGE);
			exportWin.dispose();
		}
	}

	/////////////////////////////////////////////////////////////////////
	/// Login Management
	/////////////////////////////////////////////////////////////////////
	
	/*
	 * Es checkt Anwender und Kennwort.
	 */
	public void EnterLogin() {
		String kennwort = String.valueOf(loginWin.getKennwortJTF().getPassword());
		String benutzername = loginWin.getAnwenderJTF().getText();
		loginWin.getMessageLBL().setText("");
		
		if(benutzername.indexOf(".")>0) {
			boolean einloggt = manager.getAnwenderPersistence().Login(benutzername, kennwort);
			if(einloggt) {
				anwender = manager.getAnwenderPersistence().Load(benutzername);
				loginWin.setVisible(false);
				view.buttonStatus(true);
				String userName = benutzername.split("\\.")[0];
				String userNachname = benutzername.split("\\.")[1];
				userNachname = userNachname.substring(0, 1).toUpperCase()+userNachname.substring(1);
				userName = userName.substring(0, 1).toUpperCase()+userName.substring(1);
				view.setUsername(userName, userNachname);
				setAufgabeListevonAnwender();
			}
			else {
				loginWin.getMessageLBL().setText("Falsche Anwender");
			}
		}
		else {
			loginWin.getMessageLBL().setText("Du musst ein Benutzername schreiben");
		}
	}

	/////////////////////////////////////////////////////////////////////
	/// Projekt Management
	/////////////////////////////////////////////////////////////////////
	
	public void projektFensterVisible() {
		projektWin.setListe(manager.getProjektPersistence().List());
		int x = view.getX() + (view.getWidth()/2)-(projektWin.getWidth()/2);
		int y = view.getY() + (view.getHeight()/2)-(projektWin.getHeight()/2);

		projektWin.setFenster(x, y);
		projektWin.setSpeichernEnable(false);
		projektWin.getSpeichern().addActionListener(this::actionProjektSpeichern);
		projektWin.setCaretNeueProjekt(this::setCaretNameProjekt);
		projektAddListenersListe();
		projektWin.setVisible(true);
	}
	
	private void setCaretNameProjekt(CaretEvent e) {

		if(projektWin.getNeueName().length()>0) {
			projektWin.setSpeichernEnable(true);
			projektWin.setMessageFehler("");
			
			for (Projekt projekt : manager.getProjektPersistence().List()) {
				if(projekt.getName().toLowerCase().equals(projektWin.getNeueName().toLowerCase())) {
					projektWin.setSpeichernEnable(false);
					projektWin.setMessageFehler("Das Projekt existiert schon");
				}
			}
		}
		else {
			projektWin.setMessageFehler("Neu Name musst nicht leere sein");
			projektWin.setSpeichernEnable(false);
		}
		
	}
	
	private void actionProjektSpeichern(ActionEvent e) {
		Projekt neueProjekt = new Projekt(projektWin.getNeueName());
		if(manager.getProjektPersistence().Load(projektWin.getNeueName()).getId()==0) {
			System.out.println(neueProjekt.getId());
			manager.getProjektPersistence().Save(neueProjekt);
			projektWin.setListe(manager.getProjektPersistence().List());
			projektWin.setListeProjekten();
			projektAddListenersListe();
			projektWin.setSpeichernEnable(false);
		}
		else {
			JOptionPane.showMessageDialog(bearbeitenFenster, 
					"Die Projekt ist bereits vorhanden, ¸berpr¸fen Sie den Namen.", 
					"Projekten", 
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void projektAddListenersListe() {
//		for (ProjektReihe pr : projektWin.getListeReihe()) {
//			pr.getNameLBL().addMouseListener(new MouseListener() {
//				
//				@Override
//				public void mouseReleased(MouseEvent e) {
//					// TODO Auto-generated method stub
//					
//				}
//				
//				@Override
//				public void mousePressed(MouseEvent e) {
//					// TODO Auto-generated method stub
//					
//				}
//				
//				@Override
//				public void mouseExited(MouseEvent e) {
//					// TODO Auto-generated method stub
//					
//				}
//				
//				@Override
//				public void mouseEntered(MouseEvent e) {
//					// TODO Auto-generated method stub
//					
//				}
//				
//				@Override
//				public void mouseClicked(MouseEvent e) {
//					pr.Editable(true);
//				}
//			});
//			pr.getNameEdit().addKeyListener(new KeyListener() {
//				
//				@Override
//				public void keyTyped(KeyEvent e) {
//					// TODO Auto-generated method stub
//					
//				}
//				
//				@Override
//				public void keyReleased(KeyEvent e) {
//					if(e.getKeyCode() == KeyEvent.VK_ENTER) {
//						pr.Editable(false);
//						pr.getNameLBL().setText(pr.getNameEdit().getText());
//						pr.getProjekt().setName(pr.getNameEdit().getText());
//						manager.getProjektPersistence().Save(pr.getProjekt());
//						setAufgabenliste();
//					}
//				}
//				
//				@Override
//				public void keyPressed(KeyEvent e) {
//					// TODO Auto-generated method stub
//					
//				}
//			});
//
//			pr.getLoeschen().addActionListener(e -> actionProjektLoesechen(pr));
//		}
		projektWin.getListeReihe().stream()
			.forEach(pr -> pr.getLoeschen().addActionListener(e -> actionProjektLoesechen(pr))
			);
	}
	
	private void actionProjektLoesechen(ProjektReihe pr) {
		manager.getProjektPersistence().Remove(pr.getProjekt());
		projektWin.getListe().remove(pr.getProjekt());
		projektWin.getListeReihe().remove(pr);
		projektWin.setListeProjekten();
		projektAddListenersListe();
	}
	
	public void setProjekteComboBox(){
		manager.getProjektPersistence().reloadList();
		if(bearbeitenFenster.getAlleProjekten().getComponentCount()>0)
			bearbeitenFenster.getAlleProjekten().removeAllItems();
		for(Projekt p: manager.getProjektPersistence().List()) {
			bearbeitenFenster.getAlleProjekten().addItem(p);
		}
	}

	/////////////////////////////////////////////////////////////////////
	/// Keyboard Management
	/////////////////////////////////////////////////////////////////////
	
	@Override
	public boolean dispatchKeyEvent(KeyEvent e) {
		if(loginWin.isVisible()) {
			LoginFenster(e);
		}
		if(bearbeitenFenster.isCanESC()) {
			BearbeitenFenster(e);
		}
		if(exportWin.isVisible()) {
			SpeichernFenster(e);
		}
		if(projektWin.isVisible()) {
			ProkejtFenster(e);
		}
		else {
//			if(e.getID() == KeyEvent.KEY_RELEASED)
				Hauptfenster(e);
		}
		
		
		return false;
	}

	/*
	 * Tastatuer Events f¸r Hauptfenster.
	 */
	public void Hauptfenster(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_ESCAPE:
				break;
			case KeyEvent.VK_ENTER:
				break;
			case KeyEvent.VK_N:
				if(e.isControlDown()) {
					actionNeueAufgabe(null);
				}
				break;
			case KeyEvent.VK_S:
				if(e.isControlDown()) {
					SpeichernFensterVisible();
				}
				break;
			case KeyEvent.VK_P:
				if(e.isControlDown()) {
					projektFensterVisible();
				}
				break;
			case KeyEvent.VK_Q:
				if(e.isControlDown()) {
					actionLogout(null);
				}
				break;
			case KeyEvent.VK_L:
				if(e.isControlDown()) {
					actionLaden(null);
				}
				break;
			case KeyEvent.VK_A:
				if(e.isControlDown() && e.isAltDown()) {
					AdminSecret as = new AdminSecret(manager.getAnwenderPersistence().List());
					as.setVisible(true);
				}
				break;
//			case KeyEvent.VK_DOWN:
//				if(e.getID() == KeyEvent.KEY_RELEASED) {
//					aktuelle = itAufgaben;
//					itAufgaben = (itAufgaben>=(aufgaben-2)) ? aufgaben-1 : itAufgaben+1 ;
//					
//					PaintSelected();
//				}
//				break;
//			case KeyEvent.VK_UP:
//				if(e.getID() == KeyEvent.KEY_RELEASED) {
//					aktuelle = itAufgaben;
//					itAufgaben = (itAufgaben<=0) ? 0 : itAufgaben-1;
//					
//					PaintSelected();
//				}
//				break;
		}
	}

	/*
	 * Tastatuer Events f¸r Login Fenster.
	 */	
	public void LoginFenster(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_ESCAPE:
				stop();
				break;
			case KeyEvent.VK_ENTER:
				EnterLogin();
				break;
		}
	}

	/*
	 * Tastatuer Events f¸r Bearbeiten Fenster.
	 */	
	public void BearbeitenFenster(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_ESCAPE:
				if(bearbeitenFenster.isCanESC() && e.getID() == KeyEvent.KEY_RELEASED) {
//					bearbeitenFenster.setVisible(false);
				}
				break;
			case KeyEvent.VK_ENTER:
				
				break;
		}
	}

	/*
	 * Tastatuer Events f¸r Speichern Fenster.
	 */	
	public void SpeichernFenster(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_ESCAPE:
//				exportWin.setVisible(false);
				break;
			case KeyEvent.VK_ENTER:
				
				break;
		}
	}	

	/*
	 * Tastatuer Events f¸r Speichern Fenster.
	 */	
	public void ProkejtFenster(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_ESCAPE:
				projektWin.setVisible(false);
				break;
			case KeyEvent.VK_ENTER:
				
				break;
		}
	}	

	
	
	
	public static void main(String[] args) {
		Design.setDesign2();
		
		UIManager.put("ComboBox.selectionBackground", new ColorUIResource(Design.reiheBG));
		UIManager.put("Button.select", new ColorUIResource(Design.buttonBG2));
		UIManager.put("TabbedPane.selected", new ColorUIResource(Design.background2));
		UIManager.put("TabbedPane.background", new ColorUIResource(Color.RED));
		UIManager.put("TabbedPane.selectedForeground", new ColorUIResource(Design.background1));
		UIManager.put("TabbedPane.unselectedForeground", new ColorUIResource(Design.buttonBG));
		UIManager.put("TabbedPane.unselectedTabBackground", new ColorUIResource(Design.background2));
		UIManager.put("TabbedPane.tabInsets", new Insets(8, 8, 8, 8));
		
		Controller control = new Controller();
		control.start();
	}
	
}
