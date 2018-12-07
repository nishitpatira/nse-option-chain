package com.nishitp.optiondata.utils;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

public class DataUtils {
	
	private static final SimpleDateFormat expiryDateFormatter = new SimpleDateFormat("ddMMMyyyy"); 
	
	public static int convertStringToInt(final String input) throws ParseException{
		if(input.equals("-")){
			return Integer.MIN_VALUE;
		}
		try{
			return NumberFormat.getNumberInstance(Locale.US).parse(input).intValue();
		} catch(ParseException pe){
			throw new ParseException("Error while parsing the input : " + input, 0);
		}
	}
	
	public static Double convertStringToDouble(final String input) throws ParseException{
		if(input.equals("-")){
			return Double.MIN_VALUE;
		}
		try{
			return NumberFormat.getNumberInstance(Locale.US).parse(input).doubleValue();
		} catch(ParseException pe){
			throw new ParseException("Error while parsing the input : " + input, 0);
		}
	}
	
	public static LocalDate getCurrentDate(){
		return LocalDate.now();
	}
	
	public static java.sql.Date getExpiryDate(String expiryDate){
		try {
			Date date = expiryDateFormatter.parse(expiryDate);
			return new java.sql.Date(date.getTime());
		} catch(ParseException pe){
			System.err.println("Error while parsing the date : " + expiryDate);
		}
		return null;
	}
}
