package com.aksh.marketlog.res.it;

import static org.junit.Assert.assertEquals;

import java.sql.Time;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.aksh.marketlog.dto.HourlyAnalytics;
@RunWith(SpringRunner.class)
public class AnalyticsControllerIT {


	private RestTemplate restTemplate=new RestTemplate();
	private String serverUrl;
	
	@Before
	public void setup(){
		serverUrl=BaseIT.getServerUrl();
		System.out.println("In unit test server url:"+serverUrl);
	}



	@Test
	public void pingtest() {
		ResponseEntity<String> pingResponse=restTemplate.getForEntity(serverUrl+"/analytics/ping", String.class);
		assertEquals(HttpStatus.OK,pingResponse.getStatusCode());
	}

	
	
	@Test
	public void saveAnalytics() {
		Date executionTime=new Date();
		HourlyAnalytics exec=new HourlyAnalytics();
		exec.setStock("AAPL");
		exec.setHour(new Time(System.currentTimeMillis()));
		exec.setTradeDate(new Date());
		exec.setEntryTime(executionTime);
		
		HttpEntity<HourlyAnalytics> request = new HttpEntity<>(exec);
		
		ResponseEntity<HourlyAnalytics> res=restTemplate.postForEntity(serverUrl+"/analytics/save", request, HourlyAnalytics.class);
		assertEquals(HttpStatus.OK,res.getStatusCode());

		HourlyAnalytics fromDb=res.getBody();
		ResponseEntity<HourlyAnalytics> pingResponse=restTemplate.getForEntity(serverUrl+"/analytics/last",HourlyAnalytics.class);
		assertEquals(HttpStatus.OK,pingResponse.getStatusCode());
		HourlyAnalytics lastFromDB=pingResponse.getBody();
		
		assertEquals(fromDb.getId(), lastFromDB.getId());
		System.out.println(pingResponse.getBody());
	}
	
	


}
