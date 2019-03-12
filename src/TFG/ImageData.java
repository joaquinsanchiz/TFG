package TFG;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.amazonaws.services.rekognition.model.BoundingBox;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.util.IOUtils;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;

public class ImageData {
	
	private int id;
	private String name;
	private float x;
	private float y;
	private float width;
	private float height;
	
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public float getWidth() {
		return width;
	}
	public void setWidth(float width) {
		this.width = width;
	}
	public float getHeight() {
		return height;
	}
	public void setHeight(float height) {
		this.height = height;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	static ByteBuffer getImageBuffer(String targetImage)
	{
        ByteBuffer targetImageBytes = null;
		try (InputStream inputStream = new FileInputStream(new File(targetImage))) {
            targetImageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
        }
        catch(Exception e)
        {
            System.out.println("Failed to load target images: " + targetImage);
            System.exit(1);
        }
		return targetImageBytes;
		
	}
	
	public BufferedImage getImageBuffer(Image target)
	{
		BufferedImage image = null;
		InputStream imageBytesStream = new ByteArrayInputStream(target.getBytes().array());
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			image=ImageIO.read(imageBytesStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			ImageIO.write(image, "jpg", baos);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
	
	public int getExifOrientation()
	{
		ExifIFD0Directory directory = null;
		File imageFile = null;
		Metadata metadata = null;
	    try {
	    	
	    		imageFile = new File(DBContext.targetRoute + this.getName());
			metadata = ImageMetadataReader.readMetadata(imageFile);
	    }
	    catch(Exception e)
	    {
	    		try {
	    			
		    		imageFile = new File(DBContext.sourceRoute + this.getName());
				metadata = ImageMetadataReader.readMetadata(imageFile);
	    		}
	    		catch(Exception e2)
	    		{
		    		imageFile = new File(DBContext.unknownRoute + this.getName());
		    		try
		    		{
					metadata = ImageMetadataReader.readMetadata(imageFile);
		    		}
		    		catch (ImageProcessingException e3) {
		    			e.printStackTrace();
		    			return 0;
		    		} catch (IOException e4) {
		    			e.printStackTrace();
		    			return 0;
		    		}
	    		}
	    }
	    	
		    directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
		    //System.out.println("Orientacion EXIF: " + directory.TAG_ORIENTATION);
			
		 
	    
	    return directory.TAG_ORIENTATION;
	}
	
	public void setFaceMark(Image target, BoundingBox box, String rotation) 
	{
		//System.out.println("-----| Creando marco de cara ");
		
		BufferedImage image = this.getImageBuffer(target);
		
		int imageHeight = image.getHeight();
		int imageWidth = image.getWidth();
				
		float leftFinal = 0;
		float topFinal = 0;
		
		if(rotation == null)
		{
			//System.out.println("Rotation == null");
			int orientation = this.getExifOrientation();
			
		    if(orientation < 90)
		    {
		    		leftFinal = imageWidth * box.getLeft();
				topFinal = imageHeight * box.getTop();
		    }
		    else if(orientation >= 90 && orientation < 180)
		    {
		    		leftFinal = imageHeight * (1 - (box.getTop() + box.getHeight()));
				topFinal = imageWidth * box.getLeft();
		    }
		    else if(orientation >= 180 && orientation < 270 )
		    {
		    		leftFinal = imageWidth - (imageWidth * (box.getLeft() + box.getWidth()));
				topFinal = imageHeight * (1 - (box.getTop() + box.getHeight()));
		    }
		    else 
		    {
		    		//leftFinal = imageHeight * box.getTop();
				//topFinal = imageWidth * (1 - box.getLeft() - box.getWidth());
		    		leftFinal = imageWidth * box.getLeft();
				topFinal = imageHeight * box.getTop();
		    }

		}
		else 
		{
			switch (rotation) {
			case "ROTATE_0":
				leftFinal = imageWidth * box.getLeft();
				topFinal = imageHeight * box.getTop();
				break;
			case "ROTATE_90":
				leftFinal = imageHeight * (1 - (box.getTop() + box.getHeight()));
				topFinal = imageWidth * box.getLeft();
				break;
			case "ROTATE_180":
				leftFinal = imageWidth - (imageWidth * (box.getLeft() + box.getWidth()));
				topFinal = imageHeight * (1 - (box.getTop() + box.getHeight()));
				break;
			case "ROTATE_270":
				leftFinal = imageHeight * box.getTop();
				topFinal = imageWidth * (1 - box.getLeft() - box.getWidth());
				break;
			default:
				System.out.println("-----| No estimated orientation information. Check Exif data.");
				return;
			  }
		}
				
		
		//System.out.println("----Left: " + leftFinal + ", Top: " + topFinal + ", Width: " + box.getWidth() + ", Height: " + box.getHeight());
		this.setX(leftFinal);this.setY(topFinal);this.setWidth(box.getWidth() * imageWidth);this.setHeight(box.getHeight() * imageHeight);
		
	}
	
	@Override
	public String toString() {
		
		String cadena = "";
		cadena += "Nombre: " + this.getName() + ", ID: " + this.getId() + "x: " + this.getX() + ", y: " + this.getY();		
		return cadena;
		
	}
	
	@Override
    public boolean equals(Object object)
    {

        if (object != null && object instanceof ImageData)
        {
        		ImageData rhs = (ImageData) object;
        	
            if(id == rhs.id && x == rhs.x && y == rhs.y 
            		&& height == rhs.height && width == rhs.width)
            {
            		return true;
            }
        }

        return false;
    }
	
	@Override
	public int hashCode() {
	    return (int) this.x;
	}
	
	public ImageData(ImageData rhs)
	{
		this.id = rhs.id;
		this.height = rhs.height;
		this.width = rhs.width;
		this.name = rhs.name;
		this.x = rhs.x;
		this.y = rhs.y;
	}
	public ImageData()
	{
		
	}
	public ImageData(String nombre, int id)
	{
		this.setName(nombre);
		this.setId(id);
	}
	
	public static void recortarImagen(BufferedImage imagen, ArrayList<ImageData> noEncontrados)
	{
		//File carpetaUnknown = new File(DBContext.unknownRoute);
		DBContext context = new DBContext();
		int nImagenes = 0;
		try {
			nImagenes = context.getConn().createStatement().executeQuery("select count(*) from Images;").getInt(1);
			if(!context.getConn().isClosed())
			{
				context.getConn().close();
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//int nImagenes = carpetaUnknown.listFiles().length;
		for(int i = 0; i < noEncontrados.size(); i++)
		{
			int x = (int) noEncontrados.get(i).getX();
			int y = (int) noEncontrados.get(i).getY();
			int width = (int) noEncontrados.get(i).getWidth();
			int height = (int) noEncontrados.get(i).getHeight();
			noEncontrados.get(i).setName("Unknown" + (i + (nImagenes - 1) ) + ".jpg");
					
			try {
				System.out.println("Fuera y: " + y);
				
				System.out.println("Fuera x: " + x);
				if((y + height) > imagen.getHeight())
				{
					System.out.println("Entra aqui y: " + y);
					y = y - ((y + height) - imagen.getHeight()) - 1;
					
					System.out.println("Y ahora: " + (y + height) + " vs height " + imagen.getHeight());
				}
				if((x + width) > imagen.getWidth())
				{
					System.out.println("Entra aqui y: " + y);
					x = x - ((x + width) - imagen.getWidth()) - 1;
					
					System.out.println("Y ahora: " + (x + width) + " vs width " + imagen.getHeight());
				}
				if (y < 1)
				{
					y=1;
				}
				if (x < 1)
				{
					x=1;
				}
				BufferedImage subimage = imagen.getSubimage(x, y, width, height);
				ImageIO.write(subimage, "jpg", new File(DBContext.validarRoute + noEncontrados.get(i).getName()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public static void cambiarValidarUnknown(String name)
	{
		System.out.println("Cambiando " + name + " de Validar a Unknown");
		try {
			Files.move(Paths.get(DBContext.validarRoute + name), Paths.get(new File(DBContext.unknownRoute + name).getAbsolutePath()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				Files.move(Paths.get(DBContext.validarRoute + name), Paths.get(new File(DBContext.unknownRoute + name.substring(0, name.length() - 4) + "-2.jpg").getAbsolutePath()));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
	}
	
	public static void cambiarUnknownSource(String name, String newname)
	{
		System.out.println("Cambiando " + name + " de Unknown a Source");
		try {
			Files.move(Paths.get(DBContext.unknownRoute + name), Paths.get(new File(DBContext.sourceRoute + newname).getAbsolutePath()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				Files.move(Paths.get(DBContext.unknownRoute + name), Paths.get(new File(DBContext.sourceRoute + newname.substring(0, newname.length() - 4) + "-2.jpg").getAbsolutePath()));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
	}
	
	public static void eliminarValidar(String name)
	{
		System.out.println("Eliminando " + name + " de Validar");

		try {
			Files.delete(Paths.get(DBContext.validarRoute + name));
		}
		catch(IOException e)
		{
			
		}
	}
	
	public static void eliminarUnknown(String name)
	{
		System.out.println("Eliminando " + name + " de Unknown");
		try {
			Files.delete(Paths.get(DBContext.unknownRoute + name));
		}
		catch(IOException e)
		{
			
		}
	}
	
	public void setProperties(float x, float y, float width, float height)
	{
		this.setX(x);
		this.setY(y);
		this.setWidth(width);
		this.setHeight(height);
	}
	public void setProperties(ImageData rhs)
	{
		this.setProperties(rhs.x, rhs.y, rhs.width, rhs.height);
	}
	

}
