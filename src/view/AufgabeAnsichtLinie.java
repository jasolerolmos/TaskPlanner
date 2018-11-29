package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.text.SimpleDateFormat;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolTip;

import model.AufgabeAnsicht;

public class AufgabeAnsichtLinie extends JPanel{
	private Dimension dimName = new Dimension(180, 30);
	private Dimension dimProj = new Dimension(120, 30);
	private Dimension dimDauer = new Dimension(70, 30);
	private int marginRight = 25;
	private AufgabeAnsicht aufgabe;
	private SimpleDateFormat sdf = new SimpleDateFormat("kk:mm");

	public AufgabeAnsichtLinie(AufgabeAnsicht a, int widthVater) {
		this.aufgabe = a;

		Dimension dimensionAufgabenListe = new Dimension(widthVater-marginRight, 40);
		Dimension dimBesc = new Dimension((int)(dimensionAufgabenListe.getWidth()-590), 30);
		
		this.setPreferredSize(dimensionAufgabenListe);
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setBackground(Design.reiheBG);
		
		JLabel name = new JLabel(a.getName());
		name.setPreferredSize(dimName);
		
		JLabel projekt = new JLabel(a .getProjekt());
		projekt.setPreferredSize(dimProj);
		
		JLabel zeiten = new JLabel("");
		zeiten.setPreferredSize(dimBesc);
		
		JLabel anfang = new JLabel(sdf.format(a.getZeit().getAnfang()));
		anfang.setPreferredSize(dimDauer);

		JLabel beende = new JLabel(sdf.format(a.getZeit().getBeende()));
		beende.setPreferredSize(dimDauer);
		
		JLabel dauer = new JLabel(a.getZeit().getDauerString());
		dauer.setPreferredSize(dimDauer);
		
		this.add(name);
		this.add(projekt);
		this.add(zeiten);
		this.add(Box.createRigidArea(new Dimension(10, 30)));
		this.add(anfang);
		this.add(beende);
		this.add(dauer);
	}
}

