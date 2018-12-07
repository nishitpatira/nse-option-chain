package com.nishitp.otpiondata.beans;

import java.sql.Date;
import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class OptionDetails {
	
	String symbol;
	LocalDate currentDate;
	Date expiryDate;
	int callOpenInterest;
	int callChangeInOpenInterest;
	int callVolume;
	double callImpliedVolatility;
	double callLastTradedPrice;
	double callNetChange;
	int callBidQuantity;
	double callBidPrice;
	double callAskPrice;
	int callAskQuantity;
	double strikePrice;
	int putBidQuantity;
	double putBidPrice;
	double putAskPrice;
	int putAskQuantity;
	double putNetChange;
	double putLastTradedPrice;
	double putImpliedVolatility;
	int putVolume;
	int putChangeInOpenInterest;
	int putOpenInterest;

}
