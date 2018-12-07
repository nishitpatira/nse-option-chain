package com.nishitp;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nishitp.optiondata.dao.DBDao;
import com.nishitp.optiondata.dao.MySqlDao;
import com.nishitp.optiondata.scraper.OptionDataRetriever;
import com.nishitp.otpiondata.beans.Pair;

public class App {

	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		OptionDataRetriever retriever = (OptionDataRetriever) context.getBean(OptionDataRetriever.class);
		DBDao dbInserter = (MySqlDao) context.getBean(MySqlDao.class);
		List<Pair<String, String>> urls = retriever.fetchDom();
		for(Pair<String,String> urlSymbolPair : urls){
			System.out.println("URL IS : " + urlSymbolPair.getUrl());
			dbInserter.insertData(retriever.fetchData(urlSymbolPair.getUrl(), urlSymbolPair.getSymbol()));
		}
		
		long end = System.currentTimeMillis();
		System.out.println("Total Time is : "+ (end-start));
		
		context.close();
		
	}
}
