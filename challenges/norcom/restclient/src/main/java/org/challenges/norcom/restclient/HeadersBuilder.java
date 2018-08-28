package org.challenges.norcom.restclient;

import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;

public interface HeadersBuilder {

	HeadersBuilder withAuthentication();

	HeadersBuilder withPatchOverride();

	HeadersBuilder withTenant(String tenant);

	MultiValueMap<String, String> build();

	HeadersBuilder withContentType(MediaType t);

}