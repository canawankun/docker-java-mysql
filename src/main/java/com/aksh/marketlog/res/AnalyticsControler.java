package com.aksh.marketlog.res;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.aksh.marketlog.dao.MarketDataRepository;
import com.aksh.marketlog.dto.HourlyAnalytics;

@RestController
@RequestMapping("analytics")
public class AnalyticsControler {
	private static final Logger logger=Logger.getLogger(AnalyticsControler.class);
	@Autowired
	private MarketDataRepository repository;

	@RequestMapping("/ping")
    public String ping() {
        return "OK";
    }

	@RequestMapping(method=RequestMethod.GET,path="/last")
	public HourlyAnalytics getLastAnalytics(){
		return repository.currentHourlyAnalytics();
	}
	
	
	@RequestMapping(method=RequestMethod.POST,path="/save")
	public HourlyAnalytics saveOrder(@RequestBody HourlyAnalytics order){
		logger.info("HourlyAnalytics - "+order);
		return repository.save(order);
	}
}
