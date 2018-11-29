package view;

import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;

public class MeineTextField extends JTextField{
	private Dimension dimension = new Dimension(20, 20);
	private int maxValue = 99;
	private MeineTextField ich;

	public MeineTextField(int max) {
		super();
		ich = this;
		maxValue= max;
		setDefault();
	}
	
	public void setDefault() {
		setPreferredSize(dimension);
		setHorizontalAlignment(JTextField.RIGHT);
		
	}
	
	 @Override
    public void processKeyEvent(KeyEvent ev) {
		 if(ev.getKeyCode()==KeyEvent.VK_BACK_SPACE
    		|| ev.getKeyCode()==KeyEvent.VK_DELETE
    		|| ev.getKeyCode()==KeyEvent.VK_LEFT
    		|| ev.getKeyCode()==KeyEvent.VK_RIGHT )
			 super.processKeyEvent(ev);
		 
	    if (Character.isDigit(ev.getKeyChar()) && getText().length()<2){
	        super.processKeyEvent(ev);
        }
        if(ich.getText().length()>0 && ich.getSelectedText()!=null)
	        if(ich.getSelectedText().length()==2) {
	        	super.processKeyEvent(ev);
	        }
        ev.consume();
        return;
    }

	public int getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}

	 
	
}
