package TFG.Vistas;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class PanelCambiarUnknown extends JPanel{
	
	private JButton aceptar = new JButton("Aceptar");
    private JButton cambiar = new JButton("Cambiar");
    private JTextArea nombre;
    private String anteriorNombre = "";
	
	public PanelCambiarUnknown()
	{
		super();
	}
	
	public PanelCambiarUnknown(JLabel label)
	{
		super();
		this.setAnteriorNombre(label.getText());
		label.setName("");
		
		nombre = new JTextArea(this.getAnteriorNombre());
		nombre.setEditable(true);
		nombre.setRows(1);
		nombre.setColumns(10);
		
		
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;
		
		add(label, gbc);
		
		gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.VERTICAL;

        JPanel buttons = new JPanel(new FlowLayout());	            
        
        buttons.add(aceptar);	 
        
        buttons.add(nombre);
        
        buttons.add(cambiar);
        
        gbc.weighty = 0;   
        add(buttons, gbc);

		
		repaint();
		revalidate();
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

	public JTextArea getNombre() {
		return nombre;
	}

	public void setNombre(JTextArea nombre) {
		this.nombre = nombre;
	}

	public String getAnteriorNombre() {
		return anteriorNombre;
	}

	public void setAnteriorNombre(String anteriorNombre) {
		this.anteriorNombre = anteriorNombre;
	}
	
	

}
