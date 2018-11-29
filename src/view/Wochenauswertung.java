package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;

import model.AufgabeAnsicht;

public class Wochenauswertung extends JDialog{
	private int width = 700, height = 500;
	private JPanel main = new JPanel();
	private JPanel content = new JPanel();
	private JPanel titlePanel = new JPanel();
	private JPanel optionPanel = new JPanel();
	private JTabbedPane mainPanel = new JTabbedPane();
	private JButton nachStunde = new JButton("Nach Stunde");
	private JButton nachProjekt = new JButton("Nach Projekt");
	private WocheControl wocheControl = new WocheControl();
	private List<JPanel> panelList = new ArrayList<JPanel>();
	private List<AufgabeAnsicht> zuZeien = new ArrayList<AufgabeAnsicht>();
	private int mode = 0;

	public Wochenauswertung() {
		setSize(width,  height);
		setLocationRelativeTo(null);
		setResizable(false);
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBackground(Design.background1);

		main.setLayout(new BorderLayout());
		titlePanel.setLayout(new FlowLayout());
		content.setLayout(new BorderLayout());
		optionPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		
		JLabel title = new JLabel("Wochenauswertung");
		title.setFont(new Font("Verdana", Font.BOLD, 22));
		title.setForeground(Design.title1);


		nachProjekt.setBackground(Design.buttonBG);

		nachStunde.setBackground(Design.buttonBG2);
		
		optionPanel.add(nachProjekt);
		optionPanel.add(nachStunde);
		optionPanel.add(wocheControl);
		
		String[] wochenTag = {"Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Samstag", "Sonntag"};

		UIManager.put("TabbedPane.selected", new ColorUIResource(Design.background2));
		UIManager.put("TabbedPane.unselectedbackground", new ColorUIResource(Design.background2));
		UIManager.put("TabbedPane.tabInsets", new Insets(8, 22, 8, 23));
		
		mainPanel.updateUI();
		for (String string : wochenTag) {
			JPanel panel = new JPanel();
			panel.setBackground(Design.background1);
			mainPanel.addTab(string, panel);
			panelList.add(panel);
		}
		
		content.add(optionPanel, BorderLayout.NORTH);
		content.add(mainPanel, BorderLayout.CENTER);
		main.add(titlePanel, BorderLayout.NORTH);
		main.add(content, BorderLayout.CENTER);
				
		add(main);
	}

	public WocheControl getWocheControl() {
		return wocheControl;
	}

	public void setWocheControl(WocheControl wocheControl) {

		System.out.println("setWocheControl");
		this.wocheControl = wocheControl;
	}

	public JTabbedPane getMainPanel() {
		return mainPanel;
	}
	
	public void setAufgabeListeAnfang(List<AufgabeAnsicht> zuZeien) {
		this.zuZeien = zuZeien.stream().sorted((aa,ab) -> aa.getZeit().getAnfang().compareTo(ab.getZeit().getAnfang())).collect(Collectors.toList());
		showAufgabe();
	}
	public void setAufgabeListeProjekt(List<AufgabeAnsicht> zuZeien) {
		this.zuZeien = zuZeien.stream().sorted((aa,ab) -> aa.getProjekt().compareTo(ab.getProjekt())).collect(Collectors.toList());
		showAufgabe();
	}	
	private void showAufgabe() {
		panelList.get(mainPanel.getSelectedIndex()).removeAll();
		for (AufgabeAnsicht aufgabeAnsicht : zuZeien) {
			panelList.get(mainPanel.getSelectedIndex()).add(new AufgabeAnsichtLinie(aufgabeAnsicht, this.getWidth()));
		}
		revalidate();
		repaint();
	}
	

	public JButton getNachStunde() {
		return nachStunde;
	}

	public void setNachStunde(JButton nachStunde) {
		this.nachStunde = nachStunde;
	}

	public JButton getNachProjekt() {
		return nachProjekt;
	}

	public void setNachProjekt(JButton nachProjekt) {
		this.nachProjekt = nachProjekt;
	}

	
	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	
}
