package yafta98.botcontainer;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.*;

import yafta98.botcontainer.bot.IBot;
import yafta98.botcontainer.bot.AbstractBot;
import yafta98.botcontainer.bot.BotStatus;
import yafta98.botcontainer.loading.Loadable;
import yafta98.botcontainer.rightsidepanel.RightSidePanel;
import yafta98.botcontainer.tabs.InfoPanel;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.channel.text.TextChannelCreateEvent;
import net.dv8tion.jda.core.events.guild.GuildJoinEvent;
import net.dv8tion.jda.core.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.EventListener;
import net.dv8tion.jda.core.hooks.IEventManager;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 2334807590779291894L;
	
	private IBot bot;
	
	public InfoPanel infoPanel = new InfoPanel();
	public RightSidePanel rightSidePanel;
	
	private Iterator<Loadable> loadableIterator;
	
	private boolean isOpen = true;
	
	public MainWindow(IBot bot) {
		this.bot = bot;
		rightSidePanel = new RightSidePanel(bot instanceof AbstractBot ?
				((AbstractBot) bot).getStatusController() : null);
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addComponent(infoPanel)
				.addComponent(rightSidePanel));
		
		layout.setVerticalGroup(layout.createParallelGroup()
				.addComponent(infoPanel)
				.addComponent(rightSidePanel));
		pack();
		getContentPane().setBackground(Colours.BACKGROUND);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowListener() {
			@Override
			public void windowIconified(WindowEvent event) {}
			@Override
			public void windowDeactivated(WindowEvent event) {}
			@Override
			public void windowClosed(WindowEvent event) {}
			@Override
			public void windowActivated(WindowEvent event) {}
			@Override
			public void windowDeiconified(WindowEvent event) {}
			@Override
			public void windowOpened(WindowEvent event) {}
			
			@Override
			public void windowClosing(WindowEvent event) {
				isOpen = false;
			}
		});
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		}
		catch (ReflectiveOperationException | UnsupportedLookAndFeelException e) {
			throw new RuntimeException(e);
		}
		setVisible(true);
		setIconImage(new ImageIcon("resources/img/erasmus_icon.png").getImage());
		setTitle("Erasmus v0.0.1");
	}
	
	public void init(JDA jda) {
		List<Loadable> loadables = new ArrayList<Loadable>();
		
		
		
		loadableIterator = loadables.iterator();
		
		loadNext();
		
		infoPanel.init(jda);
	}
	
	private void loadNext() {
		if (!loadableIterator.hasNext()) return;
		Loadable loadable = loadableIterator.next();
		
		loadable.execute();
		
		loadNext();
	}
	
	public void onError(Throwable error) {
		error.printStackTrace();
		rightSidePanel.statusPanel.setError(error);
		setStatus(BotStatus.ERROR);
	}
	
	public void setStatus(BotStatus status) {
		rightSidePanel.statusPanel.setStatus(status);
		rightSidePanel.runPanel.setStatus(status);
	}
	
	public boolean isOpen() {
		return isOpen;
	}
	
	public class UIEventListener extends ListenerAdapter {
		@Override
		public void onMessageReceived(MessageReceivedEvent event) {
			infoPanel.discordViewPanel.getGuildView(event.getGuild()).getChannelView(event.getTextChannel()).messagesPanel.addNewMessage(event.getMessage());
		}
		@Override
		public void onGuildJoin(GuildJoinEvent event) {
			infoPanel.discordViewPanel.addGuild(event.getGuild());
		}
		@Override
		public void onGuildLeave(GuildLeaveEvent event) {
			infoPanel.discordViewPanel.removeGuild(event.getGuild());
		}
		@Override
		public void onTextChannelCreate(TextChannelCreateEvent event) {
			infoPanel.discordViewPanel.getGuildView(event.getGuild()).textChannelCreated(event.getChannel());
		}
	}
	
	public class UIUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
		@Override
		public void uncaughtException(Thread t, Throwable e) {
			onError(e);
		}
	}
	
	public class UIEventManager implements IEventManager {

		private final List<EventListener> listeners = new CopyOnWriteArrayList<>();

	    @Override
	    public void register(Object listener) {
	        if (!(listener instanceof EventListener)) {
	            throw new IllegalArgumentException("Listener must implement EventListener");
	        }
	        listeners.add((EventListener) listener);
	    }

	    @Override
	    public void unregister(Object listener) {
	        listeners.remove(listener);
	    }

	    @Override
	    public List<Object> getRegisteredListeners() {
	        return Collections.unmodifiableList(new LinkedList<>(listeners));
	    }

	    @Override
	    public void handle(Event event) {
	        for (EventListener listener : listeners) {
	        	try {
	        		listener.onEvent(event);
	        	}
	        	catch(Throwable thrown) {
	        		onError(thrown);
	        	}
	        }
	    }
	}
}
