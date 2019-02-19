package com.nishitp.optiondata.scraper;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.nishitp.optiondata.utils.DataUtils;
import com.nishitp.otpiondata.beans.Constants;
import com.nishitp.otpiondata.beans.OptionDetails;
import com.nishitp.otpiondata.beans.Pair;

public class OptionDataRetriever {

	private String url = "https://www.nseindia.com/live_market/dynaContent/live_watch/option_chain/optionKeys.jsp?";

	private OptionDetails generateOptionDetailsPOJO(final Elements data, final Date expiryDate, final String symbol) {
		try {
			return OptionDetails.builder().symbol(symbol).currentDate(DataUtils.getCurrentDate()).expiryDate(expiryDate)
					.callOpenInterest(DataUtils.convertStringToInt(data.get(1).text()))
					.callChangeInOpenInterest(DataUtils.convertStringToInt(data.get(2).text()))
					.callVolume(DataUtils.convertStringToInt(data.get(3).text()))
					.callImpliedVolatility(DataUtils.convertStringToDouble(data.get(4).text()))
					.callLastTradedPrice(DataUtils.convertStringToDouble(data.get(5).text()))
					.callNetChange(DataUtils.convertStringToDouble(data.get(6).text()))
					.callBidQuantity(DataUtils.convertStringToInt(data.get(7).text()))
					.callBidPrice(DataUtils.convertStringToDouble(data.get(8).text()))
					.callAskPrice(DataUtils.convertStringToDouble(data.get(9).text()))
					.callAskQuantity(DataUtils.convertStringToInt(data.get(10).text()))
					.strikePrice(DataUtils.convertStringToDouble(data.get(11).text()))
					.putBidQuantity(DataUtils.convertStringToInt(data.get(12).text()))
					.putBidPrice(DataUtils.convertStringToDouble(data.get(13).text()))
					.putAskPrice(DataUtils.convertStringToDouble(data.get(14).text()))
					.putAskQuantity(DataUtils.convertStringToInt(data.get(15).text()))
					.putNetChange(DataUtils.convertStringToDouble(data.get(16).text()))
					.putLastTradedPrice(DataUtils.convertStringToDouble(data.get(17).text()))
					.putImpliedVolatility(DataUtils.convertStringToDouble(data.get(18).text()))
					.putVolume(DataUtils.convertStringToInt(data.get(19).text()))
					.putChangeInOpenInterest(DataUtils.convertStringToInt(data.get(20).text()))
					.putOpenInterest(DataUtils.convertStringToInt(data.get(21).text())).build();
		} catch (ParseException pe) {
			System.err.println("Error while creating POJO for the data : " + data);
		}
		return null;

	}

	public List<OptionDetails> fetchData(final String url, final String symbol) {
		final List<OptionDetails> optionChain = new LinkedList<OptionDetails>();
		try {
			final Document doc = Jsoup.connect(url).timeout(0).get();
			if (doc != null) {
				final Element optionTable = doc.getElementById("octable");
				final Elements optionTableBody = optionTable.getElementsByTag("tbody");
				final Element expiryDate = doc.getElementById("date");

				for (Element table : optionTableBody) {
					final Elements options = table.getElementsByTag("tr");

					for (int i = 0; i < options.size() - 1; i++) {
						final Elements data = options.get(i).getElementsByTag("td");
						final OptionDetails optionDetails = generateOptionDetailsPOJO(data,
								DataUtils.getExpiryDate(expiryDate.getElementsByAttribute("selected").text()), symbol);
						// System.out.println(optionDetails);
						optionChain.add(optionDetails);
					}
				}
			}

		} catch (IOException ioe) {
			System.err.println("Error while connecting for URL : " + url);
			System.err.println("Exception is : " + ioe);
		}

		return optionChain;
	}

	private List<Pair<String, String>> generateURL(final String symbol) {
		final List<Pair<String, String>> urls = new LinkedList<>();
		// System.out.println(symbol);
		for (String expiryDate : fetchExpiryDates(url, symbol)) {
			if (!expiryDate.equals("Select")) {
				StringBuilder firstURL = new StringBuilder(url);
				firstURL.append("symbol=");
				firstURL.append(symbol);
				firstURL.append("&");
				firstURL.append("date=");
				firstURL.append(expiryDate);
				urls.add(new Pair<String, String>(firstURL.toString(), symbol));
			}

		}
		return urls;
	}

	private List<String> fetchExpiryDates(final String url, final String symbol) {
		final List<String> expiryDatesList = new LinkedList<>();
		final StringBuilder urlBuilder = new StringBuilder(url);
		urlBuilder.append("symbol=");
		urlBuilder.append(symbol);
		try {
			final Document doc = Jsoup.connect(urlBuilder.toString()).timeout(0).get();
			final Element expiryDates = doc.getElementById("date");
			for (Element element : expiryDates.getElementsByTag("option")) {
				expiryDatesList.add(element.text());
			}
			return expiryDatesList;
		} catch (IOException ioe) {
			System.err.println("Error while connecting to the URL : " + urlBuilder.toString() + ioe);
		}
		return null;
	}

	public List<Pair<String, String>> fetchDom() {
		final List<Pair<String, String>> urlSymbolList = new LinkedList<>();

		for (String symbol : Constants.symbols) {
			urlSymbolList.addAll(generateURL(symbol));
		}
		for (Pair pair : urlSymbolList) {
			System.out.println(pair.getUrl());
		}
		return urlSymbolList;
	}
}
