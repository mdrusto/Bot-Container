package yafta98.botcontainer.tabs.discordviewpanel;

import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import yafta98.botcontainer.CustomPanel;
import yafta98.botcontainer.loading.HasLoadable;
import yafta98.botcontainer.loading.Loadable;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.MessageHistory;

public class MessagesPanel extends CustomPanel implements HasLoadable {

	private static final long serialVersionUID = -3860894592704183045L;
	
	private Dimension size;
	
	private int currentHeight = 0;
	
	private Message lastMessage = null;
	private List<Message> continuingMessages = new ArrayList<Message>();
	private List<Message> temporaryPastMessages;
	
	public MessageChannel channel;
	public MessageHistory history;
	
	private Message newest = null;
	private MessageGroupPanel newestPanel = null;
	
	private boolean hasStarted = false;
	
	MessageChannelPanel container;
	
	public int numMessages = 0;
	
	private Loadable loadable;
	
	public MessagesPanel(MessageChannelPanel container, MessageChannel channel) {
		super(container.getWidth(), container.getHeight() - container.buttonSize.height);
		this.container = container;
		
		this.channel = channel;
		history = channel.getHistory();
		
		loadable = new Loadable() {
			@Override
			public void beforeLoading() {
				temporaryPastMessages = history.retrievePast(40).complete();
			}
			
			@Override
			public void load() {
				addPastMessages(40);
			}
		};
	}
	
	public void addPastMessages(int num) {
		numMessages += num;
		List<MessageGroupPanel> newComps = new ArrayList<MessageGroupPanel>();
		Component[] oldComps;
		synchronized (getTreeLock()) {
			oldComps = this.getComponents();
		}
		
		if (!hasStarted && temporaryPastMessages.size() > 0) newest = temporaryPastMessages.get(0);
		
		for (Message message: temporaryPastMessages) {
			if (!(lastMessage != null && message.getCreationTime().plusMinutes(10).compareTo(lastMessage.getCreationTime()) >= 0 && message.getAuthor().equals(lastMessage.getAuthor()))) {
				if (continuingMessages.size() > 0) {
					MessageGroupPanel messagePanel1 = new MessageGroupPanel(continuingMessages);
					if (!hasStarted) {
						newestPanel = messagePanel1;
						hasStarted = true;
					}
					add(messagePanel1);
					currentHeight += messagePanel1.getHeight();
					messagePanel1.containerHeight = currentHeight;
					newComps.add(messagePanel1);
				}
				continuingMessages.clear();
				continuingMessages.add(0, message);
			}
			else continuingMessages.add(0, message);
			lastMessage = message;
		}

		size = new Dimension(this.getWidth(), currentHeight);
		
		size(size);
		
		synchronized (getTreeLock()) {
			for (Component comp: oldComps) {
				if (comp instanceof MessageGroupPanel) comp.setLocation(0, getHeight() - ((MessageGroupPanel)comp).containerHeight);
			}
		}
		
		for (MessageGroupPanel panel: newComps) {
			panel.setLocation(0, this.getHeight() - panel.containerHeight);
		}
	}
	
	public void addNewMessage(Message message) {
		if (newest != null && newest.getAuthor().equals(message.getAuthor()) && message.getCreationTime().plusMinutes(10).compareTo(newest.getCreationTime()) >= 0) {
			//Include it in the last message group
			int oldHeight = newestPanel.getHeight();
			newestPanel.addMessage(message);
			currentHeight += newestPanel.getHeight() - oldHeight;
			
			size.height = currentHeight;
			
			size(size);
		}
		else {
			//Create a new group for this message
			List<Message> list = new ArrayList<Message>();
			list.add(message);
			MessageGroupPanel panel = new MessageGroupPanel(list);
			add(panel);
			panel.containerHeight = currentHeight;
			currentHeight += panel.getHeight();
			
			size = new Dimension(this.getWidth(), currentHeight);
			
			size(size);
			
			panel.setLocation(0, getHeight() - panel.getHeight());
			newest = message;
			newestPanel = panel;
		}
	}
	
	@Override
	public Loadable getLoadable() {
		return loadable;
	}
}
