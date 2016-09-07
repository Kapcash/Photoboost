package pda.datas;

import java.awt.*;
import java.awt.Font.*;
import java.lang.Object;
import java.io.*;

/**
*MyriadFont is a special font for the Photoboost application<br />
*
* @author Florent Catiau-Tristant and Mehdi Haddad
* @version 1.0
*/
public class MyriadFont extends Font implements java.io.Serializable{

	/**
	*
	*/
	private static final long serialVersionUID=1L;

	/**
	*The constructor only call the constructor of Font
	*/
	public MyriadFont(){
		super(new Font("null", Font.PLAIN, 20));
	}

	/**
	*Get z Myriad font with the given size
	*@param size Is the wanted size of the font
	*/
	public Font getFont(int size){
		//CREATION DE MYRIAD
		Font retMyriad = new Font("null", Font.PLAIN, size);
		try {
			Font myriad = Font.createFont(Font.TRUETYPE_FONT, new File("./data/font/MYRIADAM.TTF")).deriveFont(Font.PLAIN, size);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("./data/font/MYRIADAM.TTF")));
			retMyriad = myriad;
		}
		catch (IOException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		catch(FontFormatException e){
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return retMyriad;
	}
}
	