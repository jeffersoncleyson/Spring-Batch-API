package com.poc.springbatch.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;

@Configuration
public class JacksonMapperConfiguration {

	@Bean
	@Primary
	public ObjectMapper geObjMapper() {

		return new ObjectMapper()

				.registerModule(new ParameterNamesModule())

				.registerModule(new Jdk8Module())

				.registerModule(new JavaTimeModule());
	}
	
	@Bean
	public MappingJackson2MessageConverter mappingJackson2MessageConverter(ObjectMapper objectMapper) {
	    var jacksonMessageConverter = new MappingJackson2MessageConverter();
	    jacksonMessageConverter.setObjectMapper(objectMapper);
	    jacksonMessageConverter.setSerializedPayloadClass(String.class);
	    jacksonMessageConverter.setStrictContentTypeMatch(true);
	    return jacksonMessageConverter;
	}
}
