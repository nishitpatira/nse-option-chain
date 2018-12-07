package com.nishitp.optiondata.utils;

import java.text.ParseException;

import org.junit.Assert;
import org.junit.Test;

public class TestOptionUtils {
	
	@Test
	public void testConvertStringToIntNormal() throws Exception{
		int returnVal = DataUtils.convertStringToInt("123,456");
		Assert.assertEquals(123456, returnVal);
	}
	
	@Test (expected = ParseException.class)
	public void testConvertStringToIntException() throws Exception{
		System.out.println(DataUtils.convertStringToInt("ABC"));
	}

}
