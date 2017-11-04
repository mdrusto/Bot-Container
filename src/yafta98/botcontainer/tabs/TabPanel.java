package yafta98.botcontainer.tabs;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import yafta98.botcontainer.CustomButton;
import yafta98.botcontainer.MainWindow;

public abstract class TabPanel extends JPanel {
	
	private static final long serialVersionUID = -7055317331610378248L;
	
	private Dimension size = new Dimension(300, 300);
	
	private TabButton discordViewButton = new TabButton("Discord view");
	private TabButton logButton = new TabButton("Log");
	private TabButton settingsButton = new TabButton("Settings");
	
	
	public TabPanel(MainWindow window) {
		setMinimumSize(size);
		setSize(size);
		setPreferredSize(size);
		setMaximumSize(size);
		
		setOpaque(false);
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		discordViewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				window.infoPanel.showDiscordView();
			}
		});
		
		logButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				window.infoPanel.showLog();
			}
		});
		
		settingsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				window.infoPanel.showSettings();
			}
		});
		
		add(discordViewButton);
		add(logButton);
		add(settingsButton);
	}
	
	private class TabButton extends CustomButton {
		
		private static final long serialVersionUID = -8303812139799062096L;
		
		private TabButton(String text) {
			super(new Dimension(300, 80), text);
		}
	}
	
}
