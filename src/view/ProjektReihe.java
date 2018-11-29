package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Projekt;

public class ProjektReihe  extends JPanel{
	private Projekt projekt;
	private JTextField nameEdit ;
	private JLabel nameLBL; 
	private JButton loeschen = new JButton();
	private int width = 580, height = 50;
	
	public ProjektReihe(Projekt projekt) {
		this.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.setSize(new Dimension(width, height));
		this.projekt = projekt;
		nameEdit = new JTextField(projekt.getName());
		nameEdit.setVisible(false);
		nameLBL = new JLabel(projekt.getName());
		loeschen.setPreferredSize(new Dimension(50, 35));
		nameLBL.setPreferredSize(new Dimension(width-60, 20));
		nameEdit.setPreferredSize(new Dimension(width-60, 20));
		

		ImageIcon loeschenIcon = new ImageIcon("image/delete.png");
		loeschen.setIcon(loeschenIcon);
		loeschen.setBackground(Color.WHITE);
		loeschen.setOpaque(false);
		loeschen.setBorder(null);
		loeschen.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		setBackground(Design.reiheBG);
		add(nameEdit);
		add(nameLBL);
		add(loeschen);
	}
	
	public void Editable(boolean status) {
		nameEdit.setVisible(status);
		nameLBL.setVisible(!status);
	}

	public JTextField getNameEdit() {
		return nameEdit;
	}

	public JLabel getNameLBL() {
		return nameLBL;
	}

	public Projekt getProjekt() {
		return projekt;
	}

	public void setProjekt(Projekt projekt) {
		this.projekt = projekt;
	}

	public JButton getLoeschen() {
		return loeschen;
	}

	
}
