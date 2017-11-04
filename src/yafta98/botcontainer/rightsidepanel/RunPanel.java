package yafta98.botcontainer.rightsidepanel;

import javax.swing.*;

import yafta98.botcontainer.CustomButton;
import yafta98.botcontainer.CustomPanel;
import yafta98.botcontainer.bot.BotStatus;

public abstract class RunPanel extends CustomPanel {
	
	private static final long serialVersionUID = -341491779860650821L;
	
	protected JButton startButton = new CustomButton(80, 50, "Start");
	protected JButton stopButton = new CustomButton(80, 50, "Stop");
	protected JButton restartButton = new CustomButton(80, 50, "Restart");
	
	public RunPanel() {
		GroupLayout layout = new GroupLayout(this);
		setLayout(layout);
		
		layout.setHorizontalGroup(layout.createParallelGroup()
				.addGap(25)
				.addGroup(layout.createSequentialGroup()
						.addGap(30)
						.addComponent(startButton)
						.addComponent(stopButton)
						.addComponent(restartButton)
						.addGap(30))
				.addGap(25));
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGap(25)
				.addGroup(layout.createParallelGroup()
						.addComponent(startButton)
						.addComponent(stopButton)
						.addComponent(restartButton)
						.addGap(20))
				.addGap(25));
	}

	
	public abstract void switchStates(boolean on);
	
	public abstract void setOnline();
	
	public abstract void setOffline();
	
	public abstract void setLoading();
	
	public void setStatus(BotStatus status) {
		switch(status) {
		case ONLINE:
			setOnline();
			break;
		case LOADING:
			setLoading();
			break;
		case OFFLINE:
			setOffline();
			break;
		case ERROR:
			break;
		}
	}
}
