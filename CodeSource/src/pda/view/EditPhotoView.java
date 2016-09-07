package pda.view;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.image.*;

import pda.datas.*;
import pda.control.*;

/**
*The EditPhotoView is the most important page of the application<br />
*It display a chosen photo and allow to do effects on it.<br />
*
* @author Florent Catiau-Tristant and Mehdi Haddad
* @version 1.0
*/
public class EditPhotoView{
//Attributs

	/**
	*[Main panels]
	*/
	private JPanel panelFond, panelSud, panelNord, panelDossier, panelEffets, panelImage;
	/**
	*[Secondary panels]
	*/
	private JPanel panelInvisi, panelInvi, panelSelect, panelEffect, panelFilter, panelSize;
	/**
	*[Pro panels]
	*/
	private JPanel panelPro, panelFlou, panelMirror, panelRotate, panelPix, panelCrop;
	private JPanel panelSlider1, panelSlider2, panelSlider3, panelSlider4;

	/**
	*[North labels]
	*/
	private JLabel labelImg, previous, next, valid, cancel, exit; 	//Nord
	/**
	*[South labels]
	*/
	private JLabel select,effect,filter,size; 						//Sud
	/**
	*[Selection label]
	*/
	private JLabel lasso, wand; 									//Selection
	/**
	*[Effect labels]
	*/
	private JLabel blur, emboss, pixelize;							// Effect
	/**
	*[Filter labels]
	*/
	private JLabel sepia, negative, blackWhite;			//Filter
	/**
	*[Resize labels]
	*/
	private JLabel resize, rotate, mirror, crop, labelTmp; 				//Resize
	/**
	*[Pro labels]
	*/
	private JLabel mirrorH, mirrorV, rotateD, rotateG;				//Pro

	/**
	*Buffered image of the chosen image
	*/
	private BufferedImage imgSrc;
	
	/**
	*[Pro] Slider
	*/
	private JSlider sliderFlou, sliderPix, sliderCrop1, sliderCrop2, sliderCrop3, sliderCrop4;
	/**
	*[Pro] Buttons
	*/
	private JButton applyFlou, applyPix, applyCrop;
	/**
	*
	*/
	private CardLayout cardEffect, cardPro;
	/**
	*The calling page
	*/
	private ChoosePhotoView choosePhoto;
	/**
	*Create a new Myriad Font
	*/
	private MyriadFont font = new MyriadFont();

//Constructeur
	/**
	*The constructor initialize :
	*<ul>
	*<li> The complete interface</li>
	*<li> The reactions</li>
	*</ul>
	*@param choose Is the calling interface
	*@param img Is the bufferedImage of the chosen image
	*/
	public EditPhotoView(ChoosePhotoView choose, BufferedImage img){
		choosePhoto = choose;
		imgSrc = img;
		this.creerInterface();
		this.reactions();
	}

//Accesseurs------------------------------------------------------------------------
//---------Panel

	/**
	*@return Return the main panel
	*/
	public JPanel getPanel(){return panelFond;}
	/**
	*@return Return the cardPanel displaying the effects according to the selected directory
	*/
	public JPanel getPanelCard(){return panelEffets;}
	/**
	*@return Return the cardPanel displaying the pro mode
	*/
	public JPanel getPanelCardPro(){return panelPro;}
	/**
	*@return Return the panel displaying the pro mode of Blur
	*/
	public JPanel getPanelFlou(){return panelFlou;}
	/**
	*
	*/
	public JPanel getPanelCrop(){return panelCrop;}
	/**
	*@return Return the panel containing the image
	*/
	public JPanel getPanelImage(){return panelImage;}
	/**
	*@return Return the CardLayout of the cardPanel of effect
	*/
	public CardLayout getCardLayout(){return cardEffect;}
	/**
	*@return Return the CardLayout of the cardPanel of pro mode
	*/
	public CardLayout getCardPro(){return cardPro;}
	/**
	*@return Return @return the main page, PhotoBoostView
	*/
	public PhotoBoostView getParent(){return choosePhoto.getParent();}

