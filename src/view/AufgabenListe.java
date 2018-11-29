package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.Aufgabe;

public class AufgabenListe extends JScrollPane{
	private JPanel aufgabenPanel;
	private List<Aufgabe> aufgaben = new ArrayList<Aufgabe>();
//	private List<JPanel> aufgabenlistPanel = new ArrayList<JPanel>();
	private List<AufgabeReihe> aufgabenlistPanel = new ArrayList<AufgabeReihe>();
	private List<BeschreibungAnsicht> beschreibungPanel = new ArrayList<BeschreibungAnsicht>();
	private List<JButton> bearbeitenBtnListe = new ArrayList<JButton>();
//	private List<JButton> loeschenBtnListe = new ArrayList<JButton>();
	private int heightAufgabe = 52;
	private int marginRight = 25;
	
	public AufgabenListe() {
		aufgabenPanel = new JPanel();
		aufgabenPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		aufgabenPanel.setPreferredSize(new Dimension(this.getWidth()-marginRight, 300));
		aufgabenPanel.setSize(new Dimension(this.getWidth()-marginRight, 300));
		aufgabenPanel.setBackground(Design.background1);
		
		this.setViewportView(aufgabenPanel);
	}
	
	public void setAufgaben(List<Aufgabe> aufgaben) {
		this.aufgaben = aufgaben;
	}
	
	public List<Aufgabe> getAufgaben() {
		return aufgaben;
	}

	public List<AufgabeReihe> getAufgabenListe() {
		return aufgabenlistPanel;
	}
	
	public int getAufgabenNummer() {
		return aufgabenlistPanel.size();
	}
	
	public void paintAufgabenListe() {
		aufgabenlistPanel.clear();
		aufgabenPanel.removeAll();
		for (Aufgabe a : aufgaben) {
			AufgabeReihe ar = new AufgabeReihe(a, this.getWidth());
			BeschreibungAnsicht panel = new BeschreibungAnsicht(a.getBeschreibung(), this.getWidth());

			ar.setOkAction(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if(panel.isVisible())
						panel.setVisible(false);
					else
						panel.setVisible(true);
					ResizeComponents();
				}
			});
			
			aufgabenlistPanel.add(ar);
			beschreibungPanel.add(panel);
			
			aufgabenPanel.add(ar);
			if(a.getBeschreibung().length()>0) {
				aufgabenPanel.add(panel);
			}
		}
		ResizeComponents();
	}

	public void ResizeComponents() {
		Dimension dimensionAufgabenListe = new Dimension(this.getWidth()-13, 43);
		for (AufgabeReihe jPanel : aufgabenlistPanel) {
			jPanel.setPreferredSize(dimensionAufgabenListe);
			jPanel.setBeschreibungSize((int)dimensionAufgabenListe.getWidth());
		}
		int count = 0;
		for (BeschreibungAnsicht panel : beschreibungPanel) {
			panel.setNewSize(this.getWidth()+13);
			if(panel.isVisible())
				count++;
		}
		
		int height = (aufgabenlistPanel.size()*heightAufgabe)+(count*125);
		
		aufgabenPanel.setPreferredSize(new Dimension(this.getWidth()-marginRight, height));
		aufgabenPanel.setSize(new Dimension(this.getWidth()-marginRight, height));
		
		revalidate();
		repaint();
	}

	public List<JButton> getBearbeitenBtnListe() {
		return bearbeitenBtnListe;
	}

	public void Loeschen(int id) {
		Aufgabe af = null;
		for (Aufgabe aufgabe : aufgaben) {
			if(aufgabe.getId()==id) {
				af = aufgabe;
			}
		}
		if(af!=null)
			aufgaben.remove(af);
	}
	
}
