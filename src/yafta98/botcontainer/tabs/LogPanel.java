package yafta98.botcontainer.tabs;

import java.awt.Dimension;

import javax.swing.JPanel;

public class LogPanel extends JPanel {
	
	
	private static final long serialVersionUID = -3123520578701213132L;
	
	private Dimension size = new Dimension(1000, 800);
	
	public LogPanel() {
		setOpaque(false);
		
		setMinimumSize(size);
		setSize(size);
		setPreferredSize(size);
		setMaximumSize(size);
		
		
	}
	
	
}
