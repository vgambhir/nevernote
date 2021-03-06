package com.nevernote.exception;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import lombok.Data;

@Data
public class ErrorDetail {
	public static DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	public String eventDate;
	private String message;
	private String details;

	public ErrorDetail(){}
	public ErrorDetail(String message, String details) {
		this.message = message;
		this.details = details;
		Date today = Calendar.getInstance().getTime();
		this.eventDate = df.format(today);
	}

}
