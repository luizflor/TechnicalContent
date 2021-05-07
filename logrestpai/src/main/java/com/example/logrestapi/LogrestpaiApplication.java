package com.example.logrestapi;

import com.example.logrestapi.converter.ZonedDateTimeReadConverter;
import com.example.logrestapi.converter.ZonedDateTimeWriteConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class LogrestpaiApplication {

	@Bean
	public MongoCustomConversions customConversions() {
		List<Converter<?, ?>> converters = new ArrayList<>();
		converters.add(new ZonedDateTimeReadConverter());
		converters.add(new ZonedDateTimeWriteConverter());
		return new MongoCustomConversions(converters);
	}

	public static void main(String[] args) {
		SpringApplication.run(LogrestpaiApplication.class, args);
	}

}
