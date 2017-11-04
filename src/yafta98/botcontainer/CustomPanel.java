package yafta98.botcontainer;

import java.awt.Dimension;

import javax.swing.JPanel;

public class CustomPanel extends JPanel {
	
	private static final long serialVersionUID = -6231998178197306658L;
	
	public CustomPanel() {
		setOpaque(false);
	}
	
	public CustomPanel(int width, int height) {
		this(new Dimension(width, height));
	}
	
	public CustomPanel(Dimension size) {
		size(size);
		
		setOpaque(false);
		setLayout(null);
	}
	
	public void size(Dimension size) {
		setMinimumSize(size);
		setSize(size);
		setPreferredSize(size);
		setMaximumSize(size);
	}
}
