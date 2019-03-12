package TFG.Vistas;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelAccionesConsulta extends JPanel{
	
	private JComboBox nombresLista;
	private JLabel informacion = new JLabel("consultar sobre");
	private JComboBox opcionesLista;
	private JButton botonConsulta = new JButton("Ir");

	
	public PanelAccionesConsulta(String[] nombres, String[] opciones)
	{
		super();
		this.nombresLista = new JComboBox<>(nombres);
		this.opcionesLista = new JComboBox<>(opciones);	
		
		this.add(nombresLista);
		this.add(informacion);
		this.add(opcionesLista);
		this.add(this.getBotonConsulta());
		
	}


	public JComboBox getNombresLista() {
		return nombresLista;
	}


	public void setNombresLista(JComboBox nombresLista) {
		this.nombresLista = nombresLista;
	}


	public JLabel getInformacion() {
		return informacion;
	}


	public void setInformacion(JLabel informacion) {
		this.informacion = informacion;
	}


	public JComboBox getOpcionesLista() {
		return opcionesLista;
	}


	public void setOpcionesLista(JComboBox opcionesLista) {
		this.opcionesLista = opcionesLista;
	}


	public JButton getBotonConsulta() {
		return botonConsulta;
	}


	public void setBotonConsulta(JButton botonConsulta) {
		this.botonConsulta = botonConsulta;
	}
	
	

}
