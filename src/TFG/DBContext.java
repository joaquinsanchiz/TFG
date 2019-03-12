package TFG;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.xml.ws.Response;

import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DBContext {

    private String url = "jdbc:sqlite:/Users/joaquinsanchiz/Pictures/digikam4.db";
    public static String sourceRoute = "/Users/joaquinsanchiz/Pictures/TFG/Source/";
    public static String historialRoute = "/Users/joaquinsanchiz/Pictures/TFG/Historial/";
    public static String targetRoute = "/Users/joaquinsanchiz/Pictures/TFG/Target/";
    static String unknownRoute = "/Users/joaquinsanchiz/Pictures/TFG/Unknown/";
    static String validarRoute = "/Users/joaquinsanchiz/Pictures/TFG/Validar/";

    private Connection conn = null;
    private File carpetaSource = new File("/Users/joaquinsanchiz/Pictures/TFG/Source");
    private File carpetaTarget = new File("/Users/joaquinsanchiz/Pictures/TFG/Target");
    
    public void limpiarDB()
    {
		try {
			
			this.getConn().createStatement().executeQuery("delete from Images;");
			this.getConn().createStatement().executeQuery("delete from Imagetagproperties;");
			this.getConn().createStatement().executeQuery("delete from Imagetags;");
			this.getConn().createStatement().executeQuery("delete from Tags;");
			
			ResultSet response = this.getConn().createStatement().executeQuery("select * from Images;");
			DBContext.pintarRespuesta(response);
			
			response = this.getConn().createStatement().executeQuery("select * from Imagetagproperties;");
			DBContext.pintarRespuesta(response);

			response = this.getConn().createStatement().executeQuery("select * from Imagetags;");
			DBContext.pintarRespuesta(response);
			
			response = this.getConn().createStatement().executeQuery("select * from Tags;");
			DBContext.pintarRespuesta(response);


		} catch (SQLException e) {
			e.printStackTrace();	
			try {
				
				ResultSet response = this.getConn().createStatement().executeQuery("select * from Images;");
				DBContext.pintarRespuesta(response);
				
				this.getConn().createStatement().executeQuery("delete from Imagetagproperties;");
			} catch (SQLException e1) {
				e1.printStackTrace();
				try {
					
					ResultSet response = this.getConn().createStatement().executeQuery("select * from Imagetagproperties;");
					DBContext.pintarRespuesta(response);
					
					this.getConn().createStatement().executeQuery("delete from Imagetags;");
				} catch (SQLException e2) {
					e2.printStackTrace();
					try {
						ResultSet response = this.getConn().createStatement().executeQuery("select * from Imagetags;");
						DBContext.pintarRespuesta(response);
						
						this.getConn().createStatement().executeQuery("delete from Tags;");
					} catch (SQLException e3) {
						ResultSet response;					
						e3.printStackTrace();
						try {
							response = this.getConn().createStatement().executeQuery("select * from Tags;");
							DBContext.pintarRespuesta(response);
						} catch (SQLException e4) {
							// TODO Auto-generated catch block
							e4.printStackTrace();
						}
					}

				}

			}

		}

    }
    
    public DBContext() 
    {
        try {
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			System.err.println("Error");
			e.printStackTrace();
		}
        
    }

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public String getSourceRoute() {
		return sourceRoute;
	}
	public String getHistorialRoute() {
		return historialRoute;
	}

	public void setSourceRoute(String sourceRoute) {
		this.sourceRoute = sourceRoute;
	}

	public String getTargetRoute() {
		return targetRoute;
	}

	public void setTargetRoute(String targetRoute) {
		this.targetRoute = targetRoute;
	}
    
    public ArrayList<ImageData> obtainTargetNames(boolean log) throws SQLException
    {
    		ArrayList<ImageData> temp = new ArrayList<ImageData>();
		ResultSet response = this.getConn().createStatement().executeQuery("select * from Images where album=4;");
		while(response.next()) 
		{
			ImageData dummy = new ImageData();
			dummy.setId(Integer.parseInt(response.getString(1)));
			dummy.setName(response.getString(3));
			temp.add(dummy);
			
			if(log)
			{
				System.out.print("Target->");
				System.out.println(dummy);
			}
		}
		return temp;
    }
    public ArrayList<ImageData> obtainUnknownNames(boolean log)
    {
    		ArrayList<ImageData> temp = new ArrayList<ImageData>();
		ResultSet response;
		try {
			response = this.getConn().createStatement().executeQuery("select * from Images where album=5;");
			while(response.next()) 
			{
				ImageData dummy = new ImageData();
				dummy.setId(Integer.parseInt(response.getString(1)));
				dummy.setName(response.getString(3));
				temp.add(dummy);
				
				if(log)
				{
					System.out.print("Unknown->");
					System.out.println(dummy);
				}
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return temp;
    }
    
    public void sleep(int segundos)
    {
    	try {
			TimeUnit.SECONDS.sleep(segundos);
		} catch (InterruptedException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
    }
    
    public ArrayList<ImageData> obtainSourcesNames(boolean log)
    {
    		ArrayList<ImageData> temp = new ArrayList<ImageData>();
    		ResultSet response;
			try {
				response = this.getConn().createStatement().executeQuery("select * from Images where album=3;");
				while(response.next()) 
	    		{
	    			
	    			ImageData dummy = new ImageData();
	    			dummy.setId(Integer.parseInt(response.getString(1)));
	    			dummy.setName(response.getString(3));
	    			temp.add(dummy);
	    			
	    			if(log)
	    			{
	    				System.out.print("Source->");
	    				System.out.println(dummy);
	    			}
	    		}
	    		return temp; 
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		return temp; 
    		
    }
    
    public int obtainImageID(String name)
    {
		try {
			if(this.getConn().isClosed())
			{
				this.setConn(DriverManager.getConnection(url));
			}
			ResultSet response = this.getConn().createStatement().executeQuery("select * from Images where name='" + name +"';");
			int id = 0;
			while(response.next()) 
			{
				System.out.println(response.getMetaData().getColumnName(1));	   
				id = Integer.parseInt(response.getString(1));
				System.out.println(id);
				
			}
			return id;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;

    }
    
    public void addTagProperties(ImageData target, int tagID)
    {
    		System.out.println("----| -Añadiendo propiedades de imagen " + target.getName() + " ->ImagenID: " + target.getId() + ", tagID: " + tagID);
    		String value = "<rect x=\"" + target.getX() + "\" y=\"" + target.getY() + "\" width=\"" + target.getWidth() + "\" height=\"" + target.getHeight() + "\"/>";    		
		try 
		{
			String query = "insert into Imagetagproperties (imageid, tagid, property, value) values ('" + target.getId() + "', '" + tagID + "', 'tagRegion', '" + value + "');";
			this.getConn().createStatement().executeQuery(query);
			
		} 
		catch (SQLException e) 
		{
			ResultSet response;
			try {
				response = this.getConn().createStatement().executeQuery("select * from Imagetagproperties where imageid=" + target.getId() + ";");
				DBContext.pintarRespuesta(response);
			} catch (SQLException e1) {
				
			}
		}		
		
    }
    
    public int lookForTag(int imageID) 
    {
    		ResultSet response;
    		String id = null;
			try {
				if(this.getConn().isClosed())
				{
					this.setConn(DriverManager.getConnection(url));
				}
				else
				{
					this.conn.close();
					this.setConn(DriverManager.getConnection(url));
				}
				response = this.getConn().createStatement().executeQuery("select * from Imagetags where imageid=" + imageID + ";");
	    			id = response.getString(2);

			} catch (SQLException e) {
				e.printStackTrace();
			}
    		/*if(response == null)
    		{
    			System.out.println("No hay tag");
    			return 0;
    		}*/
    		return Integer.parseInt(id);
    }
    
    public void deleteTag(int tagID) throws SQLException
    {
		this.getConn().createStatement().executeQuery("delete from Tags where id=" + tagID + ";");
    }
    
    public void asignarIdentificacionEnSource(ImageData source, ImageData target)
    {
    	
    		this.asignarTagAImagen(target.getId(), source.getId() + 1000);
    		this.addTagProperties(target, source.getId() + 1000);
    		
    }
    
    public void cargarImagenesNoEncontradas(BufferedImage img, ArrayList<ImageData> noEncontradas, ImageData target)
    {
		ImageData.recortarImagen(img, noEncontradas);
    		try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
    		for(int i = 0; i < noEncontradas.size(); i++)
    		{
    			this.insertarNuevaUnknownDB(noEncontradas.get(i), target);
    		}
    }
    
    
    public void linkImageTag(int tagID, int imageID)
    {
		System.out.println("------| Linqueando imagen " + imageID + " a tagID " + tagID);
		try {
			this.getConn().createStatement().executeQuery("insert into Imagetags (imageid, tagid) values ('" + imageID + "', '" + tagID + "');");
		} catch (SQLException e) {
			System.out.println("Informacion de etiqueta ya asignada");
			e.printStackTrace();
		}
		
		System.out.println("Comprobando insercion de etiqueta");
		ResultSet response;
		try {
			response = this.getConn().createStatement().executeQuery("select * from Imagetags where imageid=" + imageID + ";");
			while(response.next()) 
			{
				for(int i = 0; i < response.getMetaData().getColumnCount(); i++) {
					System.out.println(response.getMetaData().getColumnName(i+1) + ": " + response.getString(i+1));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
    }
    
    public static void moveTargetToHistorial(ImageData target) {
    		System.out.println("Moviendo imagen de target a historial: " + target.getId() + ", " + target.getName());
        try 
        {
        	
        		System.out.println(targetRoute + target.getName());
        		System.out.println(historialRoute);
        		
			Files.move(Paths.get(targetRoute + target.getName()), Paths.get(new File(historialRoute + target.getName()).getAbsolutePath()));
		} catch (IOException e) 
        {
			e.printStackTrace();
		}
    		
    		   		
    }
    
    public static void borrarArchivoDirectorio(String direccion) {

        File directorio = new File(direccion);
        File f;
        if (directorio.isDirectory()) {
            String[] files = directorio.list();
            if (files.length > 0) {
                System.out.println(" Directorio vacio: " + direccion);
                for (String archivo : files) {
                    System.out.println(archivo);
                    f = new File(direccion + File.separator + archivo);

                    System.out.println("Borrado:" + archivo);
                    f.delete();
                    f.deleteOnExit();

                }
            }
        }

    }
    
    public void setTag(String name, int tagID) throws SQLException
    {
    		System.out.println("Creando tag ->Name: " + name + ", tagID: " + tagID);
    		this.getConn().createStatement().executeQuery("insert into Tags (id, pid, name) values ('" + tagID + "', '23', '" + name + "');");
    		
    		System.out.println("Comprobando insercion de etiqueta");
    		ResultSet response = this.getConn().createStatement().executeQuery("select * from Tags where id=" + tagID + ";");
    		while(response.next()) 
    		{
    			for(int i = 0; i < response.getMetaData().getColumnCount(); i++) {
    				System.out.println(response.getMetaData().getColumnName(i+1) + ": " + response.getString(i+1));
    			}
    		}
    		
    }

	public File getCarpetaSource() {
		return carpetaSource;
	}

	public void setCarpetaSource(File carpetaSource) {
		this.carpetaSource = carpetaSource;
	}

	public File getCarpetaTarget() {
		return carpetaTarget;
	}

	public void setCarpetaTarget(File carpetaTarget) {
		this.carpetaTarget = carpetaTarget;
	}
	
	public int insertarImagenDigiKam(String name)
	{
		try {
			int id = this.obtainImageID(name);
			
			if(id == 0)
			{
				ResultSet response = this.getConn().prepareStatement("select count(*) from Images").executeQuery();
				int size = 0;
				if (response != null) 
				{
					size = Integer.parseInt(response.getString(1));
				}
				size++;
				System.out.println(size);
				this.getConn().prepareStatement("insert into Images (id, album, name, status, category, modificationDate, fileSize, uniqueHash) values ('" + size + "', '" + 3 + "', '" + name + "', '1', '1', '2019-02-11T22:40:10', '5000', '" + name + "');").executeQuery();
				return size;
			}
			return id;		
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			try 
			{
				ResultSet response = this.getConn().prepareStatement("select * from Images").executeQuery();
				this.pintarRespuesta(response);
			} 
			catch (SQLException e1) 
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		return 0;
	}
	public void insertarNuevaSourceDB(String name) throws Exception
	{
		try 
		{
			int tagID = 0;
			if(!this.getConn().isClosed())
			{
				this.getConn().close();
				this.setConn(DriverManager.getConnection(url));
			}
			else
			{
				this.setConn(DriverManager.getConnection(url));
			}
			
			this.sleep(3);
			int imageID = this.obtainImageID(name);
			if(imageID == 0)
			{
				System.err.println("ID ES 0 PROBLEMA");
				imageID = this.obtainImageID(name);
				
				if(imageID==0)
				throw new Exception("ID 0");
			}
			ImageData source = new ImageData(name, imageID);
			ArrayList<ImageData> unkowns = this.obtainUnknownNames(true);
			
			Float threshold = 60F;
			RecognitionSystem system = new RecognitionSystem();
			
			User admin = new User();
			system.analizarSource(this, admin, unkowns, source, threshold);	
			if(system.getCarasEncontradas().size() != 0)
			{
				System.out.println("Encontrada coincidencia");
				
				ImageData perfil = system.getCarasEncontradas().get(0);
				int matchId = this.obtainImageID(perfil.getName());
				
				System.out.println("Perfil actual: " + name + ", id: " + imageID);
				System.out.println("Perfil del Unknown: " + perfil.getName() + ", id: " + matchId);
				
				ImageData.eliminarUnknown(perfil.getName());
				
				System.out.println("Comprobando que hay tags");
				
				ResultSet probando = this.getConn().createStatement().executeQuery("select * from Imagetags where imageid='" + matchId + "';");
				this.pintarRespuesta(probando);
				
				this.getConn().createStatement().executeQuery("update Imagetags set imageid='" + imageID + "' where imageid='" + matchId +"';");
				
			}
			else
			{
				if(imageID != 0)
				{
					tagID = this.crearTagIdentificativa(imageID, name);
				}
				if(tagID != 0)
				{
					boolean confirmado = this.asignarTagAImagen(imageID, tagID);		
					ResultSet probando = this.getConn().createStatement().executeQuery("select * from Imagetagproperties where imageid='" + imageID + "';");
					System.out.println("Pintando asignacion de marco facial de unknown" + imageID);
					this.pintarRespuesta(probando);
				}
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}

	}
	
	public void insertarNuevaUnknownDB(ImageData toInsert, ImageData target)
	{
		try 
		{
			int tagID = 0;
			if(!this.getConn().isClosed())
			{
				this.getConn().close();
				this.setConn(DriverManager.getConnection(url));
			}
			else
			{
				this.setConn(DriverManager.getConnection(url));
			}
			
			this.sleep(3);
			
			int imageID = this.obtainImageID(toInsert.getName());
			ImageData source = new ImageData(toInsert.getName(), imageID);
			ArrayList<ImageData> unkowns = this.obtainUnknownNames(true);
			
			Float threshold = 60F;
			RecognitionSystem system = new RecognitionSystem();		
			
			User admin = new User();
			system.analizarUnknown(this, admin, unkowns, source, threshold);	
			if(system.getCarasEncontradas().size() != 0)
			{
				System.out.println("Encontrada coincidencia");
				
				ImageData perfil = system.getCarasEncontradas().get(0);
				int matchId = this.obtainImageID(perfil.getName());
				
				System.out.println("Perfil actual: " + toInsert.getName() + ", id: " + imageID);
				System.out.println("Perfil del Unknown: " + perfil.getName() + ", id: " + matchId);
				
				/*
				 * Insertar en la tabla la informacion de imagetagproperties
				 */
				
				int matchTagId = this.lookForTag(matchId);
				System.out.println("Asignando tag de match: " + matchTagId);
				this.addTagProperties(target, matchTagId);
				
				ImageData.eliminarValidar(toInsert.getName());
				
				//this.getConn().createStatement().executeQuery("update Imagetags set imageid='" + imageID + "' where imageid='" + matchId +"';");
								
			}
			else
			{
				if(imageID != 0)
				{
					tagID = this.crearTagIdentificativa(imageID, toInsert.getName());
				}
				if(tagID != 0)
				{
					target.setProperties(toInsert);
					this.asignarTagAImagen(imageID, tagID);
					this.addTagProperties(target, tagID);

					ResultSet probando = this.getConn().createStatement().executeQuery("select * from Imagetagproperties where imageid='" + imageID + "';");
					System.out.println("Pintando asignacion de marco facial de unknown " + imageID);
					this.pintarRespuesta(probando);
				}
				
				
				ImageData.cambiarValidarUnknown(toInsert.getName());
			}
		} 
		catch (SQLException e) 
		{
			System.out.println("Update ejecutada.");
		}
		

	}
	
	public boolean asignarTagAImagen(int imageId, int tagId)
	{
		System.out.println("Asignando Tag personal " + tagId + " a imagen " + imageId);
		try {
			conn.createStatement().executeQuery("insert into Imagetags (imageid, tagid) values ('" + imageId + "', '" + tagId + "');");
			return true;
		} catch (SQLException e) {
			try {
				ResultSet response = this.getConn().createStatement().executeQuery("select* from Imagetags where imageid='" + imageId +"';");
				System.out.println("Pintando asignacion de imageid " + imageId);
				this.pintarRespuesta(response);
				
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return true;
		}

	}
	
	public static void pintarRespuesta(ResultSet response)
	{
		try {
			while(response.next())
			{
				for(int i = 0; i < response.getMetaData().getColumnCount(); i++) {
					System.out.println(response.getMetaData().getColumnName(i+1) + ": " + response.getString(i+1));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int crearTagIdentificativa(int id, String name)
	{
		int tagID = id + 1000;
		System.out.println("Creando tag identificativa para id " + id + "->Name: " + name + ", tagID: " + tagID);
		try {
			this.getConn().createStatement().executeQuery("insert into Tags (id, pid, name) values ('" + tagID + "', '23', '" + name + "');");
			System.out.println("Insertado con exito");
			return tagID;
		} catch (SQLException e) {
			try {
				ResultSet response = this.getConn().createStatement().executeQuery("select id from Tags where name='" + name +"';");
				DBContext.pintarRespuesta(response);			
				id = Integer.parseInt(response.getString(1));
				return id;
				
			} catch (SQLException e1) {
				ResultSet response;
				try {
					response = this.getConn().createStatement().executeQuery("select id from Tags where name='" + name +"';");
					id = Integer.parseInt(response.getString(1));
					System.out.println("Devolviendo el primero: " + id);
					return id;
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					System.out.println("Devolviendo trabado: " + id);
					return id;
				}
			}
		}

	}
	
	public ArrayList<JLabel> consultarHistorialTarget()
	{
		ArrayList<JLabel> resultado = new ArrayList<JLabel>();
		String directory = DBContext.historialRoute.substring(0, DBContext.historialRoute.length() - 1);
        
        File dir = new File(directory);
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) 
        {
          for (File child : directoryListing) 
          {
        	  
  			BufferedImage img;
  			if(child.getName().charAt(0) != '.')
  			try {
				img = ImageIO.read(child);
				JLabel label = new JLabel();
				Image dimg = img.getScaledInstance(200, -1, Image.SCALE_FAST);
				ImageIcon imageIcon = new ImageIcon(dimg);			
				label.setIcon(imageIcon);		
				resultado.add(label);
						
			} catch (IOException e) {
				e.printStackTrace();
			}
          }
        }
        try {
			if(!this.getConn().isClosed())
			{
				this.getConn().close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return resultado;
	}
	
	public ArrayList<JLabel> consultarImagenesUnknown()
	{
		ArrayList<JLabel> resultado = new ArrayList<JLabel>();
		String directory = DBContext.unknownRoute.substring(0, DBContext.unknownRoute.length() - 1);
        System.out.println(directory);
        
        File dir = new File(directory);
        File[] directoryListing = dir.listFiles();
        System.out.println(dir.listFiles().length);
        if (directoryListing != null) {
          for (File child : directoryListing) 
          {
        	  
  			BufferedImage img;
  			if(child.getName().charAt(0) != '.')
  			try 
  			{
  				System.out.println(child.getName());
  				System.out.println(child.getPath());
				img = ImageIO.read(child);
				JLabel label = new JLabel();
				Image dimg = img.getScaledInstance(200, -1, Image.SCALE_FAST);
				ImageIcon imageIcon = new ImageIcon(dimg);			
				label.setIcon(imageIcon);
				label.setText(child.getName().substring(0, child.getName().length() - 4));
				label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				System.out.println(label.getText());
				resultado.add(label);	
				
			} 
  			catch (IOException e) 
  			{
				e.printStackTrace();
			}
          }
        }
        try {
			if(!this.getConn().isClosed())
			{
				this.getConn().close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return resultado;
	}
	
	public ArrayList<JLabel> consultarImagenesSource()
	{
		ArrayList<JLabel> resultado = new ArrayList<JLabel>();
		String directory = DBContext.sourceRoute.substring(0, DBContext.sourceRoute.length() - 1);
        System.out.println(directory);
        
        File dir = new File(directory);
        File[] directoryListing = dir.listFiles();
        System.out.println(dir.listFiles().length);
        if (directoryListing != null) {
          for (File child : directoryListing) 
          {
        	  
  			BufferedImage img;
  			if(child.getName().charAt(0) != '.')
  			try 
  			{
  				System.out.println(child.getName());
  				System.out.println(child.getPath());
				img = ImageIO.read(child);
				JLabel label = new JLabel();
				Image dimg = img.getScaledInstance(200, -1, Image.SCALE_FAST);
				ImageIcon imageIcon = new ImageIcon(dimg);			
				label.setIcon(imageIcon);		
				resultado.add(label);	
				
			} 
  			catch (IOException e) 
  			{
				e.printStackTrace();
			}
          }
        }
        try {
			if(!this.getConn().isClosed())
			{
				this.getConn().close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return resultado;
	}
	
	
	
	public ArrayList<JLabel> consultarDonde(String name)
	{
		int imageid;
		ResultSet temp;
		ArrayList<JLabel> resultado = new ArrayList<JLabel>();
		try {
			
			imageid = this.obtainImageID(name);
			
			temp = this.getConn().createStatement().executeQuery("select tagid from Imagetags where imageid='" + imageid + "';");
			int tagid = 0;
			while(temp.next())
			{
				tagid = Integer.parseInt(temp.getString(1));
			}
						
			ResultSet ids = this.getConn().createStatement().executeQuery("select imageid from Imagetagproperties where tagid='" + tagid + "';");
			
			
			List<String> nombres = new ArrayList<String>();
			
			while(ids.next())
			{
				int id = Integer.parseInt(ids.getString(1));
				String nameImage = this.getHistorialRoute() + this.getConn().createStatement().executeQuery("select name from Images where id='" + id + "';").getString(1);
				if(!nameImage.contains(name) && !nombres.contains(nameImage)) 
				{
					try
					{
						BufferedImage img = ImageIO.read(new File(nameImage));
						nombres.add(nameImage);

						Image resized = img.getScaledInstance(200, -1, Image.SCALE_FAST);
						ImageIcon icono = new ImageIcon(resized);
						
						JLabel tempImageLabel = new JLabel();
						tempImageLabel.setIcon(icono);
						
						resultado.add(tempImageLabel);

					}
					catch(Exception e)
					{
						
					}
					
				}

			}
			
			this.getConn().close();
			return resultado;
			
		} catch (NumberFormatException | SQLException e) {
			e.printStackTrace();

		} 
		return resultado;
		
	}
	
	public ArrayList<JLabel> consultarConQuien(String name)
	{
		int imageid;
		ArrayList<JLabel> resultado = new ArrayList<JLabel>();
		try {
			
			ResultSet temp = this.getConn().createStatement().executeQuery("select * from Images where name='" + name + "';"); //Obtengo id
			imageid = Integer.parseInt(temp.getString(1));
			
			temp = this.getConn().createStatement().executeQuery("select tagid from Imagetags where imageid='" + imageid + "';"); //Obtengo tagid
			int tagid = 0;
			while(temp.next())
			{
				tagid = Integer.parseInt(temp.getString(1));
			}
			
			//temp = this.getConn().createStatement().executeQuery("select imageid from Imagetags where tagid='" + tagid + "';");
			temp = this.getConn().createStatement().executeQuery("select imageid from Imagetagproperties where tagid='" + tagid + "';");

			List<String> nombres = new ArrayList<String>();
			
			while(temp.next())
			{
				int id = Integer.parseInt(temp.getString(1));
				ResultSet dummy = this.getConn().createStatement().executeQuery("select distinct tagid from Imagetagproperties where imageid='" + id + "';");
				while(dummy.next())
				{
					
					int tagfound = this.getConn().createStatement().executeQuery("select distinct imageid from Imagetags where tagid='" + dummy.getInt(1) + "';").getInt(1);
					
					if(tagfound != imageid)
					{
						ResultSet dummy2 = this.getConn().createStatement().executeQuery("select distinct name from Images where id='" + tagfound + "' and album=3;");
						if(!dummy2.isClosed())
						{
							String nameImage = DBContext.sourceRoute + dummy2.getString(1);
							
							if(!nombres.contains(nameImage)) 
							{
								nombres.add(nameImage);
								BufferedImage img = ImageIO.read(new File(nameImage));
								Image resized = img.getScaledInstance(200, -1, Image.SCALE_FAST);
								ImageIcon icono = new ImageIcon(resized);
								
								JLabel tempImageLabel = new JLabel();
								tempImageLabel.setIcon(icono);
								
								resultado.add(tempImageLabel);
								
							}
						}
						
					}
					
					
				}
				
			}
			
			if(!this.getConn().isClosed())
			{
				this.getConn().close();
			}
			
			return resultado;
			
		} catch (NumberFormatException | SQLException e) {
			e.printStackTrace();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resultado;
	}
    
}
