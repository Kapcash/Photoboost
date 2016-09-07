package pda.view;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.image.*;

import pda.datas.*;
import pda.control.*;

/**
*The ApercuView is the page displaying a modified image<br />
*We chose this image on the GalleryView.
*
* @author Florent Catiau-Tristant and Mehdi Haddad
* @version 1.0
*/
public class ApercuView{
//Attributs

	/**
	*
	*/
	private JPanel panelFond, panelSud, panelNord, panelImage;
	/**
	*
	*/
	private JLabel labelImg, retour, labelName;
	/**
	*Is the BufferedImage of the displayed image
	*/
	private BufferedImage imgSrc;
	/**
	*Is the calling page
	*/
	private GalleryView gallery;
	/**
	*Create new Myriad Font
	*/
	private MyriadFont font = new MyriadFont();

//Contructeur
	/**
	*The constructor initialize :
	*<ul>
	*<li> The attribute of the calling page</li>
	*<li> The displayed image</li>
	*<li> The complete interface</li>
	*<li> The reactions</li>
	*@param gal Is the calling page
	*@param img Is the bufferedImage of the selected image
	*</ul>
	*/
	public ApercuView(GalleryView gal, BufferedImage img){
		gallery = gal;
		imgSrc = img;
		this.creerInterface();
		this.reactions();
	}

//Accesseurs

	/**
	*@return Return the main panel of the page
	*/
	public JPanel getPanel(){
		return panelFond;
	}
	/**
	*@return Return the calling page
	*/
	public GalleryView getParent(){
		return gallery;
	}
	/**
	*@return Return the label containing the image displayed
	*/
	public JLabel getImage(){
		return labelImg;
	}
	/**
	*@return Return the BufferedImage of the displayed image
	*/
	public BufferedImage getBufferedImage(){
		return imgSrc;
	}
	/**
	*@return Return the back button
	*/
	public JLabel getRetour(){
		return retour;
	}

	/**
	*Create the interface of the survey page.<br/>
	*It contains :
	*<ul>
	*<li> The name of the page : Survey</li>
	*<li> The back button</li>
	*<li> The selected image</li>
	*</ul>
	*/
	private void creerInterface(){
	//Panels
		panelFond = new JPanel();
		panelFond.setBackground(Color.BLACK);

		panelNord = new JPanel();
		panelNord.setBackground(new Color(255,255,255,120));
		panelNord.setPreferredSize(new Dimension(322,30));
		
		panelImage = new JPanel();
	//Labels
		retour = new JLabel(new ImageIcon("./data/img/Elements/back.png"));
		retour.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		retour.setHorizontalAlignment(SwingConstants.LEFT);

		labelName = new JLabel("Survey");
		labelName.setFont(font.getFont(20));
		labelName.setForeground(Color.BLACK);
		labelName.setHorizontalAlignment(SwingConstants.CENTER);

		labelImg = new JLabel(resizeIcon(imgSrc,317,294));
		
	//Organisation
		panelFond.setLayout(new BorderLayout());
		panelNord.setLayout(new GridLayout(1,3));

	//Placement
		panelImage.add(labelImg);

		panelNord.add(retour);
		panelNord.add(labelName);
		panelNord.add(new JLabel(""));

		panelFond.add(panelNord, BorderLayout.NORTH);
		panelFond.add(labelImg, BorderLayout.CENTER);
	//Add the main panel to the cardPanel to swich between all of them
		gallery.getParent().getCard().add(panelFond,"PanelApercu");
	}

	/**
	*Add all the needed reaction to the clickable buttons/labels
	*/
	private void reactions(){
		ApercuCtrl listener = new ApercuCtrl(this);
		retour.addMouseListener(listener);
	}

	/**
	*Resize an image to the given dimension without alter it.<br/>
	*Just to display it on a JLabel.
	*@param imageSource Is the source image, without any modifications
	*@param l Is the width of the new Icon
	*@param h Is the length of the new Icon
	*@return Return an ImageIcon, resize to the given dimensions
	*/
	private ImageIcon resizeIcon(BufferedImage imageSource, int l, int h){
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
					System.out.println("3");
				}
			}
		}
		return icontmp;
	}

}