package TFG.Vistas;

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

import TFG.DBContext;

public class PanelConQuien extends PanelGridImagenes{
	
	public PanelConQuien(ArrayList<JLabel> resultado)
	{
		super(resultado);
	}
}
