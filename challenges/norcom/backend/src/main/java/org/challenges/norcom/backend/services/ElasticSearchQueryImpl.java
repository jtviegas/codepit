package org.challenges.norcom.backend.services;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ElasticSearchQueryImpl implements ElasticSearchQuery {

	private static final Logger logger = LoggerFactory.getLogger(ElasticSearchQueryImpl.class);

	@Value("${org.challenges.norcom.backend.host}")
	private String host;

	@Value("${org.challenges.norcom.backend.port}")
	private Integer port;

	@Value("${org.challenges.norcom.backend.index}")
	private String index;

	private TransportClient client;

	@Override
	public List<Map<String, Object>> query(String query) throws IOException {
		logger.trace("[query|in]");

		List<Map<String, Object>> r = new ArrayList<Map<String, Object>>();

		SearchResponse response = client.prepareSearch(index).setQuery(QueryBuilders.termQuery("body", query)).get();

		for (SearchHit hit : response.getHits())
			r.add(hit.getSourceAsMap());

		logger.trace("[query|out] size: {}", r.size());
		return r;
	}

	@SuppressWarnings("resource")
	@PostConstruct
	private void init() throws IOException {
		logger.trace("[init|in]");
		client = new PreBuiltTransportClient(Settings.EMPTY)
				.addTransportAddress(new TransportAddress(InetAddress.getByName(host), port));
		logger.trace("[init|out]");
	}

	@PreDestroy
	public void shutdown() {
		logger.trace("[shutdown|in]");
		try {
			client.close();
		} catch (Exception ignored) {
		}
		logger.trace("[shutdown|out]");
	}

}
