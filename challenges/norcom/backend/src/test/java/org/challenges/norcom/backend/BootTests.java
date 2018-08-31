package org.challenges.norcom.backend;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestContextManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class BootTests {

	private RestTemplate client;
	private HttpHeaders headers;

	private TestContextManager testContextManager;

	@Test
	public void test_01_query_paper() {

		ResponseEntity<List<Map<String, Object>>> response = client.exchange("localhost:8080/api/query?query=paper",
				HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<List<Map<String, Object>>>() {
				});

		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assert.assertTrue(0 < response.getBody().size());
	}

	@Before
	public void setUp() throws Exception {

		this.testContextManager = new TestContextManager(getClass());
		this.testContextManager.prepareTestInstance(this);
		headers = new HttpHeaders();
		headers.set("Accept", "application/json");
		headers.setContentType(MediaType.APPLICATION_JSON);

		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		requestFactory.setBufferRequestBody(false);
		client = new RestTemplate(requestFactory);
	}

}
