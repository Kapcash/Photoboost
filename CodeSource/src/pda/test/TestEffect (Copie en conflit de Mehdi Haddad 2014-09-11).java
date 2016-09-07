package pda.test;


import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.*;
import java.util.*;

public class TestEffect{

	private BufferedImage source, bw, miroirH, miroirV, neg, rDroite, rGauche, sepia;
	private Effect effect;

	public static void main(String[] args) {
		System.out.println("-------DEBUT DES TESTS------");
		TestEffect test = new TestEffect();

		//Test de ajouter()
		System.out.println("///////  Test de ajouter() ///////");
		test.testAjouter();
		System.out.println("/////// Fin du test de ajouter() /////// \n");

		//Test de getBI()
		System.out.println("///////  Test de getBI() ///////");
		test.testGetBI();
		System.out.println("/////// Fin du test de getBI() /////// \n");

		//Test de undo()
		System.out.println("///////  Test de undo() ///////");
		test.testUndo();
		System.out.println("/////// Fin du test de undo() /////// \n");

		//Test de redo()
		System.out.println("///////  Test de redo() ///////");
		test.testRedo();
		System.out.println("/////// Fin du test de redo() /////// \n");

		//Test de cancel()
		System.out.println("///////  Test de cancel() ///////");
		test.testCancel();
		System.out.println("/////// Fin du test de cancel() /////// \n");

		//Test de getCpt()
		System.out.println("///////  Test de getCpt() ///////");
		test.testGetCpt();
		System.out.println("/////// Fin du test de getCpt() /////// \n");

		System.out.println("-------FIN DES TESTS------");
	}
	
	public TestEffect(){
		source = this.convert(new ImageIcon("./data/img/Test/a.png"));
		bw = this.convert(new ImageIcon("./data/img/Test/bw.png"));
		neg = this.convert(new ImageIcon("./data/img/Test/negative.png"));
	}

	private void testAjouter(){
		effect = new Effect();
		//Test avec une image null
		System.out.println("-Test avec une image null");
		try{
			effect.ajouter(null);
			System.out.println("ECHEC");
		}
		catch(NullPointerException e){
			System.out.println("OK");
		}

		//Test avec une image valide
		System.out.println("-Test avec une image valide");
		try{
			effect.ajouter(source);
			if(effect.getTemp().size()==1) System.out.println("OK");
			else System.out.println("ERROR");
			
		}
		catch(NullPointerException e){
			System.out.println("ECHEC");
		}

	}

	private void testGetBI(){
		effect = new Effect();

		//Test quand la clé est invalide
		System.out.println("-Test quand le clé est invalide");
		try{
			effect.getBI(1);
			System.out.println("ERROR");
		}
		catch(IndexOutOfBoundsException e){
			System.out.println("OK");
		}

		//Test quand la clé est valide
		System.out.println("-Test quand le clé est valide");
		try{
			effect.ajouter(source);
			BufferedImage test = effect.getBI(1);
			if(equals(test, source)) System.out.println("OK");
			else System.out.println("ECHEC");
		}
		catch(IndexOutOfBoundsException e){
			System.out.println("ECHEC");
		}
		catch(NullPointerException e){
			System.out.println("ECHEC");
		}

		
	}

