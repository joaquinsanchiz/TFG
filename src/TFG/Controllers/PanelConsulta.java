package TFG.Controllers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import TFG.DBContext;
import TFG.ImageData;
import TFG.Vistas.PaneInformacionAparicion;
import TFG.Vistas.PanelAccionesConsulta;
import TFG.Vistas.PanelConQuien;
import TFG.Vistas.PanelGridImagenes;

public class PanelConsulta extends JPanel{
	
	private PanelAccionesConsulta accionesPanel;
	public JScrollPane informacionScrollPane = new JScrollPane();
	
	public PanelConsulta()
	{
		super();

		DBContext context = new DBContext();
		
		ArrayList<ImageData> nombresDB = context.obtainSourcesNames(true);
		String[] nombres = new String[nombresDB.size()];
		for(int i = 0; i < nombres.length; i++)
		{
			nombres[i] = nombresDB.get(i).getName();
		}
		
		String[] opciones = {"donde aparece", "con quien aparece"};
		
		this.accionesPanel = new PanelAccionesConsulta((String[]) nombres, opciones);
		
		this.setLayout(new BorderLayout(10, 10));
		this.add(accionesPanel, BorderLayout.NORTH);
		this.add(informacionScrollPane, BorderLayout.CENTER);
		
		this.accionesPanel.getBotonConsulta().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				DBContext context = new DBContext();
				ArrayList<JLabel> resultado;
				
				switch(accionesPanel.getOpcionesLista().getSelectedItem().toString())
				{
				case "donde aparece":
					
					remove(informacionScrollPane);
					resultado = context.consultarDonde(getAccionesPanel().getNombresLista().getSelectedItem().toString());
					PanelGridImagenes informacionPanel = new PaneInformacionAparicion(resultado);
					informacionScrollPane = new JScrollPane(informacionPanel);
					add(informacionScrollPane, BorderLayout.CENTER);
					repaint();
					revalidate();					
					break;
					
				case "con quien aparece":
					
					remove(getInformacionScrollPane());
					resultado = context.consultarConQuien(getAccionesPanel().getNombresLista().getSelectedItem().toString());
					PanelGridImagenes informacionQuien = new PanelConQuien(resultado);
					informacionScrollPane = new JScrollPane(informacionQuien);
					System.out.println(resultado.size());
					add(informacionScrollPane, BorderLayout.CENTER);
					repaint();
					revalidate();
					
					break;

				}
				
			}
		});
		
		try {
			if(!context.getConn().isClosed())
			{
				context.getConn().close();
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}

	public PanelAccionesConsulta getAccionesPanel() {
		return accionesPanel;
	}

	public void setAccionesPanel(PanelAccionesConsulta accionesPanel) {
		this.accionesPanel = accionesPanel;
	}

	public JScrollPane getInformacionScrollPane() {
		return informacionScrollPane;
	}

	public void setInformacionScrollPane(JScrollPane informacionScrollPane) {
		this.informacionScrollPane = informacionScrollPane;
	}
	

}
