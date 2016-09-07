package pda.control;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.swing.filechooser.*;
import javax.swing.plaf.metal.*;
import java.lang.reflect.*;

import pda.datas.*;
import pda.view.*;

/**
*ParamCtrl is the listener of the ParamView
*
* @author Florent Catiau-Tristant and Mehdi Haddad
* @version 1.0
*/
public class ParamCtrl extends MouseAdapter implements ActionListener{
	
	/**
	*Is the main page
	*/
	PhotoBoostView photoboost;
	/**
	*Is the interface listening to this controler
	*/
	ParamView view;
	/**
	*Is the datas of the parameters
	*/
	ParamDatas data;
	/**
	*Is the path of the save directory
	*/
	String file;

//Constructeur
	/**
	*@param paramV Is the view
	*@param paramD Is the datas
	*/
	public ParamCtrl(ParamView paramV, ParamDatas paramD){
		view = paramV;
		data = paramD;
		photoboost = view.getParent();
	}

	/**
	*Define action when clicking a button
	*@param e
	*/
	public void mousePressed(MouseEvent e){
		Object src = e.getSource();
		if(src == view.getRetour()){
			view.getRetour().setIcon(new ImageIcon("./data/img/Elements/backPUSHED.png"));
		}
	}

	/**
	*Define action when release a button
	*@param e
	*/
	public void mouseReleased(MouseEvent e){
		Object src = e.getSource();
		if(src == view.getRetour()){
			view.getRetour().setIcon(new ImageIcon("./data/img/Elements/back.png"));
			photoboost.getCardLayout().show(photoboost.getPanel(), "PanelHome");
		}
		view.reloadParam();
	}

	/**
	*Set the cursor to an hand when passing throw a button
	*@param e
	*/
	public void mouseEntered(MouseEvent e){
		Object src = e.getSource();

		if(src == view.getRetour()){
			view.getRetour().setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
	}

	/**
	*Set the cursor to default
	*@param e
	*/
	public void mouseExited(MouseEvent e){
		Object src = e.getSource();

		if(src == view.getRetour()){
			view.getRetour().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
	}

	/**
	*Define the clicking actions
	*/
	public void actionPerformed(ActionEvent e){
		Object src = e.getSource();

		if(src == view.getApply()){
			if(view.radio1Checked()){
				data.setColumn(2);
			}
			else{
				data.setColumn(3);
			}
			if(file != null){
				data.setSavePath(file);
			}
			data.saveParam();
			photoboost.getCardLayout().show(photoboost.getPanel(), "PanelHome");
		}
		if(src == view.getButtonPath()){
			file = this.open();
			if(file!=null){
				view.setFieldPath(file);
				System.out.println("Dossier choisi : "+file);
			}
		}
		view.reloadParam();

	}

	/**
	*Open a JFileChoser, to selected a new directory to save modified images
	*@return Return the path of the selected directory
	*/
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