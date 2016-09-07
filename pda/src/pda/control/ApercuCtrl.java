package pda.control;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

import pda.datas.*;
import pda.view.*;

/**
*ApercuCtrl is the listener of the ApercuView, the survey.
*
* @author Florent Catiau-Tristant and Mehdi Haddad
* @version 1.0
*/
public class ApercuCtrl extends MouseAdapter{
	
	/**
	*Is the main page, containing the main panel of the application
	*/
	PhotoBoostView photoboost;
	/**
	*Is the interface listening this controler
	*/
	ApercuView apercu;
	
	/**
	*The constructor initialize the attributes
	*@param ep Is the interface listening this controler
	*/
	public ApercuCtrl(ApercuView ep){
		apercu = ep;
		photoboost = ep.getParent().getParent();
	}

	/**
	*Define action when clicking a button
	*@param e
	*/
	public void mousePressed(MouseEvent e){
		Object src = e.getSource();
		if(src == apercu.getRetour()){
			apercu.getRetour().setIcon(new ImageIcon("./data/img/Elements/backPUSHED.png"));
		}
	}

	/**
	*Define action when release a button
	*@param e
	*/
	public void mouseReleased(MouseEvent e){
		Object src = e.getSource();

		if(src == apercu.getRetour()){
			apercu.getRetour().setIcon(new ImageIcon("./data/img/Elements/back.png"));
			photoboost.getCardLayout().show(photoboost.getPanel(), "PanelGallery");
		}
	}

	/**
	*Set the cursor to an hand when passing throw a button
	*@param e
	*/
	public void mouseEntered(MouseEvent e){
		Object src = e.getSource();

		if(src == apercu.getRetour()){
			apercu.getRetour().setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
	}

	/**
	*Set the cursor to default
	*@param e
	*/
	public void mouseExited(MouseEvent e){
		Object src = e.getSource();

		if(src == apercu.getRetour()){
			apercu.getRetour().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
	}

}