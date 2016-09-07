package pda.datas;

import pda.control.*;
import pda.view.*;

import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.*;
import java.util.*;

/**
*Effect is a class containing all the effect method for Photoboost application<br/>
*It also contains the undo/redo feature
* @author Florent Catiau-Tristant and Mehdi Haddad
* @version 1.0
*/
public class Effect{

	/**
	*Is the control of the interface using effect methods
	*/
	EditCtrl editControl;
	/**
	*Is the list containing all the modified images, before save it, to use the undo/redo feature
	*/
	ArrayList<BufferedImage> temp = new ArrayList<BufferedImage>();
	/**
	*The counter of the list, where the current displayed image is in the list
	*/
	int cpt = 0;
	/**
	*Is true if an image is not the last modified 
	*/
	boolean isPrevious=false;

//Constructeur
	/**
	*The constructor initialize :
	*<ul>
	*<li> The editControl attribute</li>
	*<li> The list of undo/redo, by adding the first and original image</li>
	*</ul>
	*@param editC Is the interface using this class
	*/
	public Effect(EditCtrl editC){
		editControl = editC;
		ajouter(editControl.getImgSrc());
	}

	/**
	*Add a black and white filter on an image
	*@param img Is the image which will be modified by the effect
	*/
	public BufferedImage blackwhite(BufferedImage img){
		BufferedImage bi = new BufferedImage(img.getWidth(),img.getHeight(),BufferedImage.TYPE_BYTE_GRAY);
		bi.getGraphics().drawImage(img,0,0,null);
		
		return bi;
	}

	/**
	*Make a blur effect on an image
	*@param val Is the intensity of the blur effect, 0 is no effect.
	*@param img Is the image which will be modified by the effect
	*/
	public BufferedImage flou(int val, BufferedImage img) {
		BufferedImage imgSource = img;
		BufferedImage bi = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());

		int val2 = val*val;
		float data[] = new float[val2];
		float floatVal = 1.0f/(float)val2;

		for(int i=0;i<val2;i++){
			data[i] = floatVal;
		}

