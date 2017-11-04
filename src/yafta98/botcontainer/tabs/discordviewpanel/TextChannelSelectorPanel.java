package yafta98.botcontainer.tabs.discordviewpanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;

import yafta98.botcontainer.Colours;
import yafta98.botcontainer.CustomButton;
import net.dv8tion.jda.core.entities.*;

public class TextChannelSelectorPanel extends JScrollPane {

	private static final long serialVersionUID = 3461070594233407645L;
	
	public Map<TextChannel, TextChannelButton> channels = new HashMap<TextChannel, TextChannelButton>();
	
	private JPanel textChannelPanel = new JPanel();
	
	private TextChannelButton lastButton;
	
	Dimension size = new Dimension(200, 800);
	
	public TextChannelSelectorPanel() {
		setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.BLACK));
		
		textChannelPanel.setVisible(true);
		
		setMinimumSize(size);
		setSize(size);
		setPreferredSize(size);
		setMaximumSize(size);
		
		textChannelPanel.setLayout(new BoxLayout(textChannelPanel, BoxLayout.Y_AXIS));
		
		setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
		setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_AS_NEEDED);
		
		
		setViewportView(textChannelPanel);
		
		textChannelPanel.setBackground(Colours.BACKGROUND);
		
		
	}
	
	public void loadChannelButtons(List<TextChannel> channels) {
		channels.forEach(this::addChannel);
	}
	
	public TextChannelButton getTextChannelButton(TextChannel channel) {
		return channels.get(channel);
	}
	
	public void addChannel(TextChannel channel) {
		TextChannelButton button = new TextChannelButton(channel);
		textChannelPanel.add(button);
		channels.put(channel, button);
		revalidate();
		repaint();
	}
	
	public void textChannelDeleted(TextChannel channel) {
		textChannelPanel.remove(channels.get(channel));
		channels.remove(channel);
		revalidate();
		repaint();
	}
	
	public void selectChannel(TextChannel channel) {
		TextChannelButton button = channels.get(channel);
		button.select();
		
		if (lastButton != null) {
			lastButton.unselect();
		}
		((GuildViewPanel)getParent()).textChannelSelected(channel);
		
		lastButton = button;
	}
	
	public class TextChannelButton extends CustomButton {
		
		private static final long serialVersionUID = 64027815538733805L;
		
		private TextChannelButton(TextChannel channel) {
			super(new Dimension(200, 30), "  # " + channel.getName());
			
			setEnabled(false);
			setFocusable(true);
			setFocusPainted(false);
			
			setHorizontalAlignment(SwingConstants.LEFT);
			
			this.setFont(getFont().deriveFont(getFont().getSize() * 1.1F));
			
			addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					selectChannel(channel);
				}
			});
			setBorder(BorderFactory.createEmptyBorder());
			setBackground(Colours.TEXTCHANNELSELECTOR_BACKGROUND);
			
			unselect();
		}
		
		private void select() {
			setEnabled(false);
			setForeground(Colours.TEXT);
			setOpaque(true);
		}
		
		private void unselect() {
			setForeground(Colours.TEXTCHANNEL_UNSELECTED);
			setEnabled(true);
			setOpaque(false);
		}
	}
}
