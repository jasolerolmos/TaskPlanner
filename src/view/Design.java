package view;

import java.awt.Color;

public class Design {
//	FARBE1(new Color(244, 226, 133)),
//	FARBE2(new Color(206, 95, 45)),
//	FARBE3(new Color(144, 173, 76)),
//	FARBE4(new Color(244, 162, 89)),
//	FARBE5(new Color(255, 255, 255));
	
	public static Color background1 = Color.WHITE;
	public static Color background2 = new Color(244, 226, 133);
	public static Color background3 = new Color(244, 226, 133);
	public static Color reiheBG = new Color(244, 226, 133);
	public static Color backgroundBesc = new Color(244, 226, 133);
	public static Color buttonBG = new Color(48, 162, 255);
	public static Color buttonBG2 = new Color(140, 212, 255);
	public static Color title1 = new Color(0, 0, 0);
	public static Color errorMessage = new Color(255, 50, 50);
//	public static Color background1 = new Color(244, 226, 133);
	
	
//	FARBE1(new Color(188, 75, 81));
	
	private Color color;

	public Design(Color c) {
		this.color = c;
	}
	
	public Color getColor() {
		return color;
	}
	
	public static void setDesign1() {
		background1 = Color.WHITE;
		background2 = new Color(244, 226, 133);
		reiheBG = new Color(244, 226, 133);
		backgroundBesc = new Color(244, 226, 133);
		buttonBG = new Color(48, 162, 255);
		buttonBG2 = new Color(140, 212, 255);
		title1 = new Color(0, 0, 0);
	}

	public static void setDesign2() {
		background1 = Color.WHITE;
		background2 = new Color(32, 46, 90);
		background3 = new Color(102, 201, 255);
		reiheBG = new Color(201,208,220);
		backgroundBesc = new Color(231, 237, 242);
		buttonBG = new Color(201,208,220);
		buttonBG2 = new Color(140, 212, 255);
		title1 = new Color(255, 255, 255);
	}
}
