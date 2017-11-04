package yafta98.botcontainer.rightsidepanel;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import yafta98.botcontainer.Colours;
import yafta98.botcontainer.CustomLabel;
import yafta98.botcontainer.bot.BotStatus;

public class StatusPanel extends JPanel {
	
	private static final long serialVersionUID = -9097462012706026062L;
	
	private JLabel statusIcon = new JLabel();
	private JLabel statusText = new CustomLabel(200, 40, "Offline");
	
	private ImageIcon onlineIcon = new ImageIcon("resources/img/online.png");
	private ImageIcon loadingIcon = new ImageIcon("resources/img/loading.gif");
	
	private Dimension size = new Dimension(300, 100);
	
	public StatusPanel() {
		super();
		setLayout(null);
		
		statusIcon.setSize(new Dimension(80, 80));
		statusIcon.setLocation(10, 10);
		statusText.setLocation(100, 30);
		add(statusIcon);
		add(statusText);
		
		setMinimumSize(size);
		setSize(size);
		setPreferredSize(size);
		setMaximumSize(size);
		
		setBackground(Colours.BACKGROUND);
	}
	
	public void setStatus(BotStatus status) {
		switch(status) {
		case OFFLINE:
			statusIcon.setIcon(null);
			statusText.setText("Offline");
			statusText.setForeground(Color.WHITE);
			break;
		case LOADING:
			statusIcon.setIcon(loadingIcon);
			statusText.setText("Loading...");
			statusText.setForeground(Color.GRAY);
			break;
		case ONLINE:
			statusIcon.setIcon(onlineIcon);
			statusText.setText("Running properly");
			statusText.setForeground(new Color(30, 200, 30));
			break;
		case ERROR:
			break;
		}
	}
	
	public void setError(Throwable thrown) {
		statusText.setText(thrown.getClass().getSimpleName());
		statusText.setForeground(Color.RED);
	}
}
