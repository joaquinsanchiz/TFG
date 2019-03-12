package TFG;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class BotoneraDependiente extends JPanel{
	
	private BotonPrincipal[] botonesDependientes;
	private int botonActual;
	
	public void resetPanel() {
		if(!(this.getBotonesDependientes()== null))
		{
			for(int i = 0; i < this.getBotonesDependientes().length; i++)
			{
				this.getBotonesDependientes()[i].removeAll();
			}
			this.removeAll();
			this.botonesDependientes = null;
		}	
	}
	
	public BotoneraDependiente (int indexBotoneraPrincipal)
	{
		super();
		this.setBackground(Color.GRAY);
        this.setBorder(new TitledBorder("Menu secundario"));
		this.setPreferredSize(new Dimension(150, 800));
        
		if(!(this.getBotonesDependientes() == null))
		{
			for(int i = 0; i < this.getBotonesDependientes().length; i++) {
				remove(this.getBotonesDependientes()[i]);
			}
		}
		
		switch(indexBotoneraPrincipal)
		{
			case 0:	//Panel Source
				System.out.println("Panel source");
				
				botonesDependientes = new BotonPrincipal[3];
				//this.setLayout(new GridLayout(2,1));
				this.getBotonesDependientes()[0] = new BotonPrincipal(0, "Cargar\n imagenes");
				this.getBotonesDependientes()[1] = new BotonPrincipal(1, "Mostrar imagenes");
				this.getBotonesDependientes()[2] = new BotonPrincipal(2, "Mostrar Unknown");
				
				break;
				
			case 1: //Panel Target
				
				System.out.println("Panel target");
							
				botonesDependientes = new BotonPrincipal[2];
				//this.setLayout(new GridLayout(2,1));
				this.getBotonesDependientes()[0] = new BotonPrincipal(0, "Cargar\n imagenes");
				this.getBotonesDependientes()[1] = new BotonPrincipal(1, "Mostrar historial");
				
				break;
			case 2:
				System.out.println("Panel target");
				resetPanel();
				break;
				
			default: 
				break;
		}
		
		
		
		for(int i = 0; i < this.getBotonesDependientes().length; i++)
		{
			this.add(this.getBotonesDependientes()[i]);
		}
		
		revalidate();
		this.repaint();
	}

	public int getBotonActual() {
		return botonActual;
	}

	public void setBotonActual(int botonActual) {
		this.botonActual = botonActual;
		this.getBotonesDependientes()[0].setBackground(Color.BLUE);
	}

	public BotonPrincipal[] getBotonesDependientes() {
		return botonesDependientes;
	}

	public void setBotonesDependientes(BotonPrincipal[] botonesDependientes) {
		this.botonesDependientes = botonesDependientes;
	}
	
	

}
