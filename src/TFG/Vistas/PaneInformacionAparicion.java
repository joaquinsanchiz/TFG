package TFG.Vistas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import TFG.DBContext;


public class PaneInformacionAparicion extends PanelGridImagenes{
	
	public PaneInformacionAparicion(ArrayList<JLabel> imagenes)
	{	
		super(imagenes);
	}
}
