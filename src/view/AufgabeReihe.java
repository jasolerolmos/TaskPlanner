package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolTip;

import model.Aufgabe;

public class AufgabeReihe extends JPanel{
	private Aufgabe aufgabe;
	private Dimension dimName = new Dimension(120, 30);
	private Dimension dimProj = new Dimension(100, 30);
	private Dimension dimDauer = new Dimension(80, 30);
	private JButton bearbeiten = new JButton("");
	private JButton loeschen = new JButton("");
	private JButton mas = new JButton("");
	private JLabel beschreibung;
	private int marginRight = 25;

	public AufgabeReihe(Aufgabe a, int widthVater) {
		this.aufgabe = a;

		ImageIcon masIcon = new ImageIcon("image/mas.png");	
		Dimension dimensionAufgabenListe = new Dimension(widthVater-marginRight, 10);
		Dimension dimBesc = new Dimension((int)(dimensionAufgabenListe.getWidth()-480), 30);
		
		
		this.setPreferredSize(dimensionAufgabenListe);
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setBackground(Design.reiheBG);
		
		JLabel name = new JLabel(a.getName());
		name.setPreferredSize(dimName);
		
		JLabel projekt = new JLabel(a.getProjekt().getName());
		projekt.setPreferredSize(dimProj);
		
		beschreibung = new JLabel(a.getBeschreibung());
		beschreibung.setPreferredSize(dimBesc);
		JToolTip tipBeschreibung = new JToolTip();
		tipBeschreibung.setTipText(a.getBeschreibung());
		beschreibung.setToolTipText("<html>"+a.getBeschreibung()+"</html>");
		
		JLabel dauer = new JLabel(a.getDauer());
		dauer.setPreferredSize(dimDauer);
		
		ImageIcon editIcon = new ImageIcon("image/edit.png");
		ImageIcon loeschenIcon = new ImageIcon("image/delete.png");
		bearbeiten.setIcon(editIcon);
		bearbeiten.setBackground(Color.WHITE);
		bearbeiten.setOpaque(false);
		bearbeiten.setBorder(null);
		bearbeiten.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		loeschen.setIcon(loeschenIcon);
		loeschen.setBackground(Color.WHITE);
		loeschen.setOpaque(false);
		loeschen.setBorder(null);		
		loeschen.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		if(a.getBeschreibung().length()>0) {
			mas.setIcon(masIcon);
		}
		mas.setBackground(Color.WHITE);
		mas.setOpaque(false);
		mas.setBorder(null);
		mas.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		
		this.add(name);
		this.add(projekt);
		this.add(beschreibung);
		this.add(mas);
		this.add(Box.createRigidArea(new Dimension(10, 30)));
		this.add(dauer);
		this.add(bearbeiten);
		this.add(loeschen);
	}

	public JButton getBearbeiten() {
		return bearbeiten;
	}

	public void setBearbeiten(JButton bearbeiten) {
		this.bearbeiten = bearbeiten;
	}

	public JButton getLoeschen() {
		return loeschen;
	}

	public void setLoeschen(JButton loeschen) {
		this.loeschen = loeschen;
	}

	public Aufgabe getAufgabe() {
		return aufgabe;
	}

	public void setAufgabe(Aufgabe aufgabe) {
		this.aufgabe = aufgabe;
	}

	public JButton getMas() {
		return mas;
	}

	public void setMas(JButton mas) {
		this.mas = mas;
	}

	public String getBeschreibung() {
		return beschreibung.getText();
	}

	public void setBeschreibung(String beschreibung) {
		this.beschreibung.setText(beschreibung);
	}

	public void setBeschreibungSize(int size) {
		int height = (int)(size-427);
		if(beschreibung.getText().length()>0)
			height = (int)(size-443);
		beschreibung.setPreferredSize(new Dimension(height, 30));
		
	}
	
	public void setOkAction(ActionListener actionListener) {
		mas.addActionListener(actionListener);
		
	}
	
}
