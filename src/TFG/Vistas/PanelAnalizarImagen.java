package TFG.Vistas;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class PanelAnalizarImagen extends JPanel{
	
    private JButton cargaImagen = new JButton("Analizar una imagen");

	
	public PanelAnalizarImagen()
	{
		super();
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
	}


	public JButton getCargaImagen() {
		return cargaImagen;
	}


	public void setCargaImagen(JButton cargaImagen) {
		this.cargaImagen = cargaImagen;
	}
	
	

}
