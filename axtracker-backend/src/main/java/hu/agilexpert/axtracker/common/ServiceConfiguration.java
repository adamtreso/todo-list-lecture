package hu.agilexpert.axtracker.common;

import java.util.Locale;
import java.util.Set;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;

@SpringBootApplication
public class ServiceConfiguration {

	public static final String AM_CONVERSION_SERVICE = "amConversionService";

	@Bean(AM_CONVERSION_SERVICE)
	public ConversionService createConversionService(final Set<Converter<?, ?>> converters) {
		final ConversionServiceFactoryBean bean = new ConversionServiceFactoryBean();
		bean.setConverters(converters);
		bean.afterPropertiesSet();
		return bean.getObject();
	}

	@Bean
	public MessageSourceAccessor messageSourceAccessor(final MessageSource messageSource) {
		return new MessageSourceAccessor(messageSource, new Locale("hu", "HU"));
	}

}
