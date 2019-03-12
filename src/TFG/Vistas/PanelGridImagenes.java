package TFG.Vistas;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelGridImagenes extends JPanel{
	
	public PanelGridImagenes(ArrayList<JLabel> resultado)
	{
		super();
		this.setLayout(new GridLayout((int) Math.ceil(resultado.size()/3), 3));
		for(int i = 0; i < resultado.size(); i++)
		{
			this.add(resultado.get(i));
		}

	}

}
