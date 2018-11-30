package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class BeschreibungAnsicht extends JScrollPane{
	private JPanel panel = new JPanel();
	private int margin = 40;
	private String text = "";

	public BeschreibungAnsicht(String beschreibung, int widthVater) {
		text = beschreibung;
		this.setBackground(Color.RED);
		JTextArea textArea = new JTextArea();
		
		panel.add(textArea);
		textArea.setText(beschreibung);
		textArea.setColumns(50);
		textArea.setRows(8);
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		textArea.setBackground(Design.reiheBG);
		textArea.setBorder(null);
		add(panel);
		setViewportView(textArea);

		Dimension dimensionAufgabenListe = new Dimension(widthVater-margin, 150);
		panel.setPreferredSize(dimensionAufgabenListe);
		
		setVisible(false);
	}

	
	
	@Override
	public void paint(Graphics g) {
		setNewSize(this.getWidth()+margin);
		super.paint(g);
	}



	private void setMargin() {
		if(this.getVerticalScrollBar().isVisible()) {
			margin = 40;
		}
		else 
			margin = 25;
		}
	
	public void setNewSize(int width) {
		setMargin();

		Dimension dimensionAufgabenListe = new Dimension(width-margin, 150);
		
		this.setPreferredSize(dimensionAufgabenListe);

		revalidate();
		repaint();
	}
	
	public JPanel getPanel() {
		return panel;
	}

	public void setPanel(JPanel panel) {
		this.panel = panel;
	}
	
	
}
