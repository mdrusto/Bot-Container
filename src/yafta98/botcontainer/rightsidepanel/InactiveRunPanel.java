package yafta98.botcontainer.rightsidepanel;

public class InactiveRunPanel extends RunPanel {
	
	private static final long serialVersionUID = -3006194478628746937L;
	
	public InactiveRunPanel() {
		startButton.setEnabled(false);
		stopButton.setEnabled(false);
		restartButton.setEnabled(false);
	}

	@Override
	public void switchStates(boolean on) {
		
	}

	@Override
	public void setOnline() {
		
	}

	@Override
	public void setOffline() {
		
	}

	@Override
	public void setLoading() {
		
	}
}
