package TFG;

import javax.swing.JButton;

public class BotonPrincipal extends JButton{
	
	private int index;
	
	public BotonPrincipal(int index, String text)
	{
		super(text);
		this.setIndex(index);
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	

}
