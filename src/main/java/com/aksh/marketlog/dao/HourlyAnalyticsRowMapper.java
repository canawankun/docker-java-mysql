package com.aksh.marketlog.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.aksh.marketlog.dto.HourlyAnalytics;

public class HourlyAnalyticsRowMapper implements RowMapper<HourlyAnalytics> {
	
	@Override
	public HourlyAnalytics mapRow(ResultSet rs, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		HourlyAnalytics hourlyAnalytics=new HourlyAnalytics();
		hourlyAnalytics.setId(rs.getInt("ID"));
		hourlyAnalytics.setHour(rs.getTime("HOUR"));
		hourlyAnalytics.setMinPrice(rs.getFloat("MIN_PRICE"));
		hourlyAnalytics.setMaxPrice(rs.getFloat("MAX_PRICE"));
		hourlyAnalytics.setAvgPrice(rs.getFloat("AVG_PRICE"));
		hourlyAnalytics.setMinQty(rs.getInt("MIN_QTY"));
		hourlyAnalytics.setMaxQty(rs.getInt("MAX_QTY"));
		hourlyAnalytics.setAvgQty(rs.getInt("AVG_QTY"));
		hourlyAnalytics.setMinValue(rs.getFloat("MIN_VALUE"));
		hourlyAnalytics.setMaxValue(rs.getFloat("MAX_VALUE"));
		hourlyAnalytics.setAvgValue(rs.getFloat("AVG_VALUE"));
		hourlyAnalytics.setStock(rs.getString("STOCK"));
		hourlyAnalytics.setEntryTime(rs.getDate("ENTRY_TIME"));
		hourlyAnalytics.setLastUpdate(rs.getDate("LAST_UPDATE"));
		return hourlyAnalytics;
	}

}
