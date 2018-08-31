package org.challenges.norcom.backend.resources;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.challenges.norcom.backend.services.ElasticSearchQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/api/query")
@Api(tags = { "queries" }, value = "API root for queries")
@ApiResponses(value = { @ApiResponse(code = 400, message = "Invalid status value", response = void.class),
		@ApiResponse(code = 500, message = "Internal server error", response = void.class) })
public class Query {

	private static final Logger logger = LoggerFactory.getLogger(Query.class);

	@Autowired
	private ElasticSearchQuery elasticSearchQuery;

	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "used to query against elastic search", notes = "", response = Object.class, responseContainer = "List")
	@io.swagger.annotations.ApiResponses(value = {
			@ApiResponse(code = 200, message = "successful operation", response = Object.class, responseContainer = "List") })
	public ResponseEntity<List<Map<String, Object>>> getQuery(
			@ApiParam(value = "the input term to query the server", required = true) @RequestParam("query") String query)
			throws IOException {
		logger.trace("[getQuery|in] {}", query);
		try {
			return new ResponseEntity<List<Map<String, Object>>>(elasticSearchQuery.query(query), HttpStatus.OK);
		} finally {
			logger.trace("[getQuery|out]");
		}
	}

}
