package TFG;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.CompareFacesMatch;
import com.amazonaws.services.rekognition.model.CompareFacesRequest;
import com.amazonaws.services.rekognition.model.CompareFacesResult;
import com.amazonaws.services.rekognition.model.ComparedFace;


public class RecognitionSystem {
	
	private ArrayList<ImageData> carasEncontradas;
	private ArrayList<ImageData> carasNoEncontradas;
	
	public ArrayList<ImageData> getCarasEncontradas() {
		return carasEncontradas;
	}

	public void setCarasEncontradas(ArrayList<ImageData> carasEncontradas) {
		this.carasEncontradas = carasEncontradas;
	}

	public ArrayList<ImageData> getCarasNoEncontradas() {
		return carasNoEncontradas;
	}

	public void setCarasNoEncontradas(ArrayList<ImageData> carasNoEncontradas) {
		this.carasNoEncontradas = carasNoEncontradas;
	}
	
	public RecognitionSystem() 
	{		
		
	}
	
	public void analizarSource(DBContext context, User admin, ArrayList<ImageData> sources, ImageData target, Float similarityThreshold) throws SQLException
	{
		this.carasEncontradas = new ArrayList<ImageData>();
		this.carasNoEncontradas = new ArrayList<ImageData>();
		String url = context.getSourceRoute() + target.getName();		
		
		Image targetImage = new Image().withBytes(ImageData.getImageBuffer(url));
		
		float t = System.currentTimeMillis();
		for(int i = 0; i < sources.size(); i++)
		{
			System.out.println("COMPARANDO con " + sources.get(i).getName());
			String sourcerl = null;
			
			System.out.println("Es analisis source");
			sourcerl = DBContext.unknownRoute + sources.get(i).getName();
			
			Image sourceImage = new Image().withBytes(ImageData.getImageBuffer(sourcerl));
			CompareFacesRequest request = new CompareFacesRequest().withSourceImage(sourceImage).withTargetImage(targetImage).withSimilarityThreshold(similarityThreshold);
			CompareFacesResult result = null;
			try 
			{
				result = admin.getRekognitionClient().compareFaces(request);
			}
			catch(Exception e)
			{
				System.err.println("Imposible comparar rostros.");
			}
			
			if(result != null)
			{
				List<CompareFacesMatch> matches = result.getFaceMatches();

				if(matches.size() == 1)
				{
					System.out.println("Encontrado " + sources.get(i).getName());   
					
	        			ImageData dummy = new ImageData(target);
	    				dummy.setName(sources.get(i).getName());
	        			this.getCarasEncontradas().add(dummy);      			
	    				break;

				}
				
			}
									
		}
		
		float t2 = System.currentTimeMillis();
		System.err.println("RENDIMIENTO DE RECONOCIMIENTO UNKNOWN PARA " + sources.size() + ": " + (t2-t) + " MILISEGUNDOS." );

	}
	
	public void analizarTarget(DBContext context, User admin, ArrayList<ImageData> sources, ImageData target, Float similarityThreshold) throws SQLException
	{
		this.carasEncontradas = new ArrayList<ImageData>();
		this.carasNoEncontradas = new ArrayList<ImageData>();
		String url = context.getTargetRoute() + target.getName();	
		
		Image targetImage = new Image().withBytes(ImageData.getImageBuffer(url));
		
		for(int i = 0; i < sources.size(); i++)
		{
			System.out.println("COMPARANDO con " + sources.get(i).getName());
			String sourcerl = null;
			
			System.out.println("Es analisis target");
			sourcerl = context.getSourceRoute() + sources.get(i).getName();	
			
			Image sourceImage = new Image().withBytes(ImageData.getImageBuffer(sourcerl));
			CompareFacesRequest request = new CompareFacesRequest().withSourceImage(sourceImage).withTargetImage(targetImage).withSimilarityThreshold(similarityThreshold);
			CompareFacesResult result = null;
			long t = System.currentTimeMillis();
			try 
			{
				result = admin.getRekognitionClient().compareFaces(request);
			}
			catch(Exception e)
			{
				System.err.println("Imposible comparar rostros.");
			}
			long t2 = System.currentTimeMillis();
			
			System.err.println("RENDIMIENTO DE " + (t2 - t) + " MILISEGUNDOS");

			if(result != null)
			{
				List<CompareFacesMatch> matches = result.getFaceMatches();
				List<ComparedFace> unmatches = result.getUnmatchedFaces();	

				if(matches.size() == 1)
				{
					System.out.println("Encontrado " + sources.get(i).getName());
					
        				target.setFaceMark(targetImage, matches.get(0).getFace().getBoundingBox(), result.getTargetImageOrientationCorrection());   
					
	        			ImageData dummy = new ImageData(target);
	        			this.getCarasEncontradas().add(dummy);
	        			
	        			if(this.getCarasNoEncontradas().contains(dummy))
	        			{
	        				this.getCarasNoEncontradas().remove(dummy);
	        			}
	        			
        				context.asignarIdentificacionEnSource(sources.get(i), dummy);	
	        			
	    				dummy.setName(sources.get(i).getName());

				}
				
				
				for(int j = 0; j < unmatches.size(); j++)
				{
					target.setFaceMark(targetImage, unmatches.get(j).getBoundingBox(), result.getTargetImageOrientationCorrection());
	    				ImageData dummy = new ImageData(target);
	
					if( (!this.getCarasNoEncontradas().contains(dummy)) && (!this.getCarasEncontradas().contains(dummy)))
					{
						this.getCarasNoEncontradas().add(dummy);
					}
				}
				
				
				//Cuando se hayan reconocido todas las caras, salir del programa
				/*if(this.getCarasNoEncontradas().size() == 0)
				{
					i = sources.size();
				}*/
			}
									
		}
	}
	
	public void analizarUnknown(DBContext context, User admin, ArrayList<ImageData> sources, ImageData target, Float similarityThreshold) throws SQLException
	{
		this.carasEncontradas = new ArrayList<ImageData>();
		this.carasNoEncontradas = new ArrayList<ImageData>();
		String url = context.validarRoute + target.getName();	
		
		Image targetImage = new Image().withBytes(ImageData.getImageBuffer(url));
		
		for(int i = 0; i < sources.size(); i++)
		{
			long t = System.currentTimeMillis();
			System.out.println("COMPARANDO con " + sources.get(i).getName());
			String sourcerl = null;
			
			System.out.println("Es analisis unknown");
			sourcerl = context.unknownRoute + sources.get(i).getName();	
			
			Image sourceImage = new Image().withBytes(ImageData.getImageBuffer(sourcerl));
			CompareFacesRequest request = new CompareFacesRequest().withSourceImage(sourceImage).withTargetImage(targetImage).withSimilarityThreshold(similarityThreshold);
			CompareFacesResult result = null;
			try 
			{
				result = admin.getRekognitionClient().compareFaces(request);
			}
			catch(Exception e)
			{
				System.err.println("Imposible comparar rostros.");
			}
			
			if(result != null)
			{
				List<CompareFacesMatch> matches = result.getFaceMatches();

				if(matches.size() == 1)
				{
					System.out.println("Encontrado " + sources.get(i).getName());					
					
	        			ImageData dummy = new ImageData(target);
	    				dummy.setName(sources.get(i).getName());
	        			this.getCarasEncontradas().add(dummy);	        			
	    				break;

				}
			}
			long t2 = System.currentTimeMillis();		
			
			System.err.println("RENDIMIENTO UNKNOWN DE " + (t2-t) + " MILISEGUNDOS");
		}
	}
	
}
