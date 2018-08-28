package org.challenges.norcom.restclient;

import static java.lang.String.format;

import java.nio.charset.Charset;
import java.security.KeyStore;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Future;
import java.util.function.Predicate;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class RestClientImpl implements RestClient, AsyncRestClient {

	private static final Logger logger = LoggerFactory.getLogger(RestClientImpl.class);

	private RestTemplate restTemplate;
	private HttpClient httpClient;

	private String user, password;

	@Autowired
	public RestClientImpl(@Value("${org.challenges.norcom.restclient.ssl:#{null}}") final String ssl,
			@Value("${org.challenges.norcom.restclient.auth.password:#{null}}") final String password,
			@Value("${org.challenges.norcom.restclient.auth.user:#{null}}") final String authUser,
			@Value("${org.challenges.norcom.restclient.keystorepassword:#{null}}") final String keystorePassword,
			@Value("${org.challenges.norcom.restclient.keystorefile:#{null}}") final String keystoreFile) {

		boolean sslOn = false;
		if (null != ssl && Boolean.parseBoolean(ssl))
			sslOn = true;

		boolean keystoreOn = false;
		if (((null != keystorePassword) && (null != keystoreFile)) && sslOn) {
			logger.info("going to setup keystore");
			keystoreOn = true;
		} else
			logger.info("not enough info to setup keystore");

		this.user = authUser;
		this.password = password;

		try {

			if (keystoreOn) {
				KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
				keyStore.load(RestClientImpl.class.getClassLoader().getResourceAsStream(keystoreFile),
						keystorePassword.toCharArray());
				SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(
						new SSLContextBuilder().loadTrustMaterial(null, new TrustSelfSignedStrategy())
								.loadKeyMaterial(keyStore, keystorePassword.toCharArray()).build(),
						NoopHostnameVerifier.INSTANCE);
				httpClient = HttpClients.custom().setSSLSocketFactory(socketFactory).build();
			} else {
				if (sslOn)
					httpClient = HttpClients.custom().setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
				else
					httpClient = HttpClients.custom().build();
			}

			ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
			restTemplate = new RestTemplate(requestFactory);

		} catch (Exception e) {
			throw new RuntimeException("could not load client", e);
		}
	}

	private class HeadersBuilderImpl implements HeadersBuilder {

		private MultiValueMap<String, String> header;

		public HeadersBuilderImpl() {
			header = new LinkedMultiValueMap<String, String>();
			// header.add("Content-Type", "application/json");
		}

		@Override
		public HeadersBuilder withAuthentication() {
			String auth = format("%s:%s", RestClientImpl.this.user, RestClientImpl.this.password);
			String encodedAuth = new String(Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII"))));
			String authHeader = format("Basic %s", new String(encodedAuth));
			header.add("Authorization", authHeader);
			return this;
		}

		@Override
		public HeadersBuilder withPatchOverride() {
			header.add(X_HTTP_METHOD_OVERRIDE_HEADER, "patch");
			return this;
		}

		@Override
		public HeadersBuilder withContentType(MediaType t) {
			header.add("Content-Type", t.toString());
			return this;
		}

		@Override
		public HeadersBuilder withTenant(String tenant) {
			if (null != tenant)
				header.add("tenant", tenant);
			return this;
		}

		@Override
		public MultiValueMap<String, String> build() {
			return header;
		}

	}

	@Override
	public HeadersBuilder getHeadersBuilder() {
		return new HeadersBuilderImpl();
	}

	@Override
	public <T> ResponseEntity<T> getResponse(ParameterizedTypeReference<T> type, MultiValueMap<String, String> header,
			String uri, MultiValueMap<String, String> queryParams, Object... uriParams) {

		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(uri);
		if (null != queryParams && !queryParams.isEmpty())
			uriBuilder.queryParams(queryParams);

		UriComponents u = null != uriParams ? uriBuilder.buildAndExpand(uriParams) : uriBuilder.build();
		return restTemplate.exchange(u.encode().toUri(), HttpMethod.GET, new HttpEntity<Object>(header), type);
	}

	@Async
	@Override
	public <T> Future<ResponseEntity<T>> getAsyncResponse(ParameterizedTypeReference<T> type,
			MultiValueMap<String, String> header, String uri, MultiValueMap<String, String> queryParams,
			Object... uriParams) {
		logger.info("here");
		return new AsyncResult<ResponseEntity<T>>(getResponse(type, header, uri, null, (Object) null));
	}

	@Override
	public <T extends Collection<E>, E> Optional<E> getFromCollection(ParameterizedTypeReference<T> collectionType,
			MultiValueMap<String, String> header, String uri, Predicate<E> filter, Object... uriParams) {
		Optional<E> r = null;

		ResponseEntity<T> o = getResponse(collectionType, header, uri, null, uriParams);
		if (null != o.getBody() && !o.getBody().isEmpty()) {
			if (null != filter)
				r = o.getBody().stream().filter(filter).findFirst();
			else
				r = o.getBody().stream().findFirst();
		} else
			r = Optional.empty();

		return r;
	}

	@Async
	@Override
	public <T extends Collection<E>, E> Future<Optional<E>> getAsyncFromCollection(
			ParameterizedTypeReference<T> collectionType, MultiValueMap<String, String> header, String uri,
			Predicate<E> filter, Object... uriParams) {
		return new AsyncResult<Optional<E>>(getFromCollection(collectionType, header, uri, filter, uriParams));
	}

	@Override
	public <T> Optional<T> get(ParameterizedTypeReference<T> type, MultiValueMap<String, String> header, String uri,
			MultiValueMap<String, String> queryParams, Object... uriParams) {
		Optional<T> r = null;

		ResponseEntity<T> o = getResponse(type, header, uri, queryParams, uriParams);
		r = null != o.getBody() ? Optional.of(o.getBody()) : Optional.empty();

		return r;
	}

	@Async
	@Override
	public <T> Future<Optional<T>> getAsync(ParameterizedTypeReference<T> type, MultiValueMap<String, String> header,
			String uri, MultiValueMap<String, String> queryParams, Object... uriParams) {
		return new AsyncResult<Optional<T>>(get(type, header, uri, queryParams, uriParams));
	}

	@Override
	public int getXTotalCount(MultiValueMap<String, String> header, String uri,
			MultiValueMap<String, String> queryParams, Object... uriParams) {

		ResponseEntity<Object> r = getResponse(new ParameterizedTypeReference<Object>() {
		}, header, uri, queryParams, uriParams);
		return Integer.parseInt(r.getHeaders().get("X-Total-Count").get(0));

	}

	@Async
	@Override
	public Future<Integer> getAsyncXTotalCount(MultiValueMap<String, String> header, String uri,
			MultiValueMap<String, String> queryParams, Object... uriParams) {
		return new AsyncResult<Integer>(getXTotalCount(header, uri, queryParams, uriParams));
	}

	@Override
	public int getCount(MultiValueMap<String, String> header, String uri, MultiValueMap<String, String> queryParams,
			Object... uriParams) {

		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(uri);
		if (null != queryParams && !queryParams.isEmpty())
			uriBuilder.queryParams(queryParams);

		UriComponents u = null != uriParams ? uriBuilder.buildAndExpand(uriParams) : uriBuilder.build();

		ResponseEntity<List<Object>> r = restTemplate.exchange(u.encode().toUri(), HttpMethod.GET,
				new HttpEntity<Object>(header), new ParameterizedTypeReference<List<Object>>() {
				});

		return r.getBody().size();
	}

	@Async
	@Override
	public Future<Integer> getAsyncCount(MultiValueMap<String, String> header, String uri,
			MultiValueMap<String, String> queryParams, Object... uriParams) {
		return new AsyncResult<Integer>(getCount(header, uri, queryParams, uriParams));
	}

	@Override
	public ResponseEntity<Void> patch(Map<String, Object> changes, MultiValueMap<String, String> header, String uri,
			Object... uriParams) {
		ResponseEntity<Void> r = null;
		UriComponents u = UriComponentsBuilder.fromHttpUrl(uri).buildAndExpand(uriParams);
		r = restTemplate.exchange(u.encode().toUri(), HttpMethod.PATCH,
				new HttpEntity<Map<String, Object>>(changes, header), Void.class);
		return r;
	}

	@Async
	@Override
	public Future<ResponseEntity<Void>> asyncPatch(Map<String, Object> changes, MultiValueMap<String, String> header,
			String uri, Object... uriParams) {
		return new AsyncResult<ResponseEntity<Void>>(patch(changes, header, uri, uriParams));
	}

	@Override
	public <I> ResponseEntity<Void> put(I body, MultiValueMap<String, String> header, String uri, Object... uriParams) {
		ResponseEntity<Void> r = null;

		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(uri);
		UriComponents u = null != uriParams ? uriBuilder.buildAndExpand(uriParams) : uriBuilder.build();

		r = restTemplate.exchange(u.encode().toUri(), HttpMethod.PUT, new HttpEntity<I>(body, header),
				new ParameterizedTypeReference<Void>() {
				});
		return r;
	}

	@Async
	@Override
	public <I> Future<ResponseEntity<Void>> asyncPut(I body, MultiValueMap<String, String> header, String uri,
			Object... uriParams) {
		return new AsyncResult<ResponseEntity<Void>>(put(body, header, uri, uriParams));
	}

	@Override
	public <I> ResponseEntity<Void> post(I body, MultiValueMap<String, String> header, String uri,
			Object... uriParams) {
		ResponseEntity<Void> r = null;

		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(uri);
		UriComponents u = null != uriParams ? uriBuilder.buildAndExpand(uriParams) : uriBuilder.build();
		r = restTemplate.exchange(u.encode().toUri(), HttpMethod.POST, new HttpEntity<I>(body, header),
				new ParameterizedTypeReference<Void>() {
				});
		return r;
	}

	@Override
	public <I> ResponseEntity<Void> postEntity(HttpEntity<I> requestEntity, String uri) throws RestClientException {
		ResponseEntity<Void> r = null;

		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(uri);
		restTemplate.postForObject(uriBuilder.build().encode().toUri(), requestEntity, Void.class);

		return r;
	}

	/*
	 * @Override public <I> ResponseEntity<Void> post(I body, boolean
	 * bufferRequestBody, MultiValueMap<String, String> header, String uri,
	 * Object... uriParams) { ResponseEntity<Void> r = null;
	 * 
	 * UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(uri);
	 * UriComponents u = null != uriParams ? uriBuilder.buildAndExpand(uriParams) :
	 * uriBuilder.build();
	 * 
	 * RestTemplate template = restTemplate; if( !bufferRequestBody ) {
	 * ClientHttpRequestFactory requestFactory = new
	 * HttpComponentsClientHttpRequestFactory(httpClient); requestFactory.
	 * restTemplate = new RestTemplate(requestFactory); }
	 * 
	 * r = restTemplate.exchange(u.encode().toUri(), HttpMethod.POST, new
	 * HttpEntity<I>(body, header), new ParameterizedTypeReference<Void>() { });
	 * return r; }
	 */

	@Async
	@Override
	public <I> Future<ResponseEntity<Void>> asyncPost(I body, MultiValueMap<String, String> header, String uri,
			Object... uriParams) {
		return new AsyncResult<ResponseEntity<Void>>(post(body, header, uri, uriParams));
	}

	@Override
	public <I> ResponseEntity<Void> post(I body, MultiValueMap<String, String> header, String uri) {
		return post(body, header, uri, (Object[]) null);
	}

	@Async
	@Override
	public <I> Future<ResponseEntity<Void>> asyncPost(I body, MultiValueMap<String, String> header, String uri) {
		return new AsyncResult<ResponseEntity<Void>>(post(body, header, uri));
	}

	@Override
	public <I> Id postAndGetId(I body, MultiValueMap<String, String> header, String uri, Object... uriParams) {
		ResponseEntity<Void> r = null;
		r = post(body, header, uri, uriParams);
		String location = r.getHeaders().getFirst("Location");
		return Id.create(location.substring(location.lastIndexOf("/") + 1));
	}

	@Async
	@Override
	public <I> Future<Id> asyncPostAndGetId(I body, MultiValueMap<String, String> header, String uri,
			Object... uriParams) {
		return new AsyncResult<Id>(postAndGetId(body, header, uri, uriParams));
	}

	@Override
	public <I> Id postAndGetId(I body, MultiValueMap<String, String> header, String uri) {
		return postAndGetId(body, header, uri, (Object[]) null);
	}

	@Async
	@Override
	public <I> Future<Id> asyncPostAndGetId(I body, MultiValueMap<String, String> header, String uri) {
		return new AsyncResult<Id>(postAndGetId(body, header, uri, (Object[]) null));
	}

	@Override
	public ResponseEntity<Void> delete(MultiValueMap<String, String> header, String uri, Object... uriParams) {
		UriComponents u = UriComponentsBuilder.fromHttpUrl(uri).buildAndExpand(uriParams);
		return restTemplate.exchange(u.encode().toUri(), HttpMethod.DELETE, new HttpEntity<Object>(header), void.class);
	}

	@Async
	@Override
	public Future<ResponseEntity<Void>> asyncDelete(MultiValueMap<String, String> header, String uri,
			Object... uriParams) {
		return new AsyncResult<ResponseEntity<Void>>(delete(header, uri, uriParams));
	}

}