	private void testUndo(){
		effect = new Effect();
		System.out.println("-Test de undo() quand nous sommes sur la première image");
		try{
			effect.ajouter(source);
			if(equals(effect.getBI(effect.getCpt()), source)){
				effect.undo();
				if(equals(effect.getBI(effect.getCpt()), source)) System.out.println("OK");
				else System.out.println("ECHEC");
			}
			else System.out.println("ECHEC");
			
		}
		catch(NullPointerException e){
			System.out.println("ECHEC");
		}

		effect = new Effect();
		System.out.println("-Test de undo() quand nous ne sommes pas sur la première image");
		try{
			effect.ajouter(source);
			effect.ajouter(bw);
			if(equals(effect.getBI(effect.getCpt()), bw)){
				effect.undo();
				if(equals(effect.getBI(effect.getCpt()), source)) System.out.println("OK");
				else System.out.println("ECHEC");
			}
			else System.out.println("ECHEC");
			
		}
		catch(NullPointerException e){
			System.out.println("ECHEC");
		}
	}
	private void testRedo(){
		effect = new Effect();

		System.out.println("-Test de redo() quand nous sommes sur la dernière image");
		try{
			effect.ajouter(source);
			effect.ajouter(bw);
			if(equals(effect.getBI(effect.getCpt()), bw)){
				effect.redo();
				if(equals(effect.getBI(effect.getCpt()), bw)) System.out.println("OK");
				else System.out.println("ECHEC");
			}
			else System.out.println("ECHEC");
			
		}
		catch(NullPointerException e){
			System.out.println("ECHEC");
		}

		effect = new Effect();
		System.out.println("-Test de redo() quand nous ne sommes pas sur la dernière image");
		try{
			effect.ajouter(source);
			effect.ajouter(bw);
			if(equals(effect.getBI(effect.getCpt()), bw)){
				effect.undo();
				if(equals(effect.getBI(effect.getCpt()), source)){
					effect.redo();
					if(equals(effect.getBI(effect.getCpt()), bw)) System.out.println("OK");
					else System.out.println("ECHEC");
				}
				else System.out.println("ECHEC");
			}
			else System.out.println("ECHEC");
			
		}
		catch(NullPointerException e){
			System.out.println("ECHEC");
		}
	}
	private void testCancel(){
		effect = new Effect();
		try{
			effect.ajouter(source);
			effect.ajouter(bw);
			effect.ajouter(neg);
			if(effect.getTemp().size()==3){
				effect.cancel();
				if(effect.getTemp().size()==1) System.out.println("OK");
				else System.out.println("ECHEC");
			}
			else System.out.println("ECHEC");
		}
		catch(NullPointerException e){
			System.out.println("ECHEC");
		}
	}

	private void testGetCpt(){
		effect = new Effect();
		System.out.println("-Test de getCpt() après ajouter()");
		try{
			effect.ajouter(source);
			if(effect.getCpt()==1) System.out.println("OK");
			else System.out.println("ECHEC");

		}
		catch(NullPointerException e){
			System.out.println("ECHEC");
		}

		System.out.println("-Test de getCpt() après undo()");
		try{
			effect.ajouter(bw);
			if(effect.getCpt()==2){
				effect.undo();
				if(effect.getCpt()==1) System.out.println("OK");
				else System.out.println("ECHEC");
			}
			else System.out.println("ECHEC");
		}
		catch(NullPointerException e){
			System.out.println("ECHEC");
		}

		System.out.println("-Test de getCpt() après redo()");
		try{
			effect.redo();
			if(effect.getCpt()==2) System.out.println("OK");
			else System.out.println("ECHEC");
		}
		catch(NullPointerException e){
			System.out.println("ECHEC");
		}

		System.out.println("-Test de getCpt() après cancel()");
		try{
			effect.cancel();
			if(effect.getCpt()==1) System.out.println("OK");
			else System.out.println("ECHEC");
		}
		catch(NullPointerException e){
			System.out.println("ECHEC");
		}
	}

	private boolean equals(BufferedImage img1, BufferedImage img2){
		boolean ret = true;

		if(img1.getWidth()==img2.getWidth() && img1.getHeight() == img2.getHeight()){
			for (int x = 0; x < img1.getWidth(); x++) {
				for (int y = 0; y < img1.getHeight(); y++) {
					if(img1.getRGB(x, y)!=img2.getRGB(x,y)){
						ret = false;
						x = img1.getWidth();		//Pour sortir de la boucle directement
						y = img1.getHeight();		//Pour sortir de la boucle directement
					}			
				}
			}
		}
		else{ret=false;}
		return ret;	
	}

	private static BufferedImage convert(ImageIcon icon){
		BufferedImage bi = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics g = bi.createGraphics();
		icon.paintIcon(null, g, 0,0);
		g.dispose();
		return bi;
	}
}