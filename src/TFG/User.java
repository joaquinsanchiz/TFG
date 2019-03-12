package TFG;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;

public class User {
	
    private AWSCredentials credentials;
    private AmazonRekognition rekognitionClient;
    
    public User() 
    {
	    	try 
	    	{
            this.credentials = new AWSCredentials() 
            {
  			
	  			@Override
	  			public String getAWSSecretKey() 
	  			{
	  				
	  				return "secretkey";
	  			}
	  			
	  			@Override
	  			public String getAWSAccessKeyId() 
	  			{
	  				
	  				return "accesskey";
	  			}
            };
        } 
	    	catch(Exception e) 
	    	{
           throw new AmazonClientException("Cannot load the credentials from the credential profiles file. "
            + "Please make sure that your credentials file is at the correct "
            + "location (/Users/userid/.aws/credentials), and is in a valid format.", e);
        }
	    	
	    this.rekognitionClient = AmazonRekognitionClientBuilder.standard()
	    		.withRegion(Regions.US_EAST_1)
	    		.withCredentials(new AWSStaticCredentialsProvider(this.getCredentials()))
	    		.build();
    }

	public AWSCredentials getCredentials() {
		return credentials;
	}

	public void setCredentials(AWSCredentials credentials) {
		this.credentials = credentials;
	}

	public AmazonRekognition getRekognitionClient() {
		return rekognitionClient;
	}

	public void setRekognitionClient(AmazonRekognition rekognitionClient) {
		this.rekognitionClient = rekognitionClient;
	}
    
    

}