	/**
	*@return Return the bufferedImage of the displayed image
	*/	
	public BufferedImage getBufferedImage(){return imgSrc;}
	/**
	*@return Return Return the label containing the displayed image
	*/
	public JLabel getImage(){return labelImg;}

//---------TabBar
	/**
	*@return Return the label "Effect"
	*/
	public JLabel getEffect(){return effect;}
	/**
	*@return Return the label "Filter"
	*/
	public JLabel getFilter(){return filter;}
	/**
	*@return Return the label "Size"
	*/
	public JLabel getSize(){return size;}
	
	//-----Size
	/**
	*@return Return the label "Resize"
	*/
	public JLabel getResize(){return resize;}
	/**
	*@return Return the label "Crop"
	*/
	public JLabel getCrop(){return crop;}
		/**
		*
		*/
		public JSlider getSliderCrop1(){return sliderCrop1;}
		/**
		*
		*/
		public JSlider getSliderCrop2(){return sliderCrop2;}
		/**
		**
		*/
		public JSlider getSliderCrop3(){return sliderCrop3;}
		/**
		*
		*/
		public JSlider getSliderCrop4(){return sliderCrop4;}
		/**
		*
		*/
		public JButton getApplyCrop(){return applyCrop;}
	/**
	*@return Return the label "Rotate"
	*/
	public JLabel getRotate(){return rotate;}
	/**
	*@return Return the label "Left Rotate"
	*/
		public JLabel getRotateG(){return rotateG;}
	/**
	*@return Return the label "Right Rotate"
	*/
		public JLabel getRotateD(){return rotateD;}
	/**
	*@return Return the label "Mirror"
	*/
	public JLabel getMirror(){return mirror;}
	/**
	*@return Return the label "Horizontal Mirror"
	*/
		public JLabel getMirrorH(){return mirrorH;}
	/**
	*@return Return the label "Vertical Mirror"
	*/
		public JLabel getMirrorV(){return mirrorV;}

	//-----Effect
	/**
	*@return Return the label "Emboss"
	*/
	public JLabel getEmboss(){return emboss;}
	/**
	*@return Return the label "Blur"
	*/
	public JLabel getBlur(){return blur;}
	/**
	*@return Return the blur slider
	*/
		public JSlider getSliderFlou(){return sliderFlou;}
	/**
	*@return Return the blur apply button
	*/
		public JButton getApplyFlou(){return applyFlou;}
	/**
	*@return Return the label "Pixelize"
	*/
	public JLabel getPixelize(){return pixelize;}
	/**
	*@return Return the pixelize apply button
	*/
		public JButton getApplyPix(){return applyPix;}
	/**
	*@return Return the pixelize slider
	*/
		public JSlider getSliderPix(){return sliderPix;}

	//-----Filter
	/**
	*@return Return the label "Black_White"
	*/
	public JLabel getBlack(){return blackWhite;}
	/**
	*@return Return the label "Sepia"
	*/
	public JLabel getSepia(){return sepia;}
	/**
	*@return Return the label "Negative"
	*/
	public JLabel getNegative(){return negative;}


//--------Nord
	/**
	*@return Return the label "Cancel"
	*/
	public JLabel getCancel(){return cancel;}
	/**
	*@return Return the label "Valid"
	*/
	public JLabel getValid(){return valid;}
	/**
	*@return Return the label "Undo"
	*/
	public JLabel getPrevious(){return previous;}
	/**
	*@return Return the label "Redo"
	*/
	public JLabel getNext(){return next;}
	/**
	*@return Return the label "Exit"
	*/
	public JLabel getExit(){return exit;}
	/**
	*@return Return the label containing the selected image in the pro mode of crop
	*/
	public JLabel getTmp(){return labelTmp;}
	/**
	*Change the selected image on the pro mode of crop
	*@param icon Is the new image for the labelTmp
	*/
	public void setTmp(ImageIcon icon){labelTmp.setIcon(icon);}

