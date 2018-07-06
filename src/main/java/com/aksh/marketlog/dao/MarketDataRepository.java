/**
 * 
 */
package com.aksh.marketlog.dao;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Time;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.aksh.marketlog.dto.Execution;
import com.aksh.marketlog.dto.ExecutionMessage;
import com.aksh.marketlog.dto.HourlyAnalytics;
import com.aksh.marketlog.dto.NewOrder;
import com.aksh.marketlog.dto.OrderStatus;

/**
 * @author arawa3
 *
 */
@Repository
public class MarketDataRepository {
	private static final Logger logger = Logger.getLogger(MarketDataRepository.class);

	@Autowired
	private JdbcTemplate template;

	@PostConstruct
	public void init() {
		logger.info("Initiatlizing, checking values set- template:" + (template != null));

	}

	private static final String INSERT_ORDER = "INSERT INTO ORDERS (REFID,TYPE,STOCK,PRICE,QTY,STATUS,ENTRY_TIME,LAST_UPDATE_TIME,REM_QTY)"
			+ " VALUES (?,?,?,?,?,?,?,?,?)";

	
	
	public NewOrder save(NewOrder ord) {
		ord.setRemainingQty(ord.getQty());

		/*
		 * template.update(INSERT_ORDER, new Object[] { ord.getId(),
		 * ord.getRefId(), ord.getType(), ord.getStock(), ord.getStatus(),
		 * ord.getEntryTime(), new Date() });
		 */
		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(con -> {
			PreparedStatement ps = con.prepareStatement(INSERT_ORDER, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, ord.getRefId());
			ps.setString(2, ord.getType() + "");
			ps.setString(3, ord.getStock());
			ps.setFloat(4, ord.getPrice());
			ps.setInt(5, ord.getQty());
			ps.setString(6, ord.getStatus() + "");
			ps.setDate(7, new java.sql.Date(ord.getEntryTime().getTime()));
			ps.setDate(8, new java.sql.Date(System.currentTimeMillis()));
			ps.setInt(9, ord.getRemainingQty());
			return ps;
		}, keyHolder);
		ord.setId(keyHolder.getKey().intValue());
		return ord;
	}

	private static final String UPDATE_ORDER = "UPDATE ORDERS SET STATUS=? , LAST_UPDATE_TIME =? ,REM_QTY =?  WHERE REFID=?";
	
	public void updateOrder(NewOrder ord){
		template.update(con -> {
			PreparedStatement ps = con.prepareStatement(UPDATE_ORDER);
			ps.setString(1, ord.getStatus() + "");
			ps.setDate(2, new java.sql.Date(System.currentTimeMillis()));
			ps.setInt(3, ord.getRemainingQty());
			ps.setInt(4, ord.getRefId());
			return ps;
		});
	}
	private static final String INSERT_EXECUTION = "INSERT INTO EXECUTIONS (REFID,STOCK,PRICE,QTY,EXECUTION_TIME,LAST_UPDATE)"
			+ " VALUES (?,?,?,?,?,?)";

	public Execution save(Execution exec) {
		/*
		 * template.update(INSERT_EXECUTION, new Object[] { exec.getId(),
		 * exec.getRefId(), exec.getStock(), exec.getPrice(), exec.getQty(),
		 * exec.getExectutionTime(), new Date() });
		 */

		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(con -> {
			PreparedStatement ps = con.prepareStatement(INSERT_EXECUTION, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, exec.getRefId());
			ps.setString(2, exec.getStock() + "");
			ps.setFloat(3, exec.getPrice());
			ps.setInt(4, exec.getQty());
			ps.setDate(5, new java.sql.Date(exec.getExectutionTime().getTime()));
			ps.setDate(6, new java.sql.Date(System.currentTimeMillis()));
			return ps;
		}, keyHolder);
		exec.setId(keyHolder.getKey().intValue());
		return exec;
	}

