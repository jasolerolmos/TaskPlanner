package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Jahr;
import model.Monate;
import model.Zeit;

public class ZeitNeueReihe extends JPanel{
	private MeineTextField neueAnfangStunde = new MeineTextField(23);
	private MeineTextField neueAnfangMinuten = new MeineTextField(59);
	private MeineTextField neueBeendeStunde = new MeineTextField(23);
	private MeineTextField neueBeendeMinuten = new MeineTextField(59);
	private JButton speichern = new JButton("Hinzufügen");
	private JComboBox<String> jahres;
	private JComboBox<String> tages;
	private JComboBox<Monate> monaten;
	private String neueAnfang, neueBeende;
	private JLabel dauerLBL = new JLabel("Dauer");

	
	public ZeitNeueReihe() {
		setLayout(new FlowLayout(FlowLayout.LEADING));
		setPreferredSize(new Dimension(280, 70));
		setSize(new Dimension(280, 70));
		
		JPanel datePanel = new JPanel();
		datePanel.setLayout(new FlowLayout(FlowLayout.LEADING));
//		datePanel.setLayout(new BoxLayout(datePanel, BoxLayout.LINE_AXIS));
		datePanel.setPreferredSize(new Dimension(480, 30));
		datePanel.setSize(new Dimension(480, 30));
		datePanel.setBackground(Design.background1);
		
		jahres = new JComboBox<String>();	
		monaten = new JComboBox<Monate>();
		tages = new JComboBox<String> ();
		
		for(int i=Calendar.getInstance().get(Calendar.YEAR)-1;i<=Calendar.getInstance().get(Calendar.YEAR)+2;i++)
			jahres.addItem(String.valueOf(i));	
		datePanel.add(jahres);
		jahres.setSelectedIndex(1);
		jahres.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				setMonatenBox();
			}	
		});
		
		setMonatenBox();
		datePanel.add(monaten);
		
		
		datePanel.add(tages);
		setTagesBox();
		monaten.setSelectedIndex(Calendar.getInstance().get(Calendar.MONTH));
		tages.setSelectedIndex(Calendar.getInstance().get(Calendar.DATE)-1);
		
		speichern.setEnabled(false);
		
//		speichern.setPreferredSize(new Dimension((int)dd.getWidth()-1, (int)dd.getWidth()-1));
//		speichern.setMargin(new Insets(0, 0, 0, 0));
		
