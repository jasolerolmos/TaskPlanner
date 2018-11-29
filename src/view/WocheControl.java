package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class WocheControl extends JPanel {
	private JButton vor = new JButton();
	private JButton nach = new JButton();
	private JLabel label = new JLabel();
	private JLabel datum = new JLabel();
	private Calendar heute = Calendar.getInstance();
	private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
	private Date montagDate = new Date();
	private Date sonntagDate = new Date();
	private String montag = "";
	private String sonntag ="";
	
	public WocheControl() {
		setLayout(new FlowLayout());
		vor.setMargin(new Insets(0, 0, 0, 0));
		nach.setMargin(new Insets(0, 0, 0, 0));
		ImageIcon vorIco = new ImageIcon("image/pre.png");
		ImageIcon nachIco = new ImageIcon("image/next.png");
		vor.setIcon(vorIco);
		nach.setIcon(nachIco);
		
		vor.setPreferredSize(new Dimension(16, 16));
		nach.setPreferredSize(new Dimension(16, 16));
		
		label.setText("Woche: "+heute.get(Calendar.WEEK_OF_YEAR));
		
		add(vor);
		add(label);
		add(nach);
		add(datum);
		setWochenText();

	}
	public JButton getVor() {
		return vor;
	}
	public void setVor(JButton vor) {
		this.vor = vor;
	}
	public JButton getNach() {
		return nach;
	}
	public void setNach(JButton nach) {
		this.nach = nach;
	}
	public JLabel getLabel() {
		return label;
	}
	public void setLabel(JLabel label) {
		this.label = label;
	}
	public Calendar getHeute() {
		return heute;
	}
	public void setHeute(Calendar heute) {
		this.heute = heute;
	}
	
	private void actionVorWoche() {

		System.out.println("Anterior");
		try {
			heute.add(Calendar.WEEK_OF_YEAR, -1);
			System.out.println("Hoy: "+heute.get(Calendar.WEEK_OF_YEAR));
			label.setText("Woche: ");
		} catch (Exception e) {
			System.out.println("ERROR");
		}
		
	}
	
	private void actionNachWoche() {

		System.out.println("Anterior");
		try {
			heute.add(Calendar.WEEK_OF_YEAR, 1);
			label.setText("Woche: "+heute.get(Calendar.WEEK_OF_YEAR));
		} catch (Exception e) {
			System.out.println("ERROR");
		}	
	}
	
	public void setWochenText() {;
		if(heute.get(Calendar.DAY_OF_WEEK)!=2) {
			heute.add(Calendar.DATE, -(heute.get(Calendar.DAY_OF_WEEK)-2));
		}
		montag = sdf.format(heute.getTime());
		montagDate = heute.getTime();
		heute.add(Calendar.DATE, 6);
		sonntag = sdf.format(heute.getTime());
		sonntagDate = heute.getTime();
		datum.setText(montag+" - "+sonntag);
		heute.add(Calendar.DATE, -6);
	}
	public Date getMontagDate() {
		return montagDate;
	}
	public void setMontagDate(Date montagDate) {
		this.montagDate = montagDate;
	}
	public Date getSonntagDate() {
		return sonntagDate;
	}
	public void setSonntagDate(Date sonntagDate) {
		this.sonntagDate = sonntagDate;
	}
	public String getMontag() {
		return montag;
	}
	public void setMontag(String montag) {
		this.montag = montag;
	}
	public String getSonntag() {
		return sonntag;
	}
	public void setSonntag(String sonntag) {
		this.sonntag = sonntag;
	}
	
}
