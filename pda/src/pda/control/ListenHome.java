package pda.control;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;
import java.io.*;
import javax.swing.filechooser.*;
import javax.swing.plaf.metal.*;
import java.lang.reflect.*;

import pda.datas.*;
import pda.view.*;

/**
*ApercuCtrl is the listener of the PhotoBoostView, the main page
*
* @author Florent Catiau-Tristant and Mehdi Haddad
* @version 1.0
*/
public class ListenHome extends MouseAdapter{
	
	/**
	*Is the main page
	*/
	private PhotoBoostView photoboost;
	/**
	*Is one of the two buttons of the page
	*/
	private JLabel choose, gallery;

	/**
	*The constructor initialize the buttons
	*@param pb Is the interface listening to this controler
	*/
	public ListenHome(PhotoBoostView pb){
		photoboost = pb;
		choose = pb.getChoose();
		gallery = pb.getGallery();
	}

	/**
	*Define action when clicking a button
	*@param e
	*/
	public void mousePressed(MouseEvent e){
		if(e.getSource() == choose){
			choose.setIcon(new ImageIcon("./data/img/Elements/BTchooseaphotoPUSHED.png"));
			Chargement load = new Chargement(photoboost);
		}
		else if(e.getSource() == gallery){
			gallery.setIcon(new ImageIcon("./data/img/Elements/BTviewmygalleryPUSHED.png"));
			Chargement load = new Chargement(photoboost);
			photoboost.getCardLayout().show(photoboost.getPanel(), "PanelChargement");
		}
		else if(e.getSource() == photoboost.getLabelParam()){
			photoboost.getLabelParam().setIcon(new ImageIcon("./data/img/Elements/BToptionsPUSHED.png"));
		}
	}

	/**
	*Define action when release a button
	*@param e
	*/
	public void mouseReleased(MouseEvent e){
		Object src = e.getSource();
		//photoboost.getCardLayout().show(photoboost.getPanel(), "PanelChargement");
		if(src == choose){
			photoboost.getCardLayout().show(photoboost.getPanel(), "PanelChargement");
			String file = this.open();
			if(file!=null){
				System.out.println("Dossier de chargement : "+file);
				//Chargement des images lors de la création de l'interface
				ChoosePhotoView choosePhoto = new ChoosePhotoView(photoboost, file);
				photoboost.getCardLayout().show(photoboost.getPanel(),"PanelChoose");
			}
			else{
				photoboost.getCardLayout().show(photoboost.getPanel(), "PanelHome");
			}
			choose.setIcon(new ImageIcon("./data/img/Elements/BTchooseaphoto.png"));
		}
		else if(src == gallery){
			File f = new File(photoboost.getSavePath());
			if (f.exists() && f.isDirectory()) {
				GalleryView galleryView = new GalleryView(photoboost);
				photoboost.getCardLayout().show(photoboost.getPanel(),"PanelGallery");
			}
			else{
				JOptionPane.showMessageDialog(photoboost.getPanel(), " The folder does not exist, \n Please change it in the settings", "ERROR", JOptionPane.ERROR_MESSAGE);
				photoboost.getCardLayout().show(photoboost.getPanel(), "PanelHome");
			}
			gallery.setIcon(new ImageIcon("./data/img/Elements/BTviewmygallery.png"));
		}
		else if(src == photoboost.getLabelParam()){
			photoboost.getLabelParam().setIcon(new ImageIcon("./data/img/Elements/BToptions.png"));
			ParamView param = new ParamView(photoboost);
			photoboost.getCardLayout().show(photoboost.getPanel(), "PanelParam");
		}
	}

	/**
	*Set the cursor to an hand when passing throw a button
	*@param e
	*/
	public void mouseEntered(MouseEvent e){
		Object src = e.getSource();

		if(src == choose){
			choose.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		if(src == gallery){
			gallery.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		if(src == photoboost.getLabelParam()){
			photoboost.getLabelParam().setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
	}

	/**
	*Set the cursor to default
	*@param e
	*/
	public void mouseExited(MouseEvent e){
		Object src = e.getSource();

		if(src == choose){
			choose.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		if(src == gallery){
			gallery.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		if(src == photoboost.getLabelParam()){
			photoboost.getLabelParam().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
	}

	private String open(){
		String ret = null;
		File file = new File("./data/parametres.out");
		JFileChooser chooser = new JFileChooser();
		//Rend le field du JFileChooser non éditable
		try{
		MetalFileChooserUI ui = (MetalFileChooserUI)chooser.getUI();
  		Field field = MetalFileChooserUI.class.getDeclaredField("fileNameTextField");
  		field.setAccessible(true);
  		JTextField tf = (JTextField) field.get(ui);
  		tf.setEditable(false);
  		}
  		catch(NoSuchFieldException e){
  			System.out.println(e.getMessage());
  		}
  		catch(IllegalAccessException e){
  			System.out.println(e.getMessage());
  		}
  		//------------------------------------------
   		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = chooser.showOpenDialog(photoboost.getPanel());
   		if(returnVal == JFileChooser.APPROVE_OPTION) {
    		File dir = chooser.getSelectedFile();
    		ret = dir.getPath();
   		}
   		return ret;
	}

}