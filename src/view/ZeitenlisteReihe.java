package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import model.Zeit;

public class ZeitenlisteReihe extends JPanel{
	
//	private JButton loeschen = new JButton("-");
//	private JButton bearbeiten = new JButton("*");
//	private JPanel dauerPanel = new JPanel();
//	private JLabel dauerLBL = new JLabel("");
//	private JPanel datumPanel = new JPanel();
//	private JLabel dateLBL = new JLabel("");
//	private JLabel anfangLBL = new JLabel("");
//	private JLabel beendeLBL = new JLabel("");

//	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyy HH:mm:ss");
	
	private List<Zeit> zeitenliste = new ArrayList<Zeit>();
	private List<JButton> loeschenButtonListe = new ArrayList<JButton>();
	private DateFormat timeFormat = new SimpleDateFormat("HH:mm");
	private DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
	private NeueZeitReihe south = new NeueZeitReihe();
	private JPanel centro = new JPanel();
	private JScrollPane scroll = new JScrollPane();
	
	public ZeitenlisteReihe() {
		setLayout(new BorderLayout());
		JPanel headers = new JPanel();
		headers.setBackground(Design.background1);
		JLabel spalte1 = new JLabel("Datum");
		spalte1.setPreferredSize(new Dimension(140, 30));
		JLabel spalte2 = new JLabel("Anfang");
		spalte2.setPreferredSize(new Dimension(75, 30));
		JLabel spalte3 = new JLabel("Beende");
		spalte3.setPreferredSize(new Dimension(75, 30));
		JLabel spalte4 = new JLabel("Dauer");
		spalte3.setPreferredSize(new Dimension(80, 30));
		
		headers.setLayout(new FlowLayout(FlowLayout.LEADING));
		headers.add(Box.createRigidArea(new Dimension(30, 15)));
		headers.add(spalte1);
		headers.add(spalte2);
		headers.add(spalte3);
		headers.add(spalte4);
		
		add(headers, BorderLayout.NORTH);
		

		south.setBackground(Design.background1);
		add(south, BorderLayout.SOUTH);
		
	}
	
	public void setListe(List<Zeit> zeitenliste) {
		this.zeitenliste = zeitenliste;
		setListe();
	}
	
	public List<Zeit> getListe() {
		return zeitenliste;
	}
	
	public void setListe() {
		loeschenButtonListe.clear();
		centro.removeAll();
		centro.setLayout(new FlowLayout(FlowLayout.LEADING));
		centro.setPreferredSize(new Dimension(280, (zeitenliste.size()*45)+5));
		centro.setSize(new Dimension(280, (zeitenliste.size()*45)+5));
		centro.setBackground(Color.white);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setViewportView(centro);
		
		
		for (Zeit zeit : zeitenliste) {
			JPanel title = new JPanel();
			
			title.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 15));
//			title.setLayout(new BoxLayout(title, BoxLayout.LINE_AXIS));
			title.setBackground(new Color(190, 190, 190));
			title.setSize(new Dimension(430, 30));
			title.setPreferredSize(new Dimension(430, 40));
			
			JLabel datum = new JLabel(dateFormat.format(zeit.getAnfang()));
			datum.setPreferredSize(new Dimension(170, 15));
			
			JLabel spalte1 = new JLabel(timeFormat.format(zeit.getAnfang()));
			spalte1.setPreferredSize(new Dimension(80, 15));
			
			JLabel spalte2 = new JLabel(timeFormat.format(zeit.getBeende()));
			spalte2.setPreferredSize(new Dimension(80, 15));
			
			JLabel spalte3 = new JLabel(zeit.getDauerString().split(" ")[1]);
			spalte3.setPreferredSize(new Dimension(50, 15));
			
			JPanel button = new JPanel();
			button.setLayout(new FlowLayout(FlowLayout.LEADING));
			button.setBackground(Design.background1);
			button.setSize(new Dimension(40, 30));
			button.setPreferredSize(new Dimension(40, 40));
			button.setBorder(null);
			
			JButton loeschen = new JButton();
			ImageIcon icon = new ImageIcon("image/delete.png");
			loeschen.setIcon(icon);
			loeschen.setBackground(Color.WHITE);
			loeschen.setPreferredSize(new Dimension(30, 30));
//			loeschen.setMargin(new Insets(0, 0, 0, 0));
			loeschen.setBorder(null);
//			loeschen.setOpaque(true);
//			loeschen.setContentAreaFilled(false);
//			loeschen.setBorderPainted(false);
			loeschen.setCursor(new Cursor(Cursor.HAND_CURSOR));
			
//			loeschen.addMouseListener(new MouseListener() {
//				
//				@Override
//				public void mouseReleased(MouseEvent e) {
//					// TODO Auto-generated method stub
//					
//				}
//				
//				@Override
//				public void mousePressed(MouseEvent e) {
//					// TODO Auto-generated method stub
//					
//				}
//				
//				@Override
//				public void mouseExited(MouseEvent e) {
//					loeschen.setBorder(null);
//				}
//				
//				@Override
//				public void mouseEntered(MouseEvent e) {
//					loeschen.setBorder(new JButton("").getBorder());
//				}
//				
//				@Override
//				public void mouseClicked(MouseEvent e) {
//					// TODO Auto-generated method stub
//					
//				}
//			});
			
			title.add(Box.createRigidArea(new Dimension(15, 15)));
			title.add(datum);
			title.add(spalte1);
			title.add(spalte2);
			title.add(spalte3);
			
			button.add(loeschen);
			loeschenButtonListe.add(loeschen);
			title.setBackground(Design.reiheBG);
			centro.add(title);
			centro.add(button);
		}
		add(scroll, BorderLayout.CENTER);
		revalidate();
		repaint();
	}

	public void maxDigit(JTextField field) {
		if(field.getText().length()>2) {
			field.setText(field.getText().substring(0, 1));
		}
	}

	public NeueZeitReihe getNeueZeit() {
		return south;
	}

	public List<JButton> getLoeschenButtonListe() {
		return loeschenButtonListe;
	}

	public void setLoeschenButtonListe(List<JButton> loeschenButtonListe) {
		this.loeschenButtonListe = loeschenButtonListe;
	}


	
	
}
