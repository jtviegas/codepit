package org.aprestos.labs.jaxrs.jersey;

import static org.junit.Assert.assertEquals;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.UriBuilder;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;



public class MyResourceIntegrationTest {

	private Client client;
    private WebTarget target;
    
    @Before
    public void setUp() throws Exception {
    	this.client = ClientBuilder.newClient();
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test to see that the message "Got it!" is sent in the response.
     */
    @Test
    public void testGetIt() {
    	
    	 URI builder = UriBuilder.fromPath("jerseyjaxrsjee/api")
 	            .scheme("http").host("localhost").port(9080).build();
    	 this.target = this.client.target(builder);
    	 String responseMsg = this.target.path("myresource").request().get(String.class);
         assertEquals("Got it!", responseMsg);
         

    }
}
