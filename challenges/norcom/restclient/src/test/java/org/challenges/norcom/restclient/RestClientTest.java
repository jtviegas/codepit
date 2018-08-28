package org.challenges.norcom.restclient;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.MultiValueMap;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RestClientTest {

	@Autowired
	private RestClient restClient;

	@Autowired
	private AsyncRestClient asyncClient;

	@Test
	public void test() throws InterruptedException, ExecutionException {
		MultiValueMap<String, String> h = restClient.getHeadersBuilder().build();

		Future<ResponseEntity<String>> r = asyncClient.getAsyncResponse(new ParameterizedTypeReference<String>() {
		}, h, "http://www.google.com:80/", null, (Object) null);
		System.out.println("doing");

		Assert.assertNotEquals(r.get().getBody(), restClient.getResponse(new ParameterizedTypeReference<String>() {
		}, h, "http://www.google.com:80/", null, (Object) null).getBody());

		System.out.println("done");
	}

}
