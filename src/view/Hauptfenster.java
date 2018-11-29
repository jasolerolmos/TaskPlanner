/*
 * Das Hauptfenster 
 */

package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.KeyboardFocusManager;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.Controller;
import model.Anwender;

public class Hauptfenster extends JFrame{
	private static final int _rowHeight = 40;
	private JPanel north = new JPanel();
	private JPanel center = new JPanel();
	private JPanel east = new JPanel();
//	private JPanel south = new JPanel(); 
	private JPanel anwenderPanel = new JPanel();
	private JPanel usernamePanel = new JPanel();
	private JLabel username = new JLabel();
	private static String fontName = "Verdana";
	private AufgabenListe scrollPanel = new AufgabenListe();
	private JComboBox<Object> sortieren;
	
	private JButton neueAufgabeBTN, neueProjektBTN, wochenauswertungBTN, ladenBTN, speichernBTN, logoutBTN, neueAnwender;

	public Hauptfenster(Controller tastetuer) {
		/*
		 * Tastatuer Events 
		 */
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(tastetuer);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(new Rectangle( 0, 0, 920, 600));
		setMinimumSize(new Dimension(920, 600));
		setLocationRelativeTo(null);
		setTitle("TPLer");
		
		setLayout(new BorderLayout());
		
		// North
		NorthPanel();
		
		// Center
		CenterPanel();
		
		// East
//		EastPanel();
		
		add(north,BorderLayout.NORTH);
		add(center,BorderLayout.CENTER);
//		add(east,BorderLayout.EAST);
//		add(south,BorderLayout.SOUTH);

		addComponentListener(new ComponentListener() {			
			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void componentResized(ComponentEvent e) {
				ResizeComponents();
			}
			
			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}

	/*
	 * Aufgaben Panel
	 */
	public void CenterPanel(){
		center.setLayout(new BorderLayout());
		center.setBackground(Design.background2);
		
		JPanel tittelAufagen = new JPanel();
		tittelAufagen.setLayout(new FlowLayout(FlowLayout.LEADING));
		tittelAufagen.setBackground(Design.background2);
		
		JLabel aufgabeLBL = new JLabel("Aufgabenlist");
		aufgabeLBL.setFont(new Font(fontName, Font.BOLD, 20));
		aufgabeLBL.setForeground(Design.title1);
		
		sortieren = new JComboBox<Object>();
		
		tittelAufagen.add(Box.createRigidArea((new Dimension(15, 0))));
		tittelAufagen.add(aufgabeLBL);
		tittelAufagen.add(Box.createRigidArea((new Dimension(15, 0))));
		tittelAufagen.add(sortieren);
		sortieren.setBackground(Design.background1);
		sortieren.setPreferredSize(new Dimension(110, 30));
		
		center.add(tittelAufagen, BorderLayout.NORTH);
		center.add(scrollPanel, BorderLayout.CENTER);
	}
	
	/*
	 * Buttons Panel
	 */
	public void NorthPanel() {
		north.setLayout(new FlowLayout(FlowLayout.LEFT));
		north.setBackground(Design.background1);
		
		neueAufgabeBTN = new JButton("Neue Aufgabe");
		neueAufgabeBTN.setBackground(Design.buttonBG);
		
		neueProjektBTN = new JButton("Projekten");
		neueProjektBTN.setBackground(Design.buttonBG);
		wochenauswertungBTN = new JButton("Wochenauswertung");
		wochenauswertungBTN.setBackground(Design.buttonBG);
		ladenBTN = new JButton("Laden");
		ladenBTN.setBackground(Design.buttonBG);
		speichernBTN = new JButton("Speichern");
		speichernBTN.setBackground(Design.buttonBG);
		logoutBTN = new JButton("Logout");
		logoutBTN.setBackground(Design.buttonBG);
		neueAnwender = new JButton("+");
		
		usernamePanel.setLayout(new FlowLayout(FlowLayout.TRAILING));
		// Die Größe von dem Anwendername. Aktuelle Größe, ohne Buttons. 
		Dimension dim = new Dimension(this.getWidth()-645, 40);
		usernamePanel.setPreferredSize(dim);
//		usernamePanel.setBackground(Color.WHITE);
		username = new JLabel("");
		usernamePanel.setBackground(Design.background1);
		Font font = new Font(fontName, Font.BOLD, 22);
		username.setFont(font);
		usernamePanel.add(username);

		north.add(neueAufgabeBTN);
		north.add(neueProjektBTN);
		north.add(wochenauswertungBTN);
		north.add(ladenBTN);
		north.add(ladenBTN);
		north.add(speichernBTN);
		north.add(logoutBTN);
		north.add(usernamePanel);
		
		buttonStatus(true);
	}
	
	/*
	 * Anwendern Panel
	 */
	public void EastPanel() {
		east.setLayout(new BorderLayout());
		east.setBackground(Color.GRAY);
		
		anwenderPanel.setLayout(new BoxLayout(anwenderPanel, BoxLayout.PAGE_AXIS));
		
		JPanel tittelAnwender = new JPanel();
		tittelAnwender.setLayout(new BoxLayout(tittelAnwender, BoxLayout.X_AXIS));
		tittelAnwender.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
				
		JLabel anwenderLBL = new JLabel("Anwender");
		anwenderLBL.setFont(new Font(fontName, Font.BOLD, 20));
				
		tittelAnwender.add(anwenderLBL);
		tittelAnwender.add(Box.createRigidArea((new Dimension(15, 0))));
		tittelAnwender.add(neueAnwender);
		
		east.add(tittelAnwender, BorderLayout.NORTH);
		east.add(anwenderPanel, BorderLayout.CENTER);
	}
	
	/*
	 * Es braucht Resize die Components, wenn die Größe das Fenster ändert 
	 */
	public void ResizeComponents() {
		scrollPanel.ResizeComponents();
		usernamePanel.setPreferredSize(new Dimension(this.getWidth()-645, _rowHeight));
		usernamePanel.revalidate();
		usernamePanel.repaint();
		revalidate();
		repaint();
	}

	/*
	 * Wenn es kein Anwender eingeloggt ist, sind die Buttons "Not Enable"
	 */
	public void buttonStatus(boolean status) {
		neueAufgabeBTN.setEnabled(status);
		neueProjektBTN.setEnabled(status);
		wochenauswertungBTN.setEnabled(status);
		ladenBTN.setEnabled(status);
		ladenBTN.setEnabled(status);
		speichernBTN.setEnabled(status);
		logoutBTN.setEnabled(status);
		neueAnwender.setEnabled(status);
	}
	
	/*
	 * Die Liste die Anwendern 
	 */
	public void AnwenderListe(List<Anwender> anwender) {
		anwenderPanel.removeAll();
		for (Anwender a : anwender) {
			anwenderPanel.add(new JLabel(a.getNachname()+", "+a.getVorname()));
		}
		revalidate();
		repaint();
	}
	
	public AufgabenListe getScrollPanel() {
		return scrollPanel;
	}

	public void setScrollPanel(AufgabenListe scrollPanel) {
		this.scrollPanel = scrollPanel;
	}

	public JButton getNeueAufgabeBTN() {
		return neueAufgabeBTN;
	}

	public JButton getNeueProjektBTN() {
		return neueProjektBTN;
	}

	public JButton getWochenauswertungBTN() {
		return wochenauswertungBTN;
	}

	public JButton getLadenBTN() {
		return ladenBTN;
	}

	public JButton getSpeichernBTN() {
		return speichernBTN;
	}

	public JButton getLogoutBTN() {
		return logoutBTN;
	}

	public JLabel getUsername() {
		return username;
	}
	
	public JPanel getAnwenderPanel() {
		return anwenderPanel;
	}


	public JComboBox<Object> getSortieren() {
		return sortieren;
	}


	public void setSortieren(JComboBox<Object> sortieren) {
		this.sortieren = sortieren;
	}


}

