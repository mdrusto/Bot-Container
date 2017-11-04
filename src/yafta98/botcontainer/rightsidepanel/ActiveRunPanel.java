package yafta98.botcontainer.rightsidepanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import yafta98.botcontainer.bot.StatusController;

public class ActiveRunPanel extends RunPanel {
	
	private static final long serialVersionUID = 4281428109507812283L;
	
	public ActiveRunPanel(StatusController statusController) {
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				statusController.start();
				setOnline();
			}
		});
		
		stopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				statusController.stop();
				setOffline();
			}
		});
		
		restartButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				statusController.stop();
				setLoading();
				statusController.start();
				setOnline();
			}
		});
	}
	
	@Override
	public void switchStates(boolean on) {
		startButton.setEnabled(!on);
		stopButton.setEnabled(on);
		restartButton.setEnabled(on);
	}
	
	@Override
	public void setOnline() {
		startButton.setEnabled(false);
		stopButton.setEnabled(true);
		restartButton.setEnabled(true);
	}
	
	@Override
	public void setOffline() {
		startButton.setEnabled(true);
		stopButton.setEnabled(false);
		restartButton.setEnabled(false);
	}
	
	@Override
	public void setLoading() {
		startButton.setEnabled(false);
		stopButton.setEnabled(false);
		restartButton.setEnabled(false);
	}
}
