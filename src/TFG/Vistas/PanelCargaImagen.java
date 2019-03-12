package TFG.Vistas;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class PanelCargaImagen extends JPanel{
	
    private JButton cargaImagen;
	private JFileChooser fc = new JFileChooser();
	private 	BufferedImage img;
	private JLabel iconoCargado;

	
	public PanelCargaImagen(String stringBoton)
	{
		super();
		this.cargaImagen = new JButton(stringBoton);
		setBorder(new EmptyBorder(10, 10, 10, 10));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;

        add(new JLabel("<html><h1><strong><i>Rekognition System</i></strong></h1><hr></html>"), gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JPanel buttons = new JPanel(new GridBagLayout());
        buttons.add(cargaImagen, gbc);
        
        gbc.weighty = 0;   
        add(buttons, gbc);
        this.setPreferredSize(new Dimension(820, 800));
        
	}


	public JButton getCargaImagen() {
		return cargaImagen;
	}


	public void setCargaImagen(JButton cargaImagen) {
		this.cargaImagen = cargaImagen;
	}
	
	

}
