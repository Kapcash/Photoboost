package pda.view;

import java.io.*;

/**
* A Filter to only load images on the application
* @author Florent Catiau-Tristant and Mehdi Haddad
* @version 1.0
*/
public class FilterImage implements FileFilter{
	
	/**
	*@return Return true if the file, defined by the pathname, is an image (.png, .jpg, .jpeg, .bmp). False else
	*@param pathname Is the path of a chosen file
	*/
	public boolean accept(File pathname){
		boolean ret = false;
		String name = pathname.getName();
		if(name.contains(".png") || name.contains(".PNG") || name.contains(".jpg") || name.contains(".JPG") || name.contains(".jpeg") || name.contains(".JPEG") || name.contains(".bmp") || name.contains(".BMP")){
			ret = true;
		}
		return ret;
	}
}