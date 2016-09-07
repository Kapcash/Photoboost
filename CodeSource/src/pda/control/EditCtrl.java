package pda.control;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

import pda.datas.*;
import pda.view.*;

/**
*EditCtrl is the listener of the EditView, the edition interface
*
* @author Florent Catiau-Tristant and Mehdi Haddad
* @version 1.0
*/
public class EditCtrl extends MouseAdapter implements KeyListener, ChangeListener{

//Attributs
	/**
	*Is the main page
	*/
	private PhotoBoostView photoboost;
	/**
	*Is the interface listening to this controler
	*/
	private EditPhotoView editPhoto;
	/**
	*Is the bufferedImage of the selected image
	*/
	private BufferedImage imgSrc;
	/**
	*Is the effect class to do effects on the selected image
	*/
	private Effect effect;
	/**
	*Is the path to save modified images
	*/
	private String savePath;
	//Attributs de la popup de redimensionnement
	/**
	*[Resize] Is the textField for the width 
	*/
	private JTextField largeurTF;
	/**
	*[Resize] Is the textField for the length 
	*/
	private JTextField hauteurTF;
	/**
	*[Resize] Is the checkbox to set relative resize or not
	*/
	private JCheckBox relatif = new JCheckBox("Relative", null, true);

//Constructeur
	/**
	*The constructor initialize :
	*<ul>
	*<li> The interface's attributes </li>
	*<li> The selected image</li>
	*<li> The effect class</li>
	*<li> The unavailable button</li>
	*</ul>
	*@param ep Is the interface listening to this controler
	*/
	public EditCtrl(EditPhotoView ep){
		editPhoto = ep;
		photoboost = ep.getParent();
		savePath = photoboost.getSavePath();
		imgSrc = ep.getBufferedImage();
		effect = new Effect(this);
		editPhoto.getCancel().setIcon(new ImageIcon("./data/img/Elements/cancelGRAY.png"));
		editPhoto.getPrevious().setIcon(new ImageIcon("./data/img/Elements/undoGRAY.png"));
		editPhoto.getNext().setIcon(new ImageIcon("./data/img/Elements/redoGRAY.png"));
	}

//Accesseurs
	/**
	*@return Return the bufferedImage of the current displayed image
	*/
	private BufferedImage currentImg(){return effect.getBI(effect.getCpt());}
	/**
	*@return Return the bufferedImage of the original image
	*/
	public BufferedImage getImgSrc(){return imgSrc;}
	/**
	*@param img Is the new bufferedImage of the displayed image
	*/
	public void setImg(BufferedImage img){editPhoto.setImage(img);}
	/**
	*@return Return the path of the directory to save modified images
	*/
	public String getPath(){return savePath;}
	/**
	*@param newPath Is the new path of the directory to save modified images
	*/
	public void setPath(String newPath){savePath = newPath;}

