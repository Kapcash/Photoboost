package pda.test;

import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.*;
import java.util.*;


public class Effect{

	ArrayList<BufferedImage> temp = new ArrayList<BufferedImage>();
	int cpt = 0;
	boolean isPrevious=false;

	public Effect(){
		
	}

	public BufferedImage blackwhite(BufferedImage img){
		BufferedImage bi = new BufferedImage(img.getWidth(),img.getHeight(),BufferedImage.TYPE_BYTE_GRAY);
		bi.getGraphics().drawImage(img,0,0,null);
		
		return bi;
	}

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

	public BufferedImage repoussage(BufferedImage img) {

		BufferedImage bi = new BufferedImage(img.getWidth(), img.getHeight(), img.getType()); 
		float data[] = {-2f, -1f, 0f, -1f, 1f, 1f, 0f,1f, 2f};
		Kernel kernel = new Kernel(3, 3, data);
		ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
		bi = op.filter(img, null);
		
		return bi;
	}

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

	public void ajouter(BufferedImage bi){
		if(bi==null) throw new NullPointerException();
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
	}

	public void undo(){
		if(cpt>1){
			isPrevious = true;
			cpt--;
		}
	}

	public void redo(){
		if(cpt<temp.size()){
			cpt++;
			isPrevious = false;
		}
	}

	public void cancel(){
		cpt=1;
		isPrevious=false;
		while(temp.size()!=1){
			temp.remove(temp.size()-1);
		}
		
	}

	public BufferedImage getBI(int key){
		if(key>temp.size() || key<1) throw new IndexOutOfBoundsException();
		return temp.get(key-1);
	}

	public ArrayList<BufferedImage> getTemp(){
		return temp;
	}
	public int getCpt(){
		return cpt;
	}
}