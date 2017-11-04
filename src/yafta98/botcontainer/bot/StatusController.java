package yafta98.botcontainer.bot;

import java.util.ArrayList;
import java.util.List;

public class StatusController {
	
	private boolean isOn = false;
	
	private List<StartupListener> startupListeners = new ArrayList<>();
	private List<ShutdownListener> shutdownListeners = new ArrayList<>();
	
	public void addStartupListener(StartupListener listener) {
		startupListeners.add(listener);
	}
	
	public void removeStartupListener(StartupListener listener) {
		startupListeners.remove(listener);
	}
	
	public void addShutdownListener(ShutdownListener listener) {
		shutdownListeners.add(listener);
	}
	
	public void removeShutdownListener(ShutdownListener listener) {
		shutdownListeners.remove(listener);
	}
	
	public void start() {
		for (StartupListener listener: startupListeners) {
			listener.onStartup();
		}
		
		isOn = true;
	}
	
	public void stop() {
		for (ShutdownListener listener: shutdownListeners) {
			listener.onShutdown();
		}
		
		isOn = false;
	}
	
	public void toggle() {
		if (isOn)
			stop();
		else
			start();
	}
}
