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
		new TestEffect().init();

		//Test de ajouter()
		System.out.println("///////  Test de ajouter() ///////");
		new TestEffect().testAjouter();
		System.out.println("/////// Fin du test de ajouter() /////// \n");

		//Test de undo()
		System.out.println("///////  Test de undo() ///////");
		new TestEffect().testUndo();
		System.out.println("/////// Fin du test de undo() /////// \n");

		//Test de redo()
		System.out.println("///////  Test de redo() ///////");
		new TestEffect().testRedo();
		System.out.println("/////// Fin du test de redo() /////// \n");

		//Test de cancel()
		System.out.println("///////  Test de cancel() ///////");
		new TestEffect().testCancel();
		System.out.println("/////// Fin du test de cancel() /////// \n");

		//Test de getBI()
		System.out.println("///////  Test de getBI() ///////");
		new TestEffect().testGetBI();
		System.out.println("/////// Fin du test de getBI() /////// \n");

		//Test de getCpt()
		System.out.println("///////  Test de getCpt() ///////");
		new TestEffect().testGetCpt();
		System.out.println("/////// Fin du test de getCpt() /////// \n");

		System.out.println("-------FIN DES TESTS------");
	}
	private void testAjouter(){
		System.out.println("Test avec une image null");
	}
	private void testUndo(){}
	private void testRedo(){}
	private void testCancel(){}
	private void testGetBI(){}
	private void testGetCpt(){}


	private void init(){
		source = this.convert(new ImageIcon("./data/img/Test/a.png"));
		bw = this.convert(new ImageIcon("./data/img/Test/bw.png"));
		miroirH = this.convert(new ImageIcon("./data/img/Test/horizontal.png"));
		miroirV = this.convert(new ImageIcon("./data/img/Test/vertical.png"));
		neg = this.convert(new ImageIcon("./data/img/Test/negative.png"));
		rDroite = this.convert(new ImageIcon("./data/img/Test/rDroite.png"));
		rGauche = this.convert(new ImageIcon("./data/img/Test/rGauche.png"));
		sepia = this.convert(new ImageIcon("./data/img/Test/sepia.png"));
		effect = new Effect();
				//System.out.println(equals(a, b));
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