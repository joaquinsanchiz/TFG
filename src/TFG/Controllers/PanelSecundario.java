package TFG.Controllers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.FileChooserUI;

import TFG.DBContext;
import TFG.ImageData;
import TFG.RecognitionSystem;
import TFG.User;
import TFG.Vistas.PaneInformacionAparicion;
import TFG.Vistas.PanelAnalizarImagen;
import TFG.Vistas.PanelCambiarUnknown;
import TFG.Vistas.PanelCargaImagen;
import TFG.Vistas.PanelConQuien;
import TFG.Vistas.PanelConfirmacion;
import TFG.Vistas.PanelGridImagenes;
import TFG.Vistas.PanelMostrarHistorial;
import TFG.Vistas.PanelMostrarImagenesSource;
import TFG.Vistas.PanelResultado;
import TFG.Vistas.VistaimagenesPanel;

public class PanelSecundario extends JPanel{
	
	DBContext context = new DBContext();
	
	public void cargarUnknown()
	{
		this.removeAll();
		setLayout(new BorderLayout());
		
	}
	
	public PanelSecundario(int indexBotonDependiente, int indexBotonPrincipal)
	{
		super();
        this.setPreferredSize(new Dimension(820, 800));
		System.out.println(indexBotonPrincipal + indexBotonDependiente);
		
		switch(indexBotonPrincipal)
		{
		case 0:
			switch(indexBotonDependiente)
			{
			case 0:
				this.cargaImagenesSource();
				break;
			
			case 1:			
				this.vistaMostrarImagenesSource();
				break;
				
			case 2:
				this.vistaMostrarImagenesUnknown();
				break;
			
			default: break;
			
			}
			break;
			
		case 1:
			switch(indexBotonDependiente)
			{
			case 0:
				this.cargaImagenesTarget();
				break;
			
			case 1:
				this.vistaMostrarHistorial();
				break;
			
			default: break;
			
			}
			break;
			
		case 2:
			this.vistaMostrarConsulta();
			break;
					
		}
	}
	
