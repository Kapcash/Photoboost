package pda.control;

import pda.datas.*;
import pda.view.*;
import javax.swing.*;
import java.awt.event.*;

/**
* The PhotoBoostCtrl is the controler allowing to add the application to the PDA<br />
* It implements IApplication and define all of the needed methods<br/>
*
* @author Florent Catiau-Tristant and Mehdi Haddad
* @version 1.0
*/

public class PhotoBoostCtrl implements IApplication{
	
	/**
	*The name of the application
	*/
	String name = "PhotoBoost";

	/**
	*The interface of the application which contains all the containers and components
	*/
	PhotoBoostView view;

	/**
	*The constructor only initialise the application creating a new PhotoBoostView object
	*/
	public PhotoBoostCtrl(){
		view = new PhotoBoostView();
	}

	/**
	*Displays on the terminal that the application is launched
	*/
	public void start ( PdaCtrl pda ) {
       	System.out.println ( "Start of  PhotoBoost application" );
	}
    
	/**
	*@return Return the name of the application
	*@see PhotoBoostCtrl#name
	*/
    public String getAppliName() {
		return name;
    }
    	
    /**
    *@return Return the main panel of the application
    */
    public JPanel getAppliPanel() {
		return view.getPanel();
    }

    /**
    *@return Return true
    */
    public boolean close() {
		return true;
    }

	/**
	*Allow to change the application name
	*@param theName Is the new name of the application.
	*/
	public void setAppliName ( String theName ) {
		this.name = theName;
	}
}