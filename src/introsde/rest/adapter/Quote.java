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
@Path("/Quote")

// this class communicates with external api to get random quote
public class Quote {

      @GET
      @Path("/getOne")
      public String getBMI() throws ClientProtocolException, IOException {
  		System.out.println("getBMI");

      ClientConfig clientConfig = new ClientConfig();
      Client client = ClientBuilder.newClient(clientConfig);
      WebTarget service = client.target("https://andruxnet-random-famous-quotes.p.mashape.com/?cat=famous"); 	  
      
		Response response = service.request()
				.header("X-Mashape-Key", "GuCjdAkWBjmshMRXs16T2VGWetJjp1O8iU6jsnG51qatFngVJo")
				.header("Content-Type", "application/x-www-form-urlencoded")
				.header("Accept", "application/json")
		        .accept(MediaType.APPLICATION_JSON)
		        .get();
		
		String body = response.readEntity(String.class);
		
		JSONObject obj = new JSONObject(body);
		String n = obj.getString("quote");
		// print out just to check while coding
		System.out.println("the day of the day "+n);
		String m = obj.getString("author");
		// print out just to check while coding
		System.out.println(""+m);

		return body; 
	
}
}
