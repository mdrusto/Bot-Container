package yafta98.botcontainer.tabs.discordviewpanel;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import net.dv8tion.jda.core.entities.*;

public class EntityDetailsPanel extends JPanel {
		
	private Guild selectedGuild = null;

	private static final long serialVersionUID = 8881086496966596033L;
	
	public void setVisible(ISnowflake entity) {
		List<String> details = new ArrayList<String>();
		DateTimeFormatter format = DateTimeFormatter.BASIC_ISO_DATE;
		
		if (entity instanceof Message) {
			Message message = (Message) entity;
			details.add("Creation time: " + message.getCreationTime().format(format));
			details.add("Content" + message.getContent());
			details.add("Author: " + message.getAuthor().getAsMention());
			details.add("Raw content: " + message.getRawContent());
			details.add("Stripped content: " + message.getStrippedContent());
			details.add("Channel name" + message.getChannel().getName());
			details.add("Is TTS: " + message.isTTS());
			details.add("Mentions everyone: " + message.mentionsEveryone());
			details.add(message.getCreationTime().format(format));
		}
		else if (entity instanceof User) {
			User user = (User) entity;
			details.add("Name: " + user.getName());
			if (selectedGuild != null) {
				Member member = selectedGuild.getMember(user);
				details.add("Nickname: " + member.getNickname());
				details.add("Is playing: " + member.getGame().getName());
				details.add("Join date: " + member.getJoinDate());
				details.add("Online status: " + member.getOnlineStatus().toString());
				details.add(("In voice channel: " + member.getVoiceState().getChannel().getName()).replace("null", "false"));
				details.add("Is deafened: " + member.getVoiceState().isDeafened());
				details.add("Is guild-deafened: " + member.getVoiceState().isGuildDeafened());
				details.add("Is muted: " + member.getVoiceState().isMuted());
				details.add("Is guild-muted: " + member.getVoiceState().isGuildMuted());
				details.add("Is suppressed: " + member.getVoiceState().isSuppressed());
			}
			details.add("Creation time: " + user.getCreationTime().format(format));
			details.add("Is bot: " + user.isBot());
			details.add("Discriminator: " + user.getDiscriminator());
			details.add("Avatar URL: " + user.getAvatarUrl());
			details.add("Has private channel: " + user.hasPrivateChannel());
			
		}
		else if (entity instanceof TextChannel) {
			TextChannel channel = (TextChannel) entity;
			details.add("Name: " + channel.getName());
			details.add("Creation time: " + channel.getCreationTime());
			details.add("Position: " + channel.getPosition());
			details.add("Raw position: " + channel.getPositionRaw());
			details.add("Topic: " + channel.getTopic());
			details.add("Can talk: " + channel.canTalk());
		}
		else if (entity instanceof PrivateChannel) {
			PrivateChannel channel = (PrivateChannel) entity;
			details.add("Name: " + channel.getName());
			details.add("Creation time: " + channel.getCreationTime().format(format));
			details.add("User: " + channel.getUser().getAsMention());
		}
		else if (entity instanceof Guild) {
			Guild guild = (Guild) entity;
			details.add("Name: " + guild.getName());
			details.add("Creation time: " + guild.getCreationTime().format(format));
			details.add("Owner: " + guild.getOwner().getAsMention());
			details.add("Icon URL: " + guild.getIconUrl());
			details.add("AFK Channel: " + guild.getAfkChannel().getName());
			details.add("AFK Timeout: " + guild.getAfkTimeout().getSeconds() + " seconds");
			details.add("Default channel: " + guild.getPublicChannel().getAsMention());
			details.add("Default role: " + guild.getPublicRole().getName());
		}
		else if (entity instanceof Role) {
			Role role = (Role) entity;
			details.add("Name: " + role.getName());
			details.add("ID: " + role.getId());
			details.add("Position: " + role.getPosition());
			details.add("Raw position: " + role.getPositionRaw());
			details.add("Color: " + "R: " + role.getColor().getRed() + " G: " + role.getColor().getGreen() + " B: " + role.getColor().getBlue());
			details.add("Is hoisted: " + role.isHoisted());
			details.add("Is mentionable: " + role.isMentionable());
			details.add("Is managed: " + role.isManaged());
		}

		
		
		
		
		super.setVisible(true);
	}
	
}
