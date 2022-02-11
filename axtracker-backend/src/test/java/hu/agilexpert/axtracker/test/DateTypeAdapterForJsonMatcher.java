package hu.agilexpert.axtracker.test;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import lombok.SneakyThrows;

public class DateTypeAdapterForJsonMatcher extends TypeAdapter<Date> {

	private final DateFormat dateFormatter = new SimpleDateFormat("MMM d, yyyy H:mm:ss aaa", Locale.ENGLISH);

	public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() {
		@SuppressWarnings("unchecked")
		@Override
		public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> typeToken) {
			return Date.class.isAssignableFrom(typeToken.getRawType()) ? (TypeAdapter<T>) new DateTypeAdapterForJsonMatcher() : null;
		}
	};

	@Override
	public void write(final JsonWriter out, final Date value) throws IOException {
		if (value == null) {
			out.nullValue();
		} else {
			String dateString = dateFormatter.format(value);
			out.value(dateString);
		}
	}

	@Override
	@SneakyThrows
	public Date read(final JsonReader in) throws IOException{
		if (in.peek() == JsonToken.NULL) {
			in.nextNull();
			return null;
		}

		String dateAsString = in.nextString();
		return dateFormatter.parse(dateAsString);
	}

}
