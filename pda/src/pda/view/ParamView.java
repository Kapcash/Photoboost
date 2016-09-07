package pda.view;

import javax.swing.*;
import java.awt.*;

import pda.datas.*;
import pda.control.*;

/**
*The ParamView is the parameters pages. Access from the PhotoBoostView page.<br/>
*It diplays all the parameters changeable on the application
*
* @author Florent Catiau-Tristant and Mehdi Haddad
* @version 1.0
*/
public class ParamView{

	/**
	*Is the calling page
	*/
	private PhotoBoostView photoboost;
	/**
	*Is the object which contains all the methods to save/load and change parameters
	*/
	private ParamDatas paramDatas;
	/**
	*
	*/
	private JPanel panelFond, panelNord, panelCentre, panelSud;
	/**
	*
	*/
	private JLabel labelName, retour, labelCol, labelSave;
	/**
	*
	*/
	private JButton apply, buttonPath;
	/**
	*
	*/
	private JRadioButton col2, col3;
	/**
	*Display the default save path, or the selected one. Uneditable.
	*/
	private JTextField fieldPath;

	/**
	*Create a new Myriad font.
	*/
	private MyriadFont font = new MyriadFont();

//Constructeur
	

	/**
	*The constructor initialize :
	*<ul>
	*<li> The parameters by getting the ParamDatas</li>
	*<li> The complete interface</li>
	*<li> The reactions</li>
	*</ul>
	*@param pb Is the calling page
	*/
	public ParamView(PhotoBoostView pb){
		photoboost = pb;
		paramDatas = pb.getDatas();
		this.creerInterface();
		this.reactions();
	}

//Accesseurs

	/**
	*@return Return the apply button
	*/
	public JButton getApply(){
		return apply;
	}
	/**
	*@return Return true if the first radioButton (for 2 column) is selected. False else.
	*/
	public boolean radio1Checked(){
		return col2.isSelected();
	}
	/**
	*@return Return the first radioButton (2)
	*/
	public JRadioButton getRadio1(){
		return col2;
	}
	/**
	*@return Return the second radioButton (3)
	*/
	public JRadioButton getRadio2(){
		return col3;
	}
	/**
	*@return Return the back button
	*/
	public JLabel getRetour(){
		return retour;
	}
	/**
	*@return Return the button to access to JFileChooser and choose a new save path
	*/
	public JButton getButtonPath(){
		return buttonPath;
	}
	/**
	*@return Return the calling page, which is a PhotoBoostView
	*/
	public PhotoBoostView getParent(){
		return photoboost;
	}
	/**
	*@return Return the field displaying the save path
	*/
	public JTextField getFieldPath(){
		return fieldPath;
	}
	/**
	*Change the save path
	*@param newPath Is the new Path to save images
	*/
	public void setFieldPath(String newPath){
		fieldPath.setText(newPath);
	}


