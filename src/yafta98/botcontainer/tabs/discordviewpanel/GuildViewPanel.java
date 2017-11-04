package yafta98.botcontainer.tabs.discordviewpanel;

import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;
import yafta98.botcontainer.CustomPanel;
import yafta98.botcontainer.loading.CompositeLoadable;
import yafta98.botcontainer.loading.HasLoadable;
import yafta98.botcontainer.loading.Loadable;

public class GuildViewPanel extends CustomPanel implements HasLoadable {
	
	private static final long serialVersionUID = 8592305740116559621L;
	
	private Guild guild;
	
	public TextChannelSelectorPanel textChannelSelector = new TextChannelSelectorPanel();
	
	private static final Dimension SIZE = new Dimension(900, 800);
	
	private transient Map<TextChannel, TextChannelPanel> textChannels = new HashMap<TextChannel, TextChannelPanel>();
	private TextChannelPanel lastPanel;
	
	private CompositeLoadable loadable = new CompositeLoadable();
	
	public GuildViewPanel(Guild guild) {
		super(SIZE);
		this.guild = guild;
		
		setLayout(null);
		add(textChannelSelector);
		textChannelSelector.setLocation(0, 0);
		textChannelSelector.loadChannelButtons(guild.getTextChannels());
		guild.getTextChannels().forEach((channel) -> {
			TextChannelPanel panel = new TextChannelPanel(channel);
			textChannels.put(channel, panel);
			add(panel);
			panel.setLocation(200, 0);
			panel.setVisible(false);
			
			CompositeLoadable tempLoadable = new CompositeLoadable() {
				@Override
				public void afterLoading() {
					panel.addScrollListener();
					textChannelSelector.getTextChannelButton(channel).setEnabled(true);
					if (guild.getPublicChannel() == null) textChannelSelected(guild.getTextChannels().get(0));
					else if (guild.getPublicChannel().equals(channel)) textChannelSelected(channel);
				}
			};
			tempLoadable.addLoadable(panel.getLoadable());
			loadable.addLoadable(tempLoadable);
		});
	}
	
	public Guild getGuild() {
		return guild;
	}
	
	public TextChannelPanel getChannelView(TextChannel channel) {
		return textChannels.get(channel);
	}
	
	public void textChannelSelected(TextChannel channel) {
		if (lastPanel != null) lastPanel.setVisible(false);
		textChannels.get(channel).setVisible(true);
		lastPanel = textChannels.get(channel);
	}
	
	public void textChannelCreated(TextChannel channel) {
		textChannels.put(channel, new TextChannelPanel(channel));
		textChannelSelector.addChannel(channel);
	}
	
	public void textChannelDeleted(TextChannel channel) {
		textChannels.remove(channel);
	}
	
	@Override
	public Loadable getLoadable() {
		return loadable;
	}
}
