package com.aksh.marketlog.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.aksh.marketlog.dto.Execution;

public class ExecutionRowMapper implements RowMapper<Execution> {
	
	@Override
	public Execution mapRow(ResultSet rs, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		Execution execution=new Execution();
		execution.setId(rs.getInt("ID"));
		execution.setRefId(rs.getInt("REFID"));
		execution.setStock(rs.getString("STOCK"));
		execution.setQty(rs.getInt("QTY"));
		execution.setPrice(rs.getFloat("PRICE"));
		execution.setExectutionTime(rs.getDate("EXECUTION_TIME"));
		execution.setLastUpdate(rs.getDate("LAST_UPDATE"));
		return execution;
	}

}
