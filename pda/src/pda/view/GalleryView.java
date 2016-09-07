package pda.view;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import javax.imageio.*;
import java.awt.image.*;
import javax.*;
import java.io.*;

import pda.datas.*;
import pda.control.*;

/**
*The ApercuView is the page displaying a modified image<br />
*We chose this image on the GalleryView.
*
* @author Florent Catiau-Tristant and Mehdi Haddad
* @version 1.0
*/
public class GalleryView{

//Attributs

	/**
	*Is the calling page
	*/
	private PhotoBoostView photoboost;
	/**
	*
	*/
	private JPanel panelFond, panelCentre, panelNord, panelSud;
	/**
	*Is the panel containing all the displayed images
	*/
	private JScrollPane panelCentre2;
	/**
	*
	*/
	private JLabel labelName, retour;
	/**
	*Is the list containing all the image and their panels
	*/
	private Hashtable<BufferedImage,JPanel> list;
	/**
	*Create new Myriad Font
	*/
	private MyriadFont font = new MyriadFont();

//Contructeur
	/**
	*The constructor initialize :
	*<ul>
	*<li> The complete interface</li>
	*<li> The reactions</li>
	*</ul>
	*@param pb Is the calling page
	*/
	public GalleryView(PhotoBoostView pb){
		photoboost = pb;
		this.creerInterface();
		this.reactions();
	}

//Accesseurs
	/**
	*@return Return the calling page
	*/
	public PhotoBoostView getParent(){
		return photoboost;
	}
	/**
	*@return Return the back button
	*/
	public JLabel getRetour(){
		return retour;
	}
	/**
	*@return Return the main panel
	*/
	public JPanel getPanel(){
		return panelFond; //Est un ImagePanel, héritant de JPanel
	}
	/**
	*@param index Is the number of the wanted image
	*@return Return the panel containing the wanted image and his frame
	*/
	public JPanel getImage(int index){
		return list.get(index);
	}

	/**
	*Create the interface of the Gallery page.<br/>
	*It contains :
	*<ul>
	*<li> The name of the application : Gallery</li>
	*<li> The back button</li>
	*<li> The images of the chosed save directory</li>
	*</ul>
	*/
	private void creerInterface(){
	//Panels
		panelFond = new ImagePanel(new ImageIcon("./data/img/Elements/background.png").getImage());

		panelNord = new JPanel();
		panelNord.setBackground(new Color(255,255,255,120));
		panelNord.setPreferredSize(new Dimension(322,30));

		panelCentre = new JPanel();
		panelCentre.setOpaque(false);
		
		panelCentre2 = new JScrollPane(panelCentre);
		panelCentre2.setOpaque(false);
		panelCentre2.getViewport().setOpaque(false);
	//Labels
		labelName = new JLabel("Saved Images");
		labelName.setFont(font.getFont(16));
		labelName.setHorizontalAlignment(SwingConstants.CENTER);
		labelName.setForeground(Color.BLACK);
		retour = new JLabel(new ImageIcon("./data/img/Elements/back.png"));
		retour.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		retour.setHorizontalAlignment(SwingConstants.LEFT);

	//Organisation
		panelFond.setLayout(new BorderLayout());
		panelNord.setLayout(new GridLayout(1,3));
		panelCentre.setLayout(new GridBagLayout());

	//GridBagLayout | Placement des images
		GridBagConstraints gbc = new GridBagConstraints();
		File dir = new File(photoboost.getSavePath());
		File[] listFile = dir.listFiles(new FilterImage());
		BufferedImage img = null;
		JLabel labelTmp = null;

		list = new Hashtable<BufferedImage,JPanel>();
		
		//Initialisation of gridConstraint's attributes
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.insets = new Insets(8,8,8,8);

		//Loading of images
		for(int i=0;i<listFile.length;i++){
			try{
				img = ImageIO.read(listFile[i]);
			}
			catch(IOException e){
				System.out.println(e.getMessage());
			}
				
			gbc.gridx++; //Images are add on only a ligne
			
			Dimension dim = new Dimension(200,200);

			JPanel panelTmp = new JPanel(); //Panel with defined frame
			labelTmp = new JLabel();
			panelTmp = new ImagePanel(new ImageIcon("./data/img/Elements/bgImgGal.png").getImage()); //Cadre de l'image
			panelTmp.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 2));

			panelTmp.setPreferredSize(dim);
			labelTmp.setIcon(resizeIcon(img, 148, 114));
			labelTmp.setPreferredSize(new Dimension(150, 116));
			labelTmp.setOpaque(false);
			labelTmp.setHorizontalAlignment(SwingConstants.CENTER);
			labelTmp.setVerticalAlignment(SwingConstants.CENTER);

			panelTmp.add(labelTmp);

			list.put(img,panelTmp);
			panelCentre.add(panelTmp,gbc);
		}

	//Placement
		panelNord.add(retour);
		panelNord.add(labelName);
		panelNord.add(new JLabel(""));

		panelFond.add(panelNord, BorderLayout.NORTH);
		panelFond.add(panelCentre2, BorderLayout.CENTER);

	//Add main panel to the cardPanel to swich between all of them
		photoboost.getCard().add(panelFond,"PanelGallery");
		
	}

	/**
	*Add all the needed reaction to the clickable buttons/labels
	*/
	private void reactions(){
		GalleryCtrl ctrl = new GalleryCtrl(this);
		retour.addMouseListener(ctrl);

		//Ecouteur sur l'image réduite, mais ajout du BufferedImage sans redimensionnement
		Enumeration<BufferedImage> enum1 = list.keys();
		while(enum1.hasMoreElements()){
			BufferedImage bufTmp = enum1.nextElement();
			JPanel panTmp = list.get(bufTmp);
			panTmp.addMouseListener(new GalleryCtrl(this,panTmp,bufTmp));
		}
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
				}
			}
		}
		return icontmp;
	}
}