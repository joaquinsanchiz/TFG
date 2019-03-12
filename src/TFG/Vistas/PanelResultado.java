package TFG.Vistas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

import TFG.ImageData;

public class PanelResultado extends JPanel{
	
	
	public static BufferedImage image; 
	public static ArrayList<ImageData> encontrados;
	public static ArrayList<ImageData> noEncontrados;
	
	public PanelResultado(BufferedImage image, ArrayList<ImageData> encontrados, ArrayList<ImageData> noEncontrados)
	{
		super();
		
		int w = image.getWidth();
		int h = image.getHeight();
		
		this.image = image;
		this.encontrados = encontrados;
		this.noEncontrados = noEncontrados;
		
		this.setPreferredSize(new Dimension(this.image.getWidth(), this.image.getHeight()));

	}
	
	@Override
	public void paintComponent(Graphics g) 
	  { 
	    g.drawImage(image, 0, 0, null); 
	    
	    for(int i = 0; i < this.noEncontrados.size(); i++)
	    {
	    		Float x = (float) (noEncontrados.get(i).getX());
		    Float y = (float) (noEncontrados.get(i).getY());
		    Float height = (float) (noEncontrados.get(i).getHeight());
		    Float width = (float) (noEncontrados.get(i).getWidth());
		    
		    g.setColor(Color.RED);
		    g.drawRect(x.intValue(), y.intValue(), width.intValue(), height.intValue());
		    String iterator = "" + i + ". Not found";
		    g.drawString(iterator, x.intValue(), y.intValue());
	    }
	    
	    for(int i = 0; i < this.encontrados.size(); i++)
	    {
	    		Float x = (float) (encontrados.get(i).getX());
		    Float y = (float) (encontrados.get(i).getY());
		    Float height = (float) (encontrados.get(i).getHeight());
		    Float width = (float) (encontrados.get(i).getWidth());
		    
		    g.setColor(Color.GREEN);
		    g.drawRect(x.intValue(), y.intValue(), width.intValue(), height.intValue());
		    String iterator = "" + i + "." + this.encontrados.get(i).getName();
		    g.drawString(iterator, x.intValue(), y.intValue());
	    }	
	    
	    repaint(); 
	  } 
	

}
