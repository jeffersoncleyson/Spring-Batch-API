package com.poc.springbatch.config;

import com.poc.springbatch.repository.converters.ZonedDateTimeReadConverter;
import com.poc.springbatch.repository.converters.ZonedDateTimeWriteConverter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class MongoConfiguration {

	@NonNull
	private MongoDatabaseFactory mongoDbFactory;

	@NonNull
	private MongoMappingContext mongoMappingContext;

	@Bean
	public MappingMongoConverter mappingMongoConverter() {

		var dbRefResolver = new DefaultDbRefResolver(mongoDbFactory);
		var converter = new MappingMongoConverter(dbRefResolver, mongoMappingContext);
		converter.setTypeMapper(new DefaultMongoTypeMapper(null));
		converter.setCustomConversions(customConversions());
		converter.afterPropertiesSet();
		return converter;
	}

	public MongoCustomConversions customConversions() {

		List<Converter<?, ?>> converters = new ArrayList<>();
		converters.add(new ZonedDateTimeReadConverter());
		converters.add(new ZonedDateTimeWriteConverter());
		return new MongoCustomConversions(converters);
	}
}
