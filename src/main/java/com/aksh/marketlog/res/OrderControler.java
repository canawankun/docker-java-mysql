package com.aksh.marketlog.res;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aksh.marketlog.dao.MarketDataRepository;
import com.aksh.marketlog.dto.Execution;
import com.aksh.marketlog.dto.ExecutionMessage;
import com.aksh.marketlog.dto.NewOrder;

@RestController
@RequestMapping("orders")
public class OrderControler {
	
	private static final Logger logger=Logger.getLogger(OrderControler.class);
	
	@Value("${db.datasource.driver}")
	private String driverClass;
	
	@Autowired
	private MarketDataRepository repository;
	
	
	@RequestMapping(method=RequestMethod.GET,path="/ping")
    public String ping() {
		logger.info(driverClass);
        return driverClass;
    }
	
	@RequestMapping(method=RequestMethod.GET,path="/byref")
	public NewOrder getOrder(@RequestParam(value="refId") int refId){
		logger.info("Order ref id ="+refId);
		return repository.getOrder(refId);
		
	}
	
	@RequestMapping(method=RequestMethod.GET,path="/all")
	public List<NewOrder> getAllOrders(){
		return repository.getAllOrders();
		
	}
	
	
	@RequestMapping(method=RequestMethod.POST,path="/save")
	public NewOrder addOrder(@RequestBody NewOrder order){
		logger.info("Order - "+order);
		return repository.save(order);
	}
	
	@RequestMapping(method=RequestMethod.POST,path="/execute")
	public Execution execute(@RequestParam(value="refId") int refId,@RequestBody ExecutionMessage executionMessage){
		logger.info("RefId:"+refId+"exec:"+executionMessage);
		return repository.fillOrder(refId, executionMessage);
		
	}

	
}
