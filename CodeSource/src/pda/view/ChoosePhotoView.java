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
*The ChoosePhotoView is the page displaying the images of the chosen directory<br/>
*It displays thoses images in 2 or 3 columns
*
* @author Florent Catiau-Tristant and Mehdi Haddad
* @version 1.0
*/

public class ChoosePhotoView{
//Attributs
	/**
	*Is the calling interface
	*/
	private PhotoBoostView photoboost;
	/**
	*
	*/
	private JPanel panelFond, panelCentre, panelNord, panelSud, panelParam;
	/**
	*Is the panel displaying all the images
	*/
	private JScrollPane panelScroll;
	/**
	*
	*/
	private JLabel labelName, retour;
	/**
	*Is a list of all the displayed images, associating their BufferedImage and their reduced label
	*/
	private Hashtable<BufferedImage,JLabel> list;
	/**
	*Is a parameter used on the interface. It's initialized thanks to the PhotoBoostView
	*/
	private int nbColumn, dimension;
	/**
	*Is the directory where the interface must load the images
	*/
	private String loadPath;
	/**
	*Create a new Myriad Font
	*/
	private MyriadFont font = new MyriadFont();

//Constructeur
	/**
	*The constructor initialize :
	*<ul>
	*<li> The parameters, loading them with the calling PhotoBoostView</li>
	*<li> The path of the directory to load the images</li>
	*<li> The complete interface</li>
	*<li> The reactions</li>
	*</ul>
	*@param pb Is the calling interface
	*@param pathName Is the chosen directory on the JFileChooser
	*/
	public ChoosePhotoView(PhotoBoostView pb, String pathName){
		photoboost = pb;
		nbColumn = photoboost.getNbColumn();
		dimension = photoboost.getDimension();
		loadPath = pathName;
		this.creerInterface();
		this.reactions();
	}

//Accesseurs
	/**
	*@return Return the calling interface
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
	*@return Return the main panel of the page
	*/
	public JPanel getPanel(){
		return panelFond; //Est un ImagePanel, héritant de JPanel
	}
	/**
	*@param index Is the number of the image we want to access
	*@return Return one of the displayed image
	*/
	public JLabel getImage(int index){
		return list.get(index);
	}
	/**
	*@return Return the number of column
	*/
	public int getNbColumn(){
		return nbColumn;
	}
	/**
	*@return Return the dimension of the displayed images
	*/
	public int getDimension(){
		return dimension;
	}
	/**
	*@return Return the path used to load the images
	*/
	public String getLoadPath(){
		return loadPath;
	}
	/**
	*@param newPath Is the new loading path
	*/
//Modificateur
	public void setLoadPath(String newPath){
		loadPath = newPath;
	}

	/**
	*Create the interface of the ChoosePhoto gallery.<br/>
	*It contains :
	*<ul>
	*<li> The name of the page : Gallery</li>
	*<li> A button back, to return to the home page</li>
	*<li> All the images contained on the chosen directory</li>
	*</ul>
	*/
	private void creerInterface(){
	//Panels
		panelFond = new ImagePanel(new ImageIcon("./data/img/Elements/background.png").getImage());
		panelFond.setLayout(new BorderLayout());
		panelNord = new JPanel();
		panelNord.setBackground(new Color(255,255,255,120));
		panelNord.setPreferredSize(new Dimension(322,30));
		panelNord.setLayout(new GridLayout(1,3));

		panelCentre = new JPanel();
		panelCentre.setOpaque(false);
		panelCentre.setLayout(new GridBagLayout());

		panelScroll = new JScrollPane(panelCentre);
		panelScroll.setOpaque(false);
        panelScroll.getViewport().setOpaque(false);

	//Labels
		labelName = new JLabel("Gallery");
		labelName.setFont(font.getFont(20));
		labelName.setHorizontalAlignment(SwingConstants.CENTER);
		labelName.setForeground(Color.BLACK);
		
		retour = new JLabel(new ImageIcon("./data/img/Elements/back.png"));
		retour.setHorizontalAlignment(SwingConstants.LEFT);
		retour.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));


	//GridBagLayout
		GridBagConstraints gbc = new GridBagConstraints(); 	//Is use to place the images
		File dir = new File(loadPath); 						//Is the chosen directory to load the contained images
		File[] listFile = dir.listFiles(new FilterImage()); //List all the images .png, .jpg, jpeg, .bmp
		BufferedImage img = null;
		JLabel labelTmp = null;
		list = new Hashtable<BufferedImage,JLabel>();
		
		//Initialize the attributes of the GridBagConstraint
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.insets = new Insets(14,14,14,14);

		//Chargement images
		for(int i=0;i<listFile.length;i++){
			try{
				img = ImageIO.read(listFile[i]);
			}
			catch(IOException e){
				System.out.println(e.getMessage());
			}
				
			gbc.gridx++;


			Dimension dim = new Dimension(0,0);
			int l=0;
			int h=0;
			JPanel panelTmp = new JPanel();
			labelTmp = new JLabel();
			/*The dimension depend on the number of column
			The only possible values are 2 and 3
			The panelTmp is used to draw the images's frames
			*/
			if(nbColumn==2){
				panelTmp = new ImagePanel(new ImageIcon("./data/img/Elements/bg2.png").getImage());
				dim = new Dimension(95, 76);
				labelTmp.setBorder(BorderFactory.createEmptyBorder(-11, 0, 0, 0));
				l = 76;
				h = 55;
			}
			else if(nbColumn==3){
				panelTmp = new ImagePanel(new ImageIcon("./data/img/Elements/bg3.png").getImage());
				dim = new Dimension(70,56);
				labelTmp.setBorder(BorderFactory.createEmptyBorder(-10, 0, 0, 0));
				l=56;
				h=40;
			}
			panelTmp.setPreferredSize(dim);

			labelTmp.setIcon(resizeIcon(img, l, h));
			labelTmp.setPreferredSize(dim);
			labelTmp.setOpaque(false);
			labelTmp.setHorizontalAlignment(SwingConstants.CENTER);
			labelTmp.setVerticalAlignment(SwingConstants.CENTER);
			//Add the image to is frame
			panelTmp.add(labelTmp);
			//Add the image and it bufferedImage to the Hashtable
			list.put(img,labelTmp);
			panelCentre.add(panelTmp,gbc);
			if(gbc.gridx >= nbColumn){	//When an image is display on the last colum,
				gbc.gridx = 0;			//it goes to the next line
				gbc.gridy++;
			}			
		}

		panelNord.add(retour);
		panelNord.add(labelName);
		panelNord.add(new JLabel(""));

		panelFond.add(panelNord, BorderLayout.NORTH);
		panelFond.add(panelScroll, BorderLayout.CENTER);
		//Add the main panel to the cardPanel on PhotoBoostView, to swich between all of them
		photoboost.getCard().add(panelFond,"PanelChoose");
		
	}

	/**
	*Add all the needed reaction to the clickable buttons/labels
	*/
	private void reactions(){
		ChooseCtrl ctrl = new ChooseCtrl(this);
		retour.addMouseListener(ctrl);

		//Ecouteur sur l'image réduite, mais ajout du BufferedImage sans redimensionnement
		Enumeration<BufferedImage> enum1 = list.keys();
		while(enum1.hasMoreElements()){
			BufferedImage bufTmp = enum1.nextElement();
			JLabel labTmp = list.get(bufTmp);
			labTmp.addMouseListener(new ChooseCtrl(this,labTmp,bufTmp));
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