	/**
	*Define action when clicking a button<br/>
	*Change the button icon when clicking it<br/>
	*Display the chosen panels (According to the selected folder, the pro modes)<br/>
	*Set the color of the text according to the selected folder<br/>
	*
	*@param e
	*/
	public void mousePressed(MouseEvent e){
		Object src = e.getSource();

	//TopBar
		//Bouton vers l'arrière
		if(src == editPhoto.getPrevious()){
			if(effect.getCpt()==1){
				editPhoto.getPrevious().setIcon(new ImageIcon("./data/img/Elements/undoGRAY.png"));
			}
			else{
				editPhoto.getPrevious().setIcon(new ImageIcon("./data/img/Elements/undoPUSHED.png"));
			}
		}
		//Bouton vers l'avant
		if(src == editPhoto.getNext()){
			if(effect.getCpt()==effect.getTemp().size()){
				editPhoto.getNext().setIcon(new ImageIcon("./data/img/Elements/redoGRAY.png"));
			}
			else{
				editPhoto.getNext().setIcon(new ImageIcon("./data/img/Elements/redoPUSHED.png"));
			}
		}
		//Bouton valider
		if(src == editPhoto.getValid()){
			panelInvi();
			editPhoto.getValid().setIcon(new ImageIcon("./data/img/Elements/validPUSHED.png"));
		}
		//Bouton annuler
		if(src == editPhoto.getCancel()){
			if(effect.getCpt()==effect.getTemp().size() && effect.getCpt()==1){
			editPhoto.getCancel().setIcon(new ImageIcon("./data/img/Elements/cancelGRAY.png"));
			}
			else{
				editPhoto.getCancel().setIcon(new ImageIcon("./data/img/Elements/cancel.png"));
				panelInvi();
				editPhoto.getCancel().setIcon(new ImageIcon("./data/img/Elements/cancelPUSHED.png"));
				this.selectedElements("null");
			}
			
		}
		//Bouton exit
		if(src == editPhoto.getExit()){
			panelInvi();
			editPhoto.getExit().setIcon(new ImageIcon("./data/img/Elements/exitPUSHED.png"));
			this.selectedElements("null");
		}
		

	//ToolBar
		//Effect
		if(src == editPhoto.getEffect()){
			panelInvi();
			this.selectedElements("Effect");
			editPhoto.getCardLayout().show(editPhoto.getPanelCard(), "panelEffect");
		}
		//Filter
		if(src == editPhoto.getFilter()){
			panelInvi();
			this.selectedElements("Filter");
			editPhoto.getCardLayout().show(editPhoto.getPanelCard(), "panelFilter");
		}	
		//Size
		if(src == editPhoto.getSize()){
			panelInvi();
			this.selectedElements("Size");
			editPhoto.getCardLayout().show(editPhoto.getPanelCard(), "panelSize");
		}
	


	//Effect
		//Flou
		if(src == editPhoto.getBlur()){
			loadPanelPro();
			this.selectedElements("Blur");
			editPhoto.getCardPro().show(editPhoto.getPanelCardPro(),"panelFlou");
		}
			//Flou Appliquer
			if(src == editPhoto.getApplyFlou()){
				panelInvi();
				effect.ajouter(effect.flou(editPhoto.getSliderFlou().getValue(), currentImg()));
			}
		//Repoussage
		if(src == editPhoto.getEmboss()){
			panelInvi();
			this.selectedElements("Emboss");
			effect.ajouter(effect.repoussage(currentImg()));
		}
		//Pixellisation
		if(src == editPhoto.getPixelize()){
			loadPanelPro();
			this.selectedElements("Pixelize");
			editPhoto.getCardPro().show(editPhoto.getPanelCardPro(),"panelPix");
		}
			//Pixellisation Appliquer
			if(src== editPhoto.getApplyPix()){
				effect.ajouter(effect.pixellisation(editPhoto.getSliderPix().getValue(), currentImg()));
				panelInvi();
			}



	//Filter
			//Sepia
			if(src == editPhoto.getSepia()){
				panelInvi();
				this.selectedElements("Sepia");
				effect.ajouter(effect.sepia(currentImg()));
			}
			//Negatif
			if(src == editPhoto.getNegative()){
				panelInvi();
				this.selectedElements("Negative");
				effect.ajouter(effect.negatif(currentImg()));
			}
			//Noir et Blanc
			if(src == editPhoto.getBlack()){
				panelInvi();
				this.selectedElements("Black&White");
				effect.ajouter(effect.blackwhite(currentImg()));
			}

	//Size
			//Resize
			if(src== editPhoto.getResize()){
				panelInvi();
				this.selectedElements("Resize");
				this.resizeButton();
			}
			//Rognage
			if(src == editPhoto.getCrop()){
				loadPanelCrop();
				this.selectedElements("Crop");
				this.cropButton();
				editPhoto.getCardPro().show(editPhoto.getPanelCardPro(),"panelCrop");
			}
				//Appliquer rognage
				if(src==editPhoto.getApplyCrop()){
					panelInvi();
					int xDebut = editPhoto.getSliderCrop1().getValue();
					int yDebut = editPhoto.getSliderCrop2().getValue();
					int xFin = editPhoto.getSliderCrop3().getValue();
					int yFin = editPhoto.getSliderCrop4().getValue();
					effect.ajouter(effect.rognage(currentImg(), xDebut, xFin, yDebut, yFin));
				}
			//Rotate
			if(src == editPhoto.getRotate()){
				loadPanelPro();
				this.selectedElements("Rotate");
				editPhoto.getCardPro().show(editPhoto.getPanelCardPro(),"panelRotate");
			}
				//Rotation vers la gauche
				if(src == editPhoto.getRotateG()){
					effect.ajouter(effect.rotationGauche(currentImg()));
				}
				//Rotation vers la droite
				if(src == editPhoto.getRotateD()){
					effect.ajouter(effect.rotationDroite(currentImg()));
				}
			//Mirror
			if(src == editPhoto.getMirror()){
				loadPanelPro();
				this.selectedElements("Mirror");
				editPhoto.getCardPro().show(editPhoto.getPanelCardPro(),"panelMirror");
			}
				//Mirroir Vertical
				if(src == editPhoto.getMirrorV()){
					effect.ajouter(effect.mirroirVertical(currentImg()));
				}
				//Mirroir Horizontal
				if(src == editPhoto.getMirrorH()){
					effect.ajouter(effect.mirroirHorizontal(currentImg()));
				}
	}


