package yafta98.botcontainer.tabs.discordviewpanel;

import yafta98.botcontainer.tabs.discordviewpanel.MessageChannelPanel;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.entities.User;

public class PrivateChannelPanel extends MessageChannelPanel {
	
	private static final long serialVersionUID = -4364869147007373856L;
	
	private User user;
	
	public PrivateChannelPanel(PrivateChannel channel) {
		super(channel);
		user = channel.getUser();
	}
	
	public User getUser() {
		return user;
	}
}
