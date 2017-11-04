package yafta98.botcontainer.rightsidepanel;

import java.awt.Dimension;

import javax.swing.*;

import yafta98.botcontainer.CustomLabel;

public class Icon extends CustomLabel {
	
	private static final long serialVersionUID = 8758110233173287590L;
		
	public Icon() {
		super(new Dimension(300, 300));
		
		setIcon(new ImageIcon("resources/img/erasmus_icon.png"));
		setOpaque(false);
	}
}
