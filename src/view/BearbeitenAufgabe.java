package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.CaretListener;

import model.Aufgabe;
import model.Projekt;

public class BearbeitenAufgabe extends JDialog{
	private int width = 500, height = 500;
	private JTabbedPane  main = new JTabbedPane ();
	private JPanel north, west, beschreibungMainPanel, south;
	private JTextArea beschreibungJTA;
	private JTextField nameJTF;
	private Aufgabe aufgabe;
	private JButton speichernBTN = new JButton("Speichern");
	private JButton abbrechenBTN = new JButton("Abbrechen");
	private ZeitenlisteReihe zeitenliste = new ZeitenlisteReihe();
	private boolean canESC = false;
	
	private JComboBox<Projekt> alleProjekten = new JComboBox<Projekt>();
	
	public BearbeitenAufgabe() {
		setSize(width,  height);
		setLocationRelativeTo(null);
		setResizable(false);
//		setUndecorated(true);
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		aufgabe = new Aufgabe();
		
		north = new JPanel();
		north.setLayout(new BoxLayout(north, BoxLayout.PAGE_AXIS));
		west = new JPanel();
		west.setLayout(new BoxLayout(west, BoxLayout.PAGE_AXIS));
		beschreibungMainPanel = new JPanel();
		south = new JPanel();
		south.setLayout(new FlowLayout(FlowLayout.TRAILING));
		
		
		setLayout(new BorderLayout());
		add(main, BorderLayout.CENTER);
		add(south, BorderLayout.SOUTH);
	}

	public void setFenster() {
		north.removeAll();
		beschreibungMainPanel.removeAll();
		
		setTitle("Bearbeiten Aufgabe");
		
		JPanel namePanel = new JPanel();
		namePanel.setMaximumSize(new Dimension(this.getWidth(), 40));
		namePanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		
		JLabel nameLBL = new JLabel("Name");
		nameLBL.setPreferredSize(new Dimension(80, 30));
		
		nameJTF = new JTextField(aufgabe.getName());
		nameJTF.setPreferredSize(new Dimension(280, 30));
		
		namePanel.add(nameLBL);
		namePanel.add(nameJTF);
		
		JPanel projektPanel = new JPanel();
		projektPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		projektPanel.setMaximumSize(new Dimension(this.getWidth(), 40));
		
		JLabel projektLBL = new JLabel("Projekt");
		projektLBL.setPreferredSize(new Dimension(80, 30));
		
		alleProjekten.setPreferredSize(new Dimension(180, 30));
		
		projektPanel.add(projektLBL);
		
		int selected = aufgabe.getProjekt().getId()>0 ? aufgabe.getProjekt().getId()-1 : 0;
		alleProjekten.setSelectedIndex(selected);
		projektPanel.add(alleProjekten);
		
//		beschreibungMainPanel.setLayout(new BoxLayout(beschreibungMainPanel, BoxLayout.PAGE_AXIS));

		JPanel beschreibungLBLPanel = new JPanel();
		beschreibungLBLPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		beschreibungLBLPanel.setMaximumSize(new Dimension(this.getWidth(), 40));
		beschreibungLBLPanel.setBackground(Design.background1);
		
		JLabel beschreibungLBL = new JLabel("Beschreibung");
		beschreibungLBL.setMaximumSize(new Dimension(this.getWidth()/2, 40));
				
		beschreibungLBLPanel.add(beschreibungLBL);
		
		beschreibungJTA = new JTextArea(aufgabe.getBeschreibung(), 17, 38);
		beschreibungJTA.setLineWrap(true);
		beschreibungJTA.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		JScrollPane scrollJTA = new JScrollPane();
		scrollJTA.setViewportView(beschreibungJTA);
		scrollJTA.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		beschreibungMainPanel.add(scrollJTA);
		
		north.add(namePanel);
		north.add(projektPanel);
		north.add(beschreibungLBLPanel);
		north.add(beschreibungMainPanel);
		
		JPanel zeitPanel = new JPanel();
		zeitPanel.setLayout(new BoxLayout(zeitPanel, BoxLayout.PAGE_AXIS));
		
		zeitenliste.setListe(aufgabe.getZeitenliste());
		zeitPanel.add(zeitenliste);
		
		west.add(zeitPanel);

		main.addTab("Allgemein", north);
		main.addTab("Zeit", west);
		
		main.setSelectedIndex(0);

		south.add(speichernBTN);
		south.add(abbrechenBTN);

		north.setBackground(Design.background1);
		beschreibungMainPanel.setBackground(Design.background1);
		south.setBackground(Design.background1);

		this.			setBackground(Design.background1);
//		main.			setBackground(Design.background1);
		zeitPanel.		setBackground(Design.background1);
		alleProjekten.	setBackground(Design.background1);
		projektPanel.	setBackground(Design.background1);
		namePanel.		setBackground(Design.background1);
	}
	
