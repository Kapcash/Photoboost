package pda.control;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

import pda.datas.*;
import pda.view.*;

/**
*GalleryCtrl is the listener of the GalleryView, the gallery of the saved images
*
* @author Florent Catiau-Tristant and Mehdi Haddad
* @version 1.0
*/
public class GalleryCtrl extends MouseAdapter{
	
	/**
	*Is the main page
	*/
	PhotoBoostView photoboost;
	/**
	*Is the interface listening to this controler
	*/
	GalleryView gallery;
	/**
	*Is the panel containg an image and is frame
	*/
	JPanel panelRedim;
	/**
	*Is the BufferedImage of a clicked image
	*/
	BufferedImage imgSrc;

	/**
	*The listener for static button
	*@param gal Is the calling interface
	*/
	public GalleryCtrl(GalleryView gal){
		gallery = gal;
		photoboost = gallery.getParent();
	}
	/**
	*The listener for each image displayed
	*@param gal Is the calling interface
	*@param imgReduite Is the clicked image
	*@param img Is the bufferedImage of the clicked image
	*/
	public GalleryCtrl(GalleryView gal, JPanel imgReduite, BufferedImage img){
		gallery = gal;
		photoboost = gallery.getParent();
		panelRedim = imgReduite;
		imgSrc = img;
	}

	/**
	*Define action when clicking a button
	*@param e
	*/
	public void mousePressed(MouseEvent e){
		Object src = e.getSource();
		if(src == gallery.getRetour()){
			gallery.getRetour().setIcon(new ImageIcon("./data/img/Elements/backPUSHED.png"));
		}
		if(src == panelRedim){
			//Création d'une fenêtre EditPhotoView en fonction de l'image cliquée
			ApercuView apercu = new ApercuView(gallery, imgSrc);
			photoboost.getCardLayout().show(photoboost.getPanel(), "PanelApercu");
		}
	}

	/**
	*Define action when release a button
	*@param e
	*/
	public void mouseReleased(MouseEvent e){
		Object src = e.getSource();

		if(src == gallery.getRetour()){
			gallery.getRetour().setIcon(new ImageIcon("./data/img/Elements/back.png"));
			photoboost.getCardLayout().show(photoboost.getPanel(), "PanelHome");
		}
	}

	/**
	*Set the cursor to an hand when passing throw a button
	*@param e
	*/
	public void mouseEntered(MouseEvent e){
		Object src = e.getSource();

		if(src == gallery.getRetour()){
			gallery.getRetour().setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		if(src == panelRedim){
			panelRedim.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
	}

	/**
	*Set the cursor to default
	*@param e
	*/
	public void mouseExited(MouseEvent e){
		Object src = e.getSource();

		if(src == gallery.getRetour()){
			gallery.getRetour().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		if(src == panelRedim){
			panelRedim.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
	}
}