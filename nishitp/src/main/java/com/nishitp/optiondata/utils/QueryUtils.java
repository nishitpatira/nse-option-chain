package com.nishitp.optiondata.utils;

public class QueryUtils {
	
	public static String generateQueryForOptionChainInsert(){
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("INSERT INTO ").append("Option_Chain").append(" VALUES ").append("(");
		int columnCount = 24;
		for(int i = 0; i < columnCount; i++){
			queryBuilder.append("?,");
		}
		queryBuilder.deleteCharAt(queryBuilder.length()-1);
		queryBuilder.append(")");
		//System.out.println(queryBuilder.toString());
		return queryBuilder.toString();
	}

}
