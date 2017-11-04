package yafta98.botcontainer.rightsidepanel;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;

import yafta98.botcontainer.CustomPanel;
import yafta98.botcontainer.bot.StatusController;
import yafta98.botcontainer.tabs.TabPanel;

public class RightSidePanel extends CustomPanel {

	private static final long serialVersionUID = 7503402610421354996L;

	public StatusPanel statusPanel = new StatusPanel();
	private TabPanel tabPanel;
	private Icon icon = new Icon();
	public RunPanel runPanel;

	public RightSidePanel(StatusController statusController) {
		tabPanel = new TabPanel(window);
		runPanel = (statusController == null) ? new InactiveRunPanel() : new ActiveRunPanel(statusController);
		GroupLayout layout = new GroupLayout(this);
		setLayout(layout);

		layout.setVerticalGroup(layout.createSequentialGroup().addComponent(statusPanel).addComponent(tabPanel)
				.addComponent(icon).addComponent(runPanel));
		layout.setHorizontalGroup(layout.createParallelGroup().addComponent(statusPanel).addComponent(tabPanel)
				.addComponent(icon).addComponent(runPanel));

		setBorder(BorderFactory.createMatteBorder(0, 2, 0, 0, new Color(80, 80, 80)));
	}
}
