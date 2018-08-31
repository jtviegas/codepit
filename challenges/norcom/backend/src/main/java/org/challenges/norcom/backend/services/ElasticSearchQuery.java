package org.challenges.norcom.backend.services;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ElasticSearchQuery {

	List<Map<String, Object>> query(String query) throws IOException;

}