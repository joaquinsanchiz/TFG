package TFG.Vistas;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
import javax.swing.JScrollPane;

import TFG.DBContext;


public class PanelMostrarImagenesSource extends PanelGridImagenes{
	
	public PanelMostrarImagenesSource(ArrayList<JLabel> resultado)
	{
		super(resultado);
	}
}
