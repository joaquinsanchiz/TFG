package TFG.Vistas;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class VistaimagenesPanel extends JPanel{
	
    private JButton aceptar = new JButton("Aceptar");
    private JButton cambiar = new JButton("Cambiar");
	private JFileChooser fc = new JFileChooser();
	private 	BufferedImage img;
	
	public VistaimagenesPanel()
	{
		super();
		int seleccion = fc.showOpenDialog(null);
		if(seleccion == JFileChooser.APPROVE_OPTION) {
			
			try {
				img = ImageIO.read(fc.getSelectedFile());
				JLabel label = new JLabel();
				Image dimg = img.getScaledInstance(300, -1, Image.SCALE_FAST);
				ImageIcon imageIcon = new ImageIcon(dimg);			
				label.setIcon(imageIcon);
				setLayout(new GridBagLayout());
				GridBagConstraints gbc = new GridBagConstraints();
	            gbc.gridwidth = GridBagConstraints.REMAINDER;
	            gbc.anchor = GridBagConstraints.NORTH;
				
				add(label, gbc);
				
				gbc.anchor = GridBagConstraints.CENTER;
	            gbc.fill = GridBagConstraints.VERTICAL;

	            JPanel buttons = new JPanel(new FlowLayout());
	            
	            
	            buttons.add(aceptar);
	            
	            
	            
	            buttons.add(cambiar);
	            
	            gbc.weighty = 0;   
	            add(buttons, gbc);

				
				repaint();
				revalidate();
				
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
	        
		}
	}

	public JButton getAceptar() {
		return aceptar;
	}

	public void setAceptar(JButton aceptar) {
		this.aceptar = aceptar;
	}

	public JButton getCambiar() {
		return cambiar;
	}

	public void setCambiar(JButton cambiar) {
		this.cambiar = cambiar;
	}

	public JFileChooser getFc() {
		return fc;
	}

	public void setFc(JFileChooser fc) {
		this.fc = fc;
	}

	public BufferedImage getImg() {
		return img;
	}

	public void setImg(BufferedImage img) {
		this.img = img;
	}
	
	

}