	/**
	*@param imgTmp Is the new image to display
	*/
	public void setImage(BufferedImage imgTmp){
		imgSrc = imgTmp;
		labelImg.setIcon(resizeIcon(imgSrc, 274, 239));
	}

//-----------------------------------------------------------------------------

	/**
	*Create the interface of the EditPhotoView page.<br/>
	*It contains :
	*<ul>
	*<li> The name of the page : EditPhoto</li>
	*<li> 4 directory buttons (which contains some effects) </li>
	*<li> All the effect buttons</li>
	*<li> 5 Edition button (save, undo/redo, cancel, exit)</li>
	*<li> The chosen image</li>
	*</ul>
	*/
	private void creerInterface(){
//Panels
	//Fond
		panelFond = new JPanel();
		panelFond.setBackground(new Color(50,50,50));
		panelFond.setLayout(new BorderLayout());
	//Image
		panelImage = new JPanel();
		panelImage.setPreferredSize(new Dimension(274,239));

	//Sud (avec icones des effets)
		panelSud = new JPanel();
		panelSud.setLayout(new GridLayout(2,1));
		panelSud.setBackground(new Color(0,0,0,0));
		//Panel Fixe avec catégories d'effets
		panelDossier = new JPanel();
		panelDossier.setLayout(new GridLayout(1,3));
		panelDossier.setBackground(new Color(105,105,105));
		panelDossier.setPreferredSize(new Dimension(317, 28));
		//Panel modulable avec les effets de chaque catégorie
		panelEffets = new JPanel();
		cardEffect = new CardLayout();
		panelEffets.setLayout(cardEffect);
		panelEffets.setBackground(new Color(128,128,128));
		panelEffets.setPreferredSize(new Dimension(317, 28));
			//Panels des catégories
		panelInvisi = new JPanel();
		panelInvisi.setOpaque(false);
		panelInvi = new JPanel();
		panelInvi.setOpaque(false);

	//Dossiers
		panelEffect = new JPanel();
		panelEffect.setBackground(new Color(128,128,128));
		panelEffect.setLayout(new GridLayout(1,3));

		panelFilter = new JPanel();
		panelFilter.setBackground(new Color(128,128,128));
		panelFilter.setLayout(new GridLayout(1,3));

		panelSize = new JPanel();
		panelSize.setBackground(new Color(128,128,128));
		panelSize.setLayout(new GridLayout(1,4));
	//Nord (avec boutons)
		panelNord = new JPanel();
		panelNord.setBackground(new Color(105,105,105));
		panelNord.setPreferredSize(new Dimension(322,30));
		panelNord.setLayout(new GridLayout(1,3));

	//Panel Pro (avec slider)
		panelPro = new JPanel();
		panelPro.setPreferredSize(new Dimension(0,0));
		panelPro.setBackground(new Color(50,50,50));
		cardPro = new CardLayout();
		panelPro.setLayout(cardPro);

		//Flou
		panelFlou = new JPanel();
		panelFlou.setOpaque(false);
		sliderFlou = new JSlider(1, 3, 40, 3);
		applyFlou = new JButton("Ok");
		applyFlou.setFont(font.getFont(16));
		panelFlou.add(sliderFlou);
		panelFlou.add(applyFlou);
		//Pixelize
		panelPix = new JPanel();
		panelPix.setOpaque(false);
		sliderPix = new JSlider(1, 8, 30, 8);
		applyPix = new JButton("Ok");
		applyPix.setFont(font.getFont(16));
		panelPix.add(sliderPix);
		panelPix.add(applyPix);
		//Mirror
		panelMirror = new JPanel();
		panelMirror.setOpaque(false);
		panelMirror.setLayout(new GridLayout(4,1));
		mirrorH = new JLabel(new ImageIcon("./data/img/Elements/mirrorH.png"));
		mirrorH.setOpaque(false);
		mirrorV = new JLabel(new ImageIcon("./data/img/Elements/mirrorV.png"));
		mirrorV.setOpaque(false);
		panelMirror.add(new JLabel(""));
		panelMirror.add(mirrorH);
		panelMirror.add(mirrorV);
		panelMirror.add(new JLabel(""));
		//Rotate
		panelRotate = new JPanel();
		panelRotate.setOpaque(false);
		panelRotate.setLayout(new GridLayout(4,1));
		rotateD = new JLabel(new ImageIcon("./data/img/Elements/rotationD.png"));
		rotateD.setOpaque(false);
		rotateG = new JLabel(new ImageIcon("./data/img/Elements/rotationG.png"));
		rotateG.setOpaque(false);
		panelRotate.add(new JLabel(""));
		panelRotate.add(rotateD);
		panelRotate.add(rotateG);
		panelRotate.add(new JLabel(""));
		//Crop
		labelTmp = new JLabel();
		labelTmp.setHorizontalAlignment(SwingConstants.CENTER);
		panelCrop = new JPanel();
		panelCrop.setLayout(new BorderLayout());
		panelCrop.setOpaque(false);

		panelSlider1 = new JPanel();
		panelSlider1.setLayout(new BoxLayout(panelSlider1, BoxLayout.PAGE_AXIS));
		panelSlider2 = new JPanel();
		panelSlider2.setLayout(new BoxLayout(panelSlider2, BoxLayout.PAGE_AXIS));
		panelSlider3 = new JPanel();
		panelSlider3.setLayout(new BoxLayout(panelSlider3, BoxLayout.PAGE_AXIS));
		panelSlider4 = new JPanel();
		panelSlider4.setLayout(new BoxLayout(panelSlider4, BoxLayout.PAGE_AXIS));

		sliderCrop1 = new JSlider(0,1,10,5);
		sliderCrop1.setOpaque(false);
		sliderCrop2 = new JSlider(1,1,10,5);
		sliderCrop2.setOpaque(false);
		sliderCrop2.setInverted(true);
		sliderCrop3 = new JSlider(0,1,10,5);
		sliderCrop3.setOpaque(false);
		sliderCrop3.setInverted(true);
		sliderCrop4 = new JSlider(1,1,10,5);

		sliderCrop4.setOpaque(false);
		
		panelSlider1.add(sliderCrop1);
		panelSlider1.setOpaque(false);
		panelSlider2.add(sliderCrop2);
		panelSlider2.setOpaque(false);
		panelSlider3.add(sliderCrop3);
		panelSlider3.setOpaque(false);
		panelSlider4.add(sliderCrop4);
		panelSlider4.setOpaque(false);


		applyCrop = new JButton("OK");
		applyCrop.setFont(font.getFont(16));
		panelSlider3.add(applyCrop);

		panelCrop.add(panelSlider1,BorderLayout.NORTH);
		panelCrop.add(panelSlider2, BorderLayout.WEST);
		panelCrop.add(panelSlider3, BorderLayout.SOUTH);
		panelCrop.add(panelSlider4, BorderLayout.EAST);
		panelCrop.add(labelTmp, BorderLayout.CENTER);

	//Labels
		labelImg = new JLabel(resizeIcon(imgSrc, 317, 244));

		previous = new JLabel(new ImageIcon("./data/img/Elements/undo.png"));
		previous.setHorizontalAlignment(SwingConstants.LEFT);

		next = new JLabel(new ImageIcon("./data/img/Elements/redo.png"));
		next.setHorizontalAlignment(SwingConstants.LEFT);

		valid = new JLabel(new ImageIcon("./data/img/Elements/valid.png"));
		valid.setHorizontalAlignment(SwingConstants.LEFT);

		cancel = new JLabel(new ImageIcon("./data/img/Elements/cancel.png"));
		cancel.setHorizontalAlignment(SwingConstants.LEFT);

		exit = new JLabel(new ImageIcon("./data/img/Elements/exit.png"));
		exit.setHorizontalAlignment(SwingConstants.RIGHT);
		
		//Panel Dossier
		Color white = Color.WHITE;

		effect = new JLabel("Effect");
		effect.setFont(font.getFont(16));
		effect.setForeground(white);
		effect.setHorizontalAlignment(SwingConstants.CENTER);
		filter = new JLabel("Filter");
		filter.setFont(font.getFont(16));
		filter.setForeground(white);
		filter.setHorizontalAlignment(SwingConstants.CENTER);
		size = new JLabel("Size");
		size.setFont(font.getFont(16));
		size.setForeground(white);
		size.setHorizontalAlignment(SwingConstants.CENTER);

			//Effect
		blur = new JLabel("Blur");
		blur.setFont(font.getFont(16));
		blur.setForeground(white);
		blur.setHorizontalAlignment(SwingConstants.CENTER);
		emboss = new JLabel("Emboss");
		emboss.setFont(font.getFont(16));
		emboss.setForeground(white);
		emboss.setHorizontalAlignment(SwingConstants.CENTER);
		pixelize = new JLabel("Pixelize");
		pixelize.setFont(font.getFont(16));
		pixelize.setForeground(white);
		pixelize.setHorizontalAlignment(SwingConstants.CENTER);
			//Filter
		sepia = new JLabel("Sepia");
		sepia.setFont(font.getFont(16));
		sepia.setForeground(white);
		sepia.setHorizontalAlignment(SwingConstants.CENTER);
		negative = new JLabel("Negative");
		negative.setFont(font.getFont(16));
		negative.setForeground(white);
		negative.setHorizontalAlignment(SwingConstants.CENTER);
		blackWhite = new JLabel("Black&White");
		blackWhite.setFont(font.getFont(16));
		blackWhite.setForeground(white);
		blackWhite.setHorizontalAlignment(SwingConstants.CENTER);
			//Resize
		resize = new JLabel("Resize");
		resize.setFont(font.getFont(16));
		resize.setForeground(white);
		resize.setHorizontalAlignment(SwingConstants.CENTER);
		crop = new JLabel("Crop");
		crop.setFont(font.getFont(16));
		crop.setForeground(white);
		crop.setHorizontalAlignment(SwingConstants.CENTER);
		rotate = new JLabel("Rotate");
		rotate.setFont(font.getFont(16));
		rotate.setForeground(white);
		rotate.setHorizontalAlignment(SwingConstants.CENTER);		
		mirror = new JLabel("Mirror");
		mirror.setFont(font.getFont(16));
		mirror.setForeground(white);
		mirror.setHorizontalAlignment(SwingConstants.CENTER);
		
//Placement

		panelDossier.add(effect);
		panelDossier.add(filter);
		panelDossier.add(size);

		panelEffect.add(blur);
		panelEffect.add(emboss);
		panelEffect.add(pixelize);

		panelFilter.add(sepia);
		panelFilter.add(negative);
		panelFilter.add(blackWhite);

		panelSize.add(resize);
		panelSize.add(crop);
		panelSize.add(rotate);
		panelSize.add(mirror);

		panelEffets.add(panelInvisi, "panelInvisi");
		panelEffets.add(panelEffect, "panelEffect");
		panelEffets.add(panelFilter, "panelFilter");
		panelEffets.add(panelSize, "panelSize");

		panelPro.add(panelInvi, "panelInvi");
		panelPro.add(panelFlou, "panelFlou");
		panelPro.add(panelMirror, "panelMirror");
		panelPro.add(panelRotate, "panelRotate");
		panelPro.add(panelPix, "panelPix");
		panelPro.add(panelCrop, "panelCrop");

		panelSud.add(panelEffets);
		panelSud.add(panelDossier);

		panelNord.add(previous);
		panelNord.add(next);
		panelNord.add(valid);
		panelNord.add(cancel);
		panelNord.add(exit);

		panelImage.add(labelImg);

		panelFond.add(panelNord, BorderLayout.NORTH);
		panelFond.add(labelImg, BorderLayout.CENTER);
		panelFond.add(panelSud, BorderLayout.SOUTH);
		panelFond.add(panelPro, BorderLayout.WEST);
		getParent().getCard().add(panelFond,"PanelEdit");

	}
	/**
	*@return Return one of the four panels containing a JSlider on the pro mode of crop
	*/
	public JPanel getPanelSlider1(){
		return panelSlider1;
	}
	/**
	*@return Return one of the four panels containing a JSlider on the pro mode of crop
	*/
	public JPanel getPanelSlider2(){
		return panelSlider2;
	}
	/**
	*@return Return one of the four panels containing a JSlider on the pro mode of crop
	*/
	public JPanel getPanelSlider3(){
		return panelSlider3;
	}
	/**
	*@return Return one of the four panels containing a JSlider on the pro mode of crop
	*/
	public JPanel getPanelSlider4(){
		return panelSlider4;
	}
	/**
	*Add all the needed reaction to the clickable buttons/labels
	*/
	private void reactions(){
		EditCtrl listener = new EditCtrl(this);
		effect.addMouseListener(listener);
		filter.addMouseListener(listener);
		size.addMouseListener(listener);
			//Effect
		blur.addMouseListener(listener);
			sliderFlou.addMouseListener(listener);
			applyFlou.addMouseListener(listener);
		emboss.addMouseListener(listener);
		pixelize.addMouseListener(listener);
			sliderPix.addMouseListener(listener);
			applyPix.addMouseListener(listener);
			//Filter
		sepia.addMouseListener(listener);
		negative.addMouseListener(listener);
		blackWhite.addMouseListener(listener);
			//Size
		resize.addMouseListener(listener);
		crop.addMouseListener(listener);
			applyCrop.addMouseListener(listener);
			sliderCrop1.addChangeListener(listener);
			sliderCrop2.addChangeListener(listener);
			sliderCrop3.addChangeListener(listener);
			sliderCrop4.addChangeListener(listener);
		rotate.addMouseListener(listener);
			rotateG.addMouseListener(listener);
			rotateD.addMouseListener(listener);
		mirror.addMouseListener(listener);
			mirrorH.addMouseListener(listener);
			mirrorV.addMouseListener(listener);
			//North
		previous.addMouseListener(listener);
		next.addMouseListener(listener);
		valid.addMouseListener(listener);
		cancel.addMouseListener(listener);
		exit.addMouseListener(listener);



	}

