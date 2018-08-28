package org.challenges.norcom.restclient;

public interface BaseRestFunctions {

	String X_HTTP_METHOD_OVERRIDE_HEADER = "X-HTTP-Method-Override";

	HeadersBuilder getHeadersBuilder();

}