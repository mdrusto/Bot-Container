package yafta98.botcontainer;

import java.awt.Dimension;

import javax.swing.JLabel;

public class CustomLabel extends JLabel {
	
	private static final long serialVersionUID = 4212405429754099618L;
	
	public CustomLabel(int width, int height) {
		this(width, height, null);
	}
	
	public CustomLabel(Dimension size) {
		this(size, null);
	}
	
	public CustomLabel(int width, int height, String text) {
		this(new Dimension(width, height), text);
	}
	
	public CustomLabel(Dimension size, String text) {
		setMinimumSize(size);
		setSize(size);
		setPreferredSize(size);
		setMaximumSize(size);
		
		setText(text);
	}
}