	/**
	*Define action when release a button<br/>
	*Set the icon of the clicked button to their original appearance
	*Set the color of the text according to the selected folder
	*
	*@param e
	*/
	public void mouseReleased(MouseEvent e){
		Object src = e.getSource();
		//Slider pixellisation
		if(src == editPhoto.getSliderPix()){
			editPhoto.setImage(effect.pixellisation(editPhoto.getSliderPix().getValue(), currentImg()));	
		}
		//Slider Flou
		if(src == editPhoto.getSliderFlou()){
			editPhoto.setImage(effect.flou(editPhoto.getSliderFlou().getValue(), currentImg()));
		}

		//Bouton exit
		if(src == editPhoto.getExit()){
			editPhoto.getExit().setIcon(new ImageIcon("./data/img/Elements/exit.png"));
			this.selectedElements("null");
			photoboost.getCardLayout().show(photoboost.getPanel(), "PanelChoose");
		}
		//Bouton vers l'arrière
		if(src == editPhoto.getPrevious()){
			editPhoto.getPrevious().setIcon(new ImageIcon("./data/img/Elements/undo.png"));
			effect.undo();
		}
		//Bouton vers l'avant
		if(src == editPhoto.getNext()){
			editPhoto.getNext().setIcon(new ImageIcon("./data/img/Elements/redo.png"));
			effect.redo();
		}
		//Bouton valider
		if(src == editPhoto.getValid()){
			this.saveButton();
			editPhoto.getValid().setIcon(new ImageIcon("./data/img/Elements/valid.png"));
			this.selectedElements("null");
		}
		//Bouton annuler
		if(src == editPhoto.getCancel()){
			if(effect.getCpt()==effect.getTemp().size() && effect.getCpt()==1){
			editPhoto.getCancel().setIcon(new ImageIcon("./data/img/Elements/cancelGRAY.png"));
			}
			else{
				editPhoto.getCancel().setIcon(new ImageIcon("./data/img/Elements/cancel.png"));
				effect.cancel();
				editPhoto.getCancel().setIcon(new ImageIcon("./data/img/Elements/cancel.png"));
				this.selectedElements("null");
			}
			
		}


		//Effect
		
			//Flou Appliquer
			if(src == editPhoto.getApplyFlou()){
				this.selectedElements("Effect");
			}
		//Repoussage
		if(src == editPhoto.getEmboss()){
			this.selectedElements("Effect");
		}
			//Pixellisation Appliquer
			if(src== editPhoto.getApplyPix()){
				this.selectedElements("Effect");
			}

		//Filter
			//Sepia
			if(src == editPhoto.getSepia()){
				this.selectedElements("Filter");
			}
			//Negatif
			if(src == editPhoto.getNegative()){
				this.selectedElements("Filter");
			}
			//Noir et Blanc
			if(src == editPhoto.getBlack()){
				this.selectedElements("Filter");
			}
			this.grayButton();
	}

