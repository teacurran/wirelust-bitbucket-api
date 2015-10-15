package com.wirelust.bitbucket.example.providers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

/**
 * Date: 07-Oct-2015
 *
 * @author T. Curran
 */
public class JacksonObjectMapper extends ObjectMapper {

	public static JacksonObjectMapper get() {

		// Jackson 2.0
		JacksonObjectMapper mapper = new JacksonObjectMapper();

		mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, false);
		mapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
		mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

		// In the unit tests this is set to true because we want it to fail there
		// but in production we don't want it to fail so easily.
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		return mapper;
	}
}
