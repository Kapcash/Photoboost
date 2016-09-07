/* 
 *  PDA project -- UBS/IUT de Vannes -- Dept Informatique
 *
 *  $Id$
 *
 *  Copyright 2007-08 © IUT de Vannes
 *  Ce logiciel pédagogique est diffusé sous licence GPL
 */
package pda.control;

import pda.datas.*;
import pda.view.*;

import javax.swing.*;
import java.awt.event.*;

/**
 *  The simplest application in the PDA.
 *
 *  It can be used to construct other applications.
 *
 *  @author F. Merciol, D. Deveaux MAJ J-F. Kamp
 *                      <{francois.merciol|daniel.deveaux}@univ-ubs.fr>
 *  @version $Revision: 27 $
 */
public class HelloCtrl implements IApplication, ActionListener {

	/*
     	 * Private implementation -------------------------------------------------
     	 */
    
    	/** the name of the application */
    	private String name;
    
    	/** the view of the application */
    	private HelloView view;
    
    	/** the engine of the application */
    	private HelloDatas engine;

    	/*
     	 *  Public ressources -----------------------------------------------------
     	 *
     	 *  Constructors
     	 */

    	/**
     	 * Initialize the datas and ihm of Hello application.
     	 */
    	public HelloCtrl () {

        	engine = new HelloDatas();
        	view = new HelloView ( engine );
    	} // --------------------------------------------------------- HelloCtrl()

    	/*
     	 *  Public methods
     	 */
    
    	/*
     	 * See documentation of interface
     	 */
    	public void start ( PdaCtrl pda ) {
        	System.out.println ( "Start of Hello application" );
    	} // -------------------------------------------------------------- start()

    	/*
     	 * See documentation of interface
     	 */
    	public String getAppliName() {
		    return name;
    	} // ---------------------------------------------------------- getAppliName()

    	/*
     	 * See documentation of interface
     	 */
    	public JPanel getAppliPanel() {
		    return view.getPanel();
    	} // ---------------------------------------------------------- getAppliPanel()

    	/*
     	 * See documentation of interface
     	 */
    	public boolean close() {
		    return true;
    	} // ---------------------------------------------------------- close()

	/*
	 * See documentation of interface
	 */
	public void setAppliName ( String theName ) {
		this.name = theName;
	} // ---------------------------------------------------------- setAppliName()

    	/**
     	 * Callback method, reaction to button pushed
	 * @param e the captured event
     	 */
    	public void actionPerformed ( ActionEvent e ) {

		// Write here reactions to Events generated by Hello application

	} // ---------------------------------------------------------- actionPerformed()

} // ---------------------------------------------------------- Class HelloCtrl
