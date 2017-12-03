package com.banana.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class StringToLocalDateTime {
	public static LocalDateTime convert(String s) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern(("MM-dd-yyyy HH:mm"));
		LocalDateTime ldt = null;
		try {
			ldt = LocalDateTime.parse(s, format);
		}catch(DateTimeParseException e) {
			return null;
		}
		
		
		return ldt;
	}
}
