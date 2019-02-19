package com.nishitp;

import java.util.LinkedList;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nishitp.optiondata.dao.DBDao;
import com.nishitp.optiondata.dao.MySqlDao;
import com.nishitp.optiondata.scraper.OptionDataRetriever;
import com.nishitp.otpiondata.beans.Pair;

public class SpecificURLApp {
	
	private static final List<Pair<String,String>> fetchPairs(){
		
		List<Pair<String,String>> pairList = new LinkedList<>();
		
		String url = "https://www.nseindia.com/live_market/dynaContent/live_watch/option_chain/optionKeys.jsp?symbol=SUNPHARMA&date=28MAR2019";
		String symbol = "SUNPHARMA";
		Pair<String,String> pair = new Pair<String, String>(url, symbol);
		pairList.add(pair);
				
		return pairList;
	}

	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		OptionDataRetriever retriever = (OptionDataRetriever) context.getBean(OptionDataRetriever.class);
		DBDao dbInserter = (MySqlDao) context.getBean(MySqlDao.class);
		List<Pair<String, String>> urls = fetchPairs();
		for(Pair<String,String> urlSymbolPair : urls){
			System.out.println("URL IS : " + urlSymbolPair.getUrl());
			dbInserter.insertData(retriever.fetchData(urlSymbolPair.getUrl(), urlSymbolPair.getSymbol()));
		}
		
		long end = System.currentTimeMillis();
		System.out.println("Total Time is : "+ (end-start));
		
		context.close();
		
	}
}
