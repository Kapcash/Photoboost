package pda.view;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import javax.imageio.*;
import javax.*;
import java.io.*;

import pda.datas.*;
import pda.control.*;

/**
*The PhotoBoostView is the first page of the Photoboost's interface<br />
*This page displays the parameters menus and the access to the galleries.
*
* @author Florent Catiau-Tristant and Mehdi Haddad
* @version 1.0
*/

public class PhotoBoostView implements java.io.Serializable{
	
//Attributs

	private static final long serialVersionUID = 0;

	/**
	*Class containing all parameters values.
	*Parameters are set on the first interface's page.
	*/
	private ParamDatas paramDatas;
	/**
	*Main panel of the application. It contains the panels of all the interface and allow to switch between them
	*/
	private JPanel panelCard;
	/**
	*
	*/
	private JPanel panelFond, panelNord, panelNord2, panelSud, sud1, sud2;
	/**
	*Label displaying the name of the page
	*/
	private JLabel labelName;
	/**
	*
	*/
	private JLabel buttonChoose, buttonGallery, logo, param;
	/**
	*The cardLayout of the panelCard, swiching between all the panels of the interface
	*/
	private CardLayout c;
	/**
	*[Parameter] The nombre of column on the ChoosePhoto gallery
	*/
	private int nbColumn;
	/**
	*[Parameter] The dimension of the displayed images, depending of the nombre of colum
	*/
	private int dimension;
	/**
	*[Parameter] The path to save new images
	*/
	private String savePath;
	/**
	*Create a new Myriad font.
	*/
	private MyriadFont font = new MyriadFont();



//Constructeur
	/**
	*The constructor initialize :
	*<ul>
	*<li> The parameters by creating a new ParamDatas and loading it</li>
	*<li> The complete interface</li>
	*<li> The reactions</li>
	*</ul>
	*/
	public PhotoBoostView(){
		paramDatas = new ParamDatas(this);
		paramDatas.loadParam();
		panelCard = new JPanel();
		this.creerInterface();
		this.reaction();
		panelFond.setVisible(true);
	}

	
//Methodes
	/**
	*@return Return the main panel, used by the PDA to display it.
	*/
	public JPanel getPanel(){
		return panelCard;
	}
	/**
	*@return Return the main panel, used by the Photoboost application.
	*/
	public JPanel getCard(){
		return panelCard;
	}
	/**
	*@return Return the label used as a button to access to the ChoosePhoto gallery
	*/
	public JLabel getChoose(){
		return buttonChoose;
	}
	/**
	*@return Return the label used as a button to access to the ViewPhoto gallery
	*/
	public JLabel getGallery(){
		return buttonGallery;
	}
	/**
	*@return Return the CardLayout used by the panelCard to display all the interface
	*/
	public CardLayout getCardLayout(){
		return c;
	}
	/**
	*@return Return the object containing the parameters
	*/
	public ParamDatas getDatas(){
		return paramDatas;
	}
	/**
	*@return Return the label used as a button to acces to the parameters part
	*/
	public JLabel getLabelParam(){
		return param;
	}
	/**
	*@return Return the nomber of column chosed on the parameters, or the defaut one.
	*/
	public int getNbColumn(){
		return nbColumn;
	}
	/**
	*@return Return the dimension of the displayed images, depending on the nombre of column
	*/
	public int getDimension(){
		return dimension;
	}
	/**
	*Change the number of column displayed on the ChoosePhoto gallery
	*@param nb Is the new nomber of column
	*/
	public void setNbColumn(int nb){
		nbColumn = nb;
	}
	/**
	*Change the dimension of the images displayed on the ChoosePhoto gallery
	*@param nb Is the new dimension of images
	*/
	public void setDimension(int nb){
		dimension = nb;
	}
	/**
	*@return Return the string containing the path of the save directory
	*/
	public String getSavePath(){
		return savePath;
	}
	/**
	*Change the path of the directory to save new images
	*@param newPath Is the new path of the directory
	*/
	public void setSavePath(String newPath){
		savePath = newPath;
	}

	/**
	*Create the interface of the home page.<br/>
	*It contains :
	*<ul>
	*<li> The name of the application : PhotoBoost</li>
	*<li> 3 buttons, to access to the parameters and the galleries</li>
	*</ul>
	*/
	private void creerInterface(){
	
	//Panels						
		panelFond = new ImagePanel(new ImageIcon("./data/img/Elements/background.png").getImage());
		//An ImagePanel is a JPanel with a background from a given ImageIcon
		panelNord = new JPanel();
		panelNord.setBackground(new Color(255,255,255,120));
		panelNord.setPreferredSize(new Dimension(322,30));
		//Those panels each contains a Button
		sud1 = new JPanel();
		sud2 = new JPanel();
		//This panel contains the two panels "sudX"
		panelSud = new JPanel();
		
	//Labels
		buttonGallery = new JLabel(new ImageIcon("./data/img/Elements/BTviewmygallery.png"));
		buttonGallery.setPreferredSize(new Dimension(230,40));

		buttonChoose = new JLabel(new ImageIcon("./data/img/Elements/BTchooseaphoto.png"));
		buttonChoose.setPreferredSize(new Dimension(230,40));

		logo = new JLabel(new ImageIcon("./data/img/Elements/polaroid.png"));
		logo.setPreferredSize(new Dimension(145,146));

		labelName = new JLabel("PhotoBoost");
		labelName.setFont(font.getFont(20));
		labelName.setHorizontalAlignment(SwingConstants.CENTER);
		labelName.setForeground(Color.BLACK);

		param = new JLabel(new ImageIcon("./data/img/Elements/BToptions.png"));
		param.setHorizontalAlignment(SwingConstants.RIGHT);
		param.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
	//Organisation
		//Is the cardLayout which will contains all the main panel of each page
		c = new CardLayout();

		panelNord.setLayout(new GridLayout(1,3));
		panelNord.add(new JLabel("")); //Fiil the first case of the layout
		panelNord.add(labelName);
		panelNord.add(param);

		panelSud.setOpaque(false);
		sud1.setOpaque(false);
		sud2.setOpaque(false);

		//Center the button on the screen
		sud1.setLayout(new BoxLayout(sud1, BoxLayout.LINE_AXIS));
		sud2.setLayout(new BoxLayout(sud2, BoxLayout.LINE_AXIS));

		sud1.add(buttonChoose);
		sud2.add(buttonGallery);

		panelSud.setLayout(new BoxLayout(panelSud, BoxLayout.PAGE_AXIS));
		panelSud.add(sud1);
		panelSud.add(sud2);

		panelFond.setLayout(new BorderLayout());
		panelFond.add(panelSud, BorderLayout.SOUTH);
		panelFond.add(panelNord, BorderLayout.NORTH);
		panelFond.add(logo,BorderLayout.CENTER);

		panelCard.setLayout(c);
		//We call the first panel "PanellHome", to easily access it
		panelCard.add(panelFond, "PanelHome");

	}

	/**
	*Add all the needed reaction to the clickable buttons/labels
	*/
	public void reaction(){
		ListenHome listener = new ListenHome(this);
		buttonChoose.addMouseListener(listener);
		buttonGallery.addMouseListener(listener);
		param.addMouseListener(listener);
	}
}