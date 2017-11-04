package yafta98.botcontainer.tabs.discordviewpanel;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.Message.Attachment;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

import yafta98.botcontainer.CustomPanel;

public class MessageGroupPanel extends CustomPanel {
	
	private static final long serialVersionUID = 6935897330723885326L;
	
	private Font font = new JLabel().getFont();
	
	public FontMetrics metrics = new JLabel().getFontMetrics(font);
	
	
	public int numLines = 0;
	public int height = 40;
	public int containerHeight = 0;
	
	
	private Guild guild;
	private User author;
	private JDA jda;
	private Member member;
	private MessageChannel channel;
	private OffsetDateTime creationTime;
	
	private Message lastMessage;
	
	private AvatarLabel avatar;
	private TimeLabel dateTime;
	private NameLabel name;
	private TextPanel text;
	
	public MessageGroupPanel(List<Message> messages) {
		if (messages.size() < 1) throw new IllegalArgumentException("Empty messages list in message group panel");
		this.author = messages.get(0).getAuthor();
		this.guild = messages.get(0).getGuild();
		this.jda = messages.get(0).getJDA();
		this.lastMessage = messages.get(0);
		this.member = guild == null ? null : guild.getMember(author);
		this.channel = messages.get(0).getChannel();
		this.creationTime = messages.get(0).getCreationTime();
		
		this.avatar = new AvatarLabel();
		this.dateTime = new TimeLabel();
		this.name = new NameLabel();
		this.text = new TextPanel();
		
		setLayout(null);
		setOpaque(false);
		setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(60, 62, 67)));
		
		add(avatar);
		add(name);
		add(dateTime);
		add(text);
		
		avatar.setLocation(10, 10);
		name.setLocation(60, 10);
		dateTime.setLocation(name.getWidth() + 50, 10);
		text.setLocation(60, 30);
		
		messages.forEach(this::addMessage);
		
	}
	
	public Guild getGuild() {
		return guild;
	}
	
	public User getAuthor() {
		return author;
	}
	
	public JDA getJDA() {
		return jda;
	}
	
	public MessageChannel getChannel() {
		return channel;
	}
	
	public Member getMember() {
		return member;
	}
	
	public Message getLastMessage() {
		return lastMessage;
	}
	
	public void addMessage(Message message) {
		/*String remainder = message.getRawContent();
		
		int cutoff = 0;
		
		linesLoop: while(remainder.length() > 0) {
			
			int initialStart = 0;
			
			remainder = remainder.substring(cutoff);
			
			for (int x = 0; x < remainder.length(); x++) {
				if (remainder.charAt(x) == ' ') initialStart++;
				else break;
			}
			
			remainder = remainder.substring(initialStart);
			
			int lineLength = 0;
			int wordLength = 0;
			
			cutoff = 0;
			
			charsLoop: for (int pos = 0; pos < remainder.length(); pos++) {
				char x = remainder.charAt(pos);
				if (x == ' ') {
					cutoff = pos;
					lineLength += wordLength + metrics.charWidth(' ');
					wordLength = 0;
				}
				else wordLength += metrics.charWidth(x);
				
				if (lineLength + wordLength >= 600) {
					text.addLine(remainder.substring(0, cutoff != 0 ? cutoff : pos));
					if (cutoff == 0) cutoff = pos;
					break charsLoop;
				}
				if (pos == remainder.length() - 1) {
					
					text.addLine(remainder);
					break linesLoop;
				}
				
			}
			
		}*/
		
		String remainder = message.getRawContent();
		List<String> list = new ArrayList<String>();
		List<User> mentionedUsers = message.getMentionedUsers();
		
		List<List<Integer>> indexUsers = new ArrayList<List<Integer>>();
		for (User user: mentionedUsers) {
			List<Integer> indexes = new ArrayList<Integer>();
			String mention = "<@" + (guild != null && guild.getMember(user).getNickname() != null ? "!" : "") + user.getAsMention() + ">";
			String remaining = message.getRawContent();
			int lastIndex = remaining.indexOf(mention);
			while(lastIndex != -1) {
				indexes.add(lastIndex);
				lastIndex = remaining.indexOf(mention, lastIndex);
			}
			indexUsers.add(indexes);
		}
		int cutoff = 0;
				
		for (int counter = 0; counter < remainder.length(); counter++) {
			if (remainder.charAt(counter) == ' ') {
				list.add(remainder.substring(cutoff, counter));
				cutoff = counter;
			}
			else if (metrics.stringWidth(remainder.substring(cutoff, counter)) > 600) {
				list.add(remainder.substring(cutoff, counter));
				cutoff = counter;
			}
			else if (counter == remainder.length() - 1) {
				list.add(remainder.substring(cutoff, counter + 1));
			}
		}
		
		List<JComponent> components = new ArrayList<JComponent>();
		

		for (String s: list) {
			if (s.startsWith("<") && s.endsWith(">") && s.length() > 6) {
				if (s.charAt(1) == '@' && s.charAt(2) == '&') {
					Role role = guild.getRoleById(s.substring(3, s.length() - 1));
					if (role != null) {
						components.add(new RoleButton(role));
						continue;
					}
				}
				else if (s.charAt(1) == '@') {
					User user;
					if (s.charAt(2) == '!') user = jda.getUserById(s.substring(3, s.length() - 1));
					else user = jda.getUserById(s.substring(2, s.length() - 1));
					if (user != null) {
						components.add(new UserButton(user));
						continue;
					}
				}
				else if (s.charAt(1) == '#') {
					TextChannel channel = guild.getTextChannelById(s.substring(2, s.length() - 1));
					if (channel != null) {
						components.add(new TextChannelButton(channel));
						continue;
					}
				}
			}
			components.add(new TextLabel(s));
		}
		
		int cutoff2 = 0;
		
		linesLoop: while(true) {
			
			LinePanel panel = new LinePanel();
			int lineLength = 0;
			for (int counter = cutoff2; counter < components.size(); counter++) {
				JComponent comp = components.get(counter);
				if (lineLength + comp.getWidth() >= 500) {
					cutoff2 = counter + 1;
					text.addLine(panel);
					continue linesLoop;
				}
				panel.add(comp);
				if (counter == components.size() - 1) text.addLine(panel);
			}
			break linesLoop;
		}
		
		message.getAttachments().forEach(att -> {
			if (att.isImage()) {
				ImageLabel la = new ImageLabel(att);
				text.add(la);
				int h = text.getHeight();
				h += la.getHeight();
				text.setSize(text.getWidth(), text.getHeight() + h);
			}
		});
		
		setSize(new Dimension(700, height));
		revalidate();
		repaint();
	}
	
	private class TextPanel extends JPanel {
		
		private static final long serialVersionUID = -1989429485921596825L;
		
		private boolean isEmpty = true;
		
		private TextPanel() {
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			setOpaque(false);
			this.setAlignmentX(LEFT_ALIGNMENT);
		}
		
		private void addLine(LinePanel line) {
			if (!isEmpty) add(Box.createVerticalStrut(4));
			add(line);
			if(isEmpty) isEmpty = false;
			setSize(600, getHeight() + metrics.getHeight() + 4);
			revalidate();
			repaint();
			line.setAlignmentX(LEFT_ALIGNMENT);
			height += metrics.getHeight() + 5;
			MessageGroupPanel.this.setSize(MessageGroupPanel.this.getWidth(), height);
		}
	}
	
	private class UserButton extends JButton {
		
		private static final long serialVersionUID = -2989417875490973889L;
		
		private UserButton(User user) {
			Member member = guild.getMember(user);
			setText("@" + (guild == null || member.getNickname() == null ? user.getName() : member.getNickname()));
		}
	}
	
	private class TextChannelButton extends JButton {
		
		private static final long serialVersionUID = -8237815230082786673L;
		
		private TextChannelButton(TextChannel channel) {
			setText(channel.getName());
		}
	}
	
	private class RoleButton extends JButton {
		
		private static final long serialVersionUID = 5047962059027473278L;
		
		private RoleButton(Role role) {
			setText(role.getName());
		}
	}
	
	private class TextLabel extends JLabel {
		
		private static final long serialVersionUID = -6069090564493229802L;
		
		private Dimension size;
		
		private TextLabel(String text) {
			setFont(font);
			setBorder(BorderFactory.createLineBorder(Color.BLACK));
			setVerticalTextPosition(SwingConstants.TOP);
			size = new Dimension(metrics.stringWidth(text), metrics.getHeight());
			setOpaque(false);
			setMinimumSize(size);
			setSize(size);
			setPreferredSize(size);
			setMaximumSize(size);
			MessageGroupPanel.this.add(this);
			setForeground(new Color(180, 180, 180));
			setBorder(BorderFactory.createEmptyBorder());
			setCursor(new Cursor(Cursor.TEXT_CURSOR));
			setText(text);
			setHorizontalAlignment(SwingConstants.LEFT);
			
		}
	}
	
	private class LinePanel extends JPanel {
		
		private static final long serialVersionUID = 6770124454025391729L;
		
		private LinePanel() {
			setFont(font);
			setBorder(BorderFactory.createEmptyBorder());
			setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			setOpaque(false);
		}
	}
	
	private class AvatarLabel extends JLabel {
		
		private static final long serialVersionUID = 5425392462254431707L;
		
		private AvatarLabel() {
			try {
				URLConnection conn;
				URL url;
				url = new URL(author.getAvatarUrl() == null ? author.getDefaultAvatarUrl() : author.getAvatarUrl());
				conn = url.openConnection();
				conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36");
				
				setIcon(new ImageIcon(ImageIO.read(conn.getInputStream()).getScaledInstance(40, 40, Image.SCALE_DEFAULT)));
			}
			catch (IOException e) {
				throw new RuntimeException(e);
			}
			
			MessageGroupPanel.this.add(AvatarLabel.this);
			setSize(40, 40);
			setLocation(15, 15);
		}
		
	}
	
	private class NameLabel extends JLabel {
		
		private static final long serialVersionUID = -8380560566620777535L;
		
		private NameLabel() {
			String text = member == null ? author.getName() : member.getEffectiveName();
			setText(text);
			setSize(metrics.stringWidth(text) + 20, 20);
			setLocation(70, 10);
			setOpaque(false);
			setCursor(new Cursor(Cursor.HAND_CURSOR));
			setBackground(new Color(40, 40, 40));
			setBorder(BorderFactory.createEmptyBorder());
			setForeground(member == null || member.getColor() == null ? Color.WHITE : guild.getMember(author).getColor());
			setHorizontalTextPosition(0);
			setHorizontalAlignment(SwingConstants.LEFT);
			setFocusable(false);
			addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {}

				@Override
				public void mousePressed(MouseEvent e) {}

				@Override
				public void mouseReleased(MouseEvent e) {}

				@Override
				public void mouseEntered(MouseEvent e) {
					HashMap<TextAttribute, Object> map = new HashMap<TextAttribute, Object>();
					map.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
					setFont(getFont().deriveFont(map));
				}

				@Override
				public void mouseExited(MouseEvent e) {
					HashMap<TextAttribute, Object> map = new HashMap<TextAttribute, Object>();
					map.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE);
					setFont(getFont().deriveFont(map));
				}
			});
		}
	}
	
	private class TimeLabel extends JLabel {
		
		private static final long serialVersionUID = 8627264809703508849L;
		
		private TimeLabel() {
			setOpaque(false);
			
			OffsetDateTime currentTime = OffsetDateTime.now(ZoneOffset.UTC);
			String date = "";
			String time = "";
			ZonedDateTime zoned = creationTime.atZoneSameInstant(ZoneId.systemDefault());
			if (currentTime.minusDays(7).compareTo(creationTime) < 0) {
				if (currentTime.getDayOfMonth() == creationTime.getDayOfMonth()) date = "Today at ";
				else if (currentTime.getDayOfMonth() - 1 == creationTime.getDayOfMonth()) date = "Yesterday at ";
				else date = "Last " + zoned.getDayOfWeek().toString().substring(0, 1) + zoned.getDayOfWeek().toString().substring(1).toLowerCase() + " at ";
				String minute = zoned.getMinute() < 10 ? "0" + zoned.getMinute() : "" + zoned.getMinute();
				if (zoned.getHour() == 0) time = "12:" + minute + " AM";
				else if (zoned.getHour() < 12) time = zoned.getHour() + ":" + minute + " AM";
				else if (zoned.getHour() == 12) time = "12:" + minute + " PM";
				else time = zoned.getHour() - 12 + ":" + minute + " PM";
			}
			else date = zoned.getMonth().getValue() + "/" + zoned.getDayOfMonth() + "/" + zoned.getYear();
			
			setText(date + time);
			setForeground(new Color(85, 87, 92));
			setFont(getFont().deriveFont(getFont().getSize() * 0.95f));
			setSize(getFontMetrics(getFont()).stringWidth(date + time), 20);
		}
	}
	
	private class ImageLabel extends JLabel {
		
		private static final long serialVersionUID = -8662361637132459429L;
		
		private ImageLabel(Attachment att) {
			try {
				URLConnection conn;
				URL url;
				url = new URL(att.getUrl());
				conn = url.openConnection();
				conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36");
				
				BufferedImage rawImage = ImageIO.read(conn.getInputStream());
				
				int oldHeight = rawImage.getHeight(), oldWidth = rawImage.getWidth();
				int newWidth = 300;
				int newHeight = (int)(((double)newWidth / (double)oldWidth) * oldHeight);
				setIcon(new ImageIcon(rawImage.getScaledInstance(newWidth, newHeight, Image.SCALE_AREA_AVERAGING)));

				Dimension size = new Dimension(newWidth, newHeight);
				
				setMinimumSize(size);
				setSize(size);
				setPreferredSize(size);
				setMaximumSize(size);
				height += newHeight;
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}
}