//		datePanel.add(dauerLBL);
		
		neueAnfangStunde.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				checkField(neueAnfangStunde);
				checkZeit();
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				neueAnfangStunde.selectAll();
			}
		});
		neueAnfangMinuten.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				checkField(neueAnfangMinuten);
				checkZeit();
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				neueAnfangMinuten.selectAll();
			}
		});
		neueBeendeStunde.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				checkField(neueBeendeStunde);
				checkZeit();
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				neueBeendeStunde.selectAll();
			}
		});
		neueBeendeMinuten.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				checkField(neueBeendeMinuten);
				checkZeit();
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				neueBeendeMinuten.selectAll();
			}
		});

		datePanel.add(Box.createRigidArea(new Dimension(30, 15)));
		datePanel.add(new JLabel("Anfang "));
		datePanel.add(neueAnfangStunde);
		datePanel.add(new JLabel(":"));
		datePanel.add(neueAnfangMinuten);
		datePanel.add(new JLabel("Beende "));
		datePanel.add(neueBeendeStunde);
		datePanel.add(new JLabel(":"));
		datePanel.add(neueBeendeMinuten);
		
		
		add(datePanel);
		add(Box.createRigidArea(new Dimension(0, 15)));
		add(speichern);
	}

	public MeineTextField getNeueAnfangStunde() {
		return neueAnfangStunde;
	}

	public void setNeueAnfangStunde(MeineTextField neueAnfangStunde) {
		this.neueAnfangStunde = neueAnfangStunde;
	}

	public MeineTextField getNeueAnfangMinuten() {
		return neueAnfangMinuten;
	}

	public void setNeueAnfangMinuten(MeineTextField neueAnfangMinuten) {
		this.neueAnfangMinuten = neueAnfangMinuten;
	}

	public MeineTextField getNeueBeendeStunde() {
		return neueBeendeStunde;
	}

	public void setNeueBeendeStunde(MeineTextField neueBeendeStunde) {
		this.neueBeendeStunde = neueBeendeStunde;
	}

	public MeineTextField getNeueBeendeMinuten() {
		return neueBeendeMinuten;
	}

	public void setNeueBeendeMinuten(MeineTextField neueBeendeMinuten) {
		this.neueBeendeMinuten = neueBeendeMinuten;
	}

	public void checkField(MeineTextField field) {
		try {
			if(Integer.parseInt(field.getText().toString())>field.getMaxValue()) {
//				field.setText(String.valueOf(field.getMaxValue()));
				field.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
			}
			else {
				field.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
			}
		}
		catch (NumberFormatException nfe) {
//			field.setText("00");
			field.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
		}
	}
	
	public void checkZeit() {
		int anfangS=0,  beendeS=0,  anfangM=0,  beendeM=0;

		speichern.setEnabled(true);
		try {
			anfangS = Integer.parseInt(neueAnfangStunde.getText().toString());
		}
		catch (Exception e) {
			speichern.setEnabled(false);
		}
		try {
			beendeS = Integer.parseInt(neueBeendeStunde.getText().toString());
		}
		catch (Exception e) {	
			speichern.setEnabled(false);
		}
		try {
			anfangM = Integer.parseInt(neueAnfangMinuten.getText().toString());
		}
		catch (Exception e) {	
			speichern.setEnabled(false);
		}
		try {
			beendeM = Integer.parseInt(neueBeendeMinuten.getText().toString());
		}
		catch (Exception e) {	
			speichern.setEnabled(false);
		}
		
		if(anfangS>beendeS) {
			speichern.setEnabled(false);
		}
		else {
			if(anfangS==beendeS) {
				if(anfangM>beendeM) {
					speichern.setEnabled(false);
				}
			}
		}
		
		if(speichern.isEnabled()) {
			String tag = (( (String)tages.getSelectedItem() ).length()>1 ) ? (String)tages.getSelectedItem() : "0"+(String)tages.getSelectedItem();
			String mon = String.valueOf(( (Monate) monaten.getSelectedItem()).getMonate());
			mon = (mon.length()<2) ? "0"+mon : mon;
			
			String stundeAnfang = neueAnfangStunde.getText().toString();
			String minuteAnfang = neueAnfangMinuten.getText().toString();
			String stundebeende = neueBeendeStunde.getText().toString();
			String minuteBeende = neueBeendeMinuten.getText().toString();
			
			neueAnfang = tag+"."+mon+"."+(String)jahres.getSelectedItem()+" "+stundeAnfang+":"+minuteAnfang+":00";
			neueBeende = tag+"."+mon+"."+(String)jahres.getSelectedItem()+" "+stundebeende+":"+minuteBeende+":00";
			
			try {
				DateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
				Date anfang = format.parse(neueAnfang);
				Date beende = format.parse(neueBeende);
				Zeit z = new Zeit(anfang, beende);
				dauerLBL.setText("Dauer: "+z.getDauerString().split(" ")[1]);
				
			} catch (Exception e) {
			}
		}
	}

	public void setMonatenBox() {
		monaten.removeAllItems();
		if(tages.getComponentCount()>0)
			tages.removeAllItems();
		Jahr jahr = new Jahr();
		for(int i=0;i<jahr.size();i++)
			monaten.addItem(jahr.get(i));
		monaten.setSelectedIndex(0);
		monaten.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				setTagesBox();
			}
		});
	}
	
	public void setTagesBox() {
		Calendar cal = Calendar.getInstance();
		
		cal.set(Integer.parseInt((String)jahres.getSelectedItem()), (( monaten.getSelectedIndex() < 0 ) ? 0 : monaten.getSelectedIndex()), 1); //((Monate)monaten.getSelectedItem()).getMonate()
		tages.removeAllItems();
		for(int i=1;i<=cal.getActualMaximum(Calendar.DAY_OF_MONTH);i++)
			tages.addItem(String.valueOf(i));
		
	}
	
	public JButton getSpeichern() {
		return speichern;
	}

	public String getNeueAnfang() {
		return neueAnfang;
	}

	public String getNeueBeende() {
		return neueBeende;
	}
	
	
}
