package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import model.Projekt;


public class ProjektFenster extends JDialog{

	private int width = 600, height = 500;	
	private JPanel north, south, center;
	private List<Projekt> liste = new ArrayList<Projekt> ();
	private List<ProjektReihe> listeReihe = new ArrayList<ProjektReihe> ();
	private JButton speichern;
	private JTextField neueName;
	private JLabel message = new JLabel("");
	JScrollPane scrollProjekten = new JScrollPane();
	
	public ProjektFenster() {
		setBounds(0, 0, width, height);
		setResizable(false);
		setLayout(new BorderLayout());
//		setUndecorated(true);
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBackground(Color.WHITE);

	}
	
	public void setFenster(int x, int y) {
		setBounds(new Rectangle(x, y, width, height));
		
		north = new JPanel();
		north.setLayout(new FlowLayout());
		north.setBackground(Design.background2);
		
		JLabel title = new JLabel("Projekten");
		title.setFont(new Font("Verdanad", Font.BOLD, 22));
		title.setForeground(Design.title1);

		north.add(title);

		center = new JPanel();
		center.setBackground(Design.background1);
		
		setListeProjekten();
		
		south = new JPanel();
		south.setLayout(new BorderLayout());
		south.setBackground(Design.background1);
		
		JPanel neueProjektPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		neueProjektPanel.setBackground(Design.background1);
		
		JLabel titleNeue = new JLabel("Neue Projekt");
		titleNeue.setFont(new Font("Verdana", Font.BOLD, 18));
		
		neueProjektPanel.add(Box.createRigidArea(new Dimension(10, 15)));
		neueProjektPanel.add(titleNeue);
		neueProjektPanel.add(Box.createRigidArea(new Dimension(10, 15)));
		
		message.setForeground(Design.errorMessage);
		message.setFont(new Font("Verdana", Font.PLAIN, 12));
		neueProjektPanel.add(message);
		
		south.add(neueProjektPanel, BorderLayout.NORTH);		
		
		JPanel neuePanel = new JPanel();
		neuePanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		neuePanel.setBackground(Design.background1);
		
		JLabel neueNameLBL = new JLabel("Name");
		neueNameLBL.setPreferredSize(new Dimension(50, 30));
		neuePanel.add(neueNameLBL);
		
		neueName = new JTextField();
		neueName.setPreferredSize(new Dimension(width-180, 30));
		
		neuePanel.add(neueName);
		
		speichern = new JButton("Sprichern");
		speichern.setPreferredSize(new Dimension(100, 30));
		neuePanel.add(speichern);
		south.add(neuePanel, BorderLayout.SOUTH);

		scrollProjekten.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollProjekten.setViewportView(center);
		
		add(north,BorderLayout.NORTH);
		add(scrollProjekten, BorderLayout.CENTER);
		add(south, BorderLayout.SOUTH);
		
	}

	public void setListeProjekten() {
		center.removeAll();
		center.setLayout(new FlowLayout(FlowLayout.LEADING));
		center.setPreferredSize(new Dimension(this.getWidth()-10, liste.size()*51));

		center.setBackground(Color.WHITE);
		
		listeReihe.clear();
		
		for (Projekt projekt : liste) {
			ProjektReihe pr = new ProjektReihe(projekt);
			listeReihe.add(pr);
			center.add(pr);
		}


		scrollProjekten.setViewportView(center);
		revalidate();
		repaint();
	}
	
	public List<Projekt> getListe() {
		return liste;
	}

	public void setListe(List<Projekt> liste) {
		this.liste = liste;
	}

	public List<ProjektReihe> getListeReihe() {
		return listeReihe;
	}

	public void setListeReihe(List<ProjektReihe> listeReihe) {
		this.listeReihe = listeReihe;
	}
	
	public JButton getSpeichern() {
		return speichern;
	}

	public void setSpeichernEnable(boolean flag) {
		speichern.setEnabled(flag);
	}
	
	public void setCaretNeueProjekt(CaretListener cListener) {
		neueName.addCaretListener(cListener);
	}
	
	public void setSpeichern(JButton speichern) {
		this.speichern = speichern;
	}

	public String getNeueName() {
		return neueName.getText();
	}

	public void setNeueName(JTextField neueName) {
		this.neueName = neueName;
	}

	public void setMessageFehler(String text) {
		message.setText(text);
	}
	
	
}
