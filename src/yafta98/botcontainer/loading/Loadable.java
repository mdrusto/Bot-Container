package yafta98.botcontainer.loading;

import javax.swing.SwingWorker;

public abstract class Loadable {
	
	/**
	 * Called on the EDT
	 */
	public void beforeLoading() {};
	
	/**
	 * The main process of the loadable, called on the EDT by default
	 */
	public abstract void load();
	
	/**
	 * Called on the EDT
	 */
	public void afterLoading() {};
	
	public void execute() {
		beforeLoading();
		
		new SwingWorker<Void, Void>() {
			@Override
			public Void doInBackground() {
				load();
				return null;
			}
			
			@Override
			public void done() {
				afterLoading();
			}
		}.execute();
	}
}
