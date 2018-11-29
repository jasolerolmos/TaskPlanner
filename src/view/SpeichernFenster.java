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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.Aufgabe;

public class SpeichernFenster extends JDialog{
	private int width = 600, height = 500;	
	private JPanel north, south, center;
	
	private JButton speichernBTN = new JButton("Speichern");
	private JButton quellBTN = new JButton("Quell");
	private JLabel dateiLBL = new JLabel("");
	private JPanel dateiPanel = new JPanel();
	
	private List<Aufgabe> liste = new ArrayList<Aufgabe>();
	
	public SpeichernFenster() {
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
		north.setBackground(Design.background2);
		north.setLayout(new FlowLayout());
		JLabel title = new JLabel("Speichern");
		title.setFont(new Font("Verdana", Font.BOLD, 22));
		title.setForeground(Design.title1);
		north.add(title);

		center = new JPanel();
		setListeAufgaben();
		
		south = new JPanel();
		south.setLayout(new BorderLayout());
		south.setBackground(Design.background2);
		
		quellBTN = new JButton("Quell");
		quellBTN.setPreferredSize(new Dimension(100, 30));

		dateiPanel = new JPanel();
		dateiPanel.setBackground(Design.background2);
		dateiLBL = new JLabel("");
		dateiPanel.add(dateiLBL);
		south.add(dateiPanel, BorderLayout.CENTER);
		
		speichernBTN = new JButton("Sprichern");
		speichernBTN.setPreferredSize(new Dimension(100, 30));
		speichernBTN.setBackground(Design.background1);
		
		south.add(speichernBTN, BorderLayout.EAST);
		
		JScrollPane scrollCenter = new JScrollPane(center);
		scrollCenter.setHorizontalScrollBar(null);
		add(north,BorderLayout.NORTH);
		add(scrollCenter, BorderLayout.CENTER);
		add(south, BorderLayout.SOUTH);
		
	}
	

	public void setListeAufgaben() {
		center.removeAll();
		center.setLayout(new FlowLayout());
		center.setPreferredSize(new Dimension(600, 300));
		
		center.setLayout(new FlowLayout(FlowLayout.LEADING));
		center.setPreferredSize(new Dimension(this.getWidth(), 300));
		center.setSize(new Dimension(this.getWidth(), 300));
		center.setBackground(Design.background1);
		
		for (Aufgabe aufgabe : liste) {
			JLabel linie = new JLabel("  "+aufgabe.getProjekt().getName()+" => "+aufgabe.getName());
			linie.setBackground(Design.reiheBG);
			linie.setPreferredSize(new Dimension(545, 30));
			linie.setOpaque(true);
			JLabel check = new JLabel();
			check.setIcon(new ImageIcon("image/check.png"));
			check.setBackground(Design.background1);
			check.setPreferredSize(new Dimension(32, 30));
			check.setOpaque(true);
			
			center.add(linie);
			center.add(check);
		}

		revalidate();
		repaint();
	}
	
	public JButton getSpeichern() {
		return speichernBTN;
	}	
	
	public JButton getQuellBTN() {
		return quellBTN;
	}
	
	public void ShowDatei(String name) {
		dateiLBL.setText(name);
	}

	public String getDatei() {
		return dateiLBL.getText().toString();
	}
	
	public List<Aufgabe> getListe() {
		return liste;
	}

	public void setListe(List<Aufgabe> liste) {
		this.liste = liste;
	}
	
	
}
