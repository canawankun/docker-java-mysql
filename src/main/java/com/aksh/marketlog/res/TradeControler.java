package com.aksh.marketlog.res;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.aksh.marketlog.dao.MarketDataRepository;
import com.aksh.marketlog.dto.Execution;

@RestController
@RequestMapping("executions")
public class TradeControler {
	
	private static final Logger logger=Logger.getLogger(TradeControler.class);
	
	@Value("${db.datasource.driver}")
	private String driverClass;
	
	@Autowired
	private MarketDataRepository repository;
	
	
	@RequestMapping(method=RequestMethod.GET,path="/ping")
    public String ping() {
		logger.info(driverClass);
        return driverClass;
    }
	
	@RequestMapping(method=RequestMethod.GET,path="/last")
	public Execution getLastExecution(){
		return repository.lastExecution();
		
	}

	@RequestMapping(method=RequestMethod.POST,path="/save")
	public Execution saveExecution(@RequestBody Execution exec){
		logger.info("Execution - "+exec);
		return repository.save(exec);
	}
	
}
