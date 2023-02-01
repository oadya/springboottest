package com.testing.springboottest.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateConverter {

	private static final String PATTERN_DATE = "yyyy-MM-dd";
	private static final String PATTERN_DATE_FOR_SAP = "yyyyMMdd";
	private static final String PATTERN_DATE_FR = "dd/MM/yyyy";

	private static final Logger LOGGER = LoggerFactory.getLogger(DateConverter.class);

	public static LocalDate convertFromStringToLocalDate(String source) {

		if (source != null && !source.isEmpty()) {
			DateTimeFormatter shortDate = DateTimeFormatter.ofPattern(PATTERN_DATE);
			if (source.length() == 9) {
				source = source.substring(0, 9);
			} else {
				source = source.substring(0, 10);
			}
			LocalDate date = LocalDate.parse(source, shortDate);
			return date;
		} else {
			LOGGER.warn("$$$ The LocalDate  ==> " + source + " cannot be null $$$");
			return null;
		}
	}

	public static String convertLocalDateToString(LocalDate source) {

		if (source != null) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_DATE);
			String formattedString = source.format(formatter);
			return formattedString;
		} else {
			LOGGER.warn("$$$ The LocalDate  ==> " + source + " cannot be null $$$");
			return null;
		}
	}

	public static String convertLocalDateToStringForSAP(LocalDate source) {

		if (source != null) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_DATE_FOR_SAP);
			String formattedString = source.format(formatter);
			return formattedString;
		} else {
			LOGGER.warn("$$$ The LocalDate  ==> " + source + " cannot be null $$$");
			return null;
		}
	}

	public static String convertLocalDateFRToString(LocalDate source) {

		if (source != null) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_DATE_FR);
			String formattedString = source.format(formatter);
			return formattedString;
		} else {
			LOGGER.warn("$$$ The LocalDateFR  ==> " + source + " cannot be null $$$");
			return null;
		}
	}

	public static String convertStringDateFRToString(String sourceString) {

		if (sourceString != null) {
			LocalDate sourceDate = convertFromStringToLocalDate(sourceString);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_DATE_FR);
			String formattedString = sourceDate.format(formatter);
			return formattedString;
		} else {
			LOGGER.warn("$$$ The LocalDateFR  ==> " + sourceString + " cannot be null $$$");
			return null;
		}
	}
}