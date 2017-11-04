package yafta98.botcontainer.tabs;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import net.dv8tion.jda.core.JDA;

public class InfoPanel extends JPanel {
	
	private static final long serialVersionUID = 3445035251121393603L;
	
	private Dimension size = new Dimension(1000, 800);
	
	public DiscordViewPanel discordViewPanel = new DiscordViewPanel();
	private LogPanel logPanel = new LogPanel();
	private SettingsPanel settingsPanel = new SettingsPanel();
	
	public InfoPanel() {

		setMinimumSize(size);
		setSize(size);
		setPreferredSize(size);
		setMaximumSize(size);
		
		setOpaque(false);
		
		add(discordViewPanel);
		add(logPanel);
		add(settingsPanel);
		
		discordViewPanel.setLocation(0, 0);
		logPanel.setLocation(0, 0);
		settingsPanel.setLocation(0, 0);
		
		
		
		discordViewPanel.setVisible(false);
		logPanel.setVisible(false);
		settingsPanel.setVisible(false);
		
		showDiscordView();
		
		setBorder(BorderFactory.createEmptyBorder());
		setLayout(null);
	}
	
	public void init(JDA jda) {
		discordViewPanel.initialize(jda);
	}
	
	public void showDiscordView() {
		logPanel.setVisible(false);
		settingsPanel.setVisible(false);
		discordViewPanel.setVisible(true);
	}
	
	public void showLog() {
		discordViewPanel.setVisible(false);
		settingsPanel.setVisible(false);
		logPanel.setVisible(true);
	}
	
	public void showSettings() {
		discordViewPanel.setVisible(false);
		logPanel.setVisible(false);
		settingsPanel.setVisible(true);
	}
}
