package TFG.Controllers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import TFG.BotoneraDependiente;

public class PanelPrincipal extends JPanel{
	
	private int indexBotonPrincipal = 0;
	private BotoneraDependiente botoneraDependiente;
	private PanelSecundario panelSecundario;
	
	public void cambiarPanelSecundario (int indexBotonPrincipal, int indexBotonSecundario)
	{
		if(indexBotonPrincipal != 2)
		{
			getBotoneraDependiente().setBotonActual(indexBotonSecundario);

		}
		remove(getPanelSecundario());
		panelSecundario = new PanelSecundario(indexBotonSecundario, getIndexBotonPrincipal());	
		add(getPanelSecundario(), BorderLayout.CENTER);
		
		repaint();
		revalidate();
	}
	
	public PanelPrincipal(int indexBotonPrincipal)
	{
		super();
		this.setBackground(Color.WHITE);
		this.setPreferredSize(new Dimension(820, 800));
		this.setIndexBotonPrincipal(indexBotonPrincipal);
		
		if(this.getIndexBotonPrincipal() != 2) 
		{
			botoneraDependiente = new BotoneraDependiente(this.getIndexBotonPrincipal());
			panelSecundario = new PanelSecundario(this.getBotoneraDependiente().getBotonActual(), this.getIndexBotonPrincipal());
		}
		else
		{
			panelSecundario = new PanelSecundario(0, this.getIndexBotonPrincipal());
		}


		//Controladores para alternar botonera secundaria
		
		switch(this.getIndexBotonPrincipal())
		{
			case 0:
				remove(getBotoneraDependiente());		
				
				botoneraDependiente = new BotoneraDependiente(getIndexBotonPrincipal());
				this.getBotoneraDependiente().setBotonActual(0);		
				
				this.getBotoneraDependiente().getBotonesDependientes()[0].addActionListener(new ActionListener() {			
					@Override
					public void actionPerformed(ActionEvent e) {
						
						//Cargar imagenes source nuevas
						getBotoneraDependiente().setBotonActual(0);
						cambiarPanelSecundario(indexBotonPrincipal, getBotoneraDependiente().getBotonActual());
						
					}
				});
				
				this.getBotoneraDependiente().getBotonesDependientes()[1].addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						
						//Mostrar imagenes source
						getBotoneraDependiente().setBotonActual(1);
						cambiarPanelSecundario(indexBotonPrincipal, getBotoneraDependiente().getBotonActual());
	
					}
				});	
				
				this.getBotoneraDependiente().getBotonesDependientes()[2].addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						
						//Mostrar imagenes source
						getBotoneraDependiente().setBotonActual(2);
						cambiarPanelSecundario(indexBotonPrincipal, getBotoneraDependiente().getBotonActual());
	
					}
				});
				break;
				
				
				
			case 1:			
							
				remove(getBotoneraDependiente());		
				botoneraDependiente = new BotoneraDependiente(getIndexBotonPrincipal());
				this.getBotoneraDependiente().setBotonActual(0);
	
				this.getBotoneraDependiente().getBotonesDependientes()[0].addActionListener(new ActionListener() {		
					@Override
					public void actionPerformed(ActionEvent e) {
						//Cargar imagenes source nuevas
						getBotoneraDependiente().setBotonActual(0);
						cambiarPanelSecundario(indexBotonPrincipal, getBotoneraDependiente().getBotonActual());
					}
				});
				
				this.getBotoneraDependiente().getBotonesDependientes()[1].addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						//Mostrar imagenes source
						getBotoneraDependiente().setBotonActual(1);
						cambiarPanelSecundario(indexBotonPrincipal, getBotoneraDependiente().getBotonActual());
					}
				});
	
				break;
				
			case 2:
				
				cambiarPanelSecundario(indexBotonPrincipal, 0);			
				break;
				
			default:
				break;
		}
		
		if(this.getIndexBotonPrincipal() != 2)
		{
			this.add(this.getBotoneraDependiente(), BorderLayout.EAST);
		}
		
		this.add(this.getPanelSecundario(), BorderLayout.CENTER);
		
		this.revalidate();
		this.repaint();

	}


	public BotoneraDependiente getBotoneraDependiente() {
		return botoneraDependiente;
	}


	public void setBotoneraDependiente(BotoneraDependiente botoneraDependiente) {
		this.botoneraDependiente = botoneraDependiente;
	}


	public int getIndexBotonPrincipal() {
		return indexBotonPrincipal;
	}


	public void setIndexBotonPrincipal(int indexBotonPrincipal) {
		this.indexBotonPrincipal = indexBotonPrincipal;
	}


	public PanelSecundario getPanelSecundario() {
		return panelSecundario;
	}


	public void setPanelSecundario(PanelSecundario panelSecundario) {
		this.panelSecundario = panelSecundario;
	}
	
	
	

}