	/**
	*Create the interface of the parameters page <br/>
	*It contains :
	*<ul>
	*<li> The name of the page : Parameters</li>
	*<li> 2 JRadioButton, to change between 2 or 3 displayed column</li>
	*<li> The save path, and a JFileChooser to change it</li>
	*<li> An apply button to save parameters</li>
	*</ul>
	*/
	private void creerInterface(){
	//Panels
		panelFond = new ImagePanel(new ImageIcon("./data/img/Elements/Param.png").getImage());

		panelNord = new JPanel();
		panelNord.setBackground(new Color(255,255,255,200));
		panelNord.setPreferredSize(new Dimension(322,30));

		panelCentre = new JPanel();
		panelCentre.setBackground(new Color(255,255,255,200));

		panelSud = new JPanel();
		panelSud.setBackground(new Color(255,255,255,200));
	//Labels
		labelName = new JLabel("Settings");
		labelName.setFont(font.getFont(20));
		labelName.setHorizontalAlignment(SwingConstants.CENTER);

		retour = new JLabel(new ImageIcon("./data/img/Elements/back.png"));
		retour.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		retour.setHorizontalAlignment(SwingConstants.LEFT);

		labelCol = new JLabel("Number of columns :");
		labelCol.setFont(font.getFont(16));
		labelCol.setHorizontalAlignment(SwingConstants.LEFT);
		Dimension labelColDim = new Dimension(170,20);
		labelCol.setPreferredSize(labelColDim);
	//JRadioButton
		col2 = new JRadioButton("2");
		col2.setFont(font.getFont(16));
		col2.setOpaque(false);
		Dimension col2Dim = new Dimension(40,20);
		col2.setPreferredSize(col2Dim);
		
		col3 = new JRadioButton("3");
		col3.setFont(font.getFont(16));
		col3.setOpaque(false);
		Dimension col3Dim = new Dimension(40,20);
		col3.setPreferredSize(col3Dim);

	//Organisation
		panelFond.setLayout(new BorderLayout());
		panelNord.setLayout(new GridLayout(1,3));
		panelSud.setLayout(new GridLayout(1,3));
		panelCentre.setLayout(null); //We place components by a fixed position

		//We join the JRadioButton in a unique group, to make impossible to selected them in the same time
		ButtonGroup grp = new ButtonGroup();
		grp.add(col2);
		grp.add(col3);

		apply = new JButton("Apply");
		apply.setFont(font.getFont(16));
		apply.setPreferredSize(new Dimension(70,20));
		apply.setHorizontalAlignment(SwingConstants.CENTER);
		apply.setOpaque(false);

		labelSave = new JLabel("Backup directory :");
		labelSave.setFont(font.getFont(16));
		labelSave.setHorizontalAlignment(SwingConstants.LEFT);
		Dimension labelSaveDim = new Dimension(200,20);
		labelSave.setPreferredSize(labelSaveDim);
		labelSave.setOpaque(false);

		buttonPath = new JButton("...");
		buttonPath.setFont(font.getFont(16));
		Dimension buttonPathDim = new Dimension(25,20);
		buttonPath.setPreferredSize(buttonPathDim);
		buttonPath.setOpaque(false);

		fieldPath = new JTextField(paramDatas.getSavePath());
		Dimension fieldPathDim = new Dimension(260,20);
		fieldPath.setEditable(false);
		fieldPath.setPreferredSize(fieldPathDim);
		
	//Placement
		panelNord.add(retour);
		panelNord.add(labelName);
		panelNord.add(new JLabel(""));

		labelCol.setBounds(10, 1,labelColDim.width, labelColDim.height);
		col2.setBounds(180, 1,col2Dim.width, col2Dim.height);
		col3.setBounds(220, 1,col3Dim.width, col3Dim.height);

		labelSave.setBounds(10, 35, labelSaveDim.width, labelSaveDim.height);
		fieldPath.setBounds(10, 60, fieldPathDim.width, fieldPathDim.height);
		buttonPath.setBounds(280,60, buttonPathDim.width, buttonPathDim.height);

		panelCentre.add(labelCol);
		panelCentre.add(col2);
		panelCentre.add(col3);

		panelCentre.add(labelSave);
		panelCentre.add(fieldPath);
		panelCentre.add(buttonPath);

		panelSud.add(new JLabel(""));
		panelSud.add(apply);
		panelSud.add(new JLabel(""));

		panelFond.add(panelNord, BorderLayout.NORTH);
		panelFond.add(panelCentre, BorderLayout.CENTER);
		panelFond.add(panelSud,BorderLayout.SOUTH);

		//Add the main panel to the cardPanel, to swich between all of them
		photoboost.getCard().add(panelFond,"PanelParam");
	//To display the parameters as they were saved
		this.reloadParam();
	}

	/**
	*Display the parameters as they were saved
	*/
	public void reloadParam(){
		if(paramDatas.getNbColumn() == 2){
			col2.setSelected(true);
			col3.setSelected(false);
		}
		else{
			col3.setSelected(true);
			col2.setSelected(false);
		}
	}

	/**
	*Add all the needed reaction to the clickable buttons/labels
	*/
	private void reactions(){
		ParamCtrl listener = new ParamCtrl(this,paramDatas);
		retour.addMouseListener(listener);
		apply.addActionListener(listener);
		buttonPath.addActionListener(listener);
	}

}
