package com.calendar.rest.view;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.calendar.rest.model.Booking;

import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class Views {

	public static String getOpeningPage() {
		try {
			return getResourceFileAsString("welcome.html");
		} catch (IOException e) {
			return printError("Page not found");
		}
	}

	private static String getResourceFileAsString(String fileName) throws IOException {
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		try (InputStream is = classLoader.getResourceAsStream(fileName)) {
			if (is == null) {
				return printError("File not found");
			}
			try (InputStreamReader isr = new InputStreamReader(is); BufferedReader reader = new BufferedReader(isr)) {
				return reader.lines().collect(Collectors.joining(System.lineSeparator()));
			}
		}
	}

	public static String getBookingsPage(List<Booking> bookings) {
		return getBookingsPagePrivate(bookings, "bookings.ftl");
	}

	public static String getWeeklyBookingsPage(List<Booking> bookings) {
		return getBookingsPagePrivate(bookings, "bookingsForWeek.ftl");
	}

	private static String getBookingsPagePrivate(List<Booking> bookings, String templateFileName) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("bookings", bookings);
		return generatePage(templateFileName, data);
	}

	private static String generatePage(String templateFileName, Map<String, Object> data) {

		// Freemarker configuration object
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_0);
		try {
			URL resource = Views.class.getResource("/");
			File templateDir = Paths.get(resource.toURI()).toFile();
			cfg.setTemplateLoader(new FileTemplateLoader(templateDir));

			// Load template from resources folder
			Template template = cfg.getTemplate(templateFileName);

			// String output
			Writer page = new StringWriter();
			template.process(data, page);
			page.flush();
			page.close();
			return page.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return printError(e.toString());
		} catch (TemplateException e) {
			e.printStackTrace();
			return printError(e.toString());
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return printError(e.toString());
		}
	}

	private static String printError(String error) {
		StringBuilder sb = new StringBuilder();
		sb.append(error);
		sb.append("\n<br />");
		sb.append("\n<button type=\"button\" onclick=\"window.location='/'\">Back to main page</button>");
		return sb.toString();
	}

}
