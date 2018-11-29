/*
 * Das Fenster für eingeloggen.
 */
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class LoginFenster extends JDialog{
	
	private int width = 300, height = 190;
	private JTextField anwenderJTF = new JTextField();
	private JPasswordField kennwortJTF = new JPasswordField();
	private JButton enter = new JButton("Login");
	private JButton exit = new JButton("Schließen");
	private JLabel messageLBL ;
	
	public LoginFenster() {
		setSize(width,  height);
		setLocationRelativeTo(null);
		setResizable(false);
		setUndecorated(true);
		setModal(true);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		
		JPanel main = new JPanel();
		main.setLayout(new BorderLayout());
		
		JPanel north = new JPanel();
		north.setLayout(new FlowLayout());
		JLabel tittelLBL = new JLabel("Login");
		tittelLBL.setFont(new Font("Verdana", Font.BOLD, 20));
		north.add(tittelLBL);
		
		JPanel center = new JPanel();
		center.setLayout(new BorderLayout());

		JPanel south = new JPanel();
		south.setLayout(new FlowLayout());
		
		JLabel userLBL = new JLabel("Anwender");
		JLabel kennwortLBL = new JLabel("Kennwort");
		Font font = new Font("Verdana", Font.BOLD, 14);
		Dimension labels = new Dimension(100, 30);
		userLBL.setFont(font);
		userLBL.setPreferredSize(labels);
		kennwortLBL.setFont(font);
		kennwortLBL.setPreferredSize(labels);
		messageLBL = new JLabel("", SwingConstants.CENTER);
		messageLBL.setFont(font);
		messageLBL.setForeground(Color.red);
		messageLBL.setPreferredSize(new Dimension(300, 30));
		
		messageLBL.setHorizontalAlignment(JLabel.CENTER);
		messageLBL.setVerticalAlignment(JLabel.CENTER);
		
		anwenderJTF.setPreferredSize(new Dimension(150, 30));
		anwenderJTF.setAlignmentY(JComponent.CENTER_ALIGNMENT);
		kennwortJTF.setPreferredSize(new Dimension(150, 30));
		kennwortJTF.setAlignmentY(JComponent.CENTER_ALIGNMENT);
		
		JPanel userPanel = new JPanel();
		userPanel.setLayout(new FlowLayout());
		userPanel.add(userLBL);
		userPanel.add(anwenderJTF);
		anwenderJTF.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				anwenderJTF.selectAll();
			}
		});
		
		JPanel kennwortPanel = new JPanel();
		kennwortPanel.setLayout(new FlowLayout());
		kennwortPanel.add(kennwortLBL);
		kennwortPanel.add(kennwortJTF);

		JPanel messagePanel = new JPanel();
		messagePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		messagePanel.add(messageLBL);
		
		south.add(exit);
		south.add(enter);
		
		center.add(userPanel, BorderLayout.NORTH);
		center.add(kennwortPanel, BorderLayout.CENTER);
		center.add(messagePanel, BorderLayout.SOUTH);
		
		main.add(north, BorderLayout.NORTH);
		main.add(center, BorderLayout.CENTER);
		main.add(south, BorderLayout.SOUTH);
		
		add(main);
		
	}

	/*
	 * Es zentiert die Fenster in Hauptfenster
	 */
	public void setFenster() {
		setSize(width,  height);
		setLocationRelativeTo(null);
		setTitle("Login");
		anwenderJTF.setText("jose.soler");
		kennwortJTF.setText("123123123");
		addWindowListener(new WindowAdapter(){ 
			  public void windowOpened( WindowEvent e){ 
				  kennwortJTF.requestFocus();
			  } 
			}); 
	}
	
	/*
	 * In Logout, die Kennwort ist gelöscht
	 */
	@Override
	public void setVisible(boolean b) {
		kennwortJTF.setText("");
		super.setVisible(b);
	}

	public JPasswordField getKennwortJTF() {
		return kennwortJTF;
	}

	public JTextField getAnwenderJTF() {
		return anwenderJTF;
	}

	public JButton getEnter() {
		return enter;
	}

	public JButton getExit() {
		return exit;
	}

	public JLabel getMessageLBL() {
		return messageLBL;
	}

	public void setMessageLBL(JLabel messageLBL) {
		this.messageLBL = messageLBL;
	}

}
