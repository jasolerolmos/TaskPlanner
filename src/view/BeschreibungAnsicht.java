package view;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class BeschreibungAnsicht extends JScrollPane{
	private JPanel panel = new JPanel();

	public BeschreibungAnsicht(String beschreibung, int widthVater) {
		this.setBackground(Color.RED);
		JTextArea textArea = new JTextArea();

		Dimension dimensionAufgabenListe = new Dimension(widthVater-25, 150);
		panel.setPreferredSize(dimensionAufgabenListe);
		
		panel.add(textArea);
		textArea.setText(beschreibung);
		textArea.setColumns(50);
		textArea.setRows(8);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		textArea.setBackground(Design.reiheBG);
		textArea.setBorder(null);
		add(panel);
		setViewportView(textArea);
		
		setVisible(false);
	}

	@Override
	public void setVisible(boolean aFlag) {
		super.setVisible(aFlag);
	}

	public void setNewSize(int width) {
		Dimension dimensionAufgabenListe = new Dimension(width-25, 150);
//		panel.setPreferredSize(dimensionAufgabenListe);
		this.setPreferredSize(dimensionAufgabenListe);
	}
	
	public JPanel getPanel() {
		return panel;
	}

	public void setPanel(JPanel panel) {
		this.panel = panel;
	}
	
	
}
