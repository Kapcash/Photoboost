package pda.datas;

import java.util.*;
import java.io.*;
import pda.view.*;

/**
*ParamDatas is a class containing all the parameters of the Photoboost application
*It save and load the parameters on a "parametres.out" file.
*
* @author Florent Catiau-Tristant and Mehdi Haddad
* @version 1.0
*/

public class ParamDatas{
	
	/**
	*The view using and saving the parameters
	*/
	private PhotoBoostView photoBoost;
	/**
	*The nombre of column on the ChoosePhoto gallery
	*/
	private int nbColumn;
	/**
	*The dimension of the displayed images, depending of the nombre of colum
	*/
	private int dimension;
	/**
	*The path to save new images
	*/
	private String savePath;

	/**
	*The constructor initialize the view
	*@param view It the view using the ParamDatas
	*/
	public ParamDatas(PhotoBoostView view){
		photoBoost = view;
	}

	/**
	*@return Return the number of columns defined on the parameters
	*/
	public int getNbColumn(){
		return nbColumn;
	}
	/**
	*@return Return the dimension of the images, depending on the number of columns
	*/
	public int getDimension(){
		return dimension;
	}
	/**
	*@return Return the string containing the path to save new images
	*/
	public String getSavePath(){
		return savePath;
	}
	/**
	*Save the new parameters on the "parametres.out" file
	*/
	public void saveParam(){
		try{
			String param = "nbColumn="+nbColumn+"\ndimension="+dimension+"\nchemin de sauvegarde="+savePath;
			BufferedWriter buf = new BufferedWriter(new FileWriter("./data/parametres.out"));
			PrintWriter print = new PrintWriter(buf);
			print.println(param);
			print.close();
		}
		catch(NoSuchElementException e){
			System.out.println(e.getMessage());
		}
		catch(IOException e){
			System.out.println(e.getMessage());
		}
	}

	/**
	*Load the parameters wrote on the "parametres.out" file.
	*/
	public void loadParam(){
		String param;
		String[] split;
		try{
			File rep = new File("./data/parametres.out");
			if(!rep.exists()){
				rep.createNewFile();
				//Paramètres par défaut
				File path = new File("./Exemple_Images/saved");
				if(!path.exists()){
					path.mkdir();
				}
				nbColumn = 2;
				dimension = 100-(25*(nbColumn-1));
				savePath = "./Exemple_Images/saved";
				//Sauvegarde des paramètres dans le fichier
				this.saveParam();
			}
			else{
				BufferedReader buf = new BufferedReader(new FileReader("./data/parametres.out"));
				//Premier paramètre : nbColumn
				param = buf.readLine();
				split = param.split("=");
				nbColumn = Integer.parseInt(split[1]);
				//Second paramètre : dimension
				param = buf.readLine();
				split = param.split("=");
				dimension = Integer.parseInt(split[1]);
				//Troisième paramètre : savePath
				param = buf.readLine();
				split = param.split("=");
				savePath = split[1];
			}
			photoBoost.setNbColumn(nbColumn);
			photoBoost.setDimension(dimension);
			photoBoost.setSavePath(savePath);
		}
		catch(FileNotFoundException e){
			System.out.println(e.getMessage());
		}
		catch(IOException e){
			System.out.println(e.getMessage());
		}

	}

	/**
	*Change the number of columns of the ChoosePhoto gallery
	*@param nb Is the new number of column
	*/
	public void setColumn(int nb){
		nbColumn = nb;
		dimension = 119-(25*(nb-1));
		photoBoost.setNbColumn(nbColumn);
		photoBoost.setDimension(dimension);
	}

	/**
	*Change the path to save images
	*@param path Is the new path
	*/
	public void setSavePath(String path){
		photoBoost.setSavePath(path);
		savePath = path;
	}
}