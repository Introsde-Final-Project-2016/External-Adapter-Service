package introsde.rest.adapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import javax.ejb.*;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.glassfish.jersey.client.ClientConfig;
import org.json.*;

@Stateless // Used only if the the application is deployed in a Java EE container
@LocalBean // Used only if the the application is deployed in a Java EE container
@Path("/BmiCalculate")


public class External {

	  // this class communicates with external api and sends Weight/height/age and gender of the user
      
      @GET
      @Path("/{weight}/{height}")
      public String getBMI(@PathParam("weight") double weight, @PathParam("height") double height) throws ClientProtocolException, IOException {
  		System.out.println("getBMI");

      ClientConfig clientConfig = new ClientConfig();
      Client client = ClientBuilder.newClient(clientConfig);
      WebTarget service = client.target("https://bmi.p.mashape.com/");
     	
		String message = "{\"weight\":{\"value\":\"85.00\",\"unit\":\"kg\"},\"height\":{\"value\":\"170.00\",\"unit\":\"cm\"},\"sex\":\"m\",\"age\":\"24\"}";
		JSONObject objedit = new JSONObject(message);
		objedit.getJSONObject("weight").put("value", weight);
		objedit.getJSONObject("height").put("value", height);
		
    	  
      Entity<String> entityPostJson = Entity.json(objedit.toString());
		Response response = service.request()
				.header("X-Mashape-Key", "GuCjdAkWBjmshMRXs16T2VGWetJjp1O8iU6jsnG51qatFngVJo")
				.header("Content-Type", "application/json")
				.header("Accept", "application/json")
		        .accept(MediaType.APPLICATION_JSON)
		        .post(entityPostJson);
		
		String body = response.readEntity(String.class);
		
		JSONObject obj = new JSONObject(body);
		String n = obj.getString("ideal_weight");
		// print out just to check while coding
		System.out.println("Your ideal Weight should be between: "+n);
		String m = obj.getJSONObject("bmi").getString("value");
		// print out just to check while coding
		System.out.println("BMI value: "+m);
	 
		return body; 
	
}
}
