package view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.Anwender;

public class AdminSecret extends JDialog{
	private int width = 700, height = 500;
	private JPanel main = new JPanel();
	private List<Anwender> anwendernliste = new ArrayList<Anwender>(); 
	
	private JTable tableAnwender;

	public AdminSecret(List<Anwender> anwendernliste) {
		this.anwendernliste = anwendernliste;
		setSize(width,  height);
		setLocationRelativeTo(null);
		setResizable(false);
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBackground(Design.background1);
		
		
		Object[] columnNames = {"Vorname",
                "Nachname",
                "Kennwort"};
		Object[][] items = {};
		tableAnwender = new JTable();
		DefaultTableModel dtm = (DefaultTableModel) tableAnwender.getModel();
		
		dtm.setColumnIdentifiers(columnNames);
		
		for (Anwender anwender : anwendernliste) {
			Object[] item = {anwender.getVorname(), anwender.getNachname(), anwender.getKennwort()};
			dtm.addRow(item);
		}
		
		JScrollPane scrollPanel = new JScrollPane(tableAnwender);
		
		add(scrollPanel);
	}

}
