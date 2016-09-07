package pda.view;

import javax.swing.*;
import java.awt.*;

public class NewViewport extends JViewport{
	
	private static final long serialVersionUID = 0;

	public NewViewport(){
		this.setOpaque(false);

	}

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(new Color(255,0,0));
	}

}