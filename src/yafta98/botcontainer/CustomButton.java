package yafta98.botcontainer;

import java.awt.Dimension;

import javax.swing.JButton;

public class CustomButton extends JButton {
	
	private static final long serialVersionUID = -2228549882183904894L;
	
	public CustomButton(Dimension size) {
		this(size, null);
	}
	
	public CustomButton(int width, int height, String text) {
		this(new Dimension(width, height), text);
	}
	
	public CustomButton(Dimension size, String text) {
		setMinimumSize(size);
		setSize(size);
		setPreferredSize(size);
		setMaximumSize(size);
		
		setText(text);
		
		setFocusable(false);
	}
}
