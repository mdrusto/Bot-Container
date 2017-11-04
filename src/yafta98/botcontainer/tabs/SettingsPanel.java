package yafta98.botcontainer.tabs;

import java.awt.Dimension;

import javax.swing.JPanel;

public class SettingsPanel extends JPanel {
	
	private static final long serialVersionUID = 7434584105807698065L;
	
	private Dimension size = new Dimension(1000, 800);
	
	public SettingsPanel() {
		setMinimumSize(size);
		setSize(size);
		setPreferredSize(size);
		setMaximumSize(size);
		
		setOpaque(false);
		setFocusable(false);
	}
	
}
