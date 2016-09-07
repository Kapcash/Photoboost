package pda.control;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

import pda.datas.*;
import pda.view.*;

/**
* ChooseCtrl is the listener of the ChoosePhotoView
*
* @author Florent Catiau-Tristant and Mehdi Haddad
* @version 1.0
*/
public class ChooseCtrl extends MouseAdapter{
	
	/**
	*Is the main page
	*/
	PhotoBoostView photoboost;
	/**
	*Is the interface listening to this controler
	*/
	ChoosePhotoView choosePhoto;
	/**
	*Is the clicked image
	*/
	JLabel labelRedim;
	/**
	*Is the bufferedImage of the clicked image
	*/
	BufferedImage imgSrc;

	/**
	*The listener for static button
	*@param cb Is the calling interface
	*/
	public ChooseCtrl(ChoosePhotoView cb){
		choosePhoto = cb;
		photoboost = choosePhoto.getParent();
	}

	/**
	*The listener for each image displayed
	*@param cb Is the calling interface
	*@param imgReduite Is the clicked image
	*@param img Is the bufferedImage of the clicked image
	*/
	public ChooseCtrl(ChoosePhotoView cb, JLabel imgReduite, BufferedImage img){
		choosePhoto = cb;
		photoboost = choosePhoto.getParent();
		labelRedim = imgReduite;
		imgSrc = img;
	}

	/**
	*Define action when clicking a button
	*@param e
	*/
	public void mousePressed(MouseEvent e){
		Object src = e.getSource();
		if(src == choosePhoto.getRetour()){
			choosePhoto.getRetour().setIcon(new ImageIcon("./data/img/Elements/backPUSHED.png"));
		}
		if(src == labelRedim){
			//Création d'une fenêtre EditPhotoView en fonction de l'image cliquée
			EditPhotoView editPhoto = new EditPhotoView(choosePhoto, imgSrc);
			photoboost.getCardLayout().show(photoboost.getPanel(), "PanelEdit");
		}
	}

	/**
	*Define action when release a button
	*@param e
	*/
	public void mouseReleased(MouseEvent e){
		Object src = e.getSource();

		if(src == choosePhoto.getRetour()){
			choosePhoto.getRetour().setIcon(new ImageIcon("./data/img/Elements/back.png"));
			photoboost.getCardLayout().show(photoboost.getPanel(), "PanelHome");
		}
	}

	/**
	*Set the cursor to an hand when passing throw a button
	*@param e
	*/
	public void mouseEntered(MouseEvent e){
		Object src = e.getSource();

		if(src == choosePhoto.getRetour()){
			choosePhoto.getRetour().setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		if(src == labelRedim){
			labelRedim.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
	}

	/**
	*Set the cursor to default
	*@param e
	*/
	public void mouseExited(MouseEvent e){
		Object src = e.getSource();

		if(src == choosePhoto.getRetour()){
			choosePhoto.getRetour().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		if(src == labelRedim){
			labelRedim.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
	}

}