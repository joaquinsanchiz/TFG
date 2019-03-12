package TFG;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;

import TFG.Controllers.PanelPrincipal;

public class MainFrame extends JFrame{
	
	private BotoneraPrincipal botonesPrincipales;
	private PanelPrincipal panelPrincipal;
	
	public MainFrame()
	{
		super("Recognition system");
		
		botonesPrincipales = new BotoneraPrincipal();
		getBotonesPrincipales().setBotonActual(0);
		
		panelPrincipal = new PanelPrincipal(this.getBotonesPrincipales().getBotonActual());
        
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(1000, 900);
		this.setLocation(this.getToolkit().getScreenSize().height/2, this.getToolkit().getScreenSize().width/2);
		
		this.getBotonesPrincipales().getBotonesPrincipales().get(0).addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) 
			{				
				remove(getPanelPrincipal());		
				getBotonesPrincipales().setBotonActual(0);

				panelPrincipal = new PanelPrincipal(getBotonesPrincipales().getBotonActual());			
				add(getPanelPrincipal());
				
				repaint();
				revalidate();
			}
		});
		
		this.getBotonesPrincipales().getBotonesPrincipales().get(1).addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{				
				remove(getPanelPrincipal());				
				getBotonesPrincipales().setBotonActual(1);
				
				panelPrincipal = new PanelPrincipal(getBotonesPrincipales().getBotonActual());			
				add(getPanelPrincipal());
								
				repaint();
				revalidate();
			}
		});
		
		this.getBotonesPrincipales().getBotonesPrincipales().get(2).addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				remove(getPanelPrincipal());
				getBotonesPrincipales().setBotonActual(2);
				
				panelPrincipal = new PanelPrincipal(getBotonesPrincipales().getBotonActual());	
				add(getPanelPrincipal());
				
				revalidate();
				repaint();

			}
		});
		
		getContentPane().add(panelPrincipal);
		this.add(botonesPrincipales, BorderLayout.SOUTH);
		
		revalidate();
		
	}

	public BotoneraPrincipal getBotonesPrincipales() {
		return botonesPrincipales;
	}

	public void setBotonesPrincipales(BotoneraPrincipal botonesPrincipales) {
		this.botonesPrincipales = botonesPrincipales;
	}

	public PanelPrincipal getPanelPrincipal() {
		return panelPrincipal;
	}

	public void setPanelPrincipal(PanelPrincipal panelPrincipal) {
		this.panelPrincipal = panelPrincipal;
	}
	
	
	

}