	/**
	*Resize an image to the given dimension without alter it.<br/>
	*Just to display it on a JLabel.
	*@param imageSource Is the source image, without any modifications
	*@param l Is the width of the new Icon
	*@param h Is the length of the new Icon
	*@return Return an ImageIcon, resize to the given dimensions
	*/
	public ImageIcon resizeIcon(BufferedImage imageSource, int l, int h){
		ImageIcon icontmp = new ImageIcon(imageSource);
		double largeur = (double) imageSource.getWidth();
		double hauteur = (double) imageSource.getHeight();
		double coef1 = largeur/((double)l);
		double coef2 = hauteur/((double)h);
		if(coef1>=coef2){
			double newHaut2 = hauteur/coef1;
			int newHaut = (int) newHaut2 + 1;
			Image imgtmp = icontmp.getImage().getScaledInstance(l,newHaut,Image.SCALE_SMOOTH);
			icontmp.setImage(imgtmp);
		}
		else{
			if(coef1<coef2){
				double newLarg2 = largeur/coef2;
				int newLarg = (int) newLarg2 + 1;
				Image imgtmp = icontmp.getImage().getScaledInstance(newLarg,h,Image.SCALE_SMOOTH);
				icontmp.setImage(imgtmp);
			}
			else{
				if(coef1==coef2){
					Image imgtmp = icontmp.getImage().getScaledInstance(l,h,Image.SCALE_SMOOTH);
					icontmp.setImage(imgtmp);
				}
			}
		}
		return icontmp;
	}

}