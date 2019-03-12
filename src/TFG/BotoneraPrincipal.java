package TFG;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class BotoneraPrincipal extends JPanel{
	
	ArrayList<BotonPrincipal> botonesPrincipales;
	private int botonActual;
	final int NBOTONES = 3;
	
	public BotoneraPrincipal()
	{
		super();
        this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		this.setBackground(Color.DARK_GRAY);
        this.setBorder(new TitledBorder("Menu"));
		
		botonesPrincipales = new ArrayList<BotonPrincipal>();
		
		for(int i = 0; i < this.NBOTONES; i++)
		{
			switch(i)
			{
			case 0:
				this.getBotonesPrincipales().add(new BotonPrincipal(i, "Source"));
				this.setBotonActual(0);
				this.add(this.getBotonesPrincipales().get(i));
				break;
			case 1:
				this.getBotonesPrincipales().add(new BotonPrincipal(i, "Target"));
				this.add(this.getBotonesPrincipales().get(i));
				break;
			case 2:
				this.getBotonesPrincipales().add(new BotonPrincipal(i, "Consulta"));
				this.add(this.getBotonesPrincipales().get(i));
				break;
			}
		}
		
		//Definir controladores de botones
		
	}

	public ArrayList<BotonPrincipal> getBotonesPrincipales() {
		return botonesPrincipales;
	}

	public void setBotonesPrincipales(ArrayList<BotonPrincipal> botonesPrincipales) {
		this.botonesPrincipales = botonesPrincipales;
	}

	public int getBotonActual() {
		return botonActual;
	}

	public void setBotonActual(int botonActual) {
		this.botonActual = botonActual;
		this.getBotonesPrincipales().get(botonActual).setBackground(Color.BLUE);
	}
	

}
