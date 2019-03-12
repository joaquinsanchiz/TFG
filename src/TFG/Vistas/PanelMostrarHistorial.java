package TFG.Vistas;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import TFG.DBContext;

public class PanelMostrarHistorial extends PanelGridImagenes{
	
	public PanelMostrarHistorial(ArrayList<JLabel> imagenes)
	{
		super(imagenes);
	}

}