		Kernel kernel = new Kernel(val, val, data);
		ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
		bi = op.filter(imgSource, null);

		
		return bi;
	}

	/**
	*Make a emboss effect on an image
	*@param img Is the image which will be modified by the effect
	*/
	public BufferedImage repoussage(BufferedImage img) {

		BufferedImage bi = new BufferedImage(img.getWidth(), img.getHeight(), img.getType()); 
		float data[] = {-2f, -1f, 0f, -1f, 1f, 1f, 0f,1f, 2f};
		Kernel kernel = new Kernel(3, 3, data);
		ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
		bi = op.filter(img, null);
		
		return bi;
	}

	/**
	*Make a pixelisation effect on an image
	*@param size Is the intensity of the pixelisation effect
	*@param img Is the image which will be modified by the effect
	*/
	public BufferedImage pixellisation(int size, BufferedImage img){
		BufferedImage bi = new BufferedImage(img.getWidth(), img.getHeight(), img.getType()); 
		for (int x = 0; x < img.getWidth(); x+=size) {
			for (int y = 0; y < img.getHeight(); y+=size) {
 
				int px = 0;
 
				for (int xi = 0; xi < size; xi++) {
					for (int yi = 0; yi < size; yi++) {
						px += img.getRGB(x, y);
						px = px / 2;
					}
				}
 
				for (int xi = 0; xi < size; xi++) {
					for (int yi = 0; yi < size; yi++) {
						if(x+xi<img.getWidth() && y+yi < img.getHeight()){
							bi.setRGB(x+xi, y+yi, px);
						}
						
					}
				}
			}
		}
		
		return bi;
	}

	/**
	*Add a sepia filter on an image
	*@param img Is the image which will be modified by the effect
	*/
	public BufferedImage sepia(BufferedImage img){
		int sepiaIntensity = 20;
		BufferedImage bi = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
		
		int profondeurSepia = 30;

		int w = img.getWidth();
		int h = img.getHeight();

		for (int x = 0; x < img.getWidth(); x++) {
			for (int y = 0; y < img.getHeight(); y++) {

				int rgb = img.getRGB(x, y);
				Color color = new Color(rgb, true);
				int r = color.getRed();
				int g = color.getGreen();
				int b = color.getBlue();
				int gry = (r + g + b) / 3;

				b=gry;
				g=gry;
				r=gry;

				r = r + (profondeurSepia * 2);
				g = g + profondeurSepia;

				if(r > 255){
					r= 255;
				}
				if(g > 255){
					g = 255;
				}
				if(b > 255){
					b = 255;
				}

				// Darken blue color to increase sepia effect
				b = b - sepiaIntensity;

				// normalize if out of bounds
				if(b < 0){
					b = 0;
				}
				if(b > 255){
					b = 255;
				}

				color = new Color(r, g, b, color.getAlpha());
				bi.setRGB(x, y, color.getRGB());

			}
		}
		return bi;
	}

	/**
	*
	*@param img Is the image which will be modified by the effect
	*/
	public BufferedImage contour(BufferedImage img){
		int width = img.getWidth();
		int height = img.getHeight();
		BufferedImage bi = new BufferedImage(width, height, img.getType()); 

		return bi;		
	}

	/**
	*Add a negative filter on an image
	*@param img Is the image which will be modified by the effect
	*/
	public BufferedImage negatif(BufferedImage img){
		BufferedImage bi = new BufferedImage(img.getWidth(), img.getHeight(), img.getType()); 
		for (int x = 0; x < img.getWidth(); x++) {
			for (int y = 0; y < img.getHeight(); y++) {
				int px = 255-img.getRGB(x, y);
				bi.setRGB(x, y, px);			
			}
		}
		return bi;	
	}

	/**
	*Rotates an image 90 degrees clockwise
	*@param img Is the image which will be modified by the effect
	*/
	public BufferedImage rotationDroite(BufferedImage img){
		BufferedImage bi = new BufferedImage(img.getHeight(), img.getWidth(), img.getType()); 

		for (int x = 0; x < img.getWidth(); x++) {
			for (int y = 0; y < img.getHeight(); y++) {
				int px = img.getRGB(x, y);
				int destX = img.getHeight() - y - 1;
				int destY = x;
                bi.setRGB(destX, destY, px);			
			}
		}
		return bi;		
	}

	/**
	*Rotates an image 90 degrees inverse clockwise
	*@param img Is the image which will be modified by the effect
	*/
	public BufferedImage rotationGauche(BufferedImage img){
		BufferedImage bi = new BufferedImage(img.getHeight(), img.getWidth(), img.getType()); 

		for (int x = 0; x < img.getWidth(); x++) {
			for (int y = 0; y < img.getHeight(); y++) {
				int px = img.getRGB(x, y);
				int destX = y;
				int destY = img.getWidth() - x - 1;
                bi.setRGB(destX, destY, px);			
			}
		}
		return bi;		
	}

	/**
	*Rotates an image 180 degrees from a vertical axis
	*@param img Is the image which will be modified by the effect
	*/
	public BufferedImage mirroirHorizontal(BufferedImage img){
		BufferedImage bi = new BufferedImage(img.getWidth(), img.getHeight(), img.getType()); 

		for (int x = 0; x < img.getWidth(); x++) {
			for (int y = 0; y < img.getHeight(); y++) {
				int px = img.getRGB(x, y);
				int destX = img.getWidth() - x - 1;
				int destY = y;
                bi.setRGB(destX, destY, px);			
			}
		}
		return bi;
	}

	/**
	*Rotates an image 180 degrees from an horizontal axis
	*@param img Is the image which will be modified by the effect
	*/
	public BufferedImage mirroirVertical(BufferedImage img){
		BufferedImage bi = new BufferedImage(img.getWidth(), img.getHeight(), img.getType()); 

		for (int x = 0; x < img.getWidth(); x++) {
			for (int y = 0; y < img.getHeight(); y++) {
				int px = img.getRGB(x, y);
				int destX = x;
				int destY = img.getHeight() - y - 1;
                bi.setRGB(destX, destY, px);			
			}
		}
		return bi;
	}

	/**
	*Cut an image from the bounds with the given size
	*@param xDebut The vertical left index of the new size of the image
	*@param xFin The vertical right index of the new size of the image
	*@param yDebut The horizontal top index of the new size of the image
	*@param yFin The horizontal bottom index of the new size of the image
	*@param img Is the image which will be modified by the effect
	*/
	public BufferedImage rognage(BufferedImage img, int xDebut, int xFin, int yDebut, int yFin){
		int newWidth = img.getWidth() - (xDebut + xFin);
		int newHeight = img.getHeight() - (yDebut + yFin);
		BufferedImage bi = new BufferedImage(newWidth, newHeight, img.getType()); 

		for (int x = 0; x < newWidth; x++) {
			for (int y = 0; y < newHeight; y++) {
				int px = img.getRGB(x+xDebut, y+yDebut);
                bi.setRGB(x, y, px);			
			}
		}
		return bi;

	}

	/**
	*Resize a given image by the given dimensions
	*@param h Is the heigth of the resized image
	*@param l Is the width of the resizer image
	*@param imageSource Is the image which will be modified by the effect
	*/
	public BufferedImage resize(BufferedImage imageSource, int l, int h){
		ImageIcon icontmp = new ImageIcon(imageSource);
		Image img = icontmp.getImage().getScaledInstance(l,h,Image.SCALE_SMOOTH);

		BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

		// Draw the image on to the buffered image
		Graphics2D bGr = bi.createGraphics();
		bGr.drawImage(img, 0, 0, null);
		bGr.dispose();

		return bi;
	}

	/**
	*Add an image to the list. Only used when an image is modified, but no saved
	*@param bi Is the image which will be add the the list
	*/
	public void ajouter(BufferedImage bi){
		cpt++;
		if(cpt>=temp.size() && !isPrevious){
			temp.add(bi);
		}
		else{
			while(cpt-1!=temp.size()){
				temp.remove(temp.size()-1);
			}
			temp.add(bi);
		}
		editControl.setImg(bi);
	}

	/**
	*Display the image before the last effect was applied
	*/
	public void undo(){
		if(cpt>1){
			isPrevious = true;
			cpt--;
			editControl.setImg(getBI(cpt));
		}
	}

	/**
	*Display the next image after do an undo.
	*/
	public void redo(){
		if(cpt<temp.size()){
			cpt++;
			isPrevious = false;
			editControl.setImg(getBI(cpt));
		}
	}

	/**
	*Cancel all effect applied, display the original image
	*/
	public void cancel(){
		cpt=1;
		isPrevious=false;
		while(temp.size()!=1){
			temp.remove(temp.size()-1);
		}
		editControl.setImg(getBI(cpt));
		
	}

	/**
	*Gives one of the image in the list
	*@param key Is an index of the ArrayList
	*@return Return a BufferedImage
	*/
	public BufferedImage getBI(int key){
		return temp.get(key-1);
	}

	/**
	*@return Return the list of image
	*/
	public ArrayList<BufferedImage> getTemp(){
		return temp;
	}

	/**
	*@return Return the count of the list, where the current displayed image is in the list
	*/
	public int getCpt(){
		return cpt;
	}
}