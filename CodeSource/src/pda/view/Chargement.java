package pda.view;

import javax.swing.*;
import java.awt.*;
import pda.datas.*;

/**
*Chargement is the page displaying there is a current loading
*
* @author Florent Catiau-Tristant and Mehdi Haddad
* @version 1.0
*/
public class Chargement{

//Attributs
	/**
	*Is the home page
	*/
	private PhotoBoostView photoboost;
	/**
	*
	*/
	private JPanel panelFond, panelCentre, panelNord;
	/**
	*The label displaying "Loading..."
	*/
	private JLabel load;
	/**
	*The font of the text
	*/
	private MyriadFont font = new MyriadFont();

//Constructeur
	/**
	*The constructor initialize the interface
	*@param pb Is the calling page
	*/
	public Chargement(PhotoBoostView pb){
		photoboost = pb;
		this.creerInterface();
	}

	/**
	*@return Return the main panel
	*/
	public JPanel getPanel(){
		return panelFond;
	}
	/**
	*@return Return the loading label
	*/
	public JLabel getLabelLoad(){
		return load;
	}

	/**
	*Create the interface of the home page.<br/>
	*It contains :
	*<ul>
	*<li> The text of the page : "Loading..."</li>
	*<li> The opacity of the page</li>
	*</ul>
	*/
	public void creerInterface(){
		panelFond = new ImagePanel(new ImageIcon("./data/img/Elements/Loading.png").getImage());
		panelFond.setLayout(new BorderLayout());
		panelCentre = new JPanel();
		panelCentre.setBackground(new Color(255,255,255,220));
		panelCentre.setLayout(new BorderLayout());
		panelNord = new JPanel();
		panelNord.setBackground(new Color(255,255,255,220));
		panelNord.add(new JLabel(""));

		load = new JLabel("Loading...");
		load.setFont(font.getFont(16));
		load.setHorizontalAlignment(SwingConstants.CENTER);
		panelCentre.add(load, BorderLayout.CENTER);

		panelFond.add(panelNord, BorderLayout.NORTH);
		panelFond.add(panelCentre, BorderLayout.CENTER);

		photoboost.getCard().add(panelFond, "PanelChargement");
	}


}