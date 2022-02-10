package com.adamtreso.rest.webservices.restfullwebservices.common;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ExtendedConversionService {

	@Autowired
	@Qualifier(ServiceConfiguration.AM_CONVERSION_SERVICE)
	private ConversionService conversionService;

	public <T> T convert(final Object source, final Class<T> targetType) {
		return conversionService.convert(source, targetType);
	}

	public <T> List<T> convert(final List<?> source, final Class<T> targetType) {
		return source.stream().map(item -> convert(item, targetType)).collect(Collectors.toList());
	}

	public <T> Set<T> convert(final Set<?> source, final Class<T> targetType) {
		return source.stream().map(item -> convert(item, targetType)).collect(Collectors.toSet());
	}

	public <T> List<T> convert(final Collection<?> source, final Class<T> targetType) {
		return source.stream().map(item -> convert(item, targetType)).collect(Collectors.toList());
	}

	public <T, C extends Collection<T>> C convert(
			final Collection<?> source,
			final Class<T> targetType,
			final Supplier<C> collectionFactory) {
		return source.stream().map(item -> convert(item, targetType)).collect(Collectors.toCollection(collectionFactory));
	}

	public <T> Page<T> convert(final Page<?> source, final Class<T> targetType) {
		List<T> converted = source.getContent().stream().map(item -> convert(item, targetType)).collect(Collectors.toList());
		return new PageImpl<>(converted, PageRequest.of(source.getNumber(), source.getSize()), source.getTotalElements());
	}

	public <T, R> Page<T> convert(final Page<R> source, final Class<T> targetType, final Function<R, ?> producer) {
		List<T> converted = source.getContent().stream().map(item -> convert(producer.apply(item), targetType)).collect(Collectors.toList());
		return new PageImpl<>(converted, PageRequest.of(source.getNumber(), source.getSize()), source.getTotalElements());
	}

}
