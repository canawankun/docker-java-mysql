package com.aksh.marketlog.res;

import static org.junit.Assert.assertEquals;

import java.sql.Time;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.aksh.marketlog.dto.HourlyAnalytics;
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@ActiveProfiles("ut")
public class AnalyticsControllerTest {


	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void pingtest() {
		ResponseEntity<String> pingResponse=restTemplate.getForEntity("/analytics/ping", String.class);
		assertEquals(HttpStatus.OK,pingResponse.getStatusCode());
	}

	
	
	@Test
	public void lastExecutionTest() {
		Date executionTime=new Date();
		HourlyAnalytics exec=new HourlyAnalytics();
		exec.setStock("AAPL");
		exec.setHour(new Time(System.currentTimeMillis()));
		exec.setTradeDate(new Date());
		exec.setEntryTime(executionTime);
		
		HttpEntity<HourlyAnalytics> request = new HttpEntity<>(exec);
		
		ResponseEntity<HourlyAnalytics> res=restTemplate.postForEntity("/analytics/save", request, HourlyAnalytics.class);
		assertEquals(HttpStatus.OK,res.getStatusCode());

		HourlyAnalytics fromDb=res.getBody();
		ResponseEntity<HourlyAnalytics> pingResponse=restTemplate.getForEntity("/analytics/last",HourlyAnalytics.class);
		assertEquals(HttpStatus.OK,pingResponse.getStatusCode());
		HourlyAnalytics lastFromDB=pingResponse.getBody();
		
		assertEquals(fromDb.getId(), lastFromDB.getId());
		System.out.println(pingResponse.getBody());
	}
	
	


}