	public void setFenster2(int x, int y) {
		west.removeAll();
		beschreibungMainPanel.removeAll();
		south.removeAll();
		
		setBounds(new Rectangle(x, y, width, height));
		setTitle("Bearbeiten Aufgabe");
		
		JPanel namePanel = new JPanel();
		namePanel.setMaximumSize(new Dimension(this.getWidth()/2, 40));
		namePanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		
		JLabel nameLBL = new JLabel("Name");
		nameLBL.setPreferredSize(new Dimension(80, 30));
		
		nameJTF = new JTextField(aufgabe.getName());
		nameJTF.setPreferredSize(new Dimension(180, 30));
		
		namePanel.add(nameLBL);
		namePanel.add(nameJTF);
		
		west.add(namePanel);
		
		JPanel projektPanel = new JPanel();
		projektPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		projektPanel.setMaximumSize(new Dimension(this.getWidth()/2, 40));
		
		JLabel projektLBL = new JLabel("Projekt");
		projektLBL.setPreferredSize(new Dimension(80, 30));
		
		alleProjekten.setPreferredSize(new Dimension(180, 30));
		
		projektPanel.add(projektLBL);
		
		int selected = aufgabe.getProjekt().getId()>0 ? aufgabe.getProjekt().getId()-1 : 0; 
		alleProjekten.setSelectedIndex(selected);
		projektPanel.add(alleProjekten);
		west.add(projektPanel);
		
		JPanel zeitPanel = new JPanel();
		zeitPanel.setLayout(new BoxLayout(zeitPanel, BoxLayout.PAGE_AXIS));
		zeitPanel.setBackground(Color.gray);
		
		zeitenliste.setListe(aufgabe.getZeitenliste());
		zeitPanel.add(zeitenliste);
		
		west.add(zeitPanel);
		
		beschreibungMainPanel.setLayout(new BoxLayout(beschreibungMainPanel, BoxLayout.Y_AXIS));
		beschreibungMainPanel.setAlignmentY(JComponent.LEFT_ALIGNMENT);
		JLabel beschreibungLBL = new JLabel("Beschreibung");
		beschreibungLBL.setMaximumSize(new Dimension(this.getWidth()/2, 40));
		JPanel beschreibungPanel = new JPanel();
		beschreibungPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		beschreibungPanel.add(beschreibungLBL);
		beschreibungJTA = new JTextArea(aufgabe.getBeschreibung());
		beschreibungJTA.setLineWrap(true);
		
		beschreibungMainPanel.add(beschreibungPanel);
		beschreibungMainPanel.add(beschreibungJTA);
		
		south.setLayout(new FlowLayout(FlowLayout.TRAILING));

		south.add(abbrechenBTN);
		south.add(speichernBTN);
		
//		add(north, BorderLayout.NORTH);
		add(west, BorderLayout.WEST);
		add(beschreibungMainPanel, BorderLayout.CENTER);
		add(south, BorderLayout.SOUTH);
		
	}

	public JComboBox<Projekt> getAlleProjekten() {
		return alleProjekten;
	}

	public void setAlleProjekten(JComboBox<Projekt> alleProjekten) {
		this.alleProjekten = alleProjekten;
	}

	public JTextArea getBeschreibungJTA() {
		return beschreibungJTA;
	}

	public void setBeschreibungJTA(JTextArea beschreibungJTA) {
		this.beschreibungJTA = beschreibungJTA;
	}


	public String getNameJTF() {
		return nameJTF.getText();
	}
	
	public void setNameJTF(String nameJTF) {
		this.nameJTF.setText(" hola ");
	}

	public Aufgabe getAufgabe() {
		aufgabe.setName(nameJTF.getText());
		aufgabe.setBeschreibung(beschreibungJTA.getText());
		aufgabe.setProjekt(alleProjekten.getItemAt(alleProjekten.getSelectedIndex()));
		
		return aufgabe;
	}

	public void setAufgabe(Aufgabe aufgabe) {
		this.aufgabe = aufgabe;
	}

	public JButton getSpeichernBTN() {
		return speichernBTN;
	}

	public JButton getAbbrechenBTN() {
		return abbrechenBTN;
	}

	
	public ZeitenlisteReihe getZeitenliste() {
		return zeitenliste;
	}

	public boolean isCanESC() {
		return canESC;
	}

	public void setCanESC(boolean canESC) {
		this.canESC = canESC;
	}

	public void setSpeicherEnable(boolean flag) {
		speichernBTN.setEnabled(flag);
	}

	public void setProjektAction(ActionListener action) {
		alleProjekten.addActionListener(action);
	}

	public void setCaretNameText(CaretListener caret) {
		nameJTF.addCaretListener(caret);
	}
	
	public void setKeyTypedNameText(KeyListener listener) {
//		nameJTF.addKeyListener(new KeyListener() {
//			
//			@Override
//			public void keyTyped(KeyEvent e) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void keyReleased(KeyEvent e) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void keyPressed(KeyEvent e) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
		nameJTF.addKeyListener((KeyListener)listener);
	}


}
