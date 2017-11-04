package yafta98.botcontainer;


import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import yafta98.botcontainer.bot.AbstractBot;
import yafta98.botcontainer.bot.IBot;

public class BotContainer {
	
	private static MainWindow window;
	private static IBot bot;
	
	public MainWindow getWindow() {
		return window;
	}
	
	public static void startApplication(IBot bot) {
		BotContainer.bot = bot;
		try {
			SwingUtilities.invokeAndWait(() -> window = new MainWindow(bot));
		}
		catch (InvocationTargetException e) {
			e.getTargetException().printStackTrace();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public IBot getBot() {
		return bot;
	}
}