	public void cargaImagenesSource()
	{
		this.removeAll();
		setLayout(new BorderLayout());
		
		//DBContext context = new DBContext();
		//context.limpiarDB();
		
		PanelCargaImagen panel = new PanelCargaImagen("Cargar imagen");
		
		panel.getCargaImagen().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				accionCambiar(true);	
			}
        });
		
		add(panel, BorderLayout.CENTER);
		repaint();
		revalidate();
	}
	
	public void vistaMostrarImagenesSource()
	{
		this.removeAll();
		setLayout(new BorderLayout());
        
        PanelMostrarImagenesSource panel = new PanelMostrarImagenesSource(context.consultarImagenesSource());
        JScrollPane scrollPane = new JScrollPane(panel);
        add(scrollPane, BorderLayout.CENTER);
        repaint();	        
		revalidate();
		try {
			if(!this.context.getConn().isClosed())
			{
				this.context.getConn().close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void cambiarNombreUnknown(JLabel label)
	{
		this.removeAll();
		setLayout(new BorderLayout());
		
		PanelCambiarUnknown panel = new PanelCambiarUnknown(label);
		add(panel, BorderLayout.CENTER);
		
		panel.getCambiar().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				vistaMostrarImagenesUnknown();
			}
		});
		
		panel.getAceptar().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String nombre = panel.getNombre().getText() + ".jpg";
				String nombreAnterior = panel.getAnteriorNombre();
				
				int idAnterior = context.obtainImageID(nombreAnterior + ".jpg");
				int tag = context.lookForTag(idAnterior);
				ImageData.cambiarUnknownSource(nombreAnterior + ".jpg", nombre);
				int idActual = context.obtainImageID(nombre);
				
				try {
					context.getConn().createStatement().executeQuery("update Imagetags set imageid='" + idActual + "' where imageid='" + tag +"';");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				/*
				 * Sacar tagid de Unknown a cambiar
				 * Mover de Unknown a source
				 * obtener nuevo id de source
				 * cambiar imageid en Imagetags con tag = tagid de Unknown
				 */
				
				
				
			}
		});
		
		repaint();
		revalidate();
		
		
		try {
			if(!context.getConn().isClosed())
			{
				context.getConn().close();
				context.setConn(DriverManager.getConnection(context.getUrl()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void vistaMostrarImagenesUnknown()
	{
		this.removeAll();
		setLayout(new BorderLayout());
		ArrayList<JLabel> images = context.consultarImagenesUnknown();
		for(int i = 0; i < images.size(); i++)
		{
			images.get(i).addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent e) {
					
				}
				
				@Override
				public void mousePressed(MouseEvent e) {
					
				}
				
				@Override
				public void mouseExited(MouseEvent e) {
					
				}
				
				@Override
				public void mouseEntered(MouseEvent e) {
					
				}
				
				@Override
				public void mouseClicked(MouseEvent e) {
					cambiarNombreUnknown((JLabel) e.getComponent());
				}
			});
		}
		
		PanelGridImagenes panel = new PanelGridImagenes(images);
		JScrollPane scrollPane = new JScrollPane(panel);
        add(scrollPane, BorderLayout.CENTER);
        repaint();	        
		revalidate();
		
		
	}
	
	
	public void vistaCargaImagen() 
	{
		this.removeAll();
		setLayout(new GridLayout(1, 1));
		setBorder(new EmptyBorder(10, 10, 10, 10));
        
        PanelCargaImagen panel = new PanelCargaImagen("Cargar imagen Source");	
        
        panel.getCargaImagen().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				removeAll();
				cargaImagenesPanel(true);					
			}
        });
        
        add(panel);
        
        repaint();	        
		revalidate();
	}
	public void insertarImagenSource(File selection)
	{
		try 
		{
			Files.copy(Paths.get(selection.getAbsolutePath()), Paths.get(new File(DBContext.sourceRoute + selection.getName()).getAbsolutePath()));
			DBContext context = new DBContext();
			
			try 
			{
				TimeUnit.SECONDS.sleep(4);
				context.insertarNuevaSourceDB(selection.getName());
				
				context.getConn().close();
			} 
			catch (InterruptedException e2) 
			{
				e2.printStackTrace();
												
				
			} catch (SQLException e1) 
			{
				e1.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
				DBContext.borrarArchivoDirectorio(selection.getAbsolutePath());
			}				
			
			vistaMostrarImagenesSource();
		} 
		catch (IOException e1) 
		{
			e1.printStackTrace();
		}
	}
	
	public void analizarImagenTarget(File selection)
	{
		
			BufferedImage img = null;
			try {
				img = ImageIO.read(selection);
				Files.copy(Paths.get(selection.getAbsolutePath()), Paths.get(new File(DBContext.targetRoute + selection.getName()).getAbsolutePath()));
				if(!this.context.getConn().isClosed())
				{
					this.context.getConn().close();
					this.context.setConn(DriverManager.getConnection(context.getUrl()));
				}
				else
				{
					this.context.setConn(DriverManager.getConnection(context.getUrl()));
				}
			} catch (IOException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				TimeUnit.SECONDS.sleep(5);
			} catch (InterruptedException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

			User admin = new User();
			
		    ArrayList<ImageData> sources, target;
			try {
								
				target = context.obtainTargetNames(true);
				sources = context.obtainSourcesNames(true);
				
				
				System.out.println(sources.toArray().toString());
				System.out.println(target.toArray().toString());

				Float threshold = 80F;
				RecognitionSystem system = new RecognitionSystem();		
				
				
				system.analizarTarget(context, admin, sources, target.get(0), threshold);
				//Recortar subimagen y insertar en Unknown
				
				context.cargarImagenesNoEncontradas(img, system.getCarasNoEncontradas(), target.get(0));
				
				if(!context.getConn().isClosed())
				{
					context.getConn().close();
				}
				vistaMostrarAnalizar(img, system.getCarasEncontradas(), system.getCarasNoEncontradas());
				
				//Mover al historial
				DBContext.moveTargetToHistorial(target.get(0));
					
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
	}
	
	public void accionCambiar(boolean source)
	{
		removeAll();
		setLayout(new BorderLayout());
		PanelConfirmacion panel = new PanelConfirmacion();
		add(panel, BorderLayout.CENTER);
		
		repaint();
		revalidate();
		
		panel.getCambiar().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				accionCambiar(source);
			}
			
		});
		
		if(source)
		{
			panel.getAceptar().addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) 
				{
					insertarImagenSource(panel.getSeleccion());
				}
				
			});
		}
		else
		{
			panel.getAceptar().addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					analizarImagenTarget(panel.getSeleccion());
				}
			});
		}		
	}
	
	public void cargaImagenesTarget()
	{
		this.removeAll();
		setLayout(new BorderLayout());
		PanelCargaImagen panel = new PanelCargaImagen("Analizar imagen");
		panel.getCargaImagen().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				accionCambiar(false);	
			}
        });
		
		add(panel, BorderLayout.CENTER);
		repaint();
		revalidate();
	}
	
	public void cargaImagenesPanel(boolean source)
	{

		this.removeAll();
		setLayout(new GridBagLayout());
		
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        
        VistaimagenesPanel panel = new VistaimagenesPanel();	
        if(source)
        {
			try {
				Files.copy(Paths.get(panel.getFc().getSelectedFile().getAbsolutePath()), Paths.get(new File(DBContext.sourceRoute + panel.getFc().getSelectedFile().getName()).getAbsolutePath()));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
            	panel.getAceptar().addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						DBContext context = new DBContext();
						
						try {
							TimeUnit.SECONDS.sleep(2);
							context.insertarNuevaSourceDB(panel.getFc().getSelectedFile().getName());
							context.getConn().close();
						} catch (InterruptedException e2) {
							e2.printStackTrace();
							
							//Cerrando database al cargar imagenes
							
							
						} catch (SQLException e1) {
							e1.printStackTrace();
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						vistaMostrarImagenesSource();
					}
				});
            	
        }
        else
        {
			try {
				Files.copy(Paths.get(panel.getFc().getSelectedFile().getAbsolutePath()), Paths.get(new File(DBContext.targetRoute + panel.getFc().getSelectedFile().getName()).getAbsolutePath()));
			} catch (IOException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
            	panel.getAceptar().addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						DBContext context = new DBContext();
						
						try {
							TimeUnit.SECONDS.sleep(2);
						} catch (InterruptedException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}

						User admin = new User();
						
					    ArrayList<ImageData> sources, target;
						try {
							
							target = context.obtainTargetNames(true);
							sources = context.obtainSourcesNames(true);
							
							
							System.out.println(sources.toArray().toString());
							System.out.println(target.toArray().toString());

							Float threshold = 60F;
							RecognitionSystem system = new RecognitionSystem();		
							
							
							system.analizarTarget(context, admin, sources, target.get(0), threshold);
							//Recortar subimagen y insertar en Unknown
							
							context.cargarImagenesNoEncontradas(panel.getImg(), system.getCarasNoEncontradas(), target.get(0));
							
							if(!context.getConn().isClosed())
							{
								context.getConn().close();
							}
							vistaMostrarAnalizar(panel.getImg(), system.getCarasEncontradas(), system.getCarasNoEncontradas());
							
							//Mover al historial
							DBContext.moveTargetToHistorial(target.get(0));
								
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					    
					   
					}
				});
        }
        
        add(panel, gbc);
        repaint();	        
		revalidate();
	}
	
	public void vistaMostrarAnalizar(BufferedImage image, ArrayList<ImageData> encontrados, ArrayList<ImageData> noEncontrados)
	{
		this.removeAll();
        this.setPreferredSize(new Dimension(820, 800));
		setLayout(new BorderLayout());
        
        PanelResultado panel = new PanelResultado(image, encontrados, noEncontrados);	

        JScrollPane scrollpane = new JScrollPane(panel);
        add(scrollpane, BorderLayout.CENTER);
         
        repaint();
        panel.repaint();
        revalidate();
		
	}
	public void vistaMostrarConsulta()
	{
		this.removeAll();
		setLayout(new BorderLayout());
		PanelConsulta panel = new PanelConsulta();
		
		add(panel, BorderLayout.CENTER);
		
		repaint();
	}
	
	
	public void vistaAnalizarImagen()
	{
		this.removeAll();
		setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        
        PanelCargaImagen panel = new PanelCargaImagen("Analizar imagen");	
        gbc.weighty = 0;   

        add(panel, gbc);
		
		panel.getCargaImagen().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				removeAll();
				cargaImagenesPanel(false);
			}
		});
        
		repaint();
		revalidate();
	}
	
	public void vistaMostrarHistorial()
	{
		this.removeAll();
		setLayout(new BorderLayout());
        PanelMostrarHistorial panel = new PanelMostrarHistorial(context.consultarHistorialTarget());
        JScrollPane scrollPane = new JScrollPane(panel);
        add(scrollPane, BorderLayout.CENTER);
        repaint();	        
		revalidate();
	}
	
}
