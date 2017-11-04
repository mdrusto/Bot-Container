package yafta98.botcontainer.bot;

import net.dv8tion.jda.core.JDA;

public abstract class AbstractBot implements IBot {
	
	private JDA jda;
	
	private StatusController statusController = new StatusController();
	
	@Override
	public JDA getJDA() {
		return jda;
	}
	
	public StatusController getStatusController() {
		return statusController;
	}
}
