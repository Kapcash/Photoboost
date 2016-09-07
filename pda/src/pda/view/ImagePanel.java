package pda.view;

import java.awt.*;
import javax.swing.*;

/**
*Is a panel with a backgroup image
*/
public class ImagePanel extends JPanel{
	
	/**
	*Is the chosen image for the background
	*/
	private Image img;
	private static final long serialVersionUID = 1L;

	/**
	*Use the constructor of JPanel and add the given image
	*/
	public ImagePanel(Image image){
		this.img = image;
	}

	/**
	*Paint the given image on the panel
	*/
	public void paintComponent(Graphics g){
		g.drawImage(img,0,0,null);
	}

}