	private static final String INSERT_HOURLY_ANALYTICS = "INSERT INTO HOURLY_ANALYTICS (HOUR,STOCK,TRADE_DATE"
			+ ",MIN_PRICE,MAX_PRICE,AVG_PRICE" + ",MIN_QTY,MAX_QTY,AVG_QTY" + ",MIN_VALUE,MAX_VALUE,AVG_VALUE"
			+ ",ENTRY_TIME,LAST_UPDATE)" + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	public HourlyAnalytics save(HourlyAnalytics ha) {

		/*
		 * template.update(INSERT_HOURLY_ANALYTICS, new Object[] { ha.getHour(),
		 * ha.getStock(), ha.getTradeDate(), ha.getMinPrice(), ha.getMaxPrice(),
		 * ha.getAvgPrice(), ha.getMinQty(), ha.getMaxQty(), ha.getAvgQty(),
		 * ha.getMinValue(), ha.getMaxValue(), ha.getAvgValue(),
		 * ha.getEntryTime(), new Date() });
		 */
		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(con -> {
			PreparedStatement ps = con.prepareStatement(INSERT_HOURLY_ANALYTICS, Statement.RETURN_GENERATED_KEYS);

			ps.setTime(1, new Time(ha.getHour().getTime()));
			ps.setString(2, ha.getStock());
			ps.setDate(3, new java.sql.Date(ha.getTradeDate().getTime()));
			ps.setFloat(4, ha.getMinPrice());
			ps.setFloat(5, ha.getMaxPrice());
			ps.setFloat(6, ha.getAvgPrice());

			ps.setInt(7, ha.getMinQty());
			ps.setInt(8, ha.getMaxQty());
			ps.setInt(9, ha.getAvgQty());

			ps.setFloat(10, ha.getMinValue());
			ps.setFloat(11, ha.getMaxValue());
			ps.setFloat(12, ha.getAvgValue());

			ps.setDate(13, new java.sql.Date(ha.getEntryTime().getTime()));
			ps.setDate(14, new java.sql.Date(System.currentTimeMillis()));

			return ps;
		}, keyHolder);
		ha.setId(keyHolder.getKey().intValue());
		return ha;
	}

	private static final String GET_DAYS_ANALYTICS = "INSERT INTO EXECUTIONS (REFID,STOCK,PRICE,QTY,EXECUTION_TIME,LAST_UPDATE)"
			+ " VALUES (?,?,?,?,?,?)";
	
	public Execution lastExecution() {
		Execution execution = null;
		List<Execution> executions = template
				.query("SELECT * FROM EXECUTIONS WHERE ID=(SELECT MAX(ID) FROM EXECUTIONS)", new ExecutionRowMapper());
		if (executions != null) {
			execution = executions.stream().findFirst().orElse(null);
		}
		return execution;
	}

	public HourlyAnalytics currentHourlyAnalytics() {
		HourlyAnalytics ha = null;
		List<HourlyAnalytics> executions = template.query(
				"SELECT * FROM HOURLY_ANALYTICS WHERE ID=(SELECT MAX(ID) FROM HOURLY_ANALYTICS)",
				new HourlyAnalyticsRowMapper());
		if (executions != null) {
			ha = executions.stream().findFirst().orElse(null);
		}
		return ha;
	}

	public NewOrder getOrder(int refId) {
		NewOrder order = null;
		List<NewOrder> orders = template.query("SELECT * FROM ORDERS WHERE REFID=?", new Object[] { refId },
				new OrderRowMapper());
		if (orders != null) {
			order = orders.stream().findFirst().orElse(null);
		}

		return order;
	}
	public List<NewOrder> getAllOrders() {
		List<NewOrder> orders = template.query("SELECT * FROM ORDERS ", new Object[] {  },
				new OrderRowMapper());
		return orders;
	}
	
	public Execution fillOrder(int refId,ExecutionMessage exec){
		Execution execution=null;
		NewOrder order=getOrder(refId);
		if(order!=null){
			if(order.getRemainingQty()>exec.getQty()){
				order.setStatus(OrderStatus.P);
				order.setRemainingQty(order.getQty()-exec.getQty());
				
			}else if(order.getRemainingQty()==exec.getQty()){
				order.setStatus(OrderStatus.F);
				order.setRemainingQty(order.getQty()-exec.getQty());
			}else{
				throw new RuntimeException("Can't fill more than remaining, remaining:"+order.getRemainingQty()+",execQty:"+exec.getQty());
			}
			execution = createExecution(refId, exec, order);
			execution=save(execution);
			updateOrder(order);
		}
		
		return execution;
	}

	private Execution createExecution(int refId, ExecutionMessage exec, NewOrder order) {
		Execution execution;
		execution=new Execution();
		execution.setExectutionTime(exec.getExectutionTime());
		execution.setLastUpdate(new Date());
		execution.setPrice(exec.getPrice());
		execution.setQty(exec.getQty());
		execution.setRefId(refId);
		execution.setStock(order.getStock());
		return execution;
	}

	@PreDestroy
	public void destrov() {
		logger.info("Destroy");
	}

}
