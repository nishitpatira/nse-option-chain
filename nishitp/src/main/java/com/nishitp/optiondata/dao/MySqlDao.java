package com.nishitp.optiondata.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import com.nishitp.optiondata.utils.QueryUtils;
import com.nishitp.otpiondata.beans.OptionDetails;

import lombok.Setter;

public class MySqlDao implements DBDao {
	
	@Setter
	private DataSource dataSource;
	
	@Override
	public void insertData(List<OptionDetails> data){
		final String sql = QueryUtils.generateQueryForOptionChainInsert();
		try(Connection connection = dataSource.getConnection() ; PreparedStatement ps = connection.prepareStatement(sql)){
			System.out.println("*****SIZE OF DATA IS******" + data.size());
			for(OptionDetails record : data){
				ps.setString(1, record.getSymbol());
				ps.setDate(2, Date.valueOf(record.getCurrentDate()));
				ps.setDate(3, record.getExpiryDate());
				ps.setInt(4, record.getCallOpenInterest());
				ps.setInt(5, record.getCallChangeInOpenInterest());
				ps.setInt(6, record.getCallVolume());
				ps.setDouble(7, record.getCallImpliedVolatility());
				ps.setDouble(8, record.getCallLastTradedPrice());
				ps.setDouble(9, record.getCallNetChange());
				ps.setInt(10, record.getCallBidQuantity());
				ps.setDouble(11, record.getCallBidPrice());
				ps.setDouble(12, record.getCallAskPrice());
				ps.setInt(13, record.getCallAskQuantity());
				ps.setDouble(14, record.getStrikePrice());
				ps.setInt(15, record.getPutBidQuantity());
				ps.setDouble(16, record.getPutBidPrice());
				ps.setDouble(17, record.getPutAskPrice());
				ps.setInt(18, record.getPutAskQuantity());
				ps.setDouble(19, record.getPutNetChange());
				ps.setDouble(20, record.getPutLastTradedPrice());
				ps.setDouble(21, record.getPutImpliedVolatility());
				ps.setInt(22, record.getPutVolume());
				ps.setInt(23, record.getPutChangeInOpenInterest());
				ps.setInt(24, record.getPutOpenInterest());
				ps.addBatch();
			}
			ps.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