	/**
	*Set the cursor to an hand when passing throw a button
	*@param e
	*/
	public void mouseEntered(MouseEvent e){
		Object src = e.getSource();
	//TopBar
		//Bouton vers l'arrière
		if(src == editPhoto.getPrevious() && effect.getCpt()!=1){
			editPhoto.getPrevious().setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		//Bouton vers l'avant
		if(src == editPhoto.getNext() && effect.getCpt()!=effect.getTemp().size()){
			editPhoto.getNext().setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		//Bouton valider
		if(src == editPhoto.getValid()){
			editPhoto.getValid().setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		//Bouton annuler
		if(src == editPhoto.getCancel() && (effect.getCpt()!=1 || effect.getCpt()!=effect.getTemp().size())){
			editPhoto.getCancel().setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		//Bouton exit
		if(src == editPhoto.getExit()){
			editPhoto.getExit().setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		

	//ToolBar
		if(src == editPhoto.getEffect()){
			editPhoto.getEffect().setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		if(src == editPhoto.getFilter()){
			editPhoto.getFilter().setCursor(new Cursor(Cursor.HAND_CURSOR));
		}	
		if(src == editPhoto.getSize()){
			editPhoto.getSize().setCursor(new Cursor(Cursor.HAND_CURSOR));
		}

	//Effects
		//Effect
		if(src == editPhoto.getEmboss()){
			editPhoto.getEmboss().setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		if(src == editPhoto.getBlur()){
			editPhoto.getBlur().setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		if(src == editPhoto.getPixelize()){
			editPhoto.getPixelize().setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		//Filter
		if(src == editPhoto.getBlack()){
			editPhoto.getBlack().setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		if(src == editPhoto.getSepia()){
			editPhoto.getSepia().setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		if(src == editPhoto.getNegative()){
			editPhoto.getNegative().setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		//Size
		if(src == editPhoto.getRotate()){
			editPhoto.getRotate().setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		if(src == editPhoto.getCrop()){
			editPhoto.getCrop().setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		if(src == editPhoto.getMirror()){
			editPhoto.getMirror().setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		if(src == editPhoto.getResize()){
			editPhoto.getResize().setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		//Pro mode
		if(src == editPhoto.getRotateD()){
			editPhoto.getRotateD().setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		if(src == editPhoto.getRotateG()){
			editPhoto.getRotateG().setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		if(src == editPhoto.getMirrorH()){
			editPhoto.getMirrorH().setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		if(src == editPhoto.getMirrorV()){
			editPhoto.getMirrorV().setCursor(new Cursor(Cursor.HAND_CURSOR));
		}

		this.grayButton();
	}

	/**
	*Set the cursor to default
	*@param e
	*/
	public void mouseExit(MouseEvent e){
		Object src = e.getSource();
		//TopBar
		//Bouton vers l'arrière
		if(src == editPhoto.getPrevious()){
			editPhoto.getPrevious().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		//Bouton vers l'avant
		if(src == editPhoto.getNext()){
			editPhoto.getNext().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		//Bouton valider
		if(src == editPhoto.getValid()){
			editPhoto.getValid().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		//Bouton annuler
		if(src == editPhoto.getCancel()){
			editPhoto.getCancel().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		//Bouton exit
		if(src == editPhoto.getExit()){
			editPhoto.getExit().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		

	//ToolBar
		if(src == editPhoto.getEffect()){
			editPhoto.getEffect().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		if(src == editPhoto.getFilter()){
			editPhoto.getFilter().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}	
		if(src == editPhoto.getSize()){
			editPhoto.getSize().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}

	//Effects
		//Effect
		if(src == editPhoto.getEmboss()){
			editPhoto.getEmboss().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		if(src == editPhoto.getBlur()){
			editPhoto.getBlur().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		if(src == editPhoto.getPixelize()){
			editPhoto.getPixelize().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		//Filter
		if(src == editPhoto.getBlack()){
			editPhoto.getBlack().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		if(src == editPhoto.getSepia()){
			editPhoto.getSepia().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		if(src == editPhoto.getNegative()){
			editPhoto.getNegative().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		//Size
		if(src == editPhoto.getRotate()){
			editPhoto.getRotate().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		if(src == editPhoto.getCrop()){
			editPhoto.getCrop().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		if(src == editPhoto.getMirror()){
			editPhoto.getMirror().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		if(src == editPhoto.getResize()){
			editPhoto.getResize().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		//Pro mode
		if(src == editPhoto.getRotateD()){
			editPhoto.getRotateD().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		if(src == editPhoto.getRotateG()){
			editPhoto.getRotateG().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		if(src == editPhoto.getMirrorH()){
			editPhoto.getMirrorH().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		if(src == editPhoto.getMirrorV()){
			editPhoto.getMirrorV().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		
		this.grayButton();
	}

	/**
	*
	*@param e
	*/
	public void keyTyped(KeyEvent e) {
		char caracter = e.getKeyChar();
		if (((caracter < '0') || (caracter > '9')) && (caracter != '\b')) {
				e.consume();
		}
	}

	/**
	*
	*@param e
	*/
	public void keyReleased(KeyEvent e){
		if(relatif.isSelected()){
			if(e.getSource()==largeurTF){
				try{
					int a = Integer.parseInt(largeurTF.getText());
					double coef = ((double)a)/((double)currentImg().getWidth());
					double d = ((double)currentImg().getHeight())*coef;
					int f = (int)d;
					hauteurTF.setText(String.valueOf(f));
				}
				catch(NumberFormatException nb){
					hauteurTF.setText("");
				}
			}
			if(e.getSource()==hauteurTF){
				try{
					int b = Integer.parseInt(hauteurTF.getText());
					double coef = ((double)b)/((double)currentImg().getHeight());
					double d = ((double)currentImg().getWidth())*coef;
					int f = (int)d;
					largeurTF.setText(String.valueOf(f));
				}
				catch(NumberFormatException nb){
					largeurTF.setText("");
				}
			}
		}
	}

	public void keyPressed(KeyEvent e){}

	/**
	*Set the values to the crop effect according to the position of the sliders
	*@param e
	*/
	public void stateChanged(ChangeEvent e) {
		if(e.getSource()==editPhoto.getSliderCrop1()){
			int val = (-1)*(editPhoto.getSliderCrop3().getValue()-currentImg().getWidth());
			if(editPhoto.getSliderCrop1().getValue()>=val){
				editPhoto.getSliderCrop3().setValue(currentImg().getWidth()-editPhoto.getSliderCrop1().getValue()-1);
			}
		}
		if(e.getSource()==editPhoto.getSliderCrop2()){
			int val = (-1)*(editPhoto.getSliderCrop4().getValue()-currentImg().getHeight());
			if(editPhoto.getSliderCrop2().getValue()>=val){
				editPhoto.getSliderCrop4().setValue(currentImg().getHeight()-editPhoto.getSliderCrop2().getValue()-1);
			}
		}
		if(e.getSource()==editPhoto.getSliderCrop3()){
			int val = (-1)*(editPhoto.getSliderCrop1().getValue()-currentImg().getWidth());
			if(editPhoto.getSliderCrop3().getValue()>=val){
				editPhoto.getSliderCrop1().setValue(currentImg().getWidth()-editPhoto.getSliderCrop3().getValue()-1);
			}
		}
		if(e.getSource()==editPhoto.getSliderCrop4()){
			int val = (-1)*(editPhoto.getSliderCrop2().getValue()-currentImg().getHeight());
			if(editPhoto.getSliderCrop4().getValue()>=val){
				editPhoto.getSliderCrop2().setValue(currentImg().getHeight()-editPhoto.getSliderCrop4().getValue()-1);
			}
		}
	}
	
//Reactions

	/**
	*Display the resize popup 
	*/
	private void resizeButton(){

		largeurTF = new JTextField("");
		hauteurTF = new JTextField("");
		
		largeurTF.setText(String.valueOf(currentImg().getWidth()));
		hauteurTF.setText(String.valueOf(currentImg().getHeight()));
		largeurTF.addKeyListener(this);
		hauteurTF.addKeyListener(this);

		Object message[] = {
			"Width: ",
			largeurTF,
			"Height: ",
			hauteurTF,
			relatif
    	};

    	boolean itsOK = false;
    	while(!itsOK){
	  		int test = JOptionPane.showConfirmDialog(editPhoto.getPanel(), message, "Resize", JOptionPane.OK_CANCEL_OPTION);
	    	if (test == 2){System.out.println("Cancel is clicked.."); itsOK=true;}
			else if (test == -1){System.out.println("Close is clicked"); itsOK=true;}
			else if (test == 0) {
				try{
					if(!hauteurTF.getText().equals("0") && !largeurTF.getText().equals("0")){
						effect.ajouter(effect.resize(currentImg(), Integer.parseInt(largeurTF.getText()), Integer.parseInt(hauteurTF.getText())));
						itsOK=true;
					}
					else{
						JOptionPane.showMessageDialog(editPhoto.getPanel(), "Invalid size ! ('0')", "Error", JOptionPane.ERROR_MESSAGE);
					}
					
				}
				catch(NumberFormatException e){
					JOptionPane.showMessageDialog(editPhoto.getPanel(), "Invalid size !", "Error", JOptionPane.ERROR_MESSAGE);
				}
				
			}
    	}
    	
	}


	/**
	*Set the button to gray to show they can't be clicked
	*/
	private void grayButton(){
		//Bouton Annuler
		if(effect.getCpt()==effect.getTemp().size() && effect.getCpt()==1){
			editPhoto.getCancel().setIcon(new ImageIcon("./data/img/Elements/cancelGRAY.png"));
		}
		else{
			editPhoto.getCancel().setIcon(new ImageIcon("./data/img/Elements/cancel.png"));
		}
		//Bouton Previous
		if(effect.getCpt()==1){
			editPhoto.getPrevious().setIcon(new ImageIcon("./data/img/Elements/undoGRAY.png"));
		}
		else{
			editPhoto.getPrevious().setIcon(new ImageIcon("./data/img/Elements/undo.png"));
		}
		//Bouton Next
		if(effect.getCpt()==effect.getTemp().size()){
			editPhoto.getNext().setIcon(new ImageIcon("./data/img/Elements/redoGRAY.png"));
		}
		else{
			editPhoto.getNext().setIcon(new ImageIcon("./data/img/Elements/redo.png"));
		}
	}

	/**
	*Display the pro mode of crop effect
	*/
	private void cropButton(){
		editPhoto.getSliderCrop1().setMinimum(0);
		editPhoto.getSliderCrop1().setMaximum(currentImg().getWidth()-1);
		editPhoto.getSliderCrop1().setValue(0);
		editPhoto.getSliderCrop2().setMinimum(0);
		editPhoto.getSliderCrop2().setMaximum(currentImg().getHeight()-1);
		editPhoto.getSliderCrop2().setValue(0);
		editPhoto.getSliderCrop3().setMinimum(0);
		editPhoto.getSliderCrop3().setMaximum(currentImg().getWidth()-1);
		editPhoto.getSliderCrop3().setValue(0);
		editPhoto.getSliderCrop4().setMinimum(0);
		editPhoto.getSliderCrop4().setMaximum(currentImg().getHeight()-1);
		editPhoto.getSliderCrop4().setValue(0);

		//For vertical bars
			int a1 = 167;
			int b1 = editPhoto.getTmp().getIcon().getIconHeight();
			int c1 = a1 - b1;
			double d1 = ((double)c1)/2.0;
			int e1 = (int)d1;
		//For horizontal bars
			int a2 = 302;
			int b2 = editPhoto.getTmp().getIcon().getIconWidth();
			int c2 = a2 - b2;
			double d2 = ((double)c2)/2.0;
			int e2 = (int)d2;

		editPhoto.getPanelSlider2().setBorder(BorderFactory.createEmptyBorder(e1-4,e2-8,e1-4,0));
		editPhoto.getPanelSlider4().setBorder(BorderFactory.createEmptyBorder(e1-4,0,e1-4,e2-8));

		editPhoto.getPanelSlider1().setBorder(BorderFactory.createEmptyBorder(0,e2,0,e2));
		editPhoto.getPanelSlider3().setBorder(BorderFactory.createEmptyBorder(0,e2,0,e2));
		
	}

	/**
	*Is the reaction of the save button
	*/
	private void saveButton(){

		JTextField fileN = new JTextField("");
		Object[] cbText={".png", ".jpg", ".bmp"};
		JComboBox cb = new JComboBox(cbText);
		Object message[] = {
			"Name:",
			fileN,
			"Format",
			cb
    	};
    	File saved = new File(savePath);
    	if(!saved.exists()){
    		JOptionPane.showMessageDialog(photoboost.getPanel(), " The folder does not exist, \n Please change it in the settings", "ERROR", JOptionPane.ERROR_MESSAGE);
    	}
    	else{
    		boolean itsOK = false;
    		while(!itsOK){
				int test = JOptionPane.showConfirmDialog(editPhoto.getPanel(), message, "Save", JOptionPane.OK_CANCEL_OPTION);
				String format = (String) cb.getSelectedItem();
				String filename = fileN.getText();
				String file = filename + format;
				
				if (test == 2){itsOK=true;} //Cancel is clicked
				else if (test == -1){itsOK=true;} //Close is clicked
				else if (test == 0){
					try{
						if(!filename.equals("")){
							File f = new File(savePath+"/" + file);
							System.out.println(f.toString());
								
								if(!f.exists()){
									if(format==".png"){ ImageIO.write(currentImg(),"PNG",f); }
									else if(format==".jpg"){ ImageIO.write(currentImg(),"JPEG",f); }
									else if(format==".bmp"){ ImageIO.write(currentImg(),"BMP",f); }
									itsOK=true;
									JOptionPane.showMessageDialog(editPhoto.getPanel(), file + " has been saved in \n"+savePath, "Success", JOptionPane.INFORMATION_MESSAGE);
								}
								else{
									int erase = JOptionPane.showConfirmDialog(editPhoto.getPanel(), "This image name already exists. \n Do you want to overwrite it? ?", "Save", JOptionPane.YES_NO_OPTION);
									if(erase == 0){
											if(format==".png"){ImageIO.write(currentImg(),"PNG",f);}
											else if(format==".jpg"){ImageIO.write(currentImg(),"JPEG",f);}
											else if(format==".bmp"){ImageIO.write(currentImg(),"BMP",f);}
											itsOK=true;
											JOptionPane.showMessageDialog(editPhoto.getPanel(), file + " has been saved in \n"+savePath, "Success", JOptionPane.INFORMATION_MESSAGE);
									}
								}
								
						}
						else{
							JOptionPane.showMessageDialog(editPhoto.getPanel(), "Please enter a name.", "Error", JOptionPane.ERROR_MESSAGE);
						}
					
					}
					catch(FileNotFoundException e){
						System.out.println("lol");
					}
					catch(IOException e){
						System.out.println("Error "+ e.getMessage());
					}
				}
			}
		}
	}

	/**
	*Display the image without pro panel
	*/
	private void panelInvi(){
		editPhoto.getCardPro().show(editPhoto.getPanelCardPro(),"panelInvi");
		editPhoto.getPanelCardPro().setPreferredSize(new Dimension(0,0));
		editPhoto.getImage().setIcon(editPhoto.resizeIcon(currentImg(),274,239));
	}

	/**
	*Display the image with a pro panel
	*/
	private void loadPanelPro(){
		editPhoto.getPanelCardPro().setPreferredSize(new Dimension(40,255));
		editPhoto.getImage().setIcon(editPhoto.resizeIcon(currentImg(),277, 244));
	}

	/**
	*Display the image with the crop pro panel (which is very particular)
	*/
	private void loadPanelCrop(){
		editPhoto.getPanelCardPro().setPreferredSize(new Dimension(317,255));
		editPhoto.getImage().setIcon(editPhoto.resizeIcon(currentImg(),1, 1));
		editPhoto.setTmp(editPhoto.resizeIcon(currentImg(),276,168));
	}

	/**
	*Set the color of the text according to the selected effect
	*/
	private void selectedElements(String selected){
		if(selected.equals("null")){
			editPhoto.getEffect().setForeground(Color.WHITE);
			editPhoto.getFilter().setForeground(Color.WHITE);
			editPhoto.getSize().setForeground(Color.WHITE);
			editPhoto.getCardLayout().show(editPhoto.getPanelCard(),"panelInvisi");
		}
		if(selected.equals("Effect")){
			editPhoto.getEffect().setForeground(Color.BLACK);
			editPhoto.getFilter().setForeground(Color.WHITE);
			editPhoto.getSize().setForeground(Color.WHITE);
			editPhoto.getBlur().setForeground(Color.WHITE);
			editPhoto.getEmboss().setForeground(Color.WHITE);
			editPhoto.getPixelize().setForeground(Color.WHITE);
		}
		if(selected.equals("Filter")){
			editPhoto.getEffect().setForeground(Color.WHITE);
			editPhoto.getFilter().setForeground(Color.BLACK);
			editPhoto.getSize().setForeground(Color.WHITE);
			editPhoto.getSepia().setForeground(Color.WHITE);
			editPhoto.getNegative().setForeground(Color.WHITE);
			editPhoto.getBlack().setForeground(Color.WHITE);
		}
		if(selected.equals("Size")){
			editPhoto.getPanelCard().setVisible(true);
			editPhoto.getEffect().setForeground(Color.WHITE);
			editPhoto.getFilter().setForeground(Color.WHITE);
			editPhoto.getSize().setForeground(Color.BLACK);
			editPhoto.getResize().setForeground(Color.WHITE);
			editPhoto.getCrop().setForeground(Color.WHITE);
			editPhoto.getRotate().setForeground(Color.WHITE);
			editPhoto.getMirror().setForeground(Color.WHITE);
		}
		if(selected.equals("Blur")){
			editPhoto.getBlur().setForeground(Color.BLACK);
			editPhoto.getEmboss().setForeground(Color.WHITE);
			editPhoto.getPixelize().setForeground(Color.WHITE);
		}
		if(selected.equals("Emboss")){
			editPhoto.getBlur().setForeground(Color.WHITE);
			editPhoto.getEmboss().setForeground(Color.BLACK);
			editPhoto.getPixelize().setForeground(Color.WHITE);
		}
		if(selected.equals("Pixelize")){
			editPhoto.getBlur().setForeground(Color.WHITE);
			editPhoto.getEmboss().setForeground(Color.WHITE);
			editPhoto.getPixelize().setForeground(Color.BLACK);
		}
		if(selected.equals("Sepia")){
			editPhoto.getSepia().setForeground(Color.BLACK);
			editPhoto.getNegative().setForeground(Color.WHITE);
			editPhoto.getBlack().setForeground(Color.WHITE);
		}
		if(selected.equals("Negative")){
			editPhoto.getSepia().setForeground(Color.WHITE);
			editPhoto.getNegative().setForeground(Color.BLACK);
			editPhoto.getBlack().setForeground(Color.WHITE);
		}
		if(selected.equals("Black&White")){
			editPhoto.getSepia().setForeground(Color.WHITE);
			editPhoto.getNegative().setForeground(Color.WHITE);
			editPhoto.getBlack().setForeground(Color.BLACK);
		}
		if(selected.equals("Resize")){
			editPhoto.getResize().setForeground(Color.BLACK);
			editPhoto.getCrop().setForeground(Color.WHITE);
			editPhoto.getRotate().setForeground(Color.WHITE);
			editPhoto.getMirror().setForeground(Color.WHITE);
		}
		if(selected.equals("Crop")){
			editPhoto.getResize().setForeground(Color.WHITE);
			editPhoto.getCrop().setForeground(Color.BLACK);
			editPhoto.getRotate().setForeground(Color.WHITE);
			editPhoto.getMirror().setForeground(Color.WHITE);
		}
		if(selected.equals("Rotate")){
			editPhoto.getResize().setForeground(Color.WHITE);
			editPhoto.getCrop().setForeground(Color.WHITE);
			editPhoto.getRotate().setForeground(Color.BLACK);
			editPhoto.getMirror().setForeground(Color.WHITE);
		}
		if(selected.equals("Mirror")){
			editPhoto.getResize().setForeground(Color.WHITE);
			editPhoto.getCrop().setForeground(Color.WHITE);
			editPhoto.getRotate().setForeground(Color.WHITE);
			editPhoto.getMirror().setForeground(Color.BLACK);
		}
	}

}