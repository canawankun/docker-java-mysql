package com.aksh.marketlog.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.aksh.marketlog.dto.NewOrder;
import com.aksh.marketlog.dto.OrderStatus;
import com.aksh.marketlog.dto.OrderType;

public class OrderRowMapper implements RowMapper<NewOrder> {
	
	@Override
	public NewOrder mapRow(ResultSet rs, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		NewOrder newOrder=new NewOrder();
		newOrder.setId(rs.getInt("ID"));
		newOrder.setRefId(rs.getInt("REFID"));
		newOrder.setType(OrderType.valueOf(rs.getString("TYPE")));
		newOrder.setStock(rs.getString("STOCK"));
		newOrder.setStatus(OrderStatus.valueOf(rs.getString("STATUS")));
		newOrder.setQty(rs.getInt("QTY"));
		newOrder.setRemainingQty((rs.getInt("REM_QTY")));
		newOrder.setPrice(rs.getFloat("PRICE"));
		newOrder.setEntryTime(rs.getDate("ENTRY_TIME"));
		newOrder.setLastUpdate(rs.getDate("LAST_UPDATE_TIME"));
		return newOrder;
	}

}
